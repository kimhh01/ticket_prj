package kr.admin.adminLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.admin.common.AdminDBConnection;
import kr.admin.common.PasswordHashUtil;

public class AdminLoginDAO {

    // 로그인용 관리자 조회: 비밀번호는 SQL에서 비교하지 않고 Service에서 PBKDF2 검증
    public AdminInfoDTO selectAdminById(String id) {

        AdminInfoDTO admin = null;

        String sql = """
                SELECT ADMIN_ID,
                       ADMIN_NAME,
                       ADMIN_EMAIL,
                       ADMIN_TEL,
                       ID,
                       PASSWORD
                  FROM ADMIN
                 WHERE TRIM(ID) = ?
                """;

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    admin = mapAdmin(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return admin;
    }

    // 기존 호출 코드 보호용 메서드
    // 실제 비밀번호 검증은 AdminLoginService.loginAdmin()에서 처리합니다.
    public AdminInfoDTO selectAdmin(AdminDTO adminDTO) {

        if (adminDTO == null) {
            return null;
        }

        return selectAdminById(adminDTO.getId());
    }

    // 저장된 비밀번호 해시 조회
    public String selectPasswordByAdminCode(int adminCode) {

        String password = null;

        String sql = """
                SELECT PASSWORD
                  FROM ADMIN
                 WHERE ADMIN_ID = ?
                """;

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setInt(1, adminCode);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    password = rs.getString("PASSWORD");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return password;
    }

    // 기존 비밀번호 확인
    // 다른 코드에서 직접 호출하더라도 PBKDF2/기존 평문/기존 SHA-1 값을 검증할 수 있게 유지합니다.
    public boolean verifyPassword(int adminCode, String currentPw) {

        String storedPassword = selectPasswordByAdminCode(adminCode);

        if (storedPassword == null || currentPw == null) {
            return false;
        }

        if (PasswordHashUtil.isPbkdf2Hash(storedPassword)) {
            return PasswordHashUtil.matches(currentPw, storedPassword);
        }

        if (PasswordHashUtil.matchesLegacySha1(currentPw, storedPassword)) {
            return true;
        }

        return storedPassword.equals(currentPw);
    }

    // 비밀번호 변경
    // newPw는 Service에서 PBKDF2 해시된 값으로 전달합니다.
    public int updatePassword(int adminCode, String newPw) {

        int result = 0;

        String sql = """
                UPDATE ADMIN
                   SET PASSWORD = ?
                 WHERE ADMIN_ID = ?
                """;

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setString(1, newPw);
            pstmt.setInt(2, adminCode);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void clearAdminSession() {
    }

    private AdminInfoDTO mapAdmin(ResultSet rs) throws Exception {

        AdminInfoDTO admin = new AdminInfoDTO();

        admin.setAdminCode(rs.getInt("ADMIN_ID"));
        admin.setId(rs.getString("ID"));
        admin.setPassword(rs.getString("PASSWORD"));
        admin.setAdminName(rs.getString("ADMIN_NAME"));
        admin.setAdminEmail(rs.getString("ADMIN_EMAIL"));
        admin.setAdminTel(rs.getString("ADMIN_TEL"));

        return admin;
    }
}

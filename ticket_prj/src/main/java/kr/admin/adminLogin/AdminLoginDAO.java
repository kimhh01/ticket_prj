package kr.admin.adminLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.admin.common.AdminDBConnection;

public class AdminLoginDAO {

    // 로그인
    public AdminInfoDTO selectAdmin(AdminDTO adminDTO) {

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
                   AND TRIM(PASSWORD) = ?
                """;

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setString(1, adminDTO.getId());
            pstmt.setString(2, adminDTO.getPassword());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    admin = new AdminInfoDTO();

                    admin.setAdminCode(rs.getInt("ADMIN_ID"));
                    admin.setId(rs.getString("ID"));
                    admin.setPassword(rs.getString("PASSWORD"));

                    admin.setAdminName(rs.getString("ADMIN_NAME"));
                    admin.setAdminEmail(rs.getString("ADMIN_EMAIL"));
                    admin.setAdminTel(rs.getString("ADMIN_TEL"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return admin;
    }

    // 관리자 상세 조회
    public AdminInfoDTO selectAdminDetail(int adminCode) {

        AdminInfoDTO admin = null;

        String sql = """
                SELECT ADMIN_ID,
                       ADMIN_NAME,
                       ADMIN_EMAIL,
                       ADMIN_TEL,
                       ID,
                       PASSWORD
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

                    admin = new AdminInfoDTO();

                    admin.setAdminCode(rs.getInt("ADMIN_ID"));
                    admin.setId(rs.getString("ID"));
                    admin.setPassword(rs.getString("PASSWORD"));

                    admin.setAdminName(rs.getString("ADMIN_NAME"));
                    admin.setAdminEmail(rs.getString("ADMIN_EMAIL"));
                    admin.setAdminTel(rs.getString("ADMIN_TEL"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return admin;
    }

    // 관리자 정보 수정
    public int updateAdminProfile(AdminInfoDTO admin) {

        int result = 0;

        String sql = """
                UPDATE ADMIN
                   SET ADMIN_NAME = ?,
                       ADMIN_EMAIL = ?,
                       ADMIN_TEL = ?
                 WHERE ADMIN_ID = ?
                """;

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setString(1, admin.getAdminName());
            pstmt.setString(2, admin.getAdminEmail());
            pstmt.setString(3, admin.getAdminTel());
            pstmt.setInt(4, admin.getAdminCode());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // 기존 비밀번호 확인
    public boolean verifyPassword(int adminCode, String currentPw) {

        boolean result = false;

        String sql = """
                SELECT COUNT(*) AS CNT
                  FROM ADMIN
                 WHERE ADMIN_ID = ?
                   AND PASSWORD = ?
                """;

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setInt(1, adminCode);
            pstmt.setString(2, currentPw);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    result = rs.getInt("CNT") > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // 비밀번호 변경
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
}
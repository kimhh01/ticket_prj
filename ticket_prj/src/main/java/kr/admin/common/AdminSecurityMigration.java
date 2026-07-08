package kr.admin.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminSecurityMigration {

    public static void main(String[] args) {
        run();
        System.out.println("관리자 보안 마이그레이션이 완료되었습니다.");
    }

    public static int run() {

        List<AdminSecurityRow> adminList = selectAdminList();
        int updateCount = 0;

        for (AdminSecurityRow admin : adminList) {

            String password = admin.password;

            // 기존 평문 비밀번호만 PBKDF2로 변환합니다.
            // 이미 PBKDF2이거나 SHA-1 40자리 값인 경우에는 로그인 성공 시 자동 전환되도록 둡니다.
            if (password != null
                    && !PasswordHashUtil.isPbkdf2Hash(password)
                    && !isSha1Hex(password)) {
                password = PasswordHashUtil.hashPassword(password);
            }

            String adminName = admin.adminName;
            String adminEmail = admin.adminEmail;
            String adminTel = admin.adminTel;

            if (adminName != null && !AES256Util.isEncrypted(adminName)) {
                adminName = AES256Util.encrypt(adminName);
            }

            if (adminEmail != null && !AES256Util.isEncrypted(adminEmail)) {
                adminEmail = AES256Util.encrypt(adminEmail);
            }

            if (adminTel != null && !AES256Util.isEncrypted(adminTel)) {
                adminTel = AES256Util.encrypt(adminTel);
            }

            updateCount += updateAdmin(admin.adminCode, password, adminName, adminEmail, adminTel);
        }

        return updateCount;
    }

    private static List<AdminSecurityRow> selectAdminList() {

        List<AdminSecurityRow> list = new ArrayList<>();

        String sql = """
                SELECT ADMIN_ID,
                       PASSWORD,
                       ADMIN_NAME,
                       ADMIN_EMAIL,
                       ADMIN_TEL
                  FROM ADMIN
                """;

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {
                AdminSecurityRow row = new AdminSecurityRow();
                row.adminCode = rs.getInt("ADMIN_ID");
                row.password = rs.getString("PASSWORD");
                row.adminName = rs.getString("ADMIN_NAME");
                row.adminEmail = rs.getString("ADMIN_EMAIL");
                row.adminTel = rs.getString("ADMIN_TEL");

                list.add(row);
            }

        } catch (Exception e) {
            throw new RuntimeException("관리자 데이터 조회 중 오류가 발생했습니다.", e);
        }

        return list;
    }

    private static int updateAdmin(
            int adminCode,
            String password,
            String adminName,
            String adminEmail,
            String adminTel
    ) {

        int result = 0;

        String sql = """
                UPDATE ADMIN
                   SET PASSWORD = ?,
                       ADMIN_NAME = ?,
                       ADMIN_EMAIL = ?,
                       ADMIN_TEL = ?
                 WHERE ADMIN_ID = ?
                """;

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setString(1, password);
            pstmt.setString(2, adminName);
            pstmt.setString(3, adminEmail);
            pstmt.setString(4, adminTel);
            pstmt.setInt(5, adminCode);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("관리자 데이터 수정 중 오류가 발생했습니다. adminCode=" + adminCode, e);
        }

        return result;
    }

    private static boolean isSha1Hex(String value) {
        return value != null && value.matches("^[0-9A-Fa-f]{40}$");
    }

    private static class AdminSecurityRow {
        private int adminCode;
        private String password;
        private String adminName;
        private String adminEmail;
        private String adminTel;
    }
}

package kr.admin.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.admin.common.AdminDBConnection;

public class AdminMyPageDAO {

    public AdminMyPageDTO selectAdminInfo(int adminCode) {

        AdminMyPageDTO dto = null;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ADMIN_ID, ");
        sql.append("        ID, ");
        sql.append("        ADMIN_NAME, ");
        sql.append("        ADMIN_EMAIL, ");
        sql.append("        ADMIN_TEL ");
        sql.append("   FROM ADMIN ");
        sql.append("  WHERE ADMIN_ID = ? ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, adminCode);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    dto = new AdminMyPageDTO();

                    dto.setAdminCode(rs.getInt("ADMIN_ID"));
                    dto.setAdminId(rs.getString("ID"));
                    dto.setAdminName(rs.getString("ADMIN_NAME"));
                    dto.setAdminEmail(rs.getString("ADMIN_EMAIL"));
                    dto.setAdminTel(rs.getString("ADMIN_TEL"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    public int updateAdminInfo(AdminMyPageDTO admin) {

        int result = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE ADMIN ");
        sql.append("    SET ADMIN_NAME = ?, ");
        sql.append("        ADMIN_EMAIL = ?, ");
        sql.append("        ADMIN_TEL = ? ");
        sql.append("  WHERE ADMIN_ID = ? ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
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

    public int updateAdminInfoWithPassword(AdminMyPageDTO admin) {

        int result = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE ADMIN ");
        sql.append("    SET ADMIN_NAME = ?, ");
        sql.append("        ADMIN_EMAIL = ?, ");
        sql.append("        ADMIN_TEL = ?, ");
        sql.append("        PASSWORD = ? ");
        sql.append("  WHERE ADMIN_ID = ? ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setString(1, admin.getAdminName());
            pstmt.setString(2, admin.getAdminEmail());
            pstmt.setString(3, admin.getAdminTel());
            pstmt.setString(4, admin.getAdminPassword());
            pstmt.setInt(5, admin.getAdminCode());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
package admin;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.DBConnection;

public class AdminLoginDAO {

	private File getDBFile() {

		String path = AdminLoginDAO.class.getClassLoader().getResource("properties/database.properties").getPath();

		File file = new File(path);

		return file;
	}

	// 로그인
	public AdminInfoDTO selectAdmin(AdminDTO adminDTO) {

		AdminDTO aDTO = null;

		String sql = """
				SELECT ADMIN_ID,ADMIN_NAME,ADMIN_EMAIL,ADMIN_TEL,ID,PASSWORD
				FROM ADMIN
				WHERE TRIM(ID)=?
				AND TRIM(PASSWORD)=?
				""";

		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement pstmt = con.prepareStatement(sql);) {

			pstmt.setString(1, adminDTO.getId());
			pstmt.setString(2, adminDTO.getPassword());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				aDTO = new AdminDTO();
				aDTO.setAdminCode(rs.getInt("ADMIN_ID"));
				aDTO.setId(rs.getString("ID"));
				aDTO.setPassword(rs.getString("PASSWORD"));
				aDTO.setAdminName(rs.getString("ADMIN_NAME"));
				aDTO.setAdminEmail(rs.getString("ADMIN_EMAIL"));
				aDTO.setAdminTel(rs.getString("ADMIN_TEL"));
			} else {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return aDTO;
	}

	// 관리자 상세 조회
	public AdminInfoDTO selectAdminDetail(int adminCode) {
		AdminDTO aDTO = null;
		String sql = """
					SELECT ADMIN_ID,ADMIN_NAME,ADMIN_EMAIL,ADMIN_TEL,ID,PASSWORD
					FROM ADMIN
					WHERE ADMIN_ID=?
					""";

		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement pstmt = con.prepareStatement(sql);
		) {
			pstmt.setInt(1, adminCode);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				aDTO = new AdminDTO();
				aDTO.setAdminCode(rs.getInt("ADMIN_ID"));
				aDTO.setId(rs.getString("ID"));
				aDTO.setPassword(rs.getString("PASSWORD"));
				aDTO.setAdminName(rs.getString("ADMIN_NAME"));
				aDTO.setAdminEmail(rs.getString("ADMIN_EMAIL"));
				aDTO.setAdminTel(rs.getString("ADMIN_TEL"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aDTO;
	}

	// 관리자 정보 수정
	public int updateAdminProfile(AdminInfoDTO admin) {
		return 0;
	}

	// 기존 비밀번호 확인
	public boolean verifyPassword(int adminCode, String currentPw) {
		String sql = """
					SELECT COUNT(*)
					FROM ADMIN
					WHERE ADMIN_ID=?
					AND PASSWORD=?
					""";

		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement pstmt = con.prepareStatement(sql);
		) {
			pstmt.setInt(1, adminCode);
			pstmt.setString(2, currentPw);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 비밀번호 변경
	public int updatePassword(int adminCode, String newPw) {
		String sql = """
					UPDATE ADMIN
					SET PASSWORD=?
					WHERE ADMIN_ID=?
					""";

		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement pstmt = con.prepareStatement(sql);
		) {
			pstmt.setString(1, newPw);
			pstmt.setInt(2, adminCode);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void clearAdminSession() {
	}

}
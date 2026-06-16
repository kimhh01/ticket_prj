package admin;

public class AdminLoginDAO {
	
	public AdminInfoDTO selectAdmin(AdminDTO adminDTO) {
		return null;
	}//selectAdmin
	
	public AdminInfoDTO selectAdminDetail(int adminCode) {
		return null;
	}//selectAdminDetail
	
	public int updateAdminProfile(AdminInfoDTO adminDetailDTO) {
		return 0;
	}//updateAdminProfile
	
	public boolean verifyPassword(int adminCode, String currentPw) {
		return false;
	}//verifyPassword
	
	public int updatePassword(int adminCode, String newPw) {
		return 0;
	}//updatePassword
	
	public void clearAdminSession() {
	}//clearAdminSession
	
}//class

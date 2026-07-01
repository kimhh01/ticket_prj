package kr.admin.adminLogin;

public class AdminLoginService {

	private AdminLoginDAO dao;

	public AdminLoginService(){
		dao = new AdminLoginDAO();
    }

	//로그인
	public AdminInfoDTO loginAdmin(AdminDTO adminDTO){
		return dao.selectAdmin(adminDTO);
	}

	//관리자 정보 조회

	public AdminInfoDTO getAdminProfile(int adminCode){
		return dao.selectAdminDetail(adminCode);
	}

	//정보 수정
	public boolean modifyAdminProfile(AdminInfoDTO admin){
		return dao.updateAdminProfile(admin) > 0;
	}

	//비밀번호 변경
	public boolean changePassword(int adminCode,String currentPw,String newPw){
		if(!dao.verifyPassword(adminCode,currentPw)){
			return false;
		}

		return dao.updatePassword(adminCode,newPw) > 0;
	}

	public void logoutAdmin(){
		dao.clearAdminSession();
	}
	
	// 마이페이지 진입 전 비밀번호 확인
	public boolean checkPasswordForMyPage(int adminCode, String currentPw) {
	    return dao.verifyPassword(adminCode, currentPw);
	}

}
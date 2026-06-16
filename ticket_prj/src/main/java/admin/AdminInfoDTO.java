package admin;

public abstract class AdminInfoDTO {
	
	private AdminDTO admin;
	private String adminName;
	private String adminEmail;
	private String adminTel;
	
	public AdminInfoDTO() {
	}//AdminInfoDTO

	public AdminInfoDTO(AdminDTO admin, String adminName, String adminEmail, String adminTel) {
		this.admin = admin;
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.adminTel = adminTel;
	}//AdminInfoDTO

	public AdminDTO getAdmin() {
		return admin;
	}//getAdmin

	public void setAdmin(AdminDTO admin) {
		this.admin = admin;
	}//setAdmin

	public String getAdminName() {
		return adminName;
	}//getAdminName

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}//setAdminName

	public String getAdminEmail() {
		return adminEmail;
	}//getAdminEmail

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}//setAdminEmail

	public String getAdminTel() {
		return adminTel;
	}//getAdminTel

	public void setAdminTel(String adminTel) {
		this.adminTel = adminTel;
	}//setAdminTel

	@Override
	public String toString() {
		return "AdminInfoDTO [admin=" + admin + ", adminName=" + adminName + ", adminEmail=" + adminEmail
				+ ", adminTel=" + adminTel + "]";
	}//toString
	
}//class

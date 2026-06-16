package admin;

public class AdminDTO extends AdminInfoDTO {
	
	private int adminCode;
	private String id;
	private String password;
	
	public AdminDTO() {
	}//AdminDTO

	public AdminDTO(int adminCode, String id, String password) {
		this.adminCode = adminCode;
		this.id = id;
		this.password = password;
	}//AdminDTO

	public int getAdminCode() {
		return adminCode;
	}//getAdminCode

	public void setAdminCode(int adminCode) {
		this.adminCode = adminCode;
	}//setAdminCode

	public String getId() {
		return id;
	}//getId

	public void setId(String id) {
		this.id = id;
	}//setId

	public String getPassword() {
		return password;
	}//getPassword

	public void setPassword(String password) {
		this.password = password;
	}//setPassword

	@Override
	public String toString() {
		return "AdminDTO [adminCode=" + adminCode + ", id=" + id + ", password=" + password + "]";
	}//toString
	
}//class

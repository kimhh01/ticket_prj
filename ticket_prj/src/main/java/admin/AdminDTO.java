package admin;

public class AdminDTO extends AdminInfoDTO {

	private int adminCode;
	private String id;
	private String password;

	public AdminDTO() {
	}

	public AdminDTO(int adminCode,String id,String password) {
		this.adminCode = adminCode;
		this.id = id;
		this.password = password;
	}

	public int getAdminCode() {
		return adminCode;
	}

	public void setAdminCode(int adminCode) {
		this.adminCode = adminCode;
	}

	public String getId() {
    	return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
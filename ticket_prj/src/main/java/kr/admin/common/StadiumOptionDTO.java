package kr.admin.common;


public class StadiumOptionDTO {
	private int StadiumCode;
	private String StadiumName;
	
	public int getStadiumCode() {
		return StadiumCode;
	}
	public void setStadiumCode(int stadiumCode) {
		StadiumCode = stadiumCode;
	}
	public String getStadiumName() {
		return StadiumName;
	}
	public void setStadiumName(String stadiumName) {
		StadiumName = stadiumName;
	}
	@Override
	public String toString() {
		return "TeamOptionDTO [StadiumCode=" + StadiumCode + ", StadiumName=" + StadiumName + "]";
	}
	public StadiumOptionDTO() {
		super();
	}
	public StadiumOptionDTO(int stadiumCode, String stadiumName) {
		super();
		StadiumCode = stadiumCode;
		StadiumName = stadiumName;
	}
	
}

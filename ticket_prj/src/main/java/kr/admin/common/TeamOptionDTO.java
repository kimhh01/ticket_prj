package kr.admin.common;

public class TeamOptionDTO {
	private int TeamCode;
	private String TeamName;
	
	public int getTeamCode() {
		return TeamCode;
	}
	public void setTeamCode(int teamCode) {
		TeamCode = teamCode;
	}
	public String getTeamName() {
		return TeamName;
	}
	public void setTeamName(String teamName) {
		TeamName = teamName;
	}
	@Override
	public String toString() {
		return "StadiumOptionDTO [TeamCode=" + TeamCode + ", TeamName=" + TeamName + "]";
	}
	public TeamOptionDTO() {
		super();
	}
	public TeamOptionDTO(int teamCode, String teamName) {
		super();
		TeamCode = teamCode;
		TeamName = teamName;
	}
	
	
}

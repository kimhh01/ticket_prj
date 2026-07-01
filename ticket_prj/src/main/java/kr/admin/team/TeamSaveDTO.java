package kr.admin.team;

public class TeamSaveDTO {
	private int teamCode;
	private String teamName;
	private String teamShortName;
	private int stadiumCode;
	private String teamLogoImg;
	public int getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(int teamCode) {
		this.teamCode = teamCode;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamShortName() {
		return teamShortName;
	}
	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
	}
	public int getStadiumCode() {
		return stadiumCode;
	}
	public void setStadiumCode(int stadiumCode) {
		this.stadiumCode = stadiumCode;
	}
	public String getTeamLogoImg() {
		return teamLogoImg;
	}
	public void setTeamLogoImg(String teamLogoImg) {
		this.teamLogoImg = teamLogoImg;
	}
	public TeamSaveDTO() {
		super();
	}
	public TeamSaveDTO(int teamCode, String teamName, String teamShortName, int stadiumCode, String teamLogoImg) {
		super();
		this.teamCode = teamCode;
		this.teamName = teamName;
		this.teamShortName = teamShortName;
		this.stadiumCode = stadiumCode;
		this.teamLogoImg = teamLogoImg;
	}
	@Override
	public String toString() {
		return "TeamSaveDTO [teamCode=" + teamCode + ", teamName=" + teamName + ", teamShortName=" + teamShortName
				+ ", stadiumCode=" + stadiumCode + ", teamLogoImg=" + teamLogoImg + "]";
	}
	
	
	
}

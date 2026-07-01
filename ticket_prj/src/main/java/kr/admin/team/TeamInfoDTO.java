package kr.admin.team;

public class TeamInfoDTO {
	private int teamCode;
	private String teamName;
	private String teamShortName;
	private String teamLogoImg;
	private int stadiumCode;
	private String stadiumName;
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
	public String getTeamLogoImg() {
		return teamLogoImg;
	}
	public void setTeamLogoImg(String teamLogoImg) {
		this.teamLogoImg = teamLogoImg;
	}
	public int getStadiumCode() {
		return stadiumCode;
	}
	public void setStadiumCode(int stadiumCode) {
		this.stadiumCode = stadiumCode;
	}
	public String getStadiumName() {
		return stadiumName;
	}
	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}
	public TeamInfoDTO() {
		super();
	}

	public String getTeamShortName() {
		return teamShortName;
	}
	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
	}
	@Override
	public String toString() {
		return "TeamInfoDTO [teamCode=" + teamCode + ", teamName=" + teamName + ", teamShortName=" + teamShortName
				+ ", teamLogoImg=" + teamLogoImg + ", stadiumCode=" + stadiumCode + ", stadiumName=" + stadiumName
				+ "]";
	}
	
	
}	

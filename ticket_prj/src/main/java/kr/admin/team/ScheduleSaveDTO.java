package kr.admin.team;

public class ScheduleSaveDTO {
	
	private int gameScheduleCode;
	private String homeTeam;
	private String awayTeam;
	private int homeTeamCode;
	private int awayTeamCode;
	private String gameDate;
	private String gameStartTime;
	private int stadiumCode;
	private String stadiumName;
	public int getGameScheduleCode() {
		return gameScheduleCode;
	}
	public void setGameScheduleCode(int gameScheduleCode) {
		this.gameScheduleCode = gameScheduleCode;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public int getHomeTeamCode() {
		return homeTeamCode;
	}
	public void setHomeTeamCode(int homeTeamCode) {
		this.homeTeamCode = homeTeamCode;
	}
	public int getAwayTeamCode() {
		return awayTeamCode;
	}
	public void setAwayTeamCode(int awayTeamCode) {
		this.awayTeamCode = awayTeamCode;
	}
	public String getGameDate() {
		return gameDate;
	}
	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}
	public String getGameStartTime() {
		return gameStartTime;
	}
	public void setGameStartTime(String gameStartTime) {
		this.gameStartTime = gameStartTime;
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
	@Override
	public String toString() {
		return "ScheduleSaveDTO [gameScheduleCode=" + gameScheduleCode + ", homeTeam=" + homeTeam + ", awayTeam="
				+ awayTeam + ", homeTeamCode=" + homeTeamCode + ", awayTeamCode=" + awayTeamCode + ", gameDate="
				+ gameDate + ", gameStartTime=" + gameStartTime + ", stadiumCode=" + stadiumCode + ", stadiumName="
				+ stadiumName + "]";
	}
	
	
}

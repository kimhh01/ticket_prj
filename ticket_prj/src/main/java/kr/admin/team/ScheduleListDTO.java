package kr.admin.team;


public class ScheduleListDTO {
	private int gameScheduleCode;
	private String gameDate;
	private String gameTime;
	private String homeTeam;
	private String awayTeam;
	private String stadiumName;
	private String salesState;
	
	
	public int getGameScheduleCode() {
		return gameScheduleCode;
	}


	public void setGameScheduleCode(int gameScheduleCode) {
		this.gameScheduleCode = gameScheduleCode;
	}


	public String getGameDate() {
		return gameDate;
	}


	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}
	
	public String getGameTime() {
		return gameTime;
	}
	
	
	public void setGameTime(String gameTime) {
		this.gameTime = gameTime;
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


	public String getStadiumName() {
		return stadiumName;
	}


	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}


	public String getSalesState() {
		return salesState;
	}


	public void setSalesState(String salesState) {
		this.salesState = salesState;
	}


	@Override
	public String toString() {
		return "ScheduleListDTO []";
	}
}



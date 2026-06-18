package kr.user.main;

import java.util.Date;

public class MainGameDTO {
	private int gameScheduleCode;
	private Date gameDate;
	private int stadiumCode;
	private String stadiumName;
	private int homeTeamCode;
	private String homeTeamName;
	private String homeTeamLogo;
	private int awayTeamCode;
	private String awayTeamName;
	private String awayTeamLogo;
	private String saleStatus;

	@Override
	public String toString() {
		return "MainGameDTO [gameScheduleCode=" + gameScheduleCode 
				+ ", gameDate=" + gameDate 
				+ ", stadiumCode=" + stadiumCode
				+ ", stadiumName=" + stadiumName 
				+ ", homeTeamCode=" + homeTeamCode 
				+ ", homeTeamName=" + homeTeamName
				+ ", homeTeamLogo=" + homeTeamLogo 
				+ ", awayTeamCode=" + awayTeamCode 
				+ ", awayTeamName=" + awayTeamName
				+ ", awayTeamLogo=" + awayTeamLogo 
				+ ", saleStatus=" + saleStatus 
				+ "]";
	}

	public int getGameScheduleCode() {
		return gameScheduleCode;
	}

	public void setGameScheduleCode(int gameScheduleCode) {
		this.gameScheduleCode = gameScheduleCode;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
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

	public int getHomeTeamCode() {
		return homeTeamCode;
	}

	public void setHomeTeamCode(int homeTeamCode) {
		this.homeTeamCode = homeTeamCode;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getHomeTeamLogo() {
		return homeTeamLogo;
	}

	public void setHomeTeamLogo(String homeTeamLogo) {
		this.homeTeamLogo = homeTeamLogo;
	}

	public int getAwayTeamCode() {
		return awayTeamCode;
	}

	public void setAwayTeamCode(int awayTeamCode) {
		this.awayTeamCode = awayTeamCode;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public String getAwayTeamLogo() {
		return awayTeamLogo;
	}

	public void setAwayTeamLogo(String awayTeamLogo) {
		this.awayTeamLogo = awayTeamLogo;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}
	
	public MainGameDTO() {
		
	}
	
	public MainGameDTO(int gameScheduleCode, Date gameDate, int stadiumCode, String stadiumName, int homeTeamCode,
			String homeTeamName, String homeTeamLogo, int awayTeamCode, String awayTeamName, String awayTeamLogo,
			String saleStatus) {
		super();
		this.gameScheduleCode = gameScheduleCode;
		this.gameDate = gameDate;
		this.stadiumCode = stadiumCode;
		this.stadiumName = stadiumName;
		this.homeTeamCode = homeTeamCode;
		this.homeTeamName = homeTeamName;
		this.homeTeamLogo = homeTeamLogo;
		this.awayTeamCode = awayTeamCode;
		this.awayTeamName = awayTeamName;
		this.awayTeamLogo = awayTeamLogo;
		this.saleStatus = saleStatus;
	}

}

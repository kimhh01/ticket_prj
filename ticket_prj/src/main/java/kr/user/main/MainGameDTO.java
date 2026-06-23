package kr.user.main;

import java.util.Date;

public class MainGameDTO {
	private int gameScheduleCode;	// 경기일정 코드
	private Date gameDate;			// 경기일자
	private String gameStartTime;	// 경기 시작 시간
	private int stadiumCode;			// 구장 코드
	private String stadiumName;		// 구장 이름
	private int homeTeamCode;		// 홈팀 코드
	private String homeTeamName;		// 홈팀 이름
	private String homeTeamLogo;		// 홈팀 로고 이미지 경로
	private int awayTeamCode;		// 원정팀 코드
	private String awayTeamName;		// 원정팀 이름
	private String awayTeamLogo;		// 원정팀 로고 이미지 경로
	private String saleStatus;		// 예매 상태

	public MainGameDTO() {
	}

	public MainGameDTO(int gameScheduleCode, Date gameDate, String gameStartTime, int stadiumCode,
			String stadiumName, int homeTeamCode, String homeTeamName, String homeTeamLogo,
			int awayTeamCode, String awayTeamName, String awayTeamLogo, String saleStatus) {
		this.gameScheduleCode = gameScheduleCode;
		this.gameDate = gameDate;
		this.gameStartTime = gameStartTime;
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

	@Override
	public String toString() {
		return "MainGameDTO [gameScheduleCode=" + gameScheduleCode
				+ ", gameDate=" + gameDate
				+ ", gameStartTime=" + gameStartTime
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

}
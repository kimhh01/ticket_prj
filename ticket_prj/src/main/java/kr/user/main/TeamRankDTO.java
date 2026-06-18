package kr.user.main;

import java.util.Date;

public class TeamRankDTO {

	private int rankNo;
	private int teamCode;
	private String teamName;
	private String teamLogo;
	private String stadiumName;
	private int win;
	private int lose;
	private int draw;
	private double winRate;
	private String season;
	private Date rankUpdateDate;

	public int getRankNo() {
		return rankNo;
	}

	public void setRankNo(int rankNo) {
		this.rankNo = rankNo;
	}

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

	public String getTeamLogo() {
		return teamLogo;
	}

	public void setTeamLogo(String teamLogo) {
		this.teamLogo = teamLogo;
	}

	public String getStadiumName() {
		return stadiumName;
	}

	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public double getWinRate() {
		return winRate;
	}

	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Date getRankUpdateDate() {
		return rankUpdateDate;
	}

	public void setRankUpdateDate(Date rankUpdateDate) {
		this.rankUpdateDate = rankUpdateDate;
	}

	@Override
	public String toString() {
		return "TeamRankDTO [rankNo=" + rankNo + ", teamCode=" + teamCode + ", teamName=" + teamName + ", teamLogo="
				+ teamLogo + ", stadiumName=" + stadiumName + ", win=" + win + ", lose=" + lose + ", draw=" + draw
				+ ", winRate=" + winRate + ", season=" + season + ", rankUpdateDate=" + rankUpdateDate + "]";
	}
	
	public TeamRankDTO() {
		
	}
	
	public TeamRankDTO(int rankNo, int teamCode, String teamName, String teamLogo, String stadiumName, int win,
			int lose, int draw, double winRate, String season, Date rankUpdateDate) {
		super();
		this.rankNo = rankNo;
		this.teamCode = teamCode;
		this.teamName = teamName;
		this.teamLogo = teamLogo;
		this.stadiumName = stadiumName;
		this.win = win;
		this.lose = lose;
		this.draw = draw;
		this.winRate = winRate;
		this.season = season;
		this.rankUpdateDate = rankUpdateDate;
	}

}

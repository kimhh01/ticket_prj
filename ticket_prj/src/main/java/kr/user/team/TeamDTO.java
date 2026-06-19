package kr.user.team;

import java.util.Date;

public class TeamDTO {
	private int teamCode;//팀코드
	private int gameScheduleCode;//경기일정코드
	private Date gameDate;//경기일자
	private String stadiumName;//구장이름
	private String teamHomeImg;//홈팀이미지
	private String teamOtherImg;//원정팀이미지
	private String teamPageimg;//팀페이지이미지
	private String teamHomeName;//홈팀이름
	private String teamOtherName;//원정팀이름
	private String commonNotice;//로고 아래 공지사항
	private String noticeTitle;//각 팀별 공지사항 제목
	private String noticeContent;//공지사항내용
	private String noticeTab;//공지사항구분
	private String noticeImg;//공지사항 이미지 or 리그안내 이미지
	private String noticeWriteDate;//공지사항일자
	public TeamDTO() {
		super();
	}
	
	
	public TeamDTO(int teamCode, int gameScheduleCode, Date gameDate, String stadiumName, String teamHomeImg,
			String teamOtherImg, String teamPageimg, String teamHomeName, String teamOtherName, String commonNotice,
			String noticeTitle, String noticeContent, String noticeTab, String noticeImg, String noticeWriteDate) {
		super();
		this.teamCode = teamCode;
		this.gameScheduleCode = gameScheduleCode;
		this.gameDate = gameDate;
		this.stadiumName = stadiumName;
		this.teamHomeImg = teamHomeImg;
		this.teamOtherImg = teamOtherImg;
		this.teamPageimg = teamPageimg;
		this.teamHomeName = teamHomeName;
		this.teamOtherName = teamOtherName;
		this.commonNotice = commonNotice;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeTab = noticeTab;
		this.noticeImg = noticeImg;
		this.noticeWriteDate = noticeWriteDate;
	}


	public String getNoticeContent() {
		return noticeContent;
	}


	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}


	public int getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(int teamCode) {
		this.teamCode = teamCode;
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
	public String getStadiumName() {
		return stadiumName;
	}
	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}
	public String getTeamHomeImg() {
		return teamHomeImg;
	}
	public void setTeamHomeImg(String teamHomeImg) {
		this.teamHomeImg = teamHomeImg;
	}
	public String getTeamOtherImg() {
		return teamOtherImg;
	}
	public void setTeamOtherImg(String teamOtherImg) {
		this.teamOtherImg = teamOtherImg;
	}
	public String getTeamPageimg() {
		return teamPageimg;
	}
	public void setTeamPageimg(String teamPageimg) {
		this.teamPageimg = teamPageimg;
	}
	public String getTeamHomeName() {
		return teamHomeName;
	}
	public void setTeamHomeName(String teamHomeName) {
		this.teamHomeName = teamHomeName;
	}
	public String getTeamOtherName() {
		return teamOtherName;
	}
	public void setTeamOtherName(String teamOtherName) {
		this.teamOtherName = teamOtherName;
	}
	public String getCommonNotice() {
		return commonNotice;
	}
	public void setCommonNotice(String commonNotice) {
		this.commonNotice = commonNotice;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeTab() {
		return noticeTab;
	}
	public void setNoticeTab(String noticeTab) {
		this.noticeTab = noticeTab;
	}
	public String getNoticeImg() {
		return noticeImg;
	}
	public void setNoticeImg(String noticeImg) {
		this.noticeImg = noticeImg;
	}
	public String getNoticeWriteDate() {
		return noticeWriteDate;
	}
	public void setNoticeWriteDate(String noticeWriteDate) {
		this.noticeWriteDate = noticeWriteDate;
	}


	@Override
	public String toString() {
		return "TeamDTO [teamCode=" + teamCode + ", gameScheduleCode=" + gameScheduleCode + ", gameDate=" + gameDate
				+ ", stadiumName=" + stadiumName + ", teamHomeImg=" + teamHomeImg + ", teamOtherImg=" + teamOtherImg
				+ ", teamPageimg=" + teamPageimg + ", teamHomeName=" + teamHomeName + ", teamOtherName=" + teamOtherName
				+ ", commonNotice=" + commonNotice + ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent
				+ ", noticeTab=" + noticeTab + ", noticeImg=" + noticeImg + ", noticeWriteDate=" + noticeWriteDate
				+ "]";
	}


	
	
	
	
	
}
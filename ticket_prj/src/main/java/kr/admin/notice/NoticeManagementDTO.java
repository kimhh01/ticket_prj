package kr.admin.notice;

public class NoticeManagementDTO {

    private int noticeId;
    private int teamId;
    private String teamName;
    private int noticeTab;
    private String noticeTitle;
    private String noticeImg;
    private String noticeWriteDate;

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getNoticeTab() {
        return noticeTab;
    }

    public void setNoticeTab(int noticeTab) {
        this.noticeTab = noticeTab;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
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
}
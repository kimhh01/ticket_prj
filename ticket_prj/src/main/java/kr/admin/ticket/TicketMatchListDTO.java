package kr.admin.ticket;

public class TicketMatchListDTO {

    private int scheduleCode;
    private String homeTeam;
    private String awayTeam;
    private String gameDate;
    private String gameStartTime;
    private String stadiumName;
    private String saleState;

    private int totalBookedCnt;
    private int generalBookedCnt;
    private int cancelBookedCnt;
    private int totalPrice;

    public int getScheduleCode() {
        return scheduleCode;
    }

    public void setScheduleCode(int scheduleCode) {
        this.scheduleCode = scheduleCode;
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

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getSaleState() {
        return saleState;
    }

    public void setSaleState(String saleState) {
        this.saleState = saleState;
    }

    public int getTotalBookedCnt() {
        return totalBookedCnt;
    }

    public void setTotalBookedCnt(int totalBookedCnt) {
        this.totalBookedCnt = totalBookedCnt;
    }

    public int getGeneralBookedCnt() {
        return generalBookedCnt;
    }

    public void setGeneralBookedCnt(int generalBookedCnt) {
        this.generalBookedCnt = generalBookedCnt;
    }

    public int getCancelBookedCnt() {
        return cancelBookedCnt;
    }

    public void setCancelBookedCnt(int cancelBookedCnt) {
        this.cancelBookedCnt = cancelBookedCnt;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}
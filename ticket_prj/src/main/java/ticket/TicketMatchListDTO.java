package ticket;

public class TicketMatchListDTO {
	private int scheduleCode;
    private String homeTeam;
    private String awayTeam;
    private String gameDate;
    private String gameStartTime;
    private String stadiumName;
    private String saleState;

    public int getScheduleCode() {
        return scheduleCode;
    }//getScheduleCode

    public void setScheduleCode(int scheduleCode) {
        this.scheduleCode = scheduleCode;
    }//setScheduleCode

    public String getHomeTeam() {
        return homeTeam;
    }//getHomeTeam

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }//setHomeTeam

    public String getAwayTeam() {
        return awayTeam;
    }//getAwayTeam

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }//setAwayTeam

    public String getGameDate() {
        return gameDate;
    }//getGameDate

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }//setGameDate

    public String getGameStartTime() {
        return gameStartTime;
    }//getGameStartTime

    public void setGameStartTime(String gameStartTime) {
        this.gameStartTime = gameStartTime;
    }//setGameStartTime

    public String getStadiumName() {
        return stadiumName;
    }//getStadiumName

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }//setStadiumName

    public String getSaleState() {
        return saleState;
    }//getSaleState

    public void setSaleState(String saleState) {
        this.saleState = saleState;
    }//setSaleState
    
}//class

package ticket;

public class TicketZoneSalesDTO {
	private int reservationCode;
    private int totalSeatCnt;
    private int availableSeatCnt;
    private int zonePrice;

    public int getReservationCode() {
        return reservationCode;
    }//getReservationCode

    public void setReservationCode(int reservationCode) {
        this.reservationCode = reservationCode;
    }//setReservationCode

    public int getTotalSeatCnt() {
        return totalSeatCnt;
    }//getTotalSeatCnt

    public void setTotalSeatCnt(int totalSeatCnt) {
        this.totalSeatCnt = totalSeatCnt;
    }//setTotalSeatCnt

    public int getAvailableSeatCnt() {
        return availableSeatCnt;
    }//getAvailableSeatCnt

    public void setAvailableSeatCnt(int availableSeatCnt) {
        this.availableSeatCnt = availableSeatCnt;
    }//setAvailableSeatCnt

    public int getZonePrice() {
        return zonePrice;
    }//getZonePrice

    public void setZonePrice(int zonePrice) {
        this.zonePrice = zonePrice;
    }//setZonePrice
    
}//class

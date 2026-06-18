package ticket;

public class TicketSalesDTO {
	private int bookedCnt;
    private int areaTotalBookedCnt;
    private int totalBookedCnt;
    private int generalBookedCnt;
    private int cancelBookedCnt;
    private int totalPrice;

    public int getBookedCnt() {
        return bookedCnt;
    }//getBookedCnt

    public void setBookedCnt(int bookedCnt) {
        this.bookedCnt = bookedCnt;
    }//setBookedCnt

    public int getAreaTotalBookedCnt() {
        return areaTotalBookedCnt;
    }//getAreaTotalBookedCnt

    public void setAreaTotalBookedCnt(int areaTotalBookedCnt) {
        this.areaTotalBookedCnt = areaTotalBookedCnt;
    }//setAreaTotalBookedCnt

    public int getTotalBookedCnt() {
        return totalBookedCnt;
    }//getTotalBookedCnt

    public void setTotalBookedCnt(int totalBookedCnt) {
        this.totalBookedCnt = totalBookedCnt;
    }//setTotalBookedCnt

    public int getGeneralBookedCnt() {
        return generalBookedCnt;
    }//getGeneralBookedCnt

    public void setGeneralBookedCnt(int generalBookedCnt) {
        this.generalBookedCnt = generalBookedCnt;
    }//setGeneralBookedCnt

    public int getCancelBookedCnt() {
        return cancelBookedCnt;
    }//getCancelBookedCnt

    public void setCancelBookedCnt(int cancelBookedCnt) {
        this.cancelBookedCnt = cancelBookedCnt;
    }//setCancelBookedCnt

    public int getTotalPrice() {
        return totalPrice;
    }//getTotalPrice

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }//setTotalPrice
    
}//class

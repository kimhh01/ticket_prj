package ticket;

public class TicketInfoDTO {
	private int seatCode;
    private String seatName;
    private String ticketType;
    private int price;
    private String saleState;

    public int getSeatCode() {
        return seatCode;
    }//getSeatCode

    public void setSeatCode(int seatCode) {
        this.seatCode = seatCode;
    }//setSeatCode

    public String getSeatName() {
        return seatName;
    }//getSeatName

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }//setSeatName

    public String getTicketType() {
        return ticketType;
    }//getTicketType

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }//setTicketType

    public int getPrice() {
        return price;
    }//getPrice

    public void setPrice(int price) {
        this.price = price;
    }//setPrice

    public String getSaleState() {
        return saleState;
    }//getSaleState

    public void setSaleState(String saleState) {
        this.saleState = saleState;
    }//setSaleState
    
}//class

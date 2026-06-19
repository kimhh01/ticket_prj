package ticket;

public class TicketReservationListDTO {
	private int reservationCode;
    private String memberName;
    private String memberTel;
    private int reservationCnt;
    private int paymentPrice;
    private String reservationDate;
    private String reservationState;

    public int getReservationCode() {
        return reservationCode;
    }//getReservationCode

    public void setReservationCode(int reservationCode) {
        this.reservationCode = reservationCode;
    }//setReservationCode

    public String getMemberName() {
        return memberName;
    }//getMemberName

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }//setMemberName

    public String getMemberTel() {
        return memberTel;
    }//getMemberTel

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }//setMemberTel

    public int getReservationCnt() {
        return reservationCnt;
    }//getReservationCnt

    public void setReservationCnt(int reservationCnt) {
        this.reservationCnt = reservationCnt;
    }//setReservationCnt

    public int getPaymentPrice() {
        return paymentPrice;
    }//getPaymentPrice

    public void setPaymentPrice(int paymentPrice) {
        this.paymentPrice = paymentPrice;
    }//setPaymentPrice

    public String getReservationDate() {
        return reservationDate;
    }//getReservationDate

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }//setReservationDate

    public String getReservationState() {
        return reservationState;
    }//getReservationState

    public void setReservationState(String reservationState) {
        this.reservationState = reservationState;
    }//setReservationState
    
}//class

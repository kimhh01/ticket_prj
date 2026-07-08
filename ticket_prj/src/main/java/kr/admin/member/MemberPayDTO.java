package kr.admin.member;

public class MemberPayDTO {

    private int reservationCode;
    private String memberId;
    private String homeTeam;
    private String awayTeam;
    private int reservationCnt;
    private int paymentPrice;
    private String reservationDate;
    private String reservationState;
    private String ticketInfo;

    public MemberPayDTO() {
    }//MemberPayDTO

    public MemberPayDTO(int reservationCode, String memberId, String homeTeam, String awayTeam,
                        int reservationCnt, int paymentPrice, String reservationDate, String reservationState) {
        this.reservationCode = reservationCode;
        this.memberId = memberId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.reservationCnt = reservationCnt;
        this.paymentPrice = paymentPrice;
        this.reservationDate = reservationDate;
        this.reservationState = reservationState;
    }//MemberPayDTO

    public int getReservationCode() {
        return reservationCode;
    }//getReservationCode

    public void setReservationCode(int reservationCode) {
        this.reservationCode = reservationCode;
    }//setReservationCode

    public String getMemberId() {
        return memberId;
    }//getMemberId

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }//setMemberId

    // 기존 코드 호환용
    public String getMemberCode() {
        return memberId;
    }//getMemberCode

    public void setMemberCode(String memberCode) {
        this.memberId = memberCode;
    }//setMemberCode

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

	public void setTicketInfo(String string) {
		this.ticketInfo = ticketInfo;
	}
	
	public String getTicketInfo() {
	    return ticketInfo;
	}//getTicketInfo

}//class
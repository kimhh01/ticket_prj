package member;

public class MemberPayDTO {

	private int reservationCode;
    private String memberCode;
    private String homeTeam;
    private String awayTeam;
    private int reservationCnt;
    private int paymentPrice;
    private String reservationDate;
    private String reservationState;
    
	public MemberPayDTO() {
	}//MemberPayDTO

	public MemberPayDTO(int reservationCode, String memberCode, String homeTeam, String awayTeam, int reservationCnt,
			int paymentPrice, String reservationDate, String reservationState) {
		this.reservationCode = reservationCode;
		this.memberCode = memberCode;
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

	public String getMemberCode() {
		return memberCode;
	}//getMemberCode

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
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
	
}//class

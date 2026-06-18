package user_ReservationPage;

import java.util.Date;

public class ReservationPageDTO {
	private int reservationId;//예매코드
	private int teamHomeCode;//홈팀코드
	private int teamOtherCode;//원정팀코드
	private int stadiumCode;//구장코드
	private String memberId;//회원아이디
	private Date reservationDate;//예매일
	private int totalPrice; //총금액
	private int payPrice;//결제금액
	private int discountPrice;//할인금액
	private Date gameDate;//경기일자
	private String stadiumName;//구장이름
	private String stadiumImg;//구장이미지
	private String teamHomeName; //홈팀이름
	private String teamHomeImg;//홈팀이미지
	private String teamOtherName;// 원정팀이름
	private String teamOtherImg;//원정팀이미지
	private int stadiumSeatNum;//좌석수
	private int adultSeatPrice;//성인좌석가격
	private int adultSeatNum;//성인좌석수
	private int youthSeatPrice;//청소년좌석수
	private int youthSeatNum;//청소년좌석가격
	private int childSeatPrice;//어린이좌석가격
	private int childSeatNum;//어린이좌석수
	
	
	public ReservationPageDTO() {
		super();
	}
	
	public ReservationPageDTO(int reservationId, int teamHomeCode, int teamOtherCode, int stadiumCode, String memberId,
			Date reservationDate, int totalPrice, int payPrice, int discountPrice, Date gameDate, String stadiumName,
			String stadiumImg, String teamHomeName, String teamHomeImg, String teamOtherName, String teamOtherImg,
			int stadiumSeatNum, int adultSeatPrice, int adultSeatNum, int youthSeatPrice, int youthSeatNum,
			int childSeatPrice, int childSeatNum) {
		this.reservationId = reservationId;
		this.teamHomeCode = teamHomeCode;
		this.teamOtherCode = teamOtherCode;
		this.stadiumCode = stadiumCode;
		this.memberId = memberId;
		this.reservationDate = reservationDate;
		this.totalPrice = totalPrice;
		this.payPrice = payPrice;
		this.discountPrice = discountPrice;
		this.gameDate = gameDate;
		this.stadiumName = stadiumName;
		this.stadiumImg = stadiumImg;
		this.teamHomeName = teamHomeName;
		this.teamHomeImg = teamHomeImg;
		this.teamOtherName = teamOtherName;
		this.teamOtherImg = teamOtherImg;
		this.stadiumSeatNum = stadiumSeatNum;
		this.adultSeatPrice = adultSeatPrice;
		this.adultSeatNum = adultSeatNum;
		this.youthSeatPrice = youthSeatPrice;
		this.youthSeatNum = youthSeatNum;
		this.childSeatPrice = childSeatPrice;
		this.childSeatNum = childSeatNum;
	}
	
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public int getTeamHomeCode() {
		return teamHomeCode;
	}
	public void setTeamHomeCode(int teamHomeCode) {
		this.teamHomeCode = teamHomeCode;
	}
	public int getTeamOtherCode() {
		return teamOtherCode;
	}
	public void setTeamOtherCode(int teamOtherCode) {
		this.teamOtherCode = teamOtherCode;
	}
	public int getStadiumCode() {
		return stadiumCode;
	}
	public void setStadiumCode(int stadiumCode) {
		this.stadiumCode = stadiumCode;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(int payPrice) {
		this.payPrice = payPrice;
	}
	public int getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
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
	public String getStadiumImg() {
		return stadiumImg;
	}
	public void setStadiumImg(String stadiumImg) {
		this.stadiumImg = stadiumImg;
	}
	public String getTeamHomeName() {
		return teamHomeName;
	}
	public void setTeamHomeName(String teamHomeName) {
		this.teamHomeName = teamHomeName;
	}
	public String getTeamHomeImg() {
		return teamHomeImg;
	}
	public void setTeamHomeImg(String teamHomeImg) {
		this.teamHomeImg = teamHomeImg;
	}
	public String getTeamOtherName() {
		return teamOtherName;
	}
	public void setTeamOtherName(String teamOtherName) {
		this.teamOtherName = teamOtherName;
	}
	public String getTeamOtherImg() {
		return teamOtherImg;
	}
	public void setTeamOtherImg(String teamOtherImg) {
		this.teamOtherImg = teamOtherImg;
	}
	public int getStadiumSeatNum() {
		return stadiumSeatNum;
	}
	public void setStadiumSeatNum(int stadiumSeatNum) {
		this.stadiumSeatNum = stadiumSeatNum;
	}
	public int getAdultSeatPrice() {
		return adultSeatPrice;
	}
	public void setAdultSeatPrice(int adultSeatPrice) {
		this.adultSeatPrice = adultSeatPrice;
	}
	public int getAdultSeatNum() {
		return adultSeatNum;
	}
	public void setAdultSeatNum(int adultSeatNum) {
		this.adultSeatNum = adultSeatNum;
	}
	public int getYouthSeatPrice() {
		return youthSeatPrice;
	}
	public void setYouthSeatPrice(int youthSeatPrice) {
		this.youthSeatPrice = youthSeatPrice;
	}
	public int getYouthSeatNum() {
		return youthSeatNum;
	}
	public void setYouthSeatNum(int youthSeatNum) {
		this.youthSeatNum = youthSeatNum;
	}
	public int getChildSeatPrice() {
		return childSeatPrice;
	}
	public void setChildSeatPrice(int childSeatPrice) {
		this.childSeatPrice = childSeatPrice;
	}
	public int getChildSeatNum() {
		return childSeatNum;
	}
	public void setChildSeatNum(int childSeatNum) {
		this.childSeatNum = childSeatNum;
	}
	
	
	@Override
	public String toString() {
		return "ReservationPageDTO [reservationId=" + reservationId + ", teamHomeCode=" + teamHomeCode
				+ ", teamOtherCode=" + teamOtherCode + ", stadiumCode=" + stadiumCode + ", memberId=" + memberId
				+ ", reservationDate=" + reservationDate + ", totalPrice=" + totalPrice + ", payPrice=" + payPrice
				+ ", discountPrice=" + discountPrice + ", gameDate=" + gameDate + ", stadiumName=" + stadiumName
				+ ", stadiumImg=" + stadiumImg + ", teamHomeName=" + teamHomeName + ", teamHomeImg=" + teamHomeImg
				+ ", teamOtherName=" + teamOtherName + ", teamOtherImg=" + teamOtherImg + ", stadiumSeatNum="
				+ stadiumSeatNum + ", adultSeatPrice=" + adultSeatPrice + ", adultSeatNum=" + adultSeatNum
				+ ", youthSeatPrice=" + youthSeatPrice + ", youthSeatNum=" + youthSeatNum + ", childSeatPrice="
				+ childSeatPrice + ", childSeatNum=" + childSeatNum + "]";
	}
	
	
}

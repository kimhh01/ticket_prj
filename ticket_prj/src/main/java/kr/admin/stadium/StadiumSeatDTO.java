package kr.admin.stadium;

public class StadiumSeatDTO {
	private int seatCode;
	private int stadiumCode;
	private String stadiumName;
	private String seatName;
	private int adultPrice;
	private int youthPrice;
	private int childPrice;
	private int seatQty;
	public int getSeatCode() {
		return seatCode;
	}
	public void setSeatCode(int seatCode) {
		this.seatCode = seatCode;
	}
	public int getStadiumCode() {
		return stadiumCode;
	}
	public void setStadiumCode(int stadiumCode) {
		this.stadiumCode = stadiumCode;
	}
	public String getStadiumName() {
		return stadiumName;
	}
	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}
	public String getSeatName() {
		return seatName;
	}
	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}
	public int getAdultPrice() {
		return adultPrice;
	}
	public void setAdultPrice(int adultPrice) {
		this.adultPrice = adultPrice;
	}
	public int getYouthPrice() {
		return youthPrice;
	}
	public void setYouthPrice(int youthPrice) {
		this.youthPrice = youthPrice;
	}
	public int getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(int childPrice) {
		this.childPrice = childPrice;
	}
	public int getSeatQty() {
		return seatQty;
	}
	public void setSeatQty(int seatQty) {
		this.seatQty = seatQty;
	}
	public StadiumSeatDTO() {
		super();
	}
	public StadiumSeatDTO(int seatCode, int stadiumCode, String stadiumName, String seatName, int adultPrice,
			int youthPrice, int childPrice, int seatQty) {
		super();
		this.seatCode = seatCode;
		this.stadiumCode = stadiumCode;
		this.stadiumName = stadiumName;
		this.seatName = seatName;
		this.adultPrice = adultPrice;
		this.youthPrice = youthPrice;
		this.childPrice = childPrice;
		this.seatQty = seatQty;
	}
	
	
}

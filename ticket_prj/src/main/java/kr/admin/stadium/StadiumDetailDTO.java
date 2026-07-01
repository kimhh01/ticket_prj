package kr.admin.stadium;

public class StadiumDetailDTO {
	private int stadiumCode;
	private String stadiumName;
	private String stadiumLocation;
	private String address;
	private int homeTeamCode;
	private int homeTeamCode2;
	private String homeTeamName;
	private String stadiumSeatImg;
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
	public String getStadiumLocation() {
		return stadiumLocation;
	}
	public void setStadiumLocation(String stadiumLocation) {
		this.stadiumLocation = stadiumLocation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getHomeTeamCode() {
		return homeTeamCode;
	}
	public void setHomeTeamCode(int homeTeamCode) {
		this.homeTeamCode = homeTeamCode;
	}
	public String getHomeTeamName() {
		return homeTeamName;
	}
	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}
	public int getHomeTeamCode2() {
		return homeTeamCode2;
	}
	public void setHomeTeamCode2(int homeTeamCode2) {
		this.homeTeamCode2 = homeTeamCode2;
	}
	public String getStadiumSeatImg() {
	    return stadiumSeatImg;
	}

	public void setStadiumSeatImg(String stadiumSeatImg) {
	    this.stadiumSeatImg = stadiumSeatImg;
	}
	public StadiumDetailDTO() {
		super();
	}
	public StadiumDetailDTO(int stadiumCode, String stadiumName, String stadiumLocation, String address,
			int homeTeamCode, int homeTeamCode2, String homeTeamName) {
		super();
		this.stadiumCode = stadiumCode;
		this.stadiumName = stadiumName;
		this.stadiumLocation = stadiumLocation;
		this.address = address;
		this.homeTeamCode = homeTeamCode;
		this.homeTeamCode2 = homeTeamCode2;
		this.homeTeamName = homeTeamName;
	}
	
	
}

package kr.admin.stadium;

public class StadiumDetailDTO {
	private int stadiumCode;
	private String stadiumName;
	private String stadiumLocation;
	private String address;
	private int homeTeamCode;
	private String homeTeamName;
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
	public StadiumDetailDTO() {
		super();
	}
	public StadiumDetailDTO(int stadiumCode, String stadiumName, String stadiumLocation, String address,
			int homeTeamCode, String homeTeamName) {
		super();
		this.stadiumCode = stadiumCode;
		this.stadiumName = stadiumName;
		this.stadiumLocation = stadiumLocation;
		this.address = address;
		this.homeTeamCode = homeTeamCode;
		this.homeTeamName = homeTeamName;
	}
	
	
}

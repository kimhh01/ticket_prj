package kr.admin.stadium;

public class StadiumListDTO {
	private int stadiumCode;
	private String stadiumName;
	private String stadiumLocation;
	private int totalSeats;
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
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
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
	public StadiumListDTO() {
		super();
	}
	public StadiumListDTO(int stadiumCode, String stadiumName, String stadiumLocation, int totalSeats, int homeTeamCode,
			String homeTeamName) {
		super();
		this.stadiumCode = stadiumCode;
		this.stadiumName = stadiumName;
		this.stadiumLocation = stadiumLocation;
		this.totalSeats = totalSeats;
		this.homeTeamCode = homeTeamCode;
		this.homeTeamName = homeTeamName;
	}
	
	
}

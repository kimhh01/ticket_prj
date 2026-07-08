package kr.admin.ticket;

import java.sql.Date;

public class TicketReservationViewDTO {

	private int reservationCode;
	private int scheduleCode;
	private String teamName;
	private String stadiumName;
	private String zoneName;
	private String seatNumber;
	private String userId;
	private Date reservationDate;
	private String reservationStatus;
	
	public TicketReservationViewDTO() {
	}//TicketReservationViewDTO
	
	public TicketReservationViewDTO(int reservationCode, int scheduleCode, String teamName, String stadiumName,
			String zoneName, String seatNumber, String userId, Date reservationDate, String reservationStatus) {
		this.reservationCode = reservationCode;
		this.scheduleCode = scheduleCode;
		this.teamName = teamName;
		this.stadiumName = stadiumName;
		this.zoneName = zoneName;
		this.seatNumber = seatNumber;
		this.userId = userId;
		this.reservationDate = reservationDate;
		this.reservationStatus = reservationStatus;
	}//TicketReservationViewDTO
	
	public int getReservationCode() {
		return reservationCode;
	}//getReservationCode
	
	public void setReservationCode(int reservationCode) {
		this.reservationCode = reservationCode;
	}//setReservationCode
	
	public int getScheduleCode() {
		return scheduleCode;
	}//getScheduleCode
	
	public void setScheduleCode(int scheduleCode) {
		this.scheduleCode = scheduleCode;
	}//setScheduleCode
	
	public String getTeamName() {
		return teamName;
	}//getTeamName
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}//setTeamName
	
	public String getStadiumName() {
		return stadiumName;
	}//getStadiumName
	
	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}//setStadiumName
	
	public String getZoneName() {
		return zoneName;
	}//getZoneName
	
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}//setZoneName
	
	public String getSeatNumber() {
		return seatNumber;
	}//getSeatNumber
	
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}//setSeatNumber
	
	public String getUserId() {
		return userId;
	}//getUserId
	
	public void setUserId(String userId) {
		this.userId = userId;
	}//setUserId
	
	public Date getReservationDate() {
		return reservationDate;
	}//getReservationDate
	
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}//setReservationDate
	
	public String getReservationStatus() {
		return reservationStatus;
	}//getReservationStatus
	
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}//setReservationStatus
	
}//class

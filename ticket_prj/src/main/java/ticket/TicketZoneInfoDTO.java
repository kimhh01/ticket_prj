package ticket;

public class TicketZoneInfoDTO {

	private int zoneCode;
	private int scheduleCode;
	private String zoneName;
	private int seatCount;
	private int remainSeatCount;
	
	public TicketZoneInfoDTO() {
	}//TicketZoneInfoDTO
	
	public TicketZoneInfoDTO(int zoneCode, int scheduleCode, String zoneName, int seatCount, int remainSeatCount) {
		this.zoneCode = zoneCode;
		this.scheduleCode = scheduleCode;
		this.zoneName = zoneName;
		this.seatCount = seatCount;
		this.remainSeatCount = remainSeatCount;
	}//TicketZoneInfoDTO
	
	public int getZoneCode() {
		return zoneCode;
	}//getZoneCode
	
	public void setZoneCode(int zoneCode) {
		this.zoneCode = zoneCode;
	}//setZoneCode
	
	public int getScheduleCode() {
		return scheduleCode;
	}//getScheduleCode
	
	public void setScheduleCode(int scheduleCode) {
		this.scheduleCode = scheduleCode;
	}//setScheduleCode
	
	public String getZoneName() {
		return zoneName;
	}//getZoneName
	
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}//setZoneName
	
	public int getSeatCount() {
		return seatCount;
	}//getSeatCount
	
	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}//setSeatCount
	
	public int getRemainSeatCount() {
		return remainSeatCount;
	}//getRemainSeatCount
	
	public void setRemainSeatCount(int remainSeatCount) {
		this.remainSeatCount = remainSeatCount;
	}//setRemainSeatCount
	
}//class

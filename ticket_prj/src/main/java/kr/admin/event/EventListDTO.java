package kr.admin.event;

public class EventListDTO {
	private int eventCode;
	private String eventTitle;
	private String thumbnailImg;
	private String startDate;
	private String endDate;
	private String eventSate;
	private String eventWriteDate;
	public int getEventCode() {
		return eventCode;
	}
	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getThumbnailImg() {
		return thumbnailImg;
	}
	public void setThumbnailImg(String thumbnailImg) {
		this.thumbnailImg = thumbnailImg;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEventSate() {
		return eventSate;
	}
	public void setEventSate(String eventSate) {
		this.eventSate = eventSate;
	}
	public String getEventWriteDate() {
		return eventWriteDate;
	}
	public void setEventWriteDate(String eventWriteDate) {
		this.eventWriteDate = eventWriteDate;
	}
	@Override
	public String toString() {
		return "EventListDTO [eventCode=" + eventCode + ", eventTitle=" + eventTitle + ", thumbnailImg=" + thumbnailImg
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", eventSate=" + eventSate + ", eventWriteDate="
				+ eventWriteDate + "]";
	}
	
	
}

package kr.admin.event;

public class EventDetailDTO {
	private int eventCode;
	private int adminId;
	private String eventTitle;
	private String eventSummary;
	private String eventContent;
	private String thumbnailImg;
	private String representativeImg;
	private String startDate;
	private String endDate;
	private String writeDate;

	public int getEventCode() {
		return eventCode;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getEventContent() {
		return eventContent;
	}
	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}
	public String getRepresentativeImg() {
		return representativeImg;
	}
	public void setRepresentativeImg(String representativeImg) {
		this.representativeImg = representativeImg;
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
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
	}
	public String getThumbnailImg() {
		return thumbnailImg;
	}
	public void setThumbnailImg(String thumbnailImg) {
		this.thumbnailImg = thumbnailImg;
	}
	public String getEventSummary() {
		return eventSummary;
	}
	public void setEventSummary(String eventSummary) {
		this.eventSummary = eventSummary;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	@Override
	public String toString() {
	    return "EventDetailDTO [eventCode=" + eventCode
	            + ", adminId=" + adminId
	            + ", eventTitle=" + eventTitle
	            + ", eventSummary=" + eventSummary
	            + ", eventContent=" + eventContent
	            + ", thumbnailImg=" + thumbnailImg
	            + ", representativeImg=" + representativeImg
	            + ", startDate=" + startDate
	            + ", endDate=" + endDate
	            + ", writeDate=" + writeDate
	            + "]";
	}
	
}

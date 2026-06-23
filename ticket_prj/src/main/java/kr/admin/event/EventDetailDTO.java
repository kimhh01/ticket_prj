package kr.admin.event;

public class EventDetailDTO {
	private int eventCode;
	private String eventContent;
	private String representativeImg;
	private boolean isDiscount;
	private int teamId;
	private int discountRate;
	public int getEventCode() {
		return eventCode;
	}
	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
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
	public boolean isDiscount() {
		return isDiscount;
	}
	public void setDiscount(boolean isDiscount) {
		this.isDiscount = isDiscount;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}
	@Override
	public String toString() {
		return "EventDetailDTO [eventCode=" + eventCode + ", eventContent=" + eventContent + ", representativeImg="
				+ representativeImg + ", isDiscount=" + isDiscount + ", teamId=" + teamId + ", discountRate="
				+ discountRate + "]";
	}


}

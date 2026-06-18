package ticket_site.dto;

import java.sql.Date;

public class EventDTO {

    private int eventCode;                 // 이벤트코드
    private String eventTitle;             // 이벤트제목

    private String benefitGuide;           // 혜택 안내
    private String useGuide;               // 이용방법
    private String guideline;              // 유의사항

    private String thumbnailImg;           // 이벤트 썸네일
    private String eventImg;               // 이벤트 이미지
    private String representativeImg;      // 본문 이미지

    private Date eventStartDate;           // 이벤트 시작일
    private Date eventEndDate;             // 이벤트 종료일

    private int discountPrice;             // 이벤트 할인율

    public EventDTO(int eventCode, String eventTitle, String benefitGuide, String useGuide, String guideline,
			String thumbnailImg, String eventImg, String representativeImg, Date eventStartDate, Date eventEndDate,
			int discountPrice) {
		super();
		this.eventCode = eventCode;
		this.eventTitle = eventTitle;
		this.benefitGuide = benefitGuide;
		this.useGuide = useGuide;
		this.guideline = guideline;
		this.thumbnailImg = thumbnailImg;
		this.eventImg = eventImg;
		this.representativeImg = representativeImg;
		this.eventStartDate = eventStartDate;
		this.eventEndDate = eventEndDate;
		this.discountPrice = discountPrice;
	}

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

	public String getBenefitGuide() {
		return benefitGuide;
	}

	public void setBenefitGuide(String benefitGuide) {
		this.benefitGuide = benefitGuide;
	}

	public String getUseGuide() {
		return useGuide;
	}

	public void setUseGuide(String useGuide) {
		this.useGuide = useGuide;
	}

	public String getGuideline() {
		return guideline;
	}

	public void setGuideline(String guideline) {
		this.guideline = guideline;
	}

	public String getThumbnailImg() {
		return thumbnailImg;
	}

	public void setThumbnailImg(String thumbnailImg) {
		this.thumbnailImg = thumbnailImg;
	}

	public String getEventImg() {
		return eventImg;
	}

	public void setEventImg(String eventImg) {
		this.eventImg = eventImg;
	}

	public String getRepresentativeImg() {
		return representativeImg;
	}

	public void setRepresentativeImg(String representativeImg) {
		this.representativeImg = representativeImg;
	}

	public Date getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(Date eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public Date getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(Date eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	@Override
	public String toString() {
		return "EventDTO [eventCode=" + eventCode + ", eventTitle=" + eventTitle + ", benefitGuide=" + benefitGuide
				+ ", useGuide=" + useGuide + ", guideline=" + guideline + ", thumbnailImg=" + thumbnailImg
				+ ", eventImg=" + eventImg + ", representativeImg=" + representativeImg + ", eventStartDate="
				+ eventStartDate + ", eventEndDate=" + eventEndDate + ", discountPrice=" + discountPrice + "]";
	}

	public EventDTO() {
    }

}
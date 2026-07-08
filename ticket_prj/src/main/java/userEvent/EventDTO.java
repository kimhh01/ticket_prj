package userEvent;

import java.sql.Date;

public class EventDTO {

    // EVENT 테이블
    private int eventId;
    private int adminId;

    private String eventTitle;
    private String eventSummary;
    private String eventContent;

    private String thumbnailImg;
    private String representativeImg;

    private Date eventStartDate;
    private Date eventEndDate;
    private Date eventWriteDate;

  

    public EventDTO() {
    }

    public EventDTO(int eventId, int adminId, String eventTitle,
            String eventSummary, String eventContent,
            String thumbnailImg, String representativeImg,
            Date eventStartDate, Date eventEndDate,
            Date eventWriteDate, int eventDiscountRate,
            int teamId) {

        this.eventId = eventId;
        this.adminId = adminId;
        this.eventTitle = eventTitle;
        this.eventSummary = eventSummary;
        this.eventContent = eventContent;
        this.thumbnailImg = thumbnailImg;
        this.representativeImg = representativeImg;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventWriteDate = eventWriteDate;
        
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventSummary() {
        return eventSummary;
    }

    public void setEventSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
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

    public Date getEventWriteDate() {
        return eventWriteDate;
    }

    public void setEventWriteDate(Date eventWriteDate) {
        this.eventWriteDate = eventWriteDate;
    }

    @Override
    public String toString() {
        return "EventDTO [eventId=" + eventId
                + ", adminId=" + adminId
                + ", eventTitle=" + eventTitle
                + ", eventSummary=" + eventSummary
                + ", eventContent=" + eventContent
                + ", thumbnailImg=" + thumbnailImg
                + ", representativeImg=" + representativeImg
                + ", eventStartDate=" + eventStartDate
                + ", eventEndDate=" + eventEndDate
                + ", eventWriteDate=" + eventWriteDate
               + "]";
    }

}
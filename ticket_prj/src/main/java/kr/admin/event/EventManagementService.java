package kr.admin.event;

import java.util.List;

import kr.admin.common.BoardRangeDTO;

public class EventManagementService {
	
    private EventManagementDAO dao;

    public EventManagementService() {
        dao = new EventManagementDAO();
    }

    /*
     * 전체 이벤트 수 조회
     */
    public int totalCount(BoardRangeDTO range) {

        return dao.selectTotalCount(range);
    }

    /*
     * 한 페이지에 보여줄 이벤트 수
     */
    public int pageScale() {

        return 5;
    }

    /*
     * 전체 페이지 수 계산
     */
    public int totalPage(int totalCount, int pageScale) {

        int totalPage = totalCount / pageScale;

        if (totalCount % pageScale != 0) {
            totalPage++;
        }

        if (totalPage == 0) {
            totalPage = 1;
        }

        return totalPage;
    }

    /*
     * 시작 번호 계산
     */
    public int startNum(int currentPage, int pageScale) {

        return (currentPage - 1) * pageScale + 1;
    }

    /*
     * 끝 번호 계산
     */
    public int endNum(int currentPage, int pageScale) {

        return currentPage * pageScale;
    }

    /*
     * 이벤트 목록 조회
     */
    public List<EventListDTO> getEventDashboardList(BoardRangeDTO range) {

        return dao.selectMatchDashboard(range);
    }

    /*
     * 이벤트 상세 조회
     */
    public EventDetailDTO getEventDetail(int eventCode) {

        return dao.selectEventDetail(eventCode);
    }

    /*
     * 이벤트 등록
     */
    public boolean registerEvent(EventDetailDTO event) {

        try {

            if (event == null) {
                return false;
            }
            
            if (event.getAdminId() <= 0) {
                return false;
            }

            if (event.getEventTitle() == null ||
                event.getEventTitle().trim().isEmpty()) {
                return false;
            }

            if (event.getEventSummary() == null ||
                event.getEventSummary().trim().isEmpty()) {
                return false;
            }

            if (event.getStartDate() == null ||
                event.getStartDate().trim().isEmpty()) {
                return false;
            }

            if (event.getEndDate() == null ||
                event.getEndDate().trim().isEmpty()) {
                return false;
            }

            if (event.getThumbnailImg() == null ||
                event.getThumbnailImg().trim().isEmpty()) {
                return false;
            }

            if (event.getRepresentativeImg() == null ||
                event.getRepresentativeImg().trim().isEmpty()) {
                return false;
            }

            if (event.getEventContent() == null ||
                event.getEventContent().trim().isEmpty()) {
                return false;
            }

            if (event.isDiscount()) {

                if (event.getDiscountRate() <= 0 ||
                    event.getDiscountRate() > 100) {
                    return false;
                }
            }

            dao.insertEvent(event);

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /*
     * 이벤트 수정
     */
    public boolean modifyEvent(EventDetailDTO event) {

        try {

            if (event == null) {
                return false;
            }

            if (event.getEventCode() <= 0) {
                return false;
            }

            if (event.getEventTitle() == null ||
                event.getEventTitle().trim().isEmpty()) {
                return false;
            }

            if (event.getEventSummary() == null ||
                event.getEventSummary().trim().isEmpty()) {
                return false;
            }

            if (event.getStartDate() == null ||
                event.getStartDate().trim().isEmpty()) {
                return false;
            }

            if (event.getEndDate() == null ||
                event.getEndDate().trim().isEmpty()) {
                return false;
            }

            if (event.getThumbnailImg() == null ||
                event.getThumbnailImg().trim().isEmpty()) {
                return false;
            }

            if (event.getRepresentativeImg() == null ||
                event.getRepresentativeImg().trim().isEmpty()) {
                return false;
            }

            if (event.getEventContent() == null ||
                event.getEventContent().trim().isEmpty()) {
                return false;
            }

            if (event.isDiscount()) {

                if (event.getDiscountRate() <= 0 ||
                    event.getDiscountRate() > 100) {
                    return false;
                }
            }

            int result = dao.updateEvent(event);

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /*
     * 이벤트 삭제
     */
    public boolean removeEvent(int eventCode) {

        if (eventCode <= 0) {
            return false;
        }

        int result = dao.deleteEvent(eventCode);

        return result > 0;
    }

}
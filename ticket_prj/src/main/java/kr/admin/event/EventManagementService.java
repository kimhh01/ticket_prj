package kr.admin.event;

import java.util.List;

import common.BoardRangeDTO;

public class EventManagementService {
	
    private EventManagementDAO dao;

    public EventManagementService() {
        dao = new EventManagementDAO();
    }
    
	public int totalCount(BoardRangeDTO range) {
		return dao.selectTotalCount(range);
	}
	
	public int pageScale() {
		return 5;
	}
	
	public int totalPage(int totalCount, int pageScale) {
        int totalPage = totalCount / pageScale;

        if(totalCount % pageScale != 0) {
            totalPage++;
        }

        return totalPage;
	}
	
	public int startNum (int currentPage, int pageScale) {
		return (currentPage - 1) * pageScale + 1;
	}
	
	public int endNum (int currentPage, int pageScale) {
		return currentPage * pageScale;
	}
	
	public List<EventListDTO> getEventDashboardList(BoardRangeDTO range){
		return dao.selectMatchDashboard(range);
	}
	
	public List<EventDetailDTO> getEventDetail(int eventCode){
		return null;
	}
	
	public boolean registerEvent(EventDetailDTO event) {
		return false;
	}
	
	public boolean modifyEvent(EventDetailDTO event) {
		return false;
	}
	
	public boolean removeEvent(int eventCode) {
		return false;
	}
}

package event;

import java.util.List;

import common.BoardRangeDTO;

public class EventManagementService {
	public int totalCount(BoardRangeDTO range) {
		return 0;
	}
	
	public int pageScale() {
		return 0;
	}
	
	public int totalPage(int totalCount, int pageScale) {
		return pageScale;
	}
	
	public int startNum (int currentPage, int pageScale) {
		return pageScale;
	}
	
	public int endNum (int currentPage, int pageScale) {
		return pageScale;
	}
	
	public List<EventListDTO> getEventDashboardList(BoardRangeDTO range){
		return null;
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

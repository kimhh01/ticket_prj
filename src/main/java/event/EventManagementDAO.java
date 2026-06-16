package event;

import java.util.List;

import common.BoardRangeDTO;

public class EventManagementDAO {
	public int selectTotalCount(BoardRangeDTO range) {
		return 0;
	}
	
	public List<EventListDTO> selectMatchDashboard(BoardRangeDTO range){
		return null;
	}
	
	public EventDetailDTO selectEventDetail(int eventCode){
		return null;	
	}
	
	public void insertEvent(EventDetailDTO event) {
		
	}
	
	public int updateEvent(EventDetailDTO event) {
		return 0;
	}
	
	public int deleteEvent(int eventCode) {
		return eventCode;
	}
}

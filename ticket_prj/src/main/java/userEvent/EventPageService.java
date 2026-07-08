package userEvent;

import java.sql.Date;

import java.util.List;

import userEvent.EventDTO;
import userEvent.EventPageDAO;

public class EventPageService {

    private EventPageDAO epDAO;

    public EventPageService() {
        epDAO = new EventPageDAO();
    }

    // 이벤트 메인화면 큰 이미지
    public List<EventDTO> searchEvent(Date date) {

        return epDAO.selectEvent(date);
    }

    // 이벤트 메인화면 하단부
    public List<EventDTO> searchEvent() {

        return epDAO.selectEvent();
    }

    // 각 이벤트 내용
    public EventDTO searchEventDetail(int eventCode) {

        return epDAO.selectEventDetail(eventCode);
    }

}
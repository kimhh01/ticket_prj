package ticket_site.dao;

import java.sql.Date;
import java.util.List;

import ticket_site.dto.EventDTO;

public class EventPageDAO {

    // 이벤트 메인화면 큰 이미지
    public List<EventDTO> selectEvent(Date date) {

        List<EventDTO> list = null;

        return list;
    }

    // 이벤트 리스트
    public List<EventDTO> selectEvent() {

        List<EventDTO> list = null;

        return list;
    }

    // 각 이벤트 상세내용
    public EventDTO selectEventDetail(int eventCode) {

        EventDTO eventDTO = null;

        return eventDTO;
    }

}
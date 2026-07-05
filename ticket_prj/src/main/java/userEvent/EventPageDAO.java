package userEvent;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.user.common.UserDBConnection;
import userEvent.EventDTO;

public class EventPageDAO {

    // 이벤트 메인화면 큰 이미지
    public List<EventDTO> selectEvent(Date date) {

        List<EventDTO> list = null;

        return list;
    }
 // 이벤트 리스트 조회
    public List<EventDTO> selectEvent() {

        List<EventDTO> list = new ArrayList<EventDTO>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT EVENT_ID, EVENT_TITLE, EVENT_SUMMARY, ");
            sql.append("THUMBNAIL_IMG, REPRESENTATIVE_IMG, ");
            sql.append("EVENT_START_DATE, EVENT_END_DATE ");
            sql.append("FROM EVENT ");
            sql.append("ORDER BY EVENT_ID");

            stmt = con.prepareStatement(sql.toString());

            rs = stmt.executeQuery();

            while (rs.next()) {

                EventDTO eventDTO = new EventDTO();

                eventDTO.setEventId(rs.getInt("EVENT_ID"));
                eventDTO.setEventTitle(rs.getString("EVENT_TITLE"));
                eventDTO.setEventSummary(rs.getString("EVENT_SUMMARY"));
                eventDTO.setThumbnailImg(rs.getString("THUMBNAIL_IMG"));
                eventDTO.setRepresentativeImg(rs.getString("REPRESENTATIVE_IMG"));
                eventDTO.setEventStartDate(rs.getDate("EVENT_START_DATE"));
                eventDTO.setEventEndDate(rs.getDate("EVENT_END_DATE"));
                

                list.add(eventDTO);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                db.close(rs, stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return list;
    }
    
 // 이벤트 상세 조회
    public EventDTO selectEventDetail(int eventId) {

        EventDTO eventDTO = null;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT E.EVENT_ID, E.ADMIN_ID, ");
            sql.append("E.EVENT_TITLE, E.EVENT_SUMMARY, E.EVENT_CONTENT, ");
            sql.append("E.THUMBNAIL_IMG, E.REPRESENTATIVE_IMG, ");
            sql.append("E.EVENT_START_DATE, E.EVENT_END_DATE, E.EVENT_WRITE_DATE, ");
            sql.append("NVL(D.EVENT_DISCOUNT_RATE, 0) EVENT_DISCOUNT_RATE, ");
            sql.append("NVL(D.TEAM_ID, 0) TEAM_ID ");
            sql.append("FROM EVENT E ");
            sql.append("LEFT JOIN EVENT_DISCOUNT D ");
            sql.append("ON E.EVENT_ID = D.EVENT_ID ");
            sql.append("WHERE E.EVENT_ID = ?");

            stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, eventId);

            rs = stmt.executeQuery();

            if (rs.next()) {

                eventDTO = new EventDTO();

                eventDTO.setEventId(rs.getInt("EVENT_ID"));
                eventDTO.setAdminId(rs.getInt("ADMIN_ID"));
                eventDTO.setEventTitle(rs.getString("EVENT_TITLE"));
                eventDTO.setEventSummary(rs.getString("EVENT_SUMMARY"));
                eventDTO.setEventContent(rs.getString("EVENT_CONTENT"));
                eventDTO.setThumbnailImg(rs.getString("THUMBNAIL_IMG"));
                eventDTO.setRepresentativeImg(rs.getString("REPRESENTATIVE_IMG"));
                eventDTO.setEventStartDate(rs.getDate("EVENT_START_DATE"));
                eventDTO.setEventEndDate(rs.getDate("EVENT_END_DATE"));
                eventDTO.setEventWriteDate(rs.getDate("EVENT_WRITE_DATE"));
                eventDTO.setEventDiscountRate(rs.getInt("EVENT_DISCOUNT_RATE"));
                eventDTO.setTeamId(rs.getInt("TEAM_ID"));

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                db.close(rs, stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return eventDTO;
    }

}
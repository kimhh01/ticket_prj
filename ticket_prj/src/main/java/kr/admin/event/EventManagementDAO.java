package kr.admin.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.admin.common.AdminDBConnection;
import kr.admin.common.BoardRangeDTO;

public class EventManagementDAO {

    public int selectTotalCount(BoardRangeDTO range) {

        int totalCount = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT COUNT(*) total_count ");
        sql.append("   FROM event ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString());

            ResultSet rs =
                    pstmt.executeQuery()
        ) {

            if (rs.next()) {
                totalCount = rs.getInt("total_count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalCount;
    }

    public List<EventListDTO> selectMatchDashboard(BoardRangeDTO range) {

        List<EventListDTO> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * ");
        sql.append("   FROM ( ");
        sql.append("         SELECT ROWNUM rnum, a.* ");
        sql.append("           FROM ( ");
        sql.append("                 SELECT e.event_id, ");
        sql.append("                        e.event_title, ");
        sql.append("                        e.thumbnail_img, ");
        sql.append("                        TO_CHAR(e.event_start_date, 'YYYY.MM.DD') AS start_date, ");
        sql.append("                        TO_CHAR(e.event_end_date, 'YYYY.MM.DD') AS end_date, ");
        sql.append("                        CASE ");
        sql.append("                            WHEN TRUNC(SYSDATE) < TRUNC(e.event_start_date) THEN '예정' ");
        sql.append("                            WHEN TRUNC(SYSDATE) > TRUNC(e.event_end_date) THEN '종료' ");
        sql.append("                            ELSE '진행중' ");
        sql.append("                        END AS event_state, ");
        sql.append("                        TO_CHAR(e.event_write_date, 'YYYY.MM.DD') AS event_write_date ");
        sql.append("                   FROM event e ");
        sql.append("                  ORDER BY e.event_id ASC ");
        sql.append("                ) a ");
        sql.append("          WHERE ROWNUM <= ? ");
        sql.append("       ) ");
        sql.append("  WHERE rnum >= ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, range.getEndNum());
            pstmt.setInt(2, range.getStartNum());

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    EventListDTO dto = new EventListDTO();

                    dto.setEventCode(rs.getInt("event_id"));
                    dto.setEventTitle(rs.getString("event_title"));
                    dto.setThumbnailImg(rs.getString("thumbnail_img"));
                    dto.setStartDate(rs.getString("start_date"));
                    dto.setEndDate(rs.getString("end_date"));
                    dto.setEventSate(rs.getString("event_state"));
                    dto.setEventWriteDate(rs.getString("event_write_date"));

                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public EventDetailDTO selectEventDetail(int eventCode) {

        EventDetailDTO dto = null;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT e.event_id, ");
        sql.append("        e.admin_id, ");
        sql.append("        e.event_title, ");
        sql.append("        e.event_summary, ");
        sql.append("        e.thumbnail_img, ");
        sql.append("        e.representative_img, ");
        sql.append("        TO_CHAR(e.event_start_date, 'YYYY-MM-DD') AS start_date, ");
        sql.append("        TO_CHAR(e.event_end_date, 'YYYY-MM-DD') AS end_date, ");
        sql.append("        TO_CHAR(e.event_write_date, 'YYYY-MM-DD') AS event_write_date, ");
        sql.append("        e.event_content ");
        sql.append("   FROM event e ");
        sql.append("  WHERE e.event_id = ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, eventCode);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    dto = new EventDetailDTO();

                    dto.setEventCode(rs.getInt("event_id"));
                    dto.setAdminId(rs.getInt("admin_id"));

                    dto.setEventTitle(rs.getString("event_title"));
                    dto.setEventSummary(rs.getString("event_summary"));

                    dto.setThumbnailImg(rs.getString("thumbnail_img"));
                    dto.setRepresentativeImg(rs.getString("representative_img"));

                    dto.setStartDate(rs.getString("start_date"));
                    dto.setEndDate(rs.getString("end_date"));
                    dto.setWriteDate(rs.getString("event_write_date"));

                    dto.setEventContent(rs.getString("event_content"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    public void insertEvent(EventDetailDTO event) {

        StringBuilder selectEventIdSql = new StringBuilder();

        selectEventIdSql.append(" SELECT NVL(MAX(event_id), 0) + 1 AS event_id ");
        selectEventIdSql.append("   FROM event ");

        StringBuilder eventSql = new StringBuilder();

        eventSql.append(" INSERT INTO event ( ");
        eventSql.append("        event_id, ");
        eventSql.append("        event_title, ");
        eventSql.append("        event_summary, ");
        eventSql.append("        thumbnail_img, ");
        eventSql.append("        representative_img, ");
        eventSql.append("        event_start_date, ");
        eventSql.append("        event_end_date, ");
        eventSql.append("        event_write_date, ");
        eventSql.append("        event_content, ");
        eventSql.append("        admin_id ");
        eventSql.append(" ) VALUES ( ");
        eventSql.append("        ?, ");
        eventSql.append("        ?, ");
        eventSql.append("        ?, ");
        eventSql.append("        ?, ");
        eventSql.append("        ?, ");
        eventSql.append("        TO_DATE(?, 'YYYY-MM-DD'), ");
        eventSql.append("        TO_DATE(?, 'YYYY-MM-DD'), ");
        eventSql.append("        SYSDATE, ");
        eventSql.append("        ?, ");
        eventSql.append("        ? ");
        eventSql.append(" ) ");

        Connection con = null;

        PreparedStatement selectEventIdPstmt = null;
        PreparedStatement eventPstmt = null;

        ResultSet rs = null;

        try {

            con = AdminDBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            int eventId = 0;

            selectEventIdPstmt =
                    con.prepareStatement(selectEventIdSql.toString());

            rs = selectEventIdPstmt.executeQuery();

            if (rs.next()) {
                eventId = rs.getInt("event_id");
            }

            rs.close();
            rs = null;

            selectEventIdPstmt.close();
            selectEventIdPstmt = null;

            eventPstmt =
                    con.prepareStatement(eventSql.toString());

            int index = 1;

            eventPstmt.setInt(index++, eventId);
            eventPstmt.setString(index++, event.getEventTitle());
            eventPstmt.setString(index++, event.getEventSummary());
            eventPstmt.setString(index++, event.getThumbnailImg());
            eventPstmt.setString(index++, event.getRepresentativeImg());
            eventPstmt.setString(index++, event.getStartDate());
            eventPstmt.setString(index++, event.getEndDate());
            eventPstmt.setString(index++, event.getEventContent());
            eventPstmt.setInt(index++, event.getAdminId());

            int eventResult =
                    eventPstmt.executeUpdate();

            if (eventResult <= 0) {
                con.rollback();
                throw new RuntimeException("이벤트 등록 실패");
            }

            con.commit();

        } catch (Exception e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();

            throw new RuntimeException("이벤트 등록 중 오류 발생", e);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (selectEventIdPstmt != null) {
                    selectEventIdPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (eventPstmt != null) {
                    eventPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int updateEvent(EventDetailDTO event) {

        int result = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE event ");
        sql.append("    SET event_title = ?, ");
        sql.append("        event_summary = ?, ");
        sql.append("        thumbnail_img = ?, ");
        sql.append("        representative_img = ?, ");
        sql.append("        event_start_date = TO_DATE(?, 'YYYY-MM-DD'), ");
        sql.append("        event_end_date = TO_DATE(?, 'YYYY-MM-DD'), ");
        sql.append("        event_content = ? ");
        sql.append("  WHERE event_id = ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            int index = 1;

            pstmt.setString(index++, event.getEventTitle());
            pstmt.setString(index++, event.getEventSummary());
            pstmt.setString(index++, event.getThumbnailImg());
            pstmt.setString(index++, event.getRepresentativeImg());
            pstmt.setString(index++, event.getStartDate());
            pstmt.setString(index++, event.getEndDate());
            pstmt.setString(index++, event.getEventContent());
            pstmt.setInt(index++, event.getEventCode());

            result =
                    pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int deleteEvent(int eventCode) {

        int result = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" DELETE FROM event ");
        sql.append("  WHERE event_id = ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, eventCode);

            result =
                    pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
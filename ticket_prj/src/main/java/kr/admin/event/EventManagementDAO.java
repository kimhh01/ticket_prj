package kr.admin.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import common.BoardRangeDTO;

public class EventManagementDAO {
    private DataSource ds;

    public EventManagementDAO() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/dbcp");
            System.out.println("DataSource = " + ds);
        } catch (Exception e) {
            throw new RuntimeException("JNDI 조회 실패", e);
        }
    }

    public int selectTotalCount(BoardRangeDTO range) {

        int totalCount = 0;

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) total_count ");
        sql.append("   FROM event ");

        try (
                Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql.toString());
                ResultSet rs = pstmt.executeQuery()
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
                Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
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
	
	public void insertEvent(EventDetailDTO event) {
		
	}
	
	public int updateEvent(EventDetailDTO event) {
		return 0;
	}
	
	public int deleteEvent(int eventCode) {
		return eventCode;
	}
}

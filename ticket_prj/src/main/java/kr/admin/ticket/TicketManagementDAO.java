package kr.admin.ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.admin.common.AdminDBConnection;
import kr.admin.common.BoardRangeDTO;

public class TicketManagementDAO {

    private Connection getConn() throws SQLException {
        return AdminDBConnection.getInstance().getConnection();
    }

    public int selectTotalCount() {

        int totalCount = 0;

        String sql =
                " SELECT COUNT(*) total_count "
              + "   FROM GAME_SCHEDULE ";

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            if (rs.next()) {
                totalCount = rs.getInt("total_count");
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return totalCount;
    }

    public List<TicketMatchListDTO> selectMatchDashboard(BoardRangeDTO range) {

        List<TicketMatchListDTO> list = new ArrayList<TicketMatchListDTO>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * ");
        sql.append("   FROM ( ");
        sql.append("         SELECT A.*, ROWNUM rnum ");
        sql.append("           FROM ( ");
        sql.append("                 SELECT gs.GAME_SCHEDULE_ID schedule_code, ");
        sql.append("                        ht.TEAM_NAME home_team, ");
        sql.append("                        at.TEAM_NAME away_team, ");
        sql.append("                        TO_CHAR(gs.GAME_DATE, 'YYYY-MM-DD') game_date, ");
        sql.append("                        gs.GAME_START_TIME game_start_time, ");
        sql.append("                        s.STADIUM_NAME stadium_name, ");
        sql.append("                        gs.SALE_STATE sale_state ");
        sql.append("                   FROM GAME_SCHEDULE gs ");
        sql.append("                   JOIN TEAM ht ");
        sql.append("                     ON gs.TEAM_HOME = ht.TEAM_ID ");
        sql.append("                   JOIN TEAM at ");
        sql.append("                     ON gs.TEAM_OTHER = at.TEAM_ID ");
        sql.append("                   JOIN STADIUM s ");
        sql.append("                     ON gs.STADIUM_ID = s.STADIUM_ID ");
        sql.append("                  ORDER BY gs.GAME_DATE DESC, gs.GAME_START_TIME DESC ");
        sql.append("           ) A ");
        sql.append("          WHERE ROWNUM <= ? ");
        sql.append("   ) ");
        sql.append("  WHERE rnum >= ? ");

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, range.getEndNum());
            pstmt.setInt(2, range.getStartNum());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(setMatchDTO(rs));
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return list;
    }

    public TicketMatchListDTO selectMatchDetail(int scheduleCode) {

        TicketMatchListDTO dto = null;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT gs.GAME_SCHEDULE_ID schedule_code, ");
        sql.append("        ht.TEAM_NAME home_team, ");
        sql.append("        at.TEAM_NAME away_team, ");
        sql.append("        TO_CHAR(gs.GAME_DATE, 'YYYY-MM-DD') game_date, ");
        sql.append("        gs.GAME_START_TIME game_start_time, ");
        sql.append("        s.STADIUM_NAME stadium_name, ");
        sql.append("        gs.SALE_STATE sale_state ");
        sql.append("   FROM GAME_SCHEDULE gs ");
        sql.append("   JOIN TEAM ht ");
        sql.append("     ON gs.TEAM_HOME = ht.TEAM_ID ");
        sql.append("   JOIN TEAM at ");
        sql.append("     ON gs.TEAM_OTHER = at.TEAM_ID ");
        sql.append("   JOIN STADIUM s ");
        sql.append("     ON gs.STADIUM_ID = s.STADIUM_ID ");
        sql.append("  WHERE gs.GAME_SCHEDULE_ID = ? ");

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, scheduleCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = setMatchDTO(rs);
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return dto;
    }
    
    public TicketSalesDTO selectTicketSales(int scheduleCode) {

        TicketSalesDTO dto = new TicketSalesDTO();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT NVL(SUM(CASE WHEN r.RESERVATION_STATUS IN ('구매', '취소') ");
        sql.append("                     THEN rd.RESERVATION_QUANTITY ELSE 0 END), 0) total_booked_cnt, ");
        sql.append("        NVL(SUM(CASE WHEN r.RESERVATION_STATUS = '구매' ");
        sql.append("                     THEN rd.RESERVATION_QUANTITY ELSE 0 END), 0) general_booked_cnt, ");
        sql.append("        NVL(SUM(CASE WHEN r.RESERVATION_STATUS = '취소' ");
        sql.append("                     THEN rd.RESERVATION_QUANTITY ELSE 0 END), 0) cancel_booked_cnt, ");
        sql.append("        NVL(SUM(CASE WHEN r.RESERVATION_STATUS = '구매' ");
        sql.append("                      AND r.PAY_STATE = '구매' ");
        sql.append("                     THEN r.PAY_PRICE ELSE 0 END), 0) total_price ");
        sql.append("   FROM RESERVATION r ");
        sql.append("   JOIN RESERVATION_DETAIL rd ");
        sql.append("     ON r.RESERVATION_ID = rd.RESERVATION_ID ");
        sql.append("  WHERE r.GAME_SCHEDULE_ID = ? ");

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, scheduleCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int totalBookedCnt = rs.getInt("total_booked_cnt");
                    int generalBookedCnt = rs.getInt("general_booked_cnt");

                    dto.setTotalBookedCnt(totalBookedCnt);
                    dto.setGeneralBookedCnt(generalBookedCnt);
                    dto.setCancelBookedCnt(rs.getInt("cancel_booked_cnt"));
                    dto.setTotalPrice(rs.getInt("total_price"));

                    dto.setBookedCnt(generalBookedCnt);
                    dto.setAreaTotalBookedCnt(totalBookedCnt);
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return dto;
    }

    public List<TicketZoneInfoDTO> selectZoneInfo(int scheduleCode) {

        List<TicketZoneInfoDTO> list = new ArrayList<TicketZoneInfoDTO>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ss.STADIUM_SEAT_ID zone_code, ");
        sql.append("        gs.GAME_SCHEDULE_ID schedule_code, ");
        sql.append("        ss.SEAT_NAME zone_name, ");
        sql.append("        ss.STADIUM_SEAT_COUNT seat_count, ");
        sql.append("        ss.STADIUM_SEAT_COUNT ");
        sql.append("        - NVL(SUM(CASE WHEN r.RESERVATION_STATUS = '구매' ");
        sql.append("                       THEN rd.RESERVATION_QUANTITY ELSE 0 END), 0) remain_seat_count ");
        sql.append("   FROM GAME_SCHEDULE gs ");
        sql.append("   JOIN STADIUM_SEAT ss ");
        sql.append("     ON gs.STADIUM_ID = ss.STADIUM_ID ");
        sql.append("   LEFT JOIN RESERVATION r ");
        sql.append("     ON gs.GAME_SCHEDULE_ID = r.GAME_SCHEDULE_ID ");
        sql.append("   LEFT JOIN RESERVATION_DETAIL rd ");
        sql.append("     ON r.RESERVATION_ID = rd.RESERVATION_ID ");
        sql.append("    AND ss.STADIUM_SEAT_ID = rd.STADIUM_SEAT_ID ");
        sql.append("  WHERE gs.GAME_SCHEDULE_ID = ? ");
        sql.append("  GROUP BY ss.STADIUM_SEAT_ID, ");
        sql.append("           gs.GAME_SCHEDULE_ID, ");
        sql.append("           ss.SEAT_NAME, ");
        sql.append("           ss.STADIUM_SEAT_COUNT ");
        sql.append("  ORDER BY ss.STADIUM_SEAT_ID ");

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, scheduleCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(setZoneDTO(rs));
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return list;
    }

    public TicketZoneInfoDTO selectZoneDetail(int scheduleCode, int zoneCode) {

        TicketZoneInfoDTO dto = null;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ss.STADIUM_SEAT_ID zone_code, ");
        sql.append("        gs.GAME_SCHEDULE_ID schedule_code, ");
        sql.append("        ss.SEAT_NAME zone_name, ");
        sql.append("        ss.STADIUM_SEAT_COUNT seat_count, ");
        sql.append("        ss.STADIUM_SEAT_COUNT ");
        sql.append("        - NVL(SUM(CASE WHEN r.RESERVATION_STATUS = '구매' ");
        sql.append("                       THEN rd.RESERVATION_QUANTITY ELSE 0 END), 0) remain_seat_count ");
        sql.append("   FROM GAME_SCHEDULE gs ");
        sql.append("   JOIN STADIUM_SEAT ss ");
        sql.append("     ON gs.STADIUM_ID = ss.STADIUM_ID ");
        sql.append("   LEFT JOIN RESERVATION r ");
        sql.append("     ON gs.GAME_SCHEDULE_ID = r.GAME_SCHEDULE_ID ");
        sql.append("   LEFT JOIN RESERVATION_DETAIL rd ");
        sql.append("     ON r.RESERVATION_ID = rd.RESERVATION_ID ");
        sql.append("    AND ss.STADIUM_SEAT_ID = rd.STADIUM_SEAT_ID ");
        sql.append("  WHERE gs.GAME_SCHEDULE_ID = ? ");
        sql.append("    AND ss.STADIUM_SEAT_ID = ? ");
        sql.append("  GROUP BY ss.STADIUM_SEAT_ID, ");
        sql.append("           gs.GAME_SCHEDULE_ID, ");
        sql.append("           ss.SEAT_NAME, ");
        sql.append("           ss.STADIUM_SEAT_COUNT ");

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, scheduleCode);
            pstmt.setInt(2, zoneCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = setZoneDTO(rs);
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return dto;
    }
    
    public List<TicketInfoDTO> selectTicketInfoList(int scheduleCode) {

        List<TicketInfoDTO> list = new ArrayList<TicketInfoDTO>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT seat_code, ");
        sql.append("        seat_name, ");
        sql.append("        ticket_type, ");
        sql.append("        price, ");
        sql.append("        CASE ");
        sql.append("             WHEN base_sale_state IN ('종료', '판매종료') THEN '종료' ");
        sql.append("             WHEN remain_seat_count <= 0 THEN '매진' ");
        sql.append("             WHEN remain_seat_count <= 10 THEN '매진임박' ");
        sql.append("             ELSE base_sale_state ");
        sql.append("        END sale_state ");
        sql.append("   FROM ( ");
        sql.append("         SELECT ss.STADIUM_SEAT_ID seat_code, ");
        sql.append("                ss.SEAT_NAME seat_name, ");
        sql.append("                '일반' ticket_type, ");
        sql.append("                ss.ADULT_SEAT_PRICE price, ");
        sql.append("                ss.STADIUM_SEAT_COUNT ");
        sql.append("                - NVL(SUM(CASE WHEN r.RESERVATION_STATUS = '구매' ");
        sql.append("                               THEN rd.RESERVATION_QUANTITY ELSE 0 END), 0) remain_seat_count, ");
        sql.append("                gs.SALE_STATE base_sale_state ");
        sql.append("           FROM GAME_SCHEDULE gs ");
        sql.append("           JOIN STADIUM_SEAT ss ");
        sql.append("             ON gs.STADIUM_ID = ss.STADIUM_ID ");
        sql.append("           LEFT JOIN RESERVATION r ");
        sql.append("             ON gs.GAME_SCHEDULE_ID = r.GAME_SCHEDULE_ID ");
        sql.append("           LEFT JOIN RESERVATION_DETAIL rd ");
        sql.append("             ON r.RESERVATION_ID = rd.RESERVATION_ID ");
        sql.append("            AND ss.STADIUM_SEAT_ID = rd.STADIUM_SEAT_ID ");
        sql.append("          WHERE gs.GAME_SCHEDULE_ID = ? ");
        sql.append("          GROUP BY ss.STADIUM_SEAT_ID, ");
        sql.append("                   ss.SEAT_NAME, ");
        sql.append("                   ss.ADULT_SEAT_PRICE, ");
        sql.append("                   ss.STADIUM_SEAT_COUNT, ");
        sql.append("                   gs.SALE_STATE ");
        sql.append("   ) ");
        sql.append("  ORDER BY seat_code ");

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, scheduleCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(setTicketInfoDTO(rs));
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return list;
    }
    
    public TicketInfoDTO selectTicketInfo(int scheduleCode, int seatCode) {

        TicketInfoDTO dto = null;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT seat_code, ");
        sql.append("        seat_name, ");
        sql.append("        ticket_type, ");
        sql.append("        price, ");
        sql.append("        CASE ");
        sql.append("             WHEN base_sale_state IN ('종료', '판매종료') THEN '종료' ");
        sql.append("             WHEN remain_seat_count <= 0 THEN '매진' ");
        sql.append("             WHEN remain_seat_count <= 10 THEN '매진임박' ");
        sql.append("             ELSE base_sale_state ");
        sql.append("        END sale_state ");
        sql.append("   FROM ( ");
        sql.append("         SELECT ss.STADIUM_SEAT_ID seat_code, ");
        sql.append("                ss.SEAT_NAME seat_name, ");
        sql.append("                '일반' ticket_type, ");
        sql.append("                ss.ADULT_SEAT_PRICE price, ");
        sql.append("                ss.STADIUM_SEAT_COUNT ");
        sql.append("                - NVL(SUM(CASE WHEN r.RESERVATION_STATUS = '구매' ");
        sql.append("                               THEN rd.RESERVATION_QUANTITY ELSE 0 END), 0) remain_seat_count, ");
        sql.append("                gs.SALE_STATE base_sale_state ");
        sql.append("           FROM GAME_SCHEDULE gs ");
        sql.append("           JOIN STADIUM_SEAT ss ");
        sql.append("             ON gs.STADIUM_ID = ss.STADIUM_ID ");
        sql.append("           LEFT JOIN RESERVATION r ");
        sql.append("             ON gs.GAME_SCHEDULE_ID = r.GAME_SCHEDULE_ID ");
        sql.append("           LEFT JOIN RESERVATION_DETAIL rd ");
        sql.append("             ON r.RESERVATION_ID = rd.RESERVATION_ID ");
        sql.append("            AND ss.STADIUM_SEAT_ID = rd.STADIUM_SEAT_ID ");
        sql.append("          WHERE gs.GAME_SCHEDULE_ID = ? ");
        sql.append("            AND ss.STADIUM_SEAT_ID = ? ");
        sql.append("          GROUP BY ss.STADIUM_SEAT_ID, ");
        sql.append("                   ss.SEAT_NAME, ");
        sql.append("                   ss.ADULT_SEAT_PRICE, ");
        sql.append("                   ss.STADIUM_SEAT_COUNT, ");
        sql.append("                   gs.SALE_STATE ");
        sql.append("   ) ");

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, scheduleCode);
            pstmt.setInt(2, seatCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = setTicketInfoDTO(rs);
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return dto;
    }
    
    public int selectReservationTotalCount(int scheduleCode, int zoneCode) {

        int totalCount = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT COUNT(*) total_count ");
        sql.append("   FROM ( ");
        sql.append("         SELECT r.RESERVATION_ID ");
        sql.append("           FROM RESERVATION r ");
        sql.append("           JOIN RESERVATION_DETAIL rd ");
        sql.append("             ON r.RESERVATION_ID = rd.RESERVATION_ID ");
        sql.append("          WHERE r.GAME_SCHEDULE_ID = ? ");
        sql.append("            AND rd.STADIUM_SEAT_ID = ? ");
        sql.append("          GROUP BY r.RESERVATION_ID ");
        sql.append("   ) ");

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, scheduleCode);
            pstmt.setInt(2, zoneCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalCount = rs.getInt("total_count");
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return totalCount;
    }

	public List<TicketReservationListDTO> selectReservationList(int scheduleCode, int zoneCode, BoardRangeDTO range) {

        List<TicketReservationListDTO> list =
                new ArrayList<TicketReservationListDTO>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * ");
        sql.append("   FROM ( ");
        sql.append("         SELECT A.*, ROWNUM rnum ");
        sql.append("           FROM ( ");
        sql.append("                 SELECT r.RESERVATION_ID reservation_code, ");
        sql.append("                        m.NAME member_name, ");
        sql.append("                        m.PHONE member_tel, ");
        sql.append("                        SUM(rd.RESERVATION_QUANTITY) reservation_cnt, ");
        sql.append("                        r.PAY_PRICE payment_price, ");
        sql.append("                        TO_CHAR(r.RESERVATION_DATE, 'YYYY-MM-DD HH24:MI') reservation_date, ");
        sql.append("                        r.RESERVATION_STATUS reservation_state ");
        sql.append("                   FROM RESERVATION r ");
        sql.append("                   JOIN MEMBER m ");
        sql.append("                     ON r.MEMBER_ID = m.MEMBER_ID ");
        sql.append("                   JOIN RESERVATION_DETAIL rd ");
        sql.append("                     ON r.RESERVATION_ID = rd.RESERVATION_ID ");
        sql.append("                  WHERE r.GAME_SCHEDULE_ID = ? ");
        sql.append("                    AND rd.STADIUM_SEAT_ID = ? ");
        sql.append("                  GROUP BY r.RESERVATION_ID, ");
        sql.append("                           m.NAME, ");
        sql.append("                           m.PHONE, ");
        sql.append("                           r.PAY_PRICE, ");
        sql.append("                           r.RESERVATION_DATE, ");
        sql.append("                           r.RESERVATION_STATUS ");
        sql.append("                  ORDER BY r.RESERVATION_DATE DESC, r.RESERVATION_ID DESC ");
        sql.append("           ) A ");
        sql.append("          WHERE ROWNUM <= ? ");
        sql.append("   ) ");
        sql.append("  WHERE rnum >= ? ");

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, scheduleCode);
            pstmt.setInt(2, zoneCode);
            pstmt.setInt(3, range.getEndNum());
            pstmt.setInt(4, range.getStartNum());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TicketReservationListDTO dto = new TicketReservationListDTO();

                    dto.setReservationCode(rs.getInt("reservation_code"));
                    dto.setMemberName(rs.getString("member_name"));
                    dto.setMemberTel(rs.getString("member_tel"));
                    dto.setReservationCnt(rs.getInt("reservation_cnt"));
                    dto.setPaymentPrice(rs.getInt("payment_price"));
                    dto.setReservationDate(rs.getString("reservation_date"));
                    dto.setReservationState(rs.getString("reservation_state"));

                    list.add(dto);
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return list;
    }

    public int selectSearchTotalCount(
            String startDate,
            String endDate,
            String team,
            String stadium,
            String phone) {

        int totalCount = 0;

        StringBuilder sql = new StringBuilder();
        List<Object> paramList = new ArrayList<Object>();

        sql.append(" SELECT COUNT(*) total_count ");
        sql.append("   FROM ( ");
        sql.append("         SELECT r.RESERVATION_ID ");
        sql.append("           FROM RESERVATION r ");
        sql.append("           JOIN RESERVATION_DETAIL rd ");
        sql.append("             ON r.RESERVATION_ID = rd.RESERVATION_ID ");
        sql.append("           JOIN MEMBER m ");
        sql.append("             ON r.MEMBER_ID = m.MEMBER_ID ");
        sql.append("           JOIN GAME_SCHEDULE gs ");
        sql.append("             ON r.GAME_SCHEDULE_ID = gs.GAME_SCHEDULE_ID ");
        sql.append("           JOIN TEAM ht ");
        sql.append("             ON gs.TEAM_HOME = ht.TEAM_ID ");
        sql.append("           JOIN TEAM at ");
        sql.append("             ON gs.TEAM_OTHER = at.TEAM_ID ");
        sql.append("           JOIN STADIUM s ");
        sql.append("             ON gs.STADIUM_ID = s.STADIUM_ID ");
        sql.append("           JOIN STADIUM_SEAT ss ");
        sql.append("             ON rd.STADIUM_SEAT_ID = ss.STADIUM_SEAT_ID ");
        sql.append("          WHERE 1 = 1 ");

        appendSearchCondition(sql, paramList, startDate, endDate, team, stadium, phone);

        sql.append("          GROUP BY r.RESERVATION_ID ");
        sql.append("   ) ");

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            bindParams(pstmt, paramList);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalCount = rs.getInt("total_count");
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return totalCount;
    }

    public List<TicketSearchDTO> selectSearchReservations(
            String startDate,
            String endDate,
            String team,
            String stadium,
            String phone,
            BoardRangeDTO range) {

        List<TicketSearchDTO> list = new ArrayList<TicketSearchDTO>();

        StringBuilder sql = new StringBuilder();
        List<Object> paramList = new ArrayList<Object>();

        sql.append(" SELECT * ");
        sql.append("   FROM ( ");
        sql.append("         SELECT A.*, ROWNUM rnum ");
        sql.append("           FROM ( ");
        sql.append("                 SELECT r.RESERVATION_ID reservation_code, ");
        sql.append("                        m.NAME member_name, ");
        sql.append("                        m.PHONE member_tel, ");
        sql.append("                        ht.TEAM_NAME || ' vs ' || at.TEAM_NAME ");
        sql.append("                        || ' / ' || TO_CHAR(gs.GAME_DATE, 'YYYY-MM-DD') ");
        sql.append("                        || ' ' || gs.GAME_START_TIME ticket_name, ");
        sql.append("                        MIN(ss.SEAT_NAME) seat_name, ");
        sql.append("                        SUM(rd.RESERVATION_QUANTITY) reservation_cnt, ");
        sql.append("                        r.PAY_PRICE payment_price, ");
        sql.append("                        TO_CHAR(r.RESERVATION_DATE, 'YYYY-MM-DD HH24:MI') reservation_date, ");
        sql.append("                        r.RESERVATION_STATUS reservation_state ");
        sql.append("                   FROM RESERVATION r ");
        sql.append("                   JOIN RESERVATION_DETAIL rd ");
        sql.append("                     ON r.RESERVATION_ID = rd.RESERVATION_ID ");
        sql.append("                   JOIN MEMBER m ");
        sql.append("                     ON r.MEMBER_ID = m.MEMBER_ID ");
        sql.append("                   JOIN GAME_SCHEDULE gs ");
        sql.append("                     ON r.GAME_SCHEDULE_ID = gs.GAME_SCHEDULE_ID ");
        sql.append("                   JOIN TEAM ht ");
        sql.append("                     ON gs.TEAM_HOME = ht.TEAM_ID ");
        sql.append("                   JOIN TEAM at ");
        sql.append("                     ON gs.TEAM_OTHER = at.TEAM_ID ");
        sql.append("                   JOIN STADIUM s ");
        sql.append("                     ON gs.STADIUM_ID = s.STADIUM_ID ");
        sql.append("                   JOIN STADIUM_SEAT ss ");
        sql.append("                     ON rd.STADIUM_SEAT_ID = ss.STADIUM_SEAT_ID ");
        sql.append("                  WHERE 1 = 1 ");

        appendSearchCondition(sql, paramList, startDate, endDate, team, stadium, phone);

        sql.append("                  GROUP BY r.RESERVATION_ID, ");
        sql.append("                           m.NAME, ");
        sql.append("                           m.PHONE, ");
        sql.append("                           ht.TEAM_NAME, ");
        sql.append("                           at.TEAM_NAME, ");
        sql.append("                           gs.GAME_DATE, ");
        sql.append("                           gs.GAME_START_TIME, ");
        sql.append("                           r.PAY_PRICE, ");
        sql.append("                           r.RESERVATION_DATE, ");
        sql.append("                           r.RESERVATION_STATUS ");
        sql.append("                  ORDER BY r.RESERVATION_DATE DESC, r.RESERVATION_ID DESC ");
        sql.append("           ) A ");
        sql.append("          WHERE ROWNUM <= ? ");
        sql.append("   ) ");
        sql.append("  WHERE rnum >= ? ");

        paramList.add(range.getEndNum());
        paramList.add(range.getStartNum());

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            bindParams(pstmt, paramList);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TicketSearchDTO dto = new TicketSearchDTO();

                    dto.setReservationCode(rs.getInt("reservation_code"));
                    dto.setMemberName(rs.getString("member_name"));
                    dto.setMemberTel(rs.getString("member_tel"));
                    dto.setTicketName(rs.getString("ticket_name"));
                    dto.setSeatName(rs.getString("seat_name"));
                    dto.setReservationCnt(rs.getInt("reservation_cnt"));
                    dto.setPaymentPrice(rs.getInt("payment_price"));
                    dto.setReservationDate(rs.getString("reservation_date"));
                    dto.setReservationState(rs.getString("reservation_state"));

                    list.add(dto);
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return list;
    }

    public List<String> selectTeamNameList() {

        List<String> list = new ArrayList<String>();

        String sql =
                " SELECT TEAM_NAME "
              + "   FROM TEAM "
              + "  ORDER BY TEAM_ID ";

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {
                list.add(rs.getString("TEAM_NAME"));
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return list;
    }

    public List<String> selectStadiumNameList() {

        List<String> list = new ArrayList<String>();

        String sql =
                " SELECT STADIUM_NAME "
              + "   FROM STADIUM "
              + "  ORDER BY STADIUM_ID ";

        try (
                Connection con = getConn();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {
                list.add(rs.getString("STADIUM_NAME"));
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return list;
    }

    public int cancelReservation(int reservationCode) {

        int result = 0;

        Connection con = null;
        PreparedStatement pstmt = null;
        PreparedStatement insertPstmt = null;

        try {
            con = getConn();
            con.setAutoCommit(false);

            String updateSql =
                    " UPDATE RESERVATION "
                  + "    SET RESERVATION_STATUS = '취소', "
                  + "        PAY_STATE = '취소', "
                  + "        CANCEL_PRICE = NVL(PAY_PRICE, 0) "
                  + "  WHERE RESERVATION_ID = ? "
                  + "    AND NVL(RESERVATION_STATUS, ' ') <> '취소' ";

            pstmt = con.prepareStatement(updateSql);
            pstmt.setInt(1, reservationCode);

            result = pstmt.executeUpdate();

            System.out.println("취소 UPDATE 결과 = " + result);
            System.out.println("취소 reservationCode = " + reservationCode);

            if (result > 0) {

                String insertSql =
                        " INSERT INTO RESERVATION_CENCEL "
                      + "        (CANCEL_ID, RESERVATION_ID, CANCEL_TYPE, CANCEL_DATE) "
                      + " VALUES ( "
                      + "        (SELECT NVL(MAX(CANCEL_ID), 0) + 1 FROM RESERVATION_CENCEL), "
                      + "        ?, "
                      + "        '관리자취소', "
                      + "        SYSDATE "
                      + " ) ";

                insertPstmt = con.prepareStatement(insertSql);
                insertPstmt.setInt(1, reservationCode);

                insertPstmt.executeUpdate();
            }

            con.commit();

        } catch (SQLException se) {
            se.printStackTrace();

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }

            result = 0;

        } finally {
            close(insertPstmt);
            close(pstmt);

            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }

        return result;
    }
    
    private TicketMatchListDTO setMatchDTO(ResultSet rs) throws SQLException {

        TicketMatchListDTO dto = new TicketMatchListDTO();

        dto.setScheduleCode(rs.getInt("schedule_code"));
        dto.setHomeTeam(rs.getString("home_team"));
        dto.setAwayTeam(rs.getString("away_team"));
        dto.setGameDate(rs.getString("game_date"));
        dto.setGameStartTime(rs.getString("game_start_time"));
        dto.setStadiumName(rs.getString("stadium_name"));
        dto.setSaleState(rs.getString("sale_state"));

        return dto;
    }

    private TicketZoneInfoDTO setZoneDTO(ResultSet rs) throws SQLException {

        TicketZoneInfoDTO dto = new TicketZoneInfoDTO();

        dto.setZoneCode(rs.getInt("zone_code"));
        dto.setScheduleCode(rs.getInt("schedule_code"));
        dto.setZoneName(rs.getString("zone_name"));
        dto.setSeatCount(rs.getInt("seat_count"));
        dto.setRemainSeatCount(rs.getInt("remain_seat_count"));

        return dto;
    }
    
    private TicketInfoDTO setTicketInfoDTO(ResultSet rs) throws SQLException {

        TicketInfoDTO dto = new TicketInfoDTO();

        dto.setSeatCode(rs.getInt("seat_code"));
        dto.setSeatName(rs.getString("seat_name"));
        dto.setTicketType(rs.getString("ticket_type"));
        dto.setPrice(rs.getInt("price"));
        dto.setSaleState(rs.getString("sale_state"));

        return dto;
    }

    private void appendSearchCondition(
            StringBuilder sql,
            List<Object> paramList,
            String startDate,
            String endDate,
            String team,
            String stadium,
            String phone) {

        if (isNotEmpty(startDate)) {
            sql.append(" AND TRUNC(gs.GAME_DATE) >= TO_DATE(?, 'YYYY-MM-DD') ");
            paramList.add(startDate);
        }

        if (isNotEmpty(endDate)) {
            sql.append(" AND TRUNC(gs.GAME_DATE) <= TO_DATE(?, 'YYYY-MM-DD') ");
            paramList.add(endDate);
        }

        if (isNotEmpty(team) && !"전체".equals(team)) {
            sql.append(" AND (ht.TEAM_NAME LIKE ? OR at.TEAM_NAME LIKE ?) ");
            paramList.add("%" + team + "%");
            paramList.add("%" + team + "%");
        }

        if (isNotEmpty(stadium) && !"전체".equals(stadium)) {
            sql.append(" AND s.STADIUM_NAME LIKE ? ");
            paramList.add("%" + stadium + "%");
        }

        if (isNotEmpty(phone)) {
            sql.append(" AND (m.PHONE LIKE ? OR REPLACE(m.PHONE, '-', '') LIKE ?) ");
            paramList.add("%" + phone + "%");
            paramList.add("%" + phone.replace("-", "") + "%");
        }
    }

    private void bindParams(PreparedStatement pstmt, List<Object> paramList)
            throws SQLException {

        for (int i = 0; i < paramList.size(); i++) {
            Object value = paramList.get(i);

            if (value instanceof Integer) {
                pstmt.setInt(i + 1, ((Integer)value).intValue());
            } else {
                pstmt.setString(i + 1, String.valueOf(value));
            }
        }
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private void close(PreparedStatement pstmt) {

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}
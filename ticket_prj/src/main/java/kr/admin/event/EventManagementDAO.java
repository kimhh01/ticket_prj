package kr.admin.event;

import java.security.SecureRandom;
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
        sql.append("        e.event_content, ");
        sql.append("        ed.discount_rule_code, ");
        sql.append("        ed.event_discount_rate ");
        sql.append("   FROM event e ");
        sql.append("   LEFT JOIN event_discount ed ");
        sql.append("     ON e.event_id = ed.event_id ");
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

                    int discountRuleCode =
                            rs.getInt("discount_rule_code");

                    if (!rs.wasNull()) {

                        dto.setDiscount(true);

                        /*
                         * DISCOUNT_RULE_CODE를 쿠폰 번호로 사용
                         */
                        dto.setDiscountRuleCode(discountRuleCode);

                        dto.setDiscountRate(
                                rs.getInt("event_discount_rate"));

                    } else {

                        dto.setDiscount(false);
                        dto.setDiscountRuleCode(0);
                        dto.setDiscountRate(0);
                    }
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

        /*
         * TEAM_ID는 더 이상 사용하지 않음.
         * DISCOUNT_RULE_CODE가 쿠폰 번호 역할.
         */
        StringBuilder discountSql = new StringBuilder();

        discountSql.append(" INSERT INTO event_discount ( ");
        discountSql.append("        discount_rule_code, ");
        discountSql.append("        event_id, ");
        discountSql.append("        event_discount_rate ");
        discountSql.append(" ) VALUES ( ");
        discountSql.append("        ?, ?, ? ");
        discountSql.append(" ) ");

        Connection con = null;

        PreparedStatement selectEventIdPstmt = null;
        PreparedStatement eventPstmt = null;
        PreparedStatement discountPstmt = null;

        ResultSet rs = null;

        try {

            con = AdminDBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            int eventId = 0;

            selectEventIdPstmt =
                    con.prepareStatement(selectEventIdSql.toString());

            rs =
                    selectEventIdPstmt.executeQuery();

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

            /*
             * 할인 이벤트일 때만 랜덤 쿠폰 번호 발급
             */
            if (event.isDiscount()) {

                int discountRuleCode =
                        generateCouponNumber(con);

                discountPstmt =
                        con.prepareStatement(discountSql.toString());

                discountPstmt.setInt(1, discountRuleCode);
                discountPstmt.setInt(2, eventId);
                discountPstmt.setInt(3, event.getDiscountRate());

                int discountResult =
                        discountPstmt.executeUpdate();

                if (discountResult <= 0) {
                    con.rollback();
                    throw new RuntimeException("이벤트 할인 쿠폰 등록 실패");
                }

                event.setDiscountRuleCode(discountRuleCode);

                System.out.println("발급된 쿠폰 번호 = "
                        + discountRuleCode);
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
                if (discountPstmt != null) {
                    discountPstmt.close();
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

        StringBuilder eventSql = new StringBuilder();

        eventSql.append(" UPDATE event ");
        eventSql.append("    SET event_title = ?, ");
        eventSql.append("        event_summary = ?, ");
        eventSql.append("        thumbnail_img = ?, ");
        eventSql.append("        representative_img = ?, ");
        eventSql.append("        event_start_date = TO_DATE(?, 'YYYY-MM-DD'), ");
        eventSql.append("        event_end_date = TO_DATE(?, 'YYYY-MM-DD'), ");
        eventSql.append("        event_content = ? ");
        eventSql.append("  WHERE event_id = ? ");

        /*
         * 기존 쿠폰 번호 확인.
         * 수정할 때마다 쿠폰 번호가 바뀌면 안 되므로
         * 기존 row가 있으면 할인율만 UPDATE.
         */
        StringBuilder selectExistingDiscountSql = new StringBuilder();

        selectExistingDiscountSql.append(" SELECT discount_rule_code ");
        selectExistingDiscountSql.append("   FROM event_discount ");
        selectExistingDiscountSql.append("  WHERE event_id = ? ");

        StringBuilder updateDiscountSql = new StringBuilder();

        updateDiscountSql.append(" UPDATE event_discount ");
        updateDiscountSql.append("    SET event_discount_rate = ? ");
        updateDiscountSql.append("  WHERE event_id = ? ");

        StringBuilder deleteDiscountSql = new StringBuilder();

        deleteDiscountSql.append(" DELETE FROM event_discount ");
        deleteDiscountSql.append("  WHERE event_id = ? ");

        StringBuilder insertDiscountSql = new StringBuilder();

        insertDiscountSql.append(" INSERT INTO event_discount ( ");
        insertDiscountSql.append("        discount_rule_code, ");
        insertDiscountSql.append("        event_id, ");
        insertDiscountSql.append("        event_discount_rate ");
        insertDiscountSql.append(" ) VALUES ( ");
        insertDiscountSql.append("        ?, ?, ? ");
        insertDiscountSql.append(" ) ");

        Connection con = null;

        PreparedStatement eventPstmt = null;
        PreparedStatement selectExistingDiscountPstmt = null;
        PreparedStatement updateDiscountPstmt = null;
        PreparedStatement deleteDiscountPstmt = null;
        PreparedStatement insertDiscountPstmt = null;

        ResultSet rs = null;

        try {

            con = AdminDBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            eventPstmt =
                    con.prepareStatement(eventSql.toString());

            int index = 1;

            eventPstmt.setString(index++, event.getEventTitle());
            eventPstmt.setString(index++, event.getEventSummary());
            eventPstmt.setString(index++, event.getThumbnailImg());
            eventPstmt.setString(index++, event.getRepresentativeImg());
            eventPstmt.setString(index++, event.getStartDate());
            eventPstmt.setString(index++, event.getEndDate());
            eventPstmt.setString(index++, event.getEventContent());
            eventPstmt.setInt(index++, event.getEventCode());

            result =
                    eventPstmt.executeUpdate();

            if (result <= 0) {
                con.rollback();
                return 0;
            }

            if (!event.isDiscount()) {

                /*
                 * 할인 사용 안 함으로 수정하면 쿠폰 삭제
                 */
                deleteDiscountPstmt =
                        con.prepareStatement(
                                deleteDiscountSql.toString());

                deleteDiscountPstmt.setInt(1, event.getEventCode());
                deleteDiscountPstmt.executeUpdate();

            } else {

                int existingDiscountRuleCode = 0;

                selectExistingDiscountPstmt =
                        con.prepareStatement(
                                selectExistingDiscountSql.toString());

                selectExistingDiscountPstmt.setInt(
                        1,
                        event.getEventCode());

                rs =
                        selectExistingDiscountPstmt.executeQuery();

                if (rs.next()) {
                    existingDiscountRuleCode =
                            rs.getInt("discount_rule_code");
                }

                rs.close();
                rs = null;

                selectExistingDiscountPstmt.close();
                selectExistingDiscountPstmt = null;

                if (existingDiscountRuleCode > 0) {

                    /*
                     * 기존 쿠폰 번호 유지, 할인율만 수정
                     */
                    updateDiscountPstmt =
                            con.prepareStatement(
                                    updateDiscountSql.toString());

                    updateDiscountPstmt.setInt(
                            1,
                            event.getDiscountRate());

                    updateDiscountPstmt.setInt(
                            2,
                            event.getEventCode());

                    int discountResult =
                            updateDiscountPstmt.executeUpdate();

                    if (discountResult <= 0) {
                        con.rollback();
                        return 0;
                    }

                    event.setDiscountRuleCode(existingDiscountRuleCode);

                    System.out.println("기존 쿠폰 번호 유지 = "
                            + existingDiscountRuleCode);

                } else {

                    /*
                     * 기존 쿠폰이 없던 이벤트를 할인 이벤트로 바꾸면
                     * 새 랜덤 쿠폰 번호 발급
                     */
                    int discountRuleCode =
                            generateCouponNumber(con);

                    insertDiscountPstmt =
                            con.prepareStatement(
                                    insertDiscountSql.toString());

                    insertDiscountPstmt.setInt(1, discountRuleCode);
                    insertDiscountPstmt.setInt(2, event.getEventCode());
                    insertDiscountPstmt.setInt(3, event.getDiscountRate());

                    int discountResult =
                            insertDiscountPstmt.executeUpdate();

                    if (discountResult <= 0) {
                        con.rollback();
                        return 0;
                    }

                    event.setDiscountRuleCode(discountRuleCode);

                    System.out.println("새 쿠폰 번호 발급 = "
                            + discountRuleCode);
                }
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
            result = 0;

        } finally {

            try {
                if (rs != null) {
                    rs.close();
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
                if (selectExistingDiscountPstmt != null) {
                    selectExistingDiscountPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (updateDiscountPstmt != null) {
                    updateDiscountPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (deleteDiscountPstmt != null) {
                    deleteDiscountPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (insertDiscountPstmt != null) {
                    insertDiscountPstmt.close();
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

        return result;
    }

    public int deleteEvent(int eventCode) {

        int result = 0;

        StringBuilder deleteDiscountSql = new StringBuilder();

        deleteDiscountSql.append(" DELETE FROM event_discount ");
        deleteDiscountSql.append("  WHERE event_id = ? ");

        StringBuilder deleteEventSql = new StringBuilder();

        deleteEventSql.append(" DELETE FROM event ");
        deleteEventSql.append("  WHERE event_id = ? ");

        Connection con = null;
        PreparedStatement deleteDiscountPstmt = null;
        PreparedStatement deleteEventPstmt = null;

        try {

            con = AdminDBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            deleteDiscountPstmt =
                    con.prepareStatement(
                            deleteDiscountSql.toString());

            deleteDiscountPstmt.setInt(1, eventCode);
            deleteDiscountPstmt.executeUpdate();

            deleteEventPstmt =
                    con.prepareStatement(
                            deleteEventSql.toString());

            deleteEventPstmt.setInt(1, eventCode);

            result =
                    deleteEventPstmt.executeUpdate();

            if (result > 0) {
                con.commit();
            } else {
                con.rollback();
            }

        } catch (Exception e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();
            result = 0;

        } finally {

            try {
                if (deleteDiscountPstmt != null) {
                    deleteDiscountPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (deleteEventPstmt != null) {
                    deleteEventPstmt.close();
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

        return result;
    }

    /*
     * 8자리 랜덤 쿠폰 번호 생성
     */
    private int generateCouponNumber(Connection con) throws Exception {

        SecureRandom random =
                new SecureRandom();

        int couponNumber = 0;

        /*
         * 최대 30번까지 중복 확인
         */
        for (int i = 0; i < 30; i++) {

            couponNumber =
                    10000000 + random.nextInt(90000000);

            if (!existsCouponNumber(con, couponNumber)) {
                return couponNumber;
            }
        }

        throw new RuntimeException("쿠폰 번호 생성 실패");
    }

    /*
     * 쿠폰 번호 중복 확인
     */
    private boolean existsCouponNumber(Connection con,
                                       int couponNumber) throws Exception {

        boolean exists = false;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT COUNT(*) AS cnt ");
        sql.append("   FROM event_discount ");
        sql.append("  WHERE discount_rule_code = ? ");

        try (
            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, couponNumber);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    exists =
                            rs.getInt("cnt") > 0;
                }
            }
        }

        return exists;
    }
}
package kr.admin.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.admin.common.AdminDBConnection;
import kr.admin.common.BoardRangeDTO;

public class MemberManagementDAO {

    public int selectTotalCount(String search, String gradeFilter) {
        int totalCount = 0;

        StringBuilder sql = new StringBuilder();
        List<String> paramList = new ArrayList<String>();

        sql.append(" SELECT COUNT(*) ");
        sql.append(" FROM MEMBER m ");
        sql.append(" WHERE 1 = 1 ");

        appendSearchAndFilter(sql, paramList, search, gradeFilter);

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {
            setStringParams(pstmt, paramList);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalCount = rs.getInt(1);
                }//end if
            }//end try

        } catch (SQLException e) {
            e.printStackTrace();
        }//end catch

        return totalCount;
    }//selectTotalCount

    public List<MemberListDTO> selectMemberList(BoardRangeDTO brDTO, String search, String gradeFilter) {
        List<MemberListDTO> list = new ArrayList<MemberListDTO>();

        StringBuilder baseSql = new StringBuilder();
        List<String> paramList = new ArrayList<String>();

        baseSql.append(" SELECT ");
        baseSql.append("        m.MEMBER_ID, ");
        baseSql.append("        m.NAME, ");
        baseSql.append("        m.PHONE, ");
        baseSql.append("        m.EMAIL, ");
        baseSql.append("        m.GRADE_NAME, ");
        baseSql.append("        TO_CHAR(m.JOIN_DATE, 'YYYY.MM.DD') AS JOIN_DATE_TEXT, ");
        baseSql.append("        m.STATE ");
        baseSql.append(" FROM MEMBER m ");
        baseSql.append(" WHERE 1 = 1 ");

        appendSearchAndFilter(baseSql, paramList, search, gradeFilter);

        baseSql.append(" ORDER BY m.JOIN_DATE DESC ");

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * ");
        sql.append(" FROM ( ");
        sql.append("        SELECT ROWNUM rnum, A.* ");
        sql.append("        FROM ( ");
        sql.append(baseSql);
        sql.append("        ) A ");
        sql.append("        WHERE ROWNUM <= ? ");
        sql.append(" ) ");
        sql.append(" WHERE rnum >= ? ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {
            int index = setStringParams(pstmt, paramList);

            pstmt.setInt(index++, brDTO.getEndNum());
            pstmt.setInt(index, brDTO.getStartNum());

            try (ResultSet rs = pstmt.executeQuery()) {
                MemberListDTO mlDTO = null;

                while (rs.next()) {
                    mlDTO = new MemberListDTO();

                    mlDTO.setMemberId(rs.getString("MEMBER_ID"));
                    mlDTO.setMemberName(rs.getString("NAME"));
                    mlDTO.setMemberTel(rs.getString("PHONE"));
                    mlDTO.setMemberEmail(rs.getString("EMAIL"));
                    mlDTO.setMemberGrade(rs.getString("GRADE_NAME"));
                    mlDTO.setJoinDate(rs.getString("JOIN_DATE_TEXT"));
                    mlDTO.setMemberState(rs.getString("STATE"));

                    list.add(mlDTO);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }//selectMemberList

    public MemberListDTO selectMemberBasic(String memberId) {
        MemberListDTO mlDTO = null;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append("        MEMBER_ID, ");
        sql.append("        NAME, ");
        sql.append("        PHONE, ");
        sql.append("        EMAIL, ");
        sql.append("        GRADE_NAME, ");
        sql.append("        TO_CHAR(JOIN_DATE, 'YYYY.MM.DD') AS JOIN_DATE_TEXT, ");
        sql.append("        STATE ");
        sql.append(" FROM MEMBER ");
        sql.append(" WHERE MEMBER_ID = ? ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {
            pstmt.setString(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    mlDTO = new MemberListDTO();

                    mlDTO.setMemberId(rs.getString("MEMBER_ID"));
                    mlDTO.setMemberName(rs.getString("NAME"));
                    mlDTO.setMemberTel(rs.getString("PHONE"));
                    mlDTO.setMemberEmail(rs.getString("EMAIL"));
                    mlDTO.setMemberGrade(rs.getString("GRADE_NAME"));
                    mlDTO.setJoinDate(rs.getString("JOIN_DATE_TEXT"));
                    mlDTO.setMemberState(rs.getString("STATE"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mlDTO;
    }//selectMemberBasic

    public MemberDetailDTO selectMemberDetail(String memberId) {
        MemberDetailDTO mdDTO = null;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append("        m.MEMBER_ID, ");
        sql.append("        m.ZIPCODE, ");
        sql.append("        TRIM(m.ADDRESS || ' ' || NVL(m.ADDRESS2, '')) AS FULL_ADDRESS, ");
        sql.append("        m.SNS_RECEIVE_YN, ");
        sql.append("        m.EMAIL_RECEIVE_YN, ");

        sql.append("        CASE ");
        sql.append("            WHEN NVL(m.SNS_RECEIVE_YN, 'N') = 'Y' ");
        sql.append("              OR NVL(m.EMAIL_RECEIVE_YN, 'N') = 'Y' ");
        sql.append("            THEN '동의' ");
        sql.append("            ELSE '미동의' ");
        sql.append("        END AS MARKETING_AGREE, ");

        sql.append("        NVL(( ");
        sql.append("            SELECT TO_CHAR(MAX(cl.CONNECTION_DATE), 'YYYY.MM.DD HH24:MI') ");
        sql.append("            FROM CONNECTION_LOG cl ");
        sql.append("            WHERE cl.MEMBER_ID = m.MEMBER_ID ");
        sql.append("        ), '-') AS LAST_LOGIN, ");

        sql.append("        NVL(( ");
        sql.append("            SELECT COUNT(*) ");
        sql.append("            FROM RESERVATION r ");
        sql.append("            WHERE r.MEMBER_ID = m.MEMBER_ID ");
        sql.append("            AND r.RESERVATION_STATUS = '구매' ");
        sql.append("            AND r.PAY_STATE = '구매' ");
        sql.append("        ), 0) AS PURCHASE_COUNT, ");

        sql.append("        NVL(( ");
        sql.append("            SELECT SUM(NVL(r.PAY_PRICE, 0) - NVL(r.CANCEL_PRICE, 0)) ");
        sql.append("            FROM RESERVATION r ");
        sql.append("            WHERE r.MEMBER_ID = m.MEMBER_ID ");
        sql.append("            AND r.RESERVATION_STATUS = '구매' ");
        sql.append("            AND r.PAY_STATE = '구매' ");
        sql.append("        ), 0) AS TOTAL_PAYMENT ");

        sql.append(" FROM MEMBER m ");
        sql.append(" WHERE m.MEMBER_ID = ? ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {
            pstmt.setString(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    mdDTO = new MemberDetailDTO();

                    mdDTO.setMemberId(rs.getString("MEMBER_ID"));
                    mdDTO.setZipCode(rs.getString("ZIPCODE"));
                    mdDTO.setAddress(rs.getString("FULL_ADDRESS"));
                    mdDTO.setLastLogin(rs.getString("LAST_LOGIN"));
                    mdDTO.setSnsReceiveYn(rs.getString("SNS_RECEIVE_YN"));
                    mdDTO.setEmailReceiveYn(rs.getString("EMAIL_RECEIVE_YN"));
                    mdDTO.setMarketingAgree(rs.getString("MARKETING_AGREE"));
                    mdDTO.setPurchaseCount(rs.getInt("PURCHASE_COUNT"));
                    mdDTO.setTotalPayment(rs.getInt("TOTAL_PAYMENT"));
                }//end if
            }//end try

        } catch (SQLException e) {
            e.printStackTrace();
        }//end catch

        return mdDTO;
    }//selectMemberDetail
    
    public List<MemberPayDTO> selectPayHistory(String memberId) {
        List<MemberPayDTO> list = new ArrayList<MemberPayDTO>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append("        r.RESERVATION_ID, ");
        sql.append("        r.MEMBER_ID, ");
        sql.append("        ht.TEAM_NAME AS HOME_TEAM, ");
        sql.append("        at.TEAM_NAME AS AWAY_TEAM, ");
        sql.append("        NVL(SUM(rd.RESERVATION_QUANTITY), 0) AS RESERVATION_CNT, ");

        sql.append("        LISTAGG(rd.RESERVATION_TYPE || ' ' || rd.RESERVATION_QUANTITY || '매', ', ') ");
        sql.append("        WITHIN GROUP (ORDER BY rd.RESERVATION_DETAIL_ID) AS TICKET_INFO, ");

        sql.append("        NVL(r.PAY_PRICE, 0) - NVL(r.CANCEL_PRICE, 0) AS PAYMENT_PRICE, ");
        sql.append("        TO_CHAR(r.RESERVATION_DATE, 'YYYY.MM.DD') AS RESERVATION_DATE_TEXT, ");
        sql.append("        r.RESERVATION_STATUS ");

        sql.append(" FROM RESERVATION r ");

        sql.append(" LEFT JOIN RESERVATION_DETAIL rd ");
        sql.append("   ON r.RESERVATION_ID = rd.RESERVATION_ID ");

        sql.append(" LEFT JOIN GAME_SCHEDULE gs ");
        sql.append("   ON r.GAME_SCHEDULE_ID = gs.GAME_SCHEDULE_ID ");

        sql.append(" LEFT JOIN TEAM ht ");
        sql.append("   ON gs.TEAM_HOME = ht.TEAM_ID ");

        sql.append(" LEFT JOIN TEAM at ");
        sql.append("   ON gs.TEAM_OTHER = at.TEAM_ID ");

        sql.append(" WHERE r.MEMBER_ID = ? ");

        sql.append(" GROUP BY ");
        sql.append("        r.RESERVATION_ID, ");
        sql.append("        r.MEMBER_ID, ");
        sql.append("        ht.TEAM_NAME, ");
        sql.append("        at.TEAM_NAME, ");
        sql.append("        r.PAY_PRICE, ");
        sql.append("        r.CANCEL_PRICE, ");
        sql.append("        r.RESERVATION_DATE, ");
        sql.append("        r.RESERVATION_STATUS ");

        sql.append(" ORDER BY r.RESERVATION_DATE DESC ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {
            pstmt.setString(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                MemberPayDTO mpDTO = null;

                while (rs.next()) {
                    mpDTO = new MemberPayDTO();

                    mpDTO.setReservationCode(rs.getInt("RESERVATION_ID"));
                    mpDTO.setMemberId(rs.getString("MEMBER_ID"));
                    mpDTO.setHomeTeam(rs.getString("HOME_TEAM"));
                    mpDTO.setAwayTeam(rs.getString("AWAY_TEAM"));
                    mpDTO.setReservationCnt(rs.getInt("RESERVATION_CNT"));
                    mpDTO.setTicketInfo(rs.getString("TICKET_INFO"));
                    mpDTO.setPaymentPrice(rs.getInt("PAYMENT_PRICE"));
                    mpDTO.setReservationDate(rs.getString("RESERVATION_DATE_TEXT"));
                    mpDTO.setReservationState(rs.getString("RESERVATION_STATUS"));

                    list.add(mpDTO);
                }//end while
            }//end try

        } catch (SQLException e) {
            e.printStackTrace();
        }//end catch

        return list;
    }//selectPayHistory

    public int updateMemberState(String memberId) {
        int rowCnt = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE MEMBER ");
        sql.append(" SET STATE = '활성' ");
        sql.append(" WHERE MEMBER_ID = ? ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {
            pstmt.setString(1, memberId);

            rowCnt = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }//end catch

        return rowCnt;
    }//updateMemberState
    
    public int selectVipDiscountRate() {
        int vipDiscountRate = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT NVL(MAX(DISCOUNT_RATE), 0) AS VIP_DISCOUNT_RATE ");
        sql.append(" FROM MEMBER ");
        sql.append(" WHERE GRADE_NAME = 'VIP' ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery()
        ) {
            if (rs.next()) {
                vipDiscountRate = rs.getInt("VIP_DISCOUNT_RATE");
            }//end if

        } catch (SQLException e) {
            e.printStackTrace();
        }//end catch

        return vipDiscountRate;
    }//selectVipDiscountRate

    public int updateVipDiscountRate(int vipDiscountRate) {
        int rowCnt = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE MEMBER ");
        sql.append(" SET DISCOUNT_RATE = ? ");
        sql.append(" WHERE GRADE_NAME = 'VIP' ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {
            pstmt.setInt(1, vipDiscountRate);

            rowCnt = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }//end catch

        return rowCnt;
    }//updateVipDiscountRate

    private void appendSearchAndFilter(StringBuilder sql, List<String> paramList,
                                       String search, String gradeFilter) {
        if (search != null && !search.trim().isEmpty()) {
            String keyword = "%" + search.trim().toLowerCase() + "%";
            String phoneKeyword = "%" + search.trim().replace("-", "") + "%";

            sql.append(" AND ( ");
            sql.append("        LOWER(m.NAME) LIKE ? ");
            sql.append("     OR LOWER(m.EMAIL) LIKE ? ");
            sql.append("     OR REPLACE(m.PHONE, '-', '') LIKE ? ");
            sql.append(" ) ");

            paramList.add(keyword);
            paramList.add(keyword);
            paramList.add(phoneKeyword);
        }//end if

        if (gradeFilter != null && !gradeFilter.trim().isEmpty()) {
            if ("VIP".equalsIgnoreCase(gradeFilter)) {
                sql.append(" AND m.GRADE_NAME = 'VIP' ");
            } else if ("NORMAL".equalsIgnoreCase(gradeFilter)
                    || "일반".equals(gradeFilter)
                    || "일반회원".equals(gradeFilter)) {
                sql.append(" AND m.GRADE_NAME IN ('일반', '일반회원') ");
            } else if ("ACTIVE".equalsIgnoreCase(gradeFilter)
                    || "활성".equals(gradeFilter)) {
                sql.append(" AND m.STATE = '활성' ");
            } else if ("DORMANT".equalsIgnoreCase(gradeFilter)
                    || "휴면".equals(gradeFilter)) {
                sql.append(" AND m.STATE = '휴면' ");
            }//end if
        }//end if
    }//appendSearchAndFilter

    private int setStringParams(PreparedStatement pstmt, List<String> paramList) throws SQLException {
        int index = 1;

        for (String param : paramList) {
            pstmt.setString(index++, param);
        }//end for

        return index;
    }//setStringParams

}//class
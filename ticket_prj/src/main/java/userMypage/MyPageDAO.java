package userMypage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.user.common.PasswordHashUtil;
import kr.user.common.UserDBConnection;
import kr.user.member.MemberDTO;
import userMypage.MyPageReservationDTO;
import userMypage.ReservationDetailDTO;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyPageDAO {

    // 회원 요약 정보 조회
    public MemberDTO selectMySummary(String memberId) {

        MemberDTO memberDTO = null;

        return memberDTO;
    }

    // 회원 상세 정보 조회
    public MemberDTO selectMyDetail(String memberId) {

        MemberDTO memberDTO = null;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT MEMBER_ID, NAME, EMAIL, PHONE, ");
            sql.append("ZIPCODE, ADDRESS, ADDRESS2, ");
            sql.append("SNS_RECEIVE_YN, EMAIL_RECEIVE_YN ");
            sql.append("FROM MEMBER ");
            sql.append("WHERE MEMBER_ID=?");

            stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, memberId);

            rs = stmt.executeQuery();

            if(rs.next()){

                memberDTO = new MemberDTO();

                memberDTO.setMemberCode(rs.getString("MEMBER_ID"));
                memberDTO.setName(rs.getString("NAME"));
                memberDTO.setEmail(rs.getString("EMAIL"));
                memberDTO.setPhone(rs.getString("PHONE"));
                memberDTO.setZipcode(rs.getInt("ZIPCODE"));
                memberDTO.setAddress(rs.getString("ADDRESS"));
                memberDTO.setAddress2(rs.getString("ADDRESS2"));
                memberDTO.setSmsReceiveYN(rs.getString("SNS_RECEIVE_YN").charAt(0));
                memberDTO.setEmailReceiveYN(rs.getString("EMAIL_RECEIVE_YN").charAt(0));

            }

        } catch(SQLException e) {

            e.printStackTrace();

        } finally {
            try {
                db.close(rs, stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return memberDTO;
    }

 // 비밀번호 조회
    public String selectPassword(String memberId) {

        String password = null;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT PASS ");
            sql.append("FROM MEMBER ");
            sql.append("WHERE MEMBER_ID=?");

            stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, memberId);

            rs = stmt.executeQuery();

            if(rs.next()) {
                password = rs.getString("PASS");
            }

        } catch(SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                db.close(rs, stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return password;
    }
    // 회원 정보 수정
    public int updateMyInfo(MemberDTO memberDTO) {

        int result = 0;

        Connection con = null;
        PreparedStatement stmt = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE MEMBER ");
            sql.append("SET EMAIL=?, ");
            sql.append("PHONE=?, ");
            sql.append("ZIPCODE=?, ");
            sql.append("ADDRESS=?, ");
            sql.append("ADDRESS2=?, ");
            sql.append("SNS_RECEIVE_YN=?, ");
            sql.append("EMAIL_RECEIVE_YN=? ");
            sql.append("WHERE MEMBER_ID=?");

            stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, memberDTO.getEmail());
            stmt.setString(2, memberDTO.getPhone());
            stmt.setInt(3, memberDTO.getZipcode());
            stmt.setString(4, memberDTO.getAddress());
            stmt.setString(5, memberDTO.getAddress2());
            stmt.setString(6, String.valueOf(memberDTO.getSmsReceiveYN()));
            stmt.setString(7, String.valueOf(memberDTO.getEmailReceiveYN()));
            stmt.setString(8, memberDTO.getMemberCode());

            result = stmt.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();

        } finally {
            db.close(stmt, con);
        }

        return result;
    }

    // 비밀번호 변경
    public int updatePassword(
            String memberId,
            String oldPass,
            String newPass,
            String newPassCheck) {

        int result = 0;

        if(!newPass.equals(newPassCheck)) {
            return result;
        }

        Connection con = null;
        PreparedStatement stmt = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE MEMBER ");
            sql.append("SET PASS=? ");
            sql.append("WHERE MEMBER_ID=? ");
            sql.append("AND PASS=?");

            stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, PasswordHashUtil.sha1(newPass));
            stmt.setString(2, memberId);
            stmt.setString(3, PasswordHashUtil.sha1(oldPass));

            result = stmt.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
            
        } finally {
            db.close(stmt, con);
        }
        return result;

    }

    // 예매/취소 내역 조회
    public List<MyPageReservationDTO> selectReservationList(
            String memberId,
            Date startDate,
            Date endDate,
            String flag) {

        List<MyPageReservationDTO> list = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();
            StringBuilder sql = new StringBuilder();
            
            System.out.println("startDate = " + startDate);
            System.out.println("endDate = " + endDate);
            System.out.println("flag = " + flag);
            
         // SQL
            sql.append("SELECT ");
            sql.append("R.RESERVATION_ID, ");
            sql.append("TH.TEAM_SHORT_NAME || ' VS ' || TA.TEAM_SHORT_NAME AS GAME_NAME, ");
            sql.append("GS.GAME_DATE, ");
            sql.append("GS.GAME_START_TIME, ");
            sql.append("GS.GAME_DATE - 7 AS CANCEL_AVAILABLE_DATE, ");
            sql.append("RD.RESERVATION_QUANTITY, ");
            sql.append("R.RESERVATION_STATUS, ");
            sql.append("RC.CANCEL_DATE ");

            sql.append("FROM RESERVATION R ");

            sql.append("LEFT JOIN RESERVATION_CENCEL RC ");
            sql.append("ON RC.RESERVATION_ID = R.RESERVATION_ID ");

            sql.append("JOIN GAME_SCHEDULE GS ");
            sql.append("ON R.GAME_SCHEDULE_ID = GS.GAME_SCHEDULE_ID ");

            sql.append("JOIN TEAM TH ");
            sql.append("ON GS.TEAM_HOME = TH.TEAM_ID ");

            sql.append("JOIN TEAM TA ");
            sql.append("ON GS.TEAM_OTHER = TA.TEAM_ID ");
            
            sql.append("JOIN ( ");
            sql.append("SELECT RESERVATION_ID, ");
            sql.append("SUM(RESERVATION_QUANTITY) RESERVATION_QUANTITY ");
            sql.append("FROM RESERVATION_DETAIL ");
            sql.append("GROUP BY RESERVATION_ID ");
            sql.append(") RD ");
            sql.append("ON R.RESERVATION_ID = RD.RESERVATION_ID ");

            sql.append("WHERE R.MEMBER_ID=? ");

            if("reservation".equals(flag)) {

                sql.append("AND R.RESERVATION_STATUS='구매' ");

            } else if("cancel".equals(flag)) {

                sql.append("AND R.RESERVATION_STATUS='취소' ");

            }

         
            
            
         // 기간 검색
            if(startDate != null && endDate != null) {
            	sql.append("AND TRUNC(R.RESERVATION_DATE) BETWEEN ? AND ? ");
            }

            sql.append("ORDER BY R.RESERVATION_DATE DESC ");
            
            
            System.out.println(sql.toString());
            System.out.println("memberId = " + memberId);
            
            //기간검색 콘솔출력 확인  (캡쳐후삭제)
            System.out.println("startDate = " + startDate);
            System.out.println("endDate = " + endDate);
            System.out.println("flag = " + flag);
            
            stmt = con.prepareStatement(sql.toString());

            int idx = 1;

            stmt.setString(idx++, memberId);

            if(startDate != null && endDate != null) {

                stmt.setDate(idx++, startDate);
                stmt.setDate(idx++, endDate);

            }

            rs = stmt.executeQuery();

            Map<Integer, MyPageReservationDTO> map = new LinkedHashMap<>();

            while(rs.next()) {

                int reservationId = rs.getInt("RESERVATION_ID");

                MyPageReservationDTO dto = map.get(reservationId);

                if(dto == null) {

                    dto = new MyPageReservationDTO();

                    dto.setReservationCode(reservationId);
                    dto.setGameName(rs.getString("GAME_NAME"));
                    dto.setGameDate(rs.getDate("GAME_DATE"));
                    dto.setGameStartTime(rs.getString("GAME_START_TIME"));
                    dto.setCancelAvailableDate(rs.getDate("CANCEL_AVAILABLE_DATE"));
                    dto.setReservationStatus(rs.getString("RESERVATION_STATUS"));
                    dto.setCancelDate(rs.getDate("CANCEL_DATE"));
                    dto.setReservationQuantity(0);

                    map.put(reservationId, dto);
                }

                dto.setReservationQuantity(
                    dto.getReservationQuantity()
                    + rs.getInt("RESERVATION_QUANTITY")
                );
            }

            list.addAll(map.values());
            
        } catch(SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                db.close(rs, stmt, con);
            } catch(SQLException e) {
                e.printStackTrace();
            }

        }

        return list;
    }
    
 // 경기 정보 조회
    public MyPageReservationDTO selectReservationGameInfo(
            int reservationCode,
            String memberId) {

        MyPageReservationDTO mpDTO = null;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ");
            sql.append("R.RESERVATION_ID, ");
            sql.append("TH.TEAM_SHORT_NAME || ' VS ' || TA.TEAM_SHORT_NAME AS GAME_NAME, ");
            sql.append("GS.GAME_DATE, ");
            sql.append("GS.GAME_START_TIME, ");
            sql.append("S.STADIUM_NAME, ");
            sql.append("R.RESERVATION_STATUS, ");
            sql.append("RC.CANCEL_DATE ");

            sql.append("FROM RESERVATION R ");
            sql.append("LEFT JOIN RESERVATION_CENCEL RC ");
            sql.append("ON R.RESERVATION_ID = RC.RESERVATION_ID ");
            
            sql.append("JOIN GAME_SCHEDULE GS ");
            sql.append("ON R.GAME_SCHEDULE_ID = GS.GAME_SCHEDULE_ID ");

            sql.append("JOIN TEAM TH ");
            sql.append("ON GS.TEAM_HOME = TH.TEAM_ID ");

            sql.append("JOIN TEAM TA ");
            sql.append("ON GS.TEAM_OTHER = TA.TEAM_ID ");

            sql.append("JOIN STADIUM S ");
            sql.append("ON GS.STADIUM_ID = S.STADIUM_ID ");

            sql.append("WHERE R.RESERVATION_ID=? ");
            sql.append("AND R.MEMBER_ID=? ");

            stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, reservationCode);
            stmt.setString(2, memberId);

            rs = stmt.executeQuery();

            if(rs.next()) {

                mpDTO = new MyPageReservationDTO();

                mpDTO.setReservationCode(rs.getInt("RESERVATION_ID"));
                mpDTO.setGameName(rs.getString("GAME_NAME"));
                mpDTO.setGameDate(rs.getDate("GAME_DATE"));
                mpDTO.setGameStartTime(rs.getString("GAME_START_TIME"));
                mpDTO.setStadiumName(rs.getString("STADIUM_NAME"));
                mpDTO.setReservationStatus(rs.getString("RESERVATION_STATUS"));
                mpDTO.setCancelDate(rs.getDate("CANCEL_DATE"));

            }

        } catch(SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                db.close(rs, stmt, con);
            } catch(SQLException e) {
                e.printStackTrace();
            }

        }

        return mpDTO;
    }
 // 좌석/티켓 정보 조회
    public List<ReservationDetailDTO> selectReservationSeatInfo(
            int reservationCode,
            String memberId) {

        List<ReservationDetailDTO> list = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ");
            sql.append("RD.RESERVATION_DETAIL_ID, ");
            sql.append("RD.RESERVATION_ID, ");
            sql.append("RD.STADIUM_SEAT_ID, ");
            sql.append("RD.RESERVATION_TYPE, ");
            sql.append("RD.RESERVATION_QUANTITY, ");
            sql.append("CASE RD.RESERVATION_TYPE ");
            sql.append("WHEN '성인' THEN SS.ADULT_SEAT_PRICE ");
            sql.append("WHEN '청소년' THEN SS.YOUTH_SEAT_PRICE ");
            sql.append("WHEN '어린이' THEN SS.CHILD_SEAT_PRICE ");
            sql.append("END AS TICKET_PRICE, ");
            sql.append("R.RESERVATION_STATUS, ");
            sql.append("RC.CANCEL_DATE ");

            sql.append("FROM RESERVATION R ");
            
            sql.append("LEFT JOIN RESERVATION_CENCEL RC ");
            sql.append("ON R.RESERVATION_ID = RC.RESERVATION_ID ");

            sql.append("JOIN RESERVATION_DETAIL RD ");
            sql.append("ON R.RESERVATION_ID = RD.RESERVATION_ID ");

            sql.append("JOIN STADIUM_SEAT SS ");
            sql.append("ON RD.STADIUM_SEAT_ID = SS.STADIUM_SEAT_ID ");

            sql.append("WHERE R.RESERVATION_ID=? ");
            sql.append("AND R.MEMBER_ID=? ");

            stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, reservationCode);
            stmt.setString(2, memberId);

            rs = stmt.executeQuery();

            while(rs.next()) {

                ReservationDetailDTO dto = new ReservationDetailDTO();

                dto.setReservationDetailCode(rs.getInt("RESERVATION_DETAIL_ID"));
                dto.setReservationCode(rs.getInt("RESERVATION_ID"));
                dto.setStadiumSeatCode(rs.getInt("STADIUM_SEAT_ID"));
                dto.setReservationType(rs.getString("RESERVATION_TYPE"));
                dto.setReservationQuantity(rs.getInt("RESERVATION_QUANTITY"));
                dto.setTicketPrice(rs.getInt("TICKET_PRICE"));
                dto.setReservationStatus(rs.getString("RESERVATION_STATUS"));
                dto.setCancelDate(rs.getDate("CANCEL_DATE"));

                list.add(dto);

            }

        } catch(SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                db.close(rs, stmt, con);
            } catch(SQLException e) {
                e.printStackTrace();
            }

        }

        return list;
    }

 // 예매 정보 조회
    public MyPageReservationDTO selectReservationInfo(
            int reservationCode,
            String memberId) {

        MyPageReservationDTO mpDTO = null;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ");
            sql.append("RESERVATION_ID, ");
            sql.append("RESERVATION_DATE, ");
            sql.append("RESERVATION_STATUS ");

            sql.append("FROM RESERVATION ");

            sql.append("WHERE RESERVATION_ID=? ");
            sql.append("AND MEMBER_ID=? ");

            stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, reservationCode);
            stmt.setString(2, memberId);

            rs = stmt.executeQuery();

            if(rs.next()) {

                mpDTO = new MyPageReservationDTO();

                mpDTO.setReservationCode(rs.getInt("RESERVATION_ID"));
                mpDTO.setReservationDate(rs.getDate("RESERVATION_DATE"));
                mpDTO.setReservationStatus(rs.getString("RESERVATION_STATUS"));

            }

        } catch(SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                db.close(rs, stmt, con);
            } catch(SQLException e) {
                e.printStackTrace();
            }

        }

        return mpDTO;
    }

 // 결제 정보 조회
    public MyPageReservationDTO selectPaymentInfo(
            int reservationCode,
            String memberId) {

        MyPageReservationDTO mpDTO = null;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ");
            sql.append("PAY_PRICE, ");
            sql.append("PAY_STATE ");

            sql.append("FROM RESERVATION ");

            sql.append("WHERE RESERVATION_ID=? ");
            sql.append("AND MEMBER_ID=? ");

            stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, reservationCode);
            stmt.setString(2, memberId);

            rs = stmt.executeQuery();

            if(rs.next()) {

                mpDTO = new MyPageReservationDTO();

                mpDTO.setPaymentAmount(rs.getInt("PAY_PRICE"));
                mpDTO.setTotalPrice(rs.getInt("PAY_PRICE"));
                mpDTO.setPaymentStatus(rs.getString("PAY_STATE"));

            }

        } catch(SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                db.close(rs, stmt, con);
            } catch(SQLException e) {
                e.printStackTrace();
            }

        }

        return mpDTO;
    }
  
    // 예매 취소
    public int cancelReservation(int reservationId, String memberId) {

        int result = 0;

        Connection con = null;
        PreparedStatement stmt = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {
            con = db.getConnection();
            con.setAutoCommit(false);

            StringBuilder updateSql = new StringBuilder();
            updateSql.append("UPDATE RESERVATION ");
            updateSql.append("SET RESERVATION_STATUS=? ");
            updateSql.append("WHERE RESERVATION_ID=? ");
            updateSql.append("AND MEMBER_ID=? ");

            stmt = con.prepareStatement(updateSql.toString());
            stmt.setString(1, "취소");
            stmt.setInt(2, reservationId);
            stmt.setString(3, memberId);

            int updateResult = stmt.executeUpdate();
            db.close(stmt, null);

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO RESERVATION_CENCEL ");
            insertSql.append("(CANCEL_ID, RESERVATION_ID, CANCEL_TYPE, CANCEL_DATE) ");
            insertSql.append("VALUES ( ");
            insertSql.append("(SELECT NVL(MAX(CANCEL_ID), 0) + 1 FROM RESERVATION_CENCEL), ");
            insertSql.append("?, ?, SYSDATE) ");

            stmt = con.prepareStatement(insertSql.toString());
            stmt.setInt(1, reservationId);
            stmt.setString(2, "취소");

            int insertResult = stmt.executeUpdate();

            if(updateResult > 0 && insertResult > 0) {
                con.commit();
                result = 1;
            } else {
                con.rollback();
            }

        } catch(SQLException e) {
            try {
                if(con != null) con.rollback();
            } catch(SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();

        } finally {
            try {
                if(con != null) con.setAutoCommit(true);
            } catch(SQLException e) {
                e.printStackTrace();
            }

            db.close(stmt, con);
        }

        return result;
    }
    
 // 회원 탈퇴
    public int withdrawMember(String memberId){

        int result = 0;

        Connection con = null;
        PreparedStatement stmt = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try{

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE MEMBER ");
            sql.append("SET STATE=? ");
            sql.append("WHERE MEMBER_ID=? ");

            stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, "탈퇴");
            stmt.setString(2, memberId);

            result = stmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            db.close(stmt, con);
        }

        return result;
    }

}
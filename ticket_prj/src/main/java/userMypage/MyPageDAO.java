package userMypage;

import java.sql.Date;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.user.common.UserDBConnection;
import kr.user.member.MemberDTO;
import userMypage.MyPageReservationDTO;
import userMypage.ReservationCancelDTO;
import userMypage.ReservationDetailDTO;

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

            stmt.setString(1, newPass);
            stmt.setString(2, memberId);
            stmt.setString(3, oldPass);

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

        List<MyPageReservationDTO> list = null;

        return list;
    }

    // 경기 정보 조회
    public MyPageReservationDTO selectReservationGameInfo(
            int reservationCode,
            String memberId) {

        MyPageReservationDTO mpDTO = null;

        return mpDTO;
    }

    // 좌석/티켓 정보 조회
    public List<ReservationDetailDTO> selectReservationSeatInfo(
            int reservationCode,
            String memberId) {

        List<ReservationDetailDTO> list = null;

        return list;
    }

    // 예매 정보 조회
    public MyPageReservationDTO selectReservationInfo(
            int reservationCode,
            String memberId) {

        MyPageReservationDTO mpDTO = null;

        return mpDTO;
    }

    // 결제 정보 조회
    public MyPageReservationDTO selectPaymentInfo(
            int reservationCode,
            String memberId) {

        MyPageReservationDTO mpDTO = null;

        return mpDTO;
    }

    // 취소 내역 조회
    public List<ReservationCancelDTO> selectCancelList(
            String memberId) {

        List<ReservationCancelDTO> list = null;

        return list;
    }

    // 예매 취소
    public int cancelReservation(
            int reservationCode,
            String memberId) {

        int result = 0;

        return result;
    }

}
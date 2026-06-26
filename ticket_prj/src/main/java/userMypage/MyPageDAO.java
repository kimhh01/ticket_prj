package userMypage;

import java.sql.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.user.common.UserDBConnection;
import userMypage.MemberDTO;
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

        return memberDTO;
    }

    // 비밀번호 조회
    public String selectPassword(String memberId) {

        String password = null;

        return password;
    }

    // 회원 정보 수정
    public int updateMyInfo(MemberDTO memberDTO) {

        int result = 0;

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
        PreparedStatement pstmt = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE MEMBER ");
            sql.append("SET PASS=? ");
            sql.append("WHERE MEMBER_ID=? ");
            sql.append("AND PASS=?");

            pstmt = con.prepareStatement(sql.toString());

            pstmt.setString(1, newPass);
            pstmt.setString(2, memberId);
            pstmt.setString(3, oldPass);

            result = pstmt.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
            
        } finally {
            db.close(pstmt, con);
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
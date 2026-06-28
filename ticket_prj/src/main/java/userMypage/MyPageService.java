package userMypage;

import java.sql.Date;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.user.common.UserDBConnection;
import kr.user.member.MemberDTO;
import userMypage.MyPageDAO;
import userMypage.MyPageReservationDTO;
import userMypage.ReservationDetailDTO;


public class MyPageService {

    private MyPageDAO mpDAO;

    public MyPageService() {
        mpDAO = new MyPageDAO();
    }

    // 회원 요약 정보 조회
    public MemberDTO getMySummary(String memberId) {

        return mpDAO.selectMySummary(memberId);
    }

    // 회원 상세 정보 조회
    public MemberDTO getMyDetail(String memberId) {

        return mpDAO.selectMyDetail(memberId);
    }

    // 비밀번호 확인
    public boolean checkPassword(String memberId, String pass) {

        String dbPass = mpDAO.selectPassword(memberId);

        return pass.equals(dbPass);
    }

    // 회원 정보 수정
    public int updateMyInfo(MemberDTO memberDTO) {

        return mpDAO.updateMyInfo(memberDTO);

    }

    // 비밀번호 변경
    public boolean updatePassword(
            String memberId,
            String oldPass,
            String newPass,
            String newPassCheck) {

        // 새 비밀번호와 확인 비밀번호가 다르면 실패
        if(!newPass.equals(newPassCheck)) {
            return false;
        }

        int result = mpDAO.updatePassword(
                memberId,
                oldPass,
                newPass,
                newPassCheck);

        return result > 0;
    }
    
    // 예매 / 취소 내역 조회
    public List<MyPageReservationDTO> getReservationList(
            String memberId,
            Date startDate,
            Date endDate,
            String tabType){	
    	
        return mpDAO.selectReservationList(
                memberId,
                startDate,
                endDate,
                tabType);
    }

    // 경기 정보 조회
    public MyPageReservationDTO getReservationGameInfo(
            int reservationCode,
            String memberId) {

        return mpDAO.selectReservationGameInfo(
                reservationCode,
                memberId);
    }

    // 좌석 / 티켓 정보 조회
    public List<ReservationDetailDTO> getReservationSeatInfo(
            int reservationCode,
            String memberId) {

        return mpDAO.selectReservationSeatInfo(
                reservationCode,
                memberId);
    }

    // 예매 정보 조회
    public MyPageReservationDTO getReservationInfo(
            int reservationCode,
            String memberId) {

        return mpDAO.selectReservationInfo(
                reservationCode,
                memberId);
    }

    // 결제 정보 조회
    public MyPageReservationDTO getPaymentInfo(
            int reservationCode,
            String memberId) {

        return mpDAO.selectPaymentInfo(
                reservationCode,
                memberId);
    }


    // 예매 취소
    public boolean cancelReservation(
            int reservationCode,
            String memberId) {

        int result = mpDAO.cancelReservation(
                reservationCode,
                memberId);

        return result > 0;
    }

}
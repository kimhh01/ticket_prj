package ticket_site.dao;

import java.sql.Date;
import java.util.List;

import ticket_site.dto.MemberDTO;
import ticket_site.dto.MyPageReservationDTO;
import ticket_site.dto.ReservationCancelDTO;
import ticket_site.dto.ReservationDetailDTO;

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
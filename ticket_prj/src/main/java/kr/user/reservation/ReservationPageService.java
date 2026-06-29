package kr.user.reservation;

import java.sql.SQLException;
import java.util.List;

public class ReservationPageService {

    private ReservationPageDAO rpDAO;

    public ReservationPageService() {
        rpDAO = ReservationPageDAO.getInstance();
    }

    // 예매 추가
    public int addReservation(ReservationPageDTO rpDTO) throws SQLException {
        return rpDAO.insertReservation(rpDTO);
    }

    // 예매 상세 추가
    public int addReservationDetail(ReservationPageDTO rpDTO) throws SQLException {
        return rpDAO.insertReservationDetail(rpDTO);
    }

    // 경기 정보 조회
    public ReservationPageDTO searchGame(int gameScheduleCode) throws SQLException {
        return rpDAO.selectGame(gameScheduleCode);
    }

    // 잔여 좌석 조회
    public List<ReservationPageDTO> searchRemainingSeat(int stadiumCode) throws SQLException {
        return rpDAO.selectRemainingSeat(stadiumCode);
    }

    // 좌석 가격 조회
    public List<ReservationPageDTO> searchSeatPrice(int stadiumCode) throws SQLException {
        return rpDAO.selectSeatPrice(stadiumCode);
    }

    // 주문자 정보 조회
    public ReservationPageDTO searchOrderMemberInfo(String memberCode) throws SQLException {
        return rpDAO.selectOrderMemberInfo(memberCode);
    }

    // 주문자 정보 수정
    public int modifyOrderMemberInfo(ReservationPageDTO rpDTO) throws SQLException {
        return rpDAO.updateOrderMemberInfo(rpDTO);
    }

}

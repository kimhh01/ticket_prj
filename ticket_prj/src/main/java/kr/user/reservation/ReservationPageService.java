package kr.user.reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import kr.user.common.UserDBConnection; // 커넥션 클래스 임포트

public class ReservationPageService {

    private ReservationPageDAO rpDAO;
    // DAO와 동일하게 데이터베이스 커넥션 인스턴스를 가져옵니다.
    private UserDBConnection udbc = UserDBConnection.getInstance();

    public ReservationPageService() {
        rpDAO = ReservationPageDAO.getInstance();
    }

    public int insertTotalReservation(ReservationPageDTO rpDTO) {

        Connection conn = null;
        int reservationCode=0;
        try {

            conn = udbc.getConnection();

            if (conn != null) {

                conn.setAutoCommit(false);

                // ① 시퀀스 조회
                int reservationId = rpDAO.getReservationSeq(conn);
                rpDTO.setReservationCode(reservationId);

                // ② reservation 저장
                int mainResult = rpDAO.insertReservation(conn, rpDTO);

                // ③ reservation_detail 저장
                int detailResult = rpDAO.insertReservationDetail(conn, rpDTO);

                if(mainResult > 0 && detailResult > 0){
                    conn.commit();
                    reservationCode=rpDTO.getReservationCode();
                }else{
                    conn.rollback();
                }
            }

        } catch(SQLException e){
            e.printStackTrace();

            if(conn != null){
                try{
                    conn.rollback();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }

        } finally{

            if(conn != null){
                try{
                    conn.setAutoCommit(true);
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }

        }

        return reservationCode;
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
    public ReservationPageDTO searchSeatPrice(int stadiumCode) throws SQLException {
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
    public String getSeatName(int stadiumSeatCode) throws SQLException { 
    	return rpDAO.selectSeatName(stadiumSeatCode); 
    }
    //이벤트 쿠폰 할인
    public int getDiscountRate(String eventCode) throws SQLException {
    	return rpDAO.selectDiscountRate(eventCode);
    }
    //가격조회 메서드
    public int getSeatPrice(int stadiumSeatCode, String reservationType) throws SQLException{
        return rpDAO.selectSeatPrice(stadiumSeatCode, reservationType);
    }
}
package kr.user.reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import kr.user.common.UserDBConnection;

public class ReservationService {

    private ReservationDAO rpDAO;
    private UserDBConnection udbc = UserDBConnection.getInstance();

    public ReservationService() {
        rpDAO = ReservationDAO.getInstance();
    }

 // [수정] 메인 예약 정보(rpDTO)와 선택된 권종 리스트(detailList)를 함께 받아서 단일 트랜잭션으로 처리합니다
    public int insertTotalReservation(ReservationDTO rpDTO, List<ReservationDTO> detailList) {

        Connection conn = null;
        int reservationCode = 0;
        try {

            conn = udbc.getConnection();

            if (conn != null) {

                conn.setAutoCommit(false);

                // ① 시퀀스 조회
                int reservationId = rpDAO.getReservationSeq(conn);
                rpDTO.setReservationCode(reservationId);

                // ② reservation 마스터 테이블 저장
                int mainResult = rpDAO.insertReservation(conn, rpDTO);

                // ③ [수정] reservation_detail 다중 리스트 루프 실행 및 일괄 저장
                int detailResultCount = 0;
                for (ReservationDTO detailDTO : detailList) {
                    detailDTO.setReservationCode(reservationId); // 생성된 부모 예약 코드를 똑같이 바인딩
                    int result = rpDAO.insertReservationDetail(conn, detailDTO);
                    if (result > 0) {
                        detailResultCount++;
                    }
                }

                // ④ 좌석 수 차감 처리 (전체 구매 수량만큼 일괄 반영)
                int seatUpdateResult = rpDAO.updateSeatCount(conn, rpDTO);

                // 마스터 등록 성공 && 상세 리스트 전체 저장 성공 && 좌석 차감 성공 시 최종 커밋
                if(mainResult > 0 && detailResultCount == detailList.size() && seatUpdateResult > 0){
                    conn.commit();
                    reservationCode = rpDTO.getReservationCode();
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
    public ReservationDTO searchGame(int gameScheduleCode) throws SQLException {
        return rpDAO.selectGame(gameScheduleCode);
    }

    // 잔여 좌석 조회
    public List<ReservationDTO> searchRemainingSeat(int stadiumCode) throws SQLException {
        return rpDAO.selectRemainingSeat(stadiumCode);
    }

    // 주문자 정보 조회
    public ReservationDTO searchOrderMemberInfo(String memberCode) throws SQLException {
        return rpDAO.selectOrderMemberInfo(memberCode);
    }

    public String getSeatName(int stadiumSeatCode) throws SQLException { 
    	return rpDAO.selectSeatName(stadiumSeatCode); 
    }
    //할인율 가져오기
  	public List<ReservationDTO> getCoupon(String memberCode) throws SQLException {
  		return rpDAO.selectCoupon(memberCode);
  	}
  	//쿠폰 사용 완료후 상태값 변경
  	public void updateCouponState(String memberCode, String couponCode) throws SQLException {
  	    rpDAO.updateCouponState(memberCode, couponCode);
  	}
    //가격조회 메서드
    public int getSeatPrice(int stadiumSeatCode, String reservationType) throws SQLException{
        return rpDAO.selectSeatPrice(stadiumSeatCode, reservationType);
    }
   
}
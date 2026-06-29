package kr.user.reservation;

import java.sql.Connection;
import java.sql.SQLException;
import kr.user.common.UserDBConnection; // 커넥션 클래스 임포트

public class ReservationPageService {

    private ReservationPageDAO rpDAO;
    // DAO와 동일하게 데이터베이스 커넥션 인스턴스를 가져옵니다.
    private UserDBConnection udbc = UserDBConnection.getInstance();

    public ReservationPageService() {
        rpDAO = ReservationPageDAO.getInstance();
    }

    /**
     * 전체 예매 프로세스 (트랜잭션 처리 완료)
     */
    public boolean insertTotalReservation(ReservationPageDTO rpDTO) {
        Connection conn = null;
        boolean isSuccess = false;
        
        try {
            // [수정] 프로젝트 공통 커넥션 획득 메서드로 연결
            conn = udbc.getConnection(); 
            
            if (conn != null) {
                conn.setAutoCommit(false); // 1. 자동 커밋 끄기 (트랜잭션 시작)
                
                // 2. 예매 메인 추가 (이제 생성된 시퀀스 ID가 rpDTO 내부에 자동으로 세팅됩니다)
                int mainResult = rpDAO.insertReservation(conn, rpDTO); 
                
                // 3. 예매 상세 추가 (위에서 채워진 코드를 들고 들어갑니다)
                int detailResult = rpDAO.insertReservationDetail(conn, rpDTO);
                
                // 4. 둘 다 성공해야만 커밋
                if (mainResult > 0 && detailResult > 0) {
                    conn.commit();
                    isSuccess = true;
                } else {
                	//하나라도 실패시 롤백
                    conn.rollback(); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        } finally {
            // 5. 커넥션 반납 및 오토커밋 원상복구
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close(); 
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    // 경기 정보 조회
    public ReservationPageDTO searchGame(int gameScheduleCode) throws SQLException {
        return rpDAO.selectGame(gameScheduleCode);
    }

    // 잔여 좌석 조회
    public ReservationPageDTO searchRemainingSeat(int stadiumCode) throws SQLException {
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
}
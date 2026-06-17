package user_ReservationPage;

import java.util.List;

public class ReservationPageDAO {
	
	private static ReservationPageDAO rDAO;
	
	private ReservationPageDAO() {
		
	}
	
	public static ReservationPageDAO getInstance() {
		if(rDAO==null) {
			rDAO=new ReservationPageDAO();
		}
		
		return rDAO;
	}
	
	//예매 추가
	public ReservationPageDTO insertReservation(ReservationPageDTO rDTO) {
		
		
		return null;
	}//insertReservation
	
	//보여줄 경기 일정
	public ReservationPageDTO selectGameDate(int teamHomeCode, int teamOtherCode) {
		
		
		return null;
	}
	
	//구장 이미지
	public ReservationPageDTO selectGameStadium(int stadiumCode) {
		
		return null;
	}
	
	//구장별 좌석 가격
	public ReservationPageDTO selectSeatPrice(int stadiumCode) {
		
		return null;
	}
	
	//잔여좌석보여주기
	public List<ReservationPageDTO> selectRemainingSeat(int stadiumCode, int stadiumSeatNum) {
		
		return null;
	}
	
	//경기 로고
	public ReservationPageDTO selectGameImg(ReservationPageDTO rDTO) {
		
		return null;
	}
	
	//예매정보
	public ReservationPageDTO selectReservationInfo(String memberCode,int reservationCode) {
		
		return null;
	}
	
	//주문자정보
	public ReservationPageDTO selectOrderMemberInfo(ReservationPageDTO rDTO) {
		
		return null;
	}
	
	//주문자정보수정
	public ReservationPageDTO updateOrderMemberInfo(ReservationPageDTO rDTO) {
		
		return null;
	}
}

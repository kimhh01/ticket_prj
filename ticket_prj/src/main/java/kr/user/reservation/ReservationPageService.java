package kr.user.reservation;

import java.util.List;

public class ReservationPageService {
	
	
		//좌석예매
		public ReservationPageDTO getReservation(ReservationPageDTO rDTO) {
			
			
			return null;
		}//insertReservation
		
		//보여줄 경기 일정
		public ReservationPageDTO getGameDate(int teamHomeCode, int teamOtherCode) {
			
			
			return null;
		}
		
		//구장 이미지
		public ReservationPageDTO getGameStadium(int stadiumCode) {
			
			return null;
		}
		
		//구장별 좌석 가격
		public ReservationPageDTO getSeatPrice(int stadiumCode) {
			
			return null;
		}
		
		//잔여좌석보여주기
		public List<ReservationPageDTO> getRemainingSeat(int stadiumCode, int stadiumSeatNum) {
			
			return null;
		}
		
		//경기 로고
		public ReservationPageDTO getGameImg(ReservationPageDTO rDTO) {
			
			return null;
		}
		
		//예매정보
		public ReservationPageDTO getReservationInfo(String memberCode,int reservationCode) {
			
			return null;
		}
		
		//주문자정보
		public ReservationPageDTO gettOrderMemberInfo(ReservationPageDTO rDTO) {
			
			return null;
		}
		
		//주문자정보수정
		public ReservationPageDTO modifyOrderMemberInfo(ReservationPageDTO rDTO) {
			
			return null;
		}
}

package kr.user.reservation;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationPageDTO {
	private int reservationCode;//예매코드
	private int reservationDetailCode;//예매 상세 코드
	private int gameScheduleCode;//경기코드
	private int teamHomeCode;//홈팀코드
	private int teamOtherCode;//원정팀코드
	private int stadiumCode;//구장코드
	private String memberCode;//회원아이디
	private String memberName;//회원 이름
	private String memberPhone; //회원 전화번호
	private String memberEmail; //회원 이메일
	private Date reservationDate;//예매일
	private int totalPrice; //총금액
	private int payPrice;//결제금액
	private int discountPrice;//할인금액
	private Date gameDate;//경기시작일자
	private String gameStartTime; //경기시작시간
	private String stadiumName;//구장이름
	private String stadiumImg;//구장이미지
	private String teamHomeName; //홈팀이름
	private String teamHomeImg;//홈팀이미지
	private String teamOtherName;// 원정팀이름
	private String teamOtherImg;//원정팀이미지
	private String seatName;//좌석 이름
	private int stadiumSeatCode;//좌석코드
	private String reservationType;//좌석 유형
	private int reservationQuantity;//예매 수량
	private int totalSeatNum;//전체좌석수
	private int firstBaseSeat; //1루 잔여좌석수
	private int thirdBaseSeat; //3루 잔여좌석수
	private int homeBaseSeat; //홈루 잔여좌석수
	private int outFieldSeat; //홈루 잔여좌석수
	private int remainSeatNum;//잔여좌석수
	private int adultSeatPrice;//성인좌석가격
	private int youthSeatPrice;//청소년좌석가격
	private int childSeatPrice;//어린이좌석가격
	
	
}

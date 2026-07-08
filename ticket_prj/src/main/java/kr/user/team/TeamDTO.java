package kr.user.team;

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
public class TeamDTO {
	private int teamCode;//팀코드
	private int gameScheduleCode;//경기일정코드
	private Date gameDate;//경기일자
	private String gameStartTime;//경기 시간
	private String stadiumName;//구장이름
	private String teamHomeImg;//홈팀이미지
	private String teamOtherImg;//원정팀이미지
	private String teamPageimg;//팀페이지이미지
	private String teamHomeName;//홈팀이름
	private String teamOtherName;//원정팀이름
	private String commonNotice;//로고 아래 공지사항
	private String noticeTitle;//공지사항 제목
	private String noticeTab;//공지사항구분
	private String noticeImg;//공지사항 이미지 or 리그안내 이미지
	private Date noticeWriteDate;//공지사항일자
	private boolean reservationOpen;//경기 오픈 예정
	
}
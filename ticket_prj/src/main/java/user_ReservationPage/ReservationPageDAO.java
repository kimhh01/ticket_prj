package user_ReservationPage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dbConnection.DbConnection;
import dbConnection.Path;

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
	
	//예매창에서 보여줄 경기 구장 이미지
	public String selectGameDate(int gameScheduleCode) {
		String stadiumImg=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		
		
		try {
			con=dbCon.getConn(new File(Path.DATABASE_PROPERTIES));
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	select s.stadium_seat_img as stadimImg	")
			.append("	from game_schedule gs	")
			.append("	join stadium s ")
			.append("	on gs.stadium_id = s.stadium_id ")
			.append("	where gs.game_schedule_id = ? ");
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, gameScheduleCode);
				rs=pstmt.executeQuery();
				
			if(rs.next()) {
				stadiumImg=rs.getString("stadiumImg");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbCon.dbClose(rs, pstmt, con);
		}
		
		return stadiumImg;
		
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

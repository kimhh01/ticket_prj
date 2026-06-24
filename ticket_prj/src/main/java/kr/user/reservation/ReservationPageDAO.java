package kr.user.reservation;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DbConnection;
import dbConnection.Path;
import kr.user.team.TeamDTO;

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

	
	//예매창에서 보여줄 경기 구장 이미지 및 경기 일자
	public ReservationPageDTO selectGame(int gameScheduleCode) {
		
		ReservationPageDTO rpDTO=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		
		
		try {
			con=dbCon.getConn(new File(Path.DATABASE_PROPERTIES));
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	select s.stadium_seat_img as stadium_Iimg,	")
			.append("	h.team_name as home_name, o.team_name as other_name,	")
			.append("	h.team_logo_img as home_img, o.team_logo_img as other_img,	")
			.append("	gs.game_date as game_date, gs.game_start_time as game_start_time	")
			.append("	from game_schedule gs	")
			.append("	join stadium s ")
			.append("	on gs.stadium_id = s.stadium_id ")
			.append("	join team h ")
			.append("	on h.team_id = gs.team_home ")
			.append("	join team o ")
			.append("	on o.team_id = gs.team_other ")
			.append("	where gs.game_schedule_id = ? ");
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, gameScheduleCode);
				rs=pstmt.executeQuery();
				
			if(rs.next()) {
				rpDTO.setStadiumImg(rs.getString("stadium_Img"));
				rpDTO.setTeamHomeName(rs.getString("home_name"));
				rpDTO.setTeamOtherName(rs.getString("other_name"));
				rpDTO.setTeamHomeImg(rs.getString("home_img"));
				rpDTO.setTeamOtherImg(rs.getString("other_img"));
				rpDTO.setGameDate(rs.getDate("game_date"));
				rpDTO.setGameStartTime(rs.getString("game_start_time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbCon.dbClose(rs, pstmt, con);
		}
		
		return rpDTO;
	}
	
	//잔여좌석보여주기
	public List<ReservationPageDTO> selectRemainingSeat(int stadiumCode) {
		List<ReservationPageDTO> remainingSeatList = new ArrayList<ReservationPageDTO>();
		ReservationPageDTO rpDTO=null;

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    DbConnection dbCon=DbConnection.getInstance();
		
		
		try {
			con=dbCon.getConn(new File(Path.DATABASE_PROPERTIES));
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	selset ss.stadium_id,	")
			.append("	sum(ss.stadium_seat_count)	")
			.append("	-nvl(sum(rd.reservation_quantity),0) as remain_seat	")
			.append("	from stadium_seat ss	")
			.append("	left join reservation_detail rd	")
			.append("	on ss.stadium_seat_id=rd.stadium_seat_id	")
			.append("	where ss.stadium_id = ?	")
			.append("	group by ss.stadium_id	");
			
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, stadiumSeatCode);
				rs=pstmt.executeQuery();
				
			while(rs.next()) {
				rpDTO=new ReservationPageDTO();
				rpDTO.setRemainSeatNum(rs.getInt("remain_seat"));
				
				remainingSeatList.add(rpDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbCon.dbClose(rs, pstmt, con);
		}    

	    return remainingSeatList;
	}
	
	//구장별 좌석 가격
	public ReservationPageDTO selectSeatPrice(int stadiumCode) {
		
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

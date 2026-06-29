package kr.user.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.user.common.UserDBConnection;

public class ReservationPageDAO {
	
	private static ReservationPageDAO rDAO;
	private UserDBConnection udbc=UserDBConnection.getInstance();
	
	private ReservationPageDAO() {
		
	}
	
	public static ReservationPageDAO getInstance() {
		if(rDAO==null) {
			rDAO=new ReservationPageDAO();
		}
		
		return rDAO;
	}
	
	
	//예매 추가
	public int insertReservation(ReservationPageDTO rpDTO) throws SQLException {
		int rowCnt=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=udbc.getConnection();
			
			String sql =
					"insert into reservation(" +
					"reservation_id, member_id, admin_id, reservation_date," +
					"total_price, discount_price, pay_price, cancel_price," +
					"reservation_status, pay_state) " +
					"values(reservation_seq.nextval, ?, 1, SYSDATE, ?, ?, ?, 0, '구매', '구매')";
			
			pstmt=con.prepareStatement(sql.toString());
			
			pstmt.setString(1, rpDTO.getMemberCode());
			pstmt.setInt(2, rpDTO.getTotalPrice());
			pstmt.setInt(3, rpDTO.getDiscountPrice());
			pstmt.setInt(4, rpDTO.getPayPrice());
			
			rowCnt = pstmt.executeUpdate();
		}finally {
			udbc.close(null, pstmt, con);
		}
		
		
		return rowCnt;
	}//insertReservation
	
	//예매 추가 디테일
	public int insertReservationDetail(ReservationPageDTO rpDTO) throws SQLException {
		int rowCnt=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=udbc.getConnection();
			
			StringBuilder sql=new StringBuilder();
			 sql
			 .append(" insert into reservation_detail ")
		     .append(" (reservation_detail_id, ")
		     .append(" reservation_id, ")
		     .append(" stadium_seat_id, ")
		     .append(" reservation_type, ")
		     .append(" reservation_quantity) ")
		     .append(" values ")
		     .append(" (reservation_detail_seq.nextval, ")
		     .append(" ?, ?, ?, ?) ");

			 
			 pstmt = con.prepareStatement(sql.toString());

		        pstmt.setInt(1, rpDTO.getReservationCode());
		        pstmt.setInt(2, rpDTO.getStadiumSeatCode());
		        pstmt.setString(3, rpDTO.getReservationType());
		        pstmt.setInt(4, rpDTO.getReservationQuantity());

		        rowCnt = pstmt.executeUpdate();

			
		}finally {
			udbc.close(null, pstmt, con);
			
		}
		
		return rowCnt;
	}

	
	//예매창에서 보여줄 경기 구장 이미지 및 경기 일자
	public ReservationPageDTO selectGame(int gameScheduleCode) throws SQLException {
		
		ReservationPageDTO rpDTO=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		
		
		try {
			con=udbc.getConnection();
			StringBuilder selectGameInfo=new StringBuilder();
			selectGameInfo
			.append("	select s.stadium_seat_img as stadium_Img,	")
			.append("	h.team_name as home_name, o.team_name as other_name,	")
			.append("	h.team_logo_img as home_img, o.team_logo_img as other_img,	")
			.append("	gs.game_date as game_date, gs.game_start_time as game_start_time,	")
			.append("	s.stadium_id as stadium_code	")
			.append("	from game_schedule gs	")
			.append("	join stadium s ")
			.append("	on gs.stadium_id = s.stadium_id ")
			.append("	join team h ")
			.append("	on h.team_id = gs.team_home ")
			.append("	join team o ")
			.append("	on o.team_id = gs.team_other ")
			.append("	where gs.game_schedule_id = ? ");
			
			pstmt=con.prepareStatement(selectGameInfo.toString());
				pstmt.setInt(1, gameScheduleCode);
				rs=pstmt.executeQuery();
				
			if(rs.next()) {
				rpDTO=new ReservationPageDTO();
				rpDTO.setStadiumImg(rs.getString("stadium_Img"));
				rpDTO.setTeamHomeName(rs.getString("home_name"));
				rpDTO.setTeamOtherName(rs.getString("other_name"));
				rpDTO.setTeamHomeImg(rs.getString("home_img"));
				rpDTO.setTeamOtherImg(rs.getString("other_img"));
				rpDTO.setGameDate(rs.getDate("game_date"));
				rpDTO.setGameStartTime(rs.getString("game_start_time"));
				rpDTO.setStadiumCode(rs.getInt("stadium_code"));
			} else {
				System.out.println("경기 정보 없음 " + gameScheduleCode);
			}
		} finally {
			udbc.close(rs, pstmt, con);
		}
		
		return rpDTO;
	}
	
	//잔여좌석보여주기
	public List<ReservationPageDTO> selectRemainingSeat(int stadiumCode) throws SQLException {
		List<ReservationPageDTO> remainingSeatList = new ArrayList<ReservationPageDTO>();
		ReservationPageDTO rpDTO=null;

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

		
		
		try {
			con=udbc.getConnection();
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	select ss.stadium_id,	")
			.append("	sum(ss.stadium_seat_count)	")
			.append("	-nvl(sum(rd.reservation_quantity),0) as remain_seat	")
			.append("	from stadium_seat ss	")
			.append("	left join reservation_detail rd	")
			.append("	on ss.stadium_seat_id=rd.stadium_seat_id	")
			.append("	where ss.stadium_id = ?	")
			.append("	group by ss.stadium_id	");
			
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, stadiumCode);
				rs=pstmt.executeQuery();
				
			while(rs.next()) {
				rpDTO=new ReservationPageDTO();
				rpDTO.setRemainSeatNum(rs.getInt("remain_seat"));
				
				remainingSeatList.add(rpDTO);
			}
		} finally {
			udbc.close(rs, pstmt, con);
		}    

	    return remainingSeatList;
	}
	
	//구장별 좌석 가격
	public List<ReservationPageDTO> selectSeatPrice(int stadiumCode) throws SQLException {
		List<ReservationPageDTO> list=new ArrayList<ReservationPageDTO>();
		ReservationPageDTO rpDTO=null;

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

		
		
		try {
			con=udbc.getConnection();
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	select seat_name, adult_seat_price, youth_seat_price,child_seat_price	")
			.append("	from stadium_seat	")
			.append("	where stadium_id = ?	");

			
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, stadiumCode);
				rs=pstmt.executeQuery();
				
			while(rs.next()) {
				rpDTO=new ReservationPageDTO();
				rpDTO.setSeatName(rs.getString("seat_name"));
				rpDTO.setAdultSeatPrice(rs.getInt("adult_seat_price"));
				rpDTO.setYouthSeatPrice(rs.getInt("youth_seat_price"));
				rpDTO.setChildSeatPrice(rs.getInt("child_seat_price"));
				
				list.add(rpDTO);
				
			}
		} finally {
			udbc.close(rs, pstmt, con);
		}    

		return list;
	}
	
	//주문자정보
	public ReservationPageDTO selectOrderMemberInfo(String memberCode) throws SQLException {
		
		Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    ReservationPageDTO rpDTO = null;

	    try {
	        con = udbc.getConnection();

	        String sql =
	            "SELECT member_name, member_tel, member_email " +
	            "FROM member " +
	            "WHERE member_code = ?";

	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, memberCode);

	        rs = pstmt.executeQuery();

	        if(rs.next()) {
	            rpDTO = new ReservationPageDTO();

	            rpDTO.setMemberName(rs.getString("member_name"));
	            rpDTO.setMemberPhone(rs.getString("member_tel"));
	            rpDTO.setMemberEmail(rs.getString("member_email"));
	        }

	    } catch(SQLException e) {
	        e.printStackTrace();
	    } finally {
	        udbc.close(rs, pstmt, con);
	    }

	    return rpDTO;
		
	}
	
	//주문자정보수정시 데이터베이스에 들어갈 주문자 정보
	public int updateOrderMemberInfo(ReservationPageDTO rpDTO) throws SQLException {
		int rowCnt=0;
		
		Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
			con=udbc.getConnection();
			
			StringBuilder sql=new StringBuilder();
			sql
			.append("	update member	")
			.append("	set name=?,email=?,phone=?	")
			.append("	where member_id = ?	");
			
			pstmt=con.prepareStatement(sql.toString());
			
			pstmt.setString(1,rpDTO.getMemberName());
			pstmt.setString(2,rpDTO.getMemberEmail());
			pstmt.setString(3,rpDTO.getMemberPhone());
			pstmt.setString(4,rpDTO.getMemberCode());
			
			rowCnt=pstmt.executeUpdate();
		} finally {
			udbc.close(rs, pstmt, con);
		}
		return rowCnt;
	}
}

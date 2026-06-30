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
	
	
	public int getReservationSeq(Connection con) throws SQLException {
		int reservationId = 0;

	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT reservation_seq.nextval FROM dual";

	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            reservationId = rs.getInt(1);
	        }
	    } finally {
	        udbc.close(rs, pstmt, null);
	    }

	    return reservationId;
	}
	
	//예매 추가
	public int insertReservation(Connection con, ReservationPageDTO rpDTO) throws SQLException {
		int rowCnt=0;
		PreparedStatement pstmt=null;
		
		try {
			String sql =
				    "INSERT INTO reservation(" +
				    "reservation_id, member_id, admin_id, reservation_date," +
				    "total_price, discount_price, pay_price, cancel_price," +
				    "reservation_status, pay_state, game_schedule_id) " +
				    "VALUES(?, ?, 1, SYSDATE, ?, ?, ?, 0, '구매', '구매', ?)";
			
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, rpDTO.getReservationCode());
			pstmt.setString(2, rpDTO.getMemberCode());
			pstmt.setInt(3, rpDTO.getTotalPrice());
			pstmt.setInt(4, rpDTO.getDiscountPrice());
			pstmt.setInt(5, rpDTO.getPayPrice());
			pstmt.setInt(6, rpDTO.getGameScheduleCode());
			
			rowCnt = pstmt.executeUpdate();
			
			
		}finally {
			udbc.close(null, pstmt, null);
		}
		
		
		return rowCnt;
	}//insertReservation
	
	//예매 추가 디테일
	public int insertReservationDetail(Connection con,ReservationPageDTO rpDTO) throws SQLException {
		int rowCnt=0;
		
		PreparedStatement pstmt=null;
		
		try {
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
			udbc.close(null, pstmt, null);
			
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
			.append("	select gs.game_schedule_id as game_schedule_code, s.stadium_seat_img as stadium_Img,	")
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
				rpDTO.setGameScheduleCode(rs.getInt("game_schedule_code"));
				rpDTO.setStadiumImg(rs.getString("stadium_Img"));
				rpDTO.setTeamHomeName(rs.getString("home_name"));
				rpDTO.setTeamOtherName(rs.getString("other_name"));
				rpDTO.setTeamHomeImg(rs.getString("home_img"));
				rpDTO.setTeamOtherImg(rs.getString("other_img"));
				rpDTO.setGameDate(rs.getDate("game_date"));
				rpDTO.setGameStartTime(rs.getString("game_start_time"));
				rpDTO.setStadiumCode(rs.getInt("stadium_code"));
				System.out.println(rpDTO.getStadiumSeatCode());
			} else {
				System.out.println("경기 정보 없음 " + gameScheduleCode);
			}
		} finally {
			udbc.close(rs, pstmt, con);
		}
		
		return rpDTO;
	}
	
	// 잔여 좌석 보여주기
	public List<ReservationPageDTO> selectRemainingSeat(int stadiumCode) throws SQLException {
		List<ReservationPageDTO> list=new ArrayList<ReservationPageDTO>();
	    ReservationPageDTO rpDTO = null;

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        con = udbc.getConnection();
	        StringBuilder sql = new StringBuilder();
	        
	        // 예약 테이블을 먼저 서브쿼리로 축약한 후 JOIN하여 뻥튀기 방지
	        sql
	        .append("	select stadium_seat_id, seat_name, stadium_seat_count	")
	        .append("	from stadium_seat	")
	        .append("	where stadium_id = ?	");

	        pstmt = con.prepareStatement(sql.toString());
	        // 서브쿼리 때문에 ?(파라미터)가 2개 필요합니다.
	        pstmt.setInt(1, stadiumCode);
	        
	        rs = pstmt.executeQuery();
	            
	        // 결과가 1건 이하이므로 if문 처리
	        while (rs.next()) {
	        	rpDTO=new ReservationPageDTO();
	        	
	        	rpDTO.setStadiumSeatCode(rs.getInt("stadium_seat_id"));
	        	rpDTO.setSeatName(rs.getString("seat_name"));
	        	rpDTO.setRemainSeatNum(rs.getInt("stadium_seat_count"));
	        	
	        	
	        	list.add(rpDTO);
	        }
	    } finally {
	        udbc.close(rs, pstmt, con);
	    }    

	    return list;
	}
	
	//구장별 좌석 가격
	public ReservationPageDTO selectSeatPrice(int stadiumCode) throws SQLException {
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
				
				
			}
		} finally {
			udbc.close(rs, pstmt, con);
		}    

		return rpDTO;
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
	            "SELECT name, phone, email " +
	            "FROM member " +
	            "WHERE member_id = ?";

	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, memberCode);

	        rs = pstmt.executeQuery();

	        if(rs.next()) {
	            rpDTO = new ReservationPageDTO();

	            rpDTO.setMemberName(rs.getString("name"));
	            rpDTO.setMemberPhone(rs.getString("phone"));
	            rpDTO.setMemberEmail(rs.getString("email"));
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
	
	// 좌석 코드(ID)로 좌석 이름(예: 1루 자유석) 조회
	public String selectSeatName(int stadiumSeatCode) throws SQLException {
	    String seatName = "";
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        con = udbc.getConnection();
	        // stadium_seat 테이블에서 ID로 좌석 이름을 가져오는 쿼리
	        String sql = "SELECT seat_name FROM stadium_seat WHERE stadium_seat_id = ?";
	        
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, stadiumSeatCode);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            seatName = rs.getString("seat_name");
	        }
	    } finally {
	        udbc.close(rs, pstmt, con);
	    }
	    return seatName;
	}
	
	
}

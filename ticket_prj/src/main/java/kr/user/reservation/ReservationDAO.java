package kr.user.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.user.common.UserDBConnection;

public class ReservationDAO {
	
	private static ReservationDAO rDAO;
	private UserDBConnection udbc=UserDBConnection.getInstance();
	
	private ReservationDAO() {
		
	}
	
	public static ReservationDAO getInstance() {
		if(rDAO==null) {
			rDAO=new ReservationDAO();
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
	public int insertReservation(Connection con, ReservationDTO rpDTO) throws SQLException {
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
	public int insertReservationDetail(Connection con,ReservationDTO rpDTO) throws SQLException {
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
	
	// 좌석 차감
	public int updateSeatCount(Connection con, ReservationDTO rpDTO) throws SQLException {

	    int rowCnt = 0;

	    PreparedStatement pstmt = null;

	    try {

	        String sql =
	            "UPDATE stadium_seat " +
	            "SET stadium_seat_count = stadium_seat_count - ? " +
	            "WHERE stadium_seat_id = ? " +
	            "AND stadium_seat_count >= ?";


	        pstmt = con.prepareStatement(sql);

	        pstmt.setInt(1, rpDTO.getReservationQuantity());
	        pstmt.setInt(2, rpDTO.getStadiumSeatCode());
	        pstmt.setInt(3, rpDTO.getReservationQuantity());


	        rowCnt = pstmt.executeUpdate();


	    } finally {
	        udbc.close(null, pstmt, null);
	    }


	    return rowCnt;
	}

	
	//예매창에서 보여줄 경기 구장 이미지 및 경기 일자
	public ReservationDTO selectGame(int gameScheduleCode) throws SQLException {
		
		ReservationDTO rpDTO=null;
		
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
				rpDTO=new ReservationDTO();
				rpDTO.setGameScheduleCode(rs.getInt("game_schedule_code"));
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
	
	// 잔여 좌석 및 요금 조회 [수정: adult_seat_price, youth_seat_price, child_seat_price 정보를 쿼리에 추가 및 바인딩 완료]
		public List<ReservationDTO> selectRemainingSeat(int stadiumCode) throws SQLException {
			List<ReservationDTO> list=new ArrayList<ReservationDTO>();
		    ReservationDTO rpDTO = null;

		    Connection con = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;

		    try {
		        con = udbc.getConnection();
		        StringBuilder sql = new StringBuilder();
		        
		        // [수정] adult_seat_price, youth_seat_price, child_seat_price 컬럼을 함께 조회하도록 쿼리를 보완했습니다
		        sql
		        .append("	select stadium_seat_id, seat_name, stadium_seat_count, adult_seat_price, youth_seat_price, child_seat_price	")
		        .append("	from stadium_seat	")
		        .append("	where stadium_id = ?	");

		        pstmt = con.prepareStatement(sql.toString());
		        pstmt.setInt(1, stadiumCode);
		        
		        rs = pstmt.executeQuery();
		            
		        
		        while (rs.next()) {
		        	rpDTO=new ReservationDTO();
		        	
		        	rpDTO.setStadiumSeatCode(rs.getInt("stadium_seat_id"));
		        	rpDTO.setSeatName(rs.getString("seat_name"));
		        	rpDTO.setRemainSeatNum(rs.getInt("stadium_seat_count"));
		        	
		        	// [추가] 조회해 온 단가 정보를 DTO 객체에 세팅하여 JSP의 EL이 정상 인식하도록 처리합니다
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
	public ReservationDTO selectOrderMemberInfo(String memberCode) throws SQLException {
		
		Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    ReservationDTO rpDTO = null;

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
	            rpDTO = new ReservationDTO();

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
	
	
	//좌석 이름 조회
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
	
	//이벤트 할인 조회
		public List<ReservationDTO> selectCoupon(String memberCode) throws SQLException{
			List<ReservationDTO> couponList=new ArrayList<ReservationDTO>();
			ReservationDTO rpDTO=null;
			
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			
			try {
				con=udbc.getConnection();
				StringBuilder selectCoupon=new StringBuilder();
				selectCoupon
				.append("	select cp.coupon_code as couponCode, cp.coupon_discount_rate as discount	")
				.append("	from coupon cp ")
				.append("	join custody_coupon ctcp ")
				.append("	on cp.coupon_code=ctcp.coupon_code ")
				.append("	where ctcp.member_id=? ")
				.append("	and ctcp.coupon_state='사용가능' ");
				
				pstmt=con.prepareStatement(selectCoupon.toString());
					pstmt.setString(1, memberCode);
					rs=pstmt.executeQuery();
					
				while(rs.next()) {
					rpDTO=new ReservationDTO();
					rpDTO.setCouponCode(rs.getString("couponCode"));
					rpDTO.setCouponDiscountRate(rs.getInt("discount"));
					
					couponList.add(rpDTO);
				}
			} finally {
				 udbc.close(rs, pstmt, con);
			}
			return couponList;
		}
	//쿠폰 사용 완료후 상태값 변경
	public void updateCouponState(String memberCode, String couponCode) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			con=udbc.getConnection();
			StringBuilder updateCoupon=new StringBuilder();
			updateCoupon
			.append("	update custody_coupon	")
			.append("	set coupon_state='사용완료' ")
			.append("	where member_id=? ")
			.append("	and coupon_code=? ")
			.append("	and coupon_state='사용가능' ");
			
			pstmt=con.prepareStatement(updateCoupon.toString());
			pstmt.setString(1, memberCode);
			pstmt.setString(2, couponCode);
			pstmt.executeUpdate();
				
		} finally {
			 udbc.close(rs, pstmt, con);
		}
	}
		
	//좌석 가격 조회 메서드
	public int selectSeatPrice(int stadiumSeatCode, String reservationType) throws SQLException {

	    int price = 0;

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {

	        con = udbc.getConnection();

	        String sql =
	            "SELECT adult_seat_price, youth_seat_price, child_seat_price " +
	            "FROM stadium_seat " +
	            "WHERE stadium_seat_id=?";

	        pstmt = con.prepareStatement(sql);

	        pstmt.setInt(1, stadiumSeatCode);

	        rs = pstmt.executeQuery();

	        if(rs.next()){
	            if("성인".equals(reservationType)){
	                price = rs.getInt("adult_seat_price");
	            }else if("청소년".equals(reservationType)){
	                price = rs.getInt("youth_seat_price");
	            }else{
	                price = rs.getInt("child_seat_price");
	            }
	        }

	    } finally {
	        udbc.close(rs, pstmt, con);
	    }

	    return price;
	}
	
}

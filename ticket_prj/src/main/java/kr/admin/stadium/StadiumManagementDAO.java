package kr.admin.stadium;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import common.StadiumOptionDTO;

public class StadiumManagementDAO {
	
	 private DataSource ds;

	    public StadiumManagementDAO() {

	        try {
	            Context ctx = new InitialContext();

	            ds = (DataSource) ctx.lookup(
	                    "java:/comp/env/jdbc/dbcp");

	            System.out.println("DataSource = " + ds);

	        } catch (Exception e) {
	            throw new RuntimeException(
	                    "JNDI 조회 실패", e);
	        }
	    }
	
	    public List<StadiumListDTO> selectStadiumList() {

	        List<StadiumListDTO> list = new ArrayList<>();

	        StringBuilder sql = new StringBuilder();

	        sql.append(" SELECT s.stadium_id, ");
	        sql.append("        s.stadium_name, ");
	        sql.append("        s.stadium_region, ");
	        sql.append("        NVL(SUM(ss.stadium_seat_count), 0) total_seats, ");
	        sql.append("        t.team_id, ");
	        sql.append("        t.team_name ");
	        sql.append("   FROM stadium s ");

	        sql.append("   LEFT JOIN stadium_home sh ");
	        sql.append("     ON s.stadium_id = sh.stadium_id ");

	        sql.append("   LEFT JOIN team t ");
	        sql.append("     ON sh.team_id = t.team_id ");

	        sql.append("   LEFT JOIN stadium_seat ss ");
	        sql.append("     ON s.stadium_id = ss.stadium_id ");

	        sql.append("  GROUP BY s.stadium_id, ");
	        sql.append("           s.stadium_name, ");
	        sql.append("           s.stadium_region, ");
	        sql.append("           t.team_id, ");
	        sql.append("           t.team_name ");

	        sql.append("  ORDER BY s.stadium_id ");

	        try (
	            Connection con = ds.getConnection();
	            PreparedStatement pstmt =
	                    con.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery()
	        ) {

	            while (rs.next()) {

	                StadiumListDTO dto = new StadiumListDTO();

	                dto.setStadiumCode(
	                        rs.getInt("stadium_id"));

	                dto.setStadiumName(
	                        rs.getString("stadium_name"));

	                dto.setStadiumLocation(
	                        rs.getString("stadium_region"));

	                dto.setTotalSeats(
	                        rs.getInt("total_seats"));

	                dto.setHomeTeamCode(
	                        rs.getInt("team_id"));

	                dto.setHomeTeamName(
	                        rs.getString("team_name"));

	                list.add(dto);

	                System.out.println(dto);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        System.out.println("구장 조회 건수 : " + list.size());

	        return list;
	    }
	
	    public StadiumDetailDTO selectStadiumDetail(int stadiumCode) {

	        StadiumDetailDTO dto = null;

	        StringBuilder sql = new StringBuilder();

	        sql.append(" SELECT s.stadium_id, ");
	        sql.append("        s.stadium_name, ");
	        sql.append("        s.stadium_region, ");
	        sql.append("        s.stadium_addr, ");
	        sql.append("        t.team_id, ");
	        sql.append("        t.team_name ");
	        sql.append("   FROM stadium s ");
	        sql.append("   LEFT JOIN stadium_home sh ");
	        sql.append("     ON s.stadium_id = sh.stadium_id ");
	        sql.append("   LEFT JOIN team t ");
	        sql.append("     ON sh.team_id = t.team_id ");
	        sql.append("  WHERE s.stadium_id = ? ");

	        try (
	            Connection con = ds.getConnection();
	            PreparedStatement pstmt =
	                    con.prepareStatement(sql.toString())
	        ) {

	            pstmt.setInt(1, stadiumCode);

	            try (ResultSet rs = pstmt.executeQuery()) {

	                if (rs.next()) {

	                    dto = new StadiumDetailDTO();

	                    dto.setStadiumCode(
	                            rs.getInt("stadium_id"));

	                    dto.setStadiumName(
	                            rs.getString("stadium_name"));

	                    dto.setStadiumLocation(
	                            rs.getString("stadium_region"));

	                    dto.setAddress(
	                            rs.getString("stadium_addr"));

	                    dto.setHomeTeamCode(
	                            rs.getInt("team_id"));

	                    dto.setHomeTeamName(
	                            rs.getString("team_name"));

	                    System.out.println(dto);
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return dto;
	    }
	
	    public List<StadiumSeatDTO> selectStadiumSeat(int stadiumCode) {

	        List<StadiumSeatDTO> list = new ArrayList<>();

	        StringBuilder sql = new StringBuilder();

	        sql.append(" SELECT ss.stadium_seat_id, ");
	        sql.append("        ss.stadium_id, ");
	        sql.append("        s.stadium_name, ");
	        sql.append("        ss.seat_name, ");
	        sql.append("        ss.adult_seat_price, ");
	        sql.append("        ss.youth_seat_price, ");
	        sql.append("        ss.child_seat_price, ");
	        sql.append("        ss.stadium_seat_count ");
	        sql.append("   FROM stadium_seat ss ");
	        sql.append("   INNER JOIN stadium s ");
	        sql.append("     ON ss.stadium_id = s.stadium_id ");
	        sql.append("  WHERE ss.stadium_id = ? ");
	        sql.append("  ORDER BY ss.stadium_seat_id ");

	        try (
	            Connection con = ds.getConnection();
	            PreparedStatement pstmt =
	                    con.prepareStatement(sql.toString())
	        ) {

	            pstmt.setInt(1, stadiumCode);

	            try (ResultSet rs = pstmt.executeQuery()) {

	                while (rs.next()) {

	                    StadiumSeatDTO dto = new StadiumSeatDTO();

	                    dto.setSeatCode(
	                            rs.getInt("stadium_seat_id"));

	                    dto.setStadiumCode(
	                            rs.getInt("stadium_id"));

	                    dto.setStadiumName(
	                            rs.getString("stadium_name"));

	                    dto.setSeatName(
	                            rs.getString("seat_name"));

	                    dto.setAdultPrice(
	                            rs.getInt("adult_seat_price"));

	                    dto.setYouthPrice(
	                            rs.getInt("youth_seat_price"));

	                    dto.setChildPrice(
	                            rs.getInt("child_seat_price"));

	                    dto.setSeatQty(
	                            rs.getInt("stadium_seat_count"));

	                    list.add(dto);

	                    System.out.println(dto);
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        System.out.println("좌석 조회 건수 : " + list.size());

	        return list;
	    }
	
	public void insertStadium(StadiumDetailDTO stadium) {
		
	}
	
	public int updateStadium(StadiumDetailDTO stadium) {
		return 0;
	}
	
	public void insertStadiumSeat(StadiumSeatDTO seat) {
		
	}
	
	public int deleteStadium(int stadiumCode) {
		return stadiumCode;
	}
	
	public int deleteStadiumSeat(int stadiumSeatCode) {
		return stadiumSeatCode;
	}
	
	public List<StadiumOptionDTO> selectTeamOptions() {
		return null;
	}
}

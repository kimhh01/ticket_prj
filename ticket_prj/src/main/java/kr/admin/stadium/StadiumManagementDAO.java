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

import kr.admin.common.AdminDBConnection;
import kr.admin.common.StadiumOptionDTO;

public class StadiumManagementDAO {
	
	    public List<StadiumListDTO> selectStadiumList() {

	        List<StadiumListDTO> list = new ArrayList<>();

	        StringBuilder sql = new StringBuilder();

	        sql.append(" SELECT s.stadium_id, ");
	        sql.append("        s.stadium_name, ");
	        sql.append("        s.stadium_region, ");
	        sql.append("        NVL(seat.total_seats, 0) AS total_seats, ");
	        sql.append("        team.team_name ");
	        sql.append("   FROM stadium s ");

	        sql.append("   LEFT JOIN ( ");
	        sql.append("        SELECT stadium_id, ");
	        sql.append("               SUM(stadium_seat_count) AS total_seats ");
	        sql.append("          FROM stadium_seat ");
	        sql.append("         GROUP BY stadium_id ");
	        sql.append("   ) seat ");
	        sql.append("     ON s.stadium_id = seat.stadium_id ");

	        sql.append("   LEFT JOIN ( ");
	        sql.append("        SELECT sh.stadium_id, ");
	        sql.append("               LISTAGG(t.team_name, ', ') ");
	        sql.append("                   WITHIN GROUP (ORDER BY t.team_id) AS team_name ");
	        sql.append("          FROM stadium_home sh ");
	        sql.append("          LEFT JOIN team t ");
	        sql.append("            ON sh.team_id = t.team_id ");
	        sql.append("         GROUP BY sh.stadium_id ");
	        sql.append("   ) team ");
	        sql.append("     ON s.stadium_id = team.stadium_id ");

	        sql.append("  ORDER BY s.stadium_id ");

	        try (
	        	Connection con = AdminDBConnection.getInstance().getConnection();
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

	                dto.setHomeTeamCode(0);

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
	        sql.append("        s.stadium_seat_img, ");
	        sql.append("        t.team_id, ");
	        sql.append("        t.team_name ");
	        sql.append("   FROM stadium s ");
	        sql.append("   LEFT JOIN stadium_home sh ");
	        sql.append("     ON s.stadium_id = sh.stadium_id ");
	        sql.append("   LEFT JOIN team t ");
	        sql.append("     ON sh.team_id = t.team_id ");
	        sql.append("  WHERE s.stadium_id = ? ");
	        sql.append("  ORDER BY t.team_id ");

	        try (
	            Connection con = AdminDBConnection.getInstance().getConnection();
	            PreparedStatement pstmt = con.prepareStatement(sql.toString())
	        ) {

	            pstmt.setInt(1, stadiumCode);

	            try (ResultSet rs = pstmt.executeQuery()) {

	                int homeTeamCount = 0;

	                while (rs.next()) {

	                    if (dto == null) {

	                        dto = new StadiumDetailDTO();

	                        dto.setStadiumCode(rs.getInt("stadium_id"));
	                        dto.setStadiumName(rs.getString("stadium_name"));
	                        dto.setStadiumLocation(rs.getString("stadium_region"));
	                        dto.setAddress(rs.getString("stadium_addr"));
	                        dto.setStadiumSeatImg(rs.getString("stadium_seat_img"));
	                    }

	                    int teamId = rs.getInt("team_id");

	                    if (!rs.wasNull()) {

	                        if (homeTeamCount == 0) {

	                            dto.setHomeTeamCode(teamId);
	                            dto.setHomeTeamName(rs.getString("team_name"));

	                        } else if (homeTeamCount == 1) {

	                            dto.setHomeTeamCode2(teamId);
	                        }

	                        homeTeamCount++;
	                    }
	                }
	            }

	        } catch (Exception e) {
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
	        	Connection con = AdminDBConnection.getInstance().getConnection();
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

	        StringBuilder stadiumSql = new StringBuilder();

	        stadiumSql.append(" INSERT INTO stadium ( ");
	        stadiumSql.append("        stadium_id, ");
	        stadiumSql.append("        stadium_name, ");
	        stadiumSql.append("        stadium_region, ");
	        stadiumSql.append("        stadium_addr, ");
	        stadiumSql.append("        stadium_seat_img ");
	        stadiumSql.append(" ) VALUES ( ");
	        stadiumSql.append("        ?, ?, ?, ?, ? ");
	        stadiumSql.append(" ) ");

	        Connection con = null;
	        PreparedStatement stadiumPstmt = null;

	        try {

	            con = AdminDBConnection.getInstance().getConnection();
	            con.setAutoCommit(false);

	            int stadiumId = selectNextStadiumId(con);

	            stadiumPstmt = con.prepareStatement(stadiumSql.toString());

	            stadiumPstmt.setInt(1, stadiumId);
	            stadiumPstmt.setString(2, stadium.getStadiumName());
	            stadiumPstmt.setString(3, stadium.getStadiumLocation());
	            stadiumPstmt.setString(4, stadium.getAddress());
	            stadiumPstmt.setString(5, stadium.getStadiumSeatImg());

	            int stadiumResult = stadiumPstmt.executeUpdate();

	            if (stadiumResult <= 0) {
	                con.rollback();
	                return;
	            }

	            /*
	             * 홈팀 1은 필수
	             */
	            insertStadiumHome(
	                    con,
	                    stadiumId,
	                    stadium.getHomeTeamCode());

	            /*
	             * 홈팀 2는 선택
	             */
	            if (stadium.getHomeTeamCode2() > 0) {

	                insertStadiumHome(
	                        con,
	                        stadiumId,
	                        stadium.getHomeTeamCode2());
	            }

	            con.commit();

	        } catch (Exception e) {

	            try {
	                if (con != null) {
	                    con.rollback();
	                }
	            } catch (Exception rollbackException) {
	                rollbackException.printStackTrace();
	            }

	            e.printStackTrace();

	        } finally {

	            try {
	                if (stadiumPstmt != null) {
	                    stadiumPstmt.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	            try {
	                if (con != null) {
	                    con.setAutoCommit(true);
	                    con.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
	    public int updateStadium(StadiumDetailDTO stadium) {

	        int result = 0;

	        StringBuilder stadiumSql = new StringBuilder();

	        stadiumSql.append(" UPDATE stadium ");
	        stadiumSql.append("    SET stadium_name = ?, ");
	        stadiumSql.append("        stadium_region = ?, ");
	        stadiumSql.append("        stadium_addr = ?, ");
	        stadiumSql.append("        stadium_seat_img = ? ");
	        stadiumSql.append("  WHERE stadium_id = ? ");

	        StringBuilder deleteHomeSql = new StringBuilder();

	        deleteHomeSql.append(" DELETE FROM stadium_home ");
	        deleteHomeSql.append("  WHERE stadium_id = ? ");

	        Connection con = null;
	        PreparedStatement stadiumPstmt = null;
	        PreparedStatement deleteHomePstmt = null;

	        try {

	            con = AdminDBConnection.getInstance().getConnection();
	            con.setAutoCommit(false);

	            System.out.println("===== DAO updateStadium 진입 =====");
	            System.out.println("stadiumCode = " + stadium.getStadiumCode());
	            System.out.println("homeTeamCode = " + stadium.getHomeTeamCode());
	            System.out.println("homeTeamCode2 = " + stadium.getHomeTeamCode2());

	            stadiumPstmt =
	                    con.prepareStatement(stadiumSql.toString());

	            stadiumPstmt.setString(1, stadium.getStadiumName());
	            stadiumPstmt.setString(2, stadium.getStadiumLocation());
	            stadiumPstmt.setString(3, stadium.getAddress());
	            stadiumPstmt.setString(4, stadium.getStadiumSeatImg());
	            stadiumPstmt.setInt(5, stadium.getStadiumCode());

	            int stadiumResult =
	                    stadiumPstmt.executeUpdate();

	            System.out.println("stadium update result = " + stadiumResult);

	            if (stadiumResult <= 0) {
	                con.rollback();
	                return 0;
	            }

	            deleteHomePstmt =
	                    con.prepareStatement(deleteHomeSql.toString());

	            deleteHomePstmt.setInt(1, stadium.getStadiumCode());

	            int deleteHomeResult =
	                    deleteHomePstmt.executeUpdate();

	            System.out.println("stadium_home delete result = " + deleteHomeResult);

	            insertStadiumHome(
	                    con,
	                    stadium.getStadiumCode(),
	                    stadium.getHomeTeamCode());

	            if (stadium.getHomeTeamCode2() > 0) {

	                insertStadiumHome(
	                        con,
	                        stadium.getStadiumCode(),
	                        stadium.getHomeTeamCode2());
	            }

	            con.commit();
	            result = 1;

	        } catch (Exception e) {

	            try {
	                if (con != null) {
	                    con.rollback();
	                }
	            } catch (Exception rollbackException) {
	                rollbackException.printStackTrace();
	            }

	            e.printStackTrace();
	            result = 0;

	        } finally {

	            try {
	                if (deleteHomePstmt != null) {
	                    deleteHomePstmt.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	            try {
	                if (stadiumPstmt != null) {
	                    stadiumPstmt.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	            try {
	                if (con != null) {
	                    con.setAutoCommit(true);
	                    con.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        return result;
	    }
	
	    public void insertStadiumSeat(StadiumSeatDTO seat) {

	        StringBuilder sql = new StringBuilder();

	        sql.append(" INSERT INTO stadium_seat ( ");
	        sql.append("        stadium_seat_id, ");
	        sql.append("        stadium_id, ");
	        sql.append("        seat_name, ");
	        sql.append("        adult_seat_price, ");
	        sql.append("        youth_seat_price, ");
	        sql.append("        child_seat_price, ");
	        sql.append("        stadium_seat_count ");
	        sql.append(" ) VALUES ( ");
	        sql.append("        ?, ?, ?, ?, ?, ?, ? ");
	        sql.append(" ) ");

	        try (
	        	Connection con = AdminDBConnection.getInstance().getConnection();
	            PreparedStatement seqPstmt =
	                    con.prepareStatement(
	                            " SELECT NVL(MAX(stadium_seat_id), 0) + 1 AS next_id FROM stadium_seat ");
	            ResultSet rs = seqPstmt.executeQuery();
	            PreparedStatement pstmt =
	                    con.prepareStatement(sql.toString())
	        ) {

	            int seatId = seat.getSeatCode();

	            if (seatId <= 0 && rs.next()) {
	                seatId = rs.getInt("next_id");
	            }

	            pstmt.setInt(1, seatId);
	            pstmt.setInt(2, seat.getStadiumCode());
	            pstmt.setString(3, seat.getSeatName());
	            pstmt.setInt(4, seat.getAdultPrice());
	            pstmt.setInt(5, seat.getYouthPrice());
	            pstmt.setInt(6, seat.getChildPrice());
	            pstmt.setInt(7, seat.getSeatQty());

	            pstmt.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public int updateStadiumSeat(StadiumSeatDTO seat) {

	        int result = 0;

	        StringBuilder sql = new StringBuilder();

	        sql.append(" UPDATE stadium_seat ");
	        sql.append("    SET seat_name = ?, ");
	        sql.append("        adult_seat_price = ?, ");
	        sql.append("        youth_seat_price = ?, ");
	        sql.append("        child_seat_price = ?, ");
	        sql.append("        stadium_seat_count = ? ");
	        sql.append("  WHERE stadium_seat_id = ? ");
	        sql.append("    AND stadium_id = ? ");

	        try (
	        	Connection con = AdminDBConnection.getInstance().getConnection();
	            PreparedStatement pstmt =
	                    con.prepareStatement(sql.toString())
	        ) {

	            pstmt.setString(1, seat.getSeatName());
	            pstmt.setInt(2, seat.getAdultPrice());
	            pstmt.setInt(3, seat.getYouthPrice());
	            pstmt.setInt(4, seat.getChildPrice());
	            pstmt.setInt(5, seat.getSeatQty());
	            pstmt.setInt(6, seat.getSeatCode());
	            pstmt.setInt(7, seat.getStadiumCode());

	            result = pstmt.executeUpdate();

	            System.out.println("좌석 수정 결과 : " + result);

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return result;
	    }
	
	    public int deleteStadium(int stadiumCode) {

	        int result = 0;

	        StringBuilder deleteSeatSql = new StringBuilder();

	        deleteSeatSql.append(" DELETE FROM stadium_seat ");
	        deleteSeatSql.append("  WHERE stadium_id = ? ");

	        StringBuilder deleteHomeSql = new StringBuilder();

	        deleteHomeSql.append(" DELETE FROM stadium_home ");
	        deleteHomeSql.append("  WHERE stadium_id = ? ");

	        StringBuilder deleteStadiumSql = new StringBuilder();

	        deleteStadiumSql.append(" DELETE FROM stadium ");
	        deleteStadiumSql.append("  WHERE stadium_id = ? ");

	        Connection con = null;
	        PreparedStatement seatPstmt = null;
	        PreparedStatement homePstmt = null;
	        PreparedStatement stadiumPstmt = null;

	        try {

	            con = AdminDBConnection.getInstance().getConnection();
	            con.setAutoCommit(false);

	            seatPstmt = con.prepareStatement(deleteSeatSql.toString());
	            seatPstmt.setInt(1, stadiumCode);
	            seatPstmt.executeUpdate();

	            homePstmt = con.prepareStatement(deleteHomeSql.toString());
	            homePstmt.setInt(1, stadiumCode);
	            homePstmt.executeUpdate();

	            stadiumPstmt = con.prepareStatement(deleteStadiumSql.toString());
	            stadiumPstmt.setInt(1, stadiumCode);

	            int stadiumResult = stadiumPstmt.executeUpdate();

	            if (stadiumResult > 0) {
	                con.commit();
	                result = 1;
	            } else {
	                con.rollback();
	            }

	        } catch (Exception e) {

	            try {
	                if (con != null) {
	                    con.rollback();
	                }
	            } catch (Exception rollbackException) {
	                rollbackException.printStackTrace();
	            }

	            e.printStackTrace();

	        } finally {

	            try {
	                if (stadiumPstmt != null) stadiumPstmt.close();
	                if (homePstmt != null) homePstmt.close();
	                if (seatPstmt != null) seatPstmt.close();

	                if (con != null) {
	                    con.setAutoCommit(true);
	                    con.close();
	                }

	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        return result;
	    }
	
	    public int deleteStadiumSeat(int stadiumSeatCode) {

	        int result = 0;

	        StringBuilder sql = new StringBuilder();

	        sql.append(" DELETE FROM stadium_seat ");
	        sql.append("  WHERE stadium_seat_id = ? ");

	        try (
	        	Connection con = AdminDBConnection.getInstance().getConnection();
	            PreparedStatement pstmt =
	                    con.prepareStatement(sql.toString())
	        ) {

	            pstmt.setInt(1, stadiumSeatCode);

	            result = pstmt.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return result;
	    }
	
	    public List<StadiumOptionDTO> selectTeamOptions() {

	        List<StadiumOptionDTO> list = new ArrayList<>();

	        StringBuilder sql = new StringBuilder();

	        sql.append(" SELECT team_id, team_name ");
	        sql.append("   FROM team ");
	        sql.append("  ORDER BY team_id ");

	        try (
	        	Connection con = AdminDBConnection.getInstance().getConnection();
	            PreparedStatement pstmt =
	                    con.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery()
	        ) {

	            while (rs.next()) {

	                StadiumOptionDTO dto = new StadiumOptionDTO();

	                /*
	                 * StadiumOptionDTO를 팀 옵션용으로 재사용.
	                 * StadiumCode  → team_id
	                 * StadiumName  → team_name
	                 */
	                dto.setStadiumCode(rs.getInt("team_id"));
	                dto.setStadiumName(rs.getString("team_name"));

	                list.add(dto);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
	    
	    private int selectNextStadiumId(Connection con) throws SQLException {

	        int stadiumId = 1;

	        String sql = " SELECT NVL(MAX(stadium_id), 0) + 1 AS stadium_id FROM stadium ";

	        try (
	            PreparedStatement pstmt = con.prepareStatement(sql);
	            ResultSet rs = pstmt.executeQuery()
	        ) {

	            if (rs.next()) {
	                stadiumId = rs.getInt("stadium_id");
	            }
	        }

	        return stadiumId;
	    }
	    
		private void insertStadiumHome(Connection con, int stadiumCode, int teamCode) throws Exception {

			if (teamCode <= 0) {
				throw new RuntimeException("홈팀 코드가 없습니다.");
			}

			StringBuilder sql = new StringBuilder();

			sql.append(" INSERT INTO stadium_home ( ");
			sql.append("        stadium_id, ");
			sql.append("        team_id ");
			sql.append(" ) VALUES ( ");
			sql.append("        ?, ? ");
			sql.append(" ) ");

			try (PreparedStatement pstmt = con.prepareStatement(sql.toString())) {

				pstmt.setInt(1, stadiumCode);
				pstmt.setInt(2, teamCode);

				pstmt.executeUpdate();
			}
		}
}

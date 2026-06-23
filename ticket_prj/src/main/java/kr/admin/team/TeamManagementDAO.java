package kr.admin.team;

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
import common.TeamOptionDTO;

public class TeamManagementDAO {
	
    private DataSource ds;

    public TeamManagementDAO() {

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
	
    public List<ScheduleListDTO> selectGameScheduleList() {

        List<ScheduleListDTO> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT gs.game_schedule_id, ");
        sql.append("        TO_CHAR(gs.game_date, 'YYYY-MM-DD') game_date, ");
        sql.append("        gs.game_start_time AS game_time, ");
        sql.append("        th.team_name home_team, ");
        sql.append("        ta.team_name away_team, ");
        sql.append("        st.stadium_name, ");
        sql.append("        gs.sale_state ");
        sql.append(" FROM game_schedule gs ");

        sql.append(" INNER JOIN team th ");
        sql.append(" ON gs.team_home = th.team_id ");

        sql.append(" INNER JOIN team ta ");
        sql.append(" ON gs.team_other = ta.team_id ");

        sql.append(" INNER JOIN stadium st ");
        sql.append(" ON gs.stadium_id = st.stadium_id ");

        sql.append(" ORDER BY gs.game_date DESC ");

        try (
            Connection con = ds.getConnection();
            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {

                ScheduleListDTO dto = new ScheduleListDTO();

                dto.setGameScheduleCode(
                        rs.getInt("game_schedule_id"));

                dto.setGameDate(
                        rs.getString("game_date"));

                dto.setGameTime(
                        rs.getString("game_time"));

                dto.setHomeTeam(
                        rs.getString("home_team"));

                dto.setAwayTeam(
                        rs.getString("away_team"));

                dto.setStadiumName(
                        rs.getString("stadium_name"));

                dto.setSalesState(
                        rs.getString("sale_state"));

                list.add(dto);
                System.out.println(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("조회 건수 : " + list.size());
        
        Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
			System.out.println(
				    "접속 계정 : " +
				    con.getMetaData().getUserName()
				);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return list;
        
    }
	
	public void insertGameSchedule(ScheduleSaveDTO schedule){
		
	}
	
	public int updateGameSchedule(ScheduleSaveDTO schedule) {
		return 0;
	}
	
	public List<TeamInfoDTO> selectTeamList() {

	    List<TeamInfoDTO> list = new ArrayList<>();

	    StringBuilder sql = new StringBuilder();

	    sql.append(" SELECT t.team_id, ");
	    sql.append("        t.team_logo_img, ");
	    sql.append("        t.team_name, ");
	    sql.append("        s.stadium_id, ");
	    sql.append("        s.stadium_name ");
	    sql.append("   FROM team t ");
	    sql.append("  INNER JOIN stadium s ");
	    sql.append("     ON t.team_id = s.stadium_id ");
	    sql.append("  ORDER BY t.team_id ");

	    try (
	        Connection con = ds.getConnection();
	        PreparedStatement pstmt = con.prepareStatement(sql.toString());
	        ResultSet rs = pstmt.executeQuery()
	    ) {

	        while (rs.next()) {

	            TeamInfoDTO dto = new TeamInfoDTO();

	            dto.setTeamCode(rs.getInt("team_id"));
	            dto.setTeamLogoImg(rs.getString("team_logo_img"));
	            dto.setTeamName(rs.getString("team_name"));
	            dto.setStadiumCode(rs.getInt("stadium_id"));
	            dto.setStadiumName(rs.getString("stadium_name"));

	            list.add(dto);

	            System.out.println(dto);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    System.out.println("팀 조회 건수 : " + list.size());

	    return list;
	}
	
	public void insertTeam(TeamSaveDTO team) {
		
	}
	
	public int updateTeam(TeamSaveDTO team) {
		return 0;
	}
	
	public int deleteTeam(int teamCode) {
		return 0;
	}
	
	public List<TeamOptionDTO> selecteTeamOption(){
		
		List<TeamOptionDTO> list = new ArrayList<>();
		return list;
	}
	
	public List<StadiumOptionDTO> selecteStadiumOption(){
		
		List<StadiumOptionDTO> list = new ArrayList<>();
		return list;
	}
}

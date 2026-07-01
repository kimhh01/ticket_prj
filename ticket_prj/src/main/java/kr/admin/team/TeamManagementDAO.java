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

import kr.admin.common.AdminDBConnection;
import kr.admin.common.StadiumOptionDTO;
import kr.admin.common.TeamOptionDTO;

public class TeamManagementDAO {
	
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
        		Connection con = AdminDBConnection.getInstance().getConnection();
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
			con = AdminDBConnection.getInstance().getConnection();
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
    
    public List<ScheduleSaveDTO> selectGameScheduleEditList() {

        List<ScheduleSaveDTO> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT gs.game_schedule_id, ");
        sql.append("        gs.team_home, ");
        sql.append("        gs.team_other, ");
        sql.append("        TO_CHAR(gs.game_date, 'YYYY-MM-DD') AS game_date, ");
        sql.append("        gs.game_start_time, ");
        sql.append("        gs.stadium_id, ");
        sql.append("        th.team_name AS home_team, ");
        sql.append("        ta.team_name AS away_team, ");
        sql.append("        st.stadium_name ");
        sql.append("   FROM game_schedule gs ");

        sql.append("  INNER JOIN team th ");
        sql.append("     ON gs.team_home = th.team_id ");

        sql.append("  INNER JOIN team ta ");
        sql.append("     ON gs.team_other = ta.team_id ");

        sql.append("  INNER JOIN stadium st ");
        sql.append("     ON gs.stadium_id = st.stadium_id ");

        sql.append("  ORDER BY gs.game_date DESC ");

        try (
        	Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {

                ScheduleSaveDTO dto = new ScheduleSaveDTO();

                dto.setGameScheduleCode(
                        rs.getInt("game_schedule_id"));

                dto.setHomeTeamCode(
                        rs.getInt("team_home"));

                dto.setAwayTeamCode(
                        rs.getInt("team_other"));

                dto.setGameDate(
                        rs.getString("game_date"));

                dto.setGameStartTime(
                        rs.getString("game_start_time"));

                dto.setStadiumCode(
                        rs.getInt("stadium_id"));

                dto.setHomeTeam(
                        rs.getString("home_team"));

                dto.setAwayTeam(
                        rs.getString("away_team"));

                dto.setStadiumName(
                        rs.getString("stadium_name"));

                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("일정 수정용 조회 건수 : " + list.size());

        return list;
    }
	
    public void insertGameSchedule(ScheduleSaveDTO schedule) {

        StringBuilder selectSql = new StringBuilder();

        selectSql.append(" SELECT NVL(MAX(game_schedule_id), 0) + 1 AS game_schedule_id ");
        selectSql.append("   FROM game_schedule ");

        StringBuilder sql = new StringBuilder();

        sql.append(" INSERT INTO game_schedule ( ");
        sql.append("        game_schedule_id, ");
        sql.append("        game_date, ");
        sql.append("        game_start_time, ");
        sql.append("        team_home, ");
        sql.append("        team_other, ");
        sql.append("        stadium_id, ");
        sql.append("        sale_state, ");
        sql.append("        match_registration_date ");
        sql.append(" ) VALUES ( ");
        sql.append("        ?, ");
        sql.append("        TO_DATE(?, 'YYYY-MM-DD'), ");
        sql.append("        ?, ");
        sql.append("        ?, ");
        sql.append("        ?, ");
        sql.append("        ?, ");
        sql.append("        ?, ");
        sql.append("        SYSDATE + 7 ");
        sql.append(" ) ");

        Connection con = null;
        PreparedStatement selectPstmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            con = AdminDBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            int gameScheduleId = 0;

            selectPstmt = con.prepareStatement(selectSql.toString());
            rs = selectPstmt.executeQuery();

            if (rs.next()) {
                gameScheduleId = rs.getInt("game_schedule_id");
            }

            pstmt = con.prepareStatement(sql.toString());

            pstmt.setInt(1, gameScheduleId);
            pstmt.setString(2, schedule.getGameDate());
            pstmt.setString(3, schedule.getGameStartTime());
            pstmt.setInt(4, schedule.getHomeTeamCode());
            pstmt.setInt(5, schedule.getAwayTeamCode());
            pstmt.setInt(6, schedule.getStadiumCode());

            /*
             * 신규 경기 일정 기본 판매 상태
             * 실제 DB에서 사용하는 값에 맞게 바꾸면 됨
             */
            pstmt.setString(7, "판매");

            int result = pstmt.executeUpdate();

            if (result > 0) {
                con.commit();
            } else {
                con.rollback();
                throw new RuntimeException("경기 일정 등록 실패");
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

            /*
             * insertGameSchedule()은 void지만
             * Service에서 실패 여부를 알 수 있도록 예외를 다시 던짐
             */
            throw new RuntimeException("경기 일정 등록 중 오류 발생", e);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (selectPstmt != null) {
                    selectPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (pstmt != null) {
                    pstmt.close();
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
	
    public int updateGameSchedule(ScheduleSaveDTO schedule) {

        int result = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE game_schedule ");
        sql.append("    SET game_date = TO_DATE(?, 'YYYY-MM-DD'), ");
        sql.append("        game_start_time = ?, ");
        sql.append("        team_home = ?, ");
        sql.append("        team_other = ?, ");
        sql.append("        stadium_id = ? ");
        sql.append("  WHERE game_schedule_id = ? ");

        try (
        	Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setString(1, schedule.getGameDate());
            pstmt.setString(2, schedule.getGameStartTime());
            pstmt.setInt(3, schedule.getHomeTeamCode());
            pstmt.setInt(4, schedule.getAwayTeamCode());
            pstmt.setInt(5, schedule.getStadiumCode());
            pstmt.setInt(6, schedule.getGameScheduleCode());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
	
    public List<TeamInfoDTO> selectTeamList() {

        List<TeamInfoDTO> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT t.team_id, ");
        sql.append("        t.team_logo_img, ");
        sql.append("        t.team_name, ");
        sql.append("        t.team_short_name, ");
        sql.append("        s.stadium_id, ");
        sql.append("        s.stadium_name ");
        sql.append("   FROM team t ");

        sql.append("   LEFT JOIN stadium_home sh ");
        sql.append("     ON t.team_id = sh.team_id ");

        sql.append("   LEFT JOIN stadium s ");
        sql.append("     ON sh.stadium_id = s.stadium_id ");

        sql.append("  ORDER BY t.team_id ");

        try (
        	Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {

                TeamInfoDTO dto = new TeamInfoDTO();

                dto.setTeamCode(rs.getInt("team_id"));
                dto.setTeamLogoImg(rs.getString("team_logo_img"));
                dto.setTeamName(rs.getString("team_name"));
                dto.setTeamShortName(rs.getString("team_short_name"));
                dto.setStadiumCode(rs.getInt("stadium_id"));
                dto.setStadiumName(rs.getString("stadium_name"));

                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("팀 조회 건수 : " + list.size());

        return list;
    }
	
    public void insertTeam(TeamSaveDTO team) {

        StringBuilder selectSql = new StringBuilder();

        selectSql.append(" SELECT NVL(MAX(team_id), 0) + 1 AS team_id ");
        selectSql.append("   FROM team ");

        StringBuilder teamSql = new StringBuilder();

        teamSql.append(" INSERT INTO team ( ");
        teamSql.append("        team_id, ");
        teamSql.append("        team_logo_img, ");
        teamSql.append("        team_name, ");
        teamSql.append("        team_short_name ");
        teamSql.append(" ) VALUES ( ");
        teamSql.append("        ?, ?, ?, ? ");
        teamSql.append(" ) ");

        StringBuilder stadiumHomeSql = new StringBuilder();

        stadiumHomeSql.append(" INSERT INTO stadium_home ( ");
        stadiumHomeSql.append("        stadium_id, ");
        stadiumHomeSql.append("        team_id ");
        stadiumHomeSql.append(" ) VALUES ( ");
        stadiumHomeSql.append("        ?, ? ");
        stadiumHomeSql.append(" ) ");

        Connection con = null;
        PreparedStatement selectPstmt = null;
        PreparedStatement teamPstmt = null;
        PreparedStatement stadiumHomePstmt = null;
        ResultSet rs = null;

        try {

            con = AdminDBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            int teamId = 0;

            selectPstmt = con.prepareStatement(selectSql.toString());
            rs = selectPstmt.executeQuery();

            if (rs.next()) {
                teamId = rs.getInt("team_id");
            }

            teamPstmt = con.prepareStatement(teamSql.toString());

            teamPstmt.setInt(1, teamId);
            teamPstmt.setString(2, team.getTeamLogoImg());
            teamPstmt.setString(3, team.getTeamName());
            teamPstmt.setString(4, team.getTeamShortName());

            int teamResult = teamPstmt.executeUpdate();

            stadiumHomePstmt =
                    con.prepareStatement(stadiumHomeSql.toString());

            stadiumHomePstmt.setInt(1, team.getStadiumCode());
            stadiumHomePstmt.setInt(2, teamId);

            int stadiumHomeResult =
                    stadiumHomePstmt.executeUpdate();

            if (teamResult > 0 && stadiumHomeResult > 0) {
                con.commit();
            } else {
                con.rollback();
                throw new RuntimeException("팀 등록 실패");
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

            throw new RuntimeException("팀 등록 중 오류 발생", e);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (selectPstmt != null) {
                    selectPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (teamPstmt != null) {
                    teamPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (stadiumHomePstmt != null) {
                    stadiumHomePstmt.close();
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
	
    public int updateTeam(TeamSaveDTO team) {

        int result = 0;

        StringBuilder checkTeamSql = new StringBuilder();
        checkTeamSql.append(" SELECT COUNT(*) AS cnt ");
        checkTeamSql.append("   FROM team ");
        checkTeamSql.append("  WHERE team_id = ? ");

        StringBuilder checkStadiumSql = new StringBuilder();
        checkStadiumSql.append(" SELECT COUNT(*) AS cnt ");
        checkStadiumSql.append("   FROM stadium ");
        checkStadiumSql.append("  WHERE stadium_id = ? ");

        StringBuilder teamSql = new StringBuilder();
        teamSql.append(" UPDATE team ");
        teamSql.append("    SET team_name = ?, ");
        teamSql.append("        team_short_name = ?, ");
        teamSql.append("        team_logo_img = ? ");
        teamSql.append("  WHERE team_id = ? ");

        StringBuilder deleteHomeSql = new StringBuilder();
        deleteHomeSql.append(" DELETE FROM stadium_home ");
        deleteHomeSql.append("  WHERE team_id = ? ");

        StringBuilder insertHomeSql = new StringBuilder();
        insertHomeSql.append(" INSERT INTO stadium_home ( ");
        insertHomeSql.append("        stadium_id, ");
        insertHomeSql.append("        team_id ");
        insertHomeSql.append(" ) VALUES ( ");
        insertHomeSql.append("        ?, ? ");
        insertHomeSql.append(" ) ");

        Connection con = null;
        PreparedStatement checkTeamPstmt = null;
        PreparedStatement checkStadiumPstmt = null;
        PreparedStatement teamPstmt = null;
        PreparedStatement deleteHomePstmt = null;
        PreparedStatement insertHomePstmt = null;
        ResultSet rs = null;

        try {

            con = AdminDBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            System.out.println("===== DAO 팀 수정 =====");
            System.out.println("teamCode = " + team.getTeamCode());
            System.out.println("teamName = " + team.getTeamName());
            System.out.println("teamShortName = " + team.getTeamShortName());
            System.out.println("stadiumCode = " + team.getStadiumCode());
            System.out.println("teamLogoImg = " + team.getTeamLogoImg());

            if (team.getTeamCode() == 0) {
                throw new RuntimeException("팀 수정 실패 : teamCode가 0입니다.");
            }

            if (team.getStadiumCode() == 0) {
                throw new RuntimeException("팀 수정 실패 : stadiumCode가 0입니다.");
            }

            /*
             * 1. 수정할 팀이 실제 TEAM 테이블에 있는지 확인
             */
            checkTeamPstmt = con.prepareStatement(checkTeamSql.toString());
            checkTeamPstmt.setInt(1, team.getTeamCode());
            rs = checkTeamPstmt.executeQuery();

            int teamCount = 0;

            if (rs.next()) {
                teamCount = rs.getInt("cnt");
            }

            rs.close();
            rs = null;
            checkTeamPstmt.close();
            checkTeamPstmt = null;

            System.out.println("TEAM 존재 여부 count = " + teamCount);

            if (teamCount == 0) {
                throw new RuntimeException(
                        "팀 수정 실패 : TEAM 테이블에 team_id가 없습니다. team_id="
                        + team.getTeamCode());
            }

            /*
             * 2. 선택한 구장이 실제 STADIUM 테이블에 있는지 확인
             */
            checkStadiumPstmt =
                    con.prepareStatement(checkStadiumSql.toString());

            checkStadiumPstmt.setInt(1, team.getStadiumCode());
            rs = checkStadiumPstmt.executeQuery();

            int stadiumCount = 0;

            if (rs.next()) {
                stadiumCount = rs.getInt("cnt");
            }

            rs.close();
            rs = null;
            checkStadiumPstmt.close();
            checkStadiumPstmt = null;

            System.out.println("STADIUM 존재 여부 count = " + stadiumCount);

            if (stadiumCount == 0) {
                throw new RuntimeException(
                        "팀 수정 실패 : STADIUM 테이블에 stadium_id가 없습니다. stadium_id="
                        + team.getStadiumCode());
            }

            /*
             * 3. TEAM 수정
             */
            teamPstmt = con.prepareStatement(teamSql.toString());

            teamPstmt.setString(1, team.getTeamName());
            teamPstmt.setString(2, team.getTeamShortName());
            teamPstmt.setString(3, team.getTeamLogoImg());
            teamPstmt.setInt(4, team.getTeamCode());

            result = teamPstmt.executeUpdate();

            System.out.println("TEAM update result = " + result);

            if (result <= 0) {
                throw new RuntimeException("TEAM update 실패");
            }

            /*
             * 4. 기존 홈구장 연결 삭제
             */
            deleteHomePstmt =
                    con.prepareStatement(deleteHomeSql.toString());

            deleteHomePstmt.setInt(1, team.getTeamCode());

            int deleteHomeResult =
                    deleteHomePstmt.executeUpdate();

            System.out.println("STADIUM_HOME delete result = "
                    + deleteHomeResult);

            /*
             * 5. 새 홈구장 연결 등록
             *
             * 중요:
             * SQL 컬럼 순서가 stadium_id, team_id 이므로
             * 1번에는 stadiumCode,
             * 2번에는 teamCode가 들어가야 함
             */
            insertHomePstmt =
                    con.prepareStatement(insertHomeSql.toString());

            insertHomePstmt.setInt(1, team.getStadiumCode());
            insertHomePstmt.setInt(2, team.getTeamCode());

            System.out.println("STADIUM_HOME insert stadium_id = "
                    + team.getStadiumCode());
            System.out.println("STADIUM_HOME insert team_id = "
                    + team.getTeamCode());

            int insertHomeResult =
                    insertHomePstmt.executeUpdate();

            System.out.println("STADIUM_HOME insert result = "
                    + insertHomeResult);

            if (insertHomeResult > 0) {
                con.commit();
            } else {
                con.rollback();
                result = 0;
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
            result = 0;

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (checkTeamPstmt != null) {
                    checkTeamPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (checkStadiumPstmt != null) {
                    checkStadiumPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (teamPstmt != null) {
                    teamPstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (deleteHomePstmt != null) {
                    deleteHomePstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (insertHomePstmt != null) {
                    insertHomePstmt.close();
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
	
	public int deleteTeam(int teamCode) {
		return 0;
	}
	
	public List<TeamOptionDTO> selecteTeamOption(){
	    
	    List<TeamOptionDTO> list = new ArrayList<>();

	    StringBuilder sql = new StringBuilder();

	    sql.append(" SELECT team_id, ");
	    sql.append("        team_name ");
	    sql.append("   FROM team ");
	    sql.append("  ORDER BY team_id ");

	    try (
	    	Connection con = AdminDBConnection.getInstance().getConnection();
	        PreparedStatement pstmt = con.prepareStatement(sql.toString());
	        ResultSet rs = pstmt.executeQuery()
	    ) {

	        while (rs.next()) {

	            TeamOptionDTO dto = new TeamOptionDTO();

	            dto.setTeamCode(rs.getInt("team_id"));
	            dto.setTeamName(rs.getString("team_name"));

	            list.add(dto);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    System.out.println("팀 옵션 조회 건수 : " + list.size());

	    return list;
	}
	
	public List<StadiumOptionDTO> selecteStadiumOption(){
	    
	    List<StadiumOptionDTO> list = new ArrayList<>();

	    StringBuilder sql = new StringBuilder();

	    sql.append(" SELECT stadium_id, ");
	    sql.append("        stadium_name ");
	    sql.append("   FROM stadium ");
	    sql.append("  ORDER BY stadium_id ");

	    try (
	    	Connection con = AdminDBConnection.getInstance().getConnection();
	        PreparedStatement pstmt = con.prepareStatement(sql.toString());
	        ResultSet rs = pstmt.executeQuery()
	    ) {

	        while (rs.next()) {

	            StadiumOptionDTO dto = new StadiumOptionDTO();

	            dto.setStadiumCode(rs.getInt("stadium_id"));
	            dto.setStadiumName(rs.getString("stadium_name"));

	            list.add(dto);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    System.out.println("구장 옵션 조회 건수 : " + list.size());

	    return list;
	}
}

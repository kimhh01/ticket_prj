package kr.user.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.user.common.UserDBConnection;

@SuppressWarnings("unused")
public class MainDAO {

	/**
	 * DB 연결 객체를 얻는 메서드
	 * 사용자 전용 JNDI DataSource에서 DB 연결을 가져온다.
	 */
	private Connection getConnection() throws SQLException {
		return UserDBConnection.getInstance().getConnection();
	}// getConnection

	/**
	 * 메인화면 상단 배너 목록 조회
	 * 
	 * 아직 DB 연결 전이면 null 유지.
	 */
	public List<MainBannerDTO> selectMainBannerList() {
		return null;
	}// selectMainBannerList

	/**
	 * 메인화면 최근 경기 목록 조회
	 * 
	 * GAME_SCHEDULE을 기준으로 TEAM을 홈팀/원정팀으로 각각 조인하고,
	 * STADIUM을 조인해서 메인 화면에 보여줄 경기 정보를 조회한다.
	 * 
	 * 현재 DB 가데이터 날짜가 과거일 수 있으므로 날짜 조건은 넣지 않고,
	 * 등록된 경기 중 날짜/시간 순으로 최대 5개만 조회한다.
	 */
	public List<MainGameDTO> selectRecentGameList() {
		List<MainGameDTO> gameList = new ArrayList<MainGameDTO>();

		String sql =
				"SELECT * "
				+ "FROM ( "
				+ "    SELECT gs.game_schedule_id, "
				+ "           gs.game_date, "
				+ "           gs.game_start_time, "
				+ "           gs.stadium_id, "
				+ "           s.stadium_name, "
				+ "           gs.team_home, "
				+ "           home.team_name AS home_team_name, "
				+ "           home.team_logo_img AS home_team_logo, "
				+ "           gs.team_other, "
				+ "           away.team_name AS away_team_name, "
				+ "           away.team_logo_img AS away_team_logo, "
				+ "           gs.sale_state "
				+ "    FROM game_schedule gs "
				+ "    JOIN team home "
				+ "    ON gs.team_home = home.team_id "
				+ "    JOIN team away "
				+ "    ON gs.team_other = away.team_id "
				+ "    JOIN stadium s "
				+ "    ON gs.stadium_id = s.stadium_id "
				+ "    ORDER BY gs.game_date ASC, gs.game_start_time ASC "
				+ ") "
				+ "WHERE ROWNUM <= 5";

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				MainGameDTO dto = new MainGameDTO();

				dto.setGameScheduleCode(rs.getInt("game_schedule_id"));
				dto.setGameDate(rs.getDate("game_date"));
				dto.setGameStartTime(rs.getString("game_start_time"));

				dto.setStadiumCode(rs.getInt("stadium_id"));
				dto.setStadiumName(rs.getString("stadium_name"));

				dto.setHomeTeamCode(rs.getInt("team_home"));
				dto.setHomeTeamName(rs.getString("home_team_name"));
				dto.setHomeTeamLogo(rs.getString("home_team_logo"));

				dto.setAwayTeamCode(rs.getInt("team_other"));
				dto.setAwayTeamName(rs.getString("away_team_name"));
				dto.setAwayTeamLogo(rs.getString("away_team_logo"));

				dto.setSaleStatus(rs.getString("sale_state"));

				gameList.add(dto);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}

		return gameList;
	}// selectRecentGameList

	/**
	 * 메인화면 팀 순위 목록 조회
	 * 
	 * GAME_RECORD 테이블의 팀 성적 정보를 TEAM 테이블과 조인해서
	 * 승률 기준으로 팀 순위를 조회한다.
	 */
	public List<TeamRankDTO> selectTeamRankList() {
		List<TeamRankDTO> rankList = new ArrayList<TeamRankDTO>();

		String sql =
				"SELECT ROW_NUMBER() OVER (ORDER BY gr.win_rate DESC, gr.win_cnt DESC) AS rank_no, "
				+ "       gr.team_id, "
				+ "       t.team_name, "
				+ "       t.team_logo_img, "
				+ "       gr.game_cnt, "
				+ "       gr.win_cnt, "
				+ "       gr.lose_cnt, "
				+ "       gr.draw_cnt, "
				+ "       gr.win_rate, "
				+ "       gr.score_gap, "
				+ "       gr.record_date "
				+ "FROM game_record gr "
				+ "JOIN team t "
				+ "ON gr.team_id = t.team_id "
				+ "ORDER BY gr.win_rate DESC, gr.win_cnt DESC";

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				TeamRankDTO dto = new TeamRankDTO();

				dto.setRankNo(rs.getInt("rank_no"));
				dto.setTeamCode(rs.getInt("team_id"));
				dto.setTeamName(rs.getString("team_name"));
				dto.setTeamLogo(rs.getString("team_logo_img"));

				dto.setWin(rs.getInt("win_cnt"));
				dto.setLose(rs.getInt("lose_cnt"));
				dto.setDraw(rs.getInt("draw_cnt"));
				dto.setWinRate(rs.getDouble("win_rate"));
				dto.setRankUpdateDate(rs.getDate("record_date"));

				rankList.add(dto);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}

		return rankList;
	}

}

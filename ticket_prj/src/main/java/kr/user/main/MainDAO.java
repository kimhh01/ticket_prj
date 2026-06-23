package kr.user.main;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.DBConnection;

public class MainDAO {

	/**
	 * DB 연결 객체를 얻는 메서드
	 * InquiryDAO, MemberDAO와 같은 방식으로 database.properties 파일을 찾아서 DB에 연결한다.
	 */
	private Connection getConnection() throws SQLException {
		try {
			URL url = MainDAO.class.getClassLoader().getResource("properties/database.properties");

			if (url == null) {
				throw new SQLException("database.properties 파일을 찾을 수 없습니다.");
			}

			File file = new File(url.toURI());
			return DBConnection.getInstance().getConnection(file);

		} catch (Exception e) {
			throw new SQLException("DB 연결 설정 파일 로딩 실패", e);
		}
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
	 * 아직 DB 연결 전이면 null 유지.
	 */
	public List<TeamRankDTO> selectTeamRankList() {
		return null;
	}// selectTeamRankList

}
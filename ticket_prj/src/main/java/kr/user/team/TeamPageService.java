package kr.user.team;

import java.sql.SQLException;
import java.util.List;


public class TeamPageService {

	private TeamPageDAO tpDAO;
	
		//팀메인이미지
		public String getTeamImg(int teamCode) throws SQLException {
			return tpDAO.selectTeamImg(teamCode);
		}
		
		//경기리스트
		public List<TeamDTO> getGame(int teamCode) throws SQLException {
			
			return tpDAO.selectGame(teamCode);
		}
		
		//각팀공지사항
		public List<TeamDTO> getNotice(int teamCode) throws SQLException {
			
			return tpDAO.selectNotice(teamCode);
		}
		
		//리그안내페이지
		public String getLeagueGuide(int teamCode) throws SQLException {
			return tpDAO.selectLeagueGuide(teamCode);
		}
}

package user_Team;

import java.sql.SQLException;
import java.util.List;


public class TeamPageService {

	//팀메인이미지
		public TeamDTO getTeamImg(int teamCode) {
			TeamDTO tDTO=null;
			
			TeamPageDAO tpDAO=TeamPageDAO.getInstance();
			
			try {
				tDTO=tpDAO.selectTeamImg(teamCode);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return tDTO;
		}
		
		//경기리스트
		public TeamDTO getGame(TeamDTO tDTO) {
			
			return null;
		}
		
		//각팀공지사항
		public List<TeamDTO> getNotice(int teamCode) {
			
			return null;
		}
		
		//리그안내페이지
		public String getLeagueGuide(int teamCode) {
			String leagueGuide=null;
			
			TeamPageDAO tpDAO=TeamPageDAO.getInstance();
			
			try {
				leagueGuide=tpDAO.selectLeagueGuide(teamCode);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return leagueGuide;
		}
}

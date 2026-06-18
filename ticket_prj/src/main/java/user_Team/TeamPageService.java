package user_Team;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TeamPageService {

	//팀메인이미지 //없앨수도 있음
		public String getTeamImg(int teamCode) {
			String teamImg=null;
			
			TeamPageDAO tpDAO=TeamPageDAO.getInstance();
			
			try {
				teamImg=tpDAO.selectTeamImg(teamCode);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return teamImg;
		}
		
		//경기리스트
		public List<TeamDTO> getGame(int teamCode) {
			List<TeamDTO> list=new ArrayList<TeamDTO>();
			
			TeamPageDAO tpDAO=TeamPageDAO.getInstance();
			
			try {
				list=tpDAO.selectGame(teamCode);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return list;
		}
		
		//각팀공지사항
		public List<TeamDTO> getNotice(int teamCode) {
			List<TeamDTO> list=new ArrayList<TeamDTO>();
			
			TeamPageDAO tpDAO=TeamPageDAO.getInstance();
			
			try {
				list=tpDAO.selectNotice(teamCode);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return list;
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

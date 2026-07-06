package kr.user.team;

import java.sql.SQLException;
import java.util.List;


public class TeamPageService {

	private TeamPageDAO tpDAO =TeamPageDAO.getInstance();
	
	//팀 정보 조회
	public TeamDTO getTeamInfo(int teamCode) throws SQLException{
	    return tpDAO.selectTeamInfo(teamCode);
	}
	
	//팀메인이미지
	public String getTeamImg(int teamCode) throws SQLException {
		
		return tpDAO.selectTeamImg(teamCode);
	}
	
	//경기리스트
	public List<TeamDTO> getGame(int teamCode,int year,int month) throws SQLException {
		return tpDAO.selectGame(teamCode,year,month);
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

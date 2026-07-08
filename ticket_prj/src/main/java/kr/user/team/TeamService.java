package kr.user.team;

import java.sql.SQLException;
import java.util.List;


public class TeamService {

	private TeamDAO tDAO =TeamDAO.getInstance();
	
	//팀 정보 조회
	public TeamDTO getTeamInfo(int teamCode) throws SQLException{
	    return tDAO.selectTeamInfo(teamCode);
	}
	
	//팀메인이미지
	public String getTeamImg(int teamCode) throws SQLException {
		
		return tDAO.selectTeamImg(teamCode);
	}
	
	//경기리스트
	public List<TeamDTO> getGame(int teamCode,int year,int month) throws SQLException {
		return tDAO.selectGame(teamCode,year,month);
	}
	
	//각팀공지사항
	public List<TeamDTO> getNotice(int teamCode) throws SQLException {
		return tDAO.selectNotice(teamCode);
	}
	
	//리그안내페이지
	public List<String> getLeagueGuide(int teamCode) throws SQLException {
		return tDAO.selectLeagueGuide(teamCode);
	}
	
	
}

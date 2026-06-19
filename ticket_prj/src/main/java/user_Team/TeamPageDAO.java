package user_Team;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBConnection;
import dbConnection.DbConnection;
import dbConnection.Path;


public class TeamPageDAO {
	private static TeamPageDAO tpDAO;
	
	private TeamPageDAO() {
		
	}
	
	public static TeamPageDAO getInstance() {
		if(tpDAO==null) {
			tpDAO=new TeamPageDAO();
		}
		
		return tpDAO;
	}
	
	//팀메인이미지 //없앨수도 있음
	public String selectTeamImg(int teamCode) throws SQLException {
		String teamImg=null; //이미지 경로 받을 변수
		
		Connection con=null; 
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DBConnection dbCon=DbConnection.getInstance();
		
		
		try {
			con=dbCon.getConn(new File(Path.DATABASE_PROPERTIES));
			StringBuilder selectTeamImg=new StringBuilder();
			
			//데이터 베이스의 팀 id를 검색하여 팀 로고를 가져옴
			selectTeamImg
			.append("	select team_logo_img	")
			.append("	from team	")
			.append("	where team_id =?	");
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, teamCode);
				rs=pstmt.executeQuery();
				
			if(rs.next()) {
				    teamImg=rs.getString("team_logo_img");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbCon.dbClose(rs, pstmt, con);
		}
		return teamImg;
	}
	
	//경기리스트 //수정완료
	public List<TeamDTO> selectGame(int teamCode) throws SQLException {
		List<TeamDTO> list=new ArrayList<TeamDTO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		
		
		try {
			con=dbCon.getConn(new File(Path.DATABASE_PROPERTIES));
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	select gs.game_date,	")
			.append("	ht.team_name AS home_team_name, ht.team_logo_img AS home_team_logo,	")
			.append("	ot.team_name AS other_team_name, ot.team_logo_img AS other_team_logo,	")
			.append(" 	s.stadium_name ")
			.append("	from game_schedule gs	")
			.append("	JOIN team ht	")
			.append("	ON gs.team_home = ht.team_id	")
			.append("	JOIN team ot	")
			.append("	ON gs.team_other = ot.team_id	")
			.append("	JOIN stadium s	")
			.append("	ON gs.stadium_id = s.stadium_id	")
			.append("	WHERE ht.team_id = ?	");
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, teamCode);
				rs=pstmt.executeQuery();
				
			while(rs.next()) {
				TeamDTO tDTO = new TeamDTO();
				tDTO.setGameDate(rs.getDate("game_date"));
			    tDTO.setTeamHomeName(rs.getString("home_team_name"));
			    tDTO.setTeamHomeImg(rs.getString("home_team_logo"));
			    tDTO.setTeamOtherName(rs.getString("other_team_name"));
			    tDTO.setTeamOtherImg(rs.getString("other_team_logo"));
			    tDTO.setStadiumName(rs.getString("stadium_name"));
			    

			    list.add(tDTO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbCon.dbClose(rs, pstmt, con);
		}
		
		return list;
		
		
	}
	
	//각팀공지사항 //공지사항 고민
	public List<TeamDTO> selectNotice(int teamCode) throws SQLException {
		List<TeamDTO> list=new ArrayList<TeamDTO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		
		
		try {
			con=dbCon.getConn(new File(Path.DATABASE_PROPERTIES));
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	select 	notice_title, notice_img, notice_content, notice_write_date")
			.append("	from notice	")
			.append("	where team_id = ?	");
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, teamCode);
				rs=pstmt.executeQuery();
				
				TeamDTO tDTO=null;
				while(rs.next()) { 
					tDTO=new TeamDTO();
					tDTO.setNoticeTitle(rs.getString("notice_title"));
					tDTO.setNoticeImg(rs.getString("notice_img"));
					tDTO.setNoticeContent(rs.getString("notice_content"));
					tDTO.setNoticeWriteDate(rs.getString("notice_write_date"));
					
				}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbCon.dbClose(rs, pstmt, con);
		}
		return list;
	}
	
	//리그안내페이지
	public String selectLeagueGuide(int teamCode) throws SQLException {
		String leagueGuide=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		
		
		try {
			con=dbCon.getConn(new File(Path.DATABASE_PROPERTIES));
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	select notice_img	")
			.append("	from notice	")
			.append("	where notice_tab = 2 and team_id = ?	");
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, teamCode);
				rs=pstmt.executeQuery();
				
			if(rs.next()) {
				    leagueGuide=rs.getString("notice_img");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbCon.dbClose(rs, pstmt, con);
		}
		return leagueGuide;
	}
}

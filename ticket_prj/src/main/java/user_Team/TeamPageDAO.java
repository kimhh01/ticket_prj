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
	
	//팀메인이미지
	public TeamDTO selectTeamImg(int teamCode) throws SQLException {
		TeamDTO tDTO=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DBConnection dbCon=DbConnection.getInstance();
		
		
		try {
			con=dbCon.getConn(new File(Path.DATABASE_PROPERTIES));
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	select team_logo_img	")
			.append("	from team	")
			.append("	where team_id =?	");
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, teamCode);
				rs=pstmt.executeQuery();
				
			if(rs.next()) {
				    tDTO = new TeamDTO();
				    tDTO.setTeamHomeImg(rs.getString("team_logo_img"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbCon.dbClose(rs, pstmt, con);
		}
		return tDTO;
	}
	
	//경기리스트 //수정중
	public List<TeamDTO> selectGame(TeamDTO tDTO) throws SQLException {
		List<TeamDTO> list=new ArrayList<TeamDTO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		
		
		try {
			con=dbCon.getConn(new File(Path.DATABASE_PROPERTIES));
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	select team_logo_img	")
			.append("	from team	")
			.append("	where team_id in(?, ?)	");
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setString(1, tDTO.getTeamHomeName());
				pstmt.setString(2, tDTO.getTeamOtherName());
				rs=pstmt.executeQuery();
				
			if(rs.next()) {
				    tDTO = new TeamDTO();
				    tDTO.setTeamHomeImg(rs.getString("team_logo_img"));
				    tDTO.setTeamOtherImg(rs.getString("team_logo_img"));
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

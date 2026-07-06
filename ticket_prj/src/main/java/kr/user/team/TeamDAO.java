package kr.user.team;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.user.common.UserDBConnection;


public class TeamDAO {
	private static TeamDAO tpDAO;
	
	private Connection getConnection() throws SQLException {
		try {
			URL url = TeamDAO.class.getClassLoader().getResource("properties/database.properties");

			if (url == null) {
				throw new SQLException("database.properties 파일을 찾을 수 없습니다.");
			}

			File file = new File(url.toURI());
			return UserDBConnection.getInstance().getConnection();

		} catch (Exception e) {
			throw new SQLException("DB 연결 설정 파일 로딩 실패", e);
		}
	}// getConnection
	
	private TeamDAO() {
		
	}
	
	public static TeamDAO getInstance() {
		if(tpDAO==null) {
			tpDAO=new TeamDAO();
		}
		
		return tpDAO;
	}
	//팀 정보조회 메서드
	public TeamDTO selectTeamInfo(int teamCode) throws SQLException {
	    TeamDTO dto = null;

	    Connection con = getConnection();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;


	    try {

	        String sql =
	            "select team_id, team_name, team_logo_img " +
	            "from team " +
	            "where team_id=?";

	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, teamCode);

	        rs = pstmt.executeQuery();

	        if(rs.next()) {
	            dto = new TeamDTO();

	            dto.setTeamCode(rs.getInt("team_id"));
	            dto.setTeamHomeName(rs.getString("team_name"));
	            dto.setTeamHomeImg(rs.getString("team_logo_img"));
	        }

	    } finally {
	    	 if(rs != null) rs.close();
	    	    if(pstmt != null) pstmt.close();
	    	    if(con != null) con.close();
	    }

	    return dto;
	}
	
	//팀메인이미지 //없앨수도 있음
	/**
	 * @param teamCode
	 * @return
	 * @throws SQLException
	 */
	public String selectTeamImg(int teamCode) throws SQLException{
		String teamImg=null; //이미지 경로 받을 변수
		
		Connection con=getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		
		try {
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
			 	if(rs != null) rs.close();
			    if(pstmt != null) pstmt.close();
			    if(con != null) con.close();
			}
		return teamImg;
	}
	
	//경기리스트를 받으면서 달력형도 같이 할 생각임
	public List<TeamDTO> selectGame(int teamCode,int year,int month)	 throws SQLException {
		List<TeamDTO> list=new ArrayList<TeamDTO>();
		Connection con=getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		
		
		try {
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
		    .append(" select gs.game_schedule_id, ")
		    .append(" gs.game_date, gs.game_start_time, ")
		    .append(" ht.team_name AS home_team_name, ht.team_logo_img AS home_team_logo, ")
		    .append(" ot.team_name AS other_team_name, ot.team_logo_img AS other_team_logo, ")
		    .append(" s.stadium_name ")
		    .append(" from game_schedule gs ")
		    .append(" join team ht on gs.team_home = ht.team_id ")
		    .append(" join team ot on gs.team_other = ot.team_id ")
		    .append(" join stadium s on gs.stadium_id = s.stadium_id ")
		    .append(" where (ht.team_id = ? or ot.team_id = ?) ")
		    .append(" and extract(year from gs.game_date)=? ")
		    .append(" and extract(month from gs.game_date)=? ")
		    .append(" and gs.game_date >= CURRENT_DATE ")
		    .append(" order by gs.game_date, gs.game_start_time ");
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, teamCode);
				pstmt.setInt(2, teamCode);
				pstmt.setInt(3, year);
				pstmt.setInt(4, month);
				rs=pstmt.executeQuery();
				
			while(rs.next()) {
				TeamDTO tDTO = new TeamDTO();
				
				
				tDTO.setGameScheduleCode(rs.getInt("game_schedule_id"));
				tDTO.setGameDate(rs.getDate("game_date"));
				tDTO.setGameStartTime(rs.getString("game_start_time"));
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
			 if(rs != null) rs.close();
			    if(pstmt != null) pstmt.close();
			    if(con != null) con.close();
		}
		
		return list;
		
		
	}
	
	//각팀공지사항 //공지사항 고민
	public List<TeamDTO> selectNotice(int teamCode) throws SQLException {
		List<TeamDTO> noticeList=new ArrayList<TeamDTO>();
		Connection con=getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		
		
		try {
			StringBuilder selectTeamImg=new StringBuilder();
			selectTeamImg
			.append("	select 	notice_title, notice_img, notice_write_date		")
			.append("	from notice	")
			.append("	where team_id = ? and notice_tab=1 ");
			
			pstmt=con.prepareStatement(selectTeamImg.toString());
				pstmt.setInt(1, teamCode);
				rs=pstmt.executeQuery();
				
				TeamDTO tDTO=null;
				while(rs.next()) { 
					tDTO=new TeamDTO();
					tDTO.setNoticeTitle(rs.getString("notice_title"));
					tDTO.setNoticeImg(rs.getString("notice_img"));
					tDTO.setNoticeWriteDate(rs.getDate("notice_write_date"));
					
					noticeList.add(tDTO);
					
				}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			 if(rs != null) rs.close();
			    if(pstmt != null) pstmt.close();
			    if(con != null) con.close();
		}
		return noticeList;
	}
	
	//리그안내페이지
	public String selectLeagueGuide(int teamCode) throws SQLException {
		String leagueGuide=null;
		
		Connection con=getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		
		
		try {
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
			 if(rs != null) rs.close();
			    if(pstmt != null) pstmt.close();
			    if(con != null) con.close();
		}
		return leagueGuide;
	}
	
	
	
	
}

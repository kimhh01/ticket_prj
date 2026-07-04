package kr.user.team;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TeamPageServlet
 */
@WebServlet("/teamPage")
	public class TeamPageServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		TeamPageService tpService=new TeamPageService();
		//URI jQuery에서 팀코드 받아옴
		String teamCodeParam = request.getParameter("teamCode");
		Calendar calendar = Calendar.getInstance();
		
		int year;
		int month;

		
		//팀코드의 파라메터가 null일 겨우 메인페이지로 이동
		if(teamCodeParam == null){
		    response.sendRedirect("/index.jsp");
		    return;
		}
		//팀코드 int형으로 변환
		int teamCode = Integer.parseInt(teamCodeParam);
		try {
		    year = Integer.parseInt(request.getParameter("year"));
		} catch (Exception e) {
		    year = calendar.get(Calendar.YEAR);
		}

		try {
		    month = Integer.parseInt(request.getParameter("month"));
		} catch (Exception e) {
		    month = calendar.get(Calendar.MONTH) + 1;
		}
		try {
			List<TeamDTO> gameList = tpService.getGame(teamCode, year, month);
			
			Date now = new Date();

			Calendar cal = Calendar.getInstance();

			for(TeamDTO dto : gameList){
			    cal.setTime(dto.getGameDate());
			    cal.add(Calendar.DAY_OF_MONTH, -7);
			    Date openDate = cal.getTime();
			    dto.setReservationOpen(
			        now.compareTo(openDate) >= 0 &&
			        now.before(dto.getGameDate())
			    );
			}
			
			List<TeamDTO> noticeList=tpService.getNotice(teamCode);
			String leagueImg=tpService.getLeagueGuide(teamCode);
			TeamDTO tDTO = tpService.getTeamInfo(teamCode);
			
			
			
			calendar.set(year, month - 1, 1);
			
			int firstDay = calendar.get(Calendar.DAY_OF_WEEK); // 1=일요일
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("firstDay", firstDay);
			request.setAttribute("lastDay", lastDay);

			
			request.setAttribute("tDTO", tDTO);
			request.setAttribute("gameList", gameList);
			request.setAttribute("noticeList", noticeList);
			request.setAttribute("leagueImg", leagueImg);
			
			RequestDispatcher rd = request.getRequestDispatcher("/teamPage/teamPage.jsp");
			rd.forward(request, response);
			
		} catch(SQLException e) {
		    throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //한글 깨짐 방지
		response.setContentType("text/html; charset=UTF-8");
		doGet(request, response);
	}

}

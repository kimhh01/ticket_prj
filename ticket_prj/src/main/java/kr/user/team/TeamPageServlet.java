package kr.user.team;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TeamPageServlet
 */
@WebServlet("/TeamPageServlet")
	public class TeamPageServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		TeamPageService tpService=new TeamPageService();
		//URI jQuery에서 팀코드 받아옴
		String teamCodeParam = request.getParameter("teamCode");
		//팀코드의 파라메터가 null일 겨우 메인페이지로 이동
		if(teamCodeParam == null){
		    response.sendRedirect("main.jsp");
		    return;
		}
		//팀코드 int형으로 변환
		int teamCode = Integer.parseInt(teamCodeParam);
		
		try {
			List<TeamDTO> gameList=tpService.getGame(teamCode);
			List<TeamDTO> noticeList=tpService.getNotice(teamCode);
			String leagueImg=tpService.getLeagueGuide(teamCode);
			
			request.setAttribute("gameList", gameList);
			request.setAttribute("noticeList", noticeList);
			request.setAttribute("leagueImg", leagueImg);
			
			RequestDispatcher rd = request.getRequestDispatcher("/teamPage.jsp");
			rd.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
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

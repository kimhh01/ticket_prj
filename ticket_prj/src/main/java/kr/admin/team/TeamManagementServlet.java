package kr.admin.team;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/teamManagement")
public class TeamManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TeamManagementService service;

	@Override
	public void init() throws ServletException {
		service = new TeamManagementService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("scheduleList", service.getGameScheduleList());
		request.setAttribute("teamList", service.getTeamList());
		request.setAttribute("teamOptionList", service.getTeamOptions());
		request.setAttribute("stadiumOptionList", service.getStadiumOptions());

		request.getRequestDispatcher("/manage/team/teamManagement.jsp")
		       .forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
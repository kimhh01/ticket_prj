package kr.user.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// /main.do 주소로 들어오면 이 서블릿이 실행됨
@WebServlet("/main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 메인 화면 요청 처리
	 * 
	 * 사용자가 /main.do로 접속하면
	 * MainService를 통해 메인 화면에 필요한 데이터를 조회한 뒤
	 * main.jsp로 전달한다.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// Service 생성
		MainService mainService = new MainService();

		// 메인 화면에 필요한 데이터 조회
		List<MainBannerDTO> bannerList = mainService.getMainBannerList();
		List<MainGameDTO> gameList = mainService.getRecentGameList();
		List<TeamRankDTO> rankList = mainService.getTeamRankList();

		// 조회한 데이터를 request 영역에 저장
		// main.jsp에서 request.getAttribute("bannerList")로 꺼내 쓸 수 있음
		request.setAttribute("bannerList", bannerList);
		request.setAttribute("gameList", gameList);
		request.setAttribute("rankList", rankList);

		// main.jsp로 이동
		// forward는 request에 담은 데이터를 유지한 채 JSP로 넘김
		RequestDispatcher rd = request.getRequestDispatcher("/main.jsp");
		rd.forward(request, response);
	}

	/**
	 * POST 요청이 들어와도 doGet과 똑같이 처리
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
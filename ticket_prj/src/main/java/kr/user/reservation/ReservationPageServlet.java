package kr.user.reservation;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.user.member.MemberDTO;

/**
 * Servlet implementation class reservationPageServlet
 */
@WebServlet("/reservationPageServlet")
public class ReservationPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ReservationPageService rpService;   
	
    public ReservationPageServlet() {
        rpService=new ReservationPageService();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 // 로그인 여부 확인
			/*
			 * HttpSession session = request.getSession(false);
			 * 
			 * if (session == null || session.getAttribute("loginMember") == null) {
			 * response.sendRedirect("member/login.jsp"); return; }
			 * 
			 * // 로그인한 회원 MemberDTO loginMember = (MemberDTO)
			 * session.getAttribute("loginMember"); String memberCode =
			 * loginMember.getMemberCode();
			 */

        try {

            // 경기번호
            int gameScheduleCode =
                    Integer.parseInt(request.getParameter("gameScheduleCode"));

			/*
			 * // 주문자 정보 ReservationPageDTO memberInfo =
			 * rpService.searchOrderMemberInfo(memberCode);
			 */

            // 경기 정보
            ReservationPageDTO gameInfo =
                    rpService.searchGame(gameScheduleCode);

            // JSP로 전달
			/* request.setAttribute("memberInfo", memberInfo); */
            request.setAttribute("gameInfo", gameInfo);

            request.getRequestDispatcher("/reservationPage/reservation.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "경기번호가 올바르지 않습니다.");
        }
		
	}

	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}

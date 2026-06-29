package kr.user.reservation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.user.member.MemberDTO;

@WebServlet("/reservation")
public class ReservationPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationPageService rpService;

    public ReservationPageServlet() {
        rpService = new ReservationPageService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String mode = request.getParameter("mode");
        
        // 결제 성공 후 돌아온 경우 처리
        if ("success".equals(mode)) {
            handlePaymentSuccess(request, response);
            return;
        }

        // --- 기존 예매창 로드 로직 ---
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginMember") == null) {
            handleUnauthenticated(request, response);
            return; 
        }
        
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember"); 
        try {
            int gameScheduleCode = Integer.parseInt(request.getParameter("gameScheduleCode"));
            ReservationPageDTO memberInfo = rpService.searchOrderMemberInfo(loginMember.getMemberCode());
            ReservationPageDTO gameInfo = rpService.searchGame(gameScheduleCode);
            
            // 잔여좌석 및 가격 (가장 대중적인 1번 좌석 기준 혹은 전체 리스트 처리 필요)
            ReservationPageDTO remainingSeat = rpService.searchRemainingSeat(gameInfo.getStadiumCode()); 
            ReservationPageDTO seatPrice = rpService.searchSeatPrice(gameInfo.getStadiumCode());

            request.setAttribute("memberInfo", memberInfo); 
            request.setAttribute("gameInfo", gameInfo);
            request.setAttribute("remainingSeat", remainingSeat);
            request.setAttribute("seatPrice", seatPrice);

            request.getRequestDispatcher("/reservationPage/reservation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    // 결제 완료 후 실행될 로직
    private void handlePaymentSuccess(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
            
            // 파라미터 추출
            int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
            int payPrice = Integer.parseInt(request.getParameter("payPrice"));
            int stadiumSeatCode = Integer.parseInt(request.getParameter("stadiumSeatCode"));
            String reservationType = request.getParameter("reservationType");
            int reservationQuantity = Integer.parseInt(request.getParameter("reservationQuantity"));
            String orderId = request.getParameter("orderId");

            ReservationPageDTO rpDTO = new ReservationPageDTO();
            rpDTO.setMemberCode(loginMember.getMemberCode());
            rpDTO.setTotalPrice(totalPrice);
            rpDTO.setPayPrice(payPrice);
            rpDTO.setStadiumSeatCode(stadiumSeatCode);
            rpDTO.setReservationType(reservationType);
            rpDTO.setReservationQuantity(reservationQuantity);

            boolean isSuccess = rpService.insertTotalReservation(rpDTO);

            if (isSuccess) {
                // 성공 페이지에 보여줄 데이터 추가 조회
                String seatName = rpService.getSeatName(stadiumSeatCode);
                request.setAttribute("orderId", orderId);
                request.setAttribute("payPrice", payPrice);
                request.setAttribute("reservationQuantity", reservationQuantity);
                request.setAttribute("selectedSeatName", seatName);
                
                request.getRequestDispatcher("/reservationPage/reservationSuccess.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/reservationPage/reservationFail.jsp?code=DB_ERR&message=DB_Save_Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/reservationPage/reservationFail.jsp?code=SYSTEM_ERR&message=" + e.getMessage());
        }
    }

    private void handleUnauthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        java.io.PrintWriter out = response.getWriter();
        out.println("<script>alert('로그인이 필요합니다.'); window.close();</script>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // POST로 오더라도 동일하게 처리
    }
}
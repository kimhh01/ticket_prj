package kr.user.reservation;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.user.member.MemberDTO;

@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReservationService rpService;

	public ReservationServlet() {
		rpService = new ReservationService();
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
			ReservationDTO memberInfo = rpService.searchOrderMemberInfo(loginMember.getMemberCode());
			ReservationDTO gameInfo = rpService.searchGame(gameScheduleCode);
			List<ReservationDTO> couponList=rpService.getCoupon(loginMember.getMemberCode());

			// [수정: 잔여 좌석 조회 시 내부 요금 정보도 List에 내장하여 로드되므로, 별도로 구장 코드를 통한 오적용 seatPrice 호출 영역을 제거했습니다]
			List<ReservationDTO> seatList = rpService.searchRemainingSeat(gameInfo.getStadiumCode());

			request.setAttribute("memberInfo", memberInfo);
			request.setAttribute("gameInfo", gameInfo);
			request.setAttribute("couponList", couponList);
			request.setAttribute("seatList", seatList);

			request.getRequestDispatcher("kr/user/reservation/reservation.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}

	// 결제 완료 후 실행될 로직 (다중 권종 개별 분리 저장 지원 개편 완료)
		private void handlePaymentSuccess(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			try {
				HttpSession session = request.getSession(false);
				MemberDTO loginMember = null;

				if (session != null) {
					loginMember = (MemberDTO) session.getAttribute("loginMember");
				}

				if (loginMember == null) {
					System.out.println("로그인 세션 없음");
					response.sendRedirect(request.getContextPath() + "/login.jsp");
					return;
				}

				// 파라미터 추출
				String couponCode = request.getParameter("couponCode");
				String discountRateParam = request.getParameter("discountRate");
				int discountRate = 0;

				if (discountRateParam != null && !discountRateParam.isEmpty()) {
				    discountRate = Integer.parseInt(discountRateParam);
				}
				
				int stadiumSeatCode = Integer.parseInt(request.getParameter("stadiumSeatCode"));
				int gameScheduleCode = Integer.parseInt(request.getParameter("gameScheduleCode"));
				
				// [수정] 각 권종별 개별 선택 수량을 수집합니다
				int adultQty = Integer.parseInt(request.getParameter("adultQty"));
				int youthQty = Integer.parseInt(request.getParameter("youthQty"));
				int childQty = Integer.parseInt(request.getParameter("childQty"));
				int totalQuantity = adultQty + youthQty + childQty;
				
				// 전체 예매 수량 한도 검증
				if (totalQuantity <= 0 || totalQuantity > 3) {
				    response.sendRedirect(
				        request.getContextPath()
				        + "kr/user/reservation/reservationFail.jsp?message=잘못된 예매 수량입니다.");
				    return;
				}
				
				// 각각의 좌석 등급 단가 조회
				int adultPrice = rpService.getSeatPrice(stadiumSeatCode, "성인");
				int youthPrice = rpService.getSeatPrice(stadiumSeatCode, "청소년");
				int childPrice = rpService.getSeatPrice(stadiumSeatCode, "어린이");
				
				if (adultPrice <= 0 || youthPrice <= 0 || childPrice <= 0) {
				    response.sendRedirect(
				        request.getContextPath()
				        + "kr/user/reservation/reservationFail.jsp?message=존재하지 않는 좌석입니다.");
				    return;
				}
				
				// 혼합 요금 합산 계산
				int totalPrice = (adultPrice * adultQty) + (youthPrice * youthQty) + (childPrice * childQty);
				int fee = totalQuantity * 1000;
				int discountPrice = totalPrice * discountRate / 100;
				int payPrice = totalPrice - discountPrice + fee;
				
				// 1. 예약 마스터 테이블용 DTO 세팅
				ReservationDTO rpDTO = new ReservationDTO();
				rpDTO.setMemberCode(loginMember.getMemberCode());
				rpDTO.setTotalPrice(totalPrice);
				rpDTO.setPayPrice(payPrice);
				rpDTO.setStadiumSeatCode(stadiumSeatCode);
				rpDTO.setReservationQuantity(totalQuantity); // 좌석 카운트 차감용 일괄 수량 설정
				rpDTO.setGameScheduleCode(gameScheduleCode);
				rpDTO.setDiscountPrice(discountPrice);

				// 2. 예약 디테일 리스트 구성 (성인, 청소년, 어린이 중 1개라도 구매한 권종만 동적으로 Row 구성)
				List<ReservationDTO> detailList = new java.util.ArrayList<>();
				
				if (adultQty > 0) {
					ReservationDTO adultDetail = new ReservationDTO();
					adultDetail.setStadiumSeatCode(stadiumSeatCode);
					adultDetail.setReservationType("성인");
					adultDetail.setReservationQuantity(adultQty);
					detailList.add(adultDetail);
				}
				if (youthQty > 0) {
					ReservationDTO youthDetail = new ReservationDTO();
					youthDetail.setStadiumSeatCode(stadiumSeatCode);
					youthDetail.setReservationType("청소년");
					youthDetail.setReservationQuantity(youthQty);
					detailList.add(youthDetail);
				}
				if (childQty > 0) {
					ReservationDTO childDetail = new ReservationDTO();
					childDetail.setStadiumSeatCode(stadiumSeatCode);
					childDetail.setReservationType("어린이");
					childDetail.setReservationQuantity(childQty);
					detailList.add(childDetail);
				}

				// 3. 서비스 트랜잭션 처리 호출
				int reservationCode = rpService.insertTotalReservation(rpDTO, detailList);

				if (reservationCode > 0) {
					// 쿠폰 사용 완료 처리
				    if (couponCode != null && !couponCode.isEmpty()) {
				        rpService.updateCouponState(loginMember.getMemberCode(), couponCode);
				    }
					
					// 성공 페이지 데이터 추가 조회
					String seatName = rpService.getSeatName(stadiumSeatCode);
					request.setAttribute("reservationCode", reservationCode);
					request.setAttribute("payPrice", payPrice);
					request.setAttribute("reservationQuantity", totalQuantity);
					request.setAttribute("selectedSeatName", seatName);

					request.getRequestDispatcher("kr/user/reservation/reservationSuccess.jsp").forward(request, response);
				} else {
					response.sendRedirect(request.getContextPath()
							+ "kr/user/reservation/reservationFail.jsp?code=DB_ERR&message=DB_Save_Failed");
				}
			} catch (Exception e) {
				e.printStackTrace();

				response.sendRedirect(request.getContextPath()
						+ "kr/user/reservation/reservationFail.jsp?code=SYSTEM_ERR&message=" + e.getMessage());
			}
		}

		private void handleUnauthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
		    response.setContentType("text/html; charset=UTF-8");

		    String ctx = request.getContextPath();

		    java.io.PrintWriter out = response.getWriter();
		    out.println("<script>");
		    out.println("alert('로그인이 필요합니다.');");

		    out.println("if (window.opener && !window.opener.closed) {");
		    out.println("    window.opener.location.href = '" + ctx + "/kr/user/member/login.jsp';");
		    out.println("    window.close();");
		    out.println("} else {");
		    out.println("    location.href = '" + ctx + "/kr/user/member/login.jsp';");
		    out.println("}");
		    
		    out.println("</script>");
		}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response); // POST로 오더라도 동일하게 처리
	}
}
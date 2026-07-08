package kr.admin.ticket;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.admin.common.BoardRangeDTO;

@WebServlet({
        "/admin/ticket",
        "/admin/ticket/zone",
        "/admin/ticket/search",
        "/admin/ticket/cancel"
})
public class TicketManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private TicketManagementService service;

    @Override
    public void init() throws ServletException {
        service = new TicketManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String path = request.getServletPath();

        if ("/admin/ticket/zone".equals(path)) {
            showZoneDetail(request, response);
            return;
        }

        if ("/admin/ticket/search".equals(path)) {
            showSearchPage(request, response);
            return;
        }

        showDashboard(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String path = request.getServletPath();

        if ("/admin/ticket/cancel".equals(path)) {
            cancelReservation(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/ticket");
    }

	private void showDashboard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int page = parseInt(request.getParameter("page"), 1);

		int totalCount = service.totalCount();

		BoardRangeDTO range = service.makeRange(page, totalCount);

		List<TicketMatchListDTO> matchList = service.getMatchDashboardList(range);

		java.util.Map<Integer, List<TicketZoneInfoDTO>> zoneMap = new java.util.HashMap<Integer, List<TicketZoneInfoDTO>>();

		java.util.Map<Integer, List<TicketInfoDTO>> ticketInfoMap = new java.util.HashMap<Integer, List<TicketInfoDTO>>();

		java.util.Map<Integer, TicketSalesDTO> salesMap = new java.util.HashMap<Integer, TicketSalesDTO>();

		if (matchList != null) {
			for (TicketMatchListDTO match : matchList) {
				int scheduleCode = match.getScheduleCode();

				zoneMap.put(scheduleCode, service.getZoneStatusList(scheduleCode));

				ticketInfoMap.put(scheduleCode, service.getTicketInfoList(scheduleCode));

				salesMap.put(scheduleCode, service.getTicketSales(scheduleCode));
			}
		}

		request.setAttribute("activeMenu", "ticket");
		request.setAttribute("range", range);
		request.setAttribute("matchList", matchList);

		request.setAttribute("zoneMap", zoneMap);
		request.setAttribute("ticketInfoMap", ticketInfoMap);
		request.setAttribute("salesMap", salesMap);

		request.getRequestDispatcher("/manage/ticket/ticketManagement.jsp").forward(request, response);
	}

	private void showZoneDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int scheduleCode = parseInt(request.getParameter("scheduleCode"), 0);

		int zoneCode = parseInt(request.getParameter("zoneCode"), 0);

		int page = parseInt(request.getParameter("page"), 1);

		if (scheduleCode == 0 || zoneCode == 0) {
			response.sendRedirect(request.getContextPath() + "/admin/ticket");
			return;
		}

		TicketMatchListDTO match = service.getMatchDetail(scheduleCode);

		TicketZoneInfoDTO zone = service.getZoneDetail(scheduleCode, zoneCode);

		TicketInfoDTO ticketInfo = service.getTicketInfo(scheduleCode, zoneCode);

		int totalCount = service.reservationTotalCount(scheduleCode, zoneCode);

		BoardRangeDTO range = service.makeRange(page, totalCount);

		List<TicketReservationListDTO> reservationList = service.getReservationList(scheduleCode, zoneCode, range);

		request.setAttribute("activeMenu", "ticket");
		request.setAttribute("match", match);
		request.setAttribute("zone", zone);
		request.setAttribute("ticketInfo", ticketInfo);
		request.setAttribute("range", range);
		request.setAttribute("reservationList", reservationList);
		request.setAttribute("scheduleCode", scheduleCode);
		request.setAttribute("zoneCode", zoneCode);

		request.getRequestDispatcher("/manage/ticket/ticketZoneDetail.jsp").forward(request, response);
	}

    private void showSearchPage(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        String startDate = nvl(request.getParameter("startDate"));
        String endDate = nvl(request.getParameter("endDate"));
        String team = nvl(request.getParameter("team"));
        String stadium = nvl(request.getParameter("stadium"));
        String phone = nvl(request.getParameter("phone"));

        int page = parseInt(request.getParameter("page"), 1);

        int totalCount =
                service.searchTotalCount(startDate, endDate, team, stadium, phone);

        BoardRangeDTO range =
                service.makeRange(page, totalCount);

        List<TicketSearchDTO> searchList =
                service.searchReservations(
                        startDate,
                        endDate,
                        team,
                        stadium,
                        phone,
                        range
                );

        request.setAttribute("activeMenu", "ticket");

        request.setAttribute("range", range);
        request.setAttribute("searchList", searchList);

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("team", team);
        request.setAttribute("stadium", stadium);
        request.setAttribute("phone", phone);

        request.setAttribute("teamNameList", service.getTeamNameList());
        request.setAttribute("stadiumNameList", service.getStadiumNameList());

        request.getRequestDispatcher("/manage/ticket/ticketSearch.jsp")
                .forward(request, response);
    }

	private void cancelReservation(HttpServletRequest request, HttpServletResponse response) throws IOException {

		int reservationCode = parseInt(request.getParameter("reservationCode"), 0);

		String returnUrl = request.getParameter("returnUrl");

		boolean cancelFlag = false;

		if (reservationCode != 0) {
			cancelFlag = service.cancelReservation(reservationCode);
		}

		if (returnUrl == null || returnUrl.trim().isEmpty()) {
			returnUrl = request.getContextPath() + "/admin/ticket";
		}

		if (returnUrl.contains("/manage/ticket/")) {
			returnUrl = request.getContextPath() + "/admin/ticket";
		}

		String separator = returnUrl.contains("?") ? "&" : "?";

		response.sendRedirect(returnUrl + separator + "cancelResult=" + (cancelFlag ? "success" : "fail"));
	}

    private int parseInt(String value, int defaultValue) {

        int result = defaultValue;

        if (value != null && !value.trim().isEmpty()) {
            try {
                result = Integer.parseInt(value);
            } catch (NumberFormatException nfe) {
                result = defaultValue;
            }
        }

        return result;
    }

    private String nvl(String value) {

        if (value == null) {
            return "";
        }

        return value.trim();
    }

}
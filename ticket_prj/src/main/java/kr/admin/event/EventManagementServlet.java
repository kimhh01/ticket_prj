package kr.admin.event;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.BoardRangeDTO;

@WebServlet("/event")
public class EventManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EventManagementService service;

    @Override
    public void init() throws ServletException {
        service = new EventManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("activeMenu", "event");

        // ======================
        // 현재 페이지
        // ======================

        int currentPage = 1;

        String pageParam =
                request.getParameter("page");

        if(pageParam != null &&
           !pageParam.trim().isEmpty()) {

            try {
                currentPage =
                        Integer.parseInt(pageParam);
            } catch(Exception e) {
                currentPage = 1;
            }
        }

        // ======================
        // 페이징 계산
        // ======================

        BoardRangeDTO range =
                new BoardRangeDTO();

        int pageScale =
                service.pageScale();

        range.setPage(currentPage);
        range.setPageScale(pageScale);

        range.setStartNum(
                service.startNum(
                        currentPage,
                        pageScale));

        range.setEndNum(
                service.endNum(
                        currentPage,
                        pageScale));

        int totalCount =
                service.totalCount(range);

        range.setTotalCount(totalCount);

        range.setTotalPage(
                service.totalPage(
                        totalCount,
                        pageScale));

        // ======================
        // 이벤트 목록 조회
        // ======================

        List<EventListDTO> eventList =
                service.getEventDashboardList(range);

        // ======================
        // JSP 전달
        // ======================

        request.setAttribute(
                "eventList",
                eventList);

        request.setAttribute(
                "range",
                range);

        request.getRequestDispatcher(
                "/manage/event/eventManagement.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
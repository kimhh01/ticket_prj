package kr.admin.inquiry;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.BoardRangeDTO;

@WebServlet("/inquiry")
public class InquiryManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private InquiryManagementService service;

    @Override
    public void init() throws ServletException {
        service = new InquiryManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 사이드바 활성화
        request.setAttribute("activeMenu", "inquiry");
        
        String status = request.getParameter("status");

        if(status == null || status.trim().isEmpty()) {
            status = "all";
        }

        // ==========================
        // 현재 페이지
        // ==========================
        int currentPage = 1;

        String pageParam = request.getParameter("page");

        if(pageParam != null &&
           !pageParam.trim().isEmpty()) {

            try {
                currentPage = Integer.parseInt(pageParam);
            } catch(Exception e) {
                currentPage = 1;
            }
        }

        // ==========================
        // 페이징 설정
        // ==========================
        BoardRangeDTO range = new BoardRangeDTO();

        int pageScale = service.pageScale();

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
                service.totalCount(status);

        range.setTotalCount(totalCount);

        range.setTotalPage(
                service.totalPage(
                        totalCount,
                        pageScale));

        // ==========================
        // 문의 목록 조회
        // ==========================
        List<InquiryListDTO> inquiryList =
                service.getInquiryDashboardList(range,status);

        // ==========================
        // JSP 전달
        // ==========================
        request.setAttribute(
                "inquiryList",
                inquiryList);

        request.setAttribute(
                "range",
                range);
        
        request.setAttribute("activeMenu", "inquiry");
        request.setAttribute("inquiryList", inquiryList);
        request.setAttribute("range", range);

        request.setAttribute("totalCount", service.totalCount("all"));
        request.setAttribute("waitingCount", service.totalCount("waiting"));
        request.setAttribute("completeCount", service.totalCount("complete"));

        request.getRequestDispatcher("/manage/inquiry/inquiryManagement.jsp")
               .forward(request, response);
    }

    @Override       
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
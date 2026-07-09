package kr.admin.dashboard;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        urlPatterns = "/admin/dashboard",
        loadOnStartup = 1
)
public class AdminDashboardController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MainManagementService mainManagementService;

    @Override
    public void init() throws ServletException {
        mainManagementService =
                new MainManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        refreshDashboardData(request);

        RequestDispatcher rd =
                request.getRequestDispatcher(
                        "/manage/dashboard/main.jsp");

        rd.forward(request, response);
    }

    public void refreshDashboardData(HttpServletRequest request) {

        String startDate =
                request.getParameter("startDate");

        String endDate =
                request.getParameter("endDate");

        if (startDate == null) {
            startDate = "";
        }

        if (endDate == null) {
            endDate = "";
        }

        startDate =
                startDate.trim();

        endDate =
                endDate.trim();

        request.setAttribute("activeMenu", "dashboard");
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        request.setAttribute(
                "totalBooking",
                mainManagementService.getTotalBooking(
                        startDate,
                        endDate));

        request.setAttribute(
                "bookingTrend",
                mainManagementService.getBookingTrend(
                        startDate,
                        endDate));

        request.setAttribute(
                "totalMember",
                mainManagementService.getTotalMember(
                        startDate,
                        endDate));

        request.setAttribute(
                "memberTrend",
                mainManagementService.getMemberTrend(
                        startDate,
                        endDate));

        request.setAttribute(
                "totalInquiry",
                mainManagementService.getTotalInquiry(
                        startDate,
                        endDate));

        request.setAttribute(
                "inquiryTrend",
                mainManagementService.getInquiryTrend(
                        startDate,
                        endDate));

        request.setAttribute(
                "totalRevenue",
                mainManagementService.getTotalRevenue(
                        startDate,
                        endDate));

        request.setAttribute(
                "revenueTrend",
                mainManagementService.getRevenueTrend(
                        startDate,
                        endDate));

        DashboardChartDTO bookingData =
                mainManagementService.getBookingData(
                        startDate,
                        endDate);

        List<MonthlyChartDTO> monthlyData =
                mainManagementService.getMonthlyTrends(
                        startDate,
                        endDate);

        List<DailyChartDTO> dailyData =
                mainManagementService.getDailyTrends(
                        startDate,
                        endDate);

        request.setAttribute("bookingData", bookingData);
        request.setAttribute("monthlyData", monthlyData);
        request.setAttribute("dailyData", dailyData);

        System.out.println("===== Dashboard 검색 조건 =====");
        System.out.println("startDate = " + startDate);
        System.out.println("endDate = " + endDate);
    }
}
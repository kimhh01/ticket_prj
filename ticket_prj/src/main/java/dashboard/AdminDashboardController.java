package dashboard;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns="/manage/dashboard",
	loadOnStartup=1
)
public class AdminDashboardController extends HttpServlet {

	private MainManagementService mainManagementService;

	@Override
	public void init() throws ServletException {
		mainManagementService = new MainManagementService();
	}//init

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String result = refreshDashboardData(request);
		
		if (result.equals("success")) {
			RequestDispatcher rd = request.getRequestDispatcher("/manage/dashboard/main.jsp");
			rd.forward(request, response);
		}

	}//doGet

	public String refreshDashboardData(HttpServletRequest request) {

		// 카드 영역
		request.setAttribute("totalBooking", mainManagementService.getTotalBooking());
		request.setAttribute("totalMember", mainManagementService.getTotalMember());
		request.setAttribute("totalInquiry", mainManagementService.getTotalInquiry());
		request.setAttribute("totalRevenue", mainManagementService.getTotalRevenue());

		// 차트 영역
		DashboardChartDTO bookingData = mainManagementService.getBookingData();
		List<MonthlyChartDTO> monthlyData = mainManagementService.getMonthlyTrends();
		List<DailyChartDTO> dailyData = mainManagementService.getDailyTrends();
		request.setAttribute("bookingData", bookingData);
		request.setAttribute("monthlyData", monthlyData);
		request.setAttribute("dailyData", dailyData);
		request.setAttribute("activeMenu", "dashboard");
		return "success";
	}// refreshDashboardData

}// class
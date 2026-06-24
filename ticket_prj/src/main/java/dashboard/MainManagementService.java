package dashboard;

import java.util.List;

public class MainManagementService {

    private MainManagementDAO mainManagementDAO;

    public MainManagementService(){
        mainManagementDAO = new MainManagementDAO();
    }

    public int getTotalBooking(){
    	System.out.println("Service getTotalBooking 실행");
        return mainManagementDAO.selectTotalBooking();
    }

    public int getBookingTrend(){
        return mainManagementDAO.selectBookingTrend();
    }

    public int getTotalMember(){
        return mainManagementDAO.selectTotalMember();
    }

    public int getMemberTrend(){
        return mainManagementDAO.selectMemberTrend();
    }

    public int getTotalInquiry(){
        return mainManagementDAO.selectTotalInquiry();
    }

    public int getInquiryTrend(){
        return mainManagementDAO.selectInquiryTrend();
    }

    public long getTotalRevenue(){
        return mainManagementDAO.selectTotalRevenue();
    }

    public double getRevenueTrend(){
        return mainManagementDAO.selectRevenueTrend();
    }

    public List<MonthlyChartDTO> getMonthlyTrends(){
        return mainManagementDAO.selectMonthlySalesData();
    }

    public DashboardChartDTO getBookingData(){
        return mainManagementDAO.selectBookingCnt();
    }

    public List<DailyChartDTO> getDailyTrends(){
        return mainManagementDAO.selectDailySalesData();
    }

}//class
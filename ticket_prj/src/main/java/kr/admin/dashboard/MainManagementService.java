package kr.admin.dashboard;

import java.util.List;

public class MainManagementService {

    private MainManagementDAO mainManagementDAO;

    public MainManagementService() {
        mainManagementDAO =
                new MainManagementDAO();
    }

    public int getTotalBooking() {
        return mainManagementDAO.selectTotalBooking();
    }

    public int getTotalBooking(String startDate,
                               String endDate) {
        return mainManagementDAO.selectTotalBooking(
                startDate,
                endDate);
    }

    public int getBookingTrend() {
        return mainManagementDAO.selectBookingTrend();
    }

    public int getTotalMember() {
        return mainManagementDAO.selectTotalMember();
    }

    public int getMemberTrend() {
        return mainManagementDAO.selectMemberTrend();
    }

    public int getTotalInquiry() {
        return mainManagementDAO.selectTotalInquiry();
    }

    public int getInquiryTrend() {
        return mainManagementDAO.selectInquiryTrend();
    }

    public long getTotalRevenue() {
        return mainManagementDAO.selectTotalRevenue();
    }

    public long getTotalRevenue(String startDate,
                                String endDate) {
        return mainManagementDAO.selectTotalRevenue(
                startDate,
                endDate);
    }

    public double getRevenueTrend() {
        return mainManagementDAO.selectRevenueTrend();
    }

    public List<MonthlyChartDTO> getMonthlyTrends() {
        return mainManagementDAO.selectMonthlySalesData();
    }

    public List<MonthlyChartDTO> getMonthlyTrends(String startDate,
                                                  String endDate) {
        return mainManagementDAO.selectMonthlySalesData(
                startDate,
                endDate);
    }

    public DashboardChartDTO getBookingData() {
        return mainManagementDAO.selectBookingCnt();
    }

    public DashboardChartDTO getBookingData(String startDate,
                                            String endDate) {
        return mainManagementDAO.selectBookingCnt(
                startDate,
                endDate);
    }

    public List<DailyChartDTO> getDailyTrends() {
        return mainManagementDAO.selectDailySalesData();
    }

    public List<DailyChartDTO> getDailyTrends(String startDate,
                                              String endDate) {
        return mainManagementDAO.selectDailySalesData(
                startDate,
                endDate);
    }
}
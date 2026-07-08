package kr.admin.dashboard;

import java.util.List;

public class DashboardChartDTO {
	
	private List<MonthlyChartDTO> monthlyChartData;
    private int bookingCount;
    private int cancelCount;
    private int totalCount;
    private List<DailyChartDTO> dailyChartData;
    
	public DashboardChartDTO() {
	}//DashboardChartDTO

	public DashboardChartDTO(List<MonthlyChartDTO> monthlyChartData, int bookingCount, int cancelCount, int totalCount,
			List<DailyChartDTO> dailyChartData) {
		this.monthlyChartData = monthlyChartData;
		this.bookingCount = bookingCount;
		this.cancelCount = cancelCount;
		this.totalCount = totalCount;
		this.dailyChartData = dailyChartData;
	}//DashboardChartDTO

	public List<MonthlyChartDTO> getMonthlyChartData() {
		return monthlyChartData;
	}//getMonthlyChartData

	public void setMonthlyChartData(List<MonthlyChartDTO> monthlyChartData) {
		this.monthlyChartData = monthlyChartData;
	}//setMonthlyChartData

	public int getBookingCount() {
		return bookingCount;
	}//getBookingCount

	public void setBookingCount(int bookingCount) {
		this.bookingCount = bookingCount;
	}//setBookingCount

	public int getCancelCount() {
		return cancelCount;
	}//getCancelCount

	public void setCancelCount(int cancelCount) {
		this.cancelCount = cancelCount;
	}//setCancelCount

	public int getTotalCount() {
		return totalCount;
	}//getTotalCount

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}//setTotalCount

	public List<DailyChartDTO> getDailyChartData() {
		return dailyChartData;
	}//getDailyChartData

	public void setDailyChartData(List<DailyChartDTO> dailyChartData) {
		this.dailyChartData = dailyChartData;
	}//setDailyChartData
    
}//class
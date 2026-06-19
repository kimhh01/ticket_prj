package dashboard;

public class MonthlyChartDTO {
	
	private String month;

    private int count;

    public MonthlyChartDTO() {
    }//MonthlyChartDTO


    public MonthlyChartDTO(String month, int count) {
        this.month = month;
        this.count = count;
    }//MonthlyChartDTO


    public String getMonth() {
        return month;
    }//getMonth

    public void setMonth(String month) {
        this.month = month;
    }//setMonth

    public int getCount() {
        return count;
    }//getCount

    public void setCount(int count) {
        this.count = count;
    }//setCount
    
}

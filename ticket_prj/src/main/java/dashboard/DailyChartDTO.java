package dashboard;

public class DailyChartDTO {
	
	private String date;

    private int count;

    public DailyChartDTO() {
    }//DailyChartDTO

    public DailyChartDTO(String date, int count) {
        this.date = date;
        this.count = count;
    }//DailyChartDTO

    public String getDate() {
        return date;
    }//getDate


    public void setDate(String date) {
        this.date = date;
    }//setDate

    public int getCount() {
        return count;
    }//getCount

    public void setCount(int count) {
        this.count = count;
    }//setCount
    
}

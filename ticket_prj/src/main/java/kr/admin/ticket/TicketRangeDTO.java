package kr.admin.ticket;

public class TicketRangeDTO {
	private int page;
    private int pageScale;
    private int startNum;
    private int endNum;
    private int totalCount;
    private int totalPage;

    public int getPage() {
        return page;
    }//getPage

    public void setPage(int page) {
        this.page = page;
    }//setPage

    public int getPageScale() {
        return pageScale;
    }//getPageScale

    public void setPageScale(int pageScale) {
        this.pageScale = pageScale;
    }//setPageScale

    public int getStartNum() {
        return startNum;
    }//getStartNum

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }//setStartNum

    public int getEndNum() {
        return endNum;
    }//getEndNum

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }//setEndNum

    public int getTotalCount() {
        return totalCount;
    }//getTotalCount

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }//setTotalCount

    public int getTotalPage() {
        return totalPage;
    }//getTotalPage

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }//setTotalPage
    
}//class

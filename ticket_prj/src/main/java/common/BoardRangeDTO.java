package common;

public class BoardRangeDTO {
	private int page;
	private int pageScale;
	private int startNum;
	private int endNum;
	private int totalCount;
	private int totalPage;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageScale() {
		return pageScale;
	}
	public void setPageScale(int pageScale) {
		this.pageScale = pageScale;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	@Override
	public String toString() {
		return "BoardRangeDTO [page=" + page + ", pageScale=" + pageScale + ", startNum=" + startNum + ", endNum="
				+ endNum + ", totalCount=" + totalCount + ", totalPage=" + totalPage + "]";
	}
	
	
}

package userMypage;

import java.sql.Date;

public class MyPageReservationDTO {

    // 예매 기본 정보
    private int reservationCode;
    private String memberId;

    // 경기 정보
    private String gameName;
    private String homeTeamName;
    private String awayTeamName;
    private Date gameDate;
    private String gameStartTime;
    private Date cancelAvailableDate;

    // 경기장 / 좌석 정보
    private String stadiumName;
    private String seatName;
    private String seatInfo;

    // 티켓 정보
    private String ticketName;
    private int reservationQuantity;

    // 예매 정보
    private Date reservationDate;
    private String reservationStatus;

    // 결제 정보
    private int paymentAmount;
    private Date paymentDate;
    private String paymentStatus;
    private int totalPrice;

    // 페이징
    private int currentPage;
    private int pageSize;
    private int startNum;
    private int endNum;
    private int totalCount;
    private int totalPage;

    // 검색
    private Date startDate;
    private Date endDate;
    private int selectedYear;
    private int selectedMonth;
    private String periodType;
    private String tabType;


	public MyPageReservationDTO(int reservationCode, String memberId, String gameName, String homeTeamName,
			String awayTeamName, Date gameDate, String stadiumName, String seatName, String seatInfo, String ticketName,
			int reservationQuantity, Date reservationDate, String reservationStatus, int paymentAmount,
			Date paymentDate, String paymentStatus, int totalPrice, int currentPage, int pageSize, int startNum,
			int endNum, int totalCount, int totalPage, Date startDate,String gameStartTime, Date endDate, int selectedYear,
			int selectedMonth, String periodType, String tabType) {
		super();
		this.reservationCode = reservationCode;
		this.memberId = memberId;
		this.gameName = gameName;
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.gameDate = gameDate;
		//시작 시간 추가
		this.gameStartTime = gameStartTime;
		
		this.stadiumName = stadiumName;
		this.seatName = seatName;
		this.seatInfo = seatInfo;
		this.ticketName = ticketName;
		this.reservationQuantity = reservationQuantity;
		this.reservationDate = reservationDate;
		this.reservationStatus = reservationStatus;
		this.paymentAmount = paymentAmount;
		this.paymentDate = paymentDate;
		this.paymentStatus = paymentStatus;
		this.totalPrice = totalPrice;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.startNum = startNum;
		this.endNum = endNum;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.startDate = startDate;
		this.endDate = endDate;
		this.selectedYear = selectedYear;
		this.selectedMonth = selectedMonth;
		this.periodType = periodType;
		this.tabType = tabType;
	}

    
    public int getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(int reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}
	
	
	//경기 시간 추가
	public String getGameStartTime() {
		return gameStartTime;
	}

	public void setGameStartTime(String gameStartTime) {
		this.gameStartTime = gameStartTime;
	}

	//취소가능일 추가: 경기날짜-7
	public Date getCancelAvailableDate() {
	    return cancelAvailableDate;
	}

	public void setCancelAvailableDate(Date cancelAvailableDate) {
	    this.cancelAvailableDate = cancelAvailableDate;
	}
	
	public String getStadiumName() {
		return stadiumName;
	}

	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getSeatInfo() {
		return seatInfo;
	}

	public void setSeatInfo(String seatInfo) {
		this.seatInfo = seatInfo;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public int getReservationQuantity() {
		return reservationQuantity;
	}

	public void setReservationQuantity(int reservationQuantity) {
		this.reservationQuantity = reservationQuantity;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public int getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(int selectedYear) {
		this.selectedYear = selectedYear;
	}

	public int getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(int selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	
	@Override
	public String toString() {
		return "MyPageReservationDTO [reservationCode=" + reservationCode + ", memberId=" + memberId + ", gameName="
				+ gameName + ", homeTeamName=" + homeTeamName + ", awayTeamName=" + awayTeamName + ", gameDate="
				+ gameDate + ", stadiumName=" + stadiumName + ", seatName=" + seatName + ", seatInfo=" + seatInfo
				+ ", ticketName=" + ticketName + ", reservationQuantity=" + reservationQuantity + ", reservationDate="
				+ reservationDate + ", reservationStatus=" + reservationStatus + ", paymentAmount=" + paymentAmount
				+ ", paymentDate=" + paymentDate + ", paymentStatus=" + paymentStatus + ", totalPrice=" + totalPrice
				+ ", currentPage=" + currentPage + ", pageSize=" + pageSize + ", startNum=" + startNum + ", endNum="
				+ endNum + ", totalCount=" + totalCount + ", totalPage=" + totalPage + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", selectedYear=" + selectedYear + ", selectedMonth=" + selectedMonth
				+ ", periodType=" + periodType + ", tabType=" + tabType + "]";
	}
	
	public MyPageReservationDTO() {
    }

}
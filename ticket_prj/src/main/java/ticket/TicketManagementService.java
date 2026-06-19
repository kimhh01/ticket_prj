package ticket;

import java.util.List;

public class TicketManagementService {
	
	private final TicketManagementDAO dao;

    public TicketManagementService(TicketManagementDAO dao) {
        this.dao = dao;
    }//TicketManagementService

    public int totalCount(TicketRangeDTO range) {
        return dao.selectTotalCount(range);
    }//totalCount

    public int pageScale() {
        return 10;
    }//pageScale

    public int totalPage(int totalCount,int pageScale) {
        return (int)Math.ceil(
                (double)totalCount / pageScale
        );
    }//totalPage

    public int startNum(int currentPage,int pageScale) {
        return (currentPage - 1) * pageScale + 1;
    }//startNum

    public int endNum(int currentPage,int pageScale) {
        return currentPage * pageScale;
    }//endNum

    public List<TicketMatchListDTO> getMatchDashboardList(TicketRangeDTO range) {
        return dao.selectMatchDashboard(range);
    }//getMatchDashboardList

    public List<TicketZoneInfoDTO> getZoneStatusList(int scheduleCode) {
        return dao.selectZoneInfo(scheduleCode);
    }//getZoneStatusList

    public List<TicketZoneSalesDTO> getZoneSalesList(int scheduleCode) {
        return dao.selectZoneSales(scheduleCode);
    }//getZoneSalesList

    public List<TicketReservationViewDTO> getReservationDetailList(int seatCode) {
        return dao.selectReservationDetail(seatCode);
    }//getReservationDetailList

    public List<TicketSearchDTO> searchReservations(
            String startDate,String endDate,String team,String stadium,String phone) {
        return null;
    }//searchReservations

    public boolean cancelReservation(int reservationCode) {
        int result = dao.cancelReservation(reservationCode);
        return result > 0;
    }//cancelReservation
}

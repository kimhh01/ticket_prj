package ticket;

import java.util.List;

public class TicketManagementDAO {
	
	public int selectTotalCount(TicketRangeDTO range) {
        return 0;
    }//selectTotalCount

    public List<TicketMatchListDTO> selectMatchDashboard(
            TicketRangeDTO range) {
        return null;
    }//selectMatchDashboard

    // 구역 대시보드 조회
    public List<TicketReservationListDTO> selectZoneDashboard(TicketRangeDTO range) {
        return null;
    }//selectZoneDashboard

    // 구역 정보 조회
    public List<TicketZoneInfoDTO> selectZoneInfo(
            int scheduleCode) {
        return null;
    }//selectZoneInfo

    public List<TicketSalesDTO> selectZoneSales(
            int scheduleCode) {
        return null;
    }//selectZoneSales

    public List<TicketReservationViewDTO> selectReservationDetail(
            int seatCode) {
        return null;
    }//selectReservationDetail

    public List<TicketReservationViewDTO> selectSearchReservations(
    		String startDate,String endDate,String team,String stadium,String phone) {
        return null;
    }//selectSearchReservations

    public int cancelReservation(
            int reservationCode) {
        return 0;
    }//cancelReservation

}//class

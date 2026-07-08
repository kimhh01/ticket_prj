package kr.admin.ticket;

import java.util.List;

import kr.admin.common.BoardRangeDTO;

public class TicketManagementService {

    private final TicketManagementDAO dao;

    public TicketManagementService() {
        this.dao = new TicketManagementDAO();
    }

    public TicketManagementService(TicketManagementDAO dao) {
        this.dao = dao;
    }

    public int pageScale() {
        return 10;
    }

    public BoardRangeDTO makeRange(int page, int totalCount) {

        int pageScale = pageScale();

        int totalPage = (int)Math.ceil((double)totalCount / pageScale);

        if (totalPage == 0) {
            totalPage = 1;
        }

        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        int startNum = (page - 1) * pageScale + 1;
        int endNum = page * pageScale;

        BoardRangeDTO range = new BoardRangeDTO();
        range.setPage(page);
        range.setPageScale(pageScale);
        range.setStartNum(startNum);
        range.setEndNum(endNum);
        range.setTotalCount(totalCount);
        range.setTotalPage(totalPage);

        return range;
    }

    public int totalCount() {
        return dao.selectTotalCount();
    }

    public List<TicketMatchListDTO> getMatchDashboardList(BoardRangeDTO range) {
        return dao.selectMatchDashboard(range);
    }

    public TicketMatchListDTO getMatchDetail(int scheduleCode) {
        return dao.selectMatchDetail(scheduleCode);
    }

    public List<TicketZoneInfoDTO> getZoneStatusList(int scheduleCode) {
        return dao.selectZoneInfo(scheduleCode);
    }

    public TicketZoneInfoDTO getZoneDetail(int scheduleCode, int zoneCode) {
        return dao.selectZoneDetail(scheduleCode, zoneCode);
    }

    public TicketSalesDTO getTicketSales(int scheduleCode) {
        return dao.selectTicketSales(scheduleCode);
    }

    public List<TicketInfoDTO> getTicketInfoList(int scheduleCode) {
        return dao.selectTicketInfoList(scheduleCode);
    }

    public TicketInfoDTO getTicketInfo(int scheduleCode, int seatCode) {
        return dao.selectTicketInfo(scheduleCode, seatCode);
    }

    public int reservationTotalCount(int scheduleCode, int zoneCode) {
        return dao.selectReservationTotalCount(scheduleCode, zoneCode);
    }

    public List<TicketReservationListDTO> getReservationList(
            int scheduleCode,
            int zoneCode,
            BoardRangeDTO range) {

        return dao.selectReservationList(scheduleCode, zoneCode, range);
    }

    public int searchTotalCount(
            String startDate,
            String endDate,
            String team,
            String stadium,
            String phone) {

        return dao.selectSearchTotalCount(startDate, endDate, team, stadium, phone);
    }

    public List<TicketSearchDTO> searchReservations(
            String startDate,
            String endDate,
            String team,
            String stadium,
            String phone,
            BoardRangeDTO range) {

        return dao.selectSearchReservations(
                startDate,
                endDate,
                team,
                stadium,
                phone,
                range
        );
    }

    public List<String> getTeamNameList() {
        return dao.selectTeamNameList();
    }

    public List<String> getStadiumNameList() {
        return dao.selectStadiumNameList();
    }

    public boolean cancelReservation(int reservationCode) {
        int result = dao.cancelReservation(reservationCode);
        return result > 0;
    }

}
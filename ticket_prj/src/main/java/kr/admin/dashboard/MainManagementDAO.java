package kr.admin.dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

import kr.admin.common.AdminDBConnection;

public class MainManagementDAO {

    private boolean hasText(String value) {
        return value != null &&
               !value.trim().isEmpty();
    }

    private void appendReservationDateCondition(StringBuilder sql,
                                                String startDate,
                                                String endDate) {

        if (hasText(startDate)) {
            sql.append(" AND reservation_date >= TO_DATE(?, 'YYYY-MM-DD') ");
        }

        if (hasText(endDate)) {
            sql.append(" AND reservation_date < TO_DATE(?, 'YYYY-MM-DD') + 1 ");
        }
    }

    private int bindReservationDateCondition(PreparedStatement ps,
                                             int index,
                                             String startDate,
                                             String endDate)
            throws Exception {

        if (hasText(startDate)) {
            ps.setString(index, startDate.trim());
            index++;
        }

        if (hasText(endDate)) {
            ps.setString(index, endDate.trim());
            index++;
        }

        return index;
    }

    // 총 예매 수 - 전체
    public int selectTotalBooking() {
        return selectTotalBooking("", "");
    }

    // 총 예매 수 - 기간 검색
    public int selectTotalBooking(String startDate,
                                  String endDate) {

        int result = 0;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT COUNT(*) ");
        sql.append("   FROM reservation ");
        sql.append("  WHERE 1 = 1 ");

        appendReservationDateCondition(
                sql,
                startDate,
                endDate);

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            bindReservationDateCondition(
                    ps,
                    1,
                    startDate,
                    endDate);

            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    result =
                            rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int selectBookingTrend() {
        return 0;
    }

    // 총 회원 수
    public int selectTotalMember() {

        int result = 0;

        String sql =
                " SELECT COUNT(*) " +
                "   FROM member ";

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            if (rs.next()) {
                result =
                        rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int selectMemberTrend() {
        return 0;
    }

    // 총 문의 수
    public int selectTotalInquiry() {

        int result = 0;

        String sql =
                " SELECT COUNT(*) " +
                "   FROM inquiry ";

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            if (rs.next()) {
                result =
                        rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int selectInquiryTrend() {
        return 0;
    }

    // 총 결제 금액 - 전체
    public long selectTotalRevenue() {
        return selectTotalRevenue("", "");
    }

    // 총 결제 금액 - 기간 검색
    public long selectTotalRevenue(String startDate,
                                   String endDate) {

        long result = 0;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT NVL(SUM(total_price), 0) ");
        sql.append("   FROM reservation ");
        sql.append("  WHERE 1 = 1 ");

        appendReservationDateCondition(
                sql,
                startDate,
                endDate);

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            bindReservationDateCondition(
                    ps,
                    1,
                    startDate,
                    endDate);

            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    result =
                            rs.getLong(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public double selectRevenueTrend() {
        return 0.0;
    }

    // 월별 예매 차트 - 전체
    public List<MonthlyChartDTO> selectMonthlySalesData() {
        return selectMonthlySalesData("", "");
    }

    // 월별 예매 차트 - 기간 검색
    public List<MonthlyChartDTO> selectMonthlySalesData(String startDate,
                                                        String endDate) {

        List<MonthlyChartDTO> list =
                new ArrayList<>();

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT TO_CHAR(reservation_date, 'YYYY-MM') AS month, ");
        sql.append("        COUNT(*) AS cnt ");
        sql.append("   FROM reservation ");
        sql.append("  WHERE 1 = 1 ");

        appendReservationDateCondition(
                sql,
                startDate,
                endDate);

        sql.append("  GROUP BY TO_CHAR(reservation_date, 'YYYY-MM') ");
        sql.append("  ORDER BY month ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            bindReservationDateCondition(
                    ps,
                    1,
                    startDate,
                    endDate);

            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                while (rs.next()) {

                    MonthlyChartDTO dto =
                            new MonthlyChartDTO(
                                    rs.getString("month"),
                                    rs.getInt("cnt"));

                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 예매 상태 - 전체
    public DashboardChartDTO selectBookingCnt() {
        return selectBookingCnt("", "");
    }

    // 예매 상태 - 기간 검색
    public DashboardChartDTO selectBookingCnt(String startDate,
                                              String endDate) {

        DashboardChartDTO dto =
                new DashboardChartDTO();

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT NVL(SUM(CASE WHEN pay_state = '구매' THEN 1 ELSE 0 END), 0) AS booking, ");
        sql.append("        NVL(SUM(CASE WHEN pay_state = '취소' THEN 1 ELSE 0 END), 0) AS cancel ");
        sql.append("   FROM reservation ");
        sql.append("  WHERE 1 = 1 ");

        appendReservationDateCondition(
                sql,
                startDate,
                endDate);

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            bindReservationDateCondition(
                    ps,
                    1,
                    startDate,
                    endDate);

            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {

                    int bookingCount =
                            rs.getInt("booking");

                    int cancelCount =
                            rs.getInt("cancel");

                    dto.setBookingCount(bookingCount);
                    dto.setCancelCount(cancelCount);
                    dto.setTotalCount(
                            bookingCount + cancelCount);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    // 일별 예매 - 전체
    public List<DailyChartDTO> selectDailySalesData() {
        return selectDailySalesData("", "");
    }

    // 일별 예매 - 기간 검색
    public List<DailyChartDTO> selectDailySalesData(String startDate,
                                                    String endDate) {

        List<DailyChartDTO> list =
                new ArrayList<>();

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT TO_CHAR(reservation_date, 'YYYY-MM-DD') AS dt, ");
        sql.append("        COUNT(*) AS cnt ");
        sql.append("   FROM reservation ");
        sql.append("  WHERE 1 = 1 ");

        appendReservationDateCondition(
                sql,
                startDate,
                endDate);

        sql.append("  GROUP BY TO_CHAR(reservation_date, 'YYYY-MM-DD') ");
        sql.append("  ORDER BY dt ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            bindReservationDateCondition(
                    ps,
                    1,
                    startDate,
                    endDate);

            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                while (rs.next()) {

                    DailyChartDTO dto =
                            new DailyChartDTO(
                                    rs.getString("dt"),
                                    rs.getInt("cnt"));

                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
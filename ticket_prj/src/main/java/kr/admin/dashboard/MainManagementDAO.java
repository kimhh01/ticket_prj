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

 // 예매 증감
    public int selectBookingTrend() {
        return selectBookingTrend("", "");
    }

    // 예매 증감 - 기간 검색
    public int selectBookingTrend(String startDate,
                                  String endDate) {

        int result = 0;

        StringBuilder sql =
                new StringBuilder();

        if (hasText(startDate) && hasText(endDate)) {

            sql.append(" SELECT ");
            sql.append("        NVL(SUM(CASE ");
            sql.append("            WHEN reservation_date >= TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("             AND reservation_date < TO_DATE(?, 'YYYY-MM-DD') + 1 ");
            sql.append("            THEN 1 ELSE 0 END), 0) ");
            sql.append("      - NVL(SUM(CASE ");
            sql.append("            WHEN reservation_date >= TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("                 - ((TO_DATE(?, 'YYYY-MM-DD') + 1) - TO_DATE(?, 'YYYY-MM-DD')) ");
            sql.append("             AND reservation_date < TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("            THEN 1 ELSE 0 END), 0) AS trend ");
            sql.append("   FROM reservation ");

        } else {

            sql.append(" SELECT ");
            sql.append("        NVL(SUM(CASE ");
            sql.append("            WHEN reservation_date >= TRUNC(SYSDATE, 'MM') ");
            sql.append("             AND reservation_date < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1) ");
            sql.append("            THEN 1 ELSE 0 END), 0) ");
            sql.append("      - NVL(SUM(CASE ");
            sql.append("            WHEN reservation_date >= ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1) ");
            sql.append("             AND reservation_date < TRUNC(SYSDATE, 'MM') ");
            sql.append("            THEN 1 ELSE 0 END), 0) AS trend ");
            sql.append("   FROM reservation ");
        }

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            if (hasText(startDate) && hasText(endDate)) {
                ps.setString(1, startDate.trim());
                ps.setString(2, endDate.trim());
                ps.setString(3, startDate.trim());
                ps.setString(4, endDate.trim());
                ps.setString(5, startDate.trim());
                ps.setString(6, startDate.trim());
            }

            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    result =
                            rs.getInt("trend");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

 // 총 회원 수 - 전체
    public int selectTotalMember() {
        return selectTotalMember("", "");
    }

    // 총 회원 수 - 기간 검색
    public int selectTotalMember(String startDate,
                                 String endDate) {

        int result = 0;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT COUNT(*) ");
        sql.append("   FROM member ");
        sql.append("  WHERE 1 = 1 ");

        if (hasText(startDate)) {
            sql.append(" AND join_date >= TO_DATE(?, 'YYYY-MM-DD') ");
        }

        if (hasText(endDate)) {
            sql.append(" AND join_date < TO_DATE(?, 'YYYY-MM-DD') + 1 ");
        }

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            int index = 1;

            if (hasText(startDate)) {
                ps.setString(index, startDate.trim());
                index++;
            }

            if (hasText(endDate)) {
                ps.setString(index, endDate.trim());
                index++;
            }

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

 // 회원 증감
    public int selectMemberTrend() {
        return selectMemberTrend("", "");
    }

    // 회원 증감 - 기간 검색
    public int selectMemberTrend(String startDate,
                                 String endDate) {

        int result = 0;

        StringBuilder sql =
                new StringBuilder();

        if (hasText(startDate) && hasText(endDate)) {

            sql.append(" SELECT ");
            sql.append("        NVL(SUM(CASE ");
            sql.append("            WHEN join_date >= TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("             AND join_date < TO_DATE(?, 'YYYY-MM-DD') + 1 ");
            sql.append("            THEN 1 ELSE 0 END), 0) ");
            sql.append("      - NVL(SUM(CASE ");
            sql.append("            WHEN join_date >= TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("                 - ((TO_DATE(?, 'YYYY-MM-DD') + 1) - TO_DATE(?, 'YYYY-MM-DD')) ");
            sql.append("             AND join_date < TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("            THEN 1 ELSE 0 END), 0) AS trend ");
            sql.append("   FROM member ");

        } else {

            sql.append(" SELECT ");
            sql.append("        NVL(SUM(CASE ");
            sql.append("            WHEN join_date >= TRUNC(SYSDATE, 'MM') ");
            sql.append("             AND join_date < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1) ");
            sql.append("            THEN 1 ELSE 0 END), 0) ");
            sql.append("      - NVL(SUM(CASE ");
            sql.append("            WHEN join_date >= ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1) ");
            sql.append("             AND join_date < TRUNC(SYSDATE, 'MM') ");
            sql.append("            THEN 1 ELSE 0 END), 0) AS trend ");
            sql.append("   FROM member ");
        }

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            if (hasText(startDate) && hasText(endDate)) {
                ps.setString(1, startDate.trim());
                ps.setString(2, endDate.trim());
                ps.setString(3, startDate.trim());
                ps.setString(4, endDate.trim());
                ps.setString(5, startDate.trim());
                ps.setString(6, startDate.trim());
            }

            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    result =
                            rs.getInt("trend");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // 총 문의 수 - 전체
    public int selectTotalInquiry() {
        return selectTotalInquiry("", "");
    }

    // 총 문의 수 - 기간 검색
    public int selectTotalInquiry(String startDate,
                                  String endDate) {

        int result = 0;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT COUNT(*) ");
        sql.append("   FROM inquiry ");
        sql.append("  WHERE 1 = 1 ");

        if (hasText(startDate)) {
            sql.append(" AND inquiry_date >= TO_DATE(?, 'YYYY-MM-DD') ");
        }

        if (hasText(endDate)) {
            sql.append(" AND inquiry_date < TO_DATE(?, 'YYYY-MM-DD') + 1 ");
        }

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            int index = 1;

            if (hasText(startDate)) {
                ps.setString(index, startDate.trim());
                index++;
            }

            if (hasText(endDate)) {
                ps.setString(index, endDate.trim());
                index++;
            }

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

    // 문의 증감
    public int selectInquiryTrend() {
        return selectInquiryTrend("", "");
    }

    // 문의 증감 - 기간 검색
    public int selectInquiryTrend(String startDate,
                                  String endDate) {

        int result = 0;

        StringBuilder sql =
                new StringBuilder();

        if (hasText(startDate) && hasText(endDate)) {

            sql.append(" SELECT ");
            sql.append("        NVL(SUM(CASE ");
            sql.append("            WHEN inquiry_date >= TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("             AND inquiry_date < TO_DATE(?, 'YYYY-MM-DD') + 1 ");
            sql.append("            THEN 1 ELSE 0 END), 0) ");
            sql.append("      - NVL(SUM(CASE ");
            sql.append("            WHEN inquiry_date >= TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("                 - ((TO_DATE(?, 'YYYY-MM-DD') + 1) - TO_DATE(?, 'YYYY-MM-DD')) ");
            sql.append("             AND inquiry_date < TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("            THEN 1 ELSE 0 END), 0) AS trend ");
            sql.append("   FROM inquiry ");

        } else {

            sql.append(" SELECT ");
            sql.append("        NVL(SUM(CASE ");
            sql.append("            WHEN inquiry_date >= TRUNC(SYSDATE, 'MM') ");
            sql.append("             AND inquiry_date < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1) ");
            sql.append("            THEN 1 ELSE 0 END), 0) ");
            sql.append("      - NVL(SUM(CASE ");
            sql.append("            WHEN inquiry_date >= ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1) ");
            sql.append("             AND inquiry_date < TRUNC(SYSDATE, 'MM') ");
            sql.append("            THEN 1 ELSE 0 END), 0) AS trend ");
            sql.append("   FROM inquiry ");
        }

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            if (hasText(startDate) && hasText(endDate)) {
                ps.setString(1, startDate.trim());
                ps.setString(2, endDate.trim());
                ps.setString(3, startDate.trim());
                ps.setString(4, endDate.trim());
                ps.setString(5, startDate.trim());
                ps.setString(6, startDate.trim());
            }

            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    result =
                            rs.getInt("trend");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
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

 // 매출 증감률
    public double selectRevenueTrend() {
        return selectRevenueTrend("", "");
    }

    // 매출 증감률 - 기간 검색
    public double selectRevenueTrend(String startDate,
                                     String endDate) {

        double result = 0.0;

        long currentRevenue = 0;
        long previousRevenue = 0;

        StringBuilder sql =
                new StringBuilder();

        if (hasText(startDate) && hasText(endDate)) {

            sql.append(" SELECT ");
            sql.append("        NVL(SUM(CASE ");
            sql.append("            WHEN reservation_date >= TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("             AND reservation_date < TO_DATE(?, 'YYYY-MM-DD') + 1 ");
            sql.append("            THEN total_price ELSE 0 END), 0) AS current_revenue, ");
            sql.append("        NVL(SUM(CASE ");
            sql.append("            WHEN reservation_date >= TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("                 - ((TO_DATE(?, 'YYYY-MM-DD') + 1) - TO_DATE(?, 'YYYY-MM-DD')) ");
            sql.append("             AND reservation_date < TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append("            THEN total_price ELSE 0 END), 0) AS previous_revenue ");
            sql.append("   FROM reservation ");

        } else {

            sql.append(" SELECT ");
            sql.append("        NVL(SUM(CASE ");
            sql.append("            WHEN reservation_date >= TRUNC(SYSDATE, 'MM') ");
            sql.append("             AND reservation_date < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1) ");
            sql.append("            THEN total_price ELSE 0 END), 0) AS current_revenue, ");
            sql.append("        NVL(SUM(CASE ");
            sql.append("            WHEN reservation_date >= ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1) ");
            sql.append("             AND reservation_date < TRUNC(SYSDATE, 'MM') ");
            sql.append("            THEN total_price ELSE 0 END), 0) AS previous_revenue ");
            sql.append("   FROM reservation ");
        }

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
        ) {

            if (hasText(startDate) && hasText(endDate)) {
                ps.setString(1, startDate.trim());
                ps.setString(2, endDate.trim());
                ps.setString(3, startDate.trim());
                ps.setString(4, endDate.trim());
                ps.setString(5, startDate.trim());
                ps.setString(6, startDate.trim());
            }

            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {
                    currentRevenue =
                            rs.getLong("current_revenue");

                    previousRevenue =
                            rs.getLong("previous_revenue");
                }
            }

            if (previousRevenue == 0) {
                if (currentRevenue == 0) {
                    result = 0.0;
                } else {
                    result = 100.0;
                }
            } else {
                result =
                        ((double)(currentRevenue - previousRevenue)
                                / previousRevenue) * 100.0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
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

        sql.append(" SELECT ");
        sql.append("        NVL(SUM(CASE ");
        sql.append("            WHEN reservation_status IN ('구매완료', '구매') ");
        sql.append("            THEN 1 ELSE 0 END), 0) AS booking, ");

        sql.append("        NVL(SUM(CASE ");
        sql.append("            WHEN reservation_status IN ('취소', '취소완료') ");
        sql.append("            THEN 1 ELSE 0 END), 0) AS cancel, ");

        sql.append("        NVL(SUM(CASE ");
        sql.append("            WHEN reservation_status IN ('대기중', '대기') ");
        sql.append("            THEN 1 ELSE 0 END), 0) AS waiting ");

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

                    int waitingCount =
                            rs.getInt("waiting");

                    dto.setBookingCount(bookingCount);
                    dto.setCancelCount(cancelCount);
                    dto.setWaitingCount(waitingCount);
                    dto.setTotalCount(
                            bookingCount + cancelCount + waitingCount);
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
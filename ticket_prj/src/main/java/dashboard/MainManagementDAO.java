package dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
import common.DBConnection;

public class MainManagementDAO {

	private File getDBFile() {

		String path = MainManagementDAO.class.getClassLoader().getResource("properties/database.properties").getPath();

		File file = new File(path);

		System.out.println("DB 파일 경로 : " + file.getAbsolutePath());

		System.out.println("파일 존재 : " + file.exists());

		return file;
	}

	// 총 예매 수
	public int selectTotalBooking() {
		System.out.println("DAO selectTotalBooking 진입");
		int result = 0;
		String sql = "SELECT COUNT(*) FROM RESERVATION";

		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			System.out.println("DB 연결 확인 : " + con);
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}// selectTotalBooking

	public int selectBookingTrend() {
		return 0;
	}// selectBookingTrend

	// 총 회원 수
	public int selectTotalMember() {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM MEMBER";

		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}// selectTotalMember

	public int selectMemberTrend() {
		return 0;
	}// selectMemberTrend

	// 총 문의 수
	public int selectTotalInquiry() {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM INQUIRY";
		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}// selectTotalInquiry

	public int selectInquiryTrend() {
		return 0;
	}// selectInquiryTrend

	// 총 결제 금액
	public long selectTotalRevenue() {
		long result = 0;
		String sql = "SELECT NVL(SUM(TOTAL_PRICE),0) FROM RESERVATION";
		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			if (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}// selectTotalRevenue

	public double selectRevenueTrend() {
		return 0.0;
	}// selectRevenueTrend

	// 월별 예매 차트
	public List<MonthlyChartDTO> selectMonthlySalesData() {

		List<MonthlyChartDTO> list = new ArrayList<>();

		String sql = "SELECT TO_CHAR(RESERVATION_DATE,'MM') MONTH, COUNT(*) CNT FROM RESERVATION "
				+ "GROUP BY TO_CHAR(RESERVATION_DATE,'MM') ORDER BY MONTH";

		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				list.add(new MonthlyChartDTO(rs.getString("MONTH"), rs.getInt("CNT")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}// selectMonthlySalesData

	// 예매 상태
	public DashboardChartDTO selectBookingCnt() {
		DashboardChartDTO dto = new DashboardChartDTO();

		String sql = "SELECT SUM(CASE WHEN PAY_STATE='구매' THEN 1 ELSE 0 END) BOOKING, "
				+ "SUM(CASE WHEN PAY_STATE='취소' THEN 1 ELSE 0 END) CANCEL FROM RESERVATION";

		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			if (rs.next()) {
				dto.setBookingCount(rs.getInt("BOOKING"));

				dto.setCancelCount(rs.getInt("CANCEL"));
				dto.setTotalCount(rs.getInt("BOOKING") + rs.getInt("CANCEL"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}// selectBookingCnt

	// 일별 예매
	public List<DailyChartDTO> selectDailySalesData() {
		List<DailyChartDTO> list = new ArrayList<>();
		String sql = "SELECT TO_CHAR(RESERVATION_DATE,'MM-DD') DT, COUNT(*) CNT FROM RESERVATION "
				+ "GROUP BY TO_CHAR(RESERVATION_DATE,'MM-DD') " + "ORDER BY DT";

		try (Connection con = DBConnection.getInstance().getConnection(getDBFile());
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				list.add(new DailyChartDTO(rs.getString("DT"), rs.getInt("CNT")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}// selectDailySalesData

}

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>

<%@ page import="admin.DashboardChartDTO"%>
<%@ page import="admin.MonthlyChartDTO"%>
<%@ page import="admin.DailyChartDTO"%>

<%
Integer totalBooking = (Integer) request.getAttribute("totalBooking");
Integer totalMember = (Integer) request.getAttribute("totalMember");
Integer totalInquiry = (Integer) request.getAttribute("totalInquiry");
Long totalRevenue = (Long) request.getAttribute("totalRevenue");
DashboardChartDTO chartDTO = (DashboardChartDTO) request.getAttribute("bookingData");
List<MonthlyChartDTO> monthlyList = (List<MonthlyChartDTO>) request.getAttribute("monthlyData");
List<DailyChartDTO> dailyList = (List<DailyChartDTO>) request.getAttribute("dailyData");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 메인</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<style>
body {
	margin: 0;
	font-family: "Noto Sans KR", sans-serif;
	background: #fff;
}

.dashboard {
	margin-left: 150px;
	padding: 25px 35px;
}

.title {
	font-size: 24px;
	font-weight: bold;
}

.sub {
	font-size: 13px;
	color: #777;
	margin-bottom: 25px;
}

.card-wrap {
	display: flex;
	gap: 15px;
	margin-bottom: 20px;
}

.card {
	flex: 1;
	height: 90px;
	border: 1px solid #eee;
	border-radius: 10px;
	padding: 18px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, .05);
}

.card-title {
	font-size: 12px;
	color: #777;
}

.card-value {
	font-size: 25px;
	font-weight: bold;
	margin-top: 10px;
}

.chart-row {
	display: flex;
	gap: 20px;
	margin-bottom: 20px;
}

.chart-box {
	background: white;
	border: 1px solid #eee;
	border-radius: 10px;
	padding: 20px;
	flex: 1;
}

.chart-title {
	font-size: 14px;
	font-weight: bold;
	margin-bottom: 15px;
}

canvas {
	max-height: 260px;
}
</style>

</head>

<body>
	<%@ include file="../common/topbar.jsp"%>
	<%@ include file="../common/sidebar.jsp"%>

	<div class="dashboard">
		<div class="title">관리자 메인</div>
		<div class="sub">ticketLINK 야구 관리자 페이지에 오신 것을 환영합니다.</div>
		<div class="card-wrap">
			<div class="card">
				<div class="card-title">총 예매 건수</div>
				<div class="card-value">
					<%=totalBooking%>건
				</div>
			</div>
			<div class="card">
				<div class="card-title">총 회원 수</div>
				<div class="card-value">
					<%=totalMember%>명
				</div>
			</div>
			<div class="card">
				<div class="card-title">총 문의 수</div>
				<div class="card-value">
					<%=totalInquiry%>건
				</div>
			</div>
			<div class="card">
				<div class="card-title">총 결제 금액</div>
				<div class="card-value">
					<%=String.format("%,d", totalRevenue)%>원
				</div>
			</div>
		</div>
		<div class="chart-row">
			<div class="chart-box">
				<div class="chart-title">예매 매출 현황</div>
				<canvas id="salesChart"></canvas>
			</div>
			<div class="chart-box" style="max-width: 320px;">
				<div class="chart-title">예매 상태 비율</div>
				<canvas id="statusChart"></canvas>
			</div>
		</div>
		<div class="chart-box">
			<div class="chart-title">일별 예매 건수 추이</div>
			<canvas id="dailyChart"></canvas>
		</div>
	</div>
	<script>
		$(function() {
			new Chart(
					$("#salesChart"),
					{
						type : "bar",
						data : {
							labels : [
	<%for (int i = 0; i < monthlyList.size(); i++) {
	if (i > 0)
		out.print(",");
	out.print("'" + monthlyList.get(i).getMonth() + "'");
}%>
		],
							datasets : [
									{
										label : "매출",
										data : [
	<%for (int i = 0; i < monthlyList.size(); i++) {
	if (i > 0)
		out.print(",");
	out.print(monthlyList.get(i).getRevenue());
}%>
		]
									},
									{
										type : "line",
										label : "예매 건수",
										data : [
	<%for (int i = 0; i < monthlyList.size(); i++) {
	if (i > 0)
		out.print(",");
	out.print(monthlyList.get(i).getCount());
}%>
		],
										tension : .4
									} ]
						},
						options : {
							responsive : true
						}
					});

			new Chart($("#statusChart"), {
				type : "doughnut",
				data : {
					labels : [ "예매 완료", "취소", "대기" ],
					datasets : [ {
						data : [
	<%=chartDTO.getBookingCount()%>,
	<%=chartDTO.getCancelCount()%>,
	<%=chartDTO.getTotalCount()%>
		]
					} ]
				},
				options : {
					cutout : "65%"
				}
			});

			new Chart(
					$("#dailyChart"),
					{
						type : "line",
						data : {
							labels : [
	<%for (int i = 0; i < dailyList.size(); i++) {
	if (i > 0)
		out.print(",");
	out.print("'" + dailyList.get(i).getDate() + "'");
}%>
		],
							datasets : [ {
								label : "예매 건수",
								data : [
	<%for (int i = 0; i < dailyList.size(); i++) {
	if (i > 0)
		out.print(",");
	out.print(dailyList.get(i).getCount());
}%>
		],
								tension : .4
							} ]
						},
						options : {
							responsive : true,
							plugins : {
								legend : {
									display : false
								}
							}
						}
					});
		});
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>

<%@ page import="dashboard.DashboardChartDTO"%>
<%@ page import="dashboard.MonthlyChartDTO"%>
<%@ page import="dashboard.DailyChartDTO"%>


<%
Number totalBooking = (Number) request.getAttribute("totalBooking");
Number totalMember = (Number) request.getAttribute("totalMember");
Number totalInquiry = (Number) request.getAttribute("totalInquiry");
Number totalRevenue = (Number) request.getAttribute("totalRevenue");

if (totalBooking == null)
	totalBooking = 0;
if (totalMember == null)
	totalMember = 0;
if (totalInquiry == null)
	totalInquiry = 0;
if (totalRevenue == null)
	totalRevenue = 0;

DashboardChartDTO chartDTO = (DashboardChartDTO) request.getAttribute("bookingData");

if (chartDTO == null) {
	chartDTO = new DashboardChartDTO();
}

List<MonthlyChartDTO> monthlyList = (List<MonthlyChartDTO>) request.getAttribute("monthlyData");

if (monthlyList == null) {
	monthlyList = new java.util.ArrayList<MonthlyChartDTO>();
}

List<DailyChartDTO> dailyList = (List<DailyChartDTO>) request.getAttribute("dailyData");

if (dailyList == null) {
	dailyList = new java.util.ArrayList<DailyChartDTO>();
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 메인</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
body {
	margin: 0;
	font-family: "Noto Sans KR", sans-serif;
	background: #f6f7fb;
}

.dashboard-header {
	display: flex;
	justify-content: space-between;
	align-items: flex-start;
	margin-bottom: 25px;
}

.dashboard-filter {
	display: flex;
	align-items: center;
	gap: 12px;
}

.dashboard {
	margin-left: 150px;
	padding: 25px 35px;
}

.title {
	font-size: 30px;
	font-weight: 700;
}

.date-box {
	background: #fff;
	border: 1px solid #ddd;
	border-radius: 8px;
	padding: 8px 12px;
	display: flex;
	align-items: center;
	gap: 8px;
}

.date-box input {
	border: none;
	outline: none;
	font-size: 12px;
}

.refresh-btn {
	background: #ff3030;
	color: white;
	border: none;
	border-radius: 8px;
	padding: 9px 15px;
	cursor: pointer;
	font-size: 12px;
}

.sub {
	margin-top: 8px;
	margin-bottom: 30px;
	color: #777;
}

.card-wrap {
	display: grid;
	grid-template-columns: repeat(4, 1fr);
	gap: 18px;
	margin-bottom: 25px;
}

.card {
	background: #fff;
	border-radius: 16px;
	border: 1px solid #eee;
	padding: 22px;
	height: 110px;
	box-shadow: 0 3px 12px rgba(0, 0, 0, .05);
}

.card-title {
	font-size: 13px;
	color: #777;
}

.card-value {
	margin-top: 14px;
	font-size: 27px;
	font-weight: 700;
}

.chart-row {
	display: grid;
	grid-template-columns: 3fr 1fr;
	gap: 20px;
	margin-bottom: 25px;
}

.chart-box {
	background: white;
	border-radius: 16px;
	padding: 25px;
	border: 1px solid #eee;
	box-shadow: 0 3px 12px rgba(0, 0, 0, .05);
}

.chart-title {
	font-weight: 700;
	font-size: 15px;
	margin-bottom: 20px;
}

canvas {
	max-height: 280px;
}
</style>
</head>
<body>

	<!-- 공통 영역 -->
	<%@ include file="../common/topBar.jsp"%>
	<%@ include file="../common/sideBar.jsp"%>

	<div class="dashboard">
		<div class="dashboard-header">
			<div>
				<div class="title">관리자 메인</div>
				<div class="sub">ticketLINK 야구 관리자 페이지에 오신 것을 환영합니다.</div>
			</div>
			<div class="dashboard-filter">
				<div class="date-box">
					<input type="date" id="startDate"> <span> ~ </span> <input
						type="date" id="endDate">
				</div>
				<button class="refresh-btn" onclick="location.reload();">
					새로고침</button>
			</div>
		</div>
		<div class="card-wrap">
			<div class="card">
				<div class="card-title">총 예매 건수</div>
				<div class="card-value">
					<%=totalBooking == null ? 0 : totalBooking.intValue()%>건
				</div>
			</div>
			<div class="card">
				<div class="card-title">총 회원 수</div>
				<div class="card-value">
					<%=totalMember == null ? 0 : totalMember.intValue()%>명
				</div>
			</div>
			<div class="card">
				<div class="card-title">총 문의 수</div>
				<div class="card-value">
					<%=totalInquiry == null ? 0 : totalInquiry.intValue()%>건
				</div>
			</div>
			<div class="card">
				<div class="card-title">총 결제 금액</div>
				<div class="card-value">
					<%=String.format("%,d", totalRevenue == null ? 0 : totalRevenue.longValue())%>원
				</div>
			</div>
		</div>
		<div class="chart-row">
			<div class="chart-box">
				<div class="chart-title">예매 현황</div>
				<canvas id="salesChart"></canvas>
			</div>
			<div class="chart-box">
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
			let today = new Date().toISOString().substring(0, 10);

			$("#startDate").attr("max", today);
			$("#endDate").attr("max", today);

			$("#startDate").on("change", function() {
				let start = $(this).val();
				$("#endDate").attr("min", start);

				if ($("#endDate").val() && $("#endDate").val() < start) {
					$("#endDate").val("");
				}
			});

			$("#endDate").on("change", function() {
				let end = $(this).val();
				$("#startDate").attr("max", end);

				if ($("#startDate").val() && $("#startDate").val() > end) {
					$("#startDate").val("");
				}
			});

			new Chart(document.getElementById("salesChart"),
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
							datasets : [ {
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

			new Chart(document.getElementById("statusChart"), {
				type : "doughnut",
				data : {
					labels : [ "예매 완료", "취소" ],
					datasets : [ {
						data : [
	<%=chartDTO.getBookingCount()%>
		,
	<%=chartDTO.getCancelCount()%>
		]
					} ]
				},
				options : {
					cutout : "65%"
				}
			});

			new Chart(
					document.getElementById("dailyChart"),
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
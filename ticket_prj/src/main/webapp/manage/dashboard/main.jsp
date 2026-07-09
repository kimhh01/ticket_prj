<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="kr.admin.dashboard.DashboardChartDTO"%>
<%@ page import="kr.admin.dashboard.MonthlyChartDTO"%>
<%@ page import="kr.admin.dashboard.DailyChartDTO"%>

<%
    /*
     * 이 JSP는 직접 실행하면 안 됨.
     *
     * 정상 흐름:
     * /admin/dashboard
     *      → AdminDashboardController
     *      → request.setAttribute(...)
     *      → /manage/dashboard/main.jsp forward
     *
     * 직접 접근:
     * /manage/dashboard/main.jsp
     *      → 데이터 없음
     *      → /admin/dashboard로 redirect
     */
    Object forwardRequestUri =
            request.getAttribute("javax.servlet.forward.request_uri");

    if (forwardRequestUri == null) {
        response.sendRedirect(
                request.getContextPath()
                + "/admin/dashboard");
        return;
    }

    request.setAttribute("activeMenu", "dashboard");

    Number totalBooking =
            (Number) request.getAttribute("totalBooking");

    Number totalMember =
            (Number) request.getAttribute("totalMember");

    Number totalInquiry =
            (Number) request.getAttribute("totalInquiry");

    Number totalRevenue =
            (Number) request.getAttribute("totalRevenue");

    if (totalBooking == null) {
        totalBooking = 0;
    }

    if (totalMember == null) {
        totalMember = 0;
    }

    if (totalInquiry == null) {
        totalInquiry = 0;
    }

    if (totalRevenue == null) {
        totalRevenue = 0;
    }

    DashboardChartDTO chartDTO =
            (DashboardChartDTO) request.getAttribute("bookingData");

    if (chartDTO == null) {
        chartDTO = new DashboardChartDTO();
    }

    List<MonthlyChartDTO> monthlyList =
            (List<MonthlyChartDTO>) request.getAttribute("monthlyData");

    if (monthlyList == null) {
        monthlyList =
                new java.util.ArrayList<MonthlyChartDTO>();
    }

    List<DailyChartDTO> dailyList =
            (List<DailyChartDTO>) request.getAttribute("dailyData");

    if (dailyList == null) {
        dailyList =
                new java.util.ArrayList<DailyChartDTO>();
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 메인</title>

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/tabler-icons.min.css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2"></script>

<style>
* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

body {
    font-family: "Noto Sans KR", sans-serif;
    background: #F5F5F5;
}

/* ── Topbar ── */
.topbar {
    display: flex;
    align-items: center;
    justify-content: space-between;

    height: 56px;
    padding: 0 24px;

    background: #fff;
    border-bottom: 1px solid #E5E7EB;

    position: sticky;
    top: 0;
    z-index: 100;
}

.topbar-left {
    display: flex;
    align-items: center;
    gap: 8px;
}

.topbar-logo {
    font-size: 18px;
    font-weight: 400;
    color: #111;
}

.topbar-logo strong {
    font-weight: 700;
}

.topbar-subtitle {
    font-size: 13px;
    color: #6B7280;
}

.topbar-right {
    display: flex;
    align-items: center;
    gap: 16px;

    font-size: 13px;
    color: #6B7280;
}

.topbar-divider {
    width: 1px;
    height: 14px;
    background: #E5E7EB;
}

.topbar-right a {
    color: #6B7280;
    text-decoration: none;
}

.topbar-right a:hover {
    color: #111;
}

/* ── Layout ── */
.layout {
    display: flex;
    min-height: calc(100vh - 56px);
}

/* ── Sidebar ── */
.sidebar {
    width: 200px;
    flex-shrink: 0;

    background: #fff;
    border-right: 1px solid #E5E7EB;

    padding: 12px 0;
}

.nav-item {
    display: flex;
    align-items: center;
    gap: 10px;

    padding: 10px 20px;

    font-size: 14px;
    color: #6B7280;

    cursor: pointer;
}

.nav-item i {
    font-size: 18px;
}

.nav-item:hover {
    background: #F9FAFB;
    color: #111;
}

.nav-item.active {
    background: #FDEDF0;
    color: #C0394B;
    font-weight: 500;
}

/* 세션 표시용 디자인 */
.topbar-admin-name {
    color: #333;
    text-decoration: none;
    font-weight: 600;
}

.topbar-admin-name:hover {
    color: #e9363f;
    text-decoration: underline;
}

.session-timer {
    margin-left: 14px;
    font-size: 13px;
    color: #666;
}

.session-timer strong {
    margin-left: 5px;
    color: #e9363f;
}

.session-extend-btn {
    margin-left: 6px;
    padding: 4px 9px;

    border: 1px solid #ddd;
    border-radius: 5px;

    background: #fff;
    color: #333;

    font-size: 12px;
    cursor: pointer;
}

.session-extend-btn:hover {
    background: #f5f5f5;
}

/* ── Main ── */
.main {
    flex: 1;
    padding: 28px 32px;
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

.title {
    font-size: 30px;
    font-weight: 700;
    color: #111827;
}

.sub {
    margin-top: 8px;
    color: #777;
}

.date-box {
    display: flex;
    align-items: center;
    gap: 8px;

    padding: 8px 12px;

    background: #fff;
    border: 1px solid #ddd;
    border-radius: 8px;
}

.date-box input {
    border: none;
    outline: none;
    font-size: 12px;
}

.refresh-btn {
    padding: 9px 15px;

    background: #ff3030;
    color: white;

    border: none;
    border-radius: 8px;

    cursor: pointer;
    font-size: 12px;
}

.search-btn {
    padding: 9px 15px;

    background: #2563EB;
    color: #fff;

    border: none;
    border-radius: 8px;

    cursor: pointer;
    font-size: 12px;
    font-weight: 600;
}

.search-btn:hover {
    background: #1D4ED8;
}

.card-wrap {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 18px;

    margin-bottom: 25px;
}

.card {
    height: 110px;
    padding: 22px;

    background: #fff;
    border: 1px solid #eee;
    border-radius: 16px;

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
    color: #111827;
}

.chart-row {
    display: grid;
    grid-template-columns: 3fr 1fr;
    gap: 20px;

    margin-bottom: 25px;
}

.chart-box {
    padding: 25px;

    background: #fff;
    border: 1px solid #eee;
    border-radius: 16px;

    box-shadow: 0 3px 12px rgba(0, 0, 0, .05);
}

.chart-title {
    margin-bottom: 20px;

    font-size: 15px;
    font-weight: 700;

    color: #111827;
}

canvas {
    max-height: 310px;
}

@media (max-width: 1100px) {
    .card-wrap {
        grid-template-columns: repeat(2, 1fr);
    }

    .chart-row {
        grid-template-columns: 1fr;
    }
}
</style>
</head>

<body>

<%@ include file="../common/topBar.jsp" %>

<div class="layout">

    <%@ include file="../common/sideBar.jsp" %>

    <main class="main">

        <div class="dashboard-header">

            <div>
                <div class="title">
                    관리자 메인
                </div>

                <div class="sub">
                    ticketLINK 야구 관리자 페이지에 오신 것을 환영합니다.
                </div>
            </div>

            <form class="dashboard-filter"
			      action="${pageContext.request.contextPath}/admin/dashboard"
			      method="get">
			
			    <div class="date-box">
			        <input type="date"
			               id="startDate"
			               name="startDate"
			               value="${startDate}">
			
			        <span>~</span>
			
			        <input type="date"
			               id="endDate"
			               name="endDate"
			               value="${endDate}">
			    </div>
			
			    <button type="submit"
			            class="search-btn">
			        조회
			    </button>
			
			    <button type="button"
			            class="refresh-btn"
			            onclick="location.href='${pageContext.request.contextPath}/admin/dashboard';">
			        새로고침
			    </button>
			
			</form>

        </div>

        <div class="card-wrap">

            <div class="card">
                <div class="card-title">
                    총 예매 건수
                </div>

                <div class="card-value">
                    <%= totalBooking.intValue() %>건
                </div>
            </div>

            <div class="card">
                <div class="card-title">
                    총 회원 수
                </div>

                <div class="card-value">
                    <%= totalMember.intValue() %>명
                </div>
            </div>

            <div class="card">
                <div class="card-title">
                    총 문의 수
                </div>

                <div class="card-value">
                    <%= totalInquiry.intValue() %>건
                </div>
            </div>

            <div class="card">
                <div class="card-title">
                    총 결제 금액
                </div>

                <div class="card-value">
                    <%= String.format("%,d", totalRevenue.longValue()) %>원
                </div>
            </div>

        </div>

        <div class="chart-row">

            <div class="chart-box">
                <div class="chart-title">
                    월별 예매 현황
                </div>

                <canvas id="salesChart"></canvas>
            </div>

            <div class="chart-box">
                <div class="chart-title">
                    예매 상태 비율
                </div>

                <canvas id="statusChart"></canvas>
            </div>

        </div>

        <div class="chart-box">
            <div class="chart-title">
                일별 예매 건수 추이
            </div>

            <canvas id="dailyChart"></canvas>
        </div>

    </main>

</div>

<script>
$(function() {

    if (typeof ChartDataLabels !== 'undefined') {
        Chart.register(ChartDataLabels);
    }

    let today =
            new Date().toISOString().substring(0, 10);

    $("#startDate").attr("max", today);
    $("#endDate").attr("max", today);

    $("#startDate").on("change", function() {

        let start =
                $(this).val();

        $("#endDate").attr("min", start);

        if ($("#endDate").val() &&
            $("#endDate").val() < start) {

            $("#endDate").val("");
        }
    });

    $("#endDate").on("change", function() {

        let end =
                $(this).val();

        $("#startDate").attr("max", end);

        if ($("#startDate").val() &&
            $("#startDate").val() > end) {

            $("#startDate").val("");
        }
    });

    new Chart(
        document.getElementById("salesChart"),
        {
            type: "bar",
            data: {
                labels: [
                    <%
                    for (int i = 0; i < monthlyList.size(); i++) {
                        if (i > 0) {
                            out.print(",");
                        }

                        out.print("'" + monthlyList.get(i).getMonth() + "'");
                    }
                    %>
                ],
                datasets: [
                    {
                        label: "예매 건수",
                        data: [
                            <%
                            for (int i = 0; i < monthlyList.size(); i++) {
                                if (i > 0) {
                                    out.print(",");
                                }

                                out.print(monthlyList.get(i).getCount());
                            }
                            %>
                        ],
                        borderWidth: 1,
                        borderRadius: 8
                    }
                ]
            },
            options: {
                responsive: true,
                layout: {
                    padding: {
                        top: 28
                    }
                },
                plugins: {
                    legend: {
                        display: false
                    },
                    datalabels: {
                        anchor: "end",
                        align: "top",
                        offset: 4,
                        color: "#374151",
                        font: {
                            weight: "700",
                            size: 12
                        },
                        formatter: function(value) {
                            return Number(value).toLocaleString() + "건";
                        }
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return "예매 건수 : "
                                        + Number(context.raw).toLocaleString()
                                        + "건";
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            precision: 0
                        }
                    }
                }
            }
        }
    );

    new Chart(
        document.getElementById("statusChart"),
        {
            type: "doughnut",
            data: {
                labels: [
                    "예매 완료",
                    "취소"
                ],
                datasets: [
                    {
                        data: [
                            <%= chartDTO.getBookingCount() %>,
                            <%= chartDTO.getCancelCount() %>
                        ],
                        borderWidth: 2
                    }
                ]
            },
            options: {
                responsive: true,
                cutout: "62%",
                plugins: {
                    legend: {
                        position: "bottom"
                    },
                    datalabels: {
                        color: "#ffffff",
                        font: {
                            weight: "700",
                            size: 12
                        },
                        formatter: function(value, context) {

                            const data =
                                    context.chart.data.datasets[0].data;

                            const total =
                                    data.reduce(function(sum, item) {
                                        return sum + Number(item);
                                    }, 0);

                            if (total <= 0 || value <= 0) {
                                return "";
                            }

                            const percent =
                                    Math.round(value / total * 100);

                            return Number(value).toLocaleString()
                                    + "건\n"
                                    + percent
                                    + "%";
                        }
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {

                                const data =
                                        context.chart.data.datasets[0].data;

                                const total =
                                        data.reduce(function(sum, item) {
                                            return sum + Number(item);
                                        }, 0);

                                const value =
                                        Number(context.raw);

                                const percent =
                                        total > 0
                                                ? Math.round(value / total * 100)
                                                : 0;

                                return context.label
                                        + " : "
                                        + value.toLocaleString()
                                        + "건 ("
                                        + percent
                                        + "%)";
                            }
                        }
                    }
                }
            }
        }
    );

    new Chart(
        document.getElementById("dailyChart"),
        {
            type: "line",
            data: {
                labels: [
                    <%
                    for (int i = 0; i < dailyList.size(); i++) {
                        if (i > 0) {
                            out.print(",");
                        }

                        out.print("'" + dailyList.get(i).getDate() + "'");
                    }
                    %>
                ],
                datasets: [
                    {
                        label: "예매 건수",
                        data: [
                            <%
                            for (int i = 0; i < dailyList.size(); i++) {
                                if (i > 0) {
                                    out.print(",");
                                }

                                out.print(dailyList.get(i).getCount());
                            }
                            %>
                        ],
                        tension: .4,
                        pointRadius: 4,
                        pointHoverRadius: 6,
                        borderWidth: 2
                    }
                ]
            },
            options: {
                responsive: true,
                layout: {
                    padding: {
                        top: 28
                    }
                },
                plugins: {
                    legend: {
                        display: false
                    },
                    datalabels: {
                        anchor: "end",
                        align: "top",
                        offset: 6,
                        color: "#374151",
                        font: {
                            weight: "700",
                            size: 11
                        },
                        formatter: function(value) {
                            return Number(value).toLocaleString();
                        }
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return "예매 건수 : "
                                        + Number(context.raw).toLocaleString()
                                        + "건";
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            precision: 0
                        }
                    }
                }
            }
        }
    );

});
</script>

</body>
</html>

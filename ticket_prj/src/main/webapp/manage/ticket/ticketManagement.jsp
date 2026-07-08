<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.text.NumberFormat" %>

<%@ page import="kr.admin.ticket.TicketMatchListDTO" %>
<%@ page import="kr.admin.ticket.TicketZoneInfoDTO" %>
<%@ page import="kr.admin.ticket.TicketInfoDTO" %>
<%@ page import="kr.admin.ticket.TicketSalesDTO" %>
<%@ page import="kr.admin.common.BoardRangeDTO" %>

<%!
    private String h(Object value) {
        if (value == null) {
            return "";
        }

        return String.valueOf(value)
                     .replace("&", "&amp;")
                     .replace("<", "&lt;")
                     .replace(">", "&gt;")
                     .replace("\"", "&quot;")
                     .replace("'", "&#39;");
    }

    private String statusClass(String state) {
        if (state == null || state.trim().isEmpty() || "-".equals(state)) {
            return "status-end";
        }

        if (state.contains("예정")) {
            return "status-ready";
        }

        if (state.contains("취소") || state.contains("매진") || state.contains("종료")) {
            return "status-end";
        }

        return "status-progress";
    }
%>

<%
    request.setAttribute("activeMenu", "ticket");

    String contextPath = request.getContextPath();

    BoardRangeDTO range =
            (BoardRangeDTO)request.getAttribute("range");

    List<TicketMatchListDTO> matchList =
            (List<TicketMatchListDTO>)request.getAttribute("matchList");

    Map<Integer, List<TicketZoneInfoDTO>> zoneMap =
            (Map<Integer, List<TicketZoneInfoDTO>>)request.getAttribute("zoneMap");

    Map<Integer, List<TicketInfoDTO>> ticketInfoMap =
            (Map<Integer, List<TicketInfoDTO>>)request.getAttribute("ticketInfoMap");

    Map<Integer, TicketSalesDTO> salesMap =
            (Map<Integer, TicketSalesDTO>)request.getAttribute("salesMap");

    if (range == null) {
        range = new BoardRangeDTO();
        range.setPage(1);
        range.setTotalPage(1);
        range.setTotalCount(0);
    }

    if (zoneMap == null) {
        zoneMap = new HashMap<Integer, List<TicketZoneInfoDTO>>();
    }

    if (ticketInfoMap == null) {
        ticketInfoMap = new HashMap<Integer, List<TicketInfoDTO>>();
    }

    if (salesMap == null) {
        salesMap = new HashMap<Integer, TicketSalesDTO>();
    }

    NumberFormat nf = NumberFormat.getInstance();
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>티켓 관리</title>

<style>
*{
    margin:0;
    padding:0;
    box-sizing:border-box;
}

body{
    font-family:'Noto Sans KR', sans-serif;
    background:#F5F5F5;
}

.layout{
    display:flex;
    min-height:calc(100vh - 56px);
}

.main{
    flex:1;
    padding:28px 32px;
}

.page-header{
    display:flex;
    justify-content:space-between;
    align-items:center;
    margin-bottom:20px;
}

.page-title{
    font-size:35px;
    font-weight:700;
    color:#111827;
}

.card{
    background:#fff;
    border:1px solid #E5E7EB;
    border-radius:12px;
    overflow:hidden;
}

.tabs{
    display:flex;
    gap:28px;
    padding:20px 24px 0;
    border-bottom:1px solid #E5E7EB;
}

.tab{
    padding:0 4px 16px;
    text-decoration:none;
    color:#6B7280;
    font-size:14px;
    font-weight:700;
}

.tab.active{
    color:#EF4444;
    border-bottom:2px solid #EF4444;
}

.match-list{
    padding:0 24px 20px;
}

.match-card{
    border-bottom:1px solid #F3F4F6;
}

.match-card:last-child{
    border-bottom:none;
}

.match-summary{
    width:100%;
    min-height:66px;

    display:grid;
    grid-template-columns:240px 140px 170px 130px 1fr 110px 40px;
    align-items:center;
    gap:12px;

    color:#374151;
    text-decoration:none;

    font-size:14px;
    background:#fff;
    border:none;
    cursor:pointer;
    text-align:left;
}

.match-summary:hover{
    background:#FAFAFA;
}

.match-summary.open{
    background:#FFF7F7;
}

.teams{
    display:flex;
    align-items:center;
    gap:8px;
}

.team-badge{
    min-width:52px;
    height:26px;

    display:inline-flex;
    align-items:center;
    justify-content:center;

    padding:0 8px;
    border-radius:7px;

    background:#EF4444;
    color:#fff;

    font-size:12px;
    font-weight:800;
}

.team-badge.away{
    background:#2563EB;
}

.vs{
    color:#6B7280;
    font-size:12px;
    font-weight:700;
}

.meta{
    color:#6B7280;
    font-size:14px;
}

.arrow{
    color:#9CA3AF;
    font-size:14px;
    text-align:center;
}

.match-detail{
    display:none;
    padding:0 0 22px;
}

.match-detail.open{
    display:block;
}

.sales-box{
    display:grid;
    grid-template-columns:repeat(4, 1fr);
    gap:14px;

    padding:20px 0;
}

.sales-item{
    padding:18px 16px;

    border:1px solid #E5E7EB;
    border-radius:10px;

    background:#F9FAFB;
    text-align:center;
}

.sales-label{
    color:#6B7280;
    font-size:13px;
    margin-bottom:8px;
}

.sales-value{
    color:#111827;
    font-size:20px;
    font-weight:800;
}

.zone-table{
    width:100%;
    border-collapse:collapse;
}

.zone-table thead{
    background:#F9FAFB;
}

.zone-table th{
    padding:18px 12px;

    font-size:14px;
    font-weight:600;
    color:#6B7280;

    border-bottom:1px solid #E5E7EB;
}

.zone-table td{
    padding:16px 12px;

    text-align:center;

    font-size:14px;
    color:#374151;

    border-bottom:1px solid #F3F4F6;
}

.zone-table tbody tr:hover{
    background:#FAFAFA;
}

.zone-link{
    color:#111827;
    font-weight:600;
    text-decoration:none;
}

.zone-link:hover{
    color:#EF4444;
}

.status{
    font-size:13px;
    font-weight:700;
}

.status-progress{
    color:#EF4444;
}

.status-ready{
    color:#2563EB;
}

.status-end{
    color:#9CA3AF;
}

.empty-body{
    padding:80px 20px;

    display:flex;
    flex-direction:column;
    align-items:center;

    gap:12px;

    text-align:center;
}

.empty-icon{
    font-size:42px;
    color:#D1D5DB;
}

.empty-text{
    color:#6B7280;
    font-size:15px;
}

.empty-sub{
    color:#9CA3AF;
    font-size:13px;
}

.pagination{
    display:flex;
    justify-content:center;
    gap:8px;

    padding:24px;
}

.page-btn{
    width:36px;
    height:36px;

    border:1px solid #D1D5DB;
    border-radius:8px;

    background:#fff;

    cursor:pointer;
}

.page-btn:hover{
    background:#F9FAFB;
}

.page-btn.active{
    border-color:#EF4444;
    color:#EF4444;
    font-weight:700;
}
</style>
</head>

<body>

<%@ include file="../common/topBar.jsp" %>

<div class="layout">

    <%@ include file="../common/sideBar.jsp" %>

    <main class="main">

        <div class="page-header">
            <h1 class="page-title">티켓 관리</h1>
        </div>

        <div class="card">

            <div class="tabs">
                <a class="tab active"
                   href="<%=contextPath%>/admin/ticket">
                    티켓 관리
                </a>

                <a class="tab"
                   href="<%=contextPath%>/admin/ticket/search">
                    예매 티켓 검색
                </a>
            </div>

            <div class="match-list">

                <%
                if (matchList == null || matchList.isEmpty()) {
                %>

                    <div class="empty-body">
                        <i class="ti ti-ticket empty-icon"></i>

                        <p class="empty-text">
                            등록된 경기 일정이 없습니다.
                        </p>

                        <p class="empty-sub">
                            경기 일정이 등록되면 티켓 현황을 확인할 수 있습니다.
                        </p>
                    </div>

                <%
                } else {

                    for (TicketMatchListDTO match : matchList) {

                        int scheduleCode = match.getScheduleCode();

                        List<TicketZoneInfoDTO> zoneList =
                                zoneMap.get(scheduleCode);

                        List<TicketInfoDTO> ticketInfoList =
                                ticketInfoMap.get(scheduleCode);

                        TicketSalesDTO sales =
                                salesMap.get(scheduleCode);

                        if (sales == null) {
                            sales = new TicketSalesDTO();
                        }

                        Map<Integer, TicketInfoDTO> infoBySeatCode =
                                new HashMap<Integer, TicketInfoDTO>();

                        if (ticketInfoList != null) {
                            for (TicketInfoDTO info : ticketInfoList) {
                                infoBySeatCode.put(info.getSeatCode(), info);
                            }
                        }
                %>

                    <div class="match-card">

                        <button type="button"
                                class="match-summary"
                                onclick="toggleMatchDetail(<%=scheduleCode%>, this)">

                            <div class="teams">
                                <span class="team-badge">
                                    <%=h(match.getHomeTeam())%>
                                </span>

                                <span class="vs">VS</span>

                                <span class="team-badge away">
                                    <%=h(match.getAwayTeam())%>
                                </span>
                            </div>

                            <div class="meta">
                                <i class="ti ti-calendar"></i>
                                <%=h(match.getGameDate())%>
                            </div>

                            <div class="meta">
                                <i class="ti ti-map-pin"></i>
                                <%=h(match.getStadiumName())%>
                            </div>

                            <div class="meta">
                                <i class="ti ti-clock"></i>
                                <%=h(match.getGameStartTime())%>
                            </div>

                            <div></div>

                            <div>
                                <span class="status <%=statusClass(match.getSaleState())%>">
                                    <%=h(match.getSaleState())%>
                                </span>
                            </div>

                            <div class="arrow" id="arrow_<%=scheduleCode%>">
                                ▼
                            </div>

                        </button>

                        <div class="match-detail"
                             id="detail_<%=scheduleCode%>">

                            <div class="sales-box">

                                <div class="sales-item">
                                    <div class="sales-label">전체 예매</div>
                                    <div class="sales-value">
                                        <%=nf.format(sales.getTotalBookedCnt())%>매
                                    </div>
                                </div>

                                <div class="sales-item">
                                    <div class="sales-label">일반 예매</div>
                                    <div class="sales-value">
                                        <%=nf.format(sales.getGeneralBookedCnt())%>매
                                    </div>
                                </div>

                                <div class="sales-item">
                                    <div class="sales-label">취소 예매</div>
                                    <div class="sales-value">
                                        <%=nf.format(sales.getCancelBookedCnt())%>매
                                    </div>
                                </div>

                                <div class="sales-item">
                                    <div class="sales-label">총 매출</div>
                                    <div class="sales-value">
                                        <%=nf.format(sales.getTotalPrice())%>원
                                    </div>
                                </div>

                            </div>

                            <table class="zone-table">

                                <thead>
                                    <tr>
                                        <th>구역</th>
                                        <th style="width:140px;">티켓 종류</th>
                                        <th style="width:140px;">가격</th>
                                        <th style="width:120px;">전체 좌석</th>
                                        <th style="width:120px;">예매 완료</th>
                                        <th style="width:120px;">잔여 좌석</th>
                                        <th style="width:120px;">상태</th>
                                    </tr>
                                </thead>

                                <tbody>

                                <%
                                if (zoneList == null || zoneList.isEmpty()) {
                                %>

                                    <tr>
                                        <td colspan="7">
                                            <div class="empty-body">
                                                <i class="ti ti-armchair empty-icon"></i>

                                                <p class="empty-text">
                                                    등록된 구역 정보가 없습니다.
                                                </p>
                                            </div>
                                        </td>
                                    </tr>

                                <%
                                } else {

                                    for (TicketZoneInfoDTO zone : zoneList) {

                                        TicketInfoDTO info =
                                                infoBySeatCode.get(zone.getZoneCode());

                                        int bookedCount =
                                                zone.getSeatCount() - zone.getRemainSeatCount();

                                        if (bookedCount < 0) {
                                            bookedCount = 0;
                                        }

                                        String ticketType = "-";
                                        int price = 0;
                                        String saleState = match.getSaleState();

                                        if (info != null) {
                                            ticketType = info.getTicketType();
                                            price = info.getPrice();

                                            if (info.getSaleState() != null
                                                    && !info.getSaleState().trim().isEmpty()) {
                                                saleState = info.getSaleState();
                                            }
                                        }
                                %>

                                    <tr>
                                        <td>
                                            <a class="zone-link"
                                               href="<%=contextPath%>/admin/ticket/zone?scheduleCode=<%=scheduleCode%>&zoneCode=<%=zone.getZoneCode()%>">
                                                <%=h(zone.getZoneName())%>
                                            </a>
                                        </td>

                                        <td><%=h(ticketType)%></td>

                                        <td>
                                            <%=nf.format(price)%>원
                                        </td>

                                        <td>
                                            <%=nf.format(zone.getSeatCount())%>
                                        </td>

                                        <td>
                                            <%=nf.format(bookedCount)%>
                                        </td>

                                        <td>
                                            <%=nf.format(zone.getRemainSeatCount())%>
                                        </td>

                                        <td>
                                            <span class="status <%=statusClass(saleState)%>">
                                                <%=h(saleState)%>
                                            </span>
                                        </td>
                                    </tr>

                                <%
                                    }
                                }
                                %>

                                </tbody>

                            </table>

                        </div>

                    </div>

                <%
                    }
                }
                %>

            </div>

            <div class="pagination">

                <%
                for (int i = 1; i <= range.getTotalPage(); i++) {
                %>

                    <button type="button"
                            class="page-btn <%=i == range.getPage() ? "active" : ""%>"
                            onclick="location.href='<%=contextPath%>/admin/ticket?page=<%=i%>'">
                        <%=i%>
                    </button>

                <%
                }
                %>

            </div>

        </div>

    </main>

</div>

<script>
function toggleMatchDetail(scheduleCode, button) {
    var detail = document.getElementById("detail_" + scheduleCode);
    var arrow = document.getElementById("arrow_" + scheduleCode);

    if (!detail) {
        return;
    }

    if (detail.classList.contains("open")) {
        detail.classList.remove("open");
        button.classList.remove("open");

        if (arrow) {
            arrow.innerHTML = "▼";
        }
    } else {
        detail.classList.add("open");
        button.classList.add("open");

        if (arrow) {
            arrow.innerHTML = "▲";
        }
    }
}
</script>

</body>
</html>
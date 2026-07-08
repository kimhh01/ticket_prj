<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>

<%@ page import="kr.admin.ticket.TicketMatchListDTO" %>
<%@ page import="kr.admin.ticket.TicketZoneInfoDTO" %>
<%@ page import="kr.admin.ticket.TicketInfoDTO" %>
<%@ page import="kr.admin.ticket.TicketReservationListDTO" %>
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

    TicketMatchListDTO match =
            (TicketMatchListDTO)request.getAttribute("match");

    TicketZoneInfoDTO zone =
            (TicketZoneInfoDTO)request.getAttribute("zone");

    TicketInfoDTO ticketInfo =
            (TicketInfoDTO)request.getAttribute("ticketInfo");

    BoardRangeDTO range =
            (BoardRangeDTO)request.getAttribute("range");

    List<TicketReservationListDTO> reservationList =
            (List<TicketReservationListDTO>)request.getAttribute("reservationList");

    Integer scheduleCodeObj =
            (Integer)request.getAttribute("scheduleCode");

    Integer zoneCodeObj =
            (Integer)request.getAttribute("zoneCode");

    int scheduleCode =
            scheduleCodeObj == null ? 0 : scheduleCodeObj.intValue();

    int zoneCode =
            zoneCodeObj == null ? 0 : zoneCodeObj.intValue();

    if (range == null) {
        range = new BoardRangeDTO();
        range.setPage(1);
        range.setTotalPage(1);
        range.setTotalCount(0);
    }

    String returnUrl =
            contextPath
          + "/admin/ticket/zone?scheduleCode="
          + scheduleCode
          + "&zoneCode="
          + zoneCode
          + "&page="
          + range.getPage();

    NumberFormat nf = NumberFormat.getInstance();

    int bookedCount = 0;
    int price = 0;
    String ticketType = "-";
    String saleState = "-";

    if (zone != null) {
        bookedCount = zone.getSeatCount() - zone.getRemainSeatCount();

        if (bookedCount < 0) {
            bookedCount = 0;
        }
    }

    if (ticketInfo != null) {
        price = ticketInfo.getPrice();
        ticketType = ticketInfo.getTicketType();
        saleState = ticketInfo.getSaleState();
    } else if (match != null) {
        saleState = match.getSaleState();
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>구역 예매 상세</title>

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

.back-btn{
    display:flex;
    align-items:center;
    gap:6px;

    border:1px solid #D1D5DB;
    background:#fff;
    color:#374151;

    padding:11px 18px;
    border-radius:8px;

    font-size:14px;
    font-weight:600;

    cursor:pointer;
}

.back-btn:hover{
    background:#F9FAFB;
}

.card{
    background:#fff;
    border:1px solid #E5E7EB;
    border-radius:12px;
    overflow:hidden;
}

.detail-header{
    padding:22px 24px;
    border-bottom:1px solid #E5E7EB;
}

.detail-title{
    font-size:20px;
    font-weight:700;
    color:#111827;
    margin-bottom:10px;
}

.detail-meta{
    display:flex;
    align-items:center;
    gap:12px;
    flex-wrap:wrap;

    font-size:14px;
    color:#6B7280;
}

.sales-box{
    display:grid;
    grid-template-columns:repeat(4, 1fr);
    gap:14px;

    padding:20px 24px;
    border-bottom:1px solid #E5E7EB;
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

.ticket-table{
    width:100%;
    border-collapse:collapse;
}

.ticket-table thead{
    background:#F9FAFB;
}

.ticket-table th{
    padding:18px 12px;

    font-size:14px;
    font-weight:600;
    color:#6B7280;

    border-bottom:1px solid #E5E7EB;
}

.ticket-table td{
    padding:16px 12px;

    text-align:center;

    font-size:14px;
    color:#374151;

    border-bottom:1px solid #F3F4F6;
}

.ticket-table tbody tr:hover{
    background:#FAFAFA;
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

.action-area{
    display:flex;
    justify-content:center;
    gap:8px;
}

.cancel-btn{
    width:56px;
    height:32px;

    border-radius:6px;

    background:#fff;
    border:1px solid #EF4444;
    color:#EF4444;

    font-size:13px;
    cursor:pointer;
}

.cancel-btn:hover{
    background:#FEF2F2;
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

            <h1 class="page-title">구역 예매 상세</h1>

        </div>

        <div class="card">

            <%
            if (match == null || zone == null) {
            %>

                <div class="empty-body">
                    <i class="ti ti-alert-circle empty-icon"></i>

                    <p class="empty-text">
                        구역 정보를 찾을 수 없습니다.
                    </p>

                    <p class="empty-sub">
                        다시 티켓 관리 페이지에서 구역을 선택해 주세요.
                    </p>
                </div>

            <%
            } else {
            %>

                <div class="detail-header">

                    <div class="detail-title">
                        <%=h(zone.getZoneName())%>
                        /
                        <%=h(ticketType)%>
                    </div>

                    <div class="detail-meta">
                        <span>
                            <%=h(match.getHomeTeam())%>
                            VS
                            <%=h(match.getAwayTeam())%>
                        </span>

                        <span>·</span>

                        <span>
                            <i class="ti ti-calendar"></i>
                            <%=h(match.getGameDate())%>
                        </span>

                        <span>
                            <i class="ti ti-clock"></i>
                            <%=h(match.getGameStartTime())%>
                        </span>

                        <span>
                            <i class="ti ti-map-pin"></i>
                            <%=h(match.getStadiumName())%>
                        </span>

                        <span>·</span>

                        <span class="status <%=statusClass(saleState)%>">
                            <%=h(saleState)%>
                        </span>
                    </div>

                </div>

                <div class="sales-box">

                    <div class="sales-item">
                        <div class="sales-label">전체 좌석</div>
                        <div class="sales-value">
                            <%=nf.format(zone.getSeatCount())%>석
                        </div>
                    </div>

                    <div class="sales-item">
                        <div class="sales-label">예매 완료</div>
                        <div class="sales-value">
                            <%=nf.format(bookedCount)%>석
                        </div>
                    </div>

                    <div class="sales-item">
                        <div class="sales-label">잔여 좌석</div>
                        <div class="sales-value">
                            <%=nf.format(zone.getRemainSeatCount())%>석
                        </div>
                    </div>

                    <div class="sales-item">
                        <div class="sales-label">가격</div>
                        <div class="sales-value">
                            <%=nf.format(price)%>원
                        </div>
                    </div>

                </div>

                <table class="ticket-table">

                    <thead>
                        <tr>
                            <th style="width:100px;">예매 번호</th>
                            <th>예매자명</th>
                            <th style="width:160px;">연락처</th>
                            <th style="width:110px;">예매 수량</th>
                            <th style="width:130px;">결제 금액</th>
                            <th style="width:170px;">예매 날짜</th>
                            <th style="width:110px;">상태</th>
                            <th style="width:110px;">관리</th>
                        </tr>
                    </thead>

                    <tbody>

                    <%
                    if (reservationList == null || reservationList.isEmpty()) {
                    %>

                        <tr>
                            <td colspan="8">
                                <div class="empty-body">
                                    <i class="ti ti-ticket-off empty-icon"></i>

                                    <p class="empty-text">
                                        예매 내역이 없습니다.
                                    </p>
                                </div>
                            </td>
                        </tr>

                    <%
                    } else {

                        for (TicketReservationListDTO reservation : reservationList) {
                    %>

                        <tr>
                            <td>
                                <%=reservation.getReservationCode()%>
                            </td>

                            <td>
                                <%=h(reservation.getMemberName())%>
                            </td>

                            <td>
                                <%=h(reservation.getMemberTel())%>
                            </td>

                            <td>
                                <%=nf.format(reservation.getReservationCnt())%>
                            </td>

                            <td>
                                <%=nf.format(reservation.getPaymentPrice())%>원
                            </td>

                            <td>
                                <%=h(reservation.getReservationDate())%>
                            </td>

                            <td>
                                <span class="status <%=statusClass(reservation.getReservationState())%>">
                                    <%=h(reservation.getReservationState())%>
                                </span>
                            </td>

                            <td>
                                <%
                                if (!"취소".equals(reservation.getReservationState())) {
                                %>

                                    <div class="action-area">
                                        <form method="post"
                                              action="<%=contextPath%>/admin/ticket/cancel"
                                              onsubmit="return confirm('해당 예매를 취소하시겠습니까?');">

                                            <input type="hidden"
                                                   name="reservationCode"
                                                   value="<%=reservation.getReservationCode()%>">

                                            <input type="hidden"
                                                   name="returnUrl"
                                                   value="<%=h(returnUrl)%>">

                                            <button type="submit" class="cancel-btn">
                                                취소
                                            </button>

                                        </form>
                                    </div>

                                <%
                                } else {
                                %>

                                    -

                                <%
                                }
                                %>
                            </td>
                        </tr>

                    <%
                        }
                    }
                    %>

                    </tbody>

                </table>

                <div class="pagination">

                    <%
                    for (int i = 1; i <= range.getTotalPage(); i++) {
                    %>

                        <button type="button"
                                class="page-btn <%=i == range.getPage() ? "active" : ""%>"
                                onclick="location.href='<%=contextPath%>/admin/ticket/zone?scheduleCode=<%=scheduleCode%>&zoneCode=<%=zoneCode%>&page=<%=i%>'">
                            <%=i%>
                        </button>

                    <%
                    }
                    %>

                </div>

            <%
            }
            %>

        </div>

    </main>

</div>

</body>
</html>
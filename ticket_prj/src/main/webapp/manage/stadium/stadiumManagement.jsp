<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="stadium.StadiumListDTO" %>
<%@ page import="stadium.StadiumDetailDTO" %>
<%@ page import="stadium.StadiumSeatDTO" %>

<%
	request.setAttribute("activeMenu", "stadium");

    List<StadiumListDTO> stadiumList =
            (List<StadiumListDTO>)request.getAttribute("stadiumList");

    Map<Integer, StadiumDetailDTO> stadiumDetailMap =
            (Map<Integer, StadiumDetailDTO>)request.getAttribute("stadiumDetailMap");

    Map<Integer, List<StadiumSeatDTO>> seatMap =
            (Map<Integer, List<StadiumSeatDTO>>)request.getAttribute("seatMap");

    if (stadiumList == null) stadiumList = new ArrayList<>();
    if (stadiumDetailMap == null) stadiumDetailMap = new HashMap<>();
    if (seatMap == null) seatMap = new HashMap<>();
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>구장 관리 - ticketLINK</title>

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/tabler-icons.min.css">

<style>
    * { box-sizing: border-box; margin: 0; padding: 0; }

    body {
        font-family: 'Noto Sans KR', sans-serif;
        background: #F5F5F5;
    }

    /* ── Topbar ── */
    .topbar {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 24px;
        height: 56px;
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

    /* ── Main ── */
    .main {
        flex: 1;
        padding: 28px 32px;
    }

    .page-title {
        font-size: 20px;
        font-weight: 500;
        margin-bottom: 20px;
        color: #111;
    }

    .card {
        background: #fff;
        border: 1px solid #E5E7EB;
        border-radius: 12px;
        overflow: hidden;
    }

    .section-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 20px;
        border-bottom: 1px solid #E5E7EB;
    }

    .section-title {
        font-size: 15px;
        font-weight: 500;
        color: #111;
    }

    .add-btn {
        padding: 9px 20px;
        background: #fff;
        border: 1.5px solid #D1D5DB;
        border-radius: 8px;
        font-size: 14px;
        font-weight: 500;
        cursor: pointer;
    }

    .add-btn:hover {
        background: #F9FAFB;
    }

    .stadium-list {
        padding: 20px;
        display: flex;
        flex-direction: column;
        gap: 16px;
    }

    .stadium-card {
        border: 1px solid #E5E7EB;
        border-radius: 12px;
        overflow: hidden;
    }

    .stadium-summary {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px;
        background: #F7F3EA;
    }

    .stadium-left {
        display: flex;
        align-items: center;
        gap: 14px;
    }

    .stadium-icon {
        width: 44px;
        height: 44px;
        border-radius: 10px;
        background: #E9F2FF;
        color: #2563EB;

        display: flex;
        align-items: center;
        justify-content: center;

        font-size: 20px;
    }

    .stadium-name {
        font-size: 18px;
        font-weight: 700;
        margin-bottom: 6px;
    }

    .stadium-meta {
        display: flex;
        gap: 16px;
        flex-wrap: wrap;

        color: #6B7280;
        font-size: 13px;
    }

    .meta-item {
        display: flex;
        align-items: center;
        gap: 6px;
    }

    .toggle-btn {
        width: 36px;
        height: 36px;
        border: 1px solid #D1D5DB;
        background: #fff;
        border-radius: 8px;
        cursor: pointer;
    }

    .stadium-detail {
        display: none;
        padding: 20px;
        border-top: 1px solid #E5E7EB;
    }

    .stadium-card.open .stadium-detail {
        display: block;
    }

    .seat-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 16px;
    }

    .seat-table th,
    .seat-table td {
        padding: 12px;
        border-bottom: 1px solid #F3F4F6;
        text-align: center;
    }

    .seat-table th {
        color: #6B7280;
        font-size: 13px;
        font-weight: 400;
    }

    .empty-body {
        padding: 64px 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 10px;
    }

    .empty-icon {
        font-size: 36px;
        color: #D1D5DB;
    }

    .empty-text {
        font-size: 14px;
        color: #6B7280;
    }

    .empty-sub {
        font-size: 13px;
        color: #9CA3AF;
    }
</style>
</head>

<body>

<%@ include file="../common/topBar.jsp" %>

<div class="layout">

    <%@ include file="../common/sideBar.jsp" %>

    <main class="main">

        <h1 class="page-title">구장 관리</h1>

        <div class="card">

            <div class="section-header">
                <span class="section-title">구장 목록</span>

                <button type="button" class="add-btn">
                    + 구장 등록
                </button>
            </div>

            <div class="stadium-list">

            <%
            if (stadiumList.isEmpty()) {
            %>

                <div class="empty-body">
                    <i class="ti ti-building-stadium empty-icon"></i>

                    <p class="empty-text">
                        등록된 구장 정보가 없습니다.
                    </p>

                    <p class="empty-sub">
                        DB 연결 후 데이터가 표시됩니다.
                    </p>
                </div>

            <%
            } else {

                for (StadiumListDTO stadium : stadiumList) {

                    StadiumDetailDTO detail =
                        stadiumDetailMap.get(stadium.getStadiumCode());

                    List<StadiumSeatDTO> seatList =
                        seatMap.get(stadium.getStadiumCode());

                    if (seatList == null) {
                        seatList = new ArrayList<>();
                    }
            %>

                <div class="stadium-card">

                    <div class="stadium-summary">

                        <div class="stadium-left">

                            <div class="stadium-icon">
                                <i class="ti ti-building-stadium"></i>
                            </div>

                            <div>

                                <div class="stadium-name">
                                    <%= stadium.getStadiumName() %>
                                </div>

                                <div class="stadium-meta">

                                    <span class="meta-item">
                                        <i class="ti ti-map-pin"></i>
                                        <%= stadium.getStadiumLocation() %>
                                    </span>

                                    <span class="meta-item">
                                        <i class="ti ti-users"></i>
                                        <%= String.format("%,d", stadium.getTotalSeats()) %>석
                                    </span>

                                    <span class="meta-item">
                                        <i class="ti ti-shirt"></i>
                                        <%= stadium.getHomeTeamName() %>
                                    </span>

                                </div>

                            </div>

                        </div>

                        <button class="toggle-btn">
                            <i class="ti ti-chevron-down"></i>
                        </button>

                    </div>

                    <div class="stadium-detail">

                        <p style="margin-bottom:16px;">
                            <strong>주소 :</strong>

                            <%= detail != null ? detail.getAddress() : "-" %>
                        </p>

                        <table class="seat-table">

                            <thead>
                                <tr>
                                    <th>좌석명</th>
                                    <th>성인</th>
                                    <th>청소년</th>
                                    <th>어린이</th>
                                    <th>수량</th>
                                </tr>
                            </thead>

                            <tbody>

                            <%
                            for (StadiumSeatDTO seat : seatList) {
                            %>

                                <tr>
                                    <td><%= seat.getSeatName() %></td>
                                    <td><%= String.format("%,d원", seat.getAdultPrice()) %></td>
                                    <td><%= String.format("%,d원", seat.getYouthPrice()) %></td>
                                    <td><%= String.format("%,d원", seat.getChildPrice()) %></td>
                                    <td><%= String.format("%,d석", seat.getSeatQty()) %></td>
                                </tr>

                            <%
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

        </div>

    </main>

</div>

<script>
document.querySelectorAll('.toggle-btn').forEach(function(btn) {

    btn.addEventListener('click', function() {

        const card = this.closest('.stadium-card');

        card.classList.toggle('open');

        const icon = this.querySelector('i');

        icon.className = card.classList.contains('open')
            ? 'ti ti-chevron-up'
            : 'ti ti-chevron-down';
    });

});
</script>

</body>
</html>
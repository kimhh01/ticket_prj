<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page import="kr.admin.event.EventListDTO"%>
<%@page import="kr.admin.common.BoardRangeDTO"%>

<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    request.setAttribute("activeMenu", "event");

    List<EventListDTO> eventList =
            (List<EventListDTO>)request.getAttribute("eventList");

    if(eventList == null){
        eventList = new ArrayList<>();
    }

    BoardRangeDTO range =
            (BoardRangeDTO)request.getAttribute("range");

    if(range == null){
        range = new BoardRangeDTO();

        range.setPage(1);
        range.setTotalPage(1);
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 관리</title>

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/tabler-icons.min.css">

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

/* ───── Layout ───── */

.layout{
    display:flex;
    min-height:calc(100vh - 56px);
}

.main{
    flex:1;
    padding:28px 32px;
}

/* ───── Header ───── */

.page-header{
    display:flex;
    justify-content:space-between;
    align-items:center;

    margin-bottom:20px;
}

.page-title{
    font-size:32px;
    font-weight:700;
    color:#111827;
}

.register-btn{
    display:flex;
    align-items:center;
    gap:6px;

    border:none;
    background:#EF4444;
    color:#fff;

    padding:12px 18px;

    border-radius:8px;

    font-size:14px;
    font-weight:600;

    cursor:pointer;
}

.register-btn:hover{
    background:#DC2626;
}

/* ───── Card ───── */

.card{
    background:#fff;
    border:1px solid #E5E7EB;
    border-radius:12px;
    overflow:hidden;
}

/* ───── Table ───── */

.event-table{
    width:100%;
    border-collapse:collapse;
}

.event-table thead{
    background:#F9FAFB;
}

.event-table th{
    padding:18px 12px;

    font-size:14px;
    font-weight:600;
    color:#6B7280;

    border-bottom:1px solid #E5E7EB;
}

.event-table td{
    padding:16px 12px;

    text-align:center;

    font-size:14px;
    color:#374151;

    border-bottom:1px solid #F3F4F6;
}

.event-table tbody tr:hover{
    background:#FAFAFA;
}

.thumbnail{
    width:150px;
    height:60px;

    object-fit:cover;

    border-radius:8px;
    border:1px solid #E5E7EB;
}

.event-title{
    font-weight:500;
    color:#111827;
}

.period{
    color:#6B7280;
}

/* ───── Status ───── */

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

/* ───── Action Button ───── */

.action-area{
    display:flex;
    justify-content:center;
    gap:8px;
}

.edit-btn,
.delete-btn{

    width:56px;
    height:32px;

    border-radius:6px;

    font-size:13px;

    cursor:pointer;
}

.edit-btn{
    background:#fff;
    border:1px solid #9CA3AF;
    color:#374151;
}

.edit-btn:hover{
    background:#F3F4F6;
}

.delete-btn{
    background:#fff;
    border:1px solid #EF4444;
    color:#EF4444;
}

.delete-btn:hover{
    background:#FEF2F2;
}

/* ───── Empty ───── */

.empty-body{
    padding:80px 20px;

    display:flex;
    flex-direction:column;
    align-items:center;

    gap:12px;
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

/* ───── Pagination ───── */

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

.thumbnail-empty {
    width: 150px;
    height: 60px;

    display: flex;
    align-items: center;
    justify-content: center;

    margin: 0 auto;

    border-radius: 8px;
    border: 1px solid #E5E7EB;

    background: #F9FAFB;
    color: #9CA3AF;

    font-size: 12px;
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

</style>

</head>

<body>

<%
request.setAttribute("activeMenu", "event");
%>

<%@ include file="../common/topBar.jsp" %>

<div class="layout">

    <%@ include file="../common/sideBar.jsp" %>

    <main class="main">

        <div class="page-header">

            <h1 class="page-title">이벤트 관리</h1>

            <button type="button"
                    class="register-btn"
                    onclick="location.href='${pageContext.request.contextPath}/event/edit'">


                <i class="ti ti-plus"></i>
                이벤트 등록

            </button>

        </div>

        <div class="card">

            <table class="event-table">

                <thead>
                    <tr>
                        <th style="width:80px;">번호</th>
                        <th style="width:200px;">이벤트 이미지</th>
                        <th>이벤트 제목</th>
                        <th style="width:240px;">기간</th>
                        <th style="width:120px;">상태</th>
                        <th style="width:140px;">등록일</th>
                        <th style="width:140px;">관리</th>
                    </tr>
                </thead>

                <tbody>

                <%
                if(eventList.isEmpty()){
                %>

                    <tr>
                        <td colspan="7">

                            <div class="empty-body">

                                <i class="ti ti-calendar-event empty-icon"></i>

                                <p class="empty-text">
                                    등록된 이벤트가 없습니다.
                                </p>

                                <p class="empty-sub">
                                    이벤트 등록 버튼을 눌러 이벤트를 추가해보세요.
                                </p>

                            </div>

                        </td>
                    </tr>

                <%
                }else{

                    for(EventListDTO dto : eventList){

                        String statusClass = "";

                        if("진행중".equals(dto.getEventSate())){
                            statusClass = "status-progress";
                        }else if("예정".equals(dto.getEventSate())){
                            statusClass = "status-ready";
                        }else{
                            statusClass = "status-end";
                        }
                %>

                    <tr>

                        <td><%= dto.getEventCode() %></td>

                        <td>

                            <%
							    String thumbnailImg = dto.getThumbnailImg();
							%>
							
							<%
							if (thumbnailImg != null &&
							    !thumbnailImg.trim().isEmpty()) {
							%>
							
							    <img src="<%= request.getContextPath() %>/upload/event/<%= thumbnailImg %>"
							         class="thumbnail"
							         alt="이벤트 썸네일 이미지">
							
							<%
							} else {
							%>
							
							    <div class="thumbnail-empty">
							        이미지 없음
							    </div>
							
							<%
							}
							%>

                        </td>

                        <td class="event-title">
                            <%= dto.getEventTitle() %>
                        </td>

                        <td class="period">
                            <%= dto.getStartDate() %>
                            ~
                            <%= dto.getEndDate() %>
                        </td>

                        <td>
                            <span class="status <%= statusClass %>">
                                <%= dto.getEventSate() %>
                            </span>
                        </td>

                        <td>
                            <%= dto.getEventWriteDate() %>
                        </td>

                        <td>

                            <div class="action-area">

                                <button type="button"
                                        class="edit-btn"
                                        onclick="editEvent(<%= dto.getEventCode() %>)">
                                    수정
                                </button>

                                <button type="button"
                                        class="delete-btn"
                                        onclick="deleteEvent(<%= dto.getEventCode() %>)">
                                    삭제
                                </button>

                            </div>

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
            for(int i = 1; i <= range.getTotalPage(); i++){
            %>

                <button class="page-btn <%= i == range.getPage() ? "active" : "" %>"
                        onclick="location.href='${pageContext.request.contextPath}/event?page=<%= i %>'">

                    <%= i %>

                </button>

            <%
            }
            %>

            </div>

        </div>

    </main>

</div>

<script>

function editEvent(eventCode){

    location.href =
        "${pageContext.request.contextPath}/event/edit?eventCode="
        + eventCode;
}

function deleteEvent(eventCode){

    if(confirm("이벤트를 삭제하시겠습니까?")){

        location.href =
            "${pageContext.request.contextPath}/event/delete?eventCode="
            + eventCode;
    }
}

</script>

</body>
</html>
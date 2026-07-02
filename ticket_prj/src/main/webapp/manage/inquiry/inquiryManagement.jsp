<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="kr.admin.inquiry.InquiryListDTO"%>
<%@page import="kr.admin.common.BoardRangeDTO"%>

<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    request.setAttribute("activeMenu", "inquiry");

    List<InquiryListDTO> inquiryList =
            (List<InquiryListDTO>)request.getAttribute("inquiryList");

    if (inquiryList == null) {
        inquiryList = new ArrayList<>();
    }

    BoardRangeDTO range =
            (BoardRangeDTO)request.getAttribute("range");

    if (range == null) {
        range = new BoardRangeDTO();
        range.setPage(1);
        range.setTotalPage(1);
    }

    Integer totalCount =
            (Integer)request.getAttribute("totalCount");

    Integer waitingCount =
            (Integer)request.getAttribute("waitingCount");

    Integer completeCount =
            (Integer)request.getAttribute("completeCount");

    if (totalCount == null) totalCount = 0;
    if (waitingCount == null) waitingCount = 0;
    if (completeCount == null) completeCount = 0;

    String status = request.getParameter("status");

    if (status == null || status.isEmpty()) {
        status = "all";
    }
    
%>
<%
    String keyword =
            request.getParameter("keyword");

    String searchDate =
            request.getParameter("searchDate");

    if (keyword == null) {
        keyword = "";
    }

    if (searchDate == null) {
        searchDate = "";
    }

    String keywordValue =
            keyword.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;");

    String encodedKeyword =
            URLEncoder.encode(keyword, "UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1:1 문의 관리</title>

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/tabler-icons.min.css">

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

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

/* ── Main ── */

.main{
    flex:1;
    padding:36px 40px;
}

.page-title{
    font-size:36px;
    font-weight:700;
    color:#111827;
    margin-bottom:28px;
}

/* ── Filter Area ── */

.filter-area{
    display:flex;
    justify-content:space-between;
    align-items:flex-end;
    margin-bottom:18px;
}

.tabs{
    display:flex;
    gap:36px;
}

.tab{
    position:relative;
    padding-bottom:12px;
    text-decoration:none;
    color:#6B7280;
    font-size:15px;
    font-weight:600;
}

.tab.active{
    color:#111827;
}

.tab.active::after{
    content:'';
    position:absolute;
    left:0;
    bottom:0;
    width:100%;
    height:3px;
    background:#EF4444;
}

.count-badge{
    display:inline-flex;
    align-items:center;
    justify-content:center;

    min-width:22px;
    height:22px;

    padding:0 6px;
    margin-left:8px;

    background:#FEE2E2;
    color:#EF4444;

    border-radius:999px;

    font-size:12px;
    font-weight:700;
}

/* ── 조건 검색 디자인 ── */

.search-area{
    display:flex;
    gap:10px;
}

.search-box{
    position:relative;
}

.search-input{
    width:280px;
    height:40px;

    padding:0 40px 0 14px;

    border:1px solid #E5E7EB;
    border-radius:8px;

    background:#fff;
}

.date-filter{
    width:140px;
    height:40px;

    padding:0 12px;

    border:1px solid #E5E7EB;
    border-radius:8px;

    background:#fff;
}

.search-submit-btn {
    position: absolute;
    top: 50%;
    right: 8px;

    transform: translateY(-50%);

    width: 28px;
    height: 28px;

    border: 0;
    background: transparent;

    color: #6B7280;

    cursor: pointer;
}

.search-submit-btn:hover {
    color: #111827;
}

.search-reset-btn {
    height: 40px;

    padding: 0 14px;

    border: 1px solid #E5E7EB;
    border-radius: 8px;

    background: #fff;
    color: #6B7280;

    cursor: pointer;
}

.search-reset-btn:hover {
    background: #F9FAFB;
    color: #111827;
}

/* ── 목록 + 상세 패널 레이아웃 ── */

.inquiry-content-layout {
    display: flex;
    gap: 16px;
    align-items: flex-start;
}

.inquiry-list-area {
    flex: 1;
    min-width: 0;
}

/* ── Table ── */

.table-wrap{
    background:#fff;
    border:1px solid #E5E7EB;
    border-radius:12px;
    overflow:hidden;
}

.inquiry-table{
    width:100%;
    border-collapse:collapse;
}

.inquiry-table thead{
    background:#F9FAFB;
}

.inquiry-table th{
    padding:18px 16px;

    font-size:14px;
    font-weight:600;
    color:#6B7280;

    border-bottom:1px solid #E5E7EB;
}

.inquiry-table td{
    padding:18px 16px;
    text-align:center;

    font-size:14px;
    color:#374151;

    border-bottom:1px solid #F3F4F6;
}

.inquiry-table tbody tr{
    cursor:pointer;
}

.inquiry-table tbody tr:hover{
    background:#FAFAFA;
}

.inquiry-table tbody tr.waiting-row{
    background:#FEF2F2;
}

.title-cell{
    text-align:left !important;
    font-weight:500;
}

.new-badge{
    display:inline-flex;
    align-items:center;
    justify-content:center;

    width:16px;
    height:16px;

    margin-left:8px;

    border-radius:4px;

    background:#EF4444;
    color:#fff;

    font-size:10px;
    font-weight:700;
}

.status-badge{
    display:inline-flex;
    align-items:center;
    justify-content:center;

    min-width:72px;
    height:28px;

    border-radius:8px;

    font-size:12px;
    font-weight:600;
}

.status-waiting{
    background:#FEF2F2;
    color:#EF4444;
}

.status-complete{
    background:#EFF6FF;
    color:#2563EB;
}

/* ── Empty ── */

.empty-row td{
    padding:80px 20px;
}

.empty-box{
    display:flex;
    flex-direction:column;
    align-items:center;
    gap:12px;
}

.empty-box i{
    font-size:42px;
    color:#D1D5DB;
}

.empty-box p{
    color:#6B7280;
}

/* ── Pagination ── */

.pagination{
    display:flex;
    justify-content:center;
    gap:8px;
    padding:24px;
}

.page-btn{
    width:40px;
    height:40px;

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

<%@ include file="../common/topBar.jsp" %>

<div class="layout">

    <%@ include file="../common/sideBar.jsp" %>

    <main class="main">

        <h1 class="page-title">1:1 문의 관리</h1>

        <div class="filter-area">

            <div class="tabs">

                <a href="${pageContext.request.contextPath}/admin/inquiry?status=all&keyword=<%= encodedKeyword %>&searchDate=<%= searchDate %>"
				   class="tab <%= "all".equals(status) ? "active" : "" %>">

                    전체

                    <span class="count-badge">
                        <%= totalCount %>
                    </span>

                </a>

                <a href="${pageContext.request.contextPath}/admin/inquiry?status=waiting&keyword=<%= encodedKeyword %>&searchDate=<%= searchDate %>"
  				 class="tab <%= "waiting".equals(status) ? "active" : "" %>">

                    답변대기

                    <span class="count-badge">
                        <%= waitingCount %>
                    </span>

                </a>

                <a href="${pageContext.request.contextPath}/admin/inquiry?status=complete&keyword=<%= encodedKeyword %>&searchDate=<%= searchDate %>"
   					class="tab <%= "complete".equals(status) ? "active" : "" %>">
                   
                    답변완료

                    <span class="count-badge">
                        <%= completeCount %>
                    </span>

                </a>

            </div>

            <form class="search-area"
			      method="get"
			      action="${pageContext.request.contextPath}/admin/inquiry">
			
			    <input type="hidden"
			           name="status"
			           value="<%= status %>">
			
			    <div class="search-box">
			
			        <input type="text"
			               class="search-input"
			               name="keyword"
			               value="<%= keywordValue %>"
			               placeholder="제목 또는 작성자를 입력하세요.">
			
			        <button type="submit"
			                class="search-submit-btn"
			                aria-label="검색">
			            <i class="ti ti-search"></i>
			        </button>
			
			    </div>
			
			    <input type="date"
			           class="date-filter"
			           name="searchDate"
			           value="<%= searchDate %>"
			           onchange="this.form.submit();">
			
			    <button type="button"
			            class="search-reset-btn"
			            onclick="location.href='${pageContext.request.contextPath}/admin/inquiry?status=<%= status %>'">
			        초기화
			    </button>
			
			</form>

        </div>

        <div class="inquiry-content-layout">

            <div class="inquiry-list-area">

                <div class="table-wrap">

                    <table class="inquiry-table">

                        <thead>
                            <tr>
                                <th width="90">번호</th>
                                <th>제목</th>
                                <th width="180">작성자</th>
                                <th width="180">작성일</th>
                                <th width="140">상태</th>
                            </tr>
                        </thead>

                        <tbody>

                        <%
                        if(inquiryList.isEmpty()){
                        %>

                            <tr class="empty-row">
                                <td colspan="5">
                                    <div class="empty-box">
                                        <i class="ti ti-message-circle"></i>
                                        <p>등록된 문의 내역이 없습니다.</p>
                                    </div>
                                </td>
                            </tr>

                        <%
                        } else {

                            for(InquiryListDTO dto : inquiryList){

                                boolean waiting =
                                        "답변대기".equals(dto.getState());
                        %>

                            <tr class="<%= waiting ? "waiting-row" : "" %>"
                                onclick="openInquiryPanel(this, <%= dto.getInquiryCode() %>)">

                                <td>
                                    <%= dto.getInquiryCode() %>
                                </td>

                                <td class="title-cell">
                                    <%= dto.getInquiryTitle() %>

                                    <%
                                    if(waiting){
                                    %>
                                        <span class="new-badge">N</span>
                                    <%
                                    }
                                    %>
                                </td>

                                <td>
                                    <%= dto.getMemberId() %>
                                </td>

                                <td>
                                    <%= dto.getInquiryDate() %>
                                </td>

                                <td>
                                    <span class="status-badge
                                    <%= waiting
                                            ? "status-waiting"
                                            : "status-complete" %>">

                                        <%= dto.getState() %>

                                    </span>
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

                        <button type="button"
                                class="page-btn <%= i == range.getPage() ? "active" : "" %>"
                                onclick="location.href='${pageContext.request.contextPath}/admin/inquiry?page=<%= i %>&status=<%= status %>&keyword=<%= encodedKeyword %>&searchDate=<%= searchDate %>'">

                            <%= i %>

                        </button>

                    <%
                    }
                    %>

                    </div>

                </div>

            </div>

            <%@ include file="inquiryDetailPanel.jspf" %>

        </div>

    </main>

</div>

</body>
</html>
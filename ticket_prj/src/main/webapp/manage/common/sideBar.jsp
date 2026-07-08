<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/tabler-icons.min.css">
<style>
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
</style>

<%
    // 현재 활성화된 메뉴를 구분하기 위해 request attribute 사용
    // 각 페이지에서 request.setAttribute("activeMenu", "teamManagement") 형태로 지정
    String activeMenu = (String) request.getAttribute("activeMenu");
    if (activeMenu == null) activeMenu = "";
%>
<nav class="sidebar">
    <div class="nav-item <%= activeMenu.equals("dashboard") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/admin/dashboard'">
        <i class="ti ti-home" aria-hidden="true"></i>
        메인
    </div>
    <div class="nav-item <%= activeMenu.equals("ticket") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/admin/ticket'">
        <i class="ti ti-ticket" aria-hidden="true"></i>
        티켓관리
    </div>
    <div class="nav-item <%= activeMenu.equals("member") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/admin/member'">
        <i class="ti ti-user" aria-hidden="true"></i>
        회원관리
    </div>
    <div class="nav-item <%= activeMenu.equals("teamManagement") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/admin/teamManagement'">
        <i class="ti ti-ball-baseball" aria-hidden="true"></i>
        야구팀 관리
    </div>
    <div class="nav-item <%= activeMenu.equals("stadium") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/admin/stadium'">
        <i class="ti ti-building-stadium" aria-hidden="true"></i>
        구장관리
    </div>
    <div class="nav-item <%= activeMenu.equals("event") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/admin/event'">
        <i class="ti ti-gift" aria-hidden="true"></i>
        이벤트 관리
    </div>
    <div class="nav-item <%= activeMenu.equals("coupon") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/admin/coupon'">
        <i class="ti ti-gift-card" aria-hidden="true"></i>
        쿠폰 관리
    </div>
    <div class="nav-item <%= activeMenu.equals("inquiry") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/admin/inquiry'">
        <i class="ti ti-message-circle" aria-hidden="true"></i>
        1:1 문의 관리
    </div>
    <div class="nav-item <%= activeMenu.equals("notification") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/admin/notice'">
        <i class="ti ti-bell" aria-hidden="true"></i>
        공지사항 관리
    </div>
</nav>

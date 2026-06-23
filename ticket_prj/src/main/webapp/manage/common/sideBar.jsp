<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 현재 활성화된 메뉴를 구분하기 위해 request attribute 사용
    // 각 페이지에서 request.setAttribute("activeMenu", "teamManagement") 형태로 지정
    String activeMenu = (String) request.getAttribute("activeMenu");
    if (activeMenu == null) activeMenu = "";
%>
<nav class="sidebar">
    <div class="nav-item <%= activeMenu.equals("main") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/main'">
        <i class="ti ti-home" aria-hidden="true"></i>
        메인
    </div>
    <div class="nav-item <%= activeMenu.equals("ticket") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/ticket'">
        <i class="ti ti-ticket" aria-hidden="true"></i>
        티켓관리
    </div>
    <div class="nav-item <%= activeMenu.equals("member") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/member'">
        <i class="ti ti-user" aria-hidden="true"></i>
        회원관리
    </div>
    <div class="nav-item <%= activeMenu.equals("teamManagement") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/teamManagement'">
        <i class="ti ti-ball-baseball" aria-hidden="true"></i>
        야구팀 관리
    </div>
    <div class="nav-item <%= activeMenu.equals("stadium") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/stadium'">
        <i class="ti ti-building-stadium" aria-hidden="true"></i>
        구장관리
    </div>
    <div class="nav-item <%= activeMenu.equals("event") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/event'">
        <i class="ti ti-gift" aria-hidden="true"></i>
        이벤트 관리
    </div>
    <div class="nav-item <%= activeMenu.equals("inquiry") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/inquiry'">
        <i class="ti ti-message-circle" aria-hidden="true"></i>
        1:1 문의 관리
    </div>
    <div class="nav-item <%= activeMenu.equals("inquiry") ? "active" : "" %>"
         onclick="location.href='${pageContext.request.contextPath}/inquiry'">
        <i class="ti ti-bell" aria-hidden="true"></i>
        공지사항 관리
    </div>
</nav>

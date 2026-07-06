<%@ page import="kr.user.member.MemberDTO"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

String loginUrl = request.getContextPath() + "/member/login";
String forwardUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
String forwardQuery = (String) request.getAttribute("javax.servlet.forward.query_string");

String requestUri = forwardUri != null ? forwardUri : request.getRequestURI();
String currentPath = requestUri.substring(request.getContextPath().length());
String queryString = forwardUri != null ? forwardQuery : request.getQueryString();

// 로그인 화면 자체를 제외하고 현재 GET 화면으로 돌아올 경로를 로그인 링크에 보존한다.
if ("GET".equalsIgnoreCase(request.getMethod()) && !currentPath.startsWith("/member/")) {
	String returnPath = currentPath;
	if ("/kr/user/main/main.jsp".equals(returnPath)) {
		returnPath = "/main";
	}
	if (queryString != null && !queryString.isEmpty()) {
		returnPath += "?" + queryString;
	}
	loginUrl += "?redirect=" + URLEncoder.encode(returnPath, "UTF-8");
}
%><!-- 로그아웃 후 뒤로가기 버튼 눌렀을 때 로그인 상태로 돌아가서 캐시 방지 코드 -->

<style>
/* 기본 초기화 */
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	text-decoration: none;
	list-style: none;
	color: #333;
	font-family: 'Noto Sans KR', sans-serif;
}

.header-wrap {
	border-bottom: 1px solid #e0e0e0;
	background-color: #fff;
}

.header-top {
	background-color: #f9f9f9;
	border-bottom: 1px solid #f0f0f0;
}

.header-top-inner {
	max-width: 1200px;
	margin: 0 auto;
	display: flex;
	justify-content: flex-end;
	padding: 8px 20px;
	font-size: 12px;
}

.header-top-inner span {
    color: #666;
    margin-left: 20px;
}

.header-top-inner a {
	color: #666;
	margin-left: 20px;
}

.header-top-inner a:hover {
	color: #000;
}

.header-main {
	max-width: 1200px;
	margin: 0 auto;
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 25px 20px;
}

.logo a {
	font-size: 28px;
	font-weight: 900;
	color: #e50020;
}

.search-box {
	display: flex;
	align-items: center;
	border: 1px solid #ccc;
	border-radius: 30px;
	padding: 8px 20px;
	width: 300px;
}

.search-box input {
	border: none;
	outline: none;
	width: 100%;
	font-size: 14px;
}

.search-box button {
	background: none;
	border: none;
	cursor: pointer;
	font-size: 16px;
	color: #999;
}

.header-nav {
	max-width: 1200px;
	margin: 0 auto;
	display: flex;
	justify-content: center;
	gap: 25px;
	padding: 15px 20px;
}

.header-nav a {
	font-size: 14px;
	font-weight: 500;
	transition: color 0.2s ease-in-out;
}

/* 호버 시 빨간색 변경 기능 */
.header-nav a:hover {
	color: #e50020;
	font-weight: bold;
}
</style>

<header class="header-wrap">
	<div class="header-top">
		<div class="header-top-inner">
			<%
			MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
			%>
			<!-- 예매확인/취소가 애매해서 로그인 전에는 없애고 로그인 후에는 마이페이지로 대체했습니다. -->
			<%
			if (loginMember == null) {
			%>
			<a href="<%=loginUrl%>">로그인</a> <a
				href="<%=request.getContextPath()%>/member/join-agree">회원가입</a> <a
				href="<%=request.getContextPath()%>/user-inquiry">고객센터</a>
			<%
			} else {
			%>
			<span><c:out value="${sessionScope.loginMember.name}" />님</span> <a
				href="<%=request.getContextPath()%>/mypage_user/reservation.jsp">마이페이지</a>
			<a href="<%=request.getContextPath()%>/member/logout">로그아웃</a> <a
				href="<%=request.getContextPath()%>/user-inquiry">고객센터</a>
			<%
			}
			%>
		</div>
	</div>
	<div class="header-main">
		<div class="logo">
			<a href="${pageContext.request.contextPath}/main">BallPick⚾</a>
		</div>
	</div>
	<nav class="header-nav">
		<a
			href="${pageContext.request.contextPath}/teamPage?teamCode=1">LG트윈스</a>
		<a
			href="${pageContext.request.contextPath}/teamPage?teamCode=2">두산베어스</a>
		<a
			href="${pageContext.request.contextPath}/teamPage?teamCode=3">한화
			이글스</a> <a
			href="${pageContext.request.contextPath}/teamPage?teamCode=4">롯데
			자이언츠</a> <a
			href="${pageContext.request.contextPath}/teamPage?teamCode=5">KIA
			타이거즈</a> <a
			href="${pageContext.request.contextPath}/teamPage?teamCode=6">NC
			다이노스</a> <a
			href="${pageContext.request.contextPath}/teamPage?teamCode=7">SSG
			랜더스</a> <a
			href="${pageContext.request.contextPath}/teamPage?teamCode=8">삼성
			라이온즈</a> <a
			href="${pageContext.request.contextPath}/teamPage?teamCode=9">키움
			히어로즈</a> <a
			href="${pageContext.request.contextPath}/teamPage?teamCode=10">KT
			WIZ</a> <a href="${pageContext.request.contextPath}/user_event/eventMain.jsp">이벤트</a>
	</nav>
</header>

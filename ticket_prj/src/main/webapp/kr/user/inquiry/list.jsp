<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="kr.user.inquiry.InquiryDTO"%>
<%@page import="kr.user.common.InquiryType"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!
private String getCategoryName(int categoryCode) {
	InquiryType type = InquiryType.fromCode(categoryCode);
	return type == null ? InquiryType.ETC.getDisplayName() : type.getDisplayName();
}

@SuppressWarnings("unchecked")
private List<InquiryDTO> getInquiryList(HttpServletRequest request) {
	return (List<InquiryDTO>) request.getAttribute("inquiryList");
}
%>

<%
List<InquiryDTO> inquiryList = getInquiryList(request);
SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>내 문의내역 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr/user/inquiry/inquiry.css?v=20260708-1">
</head>
<body>
<jsp:include page="/fragment/header.jsp" />

<main class="inquiry-page">
	<section class="inquiry-shell inquiry-shell-narrow">
		<h1 class="inquiry-title">고객센터</h1>

		<nav class="inquiry-nav" aria-label="고객센터 메뉴">
			<a href="<%=request.getContextPath()%>/user-inquiry">자주 묻는 질문</a>
			<a href="<%=request.getContextPath()%>/user-inquiry/write">1:1 문의하기</a>
			<a class="active" href="<%=request.getContextPath()%>/user-inquiry/list">내 문의내역</a>
		</nav>

		<% if (inquiryList == null || inquiryList.isEmpty()) { %>
			<div class="inquiry-empty">
				<h2>등록된 문의가 없습니다.</h2>
				<p>궁금한 내용을 1:1 문의로 남겨주세요.</p>
				<a class="inquiry-button" href="<%=request.getContextPath()%>/user-inquiry/write">1:1 문의하기</a>
			</div>
		<% } else { %>
			<div class="inquiry-list-wrap">
				<table class="inquiry-list-table">
					<thead>
						<tr>
							<th>번호</th>
							<th>문의유형</th>
							<th>제목</th>
							<th>작성일</th>
							<th>처리상태</th>
						</tr>
					</thead>
					<tbody>
						<% for (int i = 0; i < inquiryList.size(); i++) {
							InquiryDTO inquiry = inquiryList.get(i);

							// 화면용 번호. DB의 inquiry_id와 다르게 사용자에게 보여주는 순번이다.
							int displayNo = i + 1;

							String statusClass = "답변완료".equals(inquiry.getInquiryStatus())
									? "inquiry-status-done"
									: "inquiry-status-wait";
						%>
							<tr class="inquiry-list-row"
								onclick="window.location.href='<%=request.getContextPath()%>/user-inquiry/detail?inquiryCode=<%= inquiry.getInquiryCode() %>'">
								<td><%= displayNo %></td>
								<td><%= getCategoryName(inquiry.getInquiryCategoryCode()) %></td>
								<td class="inquiry-list-title">
									<a href="<%=request.getContextPath()%>/user-inquiry/detail?inquiryCode=<%= inquiry.getInquiryCode() %>">
										<c:out value="<%=inquiry.getInquiryTitle()%>" />
									</a>
								</td>
								<td><%= inquiry.getInquiryDate() == null ? "-" : sdf.format(inquiry.getInquiryDate()) %></td>
								<td>
									<span class="<%= statusClass %>">
										<%= inquiry.getInquiryStatus() %>
									</span>
								</td>
							</tr>
						<% } %>
					</tbody>
				</table>
			</div>

			<div class="inquiry-list-actions">
				<a class="inquiry-button" href="<%=request.getContextPath()%>/user-inquiry/write">1:1 문의하기</a>
			</div>
		<% } %>
	</section>
</main>

<jsp:include page="/fragment/footer.jsp" />
</body>
</html>

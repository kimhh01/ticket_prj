<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="kr.user.inquiry.InquiryDTO" %>
<%
InquiryDTO inquiry = (InquiryDTO) request.getAttribute("inquiry");
String categoryName = (String) request.getAttribute("categoryName");
String inquiryDate = inquiry == null || inquiry.getInquiryDate() == null
        ? "" : new SimpleDateFormat("yyyy.MM.dd HH:mm").format(inquiry.getInquiryDate());
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>문의 상세 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr.user.inquiry/inquiry.css">
</head>
<body>
<jsp:include page="/include/header.jsp" />

<main class="inquiry-page">
    <section class="inquiry-shell inquiry-shell-narrow">
        <h1 class="inquiry-title">문의 상세</h1>
        <p class="inquiry-description">현재 화면은 DB 저장 전 UI 미리보기입니다.</p>

        <% if (request.getAttribute("successMessage") != null) { %>
        <div class="inquiry-success"><%=request.getAttribute("successMessage")%></div>
        <% } %>

        <% if (inquiry != null) { %>
        <dl class="inquiry-detail">
            <div class="inquiry-detail-row"><dt>처리 상태</dt><dd><span class="inquiry-status"><%=inquiry.getInquiryStatus()%></span></dd></div>
            <div class="inquiry-detail-row"><dt>문의 유형</dt><dd><%=categoryName%></dd></div>
            <div class="inquiry-detail-row"><dt>작성일</dt><dd><%=inquiryDate%></dd></div>
            <div class="inquiry-detail-row"><dt>제목</dt><dd><%=inquiry.getInquiryTitle()%></dd></div>
            <div class="inquiry-detail-row inquiry-detail-row-content"><dt>문의 내용</dt><dd><%=inquiry.getInquiryContent()%></dd></div>
        </dl>

        <form method="post" action="<%=request.getContextPath()%>/user-inquiry/edit">
            <input type="hidden" name="stage" value="form">
            <input type="hidden" name="inquiryCategoryCode" value="<%=inquiry.getInquiryCategoryCode()%>">
            <input type="hidden" name="inquiryTitle" value="<%=inquiry.getInquiryTitle()%>">
            <input type="hidden" name="inquiryContent" value="<%=inquiry.getInquiryContent()%>">
            <div class="inquiry-actions">
                <a class="inquiry-button inquiry-button-light" href="<%=request.getContextPath()%>/user-inquiry/list">목록</a>
                <button class="inquiry-button" type="submit">문의 수정</button>
            </div>
        </form>
        <% } else { %>
        <div class="inquiry-empty"><h2>문의 정보를 찾을 수 없습니다.</h2><a class="inquiry-button" href="<%=request.getContextPath()%>/user-inquiry/list">목록으로</a></div>
        <% } %>
    </section>
</main>

<jsp:include page="/include/footer.jsp" />
</body>
</html>

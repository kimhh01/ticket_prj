<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="kr.user.inquiry.InquiryDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
InquiryDTO inquiry = (InquiryDTO) request.getAttribute("inquiry");
String categoryName = (String) request.getAttribute("categoryName");

SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");

String inquiryDate = inquiry == null || inquiry.getInquiryDate() == null
        ? "" : sdf.format(inquiry.getInquiryDate());

String replyDate = inquiry == null || inquiry.getReplyDate() == null
        ? "" : sdf.format(inquiry.getReplyDate());

boolean isAnswered = inquiry != null && inquiry.getReplyContent() != null && !"".equals(inquiry.getReplyContent().trim());
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>문의 상세 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr/user/inquiry/inquiry.css?v=20260709-1">
</head>
<body>
<jsp:include page="/fragment/header.jsp" />

<main class="inquiry-page">
    <section class="inquiry-shell inquiry-shell-narrow">
        <h1 class="inquiry-title">문의 상세</h1>
        <p class="inquiry-description">등록한 문의와 답변 내용을 확인할 수 있습니다.</p>

        <% if (request.getAttribute("successMessage") != null) { %>
            <div class="inquiry-success"><%=request.getAttribute("successMessage")%></div>
        <% } %>

        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="inquiry-error"><%=request.getAttribute("errorMessage")%></div>
        <% } %>

        <% if (inquiry != null) { %>
            <dl class="inquiry-detail">
                <div class="inquiry-detail-row">
                    <dt>처리 상태</dt>
                    <dd><span class="inquiry-status"><c:out value="<%=inquiry.getInquiryStatus()%>" /></span></dd>
                </div>

                <div class="inquiry-detail-row">
                    <dt>문의 유형</dt>
                    <dd><c:out value="<%=categoryName%>" /></dd>
                </div>

                <div class="inquiry-detail-row">
                    <dt>작성일</dt>
                    <dd><%=inquiryDate%></dd>
                </div>

                <div class="inquiry-detail-row">
                    <dt>제목</dt>
                    <dd><c:out value="<%=inquiry.getInquiryTitle()%>" /></dd>
                </div>

                <div class="inquiry-detail-row inquiry-detail-row-content">
                    <dt>문의 내용</dt>
                    <dd><c:out value="<%=inquiry.getInquiryContent()%>" /></dd>
                </div>

                <% if (isAnswered) { %>
                    <div class="inquiry-detail-row">
                        <dt>답변일</dt>
                        <dd><%=replyDate%></dd>
                    </div>

                    <div class="inquiry-detail-row inquiry-detail-row-content">
                        <dt>답변 내용</dt>
                        <dd><c:out value="<%=inquiry.getReplyContent()%>" /></dd>
                    </div>
                <% } else { %>
                    <div class="inquiry-detail-row inquiry-detail-row-content">
                        <dt>답변 내용</dt>
                        <dd>아직 답변이 등록되지 않았습니다.</dd>
                    </div>
                <% } %>
            </dl>

            <div class="inquiry-actions">
                <a class="inquiry-button inquiry-button-light" href="<%=request.getContextPath()%>/user-inquiry/list">목록</a>

                <% if (!isAnswered) { %>
                    <form method="post" action="<%=request.getContextPath()%>/user-inquiry/edit">
                        <input type="hidden" name="stage" value="form">
                        <input type="hidden" name="inquiryCode" value="<%=inquiry.getInquiryCode()%>">
                        <button class="inquiry-button" type="submit">문의 수정</button>
                    </form>
                <% } else { %>
                    <a class="inquiry-button" href="<%=request.getContextPath()%>/user-inquiry/write">새 문의하기</a>
                <% } %>
            </div>
        <% } else { %>
            <div class="inquiry-empty">
                <h2>문의 정보를 찾을 수 없습니다.</h2>
                <p>삭제되었거나 접근 권한이 없는 문의입니다.</p>
                <a class="inquiry-button" href="<%=request.getContextPath()%>/user-inquiry/list">목록으로</a>
            </div>
        <% } %>
    </section>
</main>

<jsp:include page="/fragment/footer.jsp" />
</body>
</html>

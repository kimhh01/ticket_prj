<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="kr.user.member.MemberDTO" %>
<%
if (request.getAttribute("categories") == null) {
	response.sendRedirect(request.getContextPath() + "/user-inquiry/write");
	return;
} // 문의 작성 페이지에 필요한 카테고리 정보가 없으면 다시 문의 작성 페이지로 리다이렉트

MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

if (loginMember == null) {
	response.sendRedirect(request.getContextPath() + "/member/login?redirect=/user-inquiry/write");
	return;
} // 로그인하지 않은 사용자는 문의 작성 페이지에 접근할 수 없도록 처리
%>

<%
@SuppressWarnings("unchecked")
Map<Integer, String> categories = (Map<Integer, String>) request.getAttribute("categories");
Integer selectedCategoryCode = (Integer) request.getAttribute("selectedCategoryCode");
String formTitle = request.getAttribute("formTitle") == null ? "" : (String) request.getAttribute("formTitle");
String formContent = request.getAttribute("formContent") == null ? "" : (String) request.getAttribute("formContent");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>1:1 문의하기 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr/user/inquiry/inquiry.css">
</head>
<body>
<jsp:include page="/fragment/header.jsp" />

<main class="inquiry-page">
    <section class="inquiry-shell inquiry-shell-narrow">
        <h1 class="inquiry-title">고객센터</h1>
        <nav class="inquiry-nav" aria-label="고객센터 메뉴">
            <a href="<%=request.getContextPath()%>/user-inquiry">자주 묻는 질문</a>
            <a class="active" href="<%=request.getContextPath()%>/user-inquiry/write">1:1 문의하기</a>
            <a href="<%=request.getContextPath()%>/user-inquiry/list">내 문의내역</a>
        </nav>

        <p class="inquiry-description">문의 내용을 자세히 작성해 주시면 확인에 도움이 됩니다.</p>
        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="inquiry-error"><%=request.getAttribute("errorMessage")%></div>
        <% } %>

        <form class="inquiry-form" id="inquiryForm" method="post"
              action="<%=request.getContextPath()%>/user-inquiry/write" novalidate>
            <div class="inquiry-field">
                <label for="inquiryCategoryCode">문의 유형 <span class="inquiry-required">*</span></label>
                <select class="inquiry-select" id="inquiryCategoryCode" name="inquiryCategoryCode">
                    <option value="">문의 유형을 선택해 주세요.</option>
                    <% if (categories != null) { for (Map.Entry<Integer, String> category : categories.entrySet()) { %>
                    <option value="<%=category.getKey()%>" <%=category.getKey().equals(selectedCategoryCode) ? "selected" : ""%>><%=category.getValue()%></option>
                    <% }} %>
                </select>
            </div>
            <div class="inquiry-field">
                <label for="inquiryTitle">제목 <span class="inquiry-required">*</span></label>
                <input class="inquiry-input" type="text" id="inquiryTitle" name="inquiryTitle"
                       maxlength="100" value="<%=formTitle%>" placeholder="제목을 입력해 주세요.">
                <div class="inquiry-field-meta"><span>최대 100자</span><span id="titleCount">0/100</span></div>
            </div>
            <div class="inquiry-field">
                <label for="inquiryContent">문의 내용 <span class="inquiry-required">*</span></label>
                <textarea class="inquiry-textarea" id="inquiryContent" name="inquiryContent"
                          maxlength="2000" placeholder="문의 내용을 입력해 주세요."><%=formContent%></textarea>
                <div class="inquiry-field-meta"><span>개인정보를 포함하지 않도록 주의해 주세요.</span><span id="contentCount">0/2000</span></div>
            </div>
            <div class="inquiry-error" id="clientError" hidden></div>
            <div class="inquiry-actions">
                <a class="inquiry-button inquiry-button-light" href="<%=request.getContextPath()%>/user-inquiry">취소</a>
                <button class="inquiry-button" type="submit">문의 등록</button>
            </div>
        </form>
    </section>
</main>

<jsp:include page="/fragment/footer.jsp" />
<script>
(function() {
    const form = document.getElementById("inquiryForm");
    const category = document.getElementById("inquiryCategoryCode");
    const title = document.getElementById("inquiryTitle");
    const content = document.getElementById("inquiryContent");
    const error = document.getElementById("clientError");
    const titleCount = document.getElementById("titleCount");
    const contentCount = document.getElementById("contentCount");

    function updateCount() {
        titleCount.textContent = title.value.length + "/100";
        contentCount.textContent = content.value.length + "/2000";
    }

    title.addEventListener("input", updateCount);
    content.addEventListener("input", updateCount);
    updateCount();

    form.addEventListener("submit", function(event) {
        let message = "";
        if (category.value === "") message = "문의 유형을 선택해 주세요.";
        else if (title.value.trim().length < 1 || title.value.trim().length > 100) message = "제목은 1자 이상 100자 이하로 입력해 주세요.";
        else if (content.value.trim().length < 1 || content.value.trim().length > 2000) message = "문의 내용은 1자 이상 2,000자 이하로 입력해 주세요.";

        if (message !== "") {
            event.preventDefault();
            error.textContent = message;
            error.hidden = false;
        }
    });
})();
</script>
</body>
</html>

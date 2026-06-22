<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원코드 찾기 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr.user.member/member.css">
</head>
<body>
<jsp:include page="/include/header.jsp" />

<main class="member-page">
    <section class="member-shell member-shell-narrow">
        <h1 class="member-title">회원코드 찾기</h1>
        <p class="member-description">가입할 때 등록한 이름과 이메일을 입력해 주세요.</p>

        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="member-error"><%=request.getAttribute("errorMessage")%></div>
        <% } %>

        <form class="member-form" id="findCodeForm" method="post"
              action="<%=request.getContextPath()%>/member/find-code-result" novalidate>
            <div class="member-field">
                <label for="name">이름</label>
                <input class="member-input" type="text" id="name" name="name"
                       maxlength="30" autocomplete="name" placeholder="이름을 입력해 주세요.">
            </div>
            <div class="member-field">
                <label for="email">이메일</label>
                <input class="member-input" type="email" id="email" name="email"
                       maxlength="100" autocomplete="email" placeholder="example@ballpick.com">
            </div>
            <div id="clientError" class="member-error" hidden></div>
            <button class="member-button" type="submit">확인</button>
        </form>

        <div class="member-link-row">
            <a href="<%=request.getContextPath()%>/member/login">로그인</a>
            <span class="member-link-divider"></span>
            <a href="<%=request.getContextPath()%>/member/find-password">비밀번호 찾기</a>
        </div>
    </section>
</main>

<jsp:include page="/include/footer.jsp" />
<script>
document.getElementById("findCodeForm").addEventListener("submit", function(event) {
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const error = document.getElementById("clientError");
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (name === "") {
        event.preventDefault();
        error.textContent = "이름을 입력해 주세요.";
        error.hidden = false;
        document.getElementById("name").focus();
        return;
    }
    if (!emailPattern.test(email)) {
        event.preventDefault();
        error.textContent = "올바른 이메일을 입력해 주세요.";
        error.hidden = false;
        document.getElementById("email").focus();
    }
});
</script>
</body>
</html>

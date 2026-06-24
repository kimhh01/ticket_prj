<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>비밀번호 찾기 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr/user/member/member.css">
</head>
<body>
<jsp:include page="/fragment/header.jsp" />

<main class="member-page">
    <section class="member-shell member-shell-narrow">
        <h1 class="member-title">비밀번호 찾기</h1>
        <p class="member-description">아이디와 가입 정보를 입력해 주세요.</p>

        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="member-error"><%=request.getAttribute("errorMessage")%></div>
        <% } %>

        <form class="member-form" id="findPasswordForm" method="post"
              action="<%=request.getContextPath()%>/member/find-password-result" novalidate>
            <div class="member-field">
                <label for="memberCode">아이디</label>
                <input class="member-input" type="text" id="memberCode" name="memberCode"
                       maxlength="20" autocomplete="username" placeholder="아이디를 입력해 주세요.">
                <p class="member-help">영문 또는 숫자 4~20자</p>
            </div>
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
            <div id="clientError" class="member-error" hidden="hidden"></div>
            <div class="member-actions">
                <a class="member-button member-button-light" href="<%=request.getContextPath()%>/member/login">취소</a>
                <button class="member-button" type="submit">임시 비밀번호 전송</button>
            </div>
        </form>

        <div class="member-link-row">
            <a href="<%=request.getContextPath()%>/member/login">로그인</a>
            <span class="member-link-divider"></span>
            <a href="<%=request.getContextPath()%>/member/find-code">아이디 찾기</a>
        </div>
    </section>
</main>

<jsp:include page="/fragment/footer.jsp" />
<script>
document.getElementById("findPasswordForm").addEventListener("submit", function(event) {
    const code = document.getElementById("memberCode").value.trim();
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const error = document.getElementById("clientError");
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    let message = "";
    if (!/^[A-Za-z0-9]{4,20}$/.test(code)) {
        message = "아이디는 영문 또는 숫자 4~20자로 입력해 주세요.";
    } else if (name === "") {
        message = "이름을 입력해 주세요.";
    } else if (!emailPattern.test(email)) {
        message = "올바른 이메일을 입력해 주세요.";
    }

    if (message !== "") {
        event.preventDefault();
        error.textContent = message;
        error.hidden = false;
    }
});
</script>
</body>
</html>

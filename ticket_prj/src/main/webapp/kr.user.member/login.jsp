<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>로그인 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr.user.member/member.css">
</head>
<body>
<jsp:include page="/include/header.jsp" />

<main class="member-page">
    <section class="member-shell member-shell-narrow">
        <h1 class="member-title">로그인</h1>
        <p class="member-description">볼픽 회원코드와 비밀번호를 입력해 주세요.</p>

        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="member-error"><%=request.getAttribute("errorMessage")%></div>
        <% } %>

        <form class="member-form" id="loginForm" method="post"
              action="<%=request.getContextPath()%>/member/login" novalidate>
            <div class="member-field">
                <label for="memberCode">회원코드</label>
                <input class="member-input" type="text" id="memberCode" name="memberCode"
                       maxlength="20" autocomplete="username" placeholder="회원코드를 입력해 주세요.">
                <p class="member-help">영문 또는 숫자 4~20자</p>
            </div>
            <div class="member-field">
                <label for="password">비밀번호</label>
                <input class="member-input" type="password" id="password" name="password"
                       maxlength="20" autocomplete="current-password" placeholder="비밀번호를 입력해 주세요.">
            </div>
            <div id="clientError" class="member-error" hidden></div>
            <button class="member-button" type="submit">로그인</button>
        </form>

        <div class="member-link-row">
            <a href="<%=request.getContextPath()%>/member/find-code">회원코드 찾기</a>
            <span class="member-link-divider"></span>
            <a href="<%=request.getContextPath()%>/member/find-password">비밀번호 찾기</a>
        </div>

        <div class="member-join-panel">
            <p>아직 볼픽 회원이 아니신가요?</p>
            <a class="member-button member-button-light"
               href="<%=request.getContextPath()%>/member/join-agree">회원가입</a>
        </div>
    </section>
</main>

<jsp:include page="/include/footer.jsp" />
<script>
document.getElementById("loginForm").addEventListener("submit", function(event) {
    const code = document.getElementById("memberCode").value.trim();
    const password = document.getElementById("password").value.trim();
    const error = document.getElementById("clientError");

    if (!/^[A-Za-z0-9]{4,20}$/.test(code)) {
        event.preventDefault();
        error.textContent = "회원코드는 영문 또는 숫자 4~20자로 입력해 주세요.";
        error.hidden = false;
        document.getElementById("memberCode").focus();
        return;
    }
    if (password === "") {
        event.preventDefault();
        error.textContent = "비밀번호를 입력해 주세요.";
        error.hidden = false;
        document.getElementById("password").focus();
    }
});
</script>
</body>
</html>

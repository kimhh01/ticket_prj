<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원가입 완료 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr/user/member/member.css">
</head>
<body>
<jsp:include page="/fragment/header.jsp" />

<main class="member-page">
    <section class="member-shell member-shell-narrow">
        <h1 class="member-title">회원가입 완료</h1>
        <div class="member-steps" aria-label="회원가입 단계">
            <div class="member-step"><span class="member-step-number">1</span>약관동의</div>
            <div class="member-step"><span class="member-step-number">2</span>정보입력</div>
            <div class="member-step active"><span class="member-step-number">3</span>가입완료</div>
        </div>

        <div class="member-result">
            <div class="member-result-icon">i</div>
            <h2>${joinedName}님, 환영합니다.</h2>
            <p>회원가입이 완료되었습니다.</p>
            <div class="member-result-summary">아이디: ${joinedMemberCode}</div>
        </div>

        <div class="member-actions">
            <a class="member-button member-button-light" href="<%=request.getContextPath()%>/main">메인으로</a>
            <a class="member-button" href="<%=request.getContextPath()%>/member/login">로그인</a>
        </div>
    </section>
</main>

<jsp:include page="/fragment/footer.jsp" />
</body>
</html>

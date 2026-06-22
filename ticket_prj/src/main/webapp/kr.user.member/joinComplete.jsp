<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원가입 완료 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr.user.member/member.css">
</head>
<body>
<jsp:include page="/include/header.jsp" />

<main class="member-page">
    <section class="member-shell">
        <h1 class="member-title">회원가입</h1>
        <div class="member-steps" aria-label="회원가입 단계">
            <div class="member-step"><span class="member-step-number">1</span>약관동의</div>
            <div class="member-step"><span class="member-step-number">2</span>정보입력</div>
            <div class="member-step active"><span class="member-step-number">3</span>가입완료</div>
        </div>

        <div class="member-result">
            <div class="member-result-icon">✓</div>
            <h2>${joinedName}님, 환영합니다.</h2>
            <p>회원가입 화면 입력이 완료되었습니다.<br>DB 연결 후 실제 회원정보 저장이 적용됩니다.</p>
            <div class="member-result-summary">회원코드: ${joinedMemberCode}</div>
        </div>

        <div class="member-actions">
            <a class="member-button member-button-light" href="<%=request.getContextPath()%>/main.do">메인으로</a>
            <a class="member-button" href="<%=request.getContextPath()%>/member/login">로그인</a>
        </div>
    </section>
</main>

<jsp:include page="/include/footer.jsp" />
</body>
</html>

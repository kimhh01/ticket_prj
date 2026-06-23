<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>아이디 찾기 결과 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr.user.member/member.css">
</head>
<body>
<jsp:include page="/include/header.jsp" />

<main class="member-page">
    <section class="member-shell member-shell-narrow">
        <h1 class="member-title">아이디 찾기</h1>
        <div class="member-result">
            <div class="member-result-icon">i</div>
            <h2>회원 정보가 확인되었습니다.</h2>
            <p>입력하신 정보와 일치하는 아이디입니다.</p>
            <div class="member-result-summary">
                이름: ${verifiedName}<br>
                이메일: ${verifiedEmail}<br>
                아이디: <strong>${foundMemberCode}</strong>
            </div>
        </div>
        <div class="member-actions">
            <a class="member-button member-button-light"
               href="<%=request.getContextPath()%>/member/find-code">다시 찾기</a>
            <a class="member-button" href="<%=request.getContextPath()%>/member/login">로그인</a>
        </div>
    </section>
</main>

<jsp:include page="/include/footer.jsp" />
</body>
</html>

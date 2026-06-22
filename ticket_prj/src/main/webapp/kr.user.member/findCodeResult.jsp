<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원코드 찾기 결과 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr.user.member/member.css">
</head>
<body>
<jsp:include page="/include/header.jsp" />

<main class="member-page">
    <section class="member-shell member-shell-narrow">
        <h1 class="member-title">회원코드 찾기</h1>
        <div class="member-result">
            <div class="member-result-icon">i</div>
            <h2>회원정보가 확인되었습니다.</h2>
            <p>DB 연결 후 일치하는 회원코드를 이 영역에 표시합니다.</p>
            <div class="member-result-summary">
                이름: ${verifiedName}<br>
                이메일: ${verifiedEmail}
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

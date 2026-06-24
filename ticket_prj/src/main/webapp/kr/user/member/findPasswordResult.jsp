<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>비밀번호 찾기 결과 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr/user/member/member.css">
</head>
<body>
<jsp:include page="/fragment/header.jsp" />

<main class="member-page">
    <section class="member-shell member-shell-narrow">
        <h1 class="member-title">비밀번호 찾기</h1>
        <div class="member-result">
            <div class="member-result-icon">i</div>
            <h2>임시 비밀번호가 고객님의 이메일로 전송되었습니다.</h2>
            <p>현재 메일 발송 기능은 준비 중이므로 검수용 임시 비밀번호를 함께 표시합니다.</p>
            <div class="member-result-summary">
                아이디: ${verifiedMemberCode}<br>
                이름: ${verifiedName}<br>
                이메일: ${verifiedEmail}<br>
                임시 비밀번호: <strong>${tempPassword}</strong>
            </div>
        </div>
        <div class="member-actions">
            <a class="member-button member-button-light"
               href="<%=request.getContextPath()%>/member/find-password">다시 찾기</a>
            <a class="member-button" href="<%=request.getContextPath()%>/member/login">로그인</a>
        </div>
    </section>
</main>

<jsp:include page="/fragment/footer.jsp" />
</body>
</html>

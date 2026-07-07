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
            <h2>임시 비밀번호가 이메일로 전송되었습니다.</h2>
            <p>
                입력하신 이메일 주소로 임시 비밀번호를 발송했습니다.<br>
                로그인 후 반드시 비밀번호를 변경해 주세요.
            </p>

            <div class="member-result-summary">
                아이디: ${verifiedMemberCode}<br>
                이름: ${verifiedName}<br>
                이메일: ${verifiedEmail}
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
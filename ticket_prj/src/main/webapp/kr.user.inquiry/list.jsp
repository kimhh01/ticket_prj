<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>내 문의내역 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr.user.inquiry/inquiry.css">
</head>
<body>
<jsp:include page="/include/header.jsp" />

<main class="inquiry-page">
    <section class="inquiry-shell inquiry-shell-narrow">
        <h1 class="inquiry-title">고객센터</h1>
        <nav class="inquiry-nav" aria-label="고객센터 메뉴">
            <a href="<%=request.getContextPath()%>/user-inquiry">자주 묻는 질문</a>
            <a href="<%=request.getContextPath()%>/user-inquiry/write">1:1 문의하기</a>
            <a class="active" href="<%=request.getContextPath()%>/user-inquiry/list">내 문의내역</a>
        </nav>

        <div class="inquiry-empty">
            <h2>등록된 문의가 없습니다.</h2>
            <p>문의 내역은 DB 연결 후 회원별로 표시됩니다.</p>
            <a class="inquiry-button" href="<%=request.getContextPath()%>/user-inquiry/write">1:1 문의하기</a>
        </div>
    </section>
</main>

<jsp:include page="/include/footer.jsp" />
</body>
</html>

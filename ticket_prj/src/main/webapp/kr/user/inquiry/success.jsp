<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>문의 등록 완료 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr/user/inquiry/inquiry.css?v=20260709-1">

<style>
.success-wrap {
	min-height: 500px;
	display: flex;
	justify-content: center;
	align-items: center;
}

.success-box {
	width: 420px;
	border: 1px solid #ddd;
	padding: 40px 35px;
	text-align: center;
	background: #fff;
	box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
}

.success-title {
	font-size: 26px;
	font-weight: 700;
	margin-bottom: 16px;
}

.success-message {
	font-size: 15px;
	color: #555;
	line-height: 1.6;
	margin-bottom: 32px;
}

.success-buttons {
	display: flex;
	gap: 10px;
}

.success-buttons a {
	flex: 1;
	height: 48px;
	line-height: 48px;
	text-decoration: none;
	font-weight: 700;
	border: 1px solid #111;
}

.btn-list {
	background: #111;
	color: #fff;
}

.btn-home {
	background: #fff;
	color: #111;
}
</style>
</head>
<body>

<jsp:include page="/fragment/header.jsp" />

<main class="inquiry-page">
	<section class="success-wrap">
		<div class="success-box">
			<h1 class="success-title">문의 등록 완료</h1>

			<p class="success-message">
				문의가 정상적으로 등록되었습니다.<br>
				답변이 등록되면 내 문의내역에서 확인할 수 있습니다.
			</p>

			<div class="success-buttons">
				<a class="btn-list" href="<%=request.getContextPath()%>/user-inquiry/list">내 문의내역</a>
				<a class="btn-home" href="<%=request.getContextPath()%>/main">홈으로</a>
			</div>
		</div>
	</section>
</main>

<jsp:include page="/fragment/footer.jsp" />

</body>
</html>

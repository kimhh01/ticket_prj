<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="activeMenu" value="member" scope="request" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 할인 관리</title>

<style>
body {
	margin: 0;
	background: #f4f4f7;
	font-family: 'Noto Sans KR', sans-serif;
	color: #222;
}

.layout {
	display: flex;
}

.content {
	flex: 1;
	padding: 32px;
	background: #fff;
	min-height: calc(100vh - 60px);
}

.page-title {
	font-size: 22px;
	font-weight: 700;
	margin-bottom: 20px;
}

.tab-menu {
	display: flex;
	gap: 20px;
	margin-bottom: 28px;
}

.tab-menu a {
	text-decoration: none;
	color: #777;
	font-size: 14px;
	padding-bottom: 8px;
}

.tab-menu a.active {
	color: #ff5b68;
	border-bottom: 2px solid #ff5b68;
	font-weight: 700;
}

.discount-wrap {
	max-width: 720px;
}

.notice {
	background: #fff5f5;
	border: 1px solid #ffd6d6;
	color: #b84242;
	padding: 14px 18px;
	border-radius: 8px;
	font-size: 14px;
	margin-bottom: 18px;
}

.card {
	border: 1px solid #ffd6d6;
	background: #fffafa;
	border-radius: 10px;
	padding: 24px;
	margin-bottom: 18px;
}

.card-title {
	color: #e54858;
	font-weight: 700;
	margin-bottom: 18px;
}

.form-row {
	margin-bottom: 16px;
}

.form-row label {
	display: block;
	font-size: 14px;
	margin-bottom: 8px;
	font-weight: 600;
}

.input-percent {
	display: flex;
	align-items: center;
}

.input-percent input {
	width: 100%;
	padding: 11px 12px;
	border: 1px solid #ddd;
	border-radius: 6px;
}

.input-percent span {
	margin-left: 8px;
}

.help-text {
	font-size: 12px;
	color: #777;
	margin-top: 6px;
}

.btn-save {
	background: #ff5b68;
	color: #fff;
	border: none;
	padding: 11px 22px;
	border-radius: 6px;
	cursor: pointer;
}
</style>
</head>

<body>

	<jsp:include page="../common/topBar.jsp" />

	<div class="layout">
		<jsp:include page="../common/sideBar.jsp" />

		<main class="content">
			<h2 class="page-title">회원 관리</h2>

			<div class="tab-menu">
				<a
					href="${pageContext.request.contextPath}/manage/member/memberList.jsp">회원
					관리</a> <a
					href="${pageContext.request.contextPath}/manage/member/memberDiscount.jsp"
					class="active">회원 할인</a>
			</div>

			<section class="discount-wrap">
				<div class="notice">VIP 회원에게 적용되는 할인율만 관리합니다.</div>

				<form method="post"
					action="${pageContext.request.contextPath}/manage/member/discountUpdate">
					<div class="card">
						<div class="card-title">VIP 회원 할인</div>

						<div class="form-row">
							<label for="vipDiscountRate">할인율</label>

							<div class="input-percent">
								<input type="number" id="vipDiscountRate" name="vipDiscountRate"
									min="0" max="100" value="${vipDiscountRate}"> <span>%</span>
							</div>

							<div class="help-text">0 ~ 100 사이의 값을 입력해주세요.</div>
						</div>

						<button type="submit" class="btn-save">저장</button>
					</div>
				</form>
			</section>
		</main>
	</div>

</body>
</html>
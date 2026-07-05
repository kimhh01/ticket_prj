<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="activeMenu" value="member" scope="request" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 상세 관리</title>

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

.breadcrumb {
	font-size: 13px;
	color: #777;
	margin-bottom: 20px;
}

.page-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.page-title {
	font-size: 22px;
	font-weight: 700;
}

.btn-warning {
	border: 1px solid #f6b26b;
	background: #fffaf2;
	color: #d98200;
	padding: 9px 16px;
	border-radius: 6px;
	cursor: pointer;
}

.profile-card {
	margin-top: 20px;
	border: 1px solid #eee;
	border-radius: 10px;
	padding: 24px;
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.profile-left {
	display: flex;
	align-items: center;
	gap: 18px;
}

.profile-icon {
	width: 58px;
	height: 58px;
	background: #fff1d9;
	color: #b17400;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
}

.profile-name {
	font-size: 20px;
	font-weight: 700;
}

.profile-info {
	font-size: 13px;
	color: #666;
}

.profile-stat {
	display: flex;
	gap: 60px;
	text-align: center;
}

.profile-stat strong {
	display: block;
	font-size: 22px;
}

.badge {
	padding: 4px 10px;
	border-radius: 20px;
	font-size: 12px;
}

.badge.normal {
	background: #f1f1f1;
}

.badge.vip {
	background: #eaf2ff;
	color: #2374ff;
}

.badge.stop {
	background: #ffecec;
	color: #ff5b68;
}

.grid {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 14px;
	margin-top: 18px;
}

.info-box {
	border: 1px solid #eee;
	border-radius: 10px;
	padding: 20px;
}

.info-box h3 {
	margin-top: 0;
	font-size: 16px;
}

.info-row {
	display: flex;
	justify-content: space-between;
	padding: 9px 0;
	border-bottom: 1px solid #f3f3f3;
	font-size: 14px;
}

.info-row:last-child {
	border-bottom: none;
}

.info-row span:first-child {
	color: #777;
}

.order-box {
	margin-top: 18px;
	border: 1px solid #eee;
	border-radius: 10px;
	padding: 20px;
}

.order-table {
	width: 100%;
	border-collapse: collapse;
	font-size: 14px;
}

.order-table th, .order-table td {
	padding: 13px 10px;
	border-bottom: 1px solid #eee;
	text-align: left;
}

.order-table th {
	background: #fafafa;
	color: #666;
}

.status-complete {
	color: #0aaf55;
	font-weight: 600;
}

.status-cancel {
	color: #ff5b68;
	font-weight: 600;
}
</style>
</head>

<body>

	<jsp:include page="../common/topBar.jsp" />

	<div class="layout">
		<jsp:include page="../common/sideBar.jsp" />

		<main class="content">
			<div class="breadcrumb">회원 관리 &gt; 회원상세</div>

			<div class="page-header">
				<h2 class="page-title">회원 상세 정보</h2>

				<form method="post"
					action="${pageContext.request.contextPath}/manage/member/memberStateUpdate">
					<input type="hidden" name="memberId" value="${mdDTO.memberCode}">
					<button type="submit" class="btn-warning">휴면 해제</button>
				</form>
			</div>

			<section class="profile-card">
				<div class="profile-left">
					<div class="profile-icon">박</div>

					<div>
						<div class="profile-name">${mlDTO.memberName}</div>
						<div class="profile-info">${mlDTO.memberEmail} ·
							${mlDTO.memberTel}</div>

						<div style="margin-top: 8px;">
							<c:choose>
								<c:when test="${mlDTO.memberGrade eq 'VIP'}">
									<span class="badge vip">VIP</span>
								</c:when>
								<c:otherwise>
									<span class="badge normal">일반</span>
								</c:otherwise>
							</c:choose>

							<span class="badge stop">${mlDTO.memberState}</span>
						</div>
					</div>
				</div>

				<div class="profile-stat">
					<div>
						<strong>${mdDTO.purchaseCount}</strong> <span>구매 횟수</span>
					</div>

					<div>
						<strong>${mdDTO.totalPayment}</strong> <span>총 결제 금액</span>
					</div>
				</div>
			</section>

			<section class="grid">
				<div class="info-box">
					<h3>기본 정보</h3>

					<div class="info-row">
						<span>회원 ID</span> <span>${mdDTO.memberCode}</span>
					</div>

					<div class="info-row">
						<span>이름</span> <span>${mlDTO.memberName}</span>
					</div>

					<div class="info-row">
						<span>이메일</span> <span>${mlDTO.memberEmail}</span>
					</div>

					<div class="info-row">
						<span>전화번호</span> <span>${mlDTO.memberTel}</span>
					</div>

					<div class="info-row">
						<span>우편번호</span> <span>${mdDTO.zipCode}</span>
					</div>

					<div class="info-row">
						<span>주소</span> <span>${mdDTO.address}</span>
					</div>
				</div>

				<div class="info-box">
					<h3>활동 정보</h3>

					<div class="info-row">
						<span>가입일</span> <span>${mlDTO.joinDate}</span>
					</div>

					<div class="info-row">
						<span>마지막 로그인</span> <span>${mdDTO.lastLogin}</span>
					</div>

					<div class="info-row">
						<span>마케팅 수신 동의</span> <span>${mdDTO.marketingAgree}</span>
					</div>
				</div>
			</section>

			<section class="order-box">
				<h3>구매 내역</h3>

				<table class="order-table">
					<thead>
						<tr>
							<th>주문 번호</th>
							<th>티켓 정보</th>
							<th>결제 금액</th>
							<th>구매일</th>
							<th>상태</th>
						</tr>
					</thead>

					<tbody>
						<c:choose>
							<c:when test="${empty payHistory}">
								<tr>
									<td colspan="5" style="text-align: center; padding: 35px;">
										구매 내역이 없습니다.</td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach var="mpDTO" items="${payHistory}">
									<tr>
										<td>${mpDTO.reservationCode}</td>

										<td>${mpDTO.homeTeam} vs ${mpDTO.awayTeam} <br> <small>수량
												${mpDTO.reservationCnt}매</small>
										</td>

										<td>${mpDTO.paymentPrice}</td>
										<td>${mpDTO.reservationDate}</td>

										<td><c:choose>
												<c:when test="${mpDTO.reservationState eq '완료'}">
													<span class="status-complete">완료</span>
												</c:when>
												<c:otherwise>
													<span class="status-cancel">${mpDTO.reservationState}</span>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</section>
		</main>
	</div>

</body>
</html>
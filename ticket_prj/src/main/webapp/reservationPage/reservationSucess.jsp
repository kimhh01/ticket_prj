<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>bigBall예매 - 예매완료</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/reservationPage/reservation.css">
<style>
    /* 성공 페이지 전용 추가 스타일 */
    .success-container {
        flex: 1;
        padding: 40px;
        background: #fff;
        border: 1px solid #ddd;
        text-align: center;
    }
    .success-icon {
        font-size: 60px;
        color: #28a745;
        margin-bottom: 20px;
    }
    .success-title {
        font-size: 28px;
        font-weight: 900;
        margin-bottom: 10px;
    }
    .success-msg {
        color: #666;
        margin-bottom: 40px;
    }
    .info-table-wrapper {
        max-width: 600px;
        margin: 0 auto 40px;
        border-top: 2px solid #333;
    }
    .info-table {
        width: 100%;
        border-collapse: collapse;
    }
    .info-table th, .info-table td {
        padding: 15px;
        border-bottom: 1px solid #eee;
        text-align: left;
        font-size: 14px;
    }
    .info-table th {
        background: #f8f8f8;
        width: 30%;
        color: #666;
    }
    .info-table td {
        font-weight: bold;
    }
    .order-number {
        color: #1d70e2;
        font-family: 'Courier New', monospace;
    }
    .final-btn-group {
        display: flex;
        justify-content: center;
        gap: 15px;
    }
    .btn-home {
        width: 180px;
        height: 50px;
        background: #fff;
        border: 1px solid #333;
        font-weight: bold;
        border-radius: 4px;
        transition: background 0.3s ease;
    }
    .btn-mypage {
        width: 180px;
	    height: 50px;
	    background: #333;
	    color: #fff;
	    font-weight: bold;
	    border: none;
	    border-radius: 4px;
	    transition: background 0.3s ease;
    }
    .btn-home:hover{background: #f5f5f5;}
    .btn-mypage:hover{background: #555;}
</style>
</head>
<body>

	<div class="ticketlink-container">
		<header class="booking-header">
			<div class="logo-area">
				big<span style="font-weight: 900;">Ball</span> 예매
			</div>
		</header>

		<!-- 진행 바: 마지막 단계(결제) 활성화 -->
		<nav class="step-progress-bar">
			<div class="step-item">날짜/회차선택</div>
			<div class="step-item">등급/좌석선택</div>
			<div class="step-item">권종/할인/매수선택</div>
			<div class="step-item">배송선택/예매확인</div>
			<div class="step-item active">결제완료</div>
		</nav>

		<main class="main-workspace">
			<div class="success-container">
				<div class="success-icon">✔</div>
				<h2 class="success-title">예매가 완료되었습니다!</h2>
				<p class="success-msg">고객님의 예매가 성공적으로 완료되었습니다. 즐거운 관람 되시기 바랍니다.</p>

				<div class="info-table-wrapper">
					<table class="info-table">
						<tr>
							<th>예매번호</th>
							<td class="order-number">${orderId}</td>
						</tr>
						<tr>
							<th>경기정보</th>
							<td>
								${gameInfo.teamHomeName} vs ${gameInfo.teamOtherName}<br>
								<span style="font-weight: normal; font-size: 12px; color: #888;">${gameInfo.gameDate} ${gameInfo.gameStartTime}</span>
							</td>
						</tr>
						<tr>
							<th>선택좌석</th>
							<td>${selectedSeatName} / ${reservationQuantity}매</td>
						</tr>
						<tr>
							<th>결제금액</th>
							<td class="red"><fmt:formatNumber value="${payPrice}" pattern="#,###"/>원</td>
						</tr>
						<tr>
							<th>수령방법</th>
							<td>현장수령 (매표소 또는 무인발권기)</td>
						</tr>
						<tr>
							<th>예매자</th>
							<td>${sessionScope.loginMember.name}</td>
						</tr>
					</table>
				</div>

				<div class="final-btn-group">
					<button type="button" class="btn-home" onclick="location.href='${pageContext.request.contextPath}/index.jsp'">홈으로 이동</button>
					<button type="button" class="btn-mypage" onclick="location.href='${pageContext.request.contextPath}/mypage_user/reservation.jsp'">예매확인/취소</button>
				</div>
			</div>
		</main>
	</div>

</body>
</html>
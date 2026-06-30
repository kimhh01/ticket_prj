<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>bigBall예매 - 결제실패</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/reservationPage/reservation.css">
<style>
    /* 실패 페이지 전용 추가 스타일 */
    .fail-container {
        flex: 1;
        padding: 50px 40px;
        background: #fff;
        border: 1px solid #ddd;
        text-align: center;
        
        display: flex;
		flex-direction: column;
		min-height: 450px;
    }
    .fail-icon {
        font-size: 60px;
        color: #e60000; /* 기존 레드 테마색 사용 */
        margin-bottom: 20px;
    }
    .fail-title {
        font-size: 28px;
        font-weight: 900;
        margin-bottom: 10px;
        color: #333;
    }
    .fail-msg {
        color: #666;
        margin-bottom: 30px;
        font-size: 16px;
    }
    .error-detail-box {
        max-width: 500px;
        margin: 0 auto 40px;
        padding: 20px;
        background: #fdf2f2;
        border: 1px solid #fadbd8;
        border-radius: 8px;
        text-align: left;
    }
    .error-item {
        margin-bottom: 10px;
        font-size: 14px;
    }
    .error-label {
        font-weight: bold;
        color: #e60000;
        display: inline-block;
        width: 80px;
    }
    .error-text {
        color: #333;
        word-break: break-all;
    }
    .fail-guide-text {
        font-size: 13px;
        color: #888;
        line-height: 1.6;
        margin-bottom: 40px;
    }
    .final-btn-group {
        display: flex;
        justify-content: center;
        gap: 15px;
        margin-top: auto;
    }
    .btn-retry {
        width: 180px;
	    height: 50px;
	    background: #333; /* 기본 배경 */
	    color: #fff;
	    font-weight: bold;
	    border: none;
	    border-radius: 4px;
	    transition: background 0.3s ease; /* 부드러운 변화 추가 */
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
    .btn-home:hover { background: #f5f5f5; }
    .btn-retry:hover { background: #555; }
</style>
</head>
<body>

	<div class="ticketlink-container">
		<header class="booking-header">
			<div class="logo-area">
				big<span style="font-weight: 900;">Ball</span> 예매
			</div>
			<div class="timer-area">
				<span style="color: #e60000; font-weight: bold;">결제 실패</span>
			</div>
		</header>

		<nav class="step-progress-bar">
			<div class="step-item">날짜/회차선택</div>
			<div class="step-item">등급/좌석선택</div>
			<div class="step-item">권종/할인/매수선택</div>
			<div class="step-item">배송선택/예매확인</div>
			<div class="step-item active">결제실패</div>
		</nav>

		<main class="main-workspace">
			<div class="fail-container">
				<div class="fail-icon">✕</div>
				<h2 class="fail-title">결제에 실패하였습니다</h2>
				<p class="fail-msg">주문 처리 중 오류가 발생하여 예매가 완료되지 않았습니다.</p>

						<%
						    // 내장 객체 request를 사용하여 파라미터를 직접 가져옵니다.
						    String errorCode = request.getParameter("code");
						    String errorMsg = request.getParameter("message");
						
						    System.out.println("====== 토스 결제 실패 로그 ======");
						    System.out.println("에러 코드 : " + errorCode);
						    System.out.println("실패 사유 : " + errorMsg);
						    System.out.println("=================================");
						%>

				<div class="fail-guide-text">
					※ 일시적인 장애일 수 있으니 잠시 후 다시 시도해 주세요.<br>
					※ 지속적으로 실패할 경우 고객센터 혹은 결제 수단 해당사에 문의하시기 바랍니다.
				</div>

				<div class="final-btn-group">
					<button type="button" class="btn-home">홈으로 이동</button>
					<!-- 다시 예매를 시도할 수 있도록 일정 선택 페이지로 유도 -->
					<button type="button" class="btn-retry">다시 예매하기</button>
				</div>
			</div>
		</main>
	</div>

</body>
<script type="text/javascript">
$(function(){
	$(".btn-home").click(function(){
		opener.location.href='location.href='+${pageContext.request.contextPath}+'/index.jsp'
		window.close()
	});
	
	$(".btn-retry").click(function(){
		opener.location.href='location.href='+${pageContext.request.contextPath}+'/teamPage/teamPage.jsp'
		window.close()
	});
	
	
});//ready
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>BallPick 예매 가이드</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap" rel="stylesheet">
	<link rel="stylesheet"
      href="${pageContext.request.contextPath}/kr/user/reservationGuide/reservationGuide.css">
</head>
<body>

<div class="guide-container">
    <h1 class="page-title">예매 안내</h1>

    <!-- STEP 1 -->
    <section class="step-section">
        <div class="step-header">
            <span class="step-num">STEP 1</span>
            <span class="step-title">날짜/회차 선택</span>
        </div>
        <p class="guide-text"><span class="bullet">●</span> 각 구단 페이지에서 예매 오픈 시간에 맞춰 [예매하기] 버튼을 클릭합니다.</p>
        
        <div class="tip-box">
            <div class="img-placeholder">
                <img src="<%=request.getContextPath() %>/images/reservation_guide/reservation-step1.png">
            </div>
        </div>
			
    </section>

    <!-- STEP 2 -->
    <section class="step-section">
        <div class="step-header">
            <span class="step-num">STEP 2</span>
            <span class="step-title">등급/좌석 선택</span>
        </div>
        <p class="guide-text"><span class="bullet">●</span>클린예매 서비스에 나오는 글자를 입력하고 [입력 완료] 버튼을 클릭합니다.</p>
			<div class="tip-box">
       		<div class="img-placeholder">
           		<img src="<%=request.getContextPath() %>/images/reservation_guide/reservation-step2-1.png">
       		</div>
        </div>
        <p class="guide-text"><span class="bullet">●</span> 구장 이미지를 확인하고 오른쪽 사이드바의 자리를 클릭 후 [다음단계] 버튼을 클릭합니다.</p>
        <div class="tip-box">
	        <div class="img-placeholder">
	            <img src="<%=request.getContextPath() %>/images/reservation_guide/reservation-step2-2.png">
	        </div>
        </div>
    </section>

    <!-- STEP 3 -->
    <section class="step-section">
        <div class="step-header">
            <span class="step-num">STEP 3</span>
            <span class="step-title">권종/할인/매수 선택</span>
        </div>
        <p class="guide-text"><span class="bullet">●</span> 티켓의 종류와 할인 종류를 선택 후 [다음단계] 버튼을 클릭합니다.</p>
        <p class="guide-text"><span class="bullet">●</span> 할인 티켓은 하나만 적용됩니다.</p>
        <div class="tip-box">
	        <div class="img-placeholder" style="min-height: 200px;">
	            <img src="<%=request.getContextPath() %>/images/reservation_guide/reservation-step3.png">
	        </div>
	    </div>
    </section>

    <!-- STEP 4 -->
    <section class="step-section">
        <div class="step-header">
            <span class="step-num">STEP 4</span>
            <span class="step-title">배송선택/예매확인</span>
        </div>
        <p class="guide-text"><span class="bullet">●</span> 예매자 확인 체크 박스를 클릭, 예매 정보를 최종 확인 후 [결제하기] 버튼을 클릭합니다.</p>
        <div class="tip-box">
	        <div class="img-placeholder" style="min-height: 200px;">
	            <img src="<%=request.getContextPath() %>/images/reservation_guide/reservation-step4.png">
	        </div>
        </div>
    </section>

    <!-- STEP 5 -->
    <section class="step-section">
        <div class="step-header">
            <span class="step-num">STEP 5</span>
            <span class="step-title">결제</span>
        </div>
        <p class="guide-text"><span class="bullet">●</span>결제를 완료하면 예매가 성공적으로 마무리됩니다.</p>
        <div class="tip-box">
	        <div class="img-placeholder" style="min-height: 200px;">
	             <img src="<%=request.getContextPath() %>/images/reservation_guide/reservation-success.png">
	        </div>
        </div>
    </section>

</div>

</body>
</html>
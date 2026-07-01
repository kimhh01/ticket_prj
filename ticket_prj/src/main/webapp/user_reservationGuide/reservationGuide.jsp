<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>야구 예매 가이드 - 티켓링크</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="reservationGuide.css">
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
            <div class="tip-content">
                티켓링크 시스템 시간이 예매 오픈 시간이 되는 순간, <strong>새로고침(F5)</strong> 하시면 [오픈예정] 버튼이 [예매하기] 버튼으로 변경됩니다.
            </div>
            <!-- 이미지 자리 -->
            <div class="img-placeholder">
                <!-- <img src="step1_1.jpg" alt="이미지"> -->
                <img src="reservation-step1.png">
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
           		<img src="reservation-step2-1.png">
       		</div>
        </div>
        <p class="guide-text"><span class="bullet">●</span> 구장 이미지를 확인하고 오른쪽 사이드바의 자리를 클릭 후 [다음 단계] 버튼을 클릭합니다.</p>
        <div class="img-placeholder">
            <img src="reservation-step2.png">
        </div>
    </section>

    <!-- STEP 3 -->
    <section class="step-section">
        <div class="step-header">
            <span class="step-num">STEP 3</span>
            <span class="step-title">권종/할인/매수 선택</span>
        </div>
        <p class="guide-text"><span class="bullet">●</span> 티켓의 종류와 개수를 선택하고 할인이 적용되는 경우 해당 항목의 할인 코드를 작성 후 적용을 누릅니다.</p>
        <div class="img-placeholder" style="min-height: 200px;">
            <img src="reservation-step3.png">
        </div>
    </section>

    <!-- STEP 4 -->
    <section class="step-section">
        <div class="step-header">
            <span class="step-num">STEP 4</span>
            <span class="step-title">배송선택/예매확인</span>
        </div>
        <p class="guide-text"><span class="bullet">●</span> 예매 정보를 최종 확인 후 [결제 버튼] 을 클릭합니다.</p>
        <div class="img-placeholder" style="min-height: 200px;">
            <img src="reservation-step4.png">
        </div>
    </section>

    <!-- STEP 5 -->
    <section class="step-section">
        <div class="step-header">
            <span class="step-num">STEP 5</span>
            <span class="step-title">결제</span>
        </div>
        <p class="guide-text"><span class="bullet">●</span> 결제하기 버튼을 누르고 결제를 완료하면 예매가 성공적으로 마무리됩니다.</p>
        <div class="img-placeholder" style="min-height: 200px;">
             <img src="reservationSuccess.png">
        </div>
    </section>

</div>

</body>
</html>
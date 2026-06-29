<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>bigBall예매</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/reservationPage/reservation.css">
<script src="https://js.tosspayments.com/v1/payment"></script>
</head>
<body>

	<div id="captcha-overlay" class="captcha-overlay">
		<div class="captcha-modal">
			<div class="captcha-title">클린예매 서비스</div>
			<div class="captcha-desc">
				부정예매를 방지하기 위해 <span>보안문자 입력</span> 후 예매가 가능합니다.
			</div>
			<div class="captcha-img-box">
				<div id="captcha-display-text">LOADING</div>
				<button type="button" class="btn-captcha-refresh" onclick="generateCaptchaValue()">↻</button>
			</div>
			<input type="text" id="captcha-user-input" class="captcha-input-box"
				maxlength="6" autocomplete="off">
			<div class="captcha-btns">
				<button type="button" class="btn-date-select" onclick="goToTeamSelection()">날짜 다시 선택</button>
				<button type="button" class="btn-confirm-captcha" onclick="verifyCaptcha()">입력 완료</button>
			</div>
		</div>
	</div>

	<div class="ticketlink-container">
		<header class="booking-header">
			<div class="logo-area">
				big<span style="font-weight: 900;">Ball</span> 예매
			</div>
			<div class="timer-area">
				<span>예매가능 시간</span> <span id="countdown" class="timer-time">10:00</span>
			</div>
		</header>

		<nav class="step-progress-bar">
			<div class="step-item" id="step-nav-1">날짜/회차선택</div>
			<div class="step-item active" id="step-nav-2">등급/좌석선택</div>
			<div class="step-item" id="step-nav-3">권종/할인/매수선택</div>
			<div class="step-item" id="step-nav-4">배송선택/예매확인</div>
			<div class="step-item" id="step-nav-5">결제</div>
		</nav>

		<form id="reservationForm" action="${pageContext.request.contextPath}/reservationPageServlet" method="POST">
			
			<input type="hidden" name="stadiumSeatCode" id="hiddenStadiumSeatCode" value="1"> 
			<input type="hidden" name="reservationType" id="hiddenReservationType" value="성인">
			<input type="hidden" name="reservationQuantity" id="hiddenReservationQuantity" value="0">
			<input type="hidden" name="totalPrice" id="hiddenTotalPrice" value="0">
			<input type="hidden" name="discountPrice" id="hiddenDiscountPrice" value="0">
			<input type="hidden" name="payPrice" id="hiddenPayPrice" value="0">

			<main class="main-workspace">
	
				<div id="view-step-2" class="view-section">
					<div class="stadium-border-box">
						<span style="color: #aaa;">
							<c:choose>
								<c:when test="${not empty gameInfo.stadiumImg}">
									<img src="${pageContext.request.contextPath}/images/stadium/${gameInfo.stadiumImg}" alt="구장이미지" style="max-width:100%;">
								</c:when>
								<c:otherwise>구장 배치 이미지가 준비되지 않았습니다.</c:otherwise>
							</c:choose>
						</span>
					</div>
	
					<div class="side-panel">
						<div class="match-card-img-style">
							<img src="${pageContext.request.contextPath}/images/team_logo/${gameInfo.teamHomeImg}" alt="${gameInfo.teamHomeName}" class="team-logo-placeholder"
								onerror="this.src='https://via.placeholder.com/60x40?text=HOME'">
							<span class="vs-text-gray">VS</span> 
							<img src="${pageContext.request.contextPath}/images/team_logo/${gameInfo.teamOtherImg}" alt="${gameInfo.teamOtherName}" class="team-logo-placeholder"
								onerror="this.src='https://via.placeholder.com/60x40?text=AWAY'">
						</div>
	
						<div class="grade-header">
							<span class="grade-title-text">등급 선택</span>
							<button type="button" class="btn-refresh-img">새로고침</button>
						</div>
	
						<div class="seats-scroll-box-img">
							<div class="seats-list-item-img" style="background: #f7f7f7; font-weight: bold;">
								<span>전체</span>
							</div>
							<div class="seats-list-item-img" id="item-1" onclick="mockSeatSelect('1루 자유석', 'item-1', 1)">
								<div class="grade-name-group">
									<span class="color-square sq-blue"></span> <span>1루 자유석</span>
								</div>
								<span class="seat-count-text">${remainingSeat.firstBaseSeat }</span>
							</div>
							<div class="seats-list-item-img" id="item-2" onclick="mockSeatSelect('3루 자유석', 'item-2', 2)">
								<div class="grade-name-group">
									<span class="color-square sq-red"></span> <span>3루 자유석</span>
								</div>
								<span class="seat-count-text">${remainingSeat.thirdBaseSeat }</span>
							</div>
							<div class="seats-list-item-img" id="item-3" onclick="mockSeatSelect('홈 자유석', 'item-3', 3)">
								<div class="grade-name-group">
									<span class="color-square sq-yellow"></span> <span>홈 자유석</span>
								</div>
								<span class="seat-count-text">${remainingSeat.homeBaseSeat }</span>
							</div>
							<div class="seats-list-item-img" id="item-4" onclick="mockSeatSelect('자유 외야석', 'item-4', 4)">
								<div class="grade-name-group">
									<span class="color-square sq-green"></span> <span>자유 외야석</span>
								</div>
								<span class="seat-count-text">${remainingSeat.outFieldSeat }</span>
							</div>
						</div>
	
						<a href="#" class="info-guide-link"> <span class="info-icon-circle">i</span> 좌석선점 및 자동배정 안내</a>
						<button type="button" class="btn-next-large-img" onclick="transitionToStep3()">다음단계</button>
					</div>
				</div>
	
				<div id="view-step-3" class="view-section hidden">
					<div class="ticket-selection-box">
						<div class="selection-title-main">티켓종류, 할인, 매수선택</div>
	
						<div class="selection-alert-wrapper">
							<div class="selection-alert-bar">
								<span id="selected-seat-alert"><strong id="dynamic-seat-name">1루 자유석</strong>을 <strong id="dynamic-seat-qty">0매</strong> 선택하셨습니다.</span>
							</div>
						</div>
	
						<div class="ticket-table-container">
							<table class="ticket-table-modern">
								<colgroup>
									<col style="width: 15%;">
									<col style="width: 45%;">
									<col style="width: 25%;">
									<col style="width: 15%;">
								</colgroup>
								<tbody>
									<tr>
										<td class="cat-cell" rowspan="3">일반 권종</td>
										<td class="name-cell">성인</td>
										<td class="price-cell">${seatPrice.adultSeatPrice }원</td>
										<td class="select-cell">
											<select class="qty-select ticket-qty" data-type="성인" data-price="${seatPrice.adultSeatPrice }" onchange="calculateInvoice()">
												<option value="0">0</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="name-cell">청소년</td>
										<td class="price-cell">${seatPrice.youthSeatPrice }원</td>
										<td class="select-cell">
											<select class="qty-select ticket-qty" data-type="청소년" data-price="${seatPrice.youthSeatPrice }" onchange="calculateInvoice()">
												<option value="0">0</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="name-cell">어린이 <span class="q-icon">?</span></td>
										<td class="price-cell">${seatPrice.childSeatPrice }원</td>
										<td class="select-cell">
											<select class="qty-select ticket-qty" data-type="어린이" data-price="${seatPrice.childSeatPrice }" onchange="calculateInvoice()">
												<option value="0">0</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
											</select>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
	
						<div class="bottom-notice-area">
							<h4>안내사항</h4>
							<p>※ 티켓 부정거래 적발시 별도의 사전 통보나 소명 절차 없이 강제 예매 취소 처리가 가능하며 비정상적인 접근 시 차단됩니다.</p>
						</div>
					</div>
	
					<div class="side-panel-step3">
						<div class="side-match-info">
							<div class="match-vs-logos">
								<img src="${pageContext.request.contextPath}/images/team_logo/${gameInfo.teamHomeImg}" alt="HOME" class="team-logo-placeholder">
								<span class="vs-label">VS</span>
								<img src="${pageContext.request.contextPath}/images/team_logo/${gameInfo.teamOtherImg}" alt="AWAY" class="team-logo-placeholder">
							</div>
							<div class="match-details-text">
								<div class="match-title">
									${gameInfo.teamHomeName} <span class="vs-gray">vs</span> <span class="home-badge">H</span> ${gameInfo.teamOtherName}
								</div>
								<div class="stadium-name">구장 코드: ${gameInfo.stadiumCode} 구장</div>
								<div class="match-date">${gameInfo.gameDate} ${gameInfo.gameStartTime}</div>
							</div>
						</div>
	
						<div class="booking-summary-box-img">
							<div class="box-label">예매정보</div>
							<div class="seat-info-detail">
								<span class="seat-grade-txt" id="summary-seat-name">선택 안 됨</span>
							</div>
						</div>
	
						<table class="payment-summary-table">
							<tr>
								<td>티켓금액</td>
								<td class="price-val" id="invoice-ticket">0원</td>
							</tr>
							<tr>
								<td>예매수수료</td>
								<td class="price-val" id="invoice-fee">0원</td>
							</tr>
							<tr>
								<td>배송료</td>
								<td class="price-val">0원</td>
							</tr>
							<tr class="total-row">
								<td>총결제</td>
								<td class="total-val" id="invoice-total">0원</td>
							</tr>
						</table>
	
						<div class="side-btn-group">
							<button type="button" class="btn-step-prev" onclick="transitionToStep2()">이전단계</button>
							<button type="button" class="btn-step-next" onclick="transitionToStep4()">다음단계</button>
						</div>
					</div>
				</div>
	
				<div id="view-step-4" class="view-section hidden">
					<div class="step4-content-scroll">
						<h4 class="step4-section-title">수령방법</h4>
						<table class="step4-table">
							<tbody>
								<tr>
									<td class="step4-label-cell">티켓</td>
									<td style="font-weight: bold;">현장수령</td>
								</tr>
							</tbody>
						</table>
	
						<h4 class="step4-section-title">주문자 정보</h4>
						<table class="step4-table">
							<tbody>
								<tr>
									<td class="step4-label-cell">이름 *</td>
									<td><input type="text" value="${sessionScope.loginMember.name}" class="step4-input" style="font-weight: bold;" readonly></td>
									<td class="step4-label-cell">휴대폰 번호 *</td>
									<td><input type="text" value="${sessionScope.loginMember.phone}" class="step4-input" style="font-weight: bold;" readonly></td>
								</tr>
								<tr>
									<td class="step4-label-cell">이메일</td>
									<td colspan="3"><input type="text" value="${sessionScope.loginMember.email}" class="step4-input-full" readonly></td>
								</tr>
							</tbody>
						</table>
	
						<h4 class="step4-section-title">예매자 확인</h4>
						<div class="consent-list-container">
							<div class="consent-item">
								<div class="consent-left">
									<input type="checkbox" id="check-consent-1"> <span>티켓 예매 확인 및 개인정보 수집·이용 동의 (필수)</span>
								</div>
								<span class="link-detail" onclick="openDetailModal(1)">[상세보기]</span>
							</div>
							<div class="consent-item">
								<div class="consent-left">
									<input type="checkbox" id="check-consent-2"> <span>(선택) 예매 안내 메일 발송을 위한 이메일 수집 동의</span>
								</div>
								<span class="link-detail" onclick="openDetailModal(2)">[상세보기]</span>
							</div>
							<div class="consent-item">
								<div class="consent-left">
									<input type="checkbox" id="check-consent-3"> <span>개인정보 제 3자 제공에 동의합니다. (필수)</span>
								</div>
								<span class="link-detail" onclick="openDetailModal(3)">[상세보기]</span>
							</div>
							<div class="consent-item">
								<div class="consent-left">
									<input type="checkbox" id="check-consent-4"> <span>KBO리그 SAFE캠페인에 동의합니다. (필수)</span>
								</div>
								<span class="link-detail" onclick="openDetailModal(4)">[상세보기]</span>
							</div>
							<div class="consent-item">
								<div class="consent-left">
									<input type="checkbox" id="check-consent-5"> <span>암표매매 행위에 따른 제재사항 동의 (필수)</span>
								</div>
								<span class="link-detail" onclick="openDetailModal(5)">[상세보기]</span>
							</div>
						</div>
					</div>
	
					<div class="side-panel-step3">
						<div class="side-match-info">
							<div class="match-vs-logos">
								<img src="${pageContext.request.contextPath}/images/team_logo/${gameInfo.teamHomeImg}" alt="HOME" class="team-logo-placeholder">
								<span class="vs-label">VS</span>
								<img src="${pageContext.request.contextPath}/images/team_logo/${gameInfo.teamOtherImg}" alt="AWAY" class="team-logo-placeholder">
							</div>
							<div class="match-details-text">
								<div class="match-title">
									${gameInfo.teamHomeName} <span class="vs-gray">vs</span> <span class="home-badge">H</span> ${gameInfo.teamOtherName}
								</div>
								<div class="stadium-name">구장 코드: ${gameInfo.stadiumCode}</div>
								<div class="match-date">${gameInfo.gameDate}</div>
							</div>
						</div>
	
						<div class="booking-summary-box-img">
							<div class="box-label">예매정보</div>
							<div class="seat-info-detail">
								<span class="seat-grade-txt" id="summary-seat-name-step4">선택 안 됨</span>
							</div>
						</div>
	
						<table class="payment-summary-table">
							<tbody>
								<tr>
									<td>티켓금액</td>
									<td class="price-val" id="invoice-ticket-step4">0원</td>
								</tr>
								<tr class="total-row">
									<td>총결제</td>
									<td class="total-val" id="invoice-total-step4">0원</td>
								</tr>
							</tbody>
						</table>
	
						<div class="payment-method-selector">
							<div class="box-label">결제수단 선택</div>
							<label class="toss-radio-label"> <input type="radio" name="payment-method-group" checked> toss 결제</label>
						</div>
	
						<div class="side-btn-group">
							<button type="button" class="btn-step-prev" onclick="transitionToStep3FromStep4()">이전단계</button>
							<button type="button" class="btn-pay-red" onclick="triggerFinalPayment()">결제하기</button>
						</div>
					</div>
				</div>
	
			</main>
		</form> </div>

	<div id="detail-modal" class="modal-overlay hidden">
		<div class="modal-card">
			<div class="modal-header">
				<span id="modal-title">동의 내용 확인</span>
				<button type="button" class="modal-close-btn" onclick="closeDetailModal()">×</button>
			</div>
			<div class="modal-body" id="modal-text" style="white-space: pre-wrap; text-align: left; font-size: 14px; line-height: 1.6;">내용이 표시됩니다.</div>
			<div class="modal-footer">
				<button type="button" class="btn-action btn-prev" onclick="closeDetailModal()">닫기</button>
			</div>
		</div>
	</div>

	<script>
	    let currentCaptchaCode = "";
	    let selectActive = false; 
	    let selectedSeatGlobalName = "";
	
	    function generateCaptchaValue() {
	        const chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
	        let result = "";
	        for (let i = 0; i < 6; i++) {
	            result += chars.charAt(Math.floor(Math.random() * chars.length));
	        }
	        currentCaptchaCode = result;
	        document.getElementById('captcha-display-text').innerText = result;
	    }
	
	    function verifyCaptcha() {
	        const userInput = document.getElementById('captcha-user-input').value;
	        if (userInput.toUpperCase() === currentCaptchaCode) {
	            alert("인증에 성공했습니다.");
	            document.getElementById('captcha-overlay').classList.add('hidden');
	        } else {
	            alert("보안문자가 일치하지 않습니다. (대문자로 입력해주세요.)");
	            generateCaptchaValue();
	        }
	    }
	
	    function goToTeamSelection() {
	        location.href = "${pageContext.request.contextPath}/gameScheduleList"; 
	    }
		
	    function mockSeatSelect(name, id, seatCode) {
	        document.querySelectorAll('.seats-list-item-img').forEach(el => el.classList.remove('selected'));
	        const target = document.getElementById(id);
	        if(target) target.classList.add('selected');
	        
	        selectedSeatGlobalName = name;
	        document.getElementById('dynamic-seat-name').innerText = name;
	        document.getElementById('summary-seat-name').innerText = name;
	        document.getElementById('summary-seat-name-step4').innerText = name;
	        
	        document.getElementById('hiddenStadiumSeatCode').value = seatCode;
	        selectActive = true;
	    }
	
	    function transitionToStep3() {
	        if (!selectActive) {
	            alert("좌석 등급을 먼저 선택해주세요.");
	            return;
	        }
	        document.getElementById('view-step-2').classList.add('hidden');
	        document.getElementById('view-step-3').classList.remove('hidden');
	        document.getElementById('step-nav-2').classList.remove('active');
	        document.getElementById('step-nav-3').classList.add('active');
	    }
	
	    function transitionToStep2() {
	        document.getElementById('step-nav-3').classList.remove('active');
	        document.getElementById('step-nav-2').classList.add('active');
	        document.getElementById('view-step-3').classList.add('hidden');
	        document.getElementById('view-step-2').classList.remove('hidden');
	    }
	
	    let calculatedTicketPrice = 0;
	    let calculatedFee = 0;
	    let calculatedTotal = 0;
	
	    function calculateInvoice() {
	        const qtyDropdowns = document.querySelectorAll('.ticket-qty');
	        let sumTicketPrice = 0;
	        let sumQty = 0;
	        let lastSelectedType = "성인";
	
	        qtyDropdowns.forEach(dropdown => {
	            const qty = parseInt(dropdown.value);
	            const pricePerOne = parseInt(dropdown.getAttribute('data-price'));
	            
	            if (qty > 0) {
	                sumTicketPrice += (qty * pricePerOne);
	                sumQty += qty;
	                lastSelectedType = dropdown.getAttribute('data-type');
	            }
	        });
	
	        calculatedTicketPrice = sumTicketPrice;
	        calculatedFee = sumQty > 0 ? (sumQty * 1000) : 0;
	        calculatedTotal = calculatedTicketPrice + calculatedFee;
	
	        document.getElementById('dynamic-seat-qty').innerText = sumQty + "매";
	        document.getElementById('invoice-ticket').innerText = calculatedTicketPrice.toLocaleString() + "원";
	        document.getElementById('invoice-fee').innerText = calculatedFee.toLocaleString() + "원";
	        document.getElementById('invoice-total').innerText = calculatedTotal.toLocaleString() + "원";
	        
	        document.getElementById('hiddenReservationQuantity').value = sumQty;
	        document.getElementById('hiddenReservationType').value = lastSelectedType;
	        document.getElementById('hiddenTotalPrice').value = calculatedTicketPrice;
	        document.getElementById('hiddenDiscountPrice').value = 0;
	        document.getElementById('hiddenPayPrice').value = calculatedTotal;
	    }
	
	    function transitionToStep4() {
	        if (calculatedTotal === 0) {
	            alert("티켓 매수를 1개 이상 선택해주세요.");
	            return;
	        }
	        document.getElementById('step-nav-3').classList.remove('active');
	        document.getElementById('step-nav-4').classList.add('active');
	        document.getElementById('invoice-ticket-step4').innerText = calculatedTicketPrice.toLocaleString() + "원";
	        document.getElementById('invoice-total-step4').innerText = calculatedTotal.toLocaleString() + "원";
	        document.getElementById('view-step-3').classList.add('hidden');
	        document.getElementById('view-step-4').classList.remove('hidden');
	    }
	
	    function transitionToStep3FromStep4() {
	        document.getElementById('step-nav-4').classList.remove('active');
	        document.getElementById('step-nav-3').classList.add('active');
	        document.getElementById('view-step-4').classList.add('hidden');
	        document.getElementById('view-step-3').classList.remove('hidden');
	    }
	
	    function triggerFinalPayment() {
	        if (!document.getElementById('check-consent-1').checked || 
	            !document.getElementById('check-consent-3').checked || 
	            !document.getElementById('check-consent-4').checked || 
	            !document.getElementById('check-consent-5').checked) {
	            alert("필수동의 항목에 동의하셔야 합니다.");
	            return;
	        }

	        const clientKey = 'test_ck_ma60RZblrq5wx4j5Pa9ZrwzYWBn1'; 
	        const tossPayments = TossPayments(clientKey);
	        
	        const successUrl = window.location.origin + "${pageContext.request.contextPath}/reservation"
	        + "?mode=success"
	        + "&stadiumSeatCode=" + document.getElementById('hiddenStadiumSeatCode').value
	        + "&reservationType=" + encodeURIComponent(document.getElementById('hiddenReservationType').value)
	        + "&reservationQuantity=" + document.getElementById('hiddenReservationQuantity').value
	        + "&totalPrice=" + document.getElementById('hiddenTotalPrice').value
	        + "&discountPrice=" + document.getElementById('hiddenDiscountPrice').value // 이 줄 추가!
	        + "&payPrice=" + document.getElementById('hiddenPayPrice').value;

	        tossPayments.requestPayment('카드', {
	            amount: calculatedTotal,
	            orderId: "ORDER-" + new Date().getTime(),
	            orderName: "야구 경기 티켓 예매",
	            customerName: "${sessionScope.loginMember.name}", 
	            successUrl: successUrl, // 위에서 만든 긴 URL
	            failUrl: window.location.origin + "${pageContext.request.contextPath}/reservationPage/reservationFail.jsp",
	        });
	    }
	
	    // 🌟 [상세보기] 팝업 약관 텍스트 데이터 기입 완료
	    const consentData = {
	        1: {
	            title: "(필수) 개인정보 수집/이용 안내",
	            text: "티켓 예매에 의하여 다음과 같은 개인정보를 필수적으로 수집·이용하고자 합니다.\n\n" +
	                  "1. 개인정보 항목\n" +
	                  " - 휴대폰 번호, 이메일, 예매자명, 수령자 주소, 수령자 휴대폰번호, 배송자 전화번호, 배송자 주소, 배송 상세주소\n\n" +
	                  "2. 개인정보 수집이용 목적\n" +
	                  " - 휴대폰 번호: 예매 처리 및 예매 문자 발송\n" +
	                  " - 이메일: 예매정보 전송 및 취소 내역 전송\n" +
	                  " - 배송 수령 정보: 예매티켓 배송\n\n" +
	                  "3. 보유/이용 기간\n" +
	                  " - 예매일로부터 5년 후 파기"
	        },
	        2: {
	            title: "(선택) 개인정보 수집/이용 동의",
	            text: "1. 개인정보 항목\n" +
	                  " - 이메일\n\n" +
	                  "2. 개인정보 수집/이용 목적\n" +
	                  " - 마케팅 활용 안내 메일 발송 및 행사 정보 전송\n\n" +
	                  "3. 보유/이용 기간\n" +
	                  " - 회원탈퇴 및 수집 거부 시 즉시 파기\n\n" +
	                  "4. 동의 거부권에 대한 고지\n" +
	                  " - 해당 개인정보 수집/이용 동의를 거부할 권리가 있으며 거부하더라도 예매 자체의 영향은 주지 않습니다."
	        },
	        3: {
	            title: "개인정보 제3자 정보제공",
	            text: "예매티켓 대행처리 목적으로 기재된 개인정보를 가맹점 및 제휴사에 제공하고자 합니다.\n\n" +
	                  "1. 개인정보를 제공받는 자\n" +
	                  " - 홈 구단 및 스포츠 운영 연맹\n\n" +
	                  "2. 제공하는 개인정보 항목\n" +
	                  " - 이름, 아이디, 휴대폰번호, 이메일주소, 주문정보\n\n" +
	                  "3. 개인정보를 제공받는 자의 이용목적\n" +
	                  " - 구단 마케팅 활용, 시즌권 회원 가입여부 확인 및 고객 응대 관리\n\n" +
	                  "4. 보유 및 이용기간\n" +
	                  " - 이용 목적이 달성되거나 구단 사이의 계약 만료 후 지체없이 파기"
	        },
	        4: {
	            title: "KBO리그 SAFE 캠페인 동의",
	            text: "<SAFE 캠페인의 의의>\n" +
	                  " - SAFE 캠페인은 더 안전하고 쾌적한 야구장 관람 문화를 만들기 위해 다양한 안전관람수칙을 준수하는 야구 팬들과 구단이 함께 만드는 수칙입니다.\n\n" +
	                  "1. 반입 금지 물품 규정\n" +
	                  " - 1인당 가방 1개 및 쇼핑백 1개에 한하여 반입 가능합니다.\n" +
	                  " - 캔 및 유리병 종류의 주류, 음료 등은 관람객의 안전을 저해할 수 있어 경기장 내 일체 반입이 금지됩니다."
	        },
	        5: {
	            title: "프로스포츠 암표매매 행위 제재 동의",
	            text: "티켓 불법 재판매 및 암표 거래 시 형사 처벌 대상이 될 수 있으며, 암표 거래 연루 티켓은 구단의 재량에 의해 사전 통보 없이 일방적으로 취소 처리 및 아이디 영구 이용 정지 조치가 취해질 수 있음에 동의합니다."
	        }
	    };
	
	    function openDetailModal(id) {
	        const data = consentData[id];
	        if (data) {
	            document.getElementById('modal-title').innerText = data.title;
	            document.getElementById('modal-text').innerText = data.text;
	            document.getElementById('detail-modal').classList.remove('hidden');
	        }
	    }
	
	    function closeDetailModal() {
	        document.getElementById('detail-modal').classList.add('hidden');
	    }
	
	    window.addEventListener("load", () => {
	        // 페이지가 완전히 켜진 후 보안문자 강제 생성!
	        generateCaptchaValue();
	        
	        let totalSeconds = 600; 
	        const timerDisplay = document.getElementById("countdown");
	        if(timerDisplay) {
	            setInterval(() => {
	                let minutes = Math.floor(totalSeconds / 60);
	                let seconds = totalSeconds % 60;
	                timerDisplay.textContent = (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
	                if (totalSeconds > 0) totalSeconds--;
	            }, 1000);
	        }
	    });									
	</script>
</body>
</html>
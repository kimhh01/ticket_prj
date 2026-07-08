<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>BallPick예매</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/kr/user/reservation/reservation.css">
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
						BallPick⚾
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

		<!-- [수정] 첫 번째 좌석 정보를 변수(defaultSeat)로 지정해 둡니다 -->
		<c:set var="defaultSeat" value="${seatList[0]}" />

		<form id="reservationForm" action="${pageContext.request.contextPath}/reservationServlet" method="POST">
			
			<!-- [수정] 기본 선택된 좌석 코드를 첫 번째 좌석 ID로 EL 매핑합니다 -->
			<input type="hidden" name="stadiumSeatCode" id="hiddenStadiumSeatCode" value="${defaultSeat.stadiumSeatCode}"> 
			<input type="hidden" name="reservationType" id="hiddenReservationType" value="성인">
			<input type="hidden" name="reservationQuantity" id="hiddenReservationQuantity" value="0">
			<input type="hidden" name="totalPrice" id="hiddenTotalPrice" value="0">
			<input type="hidden" name="discountPrice" id="hiddenDiscountPrice" value="0">
			<input type="hidden" name="payPrice" id="hiddenPayPrice" value="0">
			<input type="hidden" name="gameScheduleCode" id="hiddenGameScheduleCode" value="${gameInfo.gameScheduleCode}">

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
							<button type="button" class="btn-refresh-img" onclick="refreshSeat()">새로고침</button>
						</div>
	
						<div class="seats-scroll-box-img">
							<div class="seats-list-item-img" style="background: #f7f7f7; font-weight: bold;">
								<span>전체</span>
							</div>
							
							<c:forEach var="seat" items="${seatList}" varStatus="status">
								<!-- [수정] 첫 번째 좌석 목록에 selected 클래스를 기본 부여합니다 -->
								<div class="seats-list-item-img ${status.first ? 'selected' : ''}" id="item-${status.index+1}" 
									 data-adult="${seat.adultSeatPrice}"
									 data-youth="${seat.youthSeatPrice}"
									 data-child="${seat.childSeatPrice}"
									 onclick="mockSeatSelect('${seat.seatName}','item-${status.index+1}',${seat.stadiumSeatCode}, this)">
						
						            <div class="grade-name-group">
						                <c:choose>
										    <c:when test="${seat.seatName == '1루 자유석'}">
										        <span class="color-square sq-blue"></span>
										    </c:when>
										
										    <c:when test="${seat.seatName == '3루 자유석'}">
										        <span class="color-square sq-red"></span>
										    </c:when>
										
										    <c:when test="${seat.seatName == '홈 자유석'}">
										        <span class="color-square sq-yellow"></span>
										    </c:when>
										
										    <c:when test="${seat.seatName == '외야 자유석'}">
										        <span class="color-square sq-green"></span>
										    </c:when>
										
										    <c:otherwise>
										        <span class="color-square sq-green"></span>
										    </c:otherwise>
										</c:choose>
						                <span>${seat.seatName}</span>
						            </div>
						
						            <span class="seat-count-text"
									      id="remain-${seat.stadiumSeatCode}">
									    ${seat.remainSeatNum}
									</span>
						
						        </div>
							</c:forEach>
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
								<!-- [수정] 기본 선택된 좌석명(defaultSeat.seatName)을 EL로 출력합니다 -->
								<span id="selected-seat-alert"><strong id="dynamic-seat-name">${defaultSeat.seatName}</strong>을 <strong id="dynamic-seat-qty">0매</strong> 선택하셨습니다.</span>
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
										<!-- [수정] 기본 성인 요금을 DB 데이터(EL)로 바인딩하고 data-price 기본 속성을 부여합니다 -->
										<td class="price-cell" id="price-adult">${defaultSeat.adultSeatPrice}원</td>
										<td class="select-cell">
											<select class="qty-select ticket-qty" id="qty-adult" data-type="성인" data-price="${defaultSeat.adultSeatPrice}" onchange="calculateInvoice()">
												<option value="0">0</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="name-cell">청소년</td>
										<!-- [수정] 기본 청소년 요금을 DB 데이터(EL)로 바인딩하고 data-price 기본 속성을 부여합니다 -->
										<td class="price-cell" id="price-youth">${defaultSeat.youthSeatPrice}원</td>
										<td class="select-cell">
											<select class="qty-select ticket-qty" id="qty-youth" data-type="청소년" data-price="${defaultSeat.youthSeatPrice}" onchange="calculateInvoice()">
												<option value="0">0</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="name-cell">어린이</td>
										<!-- [수정] 기본 어린이 요금을 DB 데이터(EL)로 바인딩하고 data-price 기본 속성을 부여합니다 -->
										<td class="price-cell" id="price-child">${defaultSeat.childSeatPrice}원</td>
										<td class="select-cell">
											<select class="qty-select ticket-qty" id="qty-child" data-type="어린이" data-price="${defaultSeat.childSeatPrice}" onchange="calculateInvoice()">
												<option value="0">0</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
											</select>
										</td>
									</tr>
									<tr class="discount-row">
								    <td class="cat-cell">할인</td>
								
								    <td class="discount-cell" colspan="3">
								        <div class="coupon-area" style="margin-left: auto;">
								
								            <c:choose>
								                <c:when test="${not empty couponList}">
								                    <select id="couponSelect" class="qty-select" style="width: 180px;" onchange="changeCoupon()">
								                        <option value="0">쿠폰 선택</option>
								
								                        <c:forEach var="coupon" items="${couponList}">
								                            <option
								                                value="${coupon.couponCode}"
								                                data-rate="${coupon.couponDiscountRate}">
								                                ${coupon.couponDiscountRate}% 할인
								                            </option>
								                        </c:forEach>
								                    </select>
								                </c:when>
								
								                <c:otherwise>
								                    <span class="coupon-empty-text">사용 가능한 쿠폰이 없습니다.</span>
								                </c:otherwise>
								
								            </c:choose>
								
								        </div>
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
								<div class="match-date">${gameInfo.gameDate} ${gameInfo.gameStartTime}</div>
							</div>
						</div>
	
						<div class="booking-summary-box-img">
							<div class="box-label">예매정보</div>
							<div class="seat-info-detail">
								<!-- [수정] 기본 선택된 좌석 등급명을 EL로 세팅합니다 -->
								<span class="seat-grade-txt" id="summary-seat-name">${defaultSeat.seatName}</span>
							</div>
						</div>
	
						<table class="payment-summary-table">
							<tr>
								<td>티켓금액</td>
								<td class="price-val" id="invoice-ticket">0원</td>
							</tr>
							<tr>
								<td>할인금액</td>
								<td class="price-val" id="invoice-discount">0원</td>
							</tr>
							<tr>
								<td>예매수수료</td>
								<td class="price-val" id="invoice-fee">0원</td>
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
									<td><input type="text" value="${memberInfo.memberName}" class="step4-input" style="font-weight: bold;" readonly></td>
									<td class="step4-label-cell">휴대폰 번호 *</td>
									<td><input type="text" value="${memberInfo.memberPhone}" class="step4-input" style="font-weight: bold;" readonly="readonly"></td>
								</tr>
								<tr>
									<td class="step4-label-cell">이메일</td>
									<td colspan="3"><input type="text" value="${memberInfo.memberEmail}" class="step4-input-full" readonly="readonly"></td>
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
									<input type="checkbox" id="check-consent-2"> <span>개인정보 제 3자 제공에 동의합니다. (필수)</span>
								</div>
								<span class="link-detail" onclick="openDetailModal(2)">[상세보기]</span>
							</div>
							<div class="consent-item">
								<div class="consent-left">
									<input type="checkbox" id="check-consent-3"> <span>KBO리그 SAFE캠페인에 동의합니다. (필수)</span>
								</div>
								<span class="link-detail" onclick="openDetailModal(3)">[상세보기]</span>
							</div>
							<div class="consent-item">
								<div class="consent-left">
									<input type="checkbox" id="check-consent-4"> <span>암표매매 행위에 따른 제재사항 동의 (필수)</span>
								</div>
								<span class="link-detail" onclick="openDetailModal(4)">[상세보기]</span>
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
								<div class="match-date">${gameInfo.gameDate}</div>
							</div>
						</div>
	
						<div class="booking-summary-box-img">
							<div class="box-label">예매정보</div>
							<div class="seat-info-detail">
								<!-- [수정] 기본 선택된 좌석 등급명을 EL로 세팅합니다 -->
								<span class="seat-grade-txt" id="summary-seat-name-step4">${defaultSeat.seatName}</span>
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
	    // [수정] 기본적으로 첫 번째 좌석 등급이 활성화된 상태이므로 초기 상태 값들을 그에 맞게 정의합니다
	    let selectActive = true; 
	    let selectedSeatGlobalName = "${defaultSeat.seatName}";
	
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
	    	sessionStorage.removeItem("reservationEndTime");
	        window.close();
	    }
		
	    function mockSeatSelect(name, id, seatCode, element) {
	        document.querySelectorAll('.seats-list-item-img').forEach(el => el.classList.remove('selected'));
	        const target = document.getElementById(id);
	        if(target) target.classList.add('selected');
	        
	        selectedSeatGlobalName = name;
	        document.getElementById('dynamic-seat-name').innerText = name;
	        document.getElementById('summary-seat-name').innerText = name;
	        document.getElementById('summary-seat-name-step4').innerText = name;
	        
	        document.getElementById('hiddenStadiumSeatCode').value = seatCode;
	        selectActive = true;

	        // 가격 정보 동적 할당
	        const adultPrice = parseInt(element.getAttribute('data-adult')) || 0;
	        const youthPrice = parseInt(element.getAttribute('data-youth')) || 0;
	        const childPrice = parseInt(element.getAttribute('data-child')) || 0;

	        // UI 텍스트 및 select 옵션 계산용 data-price 반영
	        document.getElementById('price-adult').innerText = adultPrice.toLocaleString() + "원";
	        document.getElementById('price-youth').innerText = youthPrice.toLocaleString() + "원";
	        document.getElementById('price-child').innerText = childPrice.toLocaleString() + "원";

	        const qtyAdult = document.getElementById('qty-adult');
	        const qtyYouth = document.getElementById('qty-youth');
	        const qtyChild = document.getElementById('qty-child');

	        qtyAdult.setAttribute('data-price', adultPrice);
	        qtyYouth.setAttribute('data-price', youthPrice);
	        qtyChild.setAttribute('data-price', childPrice);

	        // 새로운 등급 선택 시 선택 매수를 초기화하여 버그 방지
	        document.querySelectorAll('.ticket-qty').forEach(el => el.value = "0");
	        calculateInvoice();
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

	    let discountRate = 0;
	    let discountAmount = 0;
	    let couponCode = "";
	    
	    function calculateInvoice() {
	        // 각 등급별 선택 수량
	        const qtyAdult = parseInt(document.getElementById('qty-adult').value) || 0;
	        const qtyYouth = parseInt(document.getElementById('qty-youth').value) || 0;
	        const qtyChild = parseInt(document.getElementById('qty-child').value) || 0;

	        // 실시간 로드된 각 요금 단가
	        const priceAdult = parseInt(document.getElementById('qty-adult').getAttribute('data-price')) || 0;
	        const priceYouth = parseInt(document.getElementById('qty-youth').getAttribute('data-price')) || 0;
	        const priceChild = parseInt(document.getElementById('qty-child').getAttribute('data-price')) || 0;

	        const sumQty = qtyAdult + qtyYouth + qtyChild;
	        const sumTicketPrice = (qtyAdult * priceAdult) + (qtyYouth * priceYouth) + (qtyChild * priceChild);
	
	        calculatedTicketPrice = sumTicketPrice;
	        discountAmount = calculatedTicketPrice * discountRate / 100;
	        calculatedFee = sumQty > 0 ? (sumQty * 1000) : 0;
	        calculatedTotal =
	            calculatedTicketPrice
	            - discountAmount
	            + calculatedFee;
	
	        document.getElementById('dynamic-seat-qty').innerText = sumQty + "매";
	        document.getElementById('invoice-discount').innerText = discountAmount.toLocaleString() + "원";
	        document.getElementById('invoice-ticket').innerText = calculatedTicketPrice.toLocaleString() + "원";
	        document.getElementById('invoice-fee').innerText = calculatedFee.toLocaleString() + "원";
	        document.getElementById('invoice-total').innerText = calculatedTotal.toLocaleString() + "원";
	        
	        document.getElementById('hiddenReservationQuantity').value = sumQty;
	        document.getElementById('hiddenTotalPrice').value = calculatedTicketPrice;
	        document.getElementById('hiddenDiscountPrice').value = discountAmount;
	        document.getElementById('hiddenPayPrice').value = calculatedTotal;
	    }
	    
	    //할인 적용 함수
		
		function changeCoupon() {
		
		    const select = document.getElementById("couponSelect");
		    const option = select.options[select.selectedIndex];
		
		    couponCode = option.value;
		    discountRate = parseInt(option.dataset.rate || 0);
		
		    calculateInvoice();   // 금액 다시 계산
		}
	
	    function transitionToStep4() {
	        if (calculatedTotal === 0) {
	            alert("티켓 매수를 1개 이상 선택해주세요.");
	            return;
	        }
	        
	        if (parseInt(document.getElementById("hiddenReservationQuantity").value) > 4) {
	            alert("티켓은 최대 4매까지만 구매 가능합니다.");
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
	            !document.getElementById('check-consent-2').checked || 
	            !document.getElementById('check-consent-3').checked || 
	            !document.getElementById('check-consent-4').checked) {
	            alert("필수동의 항목에 동의하셔야 합니다.");
	            return;
	        }

	        const clientKey = 'test_ck_ma60RZblrq5wx4j5Pa9ZrwzYWBn1'; 
	        const tossPayments = TossPayments(clientKey);
	        const couponSelect = document.getElementById("couponSelect");

	        let selectedCoupon = "";
	        if (couponSelect && couponSelect.value !== "0") {
	            selectedCoupon = couponSelect.value;
	        }
	        
	        // 각각의 선택된 티켓 수량을 수집합니다
	        const adultQty = document.getElementById('qty-adult').value;
	        const youthQty = document.getElementById('qty-youth').value;
	        const childQty = document.getElementById('qty-child').value;
	        
	        const successUrl =
	            window.location.origin +
	            "${pageContext.request.contextPath}/reservation"
	            + "?mode=success"
	            + "&memberCode=${sessionScope.loginMember.memberCode}"
	            + "&stadiumSeatCode=" + document.getElementById("hiddenStadiumSeatCode").value
	            + "&adultQty=" + adultQty
	            + "&youthQty=" + youthQty
	            + "&childQty=" + childQty
	            + "&gameScheduleCode=" + document.getElementById("hiddenGameScheduleCode").value
	            + "&couponCode=" + selectedCoupon
	            + "&discountRate=" + discountRate;

	        tossPayments.requestPayment('카드', {
	            amount: calculatedTotal,
	            orderId: "ORDER-" + new Date().getTime(),
	            orderName: "야구 경기 티켓 예매",
	            customerName: "${memberInfo.memberName}", 
	            successUrl: successUrl,
	            failUrl: window.location.origin + "${pageContext.request.contextPath}/reservation/reservationFail.jsp"
	            								+ "?teamCode=${gameInfo.teamHomeCode}",
	        });
	    }
	
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
	        3: {
	            title: "KBO리그 SAFE 캠페인 동의",
	            text: "<SAFE 캠페인의 의의>\n" +
	                  " - SAFE 캠페인은 더 안전하고 쾌적한 야구장 관람 문화를 만들기 위해 다양한 안전관람수칙을 준수하는 야구 팬들과 구단이 함께 만드는 수칙입니다.\n\n" +
	                  "1. 반입 금지 물품 규정\n" +
	                  " - 1인당 가방 1개 및 쇼핑백 1개에 한하여 반입 가능합니다.\n" +
	                  " - 캔 및 유리병 종류의 주류, 음료 등은 관람객의 안전을 저해할 수 있어 경기장 내 일체 반입이 금지됩니다."
	        },
	        4: {
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
	    //예매 시간
	    window.addEventListener("load", () => {

	        generateCaptchaValue();

	        // 저장된 종료시간 확인
	        let endTime = sessionStorage.getItem("reservationEndTime");

	        // 새 창을 처음 열었을 때만 생성
	        if (!endTime) {
	            endTime = Date.now() + (10 * 60 * 1000);
	            sessionStorage.setItem("reservationEndTime", endTime);
	        }

	        endTime = Number(endTime);

	        const timerDisplay = document.getElementById("countdown");

	        const timer = setInterval(() => {

	            const remain = Math.floor((endTime - Date.now()) / 1000);

	            if (remain <= 0) {

	                clearInterval(timer);

	                timerDisplay.innerText = "00:00";

	                sessionStorage.removeItem("reservationEndTime");

	                alert("예매 시간이 종료되었습니다.");
	                window.close();
	                return;
	            }

	            const minutes = Math.floor(remain / 60);
	            const seconds = remain % 60;

	            timerDisplay.innerText =
	                String(minutes).padStart(2, "0") + ":" +
	                String(seconds).padStart(2, "0");

	        }, 1000);

	    });
	    
	    //새로고침 버튼 클릭시 잔여좌석 재조회
		function refreshSeat() {

		    const gameScheduleCode =
		        document.getElementById("hiddenGameScheduleCode").value;
		
		    fetch("${pageContext.request.contextPath}/seatRefresh?gameScheduleCode=" + gameScheduleCode)
		        .then(response => response.json())
		        .then(data => {
		
		            data.forEach(seat => {
		
		                const remain = document.getElementById("remain-" + seat.stadiumSeatCode);
		
		                if (remain) {
		                    remain.innerText = seat.remainSeatNum;
		                }
		
		            });
		
		        })
		        .catch(err => {
		            console.error(err);
		            alert("좌석 정보를 불러오지 못했습니다.");
		        });
		}
	</script>
</body>
</html>
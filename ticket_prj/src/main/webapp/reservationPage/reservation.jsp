<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    // [동적 서버 데이터 시뮬레이션]
    // 2단계 경기 정보
    String step2Away = "SSG Landers";
    String step2Home = "LG TWINS";
    
    // 3, 4단계 경기 및 좌석 예매 정보 (업로드해주신 이미지 데이터 기준 바인딩)
    String step3Away = "롯데";
    String step3Home = "KIA";
    String stadiumName = "KIA 챔피언스필드";
    String matchDate = "2026.06.02(화) 18:30";
    String seatGrade = "1루 자유석";
    String seatDetail = ""; // 자유석이므로 공백 처리
    
    // 4단계 회원 정보 바인딩 (이미지 속 올라검 님의 데이터 반영)
    String userName = "올라검";
    String userPhone = "01045276188";
    String userEmail = "minwoo7513@naver.com";
    
    // JSP EL 바인딩 등록
    request.setAttribute("step2Away", step2Away);
    request.setAttribute("step2Home", step2Home);
    request.setAttribute("step3Away", step3Away);
    request.setAttribute("step3Home", step3Home);
    request.setAttribute("stadiumName", stadiumName);
    request.setAttribute("matchDate", matchDate);
    request.setAttribute("seatGrade", seatGrade);
    request.setAttribute("seatDetail", seatDetail);
    
    request.setAttribute("userName", userName);
    request.setAttribute("userPhone", userPhone);
    request.setAttribute("userEmail", userEmail);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>bigBall예매</title>
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/reservationPage/reservation4.css">
<!-- 토스 페이먼츠 SDK -->
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
				<button class="btn-captcha-refresh" onclick="generateCaptchaValue()">↻</button>
			</div>
			<input type="text" id="captcha-user-input" class="captcha-input-box"
				maxlength="6" autocomplete="off">
			<div class="captcha-btns">
				<button class="btn-date-select" onclick="goToTeamSelection()">날짜
					다시 선택</button>
				<button class="btn-confirm-captcha" onclick="verifyCaptcha()">입력
					완료</button>
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

		<main class="main-workspace">

			<!-- [VIEW STATE 2: 등급/좌석선택화면] -->
			<div id="view-step-2" class="view-section">
				<div class="stadium-border-box">
					<span style="color: #aaa;">[ 좌석 배치도 영역 ]</span>
				</div>

				<!-- 이미지 기반으로 수정된 사이드바 -->
				<div class="side-panel">

					<!-- 1. 상단 팀 대진 -->
					<div class="match-card-img-style">
						<!-- 이미지 경로가 없다면 텍스트로 표시됨 -->
						<img src="../images/team_logo/hanwha.png" alt="SSG" class="team-logo-placeholder"
							onerror="this.src='https://via.placeholder.com/60x40?text=SSG'">
						<span class="vs-text-gray">VS</span> <img src="../images/team_logo/doosan.png"
							alt="LG" class="team-logo-placeholder"
							onerror="this.src='https://via.placeholder.com/60x40?text=LG'">
					</div>

					<!-- 2. 등급 선택 헤더 -->
					<div class="grade-header">
						<span class="grade-title-text">등급 선택</span>
						<button class="btn-refresh-img">새로고침</button>
					</div>

					<!-- 3. 보라색 테두리 리스트 박스 -->
					<div class="seats-scroll-box-img">
						<div class="seats-list-item-img"
							style="background: #f7f7f7; font-weight: bold;">
							<span>전체</span>
						</div>
						<div class="seats-list-item-img" id="item-1"
							onclick="mockSeatSelect('1루 자유석', 'item-1')">
							<div class="grade-name-group">
								<span class="color-square sq-blue"></span> <span>1루 자유석</span>
							</div>
							<span class="seat-count-text">0 석</span>
						</div>
						<div class="seats-list-item-img" id="item-2"
							onclick="mockSeatSelect('3루 자유석', 'item-2')">
							<div class="grade-name-group">
								<span class="color-square sq-red"></span> <span>3루 자유석</span>
							</div>
							<span class="seat-count-text">0 석</span>
						</div>
						<div class="seats-list-item-img" id="item-3"
							onclick="mockSeatSelect('홈 자유석', 'item-3')">
							<div class="grade-name-group">
								<span class="color-square sq-yellow"></span> <span>홈 자유석</span>
							</div>
							<span class="seat-count-text">0 석</span>
						</div>
						<div class="seats-list-item-img" id="item-4"
							onclick="mockSeatSelect('자유 외야석', 'item-4')">
							<div class="grade-name-group">
								<span class="color-square sq-green"></span> <span>자유 외야석</span>
							</div>
							<span class="seat-count-text">0 석</span>
						</div>
					</div>

					<!-- 4. 안내 링크 -->
					<a href="#" class="info-guide-link"> <span
						class="info-icon-circle">i</span> 좌석선점 및 자동배정 안내
					</a>

					<!-- 5. 다음단계 버튼 -->
					<button class="btn-next-large-img" onclick="transitionToStep3()">다음단계</button>

				</div>
			</div>

			<!-- [VIEW STATE 3: 권종/할인/매수선택화면] -->
			<div id="view-step-3" class="view-section hidden">
				<div class="ticket-selection-box">
					<div class="selection-title-main">티켓종류, 할인, 매수선택</div>

					<!-- 상단 선택 알림 바 (회색 테두리 박스) -->
					<div class="selection-alert-wrapper">
						<div class="selection-alert-bar">
							<span id="selected-seat-alert"><strong>${seatGrade}</strong>을
								<strong>1매</strong>를 선택하셨습니다.</span>
						</div>
					</div>

					<!-- 권종 선택 테이블 -->
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
									<td class="cat-cell" rowspan="3">성인</td>
									<td class="name-cell">성인</td>
									<td class="price-cell">${adultSeatPrice }원</td>
									<td class="select-cell"><select
										class="qty-select ticket-qty" data-price="16000"
										onchange="calculateInvoice()">
											<option value="0">0</option>
											<option value="1">1</option>
											<option value="1">2</option>
											<option value="1">3</option>
									</select></td>
								</tr>
								<tr>
									<td class="name-cell">청소년</td>
									<td class="price-cell">${youthSeatPrice }원</td>
									<td class="select-cell"><select
										class="qty-select ticket-qty" data-price="11200"
										onchange="calculateInvoice()">
											<option value="0">0</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
									</select></td>
								</tr>
								<tr>
									<td class="name-cell">어린이 <span class="q-icon">?</span></td>
									<td class="price-cell">${childSeatPrice }원</td>
									<td class="select-cell"><select
										class="qty-select ticket-qty" data-price="8000"
										onchange="calculateInvoice()">
											<option value="0">0</option>
											<option value="1">1</option>
											<option value="1">2</option>
											<option value="1">3</option>
									</select></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="bottom-notice-area">
						<h4>안내사항</h4>
						<p>※ 티켓 부정거래 적발시 별도의 사전 통보나 소명 절차 없이 강제 예매 취소 처리가 가능하며, 이 경우
							취소 수수료가 부과됩니다.</p>
						<p>※ 일부 좌석은 구단 사정(행사 등)에 따라 예매 오픈 전에 배정될 수 있습니다.</p>
					</div>
				</div>

				<div class="side-panel-step3">
					<div class="side-match-info">
						<div class="match-vs-logos">
							<img src="../images/team_logo/hanwha.png" alt="SSG" class="team-logo-placeholder"
							onerror="this.src='https://via.placeholder.com/60x40?text=SSG'">
							<span class="vs-label">VS</span>
							<img src="../images/team_logo/doosan.png" alt="LG" class="team-logo-placeholder"
							onerror="this.src='https://via.placeholder.com/60x40?text=LG'">
						</div>
						<div class="match-details-text">
							<div class="match-title">
								${game.teamHomeName } <span class="vs-gray">vs</span> <span class="home-badge">H</span>
								${game.teamOtherName }
							</div>
							<div class="stadium-name">${stadiumName}</div>
							<div class="match-date">${matchDate}</div>
						</div>
					</div>

					<div class="booking-summary-box-img">
						<div class="box-label">예매정보</div>
						<div class="seat-info-detail">
							<span class="seat-grade-txt">${seatGrade}</span>
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
						<tr>
							<td>쿠폰할인</td>
							<td class="price-val">0원</td>
						</tr>
						<tr>
							<td>포인트사용</td>
							<td class="price-val">0원</td>
						</tr>
						<tr class="total-row">
							<td>총결제</td>
							<td class="total-val" id="invoice-total">0원</td>
						</tr>
					</table>

					<div class="cancel-deadline-info">
						취소기한: 2026.06.02 14:30<br> 취소수수료: 티켓금액의 0~30% <span
							class="detail-link" onclick="openDetailModal(1)">[상세보기]</span>
					</div>

					<div class="side-btn-group">
						<button class="btn-step-prev" onclick="transitionToStep2()">이전단계</button>
						<button class="btn-step-next" onclick="transitionToStep4()">다음단계</button>
					</div>
				</div>
			</div>

			<!-- ======================================================= -->
			<!-- [VIEW STATE 4: 배송선택/예매확인 (새 업로드 이미지 완벽 반영)] -->
			<!-- ======================================================= -->
			<div id="view-step-4" class="view-section hidden">

				<!-- 스크롤 수령 및 동의 폼 컨테이너 -->
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
					<p class="step4-subtext">* 관람당일 현장에서 예매번호 및 본인확인 후 수령하실 수 있습니다.</p>

					<h4 class="step4-section-title">주문자 정보</h4>
					<table class="step4-table">
						<tbody>
							<tr>
								<td class="step4-label-cell">이름 *</td>
								<td><input type="text" value="${userName}"
									class="step4-input" style="font-weight: bold;"></td>
								<td class="step4-label-cell">휴대폰 번호 *</td>
								<td><input type="text" value="${userPhone}"
									class="step4-input" style="font-weight: bold;"></td>
							</tr>
							<tr>
								<td class="step4-label-cell">이메일</td>
								<td colspan="3"><input type="text" value="${userEmail}"
									class="step4-input-full"></td>
							</tr>
						</tbody>
					</table>

					<h4 class="step4-section-title">예매자 확인</h4>
					<!-- 예매자 체크 동의 사항 (상세보기 연계 클릭 지원) -->
					<div class="consent-list-container">
						<div class="consent-item">
							<div class="consent-left">
								<input type="checkbox" id="check-consent-1" checked> <span>티켓
									예매 확인, 취소 처리 및 배송을 위한 개인정보를 수집·이용합니다.</span>
							</div>
							<span class="link-detail" onclick="openDetailModal(1)">[상세보기]</span>
						</div>
						<div class="consent-item">
							<div class="consent-left">
								<input type="checkbox" id="check-consent-2"> <span>(선택)
									예매 안내 메일 발송을 위해 이메일 정보를 수집·이용합니다.</span>
							</div>
							<span class="link-detail" onclick="openDetailModal(2)">[상세보기]</span>
						</div>
						<div class="consent-item">
							<div class="consent-left">
								<input type="checkbox" id="check-consent-3" checked> <span>개인정보
									제 3자 제공에 동의합니다. (고객응대 및 관람정보안내 등을 위한)</span>
							</div>
							<span class="link-detail" onclick="openDetailModal(3)">[상세보기]</span>
						</div>
						<div class="consent-item">
							<div class="consent-left">
								<input type="checkbox" id="check-consent-4" checked> <span>KBO리그
									SAFE캠페인에 동의합니다.</span>
							</div>
							<span class="link-detail" onclick="openDetailModal(4)">[상세보기]</span>
						</div>
						<div class="consent-item">
							<div class="consent-left">
								<input type="checkbox" id="check-consent-5" checked> <span>프로스포츠
									암표매매 행위에 따른 제재사항에 동의합니다.</span>
							</div>
							<span class="link-detail" onclick="openDetailModal(5)">[상세보기]</span>
						</div>
					</div>
				</div>

				<!-- [VIEW STATE 4: 배송선택/예매확인] 우측 사이드바 -->
				<div class="side-panel-step3">
					<!-- Step 3와 동일한 클래스 사용 -->
					<div class="side-match-info">
						<div class="match-vs-logos">
							<div class="team-circle-logo lotte">롯데</div>
							<span class="vs-label">VS</span>
							<div class="team-circle-logo kia">KIA</div>
						</div>
						<div class="match-details-text">
							<div class="match-title">
								롯데 <span class="vs-gray">vs</span> <span class="home-badge">H</span>
								KIA
							</div>
							<div class="stadium-name">${stadiumName}</div>
							<div class="match-date">${matchDate}</div>
						</div>
					</div>

					<div class="booking-summary-box-img">
						<div class="box-label">예매정보</div>
						<div class="seat-info-detail">
							<span class="seat-grade-txt">${seatGrade}</span>
						</div>
					</div>

					<!-- 4단계 영수증 요약 -->
					<table class="payment-summary-table">
						<tbody>
							<tr>
								<td>티켓금액</td>
								<td class="price-val" id="invoice-ticket-step4">0원</td>
							</tr>
							<tr>
								<td>할인금액</td>
								<td class="price-val">0원</td>
							</tr>
							<tr>
								<td>포인트 사용</td>
								<td class="price-val">0원</td>
							</tr>
							<tr class="total-row">
								<td>총결제</td>
								<td class="total-val" id="invoice-total-step4">0원</td>
							</tr>
						</tbody>
					</table>

					<!-- 결제 수단 선택 영역 (이미지 반영) -->
					<div class="payment-method-selector">
						<div class="box-label">결제수단 선택</div>
						<label class="toss-radio-label"> <input type="radio"
							name="payment-method-group" checked> toss 결제
						</label>
					</div>

					<!-- 4단계 하단 액션 버튼 그룹 -->
					<div class="side-btn-group">
						<button class="btn-step-prev"
							onclick="transitionToStep3FromStep4()">이전단계</button>
						<button class="btn-step-next btn-pay-red"
							onclick="triggerFinalPayment()">결제하기</button>
					</div>
				</div>

			</div>

		</main>
	</div>

	<!-- [공통] 약관 상세 팝업용 가상 모달 창 -->
	<div id="detail-modal" class="modal-overlay hidden">
    <div class="modal-card">
        <div class="modal-header">
            <span id="modal-title">동의 내용 확인</span>
            <button class="modal-close-btn" onclick="closeDetailModal()">×</button>
        </div>
        <div class="modal-body" id="modal-text">내용이 표시됩니다.</div>
        <!-- 수정된 푸터 부분: 인라인 스타일 제거 -->
        <div class="modal-footer">
            <button class="btn-action btn-prev" onclick="closeDetailModal()">닫기</button>
        </div>
    </div>
</div>

	<!-- 가변 상태 흐름 제어 및 정산 수량 계산 스크립트 -->
	<script>
    	// 1단계 클린예매(보안문자) 관련 로직
	    let currentCaptchaCode = "";
	
	    // 보안문자 생성 함수 (영문 대문자 6자리)
	    function generateCaptchaValue() {
	        const chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
	        let result = "";
	        for (let i = 0; i < 6; i++) {
	            result += chars.charAt(Math.floor(Math.random() * chars.length));
	        }
	        currentCaptchaCode = result;
	        document.getElementById('captcha-display-text').innerText = result;
	        document.getElementById('captcha-user-input').value = "";
	    }
	
	    // 보안문자 검증 및 화면 전환
	    function verifyCaptcha() {
	        const userInput = document.getElementById('captcha-user-input').value;
	        if (userInput === currentCaptchaCode) {
	            alert("인증에 성공했습니다.");
	            document.getElementById('captcha-overlay').classList.add('hidden');
	        } else {
	            alert("보안문자가 일치하지 않습니다. (대문자로 입력해주세요.)");
	            generateCaptchaValue();
	        }
	    }
	    // '날짜 다시 선택' 버튼 클릭 시 이동 (기존 팀 페이지 가정)
        function goToTeamSelection() {
            // 이전에 제작하신 팀 선택 페이지 주소로 수정해 사용하세요.
            location.href = "teamPage.jsp"; 
        }
	    
     
	    
        let calculatedTicketPrice = 0;
        let calculatedFee = 0;
        let calculatedTotal = 0;

       
     	// 등급 선택 로직
        let selectActive = false; 

		function mockSeatSelect(name, id) {
		    // 모든 항목 선택 해제
		    document.querySelectorAll('.seats-list-item-img').forEach(el => el.classList.remove('selected'));
		    
		    // 클릭한 항목에 파란 테두리 추가
		    const target = document.getElementById(id);
		    if(target) target.classList.add('selected');
		    
		    selectActive = true; // "다음단계" 버튼을 누를 수 있는 상태로 변경
		}



        // 2단계 -> 3단계 전환
        function transitionToStep3() {
		    if (!selectActive) {
		        alert("좌석 등급을 먼저 선택해주세요.");
		        return;
		    }
		    // 뷰 전환
		    document.getElementById('view-step-2').classList.add('hidden');
		    document.getElementById('view-step-3').classList.remove('hidden');
		    
		    // 상단 메뉴 활성화 변경
		    document.getElementById('step-nav-2').classList.remove('active');
		    document.getElementById('step-nav-3').classList.add('active');
		}

        // 3단계 -> 2단계 전환
        function transitionToStep2() {
            document.getElementById('step-nav-3').classList.remove('active');
            document.getElementById('step-nav-2').classList.add('active');
            document.getElementById('view-step-3').classList.add('hidden');
            document.getElementById('view-step-2').classList.remove('hidden');
        }

        // 3단계 -> 4단계 전환 (이미지 속 다음단계 로직 연동)
        function transitionToStep4() {
            if (calculatedTotal === 0) {
                alert("티켓 종류 및 권종 매수를 최소 1개 이상 정해주셔야 다음 단계로 전환이 가능합니다.");
                return;
            }
            // 네비게이션 가이드 세그먼트 활성화 교체
            document.getElementById('step-nav-3').classList.remove('active');
            document.getElementById('step-nav-4').classList.add('active');

            // 영수증 수치 데이터를 4단계 최종 정산 항목에도 고스란히 반영
            document.getElementById('invoice-ticket-step4').innerText = calculatedTicketPrice.toLocaleString() + "원";
            document.getElementById('invoice-total-step4').innerText = calculatedTotal.toLocaleString() + "원";

            // 뷰 패널 전환
            document.getElementById('view-step-3').classList.add('hidden');
            document.getElementById('view-step-4').classList.remove('hidden');
        }

        // 4단계 -> 3단계 전환
        function transitionToStep3FromStep4() {
            document.getElementById('step-nav-4').classList.remove('active');
            document.getElementById('step-nav-3').classList.add('active');
            document.getElementById('view-step-4').classList.add('hidden');
            document.getElementById('view-step-3').classList.remove('hidden');
        }

        // 3단계 수량 변경에 따른 실시간 영수증 연산기
        function calculateInvoice() {
            const qtyDropdowns = document.querySelectorAll('.ticket-qty');
            let sumTicketPrice = 0;
            let sumQty = 0;

            qtyDropdowns.forEach(dropdown => {
                const qty = parseInt(dropdown.value);
                const pricePerOne = parseInt(dropdown.getAttribute('data-price'));
                
                if (qty > 0) {
                    sumTicketPrice += (qty * pricePerOne);
                    sumQty += qty;
                }
            });

            calculatedTicketPrice = sumTicketPrice;
            calculatedFee = sumQty > 0 ? (sumQty * 1000) : 0;
            calculatedTotal = calculatedTicketPrice + calculatedFee;

            // 3단계 결제 내역 바 갱신
            document.getElementById('invoice-ticket').innerText = calculatedTicketPrice.toLocaleString() + "원";
            document.getElementById('invoice-fee').innerText = calculatedFee.toLocaleString() + "원";
            document.getElementById('invoice-total').innerText = calculatedTotal.toLocaleString() + "원";
        }

        function triggerFinalPayment() {
            console.log("결제 시도 금액:", calculatedTotal);

            // 1. 필수 약관 동의 체크
            const check1 = document.getElementById('check-consent-1').checked;
            const check3 = document.getElementById('check-consent-3').checked;
            const check4 = document.getElementById('check-consent-4').checked;
            const check5 = document.getElementById('check-consent-5').checked;

            if (!check1 || !check3 || !check4 || !check5) {
                alert("필수동의 항목에 동의하셔야 예매를 완수하실 수 있습니다.");
                return;
            }

            // 2. 금액 체크
            if (calculatedTotal <= 0) {
                alert("결제 금액이 0원입니다. 티켓 매수를 선택해주세요.");
                return;
            }

            // 3. 토스페이먼츠 실행
            try {
                // SDK가 정상 로드되었는지 확인
                if (typeof TossPayments === "undefined") {
                    alert("결제 모듈을 불러오지 못했습니다. 인터넷 연결을 확인하거나 잠시 후 다시 시도해주세요.");
                    return;
                }

                const clientKey = 'test_ck_ma60RZblrq5wx4j5Pa9ZrwzYWBn1'; 
                const tossPayments = TossPayments(clientKey);

                // 변수 안전하게 할당 (값이 없을 경우 대비)
                const sName = "${stadiumName}" || "야구장";
                const sGrade = "${seatGrade}" || "좌석";
                const uName = "${userName}" || "구매자";

                tossPayments.requestPayment('가상계좌', {
                    amount: calculatedTotal,
                    orderId: "ORDER-" + new Date().getTime(), // 매번 고유한 아이디 생성
                    orderName: sName + " " + sGrade + " 티켓",
                    customerName: uName,
                    // 테스트를 위해 성공/실패 시 현재 주소로 돌아오게 설정 (실제 서비스 시 success.jsp 등으로 변경)
                    successUrl: window.location.href, 
                    failUrl: window.location.href,
                    validHours: 24,
                    cashReceipt: { type: '소득공제' },
                }).catch(function (error) {
                    if (error.code === 'USER_CANCEL') {
                        alert("사용자가 결제를 취소했습니다.");
                    } else {
                        alert("결제 오류: " + error.message);
                    }
                });
            } catch (e) {
                console.error(e);
                alert("결제 시스템 호출 중 오류가 발생했습니다.");
            }
        }

        // [상세보기] 모달 데이터 바인딩 딕셔너리
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
                      " - KIA타이거즈(구단)\n\n" +
                      "2. 제공하는 개인정보 항목\n" +
                      " - KIA타이거즈(구단): 이름, 아이디, 휴대폰번호, 이메일주소, 주문정보\n\n" +
                      "3. 개인정보를 제공받는 자의 이용목적\n" +
                      " - 구단 마케팅 활용, 시즌권 회원 가입여부 확인 및 관리\n\n" +
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

        // 제한시간 타이머 기동 (10분 기준)
        document.addEventListener("DOMContentLoaded", () => {
        	generateCaptchaValue(); // 보안문자 생성
        	
            let totalSeconds = 600; 
            const timerDisplay = document.getElementById("countdown");

            const updateTimer = () => {
                let minutes = Math.floor(totalSeconds / 60);
                let seconds = totalSeconds % 60;

                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                timerDisplay.textContent = minutes + ":" + seconds;

                if (totalSeconds > 0) {
                    totalSeconds--;
                } else {
                    clearInterval(countdownInterval);
                    timerDisplay.textContent = "00:00";
                    alert("선택 시간이 만료되었습니다.")
                    window.close();
                }
            };

            const countdownInterval = setInterval(updateTimer, 1000);
        });
        
    
    </script>
</body>
</html>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	page import="kr.user.reservation.ReservationPageDTO"%>
<%
    // [동적 서버 데이터 시뮬레이션]
    // 2단계 경기 정보
    
    ReservationPageDTO rpDTO=new ReservationPageDTO();
    String teamHomeName=rpDTO.getTeamHomeName();
    String teamHomeImg=rpDTO.getTeamHomeImg();
    String teamOtherName=rpDTO.getTeamOtherName();
    String teamOtherImg=rpDTO.getTeamOtherImg();
    
    
    // 3단계 경기 및 좌석 예매 정보 (업로드해주신 이미지 데이터 기준 바인딩)
    String stadiumName=rpDTO.getStadiumName();
    Date matchDate = rpDTO.getGameDate();
    
    
    //reservationCode받고 경기에 대한 정보를 변경함
    
    // JSP EL 바인딩을 위한 세팅
    request.setAttribute("teamHomeName", teamHomeName);
    request.setAttribute("teamHomeImg", teamHomeImg);
    request.setAttribute("temaOtherName", teamOtherName);
    request.setAttribute("temaOtherImg", teamOtherImg);
    request.setAttribute("stadiumName", stadiumName);
    request.setAttribute("matchDate", matchDate);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>티켓링크 예매 시뮬레이터</title>
<link rel="stylesheet" href="reservation.css">
</head>
<body>

    <div class="ticketlink-container">
        
        <!-- 1. 공통 상단 타이틀 -->
        <header class="booking-header">
            <div class="logo-area">
                big<span class="logo-bold">Ball</span> 예매
            </div>
            <div class="timer-area">
                <span>예매가능 시간</span>
                <span id="countdown" class="timer-time">10:00</span>
                <span class="question-badge">?</span>
            </div>
        </header>

        <!-- 2. 단계 가이드바 (동적으로 active 클래스가 할당됨) -->
        <nav class="step-progress-bar">
            <div class="step-item" id="step-nav-1">날짜/회차선택</div>
            <div class="step-item active" id="step-nav-2">등급/좌석선택</div>
            <div class="step-item" id="step-nav-3">권종/할인/매수선택</div>
            <div class="step-item" id="step-nav-4">배송선택/예매확인</div>
            <div class="step-item" id="step-nav-5">결제</div>
        </nav>

        <!-- 3. 메인 가변 워크스페이스 -->
        <main class="main-workspace">
            
            <!-- ======================================================= -->
            <!-- [VIEW STATE 2: 등급/좌석선택화면] -->
            <!-- ======================================================= -->
            <div id="view-step-2" class="view-section">
                <!-- 경기장 맵 구역 -->
                <div class="stadium-border-box">
                   	<img src="#void">

     
                </div>

                <!-- 등급 선택 패널 -->
                <div class="side-panel">
                    <div class="side-top-info">
                        <div class="match-card">
                            <div class="team-box">
                                <img src="${teamHomImg }">
                            </div>
                            <span class="vs-text">VS</span>
                            <div class="team-box">
                                <img src="${teamOtherImg }">
                            </div>
                        </div>

                        <div class="class-select-header">
                            <span class="class-title">등급 선택</span>
                            <button class="btn-refresh">새로고침</button>
                        </div>

                        <div class="seats-scroll-box">
                            <div class="seats-list-item"><span>전체</span><span></span></div>
                            <div class="seats-list-item" id="list-first" onclick="mockSeatSelect('1루 자유석')">
                                <div class="seat-name-group"><span class="square-indicator chip-blue"></span><span>1루 K9지정석</span></div>
                                <span class="seat-count">1 석</span>
                            </div>
                            <div class="seats-list-item" id="list-third" onclick="mockSeatSelect('3루 자유석')">
                                <div class="seat-name-group"><span class="square-indicator chip-red"></span><span>3루 자유석</span></div>
                                <span class="seat-count">0 석</span>
                            </div>
                        </div>
                    </div>

                    <!-- 2단계용 단일 액션 버튼 -->
                    <button class="next-step-btn" id="btn-next-step2" onclick="transitionToStep3()">다음단계</button>
                </div>
            </div>

            <!-- ======================================================= -->
            <!-- [VIEW STATE 3: 권종/할인/매수선택화면 (업로드 이미지 기반)] -->
            <!-- ======================================================= -->
            <div id="view-step-3" class="view-section hidden">
                
                <!-- 티켓 종류 및 할인 선택 상세 테이블 박스 -->
                <div class="ticket-selection-box">
                    <div class="selection-title">티켓종류, 할인, 매수선택</div>
                    
                    

                    <!-- 권종 및 카드할인/일반할인 리스트 테이블 -->
                    <div class="ticket-table-wrapper">
                        <table class="ticket-table">
                            <tbody>
                                <!-- 1. 일반(정가) 그룹 -->
                                <tr>
                                    <td class="category-cell" rowspan="3">일반(정가)</td>
                                    <td class="ticket-name-cell">성인</td>
                                    <td class="ticket-price-cell">16,000원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="16000" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="ticket-name-cell">청소년</td>
                                    <td class="ticket-price-cell">11,200원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="11200" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="ticket-name-cell">어린이 <span class="help-icon">?</span></td>
                                    <td class="ticket-price-cell">8,000원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="8000" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>

                                <!-- 2. 일반할인 그룹 -->
                                <tr>
                                    <td class="category-cell" rowspan="6">쿠폰할인</td>
                                    <td class="ticket-name-cell"> <span class="help-icon">?</span></td>
                                    <td class="ticket-price-cell">11,200원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="11200" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- 하단 안내사항 -->
                    <div class="notice-container">
                        <h5>안내사항</h5>
                        <p>※ 티켓 부정거래 적발시 별도의 사전 통보나 소명 절차 없이 강제 예매 취소 처리가 가능하며, 이 경우 취소 수수료가 부과됩니다.</p>
                        <p style="margin-top: 4px;">※ 일부 좌석은 구단 사정(행사 등)에 따라 예매 오픈 전에 배정될 수 있습니다.</p>
                    </div>
                </div>

                <!-- 3단계 전용 우측 정산 바 -->
                <div class="side-panel">
                    <div class="side-top-info">
                        <!-- 대진 매칭 (롯데 vs KIA) -->
                        <div class="match-card">
                            <div class="team-box">
                                <div class="team-logo-circle bg-lotte">${teamHomeName}</div>
                                <span class="team-name-label">${teamHomeImg}</span>
                            </div>
                            <span class="vs-text">VS</span>
                            <div class="team-box">
                                <div class="team-logo-circle bg-kia">${teamOtherName}</div>
                                <span class="team-name-label">${teamOtherImg}</span>
                            </div>
                        </div>

                        <!-- 롯데 vs KIA 상세 장소 및 일정 정보 -->
                        <div style="font-size: 11px; font-weight: bold; margin-bottom: 12px; line-height: 1.4;">
                            <div style="font-size: 12px;">${step3Away} vs <span style="background-color:#000; color:#fff; padding:1px 3px; border-radius:2px;">H</span> ${step3Home}</div>
                            <div style="color: #666; margin-top: 2px;">${stadiumName}</div>
                            <div style="color: #444;">${matchDate}</div>
                        </div>

                        <!-- 예매정보 (좌석 디스플레이 블록) -->
                        <div class="booking-summary-box">
                            <div class="summary-box-title">예매정보</div>
                            
                        </div>

                        <!-- 실시간 요약 금액 테이블 -->
                        <table class="invoice-table">
                            <tbody>
                                <tr>
                                    <td>티켓금액</td>
                                    <td class="invoice-val" id="invoice-ticket">0원</td>
                                </tr>
                                <tr>
                                    <td>예매수수료</td>
                                    <td class="invoice-val" id="invoice-fee">0원</td>
                                <tr>
                                    <td>쿠폰할인</td>
                                    <td class="invoice-val">0원</td>
                                </tr>
                                <tr>
                                    <td>총결제</td>
                                    <td class="invoice-val total-red" id="invoice-total">0원</td>
                                </tr>
                            </tbody>
                        </table>

                        <div class="cancel-guide-txt">
                            취소기한: 2026.06.02 14:30<br>
                            취소수수료: 티켓금액의 0~30% <span style="text-decoration: underline; cursor:pointer;">[상세보기]</span>
                        </div>
                    </div>

                    <!-- 3단계 전용 복수 전환 버튼 (이전단계 / 다음단계) -->
                    <div class="btn-group">
                        <button class="btn-action btn-prev" onclick="transitionToStep2()">이전단계</button>
                        <button class="btn-action btn-next" id="btn-next" onclick="alert('다음 단계인 배송선택으로 진행합니다.');">다음단계</button>
                       <%--  <%if(){
                        	
                        }
                        %> --%>
                    </div>
                </div>

            </div>

        </main>
    </div>

    <!-- 페이지 전환 및 실시간 정산 수량 계산 스크립트 -->
 	<script type="text/javascript">
 	</script>
    <script>
        let selectActive = false; // 좌석 선택 플래그

        // 2단계에서 가상의 좌석 리스트 아이템 클릭 제어
        function mockSeatSelect(zoneName) {
            const listFirst = document.getElementById('list-first');
            listFirst.classList.add('selected');
            selectActive = true;
            
            alert(zoneName + " [1루 K9지정석 112블록 11열 7번] 좌석이 임시 선택되었습니다.");
        }

        // 2단계 -> 3단계 전환 매커니즘
        function transitionToStep3() {
            if (!selectActive) {
                alert("선택할 수 있는 좌석 등급을 먼저 터치하여 지정해주세요.");
                return;
            }

            // 가이드 바 active 노드 갱신
            document.getElementById('step-nav-2').classList.remove('active');
            document.getElementById('step-nav-3').classList.add('active');

            // 뷰 패널 전환
            document.getElementById('view-step-2').classList.add('hidden');
            document.getElementById('view-step-3').classList.remove('hidden');
        }

        // 3단계 -> 2단계 회귀 매커니즘
        function transitionToStep2() {
            // 가이드 바 active 노드 갱신
            document.getElementById('step-nav-3').classList.remove('active');
            document.getElementById('step-nav-2').classList.add('active');

            // 뷰 패널 전환
            document.getElementById('view-step-3').classList.add('hidden');
            document.getElementById('view-step-2').classList.remove('hidden');
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

            // 예매수수료 계산 (티켓 1장당 수수료 1,000원 가정)
            let bookingFee = sumQty > 0 ? (sumQty * 1000) : 0;
            let finalTotalPrice = sumTicketPrice + bookingFee;

            // 정산 바 DOM 정보 갱신
            document.getElementById('invoice-ticket').innerText = sumTicketPrice.toLocaleString() + "원";
            document.getElementById('invoice-fee').innerText = bookingFee.toLocaleString() + "원";
            document.getElementById('invoice-total').innerText = finalTotalPrice.toLocaleString() + "원";
        }

        // 7분 33초 카운트다운 타이머 구현
        document.addEventListener("DOMContentLoaded", () => {
            let totalSeconds = 600; // 7분 33초 (7 * 60 + 33)
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
                    alert("선택 시간이 초과되었습니다.");
                }
            };

            const countdownInterval = setInterval(updateTimer, 1000);
        });
    </script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>티켓링크 예매 시뮬레이터</title>
    <style>
        /* 글로벌 UI 공통 스타일 */
        body {
            margin: 0;
            padding: 0;
            font-family: 'Malgun Gothic', '맑은 고딕', Arial, sans-serif;
            background-color: #ffffff;
            color: #333333;
        }

        .ticketlink-container {
            width: 1000px;
            margin: 0 auto;
            padding: 10px;
            user-select: none;
        }

        /* 1. 상단 타이틀 영역 */
        .booking-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 2px solid #4a4a4a;
        }

        .logo-area {
            font-size: 22px;
            font-weight: bold;
            color: #1a1a1a;
        }

        .logo-bold {
            font-weight: 900;
        }

        .timer-area {
            font-size: 13px;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 4px;
        }

        .timer-time {
            color: #1d70e2; 
            font-size: 15px;
        }

        .question-badge {
            display: inline-block;
            width: 15px;
            height: 15px;
            background-color: #e1e1e1;
            color: #666;
            text-align: center;
            line-height: 15px;
            font-size: 11px;
            border-radius: 2px;
        }

        /* 2. 단계(Step) 표시 진행바 */
        .step-progress-bar {
            display: flex;
            width: 100%;
            height: 36px;
            background-color: #cfd2d6;
            margin-bottom: 15px;
        }

        .step-item {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            font-weight: bold;
            color: #ffffff;
            position: relative;
            background-color: #8c939d;
            clip-path: polygon(0% 0%, 93% 0%, 100% 50%, 93% 100%, 0% 100%, 7% 50%);
            margin-right: -10px;
        }

        .step-item:first-child {
            clip-path: polygon(0% 0%, 93% 0%, 100% 50%, 93% 100%, 0% 100%);
        }

        .step-item:last-child {
            clip-path: polygon(0% 0%, 100% 0%, 100% 100%, 0% 100%, 7% 50%);
            margin-right: 0;
        }

        .step-item.active {
            background-color: #22252a;
        }

        /* 공통 작업대 뷰 구조 */
        .main-workspace {
            display: flex;
            gap: 20px;
            height: 520px;
        }

        .view-section {
            flex: 1;
            display: flex;
            gap: 20px;
            height: 100%;
        }

        .hidden {
            display: none !important;
        }

        /* [공통] 사이드 바 패널 기본 */
        .side-panel {
            width: 250px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            border-left: 1px solid #f0f0f0;
            padding-left: 15px;
        }

        /* 대진 경기 팀 정보 */
        .match-card {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 12px;
            border: 1px solid #e1e1e1;
            padding: 10px;
            border-radius: 4px;
            background-color: #fafafa;
            margin-bottom: 12px;
        }

        .team-box {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 4px;
        }

        .team-logo-circle {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            color: #ffffff;
            font-size: 8px;
            font-weight: 900;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 1px 1px 2px rgba(0,0,0,0.1);
        }

        .bg-landers { background-color: #e02c1d; }
        .bg-twins { background-color: #c50a50; }
        .bg-lotte { background-color: #002c5f; }
        .bg-kia { background-color: #c40a35; }

        .vs-text { font-size: 10px; font-weight: bold; color: #999999; }
        .team-name-label { font-size: 11px; font-weight: bold; color: #333; }

        /* ======================================================= */
        /* [2단계 구역전용 디자인] */
        /* ======================================================= */
        .stadium-border-box {
            flex: 1;
            border: 2px solid #b4a2f7;
            background-color: #ffffff;
            padding: 15px;
            border-radius: 4px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .stadium-legend {
            display: flex;
            gap: 15px;
            margin-top: 15px;
            font-size: 13px;
            font-weight: bold;
        }

        .legend-item { display: flex; align-items: center; gap: 6px; }
        .color-chip { width: 24px; height: 12px; border-radius: 2px; }
        .chip-blue { background-color: #1d70e2; }
        .chip-red { background-color: #be2a2b; }
        .chip-yellow { background-color: #f0c20c; }
        .chip-green { background-color: #16a34a; }

        .seats-scroll-box {
            border: 1px solid #ddd;
            height: 180px;
            overflow-y: scroll;
            border-radius: 4px;
            background-color: #ffffff;
        }

        .seats-list-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 8px 10px;
            border-bottom: 1px solid #f0f0f0;
            font-size: 11px;
            cursor: pointer;
        }

        .seats-list-item.selected {
            background-color: #ebf5ff;
            border-color: #3b82f6;
        }

        .seats-list-item:first-child {
            background-color: #f7f9fa;
            font-weight: bold;
        }

        /* ======================================================= */
        /* [3단계 구역전용 디자인] */
        /* ======================================================= */
        .ticket-selection-box {
            flex: 1;
            display: flex;
            flex-direction: column;
            border: 1px solid #e1e1e1;
            border-radius: 4px;
            overflow: hidden;
            height: 100%;
        }

        .selection-title {
            background-color: #ffffff;
            font-size: 13px;
            font-weight: bold;
            padding: 10px 15px;
            border-bottom: 1px solid #e1e1e1;
        }

        .selection-alert-bar {
            background-color: #ffffff;
            border-bottom: 1px solid #e1e1e1;
            padding: 12px;
            font-size: 12px;
            font-weight: bold;
            color: #333333;
            text-align: center;
        }

        .ticket-table-wrapper {
            flex: 1;
            overflow-y: auto;
        }

        .ticket-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 11px;
        }

        .ticket-table td {
            border: 1px solid #e9e9e9;
            padding: 8px 12px;
            vertical-align: middle;
        }

        .category-cell {
            background-color: #fcfcfc;
            font-weight: bold;
            text-align: center;
            width: 90px;
            color: #444;
        }

        .ticket-name-cell {
            font-weight: bold;
            color: #333;
        }

        .help-icon {
            display: inline-block;
            width: 13px;
            height: 13px;
            background-color: #e1e1e1;
            color: #666;
            border-radius: 2px;
            text-align: center;
            line-height: 13px;
            font-size: 10px;
            margin-left: 4px;
            cursor: pointer;
        }

        .ticket-price-cell {
            text-align: right;
            font-weight: bold;
            width: 80px;
            padding-right: 15px;
        }

        .ticket-select-cell {
            width: 60px;
            text-align: center;
        }

        .dropdown-select {
            width: 100%;
            padding: 3px;
            border: 1px solid #ccc;
            border-radius: 3px;
            font-size: 11px;
        }

        .notice-container {
            padding: 15px;
            background-color: #fafafa;
            border-top: 1px solid #e1e1e1;
            font-size: 11px;
            color: #555555;
            line-height: 1.6;
        }

        .notice-container h5 {
            font-size: 12px;
            font-weight: bold;
            margin: 0 0 8px 0;
            color: #222;
        }

        /* 3/4단계 우측 예매정보 디스플레이 박스 */
        .booking-summary-box {
            border: 1px solid #e1e1e1;
            border-radius: 4px;
            padding: 12px;
            background-color: #ffffff;
            margin-bottom: 12px;
        }

        .summary-box-title {
            font-size: 11px;
            font-weight: bold;
            color: #666;
            margin-bottom: 8px;
        }

        .summary-box-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .summary-grade {
            font-size: 14px;
            font-weight: bold;
            color: #333;
        }

        .summary-detail {
            font-size: 14px;
            font-weight: bold;
            color: #e51a24;
        }

        .invoice-table {
            width: 100%;
            font-size: 11px;
            border-collapse: collapse;
            margin-top: 8px;
        }

        .invoice-table td {
            padding: 5px 0;
            color: #555;
        }

        .invoice-table tr:last-child td {
            border-top: 1px solid #e1e1e1;
            padding-top: 8px;
            font-weight: bold;
        }

        .invoice-val {
            text-align: right;
            font-weight: bold;
            color: #111;
        }

        .invoice-val.total-red {
            color: #e51a24;
            font-size: 13px;
        }

        .cancel-guide-txt {
            font-size: 10px;
            color: #666666;
            margin-top: 8px;
            line-height: 1.4;
        }

        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 15px;
        }

        .btn-action {
            flex: 1;
            height: 40px;
            border-radius: 4px;
            font-size: 13px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.1s ease;
        }

        .btn-prev {
            background-color: #ffffff;
            border: 1px solid #cccccc;
            color: #333;
        }

        .btn-prev:hover {
            background-color: #f5f5f5;
        }

        .btn-next {
            background-color: #22252a;
            border: 1px solid #22252a;
            color: #ffffff;
        }

        .btn-next:hover {
            background-color: #111;
        }

        /* ======================================================= */
        /* [4단계 구역전용 디자인 - 업로드 이미지 반영] */
        /* ======================================================= */
        .step4-content-scroll {
            flex: 1;
            height: 100%;
            overflow-y: auto;
            border: 1px solid #e1e1e1;
            border-radius: 4px;
            padding: 15px;
            box-sizing: border-box;
        }

        .step4-section-title {
            font-size: 14px;
            font-weight: bold;
            color: #1a1a1a;
            border-bottom: 2px solid #555;
            padding-bottom: 6px;
            margin-top: 0;
            margin-bottom: 12px;
        }

        /* 테이블 공통 디자인 */
        .step4-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 11px;
            margin-bottom: 20px;
        }

        .step4-table td {
            border: 1px solid #e9e9e9;
            padding: 10px 12px;
        }

        .step4-label-cell {
            background-color: #fafafa;
            font-weight: bold;
            color: #444;
            width: 110px;
        }

        .step4-input {
            width: 160px;
            padding: 4px 8px;
            border: 1px solid #ccc;
            border-radius: 2px;
            font-size: 11px;
        }

        .step4-input-full {
            width: 90%;
            padding: 4px 8px;
            border: 1px solid #ccc;
            border-radius: 2px;
            font-size: 11px;
        }

        .step4-subtext {
            font-size: 10px;
            color: #666;
            margin-top: -12px;
            margin-bottom: 15px;
        }

        /* 동의 항목 리스트 */
        .consent-list-container {
            display: flex;
            flex-direction: column;
            gap: 12px;
            margin-top: 10px;
        }

        .consent-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 11px;
            font-weight: bold;
            color: #333;
        }

        .consent-left {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .link-detail {
            color: #e51a24;
            font-size: 11px;
            text-decoration: underline;
            cursor: pointer;
        }

        /* 결제하기 오버라이드 색상 */
        .btn-pay {
            background-color: #e51a24 !important;
            border-color: #e51a24 !important;
            color: #ffffff;
        }

        .btn-pay:hover {
            background-color: #c40a15 !important;
        }

        /* ======================================================= */
        /* [상세보기용 모달 창 레이아웃] */
        /* ======================================================= */
        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100vw;
            height: 100vh;
            background-color: rgba(0,0,0,0.4);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 100;
        }

        .modal-card {
            background-color: #ffffff;
            width: 480px;
            border-radius: 4px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.15);
            border: 1px solid #ccc;
            overflow: hidden;
        }

        .modal-header {
            background-color: #fafafa;
            border-bottom: 1px solid #e1e1e1;
            padding: 10px 15px;
            font-size: 12px;
            font-weight: bold;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .modal-close-btn {
            background: none;
            border: none;
            font-size: 16px;
            cursor: pointer;
            color: #666;
        }

        .modal-body {
            padding: 15px;
            font-size: 11px;
            color: #333;
            line-height: 1.6;
            max-height: 300px;
            overflow-y: auto;
            white-space: pre-wrap;
        }
    </style>
</head>
<body>

    <div class="ticketlink-container">
        
        <!-- 1. 공통 상단 타이틀 -->
        <header class="booking-header">
            <div class="logo-area">
                ticket<span class="logo-bold">LINK</span> 예매
            </div>
            <div class="timer-area">
                <span>예매가능 시간</span>
                <span id="countdown" class="timer-time">07:33</span>
                <span class="question-badge">?</span>
            </div>
        </header>

        <!-- 2. 단계 가이드바 -->
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
            	<!-- 구장 이미지 넣을 장소 -->
                <div class="stadium-border-box">
                    
					<img src="#void">
                    
                </div>

                <div class="side-panel">
                    <div class="side-top-info">
                        <div class="match-card">
                            <div class="team-box">
                                <div class="team-logo-circle bg-landers">롯데</div>
                                <span class="team-name-label">${step3Away}</span>
                            </div>
                            <span class="vs-text">VS</span>
                            <div class="team-box">
                                <div class="team-logo-circle bg-twins">KIA</div>
                                <span class="team-name-label">${step3Home}</span>
                            </div>
                        </div>

                        <div class="class-select-header">
                            <span class="class-title">등급 선택</span>
                            <button class="btn-refresh">새로고침</button>
                        </div>

                        <div class="seats-scroll-box">
                            <div class="seats-list-item"><span>전체</span><span></span></div>
                            <div class="seats-list-item" id="list-first" onclick="mockSeatSelect('1루 자유석')">
                                <div class="seat-name-group"><span class="square-indicator chip-blue"></span><span>1루 자유석</span></div>
                                <span class="seat-count">1 석</span>
                            </div>
                        </div>
                    </div>

                    <button class="next-step-btn" id="btn-next-step2" onclick="transitionToStep3()">다음단계</button>
                </div>
            </div>

            <!-- ======================================================= -->
            <!-- [VIEW STATE 3: 권종/할인/매수선택화면] -->
            <!-- ======================================================= -->
            <div id="view-step-3" class="view-section hidden">
                <div class="ticket-selection-box">
                    <div class="selection-title">티켓종류, 할인, 매수선택</div>
                    <div class="selection-alert-bar">
                        <span id="selected-seat-alert">${seatGrade}을 1매를 선택하셨습니다.</span>
                    </div>

                    <div class="ticket-table-wrapper">
                        <table class="ticket-table">
                            <tbody>
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
                                <tr>
                                    <td class="category-cell" rowspan="2">카드할인</td>
                                    <td class="ticket-name-cell">KJ광주카드</td>
                                    <td class="ticket-price-cell">13,500원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="13500" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="ticket-name-cell">현대카드</td>
                                    <td class="ticket-price-cell">13,500원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="13500" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="category-cell" rowspan="6">일반할인</td>
                                    <td class="ticket-name-cell">군인 <span class="help-icon">?</span></td>
                                    <td class="ticket-price-cell">11,200원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="11200" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="ticket-name-cell">유공자 <span class="help-icon">?</span></td>
                                    <td class="ticket-price-cell">8,000원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="8000" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="ticket-name-cell">대중교통 <span class="help-icon">?</span></td>
                                    <td class="ticket-price-cell">15,000원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="15000" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="ticket-name-cell">장애인 <span class="help-icon">?</span></td>
                                    <td class="ticket-price-cell">8,000원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="8000" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="ticket-name-cell">경로우대 <span class="help-icon">?</span></td>
                                    <td class="ticket-price-cell">8,000원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="8000" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="ticket-name-cell">임동주민 <span class="help-icon">?</span></td>
                                    <td class="ticket-price-cell">8,000원</td>
                                    <td class="ticket-select-cell">
                                        <select class="dropdown-select ticket-qty" data-price="8000" onchange="calculateInvoice()">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                        </select>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="notice-container">
                        <h5>안내사항</h5>
                        <p>※ 티켓 부정거래 적발시 별도의 사전 통보나 소명 절차 없이 강제 예매 취소 처리가 가능하며, 이 경우 취소 수수료가 부과됩니다.</p>
                        <p style="margin-top: 4px;">※ 일부 좌석은 구단 사정(행사 등)에 따라 예매 오픈 전에 배정될 수 있습니다.</p>
                    </div>
                </div>

                <div class="side-panel">
                    <div class="side-top-info">
                        <div class="match-card">
                            <div class="team-box">
                                <div class="team-logo-circle bg-lotte">롯데</div>
                                <span class="team-name-label">${step3Away}</span>
                            </div>
                            <span class="vs-text">VS</span>
                            <div class="team-box">
                                <div class="team-logo-circle bg-kia">KIA</div>
                                <span class="team-name-label">${step3Home}</span>
                            </div>
                        </div>

                        <div style="font-size: 11px; font-weight: bold; margin-bottom: 12px; line-height: 1.4;">
                            <div style="font-size: 12px;">${step3Away} vs <span style="background-color:#000; color:#fff; padding:1px 3px; border-radius:2px;">H</span> ${step3Home}</div>
                            <div style="color: #666; margin-top: 2px;">${stadiumName}</div>
                            <div style="color: #444;">${matchDate}</div>
                        </div>

                        <div class="booking-summary-box">
                            <div class="summary-box-title">예매정보</div>
                            <div class="summary-box-content">
                                <span class="summary-grade">${seatGrade}</span>
                                <span class="summary-detail">${seatDetail}</span>
                            </div>
                        </div>

                        <table class="invoice-table">
                            <tbody>
                                <tr><td>티켓금액</td><td class="invoice-val" id="invoice-ticket">0원</td></tr>
                                <tr><td>예매수수료</td><td class="invoice-val" id="invoice-fee">0원</td></tr>
                                <tr><td>배송료</td><td class="invoice-val">0원</td></tr>
                                <tr><td>쿠폰할인</td><td class="invoice-val">0원</td></tr>
                                <tr><td>포인트사용</td><td class="invoice-val">0원</td></tr>
                                <tr><td>총결제</td><td class="invoice-val total-red" id="invoice-total">0원</td></tr>
                            </tbody>
                        </table>

                        <div class="cancel-guide-txt">
                            취소기한: 2026.06.02 14:30<br>
                            취소수수료: 티켓금액의 0~30% <span style="text-decoration: underline; cursor:pointer;">[상세보기]</span>
                        </div>
                    </div>

                    <div class="btn-group">
                        <button class="btn-action btn-prev" onclick="transitionToStep2()">이전단계</button>
                        <button class="btn-action btn-next" id="btn-to-step4" onclick="transitionToStep4()">다음단계</button>
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
                                <td><input type="text" value="${userName}" class="step4-input" style="font-weight: bold;"></td>
                                <td class="step4-label-cell">휴대폰 번호 *</td>
                                <td><input type="text" value="${userPhone}" class="step4-input" style="font-weight: bold;"></td>
                            </tr>
                            <tr>
                                <td class="step4-label-cell">이메일</td>
                                <td colspan="3"><input type="text" value="${userEmail}" class="step4-input-full"></td>
                            </tr>
                        </tbody>
                    </table>

                    <h4 class="step4-section-title">예매자 확인</h4>
                    <!-- 예매자 체크 동의 사항 (상세보기 연계 클릭 지원) -->
                    <div class="consent-list-container">
                        <div class="consent-item">
                            <div class="consent-left">
                                <input type="checkbox" id="check-consent-1" checked>
                                <span>티켓 예매 확인, 취소 처리 및 배송을 위한 개인정보를 수집·이용합니다.</span>
                            </div>
                            <span class="link-detail" onclick="openDetailModal(1)">[상세보기]</span>
                        </div>
                        <div class="consent-item">
                            <div class="consent-left">
                                <input type="checkbox" id="check-consent-2">
                                <span>(선택) 예매 안내 메일 발송을 위해 이메일 정보를 수집·이용합니다.</span>
                            </div>
                            <span class="link-detail" onclick="openDetailModal(2)">[상세보기]</span>
                        </div>
                        <div class="consent-item">
                            <div class="consent-left">
                                <input type="checkbox" id="check-consent-3" checked>
                                <span>개인정보 제 3자 제공에 동의합니다. (고객응대 및 관람정보안내 등을 위한)</span>
                            </div>
                            <span class="link-detail" onclick="openDetailModal(3)">[상세보기]</span>
                        </div>
                        <div class="consent-item">
                            <div class="consent-left">
                                <input type="checkbox" id="check-consent-4" checked>
                                <span>KBO리그 SAFE캠페인에 동의합니다.</span>
                            </div>
                            <span class="link-detail" onclick="openDetailModal(4)">[상세보기]</span>
                        </div>
                        <div class="consent-item">
                            <div class="consent-left">
                                <input type="checkbox" id="check-consent-5" checked>
                                <span>프로스포츠 암표매매 행위에 따른 제재사항에 동의합니다.</span>
                            </div>
                            <span class="link-detail" onclick="openDetailModal(5)">[상세보기]</span>
                        </div>
                    </div>
                </div>

                <!-- 4단계 우측 요약 정산 바 -->
                <div class="side-panel">
                    <div class="side-top-info">
                        <div class="match-card">
                            <div class="team-box">
                                <div class="team-logo-circle bg-lotte">롯데</div>
                                <span class="team-name-label">${step3Away}</span>
                            </div>
                            <span class="vs-text">VS</span>
                            <div class="team-box">
                                <div class="team-logo-circle bg-kia">KIA</div>
                                <span class="team-name-label">${step3Home}</span>
                            </div>
                        </div>

                        <div style="font-size: 11px; font-weight: bold; margin-bottom: 12px; line-height: 1.4;">
                            <div style="font-size: 12px;">${step3Away} vs <span style="background-color:#000; color:#fff; padding:1px 3px; border-radius:2px;">H</span> ${step3Home}</div>
                            <div style="color: #666; margin-top: 2px;">${stadiumName}</div>
                            <div style="color: #444;">${matchDate}</div>
                        </div>

                        <div class="booking-summary-box">
                            <div class="summary-box-title">예매정보</div>
                            <div class="summary-box-content">
                                <span class="summary-grade">${seatGrade}</span>
                                <span class="summary-detail">${seatDetail}</span>
                            </div>
                        </div>

                        <!-- 4단계 영수증 종합 리스트 -->
                        <table class="invoice-table">
                            <tbody>
                                <tr><td>티켓금액</td><td class="invoice-val" id="invoice-ticket-step4">0원</td></tr>
                                <tr><td>할인금액</td><td class="invoice-val">0원</td></tr>
                                <tr><td>포인트 사용</td><td class="invoice-val">0원</td></tr>
                                <tr><td>총결제</td><td class="invoice-val total-red" id="invoice-total-step4">0원</td></tr>
                            </tbody>
                        </table>

                        <!-- 결제 수단 선택 영역 -->
                        <div style="margin-top: 15px; border-top: 1px solid #e1e1e1; padding-top: 10px;">
                            <span style="font-size:11px; font-weight:bold; color:#666; display:block; margin-bottom:6px;">결제수단 선택</span>
                            <label style="font-size:11px; font-weight:bold; display:inline-flex; align-items:center; gap:4px; cursor:pointer;">
                                <input type="radio" name="payment-method-group" checked> toss 결제
                            </label>
                        </div>
                    </div>

                    <!-- 4단계 하단 액션 버튼 그룹 -->
                    <div class="btn-group">
                        <button class="btn-action btn-prev" onclick="transitionToStep3FromStep4()">이전단계</button>
                        <button class="btn-action btn-next btn-pay" onclick="triggerFinalPayment()">결제하기</button>
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
            <div class="modal-body" id="modal-text">
                내용이 표시됩니다.
            </div>
            <div class="modal-footer" style="padding: 10px 15px; background-color:#fafafa; text-align:right; border-top:1px solid #e1e1e1;">
                <button class="btn-action btn-prev" style="height:28px; width:60px; font-size:11px;" onclick="closeDetailModal()">닫기</button>
            </div>
        </div>
    </div>

    <!-- 가변 상태 흐름 제어 및 정산 수량 계산 스크립트 -->
    <script>
        let selectActive = false; 
        let calculatedTicketPrice = 0;
        let calculatedFee = 0;
        let calculatedTotal = 0;

        // 2단계: 좌석 임시 지정
        function mockSeatSelect(zoneName) {
            const listFirst = document.getElementById('list-first');
            listFirst.classList.add('selected');
            selectActive = true;
            alert(zoneName + " [1루 자유석] 좌석이 임시 선택되었습니다.");
        }

        // 2단계 -> 3단계 전환
        function transitionToStep3() {
            if (!selectActive) {
                alert("선택할 수 있는 좌석 등급을 먼저 터치하여 지정해주세요.");
                return;
            }
            document.getElementById('step-nav-2').classList.remove('active');
            document.getElementById('step-nav-3').classList.add('active');
            document.getElementById('view-step-2').classList.add('hidden');
            document.getElementById('view-step-3').classList.remove('hidden');
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

        // 최종 결제 실행 검증기
        function triggerFinalPayment() {
            const check1 = document.getElementById('check-consent-1').checked;
            const check3 = document.getElementById('check-consent-3').checked;
            const check4 = document.getElementById('check-consent-4').checked;
            const check5 = document.getElementById('check-consent-5').checked;

            if (!check1 || !check3 || !check4 || !check5) {
                alert("필수동의 항목에 동의하셔야 예매를 완수하실 수 있습니다.");
                return;
            }

            alert("toss 결제 시스템창이 호출됩니다.\n최종 결제 금액: " + calculatedTotal.toLocaleString() + "원");
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

        // 제한시간 타이머 기동 (7분 33초 기준)
        document.addEventListener("DOMContentLoaded", () => {
            let totalSeconds = 453; 
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
                    alert("선택 시간이 만료되었습니다.");
                }
            };

            const countdownInterval = setInterval(updateTimer, 1000);
        });
    </script>
</body>
</html>
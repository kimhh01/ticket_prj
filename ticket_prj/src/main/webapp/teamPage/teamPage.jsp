<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSTL 태그 라이브러리 추가 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<%-- 구단 코드별 공식 홈페이지 URL 설정 (선택된 tDTO 기준) --%>
<c:choose>
    <c:when test="${tDTO.teamCode == 1}">
        <c:set var="teamUrl" value="https://www.lgtwins.com" />
        <c:set var="team_logo" value="lg.png"/>
    </c:when>
    <c:when test="${tDTO.teamCode == 2}">
        <c:set var="teamUrl" value="https://www.doosanbears.com/bears/intro" />
        <c:set var="team_logo" value="doosan.png"/>
    </c:when>
    <c:when test="${tDTO.teamCode == 3}">
        <c:set var="teamUrl" value="https://www.hanwhaeagles.co.kr/MN/CL/MNCLCI01.do" />
        <c:set var="team_logo" value="hanwha.png"/>
    </c:when>
    <c:when test="${tDTO.teamCode == 4}">
        <c:set var="teamUrl" value="https://www.giantsclub.com/html/?pcode=855" />
        <c:set var="team_logo" value="lotte.png"/>
    </c:when>
    <c:when test="${tDTO.teamCode == 5}">
        <c:set var="teamUrl" value="https://tigers.co.kr/tigers/intro" />
        <c:set var="team_logo" value="kia.png"/>
    </c:when>
    <c:when test="${tDTO.teamCode == 6}">
        <c:set var="teamUrl" value="https://www.ncdinos.com/dinos/intro.do" />
        <c:set var="team_logo" value="nc.png"/>
    </c:when>
    <c:when test="${tDTO.teamCode == 7}">
        <c:set var="teamUrl" value="https://www.ssglanders.com/main" />
        <c:set var="team_logo" value="ssg.png"/>
    </c:when>
    <c:when test="${tDTO.teamCode == 8}">
        <c:set var="teamUrl" value="https://www.samsunglions.com/index.asp" />
        <c:set var="team_logo" value="samsung.png"/>
    </c:when>
    <c:when test="${tDTO.teamCode == 9}">
        <c:set var="teamUrl" value="https://heroesbaseball.co.kr/heroes/introduce/introduce.do" />
        <c:set var="team_logo" value="kium.png"/>
    </c:when>
    <c:when test="${tDTO.teamCode == 10}">
        <c:set var="teamUrl" value="https://www.ktwiz.co.kr/ktwiz/about" />
        <c:set var="team_logo" value="kt.png"/>
    </c:when>
    <c:otherwise>
        <c:set var="teamUrl" value="http://localhost/ticket_prj/main" />
    </c:otherwise>
</c:choose>

<%-- 오늘 날짜 가져오기 (예매오픈 예정 비교용) --%>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>티켓링크 - 야구 예매</title>
<style>
    /* 티켓링크 아이덴티티를 살린 깔끔한 레이아웃 */
    body {
        font-family: 'Malgun Gothic', sans-serif;
        background-color: #f8f9fa;
        margin: 0;
        padding: 0;
        color: #333;
    }
    
    /* 구단 헤더 배너 */
    .team-info {
        background: linear-gradient(135deg, #1c1c1e 0%, #2c2c2e 100%);
        color: #fff;
        padding: 35px 0;
    }
    .team-inner {
        max-width: 1000px;
        margin: 0 auto;
        display: flex;
        align-items: center;
        gap: 25px;
        padding: 0 20px;
    }
    .team-logo img {
        width: 90px;
        height: auto;
        background: #fff;
        border-radius: 50%;
        padding: 8px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.3);
    }
    .team-name h1 {
        margin: 0 0 12px 0;
        font-size: 26px;
        font-weight: 700;
    }
    .team-btns {
        display: flex;
        gap: 8px;
        position: relative;
    }
    .team-btns button {
        background: rgba(255,255,255,0.15);
        border: 1px solid rgba(255,255,255,0.25);
        color: #fff;
        padding: 8px 14px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 13px;
        transition: all 0.2s;
    }
    .team-btns button:hover {
        background: #fff;
        color: #333;
    }
    
    /* 공지사항 한줄 배너 */
    .notice-box {
        max-width: 1000px;
        margin: 20px auto;
        background: #fff;
        border: 1px solid #e1e4e6;
        border-radius: 6px;
        display: flex;
        align-items: center;
        padding: 12px 20px;
        box-sizing: border-box;
    }
    .notice-title {
        font-weight: bold;
        color: #f1404b;
        border-right: 1px solid #e1e4e6;
        padding-right: 20px;
        margin-right: 20px;
        flex-shrink: 0;
    }
    .notice-content ul {
        margin: 0;
        padding-left: 20px;
        font-size: 13px;
        color: #666;
        line-height: 1.5;
    }
    #notice-blue {
        color: #0066ff;
        font-weight: bold;
    }

    /* 탭 메뉴 */
    .tabs-wrap {
        border-bottom: 2px solid #e1e4e6;
        background: #fff;
    }
    .tabs {
        max-width: 1000px;
        margin: 0 auto;
        display: flex;
    }
    .tab {
        padding: 15px 30px;
        cursor: pointer;
        font-size: 16px;
        font-weight: 600;
        color: #666;
        text-align: center;
        border-bottom: 3px solid transparent;
        transition: all 0.15s;
    }
    .tab:hover {
        color: #333;
    }
    .tab.active {
        color: #f1404b;
        border-bottom-color: #f1404b;
    }

    /* 컨텐츠 래퍼 */
    .tab-content {
        display: none;
    }
    .tab-content.active {
        display: block;
    }

    /* 필터 영역 */
    .filter-list {
        max-width: 1000px;
        margin: 20px auto 10px auto;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 10px;
    }
    .filter-checkbox {
        font-size: 14px;
        color: #444;
        cursor: pointer;
    }
    .calendar-btn {
        background: none;
        border: 1px solid #ccc;
        border-radius: 4px;
        padding: 5px 10px;
        cursor: pointer;
    }

    /* 경기 리스트 */
    .game-wrap {
        max-width: 1000px;
        margin: 0 auto 50px auto;
        display: flex;
        flex-direction: column;
        gap: 12px;
        padding: 0 10px;
        box-sizing: border-box;
    }
    .game-row {
        background: #fff;
        border: 1px solid #e1e4e6;
        border-radius: 6px;
        display: grid;
        grid-template-columns: 140px 160px 1fr 180px 140px;
        align-items: center;
        padding: 18px 20px;
        box-shadow: 0 1px 3px rgba(0,0,0,0.02);
        transition: transform 0.15s, box-shadow 0.15s;
        box-sizing: border-box;
    }
    .game-row:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 10px rgba(0,0,0,0.06);
    }
    
    .game-date {
        text-align: center;
        border-right: 1px solid #eee;
    }
    .game-date .day {
        font-size: 21px;
        font-weight: bold;
        color: #333;
    }
    .game-date .time {
        font-size: 13px;
        color: #888;
        margin-top: 3px;
    }

    .game-team-img {
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 8px;
        font-size: 13px;
        color: #bbb;
    }
    .game-team-img img {
        width: 40px;
        height: 40px;
        object-fit: contain;
    }

    .game-team-name {
        padding-left: 15px;
    }
    .game-flag {
        margin-bottom: 4px;
    }
    .flag-clean {
        background: #eef9f2;
        color: #2b8a3e;
        border: 1px solid #c3e6cb;
        padding: 2px 6px;
        font-size: 11px;
        border-radius: 3px;
        font-weight: bold;
    }
    .game-team-name span {
        font-size: 17px;
        font-weight: bold;
    }

    .game-title {
        color: #666;
        font-size: 13px;
    }

    .game-btn {
        text-align: right;
    }
    .reserve-btn {
        background: #f1404b;
        color: #fff;
        border: none;
        padding: 9px 20px;
        font-size: 14px;
        font-weight: bold;
        border-radius: 4px;
        cursor: pointer;
        width: 100%;
        transition: background 0.2s;
    }
    .reserve-btn:hover {
        background: #d32f2f;
    }
    .coming-btn {
        background: #e1e4e6;
        color: #888;
        border: none;
        padding: 9px 10px;
        font-size: 13px;
        font-weight: bold;
        border-radius: 4px;
        cursor: not-allowed;
        width: 100%;
    }

    /* 팝업 공통 */
    .common_popup_wrap, .cancel_popup_wrap {
        display: none;
        position: absolute;
        top: 42px;
        left: 0;
        z-index: 100;
        width: 310px;
        background: #fff;
        border: 1px solid #ccc;
        border-radius: 6px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        color: #333;
        text-align: left;
    }
    .common_popup {
        padding: 15px;
    }
    .common_popup_header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: 1px solid #eee;
        padding-bottom: 8px;
        margin-bottom: 10px;
    }
    .common_popup_title {
        margin: 0;
        font-size: 13px;
        color: #333;
    }
    .common_popup_close {
        background: none;
        border: none;
        font-size: 15px;
        cursor: pointer;
    }
    .popup_desc {
        font-size: 12px;
        line-height: 1.5;
        margin: 5px 0;
    }
    .text_medium {
        font-weight: bold;
        color: #2b8a3e;
    }
    .text_gray {
        color: #666;
    }

    /* [공지사항 탭] */
    .notice-detail-wrap {
        max-width: 1000px;
        margin: 30px auto 50px auto;
        background: #fff;
        border: 1px solid #e1e4e6;
        border-radius: 6px;
        padding: 35px;
        box-sizing: border-box;
    }
    .notice-detail-header {
        border-bottom: 2px solid #333;
        padding-bottom: 15px;
        margin-bottom: 25px;
    }
    .notice-badge {
        background: #f1404b;
        color: #fff;
        font-size: 11px;
        font-weight: bold;
        padding: 3px 8px;
        border-radius: 3px;
        vertical-align: middle;
        margin-right: 8px;
    }
    .notice-detail-header h2 {
        display: inline-block;
        margin: 0;
        font-size: 21px;
        font-weight: bold;
        vertical-align: middle;
        color: #222;
    }
    .notice-meta {
        font-size: 13px;
        color: #888;
        margin-top: 8px;
    }
    .notice-detail-body {
        font-size: 14px;
        color: #444;
        line-height: 1.7;
    }
    .notice-img-box {
        text-align: center;
        margin-bottom: 25px;
    }
    .notice-img-box img {
        max-width: 100%;
        height: auto;
        border-radius: 4px;
        border: 1px solid #eee;
    }
    .notice-text-box {
        max-width: 800px;
        margin: 0 auto;
    }
    .notice-warning {
        background: #fff5f5;
        border-left: 4px solid #f1404b;
        padding: 15px;
        color: #c92a2a;
        font-size: 13px;
        margin-top: 25px;
        border-radius: 0 4px 4px 0;
        font-weight: bold;
    }

    /* [리그안내 탭] */
    .league-guide-wrap {
        max-width: 1000px;
        margin: 30px auto 50px auto;
        background: #fff;
        border: 1px solid #e1e4e6;
        border-radius: 6px;
        padding: 35px;
        box-sizing: border-box;
    }
    .guide-section {
        margin-bottom: 40px;
    }
    .guide-section:last-child {
        margin-bottom: 0;
    }
    .guide-section h3 {
        font-size: 19px;
        margin-top: 0;
        margin-bottom: 10px;
        color: #222;
        border-left: 4px solid #f1404b;
        padding-left: 10px;
    }
    .guide-subtitle {
        font-size: 13px;
        color: #666;
        margin-bottom: 15px;
    }
    
    /* 표(Table) 스타일 */
    .info-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 15px;
        font-size: 13px;
        text-align: center;
    }
    .info-table th {
        background: #f5f6f7;
        color: #333;
        font-weight: 600;
        padding: 10px;
        border-top: 1px solid #333;
        border-bottom: 1px solid #e1e4e6;
    }
    .info-table td {
        padding: 11px;
        border-bottom: 1px solid #eee;
        color: #555;
    }
    
    /* 요금 표 컬러링 */
    .seat-vip { font-weight: bold; color: #a61e4d; }
    .seat-table { font-weight: bold; color: #d9480f; }
    .seat-exc { font-weight: bold; color: #0b7285; }
    .seat-blue { font-weight: bold; color: #1971c2; }
    .seat-red { font-weight: bold; color: #e03131; }
    .seat-navy { font-weight: bold; color: #373a3c; }
    .seat-out { font-weight: bold; color: #2b8a3e; }

    .policy-box {
        background: #f8f9fa;
        border: 1px solid #e1e4e6;
        border-radius: 6px;
        padding: 20px;
    }
    .policy-box ul {
        margin: 0;
        padding-left: 18px;
        font-size: 13px;
        color: #555;
        line-height: 1.7;
    }
    .policy-box li {
        margin-bottom: 10px;
    }
    .policy-box li:last-child {
        margin-bottom: 0;
    }
</style>
<script type="text/javascript">
$(function(){
    // 1. JS 기반 무중단 탭 전환 이벤트 구현
    $(".tab").click(function(){
        $(".tab").removeClass("active");
        $(this).addClass("active");
        
        var targetId = $(this).data("target");
        $(".tab-content").removeClass("active");
        $("#" + targetId).addClass("active");
    });

    // 구단 소개 및 가이드 외부 이동 (JSTL 변수 사용)
    $(".introduceBtn, .guideBtn").click(function(){
        window.open("${teamUrl}");
    });

    // 클린예매 팝업 토글
    $(".cleanBtn").click(function(e){
        e.stopPropagation();
        $(".cancel_popup_wrap").slideUp(100); 
        $(".common_popup_wrap").slideToggle(150);
    });

    $("#closeClean").click(function(){
        $(".common_popup_wrap").slideUp(150);
    });
    
    // 취소표대기 팝업 토글
    $(".cancelBtn").click(function(e){
        e.stopPropagation();
        $(".common_popup_wrap").slideUp(100); 
        $(".cancel_popup_wrap").slideToggle(150);
    });

    $("#closeCancel").click(function(){
        $(".cancel_popup_wrap").slideUp(150);
    });

    // 바깥 영역 클릭 시 팝업 닫기
    $(document).click(function(){
        $(".common_popup_wrap, .cancel_popup_wrap").slideUp(150);
    });

    // 팝업 내부 클릭 시 닫힘 현상 방지
    $(".common_popup_wrap, .cancel_popup_wrap").click(function(e){
        e.stopPropagation();
    });

    // 홈경기만 보기 필터
    $(".onlyHomeCheck").change(function(){
        if($(this).is(":checked")) {
            $(".game-row.away-game").hide();
        } else {
            $(".game-row").show();
        }
    });
});
</script>
</head>
<body>
<jsp:include page="../include/header.jsp"/>

<c:if test="${not empty tDTO}">
<section class="team-info">
    <div class="team-inner">
        <div class="team-logo">
            <%-- DB에서 읽어온 구단 로고 이미지 (경로가 DB에 담겨있어야 정상 작동) --%>
            <img src="${pageContext.request.contextPath}/${team_logo}" onerror="this.src='${pageContext.request.contextPath}/images/team_logo/${team_logo}'"/>
        </div>
        <div class="team-name">
            <h1>${tDTO.teamHomeName}</h1>
            <div class="team-btns">
                <button class="cleanBtn">클린예매</button>
                <button class="cancelBtn">취소표대기</button>
                <button class="guideBtn">예매가이드</button>
                <button class="introduceBtn">구단소개</button>
                
                <!-- 클린예매 팝업 -->                
                <div class="common_popup_wrap">
                    <div class="common_popup">
                        <div class="common_popup_header">
                            <h3 class="common_popup_title">클린예매 서비스 안내</h3>
                            <button type="button" id="closeClean" class="common_popup_close">✕</button>
                        </div>
                        <div class="common_popup_content">
                            <p class="popup_desc">
                                <span class="text_medium">이 상품은 보안문자 입력 후 좌석 선택이 가능합니다.</span>
                            </p>
                            <p class="popup_desc">
                                <span class="text_gray">
                                    · 대/소문자 구분 없음<br>
                                    · 혼동하기 쉬운 문자 제외<br>
                                    · 보안문자 입력 후 좌석 선택 가능
                                </span>
                            </p>
                        </div>
                     </div>
                </div>
                
                <!-- 취소표대기 팝업 -->
                <div class="cancel_popup_wrap">
                    <div class="common_popup">
                        <div class="common_popup_header">
                            <h3 class="common_popup_title">취소표대기 서비스 안내</h3>
                            <button type="button" id="closeCancel" class="common_popup_close">✕</button>
                        </div>
                        <div class="common_popup_content">
                            <p class="popup_desc">
                                <span class="text_medium">취소된 좌석 발생 시 자동으로 예매를 시도합니다.</span>
                            </p>
                            <p class="popup_desc">
                                <span class="text_gray">
                                    · 원하는 경기 선택 후 신청 가능<br>
                                    · 취소표 발생 시 순차 배정<br>
                                    · 결제 실패 시 자동 취소<br>
                                    · 경기 시작 전까지만 신청 가능
                                </span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
         </div>
    </div>
</section>
</c:if>

<section class="notice-box">
    <div class="notice-title">공지사항</div>
    <div class="notice-content">
        <ul>
            <li><span id="notice-blue">보안문자 입력 후 예매 가능합니다.</span></li>
            <li>36개월 이상 어린이는 티켓 구매 후 입장 가능합니다.</li>
            <li>경기 시작 4시간 전부터 취소 불가입니다.</li>
        </ul>
    </div>
</section>

<!-- 탭 메뉴 -->
<div class="tabs-wrap">
    <div class="tabs">
        <div class="tab active" data-target="tab-reservation">예매하기</div>
        <div class="tab" data-target="tab-notice">공지사항</div>
        <div class="tab" data-target="tab-league">리그안내</div>
    </div>
</div>

<%-- ==========================================================================
     [탭 1] 예매하기 컨텐츠 영역 (EL / JSTL 루프 적용)
     ========================================================================== --%>
<div id="tab-reservation" class="tab-content active">
    <div class="filter-list">
        <label class="filter-checkbox">
            <input type="checkbox" class="onlyHomeCheck">
            홈경기만 보기
        </label>
        <button type="button" class="calendar-btn" title="달력형 보기">📅</button>
    </div>

    <section class="game-wrap">
        <c:choose>
            <c:when test="${not empty gameList}">
                <c:forEach var="game" items="${gameList}">
                    <%-- 1. 홈경기 여부 판별 (배너 구단명과 경기의 홈팀명 일치 여부 비교) --%>
                    <c:set var="isHome" value="${not empty tDTO and game.teamHomeName eq tDTO.teamHomeName}" />
                    
                    <%-- 2. 경기 시간이 오늘보다 이후인지 비교 (예매예정 vs 예매하기) --%>
                    <c:set var="isUpcoming" value="${game.gameDate.time > now.time}" />
                    
                    <div class="game-row ${isHome ? 'home-game' : 'away-game'}">
                        <div class="game-date">
                           <span class="day">
                               <fmt:formatDate value="${game.gameDate}" pattern="MM.dd"/>
                           </span>
                           <div class="time">
                               <fmt:formatDate value="${game.gameDate}" pattern="(E) HH:mm"/>
                           </div>
                        </div>

                        <div class="game-team-img">
                            <img src="${pageContext.request.contextPath}/${game.teamHomeImg}" onerror="this.src='${pageContext.request.contextPath}/images/team_logo/${game.teamHomeImg}'"/>
                            vs 
                            <img src="${pageContext.request.contextPath}/${game.teamOtherImg}" onerror="this.src='${pageContext.request.contextPath}/images/team_logo/${game.teamOtherImg}'"/>
                        </div>
                        
                        <div class="game-team-name">
                             <div class="game-flag">
                                <span class="flag-clean">클린예매</span>
                             </div>
                             <span>${game.teamHomeName} VS ${game.teamOtherName}</span>
                        </div>

                        <div class="game-title">
                            ${game.stadiumName}
                        </div>

                        <div class="game-btn">
                            <c:choose>
                                <c:when test="${not isUpcoming}">
                                    <button class="reserve-btn">예매하기</button>
                                </c:when>
                                <c:otherwise>
                                    <button class="coming-btn" disabled>예매오픈 예정</button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div style="text-align:center; padding: 50px 0; color: #888;">예정된 경기가 없습니다.</div>
            </c:otherwise>
        </c:choose>
    </section>
</div>

<%-- ==========================================================================
     [탭 2] 공지사항 컨텐츠 영역
     ========================================================================== --%>
<div id="tab-notice" class="tab-content">
    <section class="notice-detail-wrap">
    
    <c:forEach var="notice" items="${noticeList}">
        <div class="notice-detail-header">
            <span class="notice-badge">공지</span>
            
            <h2></h2>
            <div class="notice-meta">
                <span>${notice.noticeTitle }</span> | <span>${notice.noticeWriteDate} </span><span class="notice-btn">ㅇ</span>
            </div>
        </div>
        
        <div class="notice-detail-body">
            <div class="notice-text-box">
                <p>안녕하세요. 티켓링크입니다.</p>
                <p>2026 시즌 KBO 프로야구 정규리그의 원활한 티켓 예매 서비스를 위해 아래와 같이 공식 이용 방침 및 예매 일정을 공지하오니 이용에 참고해 주시기 바랍니다.</p>
                
                <h4 style="margin-top: 30px; font-size: 15px; color: #222;">📌 1. 온라인 예매 오픈 일정</h4>
                <table class="info-table">
                    <thead>
                        <tr>
                            <th>대상 구분</th>
                            <th>오픈 일시</th>
                            <th>예매 한도</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><strong>선예매 (구단 멤버십 회원)</strong></td>
                            <td>경기 8일 전 오전 11:00</td>
                            <td>회원당 최대 2매</td>
                        </tr>
                        <tr>
                            <td><strong>일반 예매 (전체 회원)</strong></td>
                            <td>경기 7일 전 오전 11:00</td>
                            <td>회원당 최대 4매</td>
                        </tr>
                    </tbody>
                </table>
                
                <h4 style="margin-top: 30px; font-size: 15px; color: #222;">📌 2. 클린예매 도입 안내</h4>
                <p>매크로 프로그램 등을 이용한 비정상적인 티켓 예매를 근절하기 위해, 본 구단의 모든 경기는 <strong>[클린예매]</strong> 단계가 적용됩니다. 예매 시 화면에 나타나는 임의의 보안 문자를 올바르게 입력하셔야 좌석 선택 화면으로 진입할 수 있습니다.</p>
                
                <div class="notice-warning">
                    ⚠️ 불법적인 수단(매크로, 대리 티켓팅 등)을 통해 강제 예매를 시도하거나 부정 티켓 거래 적발 시, 예매 티켓은 사전 안내 없이 일괄 취소 처리되며 관련 법률에 따라 제재를 받을 수 있습니다.
                </div>
            </div>
        </div>
        </c:forEach>
    </section>
</div>

<%-- ==========================================================================
     [탭 3] 리그안내 컨텐츠 영역
     ========================================================================== --%>
<div id="tab-league" class="tab-content">
    <section class="league-guide-wrap">
        <!-- 입장 요금 섹션 -->
        <div class="guide-section">
            <h3>🎟️ 홈경기 입장 요금 안내</h3>
            <p class="guide-subtitle">관람 요금은 구장 구역과 주중/주말 구분에 따라 차등 적용됩니다.</p>
            
            <table class="info-table">
                <thead>
                    <tr>
                        <th>좌석 등급</th>
                        <th>주중 요금 (화 ~ 목)</th>
                        <th>주말 및 공휴일 요금 (금 ~ 일)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="seat-vip">VIP석</td>
                        <td>60,000 원</td>
                        <td>70,000 원</td>
                    </tr>
                    <tr>
                        <td class="seat-table">테이블석 (2인 기준)</td>
                        <td>80,000 원</td>
                        <td>90,000 원</td>
                    </tr>
                    <tr>
                        <td class="seat-exc">익사이팅존</td>
                        <td>25,000 원</td>
                        <td>30,000 원</td>
                    </tr>
                    <tr>
                        <td class="seat-blue">블루석 (지정석)</td>
                        <td>17,000 원</td>
                        <td>20,000 원</td>
                    </tr>
                    <tr>
                        <td class="seat-red">레드석 (응원 지정석)</td>
                        <td>14,000 원</td>
                        <td>16,000 원</td>
                    </tr>
                    <tr>
                        <td class="seat-navy">네이비석 (일반지정석)</td>
                        <td>12,000 원</td>
                        <td>14,000 원</td>
                    </tr>
                    <tr>
                        <td class="seat-out">외야 자유석</td>
                        <td>8,000 원</td>
                        <td>10,000 원</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- 취소 환불 정책 섹션 -->
        <div class="guide-section">
            <h3>⚠️ 티켓 취소 및 환불 규정</h3>
            <p class="guide-subtitle">예매하신 티켓의 취소 수수료와 환불 기본 규정입니다.</p>
            
            <div class="policy-box">
                <ul>
                    <li><strong>예매 취소 마감 시한:</strong> 해당 경기 시작 4시간 전까지 취소 및 환불이 가능합니다. 이후에는 온라인 취소가 절대 불가능합니다.</li>
                    <li><strong>취소 수수료 안내:</strong>
                        <ul>
                            <li><strong>예매 당일 취소 시:</strong> 별도의 취소 수수료가 부과되지 않습니다.</li>
                            <li><strong>예매 다음 날 ~ 경기 전날 취소 시:</strong> 티켓 금액의 10%가 수수료로 공제됩니다.</li>
                            <li><strong>경기 당일 취소 시 (경기 시작 4시간 전까지):</strong> 티켓 금액의 10%가 수수료로 공제됩니다.</li>
                        </ul>
                    </li>
                    <li><strong>우천 취소 규정:</strong>
                        <ul>
                            <li>경기 시작 전 우천 취소가 확정될 경우, 승인은 자동으로 카드사 영업일 기준 3~5일 이내 일괄 취소됩니다.</li>
                            <li>페이코 등 간편결제수단 역시 등록된 수단으로 자동 환불됩니다.</li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </section>
</div>
<%-- 
<footer class="footer-wrap">
   <jsp:include page="../include/footer.jsp"/>
</footer> --%>

</body>
</html>
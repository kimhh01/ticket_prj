<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSTL 태그 라이브러리 추가 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<%-- 구단 코드별 공식 홈페이지 URL 설정 (선택된 tDTO 기준) --%>
<c:choose>
    <c:when test="${tDTO.teamCode == 1}">
        <c:set var="teamUrl" value="https://www.lgtwins.com" />
    </c:when>
    <c:when test="${tDTO.teamCode == 2}">
        <c:set var="teamUrl" value="https://www.doosanbears.com/bears/intro" />
    </c:when>
    <c:when test="${tDTO.teamCode == 3}">
        <c:set var="teamUrl" value="https://www.hanwhaeagles.co.kr/MN/CL/MNCLCI01.do" />
    </c:when>
    <c:when test="${tDTO.teamCode == 4}">
        <c:set var="teamUrl" value="https://www.giantsclub.com/html/?pcode=855" />
    </c:when>
    <c:when test="${tDTO.teamCode == 5}">
        <c:set var="teamUrl" value="https://tigers.co.kr/tigers/intro" />
    </c:when>
    <c:when test="${tDTO.teamCode == 6}">
        <c:set var="teamUrl" value="https://www.ncdinos.com/dinos/intro.do" />
    </c:when>
    <c:when test="${tDTO.teamCode == 7}">
        <c:set var="teamUrl" value="https://www.ssglanders.com/main" />
    </c:when>
    <c:when test="${tDTO.teamCode == 8}">
        <c:set var="teamUrl" value="https://www.samsunglions.com/index.asp" />
        <c:set var="team_logo" value="samsung.png"/>
    </c:when>
    <c:when test="${tDTO.teamCode == 9}">
        <c:set var="teamUrl" value="https://heroesbaseball.co.kr/heroes/introduce/introduce.do" />
    </c:when>
    <c:when test="${tDTO.teamCode == 10}">
        <c:set var="teamUrl" value="https://www.ktwiz.co.kr/ktwiz/about" />
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/teamPage/teamPage.css">
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
        window.open("");
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
    
    //공지사항 팝업
    $(".notice-meta").click(function(){
        var body = $(this).closest(".notice-detail-header")
                          .next(".notice-detail-body");
        $(this).toggleClass("active");
        body.toggleClass("active");
    });

    // 홈경기만 보기 필터
    $(".onlyHomeCheck").change(function(){
        if($(this).is(":checked")) {
            $(".game-row.away-game").hide();
        } else {
            $(".game-row").show();
        }
    });
       
    //예매하기 버튼 클릭시 예매창으로 이동
    $(".reserve-btn").click(function(){
        var gameScheduleCode = $(this).data("game");
        alert(gameScheduleCode);   // 추가
        window.open(
            "<%=request.getContextPath()%>/reservationPageServlet?gameScheduleCode=" + gameScheduleCode,
            "reservation",
            "width=1200,height=800,top=100,left=200,resizable=no"
        );
    });
});
</script>
</head>
<body>
<jsp:include page="../fragment/header.jsp"/>

<c:if test="${not empty tDTO}">
<section class="team-info">
    <div class="team-inner">
        <div class="team-logo">
            <%-- DB에서 읽어온 구단 로고 이미지 (경로가 DB에 담겨있어야 정상 작동) --%>
            <img src="${pageContext.request.contextPath}/images/team_logo/${tDTO.teamHomeImg}">
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
                            <img src="${pageContext.request.contextPath}/images/team_logo/${game.teamHomeImg}">
                            vs 
                            <img src="${pageContext.request.contextPath}/images/team_logo/${game.teamOtherImg}">
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
                                    <button class="reserve-btn" data-game="${game.gameScheduleCode}">예매하기</button>
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
            
            <article class="notice-item" data-category="reservation">
            <div class="notice-meta">
                <span>${notice.noticeTitle }</span> | <span>${notice.noticeWriteDate} </span><span class="notice-btn">ㅇ</span>
            </div>
            </article>
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
       
    </section>
</div> 
<footer class="footer-wrap">
   <jsp:include page="../fragment/footer.jsp"/>
</footer> 

</body>
</html>
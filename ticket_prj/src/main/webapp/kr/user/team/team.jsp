<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- JSTL 태그 라이브러리 추가 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<%-- 구단 코드별 공식 홈페이지 URL 설정 (선택된 tDTO 기준) --%>
<c:choose>
	<c:when test="${tDTO.teamCode == 1}">
		<c:set var="teamUrl" value="https://www.lgtwins.com" />
	</c:when>
	<c:when test="${tDTO.teamCode == 2}">
		<c:set var="teamUrl" value="http://www.doosanbears.com/bears/intro" />
	</c:when>
	<c:when test="${tDTO.teamCode == 3}">
		<c:set var="teamUrl"
			value="https://www.hanwhaeagles.co.kr/MN/CL/MNCLCI01.do" />
	</c:when>
	<c:when test="${tDTO.teamCode == 4}">
		<c:set var="teamUrl"
			value="https://www.giantsclub.com/html/?pcode=855" />
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
		<c:set var="team_logo" value="samsung.png" />
	</c:when>
	<c:when test="${tDTO.teamCode == 9}">
		<c:set var="teamUrl"
			value="https://heroesbaseball.co.kr/heroes/introduce/introduce.do" />
	</c:when>
	<c:when test="${tDTO.teamCode == 10}">
		<c:set var="teamUrl" value="https://www.ktwiz.co.kr/ktwiz/about" />
	</c:when>
	<c:otherwise>
		<c:set var="teamUrl" value="${pageContext.request.contextPath}/main" />
	</c:otherwise>
</c:choose>

<%-- 오늘 날짜 가져오기 (예매오픈 예정 비교용) --%>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>BallPick - 예매</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/kr/user/team/team.css">
<script type="text/javascript">
	$(function() {

		var contextPath = "${pageContext.request.contextPath}";
		var urlParams = new URLSearchParams(window.location.search);
		var currentView = urlParams.get('view');

		if (currentView === 'calendar') {
			// 달력 보기 상태 유지
			$(".game-wrap").hide();
			$(".calendar-wrap").show();
			$(".calendar-btn").hide();
			$(".list-btn").show();
		} else {
			// 기본 리스트 보기 상태
			$(".calendar-wrap").hide();
			$(".game-wrap").show();
			$(".list-btn").hide();
			$(".calendar-btn").show();
		}

		// 1. JS 기반 무중단 탭 전환 이벤트 구현
		$(".tab").click(function() {
			$(".tab").removeClass("active");
			$(this).addClass("active");

			var targetId = $(this).data("target");
			$(".tab-content").removeClass("active");
			$("#" + targetId).addClass("active");
		});

		//예매 가이드 사이트 새 창으로 여는 함수
		$(".guideBtn").click(
				function() {
					window.open(contextPath
							+ "/kr/user/reservationGuide/reservationGuide.jsp",
							"_blank", "width=900,height=700");
				});

		//구단 사이트로 이동하는 새 창을 여는 함수
		$(".introduceBtn").click(function() {
			window.open("${teamUrl}", "teamHome");
		});

		// 클린예매 팝업 토글
		$(".cleanBtn").click(function(e) {
			e.stopPropagation();
			$(".cancel_popup_wrap").slideUp(100);
			$(".common_popup_wrap").slideToggle(150);
		});

		$("#closeClean").click(function() {
			$(".common_popup_wrap").slideUp(150);
		});

		// 취소표대기 팝업 토글
		$(".cancelBtn").click(function(e) {
			e.stopPropagation();
			$(".common_popup_wrap").slideUp(100);
			$(".cancel_popup_wrap").slideToggle(150);
		});

		$("#closeCancel").click(function() {
			$(".cancel_popup_wrap").slideUp(150);
		});

		// 바깥 영역 클릭 시 팝업 닫기
		$(document).click(function() {
			$(".common_popup_wrap, .cancel_popup_wrap").slideUp(150);
		});

		// 팝업 내부 클릭 시 닫힘 현상 방지
		$(".common_popup_wrap, .cancel_popup_wrap").click(function(e) {
			e.stopPropagation();
		});

		//공지사항 팝업
		$(".notice-meta").click(
				function() {
					var body = $(this).closest(".notice-detail-header").next(
							".notice-detail-body");
					$(this).toggleClass("active");
					body.toggleClass("active");
				});

		// 홈경기만 보기 필터
		// 공통 필터링 및 "결과 없음" 처리 함수
    function updateDisplay() {
       var isFilterChecked = $(".onlyHomeCheck").is(":checked");
       var isCalendarMode = $(".calendar-wrap").is(":visible");

       if (isFilterChecked) {
           // 1. 모든 원정 경기 숨기기
           $(".game-row.away-game").hide();
           $(".calendar-game-item.away-game").hide();

           if (isCalendarMode) {
               // [달력 모드일 때]
               $(".no-home-list").hide(); // 리스트용 메시지는 무조건 숨김
               
               var homeGameInCal = $(".calendar-game-item.home-game").length;
               if (homeGameInCal === 0) {
                   $(".calendar-table").hide();
                   $(".no-home-game").show();
               } else {
                   $(".calendar-table").show();
                   $(".no-home-game").hide();
               }
           } else {
               // [리스트 모드일 때]
               $(".no-home-game").hide(); // 달력용 메시지는 무조건 숨김
               
               var homeGameInList = $(".game-row.home-game").length;
               if (homeGameInList === 0) {
                   $(".game-wrap").hide();
                   $(".no-home-list").show();
               } else {
                   $(".game-wrap").show();
                   $(".no-home-list").hide();
               }
           }
       } else {
           // [필터 해제 상태]
           $(".game-row").show();
           $(".calendar-game-item").show();
           $(".calendar-table").show();
           $(".game-wrap").toggle(!isCalendarMode); // 달력모드가 아니면 리스트 보여줌
           $(".calendar-wrap").toggle(isCalendarMode); // 달력모드면 달력 보여줌
           
           $(".no-home-list").hide();
           $(".no-home-game").hide();
       }
    }

    // 홈경기만 보기 필터 체크박스 변경 시
    $(".onlyHomeCheck").change(function() {
        updateDisplay();
    });

    // 달력 버튼 클릭 시
    $(".calendar-btn").click(function() {
        $(".game-wrap").hide();
        $(".calendar-wrap").show();
        $(".calendar-btn").hide();
        $(".list-btn").show();
        updateDisplay(); // 뷰 전환 후 상태 업데이트
    });

    // 리스트 버튼 클릭 시
    $(".list-btn").click(function() {
        $(".calendar-wrap").hide();
        $(".game-wrap").show();
        $(".list-btn").hide();
        $(".calendar-btn").show();
        updateDisplay(); // 뷰 전환 후 상태 업데이트
    });

    // 페이지 로드 시 초기 상태 반영
    updateDisplay();
		

	//스크롤 위치 저장
	var savedScrollY = localStorage.getItem("calendarScrollPos");
	if (savedScrollY) {
		window.scrollTo(0, savedScrollY);
		localStorage.removeItem("calendarScrollPos"); // 이동 후 삭제
	}

	//달력의 이전/다음 버튼 클릭 시 현재 스크롤 위치 저장
	$(".calendar-header a").click(function() {
		localStorage.setItem("calendarScrollPos", window.scrollY);
	});

	// 예매하기 버튼 클릭시 예매창으로 이동 (맵핑 주소 정합 완료)
	$(".reserve-btn").click(
		function() {
		var gameScheduleCode = $(this).data("game");
		window.open(contextPath
			+ "/reservation?gameScheduleCode="
			+ gameScheduleCode, "reservation",
			"width=1200,height=800,top=100,left=200,resizable=no");
	});
});
</script>
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />

	<c:if test="${not empty tDTO}">
		<section class="team-info">
			<div class="team-inner">
				<div class="team-logo">
					<img
						src="${pageContext.request.contextPath}/images/team_logo/${tDTO.teamHomeImg}"
						alt="구단로고">
				</div>
				<div class="team-name">
					<h1>${tDTO.teamHomeName}</h1>
					<div class="team-btns">
						<button type="button" class="cleanBtn">클린예매</button>
						<button type="button" class="guideBtn">예매가이드</button>
						<button type="button" class="introduceBtn">구단소개</button>

						<div class="common_popup_wrap">
							<div class="common_popup">
								<div class="common_popup_header">
									<h3 class="common_popup_title">클린예매 서비스 안내</h3>
									<button type="button" id="closeClean"
										class="common_popup_close" style="color: #333">✕</button>
								</div>
								<div class="common_popup_content">
									<p class="popup_desc">
										<span class="text_medium">이 상품은 보안문자 입력 후 좌석 선택이 가능합니다.</span>
									</p>
									<p class="popup_desc">
										<span class="text_gray"> · 대/소문자 구분<br> · 혼동하기
											쉬운 문자 제외<br> · 보안문자 입력 후 좌석 선택 가능
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

	<div class="tabs-wrap">
		<div class="tabs">
			<div class="tab active" data-target="tab-reservation">예매하기</div>
			<div class="tab" data-target="tab-notice">공지사항</div>
			<div class="tab" data-target="tab-league">리그안내</div>
		</div>
	</div>

	<%-- [탭 1] 예매하기 컨텐츠 영역 --%>
	<div id="tab-reservation" class="tab-content active">
		<div class="filter-list">
			<label class="filter-checkbox"> <input type="checkbox"
				class="onlyHomeCheck"> 홈경기만 보기
			</label>
			<button type="button" class="calendar-btn" title="달력형 보기"
				style="border: none;">
				<img
					src="<%=request.getContextPath()%>/images/teamPage/calendar-icon.png"
					style="border: none;">
			</button>
			<button type="button" class="list-btn"
				style="display: none;">
				<img
					src="<%=request.getContextPath()%>/images/teamPage/list-icon.png"
					style="border: none;">
			</button>
		</div>

		<section class="game-wrap">
			<c:choose>
				<c:when test="${not empty gameList}">
					<c:forEach var="game" items="${gameList}">
						<c:set var="isHome"
							value="${not empty tDTO and game.teamHomeName eq tDTO.teamHomeName}" />
						<c:set var="isUpcoming" value="${game.gameDate.time >= now.time}" />

						<div class="game-row ${isHome ? 'home-game' : 'away-game'}">
							<div class="game-date">
								<span class="day"> <fmt:formatDate
										value="${game.gameDate}" pattern="MM.dd" />
								</span>
								<div class="time">${game.gameStartTime }</div>
							</div>

							<div class="game-team-img">
								<img
									src="${pageContext.request.contextPath}/images/team_logo/${game.teamHomeImg}"
									alt="홈팀"> vs <img
									src="${pageContext.request.contextPath}/images/team_logo/${game.teamOtherImg}"
									alt="원정팀">
							</div>

							<div class="game-team-name">
								<div class="game-flag">
									<span class="flag-clean">클린예매</span>
								</div>
								<span>${game.teamHomeName} VS ${game.teamOtherName}</span>
							</div>

							<div class="game-title">${game.stadiumName}</div>

							<div class="game-btn">
								<c:choose>
									<c:when test="${game.reservationOpen}">
										<button class="reserve-btn"
											data-game="${game.gameScheduleCode}">예매하기</button>
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
					<div style="text-align: center; padding: 50px 0; color: #888;">예정된
						경기가 없습니다.</div>
				</c:otherwise>
			</c:choose>
		</section>
		<div class="no-home-list" style="display:none; text-align: center; padding: 50px 0; color: #888;">
		    홈경기가 없습니다.
		</div>
		<section class="calendar-wrap">
			<div class="calendar-header">
				<%-- 이전 달 이동 (끝에 &view=calendar 추가) --%>
				<a
					href="${pageContext.request.contextPath}/team?teamCode=${tDTO.teamCode}&year=${month==1?year-1:year}&month=${month==1?12:month-1}&view=calendar">◀</a>

				<h2>${year}년${month}월</h2>

				<%-- 다음 달 이동 (끝에 &view=calendar 추가) --%>
				<a
					href="${pageContext.request.contextPath}/team?teamCode=${tDTO.teamCode}&year=${month==12?year+1:year}&month=${month==12?1:month+1}&view=calendar">▶</a>
			</div>

			<table class="calendar-table">
				<thead>
					<tr>
						<th class="sun">일</th>
						<th>월</th>
						<th>화</th>
						<th>수</th>
						<th>목</th>
						<th>금</th>
						<th class="sat">토</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="day" value="1" />
					<c:forEach begin="1" end="6" var="row">
						<c:if test="${day <= lastDay}">
							<%-- 마지막 날짜 이후 행 생성 방지 --%>
							<tr>
								<c:forEach begin="1" end="7" var="week">
									<c:choose>
										<%-- 시작일 전 빈칸 --%>
										<c:when test="${day == 1 && week < firstDay}">
											<td class="calendar-cell empty"></td>
										</c:when>
										<%-- 마지막 날 이후 빈칸 --%>
										<c:when test="${day > lastDay}">
											<td class="calendar-cell empty"></td>
										</c:when>
										<%-- 날짜 표시 --%>
										<c:otherwise>
											<td class="calendar-cell">
												<div class="calendar-date">${day}</div> <%-- 해당 날짜 경기 필터링 --%>
												<c:forEach var="game" items="${gameList}">
													<%-- 경기 날짜의 년, 월, 일을 추출하여 비교 --%>
													<fmt:formatDate value="${game.gameDate}" pattern="yyyy"
														var="gYear" />
													<fmt:formatDate value="${game.gameDate}" pattern="M"
														var="gMonth" />
													<fmt:formatDate value="${game.gameDate}" pattern="d"
														var="gDay" />

													<c:if
														test="${gYear == year && gMonth == month && gDay == day}">
														<c:set var="isHome"
															    value="${not empty tDTO and game.teamHomeName eq tDTO.teamHomeName}" />
															
															<div class="calendar-game-item ${isHome ? 'home-game' : 'away-game'}">
															<div class="calendar-match">
																<img
																	src="${pageContext.request.contextPath}/images/team_logo/${game.teamHomeImg}"
																	class="calendar-logo" alt="Home"> <span
																	class="vs-text">VS</span> <img
																	src="${pageContext.request.contextPath}/images/team_logo/${game.teamOtherImg}"
																	class="calendar-logo" alt="Away">
															</div>
															<div class="calendar-time">${game.gameStartTime}</div>
															<div class="calendar-btn-area">
																<c:choose>
																	<c:when test="${game.reservationOpen}">
																		<button type="button" class="reserve-btn"
																			data-game="${game.gameScheduleCode}">예매</button>
																	</c:when>
																	<c:otherwise>
																		<button type="button" class="coming-btn" disabled>예정</button>
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
													</c:if>
												</c:forEach>
											</td>
											<c:set var="day" value="${day + 1}" />
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
			<div class="no-home-game" style="display:none; text-align: center; padding: 50px 0; color: #888;">
			    홈경기가 없습니다.
			</div>
		</section>
	</div>


	<div id="tab-notice" class="tab-content">
		<section class="notice-detail-wrap">
			<c:forEach var="notice" items="${noticeList}">
				<div class="notice-detail-header">
					<span class="notice-badge">공지</span>
					<article class="notice-item" data-category="reservation">
						<div class="notice-meta">
							<span>${notice.noticeTitle}</span> | <span>${notice.noticeWriteDate}
							</span>
						</div>
					</article>
				</div>

				<div class="notice-detail-body">
					<img src="">
				</div>
			</c:forEach>
		</section>
	</div>

	<%-- [탭 3] 리그안내 컨텐츠 영역 --%>
	<div id="tab-league" class="tab-content">
		<section class="league-guide-wrap">
			<div>
				<img src="${pageContext.request.contextPath}/images/notice/${leagueList[0]}">
				<img src="${pageContext.request.contextPath}/images/notice/${leagueList[1]}">
				<img src="${pageContext.request.contextPath}/images/notice/${leagueList[2]}">
			</div>
		</section>
	</div>

	<footer class="footer-wrap">
		<jsp:include page="/fragment/footer.jsp" />
	</footer>
</body>
</html>
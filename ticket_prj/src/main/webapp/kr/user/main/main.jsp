<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="kr.user.main.TeamRankDTO"%>
<%@page import="kr.user.main.MainGameDTO"%>
<%@page import="kr.user.main.MainBannerDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
// View인 main.jsp를 직접 실행하면 DB 조회를 담당하는 MainServlet으로 이동한다.
String mainRequestPath = request.getRequestURI().substring(request.getContextPath().length());
boolean forwardedByServlet = request.getAttribute("javax.servlet.forward.request_uri") != null;
if (!forwardedByServlet && "/kr/user/main/main.jsp".equals(mainRequestPath)) {
	response.sendRedirect(request.getContextPath() + "/main");
	return;
}
%>

<title>BallPick | 야구 예매</title>

<%@ include file="/fragment/header.jsp"%>

<%!@SuppressWarnings("unchecked")
	private <T> List<T> getListAttribute(HttpServletRequest request, String attributeName) {
		Object value = request.getAttribute(attributeName);
		return value == null ? null : (List<T>) value;
	}

	private String getTeamLogoFile(int teamCode, String logoFile, String teamName) {
		String name = teamName == null ? "" : teamName.toLowerCase(Locale.KOREAN).replace(" ", "");

		if (name.contains("lg"))
			return "lg.png";
		if (name.contains("두산") || name.contains("doosan"))
			return "doosan.png";
		if (name.contains("한화") || name.contains("hanwha") || name.contains("hanhwa"))
			return "hanwha.png";
		if (name.contains("롯데") || name.contains("lotte"))
			return "lotte.png";
		if (name.contains("kia") || name.contains("기아"))
			return "kia.png";
		if (name.contains("nc"))
			return "nc.png";
		if (name.contains("ssg"))
			return "ssg.png";
		if (name.contains("삼성") || name.contains("samsung"))
			return "samsung.png";
		if (name.contains("키움") || name.contains("kiwoom") || name.contains("kium"))
			return "kium.png";
		if (name.contains("kt"))
			return "kt.png";

		switch (teamCode) {
			case 1 :
				return "lg.png";
			case 2 :
				return "doosan.png";
			case 3 :
				return "hanwha.png";
			case 4 :
				return "lotte.png";
			case 5 :
				return "kia.png";
			case 6 :
				return "nc.png";
			case 7 :
				return "ssg.png";
			case 8 :
				return "samsung.png";
			case 9 :
				return "kium.png";
			case 10 :
				return "kt.png";
			default :
				return logoFile == null ? "" : logoFile;
		}
	}%>

<%
/*
    MainServlet에서 request에 담아 보낸 데이터를 꺼낸다.

    request.setAttribute("bannerList", bannerList);
    request.setAttribute("gameList", gameList);
    request.setAttribute("rankList", rankList);

    위처럼 Servlet에서 넣었기 때문에 JSP에서는 getAttribute로 꺼낸다.
*/
List<MainBannerDTO> bannerList = getListAttribute(request, "bannerList");
List<MainGameDTO> gameList = getListAttribute(request, "gameList");
List<TeamRankDTO> rankList = getListAttribute(request, "rankList");

// 경기 날짜를 보기 좋게 출력하기 위한 날짜 포맷
SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREAN);
%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/kr/user/main/main.css">

<main class="main-wrap">

	<!-- ===================== 메인 배너 슬라이드 ===================== -->
	<section class="hero-section">

		<%
		if (bannerList == null || bannerList.isEmpty()) {
		%>
		<!-- 배너 데이터가 없을 때 기본 배너 -->
		<div class="hero-slider">
			<div class="hero-slide active no-image">
				<div class="hero-copy">
					<span class="badge">BALLPICK</span>
					<h1>
						오늘의 야구 예매를<br>한 번에 확인하세요
					</h1>
					<p>경기 일정, 팀 순위, 예매 정보를 빠르게 확인할 수 있습니다.</p>
					<span class="hero-button">예매하러 가기</span>
				</div>
			</div>
		</div>
		<%
		} else {
		%>
		<!-- 배너 데이터가 있을 때 슬라이드 출력 -->
		<div class="hero-slider" id="heroSlider">

			<%
			for (int i = 0; i < bannerList.size(); i++) {
				MainBannerDTO banner = bannerList.get(i);
			%>
			<div class="hero-slide <%=i == 0 ? "active" : ""%>">
				<a
					href="<%=request.getContextPath()%>/team?teamCode=<%=banner.getTeamCode()%>">

					<img
					src="<%=request.getContextPath()%>/images/banner/<%=banner.getBannerImg()%>"
					alt="메인 배너"
					onerror="this.style.display='none'; this.parentElement.parentElement.classList.add('no-image');">

				</a>
			</div>
			<%
			}
			%>

			<!-- 배너 하단 점 버튼 -->
			<div class="hero-dots">
				<%
				for (int i = 0; i < bannerList.size(); i++) {
				%>
				<span class="hero-dot <%=i == 0 ? "active" : ""%>"
					data-index="<%=i%>"></span>
				<%
				}
				%>
			</div>

		</div>
		<%
		}
		%>

	</section>

	<!-- ===================== 최근 경기 ===================== -->
	<section class="game-section">
		<h2 class="section-title">최근 경기</h2>

		<%
		if (gameList == null || gameList.isEmpty()) {
		%>
		<div class="empty-box">등록된 경기 일정이 없습니다.</div>
		<%
		} else {
		%>
		<div class="game-list">
			<%
			for (int i = 0; i < gameList.size(); i++) {
				MainGameDTO game = gameList.get(i);
			%>
			<article class="game-card">
				<div class="team-match">
					<div class="team-box">
						<img class="team-logo"
							src="<%=request.getContextPath()%>/images/team_logo/<%=getTeamLogoFile(game.getHomeTeamCode(), game.getHomeTeamLogo(), game.getHomeTeamName())%>"
							alt="<%=game.getHomeTeamName()%>"
							onerror="this.style.display='none';">
						<div class="team-name"><%=game.getHomeTeamName()%></div>
					</div>

					<div class="vs-text">VS</div>

					<div class="team-box">
						<img class="team-logo"
							src="<%=request.getContextPath()%>/images/team_logo/<%=getTeamLogoFile(game.getAwayTeamCode(), game.getAwayTeamLogo(), game.getAwayTeamName())%>"
							alt="<%=game.getAwayTeamName()%>"
							onerror="this.style.display='none';">
						<div class="team-name"><%=game.getAwayTeamName()%></div>
					</div>
				</div>

				<div class="game-info">
					<div class="game-date">
						<%=game.getGameDate() != null ? sdf.format(game.getGameDate()) : "경기일 미정"%>
						<%=game.getGameStartTime() != null ? " " + game.getGameStartTime() : ""%>
					</div>
					<div class="game-stadium"><%=game.getStadiumName()%></div>
				</div>

				<%
				boolean canReserve = "판매중".equals(game.getSaleStatus());
				%>
				<%
				if (canReserve) {
				%>
				<a class="reserve-btn"
					href="<%=request.getContextPath()%>/reservation?gameScheduleCode=<%=game.getGameScheduleCode()%>">
					예매하기 </a>
				<%
				} else {
				%>
				<span class="reserve-btn wait">예매대기</span>
				<%
				}
				%>
			</article>
			<%
			}
			%>
		</div>
		<%
		}
		%>
	</section>
	<!-- ===================== 팀 순위 ===================== -->
	<section class="rank-section">
		<div class="rank-box" id="rankSlider">
			<%
			if (rankList == null || rankList.isEmpty()) {
			%>
			<div class="rank-empty">등록된 팀 순위가 없습니다.</div>
			<%
			} else {
			%>
			<div class="rank-slider">
				<div class="rank-slider-track" id="rankSliderTrack">
					<%
					for (int rankIndex = 0; rankIndex < rankList.size(); rankIndex++) {
						TeamRankDTO rank = rankList.get(rankIndex);
						String rankLogo = getTeamLogoFile(rank.getTeamCode(), rank.getTeamLogo(), rank.getTeamName());
						String winRateText = String.format("%.3f", rank.getWinRate());
						String scoreGapText = rank.getScoreGap() == Math.rint(rank.getScoreGap())
						? String.format("%.0f", rank.getScoreGap())
						: String.format("%.1f", rank.getScoreGap());
						String seasonYear = new SimpleDateFormat("yyyy")
						.format(rank.getRankUpdateDate() != null ? rank.getRankUpdateDate() : new java.util.Date());
					%>
					<div class="rank-slide">
						<div class="rank-team-panel">
							<div class="rank-logo-wrap">
								<img
									src="<%=request.getContextPath()%>/images/team_logo/<%=rankLogo%>"
									alt="<%=rank.getTeamName()%>"
									onerror="this.onerror=null; this.style.display='none';">
							</div>
							<div class="rank-team-copy">
								<label class="rank-team-select-wrap"> <span>팀 선택</span>
									<select class="rank-team-select" aria-label="팀 선택">
										<%
										for (int teamIndex = 0; teamIndex < rankList.size(); teamIndex++) {
											TeamRankDTO teamOption = rankList.get(teamIndex);
										%>
										<option value="<%=teamIndex%>"
											<%=teamIndex == rankIndex ? "selected" : ""%>><%=teamOption.getTeamName()%></option>
										<%
										}
										%>
								</select>
								</label>
								<div class="rank-team-name"><%=rank.getTeamName()%></div>
							</div>
						</div>

						<div class="rank-season-panel">
							<div class="rank-season-title"><%=seasonYear%>
								시즌
							</div>
							<div class="rank-stat-grid">
								<div class="rank-stat-item">
									<div class="rank-stat-label">순위</div>
									<div class="rank-stat-value"><%=rank.getRankNo()%></div>
								</div>
								<div class="rank-stat-item">
									<div class="rank-stat-label">승</div>
									<div class="rank-stat-value"><%=rank.getWin()%></div>
								</div>
								<div class="rank-stat-item">
									<div class="rank-stat-label">무</div>
									<div class="rank-stat-value"><%=rank.getDraw()%></div>
								</div>
								<div class="rank-stat-item">
									<div class="rank-stat-label">패</div>
									<div class="rank-stat-value"><%=rank.getLose()%></div>
								</div>
								<div class="rank-stat-item">
									<div class="rank-stat-label">경기 수</div>
									<div class="rank-stat-value"><%=rank.getGameCount()%></div>
								</div>
								<div class="rank-stat-item">
									<div class="rank-stat-label">승률</div>
									<div class="rank-stat-value"><%=winRateText%></div>
								</div>
								<div class="rank-stat-item">
									<div class="rank-stat-label">승차</div>
									<div class="rank-stat-value"><%=scoreGapText%></div>
								</div>
							</div>
						</div>
					</div>
					<%
					}
					%>
				</div>
			</div>
			<%
			}
			%>
		</div>
	</section>

	<!-- ===================== 이벤트 이미지 배너 ===================== -->
	<section class="event-section" id="event">
		<h2 class="section-title">이벤트</h2>

		<div class="event-list">

			<!-- 이벤트 1 : 신규 회원 이벤트 -->
			<div class="event-banner">
				<a href="<%=request.getContextPath()%>/user_event/eventDetail_1.jsp">
					<img
					src="<%=request.getContextPath()%>/images/event/event1_banner.png"
					alt="신규 회원 이벤트"
					onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
					<div class="event-empty" style="display: none;">신규 회원 이벤트 이미지</div>
				</a>
			</div>

			<!-- 이벤트 2 : 구단 할인 이벤트 -->
			<div class="event-banner">
				<a href="<%=request.getContextPath()%>/user_event/eventDetail_2.jsp">
					<img
					src="<%=request.getContextPath()%>/images/event/event2_banner.png"
					alt="구단 할인 이벤트"
					onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
					<div class="event-empty" style="display: none;">구단 할인 이벤트 이미지</div>
				</a>
			</div>

		</div>
	</section>
</main>

<script src="<%=request.getContextPath()%>/kr/user/main/main.js"></script>

<%@ include file="/fragment/footer.jsp"%>

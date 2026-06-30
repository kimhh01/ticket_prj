<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="kr.user.main.TeamRankDTO"%>
<%@page import="kr.user.main.MainGameDTO"%>
<%@page import="kr.user.main.MainBannerDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/fragment/header.jsp"%>

<%!
@SuppressWarnings("unchecked")
private <T> List<T> getListAttribute(HttpServletRequest request, String attributeName) {
	Object value = request.getAttribute(attributeName);
	return value == null ? null : (List<T>) value;
}

private String getTeamLogoFile(int teamCode, String logoFile, String teamName) {
	String name = teamName == null ? "" : teamName.toLowerCase(Locale.KOREAN).replace(" ", "");

	if (name.contains("lg")) return "lg.png";
	if (name.contains("두산") || name.contains("doosan")) return "doosan.png";
	if (name.contains("한화") || name.contains("hanwha") || name.contains("hanhwa")) return "hanwha.png";
	if (name.contains("롯데") || name.contains("lotte")) return "lotte.png";
	if (name.contains("kia") || name.contains("기아")) return "kia.png";
	if (name.contains("nc")) return "nc.png";
	if (name.contains("ssg")) return "ssg.png";
	if (name.contains("삼성") || name.contains("samsung")) return "samsung.png";
	if (name.contains("키움") || name.contains("kiwoom") || name.contains("kium")) return "kium.png";
	if (name.contains("kt")) return "kt.png";

	switch (teamCode) {
	case 1: return "lg.png";
	case 2: return "doosan.png";
	case 3: return "hanwha.png";
	case 4: return "lotte.png";
	case 5: return "kia.png";
	case 6: return "nc.png";
	case 7: return "ssg.png";
	case 8: return "samsung.png";
	case 9: return "kium.png";
	case 10: return "kt.png";
	default: return logoFile == null ? "" : logoFile;
	}
}
%>

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

<style>
/* ===================== 전체 메인 영역 ===================== */
.main-wrap {
	width: calc(100% - 32px);
	max-width: 1180px;
	margin: 0 auto;
	padding: 34px 0 70px;
	font-family: Arial, '맑은 고딕', sans-serif;
	color: #222;
}

.section-title {
	font-size: 22px;
	font-weight: 800;
	margin: 0 0 18px;
}

/* ===================== 메인 배너 슬라이드 ===================== */
.hero-section {
	margin-bottom: 48px;
}

.hero-slider {
	position: relative;
	height: 360px;
	border-radius: 22px;
	overflow: hidden;
	background: linear-gradient(135deg, #101936 0%, #d71920 100%);
	box-shadow: 0 14px 34px rgba(0, 0, 0, 0.16);
}

.hero-slide {
	display: none;
	position: absolute;
	width: 100%;
	height: 100%;
	left: 0;
	top: 0;
}

.hero-slide.active {
	display: block;
}

.hero-slide a {
	display: block;
	width: 100%;
	height: 100%;
	text-decoration: none;
	color: white;
}

.hero-slide img {
	width: 100%;
	height: 100%;
	object-fit: cover;
	display: block;
}

/* 배너 이미지가 아직 없을 때 배경색으로 대체 */
.hero-slide.no-image {
	background: linear-gradient(135deg, #101936 0%, #d71920 100%);
}

.hero-copy {
	position: absolute;
	left: 54px;
	top: 70px;
	z-index: 2;
	color: white;
}

.hero-copy .badge {
	display: inline-block;
	padding: 8px 14px;
	border-radius: 999px;
	background: rgba(255, 255, 255, 0.18);
	font-size: 13px;
	font-weight: 700;
	margin-bottom: 18px;
}

.hero-copy h1 {
	margin: 0;
	font-size: 44px;
	line-height: 1.18;
	letter-spacing: -1px;
}

.hero-copy p {
	margin: 16px 0 0;
	font-size: 16px;
	opacity: 0.9;
}

.hero-button {
	display: inline-block;
	margin-top: 30px;
	padding: 13px 24px;
	border-radius: 999px;
	background: #fff;
	color: #d71920;
	font-size: 15px;
	font-weight: 800;
}

.hero-dots {
	position: absolute;
	right: 34px;
	bottom: 26px;
	z-index: 5;
}

.hero-dot {
	display: inline-block;
	width: 9px;
	height: 9px;
	margin-left: 7px;
	border-radius: 50%;
	background: rgba(255, 255, 255, 0.45);
	cursor: pointer;
}

.hero-dot.active {
	width: 24px;
	border-radius: 999px;
	background: #fff;
}

/* ===================== 최근 경기 ===================== */
.game-section {
	margin-bottom: 50px;
}

.game-list {
	display: flex;
	gap: 18px;
}

.game-card {
	flex: 1;
	min-height: 240px;
	padding: 24px 24px 22px;
	border: 1px solid #e5e5e5;
	border-radius: 8px;
	background: #fff;
	box-shadow: 0 8px 18px rgba(0, 0, 0, 0.05);
	transition: 0.2s;
	display: flex;
	flex-direction: column;
}

.game-card:hover {
	transform: translateY(-4px);
	box-shadow: 0 12px 24px rgba(0, 0, 0, 0.09);
}

.team-match {
	display: flex;
	align-items: flex-start;
	justify-content: space-between;
	gap: 12px;
	padding-bottom: 18px;
	border-bottom: 1px solid #eeeeee;
	margin-bottom: 14px;
}

.team-box {
	width: 40%;
	text-align: center;
}

.team-logo {
	width: 76px;
	height: 76px;
	object-fit: contain;
	margin-bottom: 8px;
}

.team-name {
	font-size: 15px;
	font-weight: 800;
	line-height: 1.35;
	word-break: keep-all;
}

.vs-text {
	width: 34px;
	height: 34px;
	border-radius: 50%;
	background: #f5f5f5;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 12px;
	font-weight: 900;
	color: #999;
	margin-top: 36px;
}

.game-info {
	text-align: center;
	margin-bottom: 20px;
}

.game-date {
	font-size: 15px;
	font-weight: 800;
	color: #222;
	margin-bottom: 8px;
}

.game-stadium {
	font-size: 13px;
	font-weight: 600;
	color: #666;
	line-height: 1.45;
	word-break: keep-all;
}

.reserve-btn {
	display: block;
	padding: 12px 0;
	border-radius: 6px;
	text-align: center;
	text-decoration: none;
	background: #d71920;
	color: white;
	font-weight: 800;
	font-size: 14px;
	margin-top: auto;
}

.reserve-btn.wait {
	background: #777;
}

/* ===================== 팀 순위 ===================== */
.rank-section {
	margin-bottom: 50px;
}

.rank-box {
	position: relative;
	height: 210px;
	border-radius: 0;
	background: radial-gradient(circle at 35% 50%, #2d2d2d 0%, #202020 38%, #171717 78%);
	color: #fff;
	overflow: hidden;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.rank-slider {
	height: 100%;
	overflow: hidden;
}

.rank-slider-track {
	height: 100%;
	transition: transform 0.55s ease-in-out;
}

.rank-slide {
	height: 210px;
	display: grid;
	grid-template-columns: 43% 57%;
	align-items: center;
	padding: 24px 48px 24px 30px;
	box-sizing: border-box;
	color: #fff;
}

.rank-team-panel {
	display: flex;
	align-items: center;
	gap: 30px;
	min-width: 0;
}

.rank-logo-wrap {
	width: 140px;
	height: 140px;
	border-radius: 50%;
	background: rgba(255, 255, 255, 0.07);
	display: flex;
	align-items: center;
	justify-content: center;
	flex: 0 0 auto;
}

.rank-logo-wrap img {
	width: 104px;
	height: 104px;
	object-fit: contain;
}

.rank-team-copy {
	min-width: 0;
}

.rank-team-select-wrap {
	position: relative;
	display: inline-flex;
	align-items: center;
	margin-bottom: 14px;
	padding-right: 18px;
	font-size: 13px;
	color: #e5e5e5;
	cursor: pointer;
}

.rank-team-select-wrap::after {
	content: "⌄";
	position: absolute;
	right: 1px;
	top: -1px;
	color: #aaa;
	pointer-events: none;
}

.rank-team-select-wrap span {
	color: #e5e5e5;
}

.rank-team-select {
	position: absolute;
	inset: 0;
	width: 100%;
	height: 100%;
	opacity: 0;
	cursor: pointer;
}

.rank-team-name {
	font-size: 32px;
	font-weight: 900;
	line-height: 1.1;
	letter-spacing: 0;
	color: #fff;
	word-break: keep-all;
}

.rank-season-panel {
	padding-left: 42px;
	border-left: 1px solid rgba(255, 255, 255, 0.1);
	min-width: 0;
}

.rank-season-title {
	position: relative;
	font-size: 17px;
	font-weight: 900;
	color: #ffd400;
	margin-bottom: 16px;
}

.rank-season-title::after {
	content: "";
	display: block;
	width: 9px;
	height: 2px;
	margin-top: 7px;
	background: #ffd400;
}

.rank-stat-grid {
	display: grid;
	grid-template-columns: repeat(4, minmax(0, 1fr));
	row-gap: 14px;
	column-gap: 22px;
}

.rank-stat-label {
	font-size: 11px;
	color: #aaa;
	margin-bottom: 5px;
}

.rank-stat-value {
	font-size: 19px;
	font-weight: 700;
	line-height: 1;
	color: #fff;
}

.rank-stat-date {
	font-size: 16px;
}

.rank-empty {
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
	color: #ccc;
}

@media (max-width: 760px) {
	.rank-box,
	.rank-slide {
		height: 350px;
	}

	.rank-slide {
		grid-template-columns: 1fr;
		align-content: center;
		gap: 18px;
		padding: 22px 24px;
	}

	.rank-team-panel {
		gap: 20px;
	}

	.rank-logo-wrap {
		width: 104px;
		height: 104px;
	}

	.rank-logo-wrap img {
		width: 76px;
		height: 76px;
	}

	.rank-team-name {
		font-size: 27px;
	}

	.rank-season-panel {
		padding: 16px 0 0;
		border-left: 0;
		border-top: 1px solid rgba(255, 255, 255, 0.1);
	}

	.rank-stat-grid {
		column-gap: 10px;
		row-gap: 13px;
	}

	.rank-stat-value {
		font-size: 17px;
	}

	.rank-stat-date {
		font-size: 13px;
	}
}

/* ===================== 이벤트 이미지 배너 ===================== */
.event-section {
	margin-top: 10px;
}

.event-list {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 20px;
}

.event-banner {
	height: 180px;
	border-radius: 18px;
	overflow: hidden;
	background: #fff;
	border: 1px solid #e5e5e5;
	box-shadow: 0 8px 18px rgba(0, 0, 0, 0.04);
}

.event-banner a {
	display: block;
	width: 100%;
	height: 100%;
	text-decoration: none;
}

.event-banner img {
	width: 100%;
	height: 100%;
	object-fit: contain;
	display: block;
	background: #fff;
}

.event-empty {
	width: 100%;
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
	color: #888;
	font-size: 15px;
	font-weight: 700;
	background: linear-gradient(135deg, #f7f7f7, #eeeeee);
}

/* ===================== 데이터 없을 때 ===================== */
.empty-box {
	border: 1px dashed #ccc;
	border-radius: 16px;
	padding: 60px 20px;
	text-align: center;
	color: #888;
	background: #fafafa;
}
</style>

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
					href="<%=request.getContextPath()%>/reservation/list?teamCode=<%=banner.getTeamCode()%>">

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
				<a class="reserve-btn <%=canReserve ? "" : "wait"%>"
					href="<%=request.getContextPath()%>/reservation/detail?gameScheduleCode=<%=game.getGameScheduleCode()%>">
					<%=canReserve ? "예매하기" : "예매대기"%>
				</a>
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
						String seasonYear = new SimpleDateFormat("yyyy").format(
								rank.getRankUpdateDate() != null ? rank.getRankUpdateDate() : new java.util.Date());
					%>
					<div class="rank-slide">
						<div class="rank-team-panel">
							<div class="rank-logo-wrap">
								<img src="<%=request.getContextPath()%>/images/team_logo/<%=rankLogo%>"
									alt="<%=rank.getTeamName()%>"
									onerror="this.onerror=null; this.style.display='none';">
							</div>
							<div class="rank-team-copy">
								<label class="rank-team-select-wrap">
									<span>팀 선택</span>
									<select class="rank-team-select" aria-label="팀 선택">
										<%
										for (int teamIndex = 0; teamIndex < rankList.size(); teamIndex++) {
											TeamRankDTO teamOption = rankList.get(teamIndex);
										%>
										<option value="<%=teamIndex%>" <%=teamIndex == rankIndex ? "selected" : ""%>><%=teamOption.getTeamName()%></option>
										<%
										}
										%>
									</select>
								</label>
								<div class="rank-team-name"><%=rank.getTeamName()%></div>
							</div>
						</div>

						<div class="rank-season-panel">
							<div class="rank-season-title"><%=seasonYear%> 시즌</div>
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


<script>
(function() {
	var slider = document.getElementById('rankSlider');
	var track = document.getElementById('rankSliderTrack');
	if (!slider || !track) {
		return;
	}

	var slides = track.querySelectorAll('.rank-slide');
	var teamSelects = slider.querySelectorAll('.rank-team-select');
	if (slides.length === 0) {
		return;
	}

	var current = 0;
	var timer = null;

	function showRank(index) {
		current = (index + slides.length) % slides.length;
		track.style.transform = 'translateY(-' + (current * slider.clientHeight) + 'px)';
	}

	function stopAutoSlide() {
		if (timer !== null) {
			clearInterval(timer);
			timer = null;
		}
	}

	function startAutoSlide() {
		stopAutoSlide();
		if (slides.length <= 1) {
			return;
		}
		timer = setInterval(function() {
			showRank(current + 1);
		}, 3200);
	}

	for (var i = 0; i < teamSelects.length; i++) {
		teamSelects[i].addEventListener('change', function() {
			showRank(parseInt(this.value, 10));
		});
	}

	slider.addEventListener('mouseenter', stopAutoSlide);
	slider.addEventListener('mouseleave', startAutoSlide);
	window.addEventListener('resize', function() {
		showRank(current);
	});

	showRank(0);
	startAutoSlide();
})();
</script>
</main>

<script>
	window.addEventListener("DOMContentLoaded", function() {

		const slides = document.querySelectorAll(".hero-slider > .hero-slide");
		const dots = document.querySelectorAll(".hero-dot");

		let currentSlide = 0;

		function showSlide(index) {
			if (slides.length === 0) {
				return;
			}

			for (let i = 0; i < slides.length; i++) {
				slides[i].classList.remove("active");

				if (dots[i]) {
					dots[i].classList.remove("active");
				}
			}

			slides[index].classList.add("active");

			if (dots[index]) {
				dots[index].classList.add("active");
			}

			currentSlide = index;
		}

		function nextSlide() {
			if (slides.length <= 1) {
				return;
			}

			let nextIndex = currentSlide + 1;

			if (nextIndex >= slides.length) {
				nextIndex = 0;
			}

			showSlide(nextIndex);
		}

		for (let i = 0; i < dots.length; i++) {
			dots[i].addEventListener("click", function() {
				showSlide(i);
			});
		}

		setInterval(nextSlide, 5000);
	});
</script>

<%@ include file="/fragment/footer.jsp"%>

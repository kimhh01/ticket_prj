<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" href="event.css">

</head>
<body>

<jsp:include page="../fragment/header.jsp"/>
<style>
@import url("event.css");
</style>

<div class="event-wrap">

    <h1 class="event-title">이벤트</h1>

    <h2 class="event-sub-title">진행중 이벤트</h2>

    <div class="event-card-wrap">

        <div class="event-card" onclick="location.href='eventDetail_1.jsp'">

            <img src="../images/event/event1.png" alt="이벤트1">

            <div class="event-card-body">

                <span class="event-type">[할인]</span>

                <h3>신규회원 첫 예매 3,000원 할인</h3>

                <p>2026.05.20 ~ 2026.06.30</p>

            </div>

        </div>

        <div class="event-card" onclick="location.href='eventDetail_2.jsp'">

            <img src="../images/event/event2.png" alt="이벤트2">

            <div class="event-card-body">

                <span class="event-type">[이벤트]</span>

                <h3>6월 키움 히어로즈 홈경기 특별 할인</h3>

                <p>2026.06.01 ~ 2026.06.30</p>

            </div>

        </div>

    </div>

    <h2 class="event-sub-title">진행중인 이벤트</h2>

    <div class="event-list">

        <div class="event-row" onclick="location.href='eventDetail_1.jsp'">

            <img src="../images/event/event1_banner.png">

            <div class="event-info">

                <span>[할인]</span>

                <strong>신규회원 첫 예매 3,000원 할인</strong>

            </div>

            <div class="event-date">
                2026.05.20 ~ 2026.06.30
            </div>

            <div class="event-arrow">〉</div>

        </div>

        <div class="event-row" onclick="location.href='eventDetail_2.jsp'">

            <img src="../images/event/event2_banner.png">

            <div class="event-info">

                <span>[이벤트]</span>

                <strong>6월 키움 히어로즈 홈경기 특별 할인</strong>

            </div>

            <div class="event-date">
                2026.06.01 ~ 2026.06.30
            </div>

            <div class="event-arrow">〉</div>

        </div>

    </div>

</div>

<jsp:include page="../fragment/footer.jsp"/>
<style>
@import url("evenvt.css");
</style>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@page import="userEvent.EventDTO"%>
<%@page import="userEvent.EventPageService"%>

<%@page import="java.util.List"%>
<%@page import="userEvent.CouponDTO"%>
<%@page import="userEvent.CouponService"%>

<%
CouponService couponService = new CouponService();

List<CouponDTO> couponList = couponService.searchCoupon();

pageContext.setAttribute("couponList", couponList);
%>

<%
int eventId = Integer.parseInt(request.getParameter("eventId"));

EventPageService eventService = new EventPageService();
EventDTO event = eventService.searchEventDetail(eventId);

pageContext.setAttribute("event", event);
%>



<%
if(event == null){
%>
    <script>
        alert("존재하지 않는 이벤트입니다.");
        location.href="eventMain.jsp";
    </script>
<%
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="eventDetail.css">

</head>
<body>

<jsp:include page="../fragment/header.jsp"/>

<div class="event-detail-wrap">

    <h1 class="event-title">이벤트</h1>


    <!-- 배너 -->
    <div class="event-banner">
       <img src="../images/event/${event.representativeImg}"
     alt="${event.eventTitle}">
    </div>

    <!-- 제목 -->
    <div class="event-header">
    <h2>${event.eventTitle}</h2>
    <span>${event.eventStartDate} ~ ${event.eventEndDate}</span>
</div>


<button
type="button"
class="coupon-btn"
onclick="location.href='couponDownload.jsp?couponCode=CP002'">
쿠폰 다운로드
</button>

     <!-- 혜택안내 / 이용방법 -->
    <div class="event-info-wrap">

        <div class="benefit-area">

            <h3>① 혜택 안내</h3>

            <table class="benefit-table">
                <tr>
                    <th>항목</th>
                    <th>내용</th>
                </tr>

                <tr>
                    <td>대상</td>
                    <td>BallPick 회원 누구나</td>
                </tr>

                <tr>
                    <td>혜택</td>
                    <td>예매 시 20% 할인 쿠폰 제공</td>
                </tr>

                <tr>
                    <td>적용 경기</td>
                    <td>이벤트 기간 내 전 구단 일반 경기 (일부 경기 제외)</td>
                </tr>

                <tr>
                    <td>사용 기간</td>
                    <td>이벤트 기간 내 1회 사용 가능</td>
                </tr>

                <tr>
                    <td>적용 방법</td>
                    <td>쿠폰 다운로드 후 결제 단계에서 적용</td>
                </tr>
            </table>

        </div>

        <div class="method-area">

            <h3>② 이용 방법</h3>

            <div class="step-wrap">

                <div class="step-box">
                    <div class="step-num">1</div>
                    <div class="step-icon">👤</div>
                    <p>상단 쿠폰 다운로드</p>
                </div>

                <div class="arrow">〉</div>

                <div class="step-box">
                    <div class="step-num">2</div>
                    <div class="step-icon">🏟</div>
                    <p>홈경기 선택</p>
                </div>

                <div class="arrow">〉</div>

                <div class="step-box">
                    <div class="step-num">3</div>
                    <div class="step-icon">🎟</div>
                    <p>결제 시<br>'여름 할인 쿠폰(20%)' 선택</p>
                </div>

            </div>

        </div>

    </div>

    <!-- 유의사항 -->
    <div class="notice-area">

        <h3>③ 유의사항</h3>

        <div class="notice-box">

            <div class="notice-icon">!</div>

            <ul>
                <li>할인 쿠폰은 1인 1회만 다운로드 가능합니다.</li>
                <li>이벤트 기간 내에만 사용할 수 있습니다.</li>
                <li>일부 특가 경기 및 이벤트 경기는 할인 대상에서 제외됩니다.</li>
                <li>다른 쿠폰 및 할인 혜택과 중복 사용할 수 없습니다.</li>
            </ul>

        </div>

    </div>

</div>

<jsp:include page="../fragment/footer.jsp"/>
</body>
</html>
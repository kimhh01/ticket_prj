<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@page import="userEvent.EventDTO"%>
<%@page import="userEvent.EventPageService"%>


<%
int eventId = Integer.parseInt(request.getParameter("eventId"));

EventPageService service = new EventPageService();
EventDTO event = service.searchEventDetail(eventId);

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

<%= event.getRepresentativeImg() %>
<%= event.getEventTitle() %>

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

    <!-- 혜택안내 / 이용방법 -->
   <div class="event-info-wrap2">

    <!-- 혜택안내 -->
    <div class="benefit-area2">

        <h3>① 혜택 안내</h3>
        

        <table class="benefit-table">

            <tr>
                <th>항목</th>
                <th>내용</th>
            </tr>

            <tr>
                <td>대상</td>
                <td>키움 히어로즈 홈경기 예매 고객</td>
            </tr>

            <tr>
                <td>혜택</td>
                <td>티켓 2,000원 할인</td>
            </tr>

            <tr>
                <td>적용 경기</td>
                <td>6월 홈경기 대상, 일부 특별 경기 제외</td>
            </tr>

            <tr>
                <td>사용 기간</td>
                <td>이벤트 기간 내 1회 사용 가능</td>
            </tr>

            <tr>
                <td>적용 방법</td>
                <td>결제 단계에서 자동 할인 적용</td>
            </tr>

        </table>

    </div>


    <!-- 이용방법 -->
    <div class="method-area2">
		<h3>2.이용방법</h3>
		
    <div class="step-wrap2">

        <div class="step-box2">
            <div class="step-num2">1</div>
            <div class="step-icon2">
                <i class="bi bi-display"></i>
            </div>
            <p>이벤트 페이지 접속</p>
        </div>

        <div class="arrow2">〉</div>

        <div class="step-box2">
            <div class="step-num2">2</div>
            <div class="step-icon2">
                <i class="bi bi-building"></i>
            </div>
            <p>키움 홈경기 선택</p>
        </div>

        <div class="arrow2">〉</div>

        <div class="step-box2">
            <div class="step-num2">3</div>
            <div class="step-icon2">
                <i class="bi bi-credit-card"></i>
            </div>
            <p>결제 시 자동 할인</p>
        </div>

    </div>
</div>
</div>

<!-- 유의사항 -->
<div class="notice-area2">

    <h3>③ 유의사항</h3>

    <div class="notice-box2">

        <div class="notice-icon2">
            !
        </div>

        <ul>
            <li>타 할인 혜택과 중복 적용이 제한될 수 있습니다.</li>
            <li>일부 좌석 및 일부 경기는 할인 대상에서 제외될 수 있습니다.</li>
            <li>할인 혜택은 1인 1회만 사용 가능합니다.</li>
            <li>상세 조건은 예매 페이지에서 확인 가능합니다.</li>
        </ul>

    </div>

</div>


<jsp:include page="../fragment/footer.jsp"/>


</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="userEvent.EventDTO"%>
<%@page import="userEvent.EventPageService"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
EventPageService service = new EventPageService();
List<EventDTO> eventList = service.searchEvent();

pageContext.setAttribute("eventList", eventList);
%>

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

<div class="event-wrap">

    <h1 class="event-title">이벤트</h1>

    <h2 class="event-sub-title">진행중 이벤트</h2>

<div class="event-card-wrap">

    <c:forEach var="event" items="${eventList}">
        <div class="event-card"
             onclick="location.href='eventDetail.jsp?eventId=${event.eventId}'">

            <img src="../images/event/${event.thumbnailImg}"
                 alt="${event.eventTitle}">

            <div class="event-card-body">

                <span class="event-type">[이벤트]</span>

                <h3>${event.eventTitle}</h3>

                <p>${event.eventStartDate} ~ ${event.eventEndDate}</p>

            </div>

        </div>
    </c:forEach>

</div>
<h2 class="event-sub-title">진행중인 이벤트</h2>

<div class="event-list">

    <c:forEach var="event" items="${eventList}">

        <div class="event-row"
             onclick="location.href='eventDetail.jsp?eventId=${event.eventId}'">

            <img src="../images/event/${event.representativeImg}">

            <div class="event-info">

                <span>[이벤트]</span>

                <strong>${event.eventTitle}</strong>

            </div>

            <div class="event-date">
                ${event.eventStartDate} ~ ${event.eventEndDate}
              
            </div>

            <div class="event-arrow">〉</div>

        </div>

    </c:forEach>

</div>



    </div>
    
    

<jsp:include page="../fragment/footer.jsp"/>

</body>
</html>
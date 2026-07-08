<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.List" %>

<%@ page import="kr.user.member.MemberDTO" %>

<%@ page import="userMypage.MyPageService" %>
<%@ page import="userMypage.MyPageReservationDTO" %>
<%@ page import="userMypage.ReservationDetailDTO" %>

<% 
MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");

int reservationId =
Integer.parseInt(request.getParameter("reservationId"));

MyPageService service = new MyPageService();

MyPageReservationDTO gameInfo =
service.getReservationGameInfo(
        reservationId,
        loginMember.getMemberCode());

List<ReservationDetailDTO> seatList =
service.getReservationSeatInfo(
        reservationId,
        loginMember.getMemberCode());

MyPageReservationDTO reservationInfo =
service.getReservationInfo(
        reservationId,
        loginMember.getMemberCode());

MyPageReservationDTO paymentInfo =
service.getPaymentInfo(
        reservationId,
        loginMember.getMemberCode());
 %>
 
<%
pageContext.setAttribute("loginMember", loginMember);
pageContext.setAttribute("gameInfo", gameInfo);
pageContext.setAttribute("seatList", seatList);
pageContext.setAttribute("reservationInfo", reservationInfo);
pageContext.setAttribute("paymentInfo", paymentInfo);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예매상세</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="myPage.css">

<style>

body{
    background:#fff;
    padding:30px;
}

/* 제목 */

.popup-title{
    display:flex;
    justify-content:space-between;
    align-items:center;
    padding-bottom:20px;
    border-bottom:1px solid #ddd;
    margin-bottom:30px;
}

.popup-title h2{
    font-size:32px;
    font-weight:700;
    margin:0;
}

.close-btn{
    border:none;
    background:none;
    font-size:36px;
    cursor:pointer;
}

/* 섹션 */

.detail-section{
    margin-bottom:40px;
}

.detail-section h3{
    font-size:26px;
    font-weight:700;
    margin-bottom:25px;
}

/* 정보 영역 */

.info-wrap{
    display:flex;
    justify-content:space-between;
}

.info-table{
    width:48%;
}

.info-table tr{
    height:55px;
}

.info-table th{
    width:120px;
    font-weight:600;
}

.status-badge{
    display:inline-block;
    background:#ffe9ea;
    color:#ff5f8f;
    padding:6px 12px;
    border-radius:6px;
    font-size:14px;
    font-weight:600;
}

/* 티켓 테이블 */

.ticket-table{
    width:100%;
    border-collapse:collapse;
}

.ticket-table th{
    background:#f5f5f5;
    height:55px;
    text-align:center;
    border:1px solid #ddd;
}

.ticket-table td{
    height:55px;
    text-align:center;
    border:1px solid #ddd;
}

/* 환불 안내 */

.notice-box{
    border:1px solid #ddd;
    border-radius:8px;
    padding:25px;
    background:#fafafa;
}

.notice-box ul{
    margin:0;
}

.notice-box li{
    margin-bottom:10px;
}

/* 하단 버튼 */

.bottom-btn{
    text-align:right;
    margin-top:40px;
}

.bottom-btn button{
    width:140px;
    height:50px;
    border:1px solid #ddd;
    background:#fff;
    border-radius:6px;
    font-weight:600;
}

</style>

</head>
<body>

<div class="popup-title">
    <h2>예매상세</h2>

    <button class="close-btn"
            onclick="window.close()">
        ×
    </button>
</div>

<!-- 예매정보 -->

<div class="detail-section">

    <h3>예매 정보</h3>

    <div class="info-wrap">

        <table class="info-table">
            <tr>
                <th>경기명</th>
               	<td>${gameInfo.gameName}</td>
            </tr>

            <tr>
                <th>경기일시</th>
				<td>
    <fmt:formatDate value="${gameInfo.gameDate}" pattern="yyyy.MM.dd"/>
    						${gameInfo.gameStartTime}
				</td>
            </tr>

            <tr>
                <th>경기장</th>
				<td>${gameInfo.stadiumName}</td>
            </tr>

    <tr>
    <th>예매상태</th>
    <td>
        <span class="status-badge">
            ${gameInfo.reservationStatus}
        </span>

        
    </td>
</tr>

            <tr>
                <th>예매번호</th>
             <td>${gameInfo.reservationCode}</td>
            </tr>
        </table>

        <table class="info-table">

            <tr>
                <th>예매자</th>
              <td>${loginMember.name}</td>
            </tr>

            <tr>
                <th>휴대전화</th>
               <td>${loginMember.phone}</td>
            </tr>

            <tr>
                <th>예매일시</th>
                <td>${reservationInfo.reservationDate}</td>
            </tr>

            <tr>
                <th>결제금액</th>
                <td>${paymentInfo.paymentAmount}원</td>
            </tr>

            <tr>
                <th>결제수단</th>
                <td>${paymentInfo.paymentAmount}원</td>
            </tr>

        </table>

    </div>

</div>

<!-- 좌석 정보 -->

<div class="detail-section">

    <h3>좌석 / 티켓 정보</h3>

    <table class="ticket-table">

        <tr>
            <th>티켓번호</th>
            <th>구역/좌석</th>
            <th>권종</th>
            <th>가격</th>
            <th>상태</th>
        </tr>
        
<c:forEach var="seat" items="${seatList}">
<tr>

<td>${seat.reservationDetailCode}</td>

<td>${seat.stadiumSeatCode}</td>

<td>${seat.reservationType}</td>

<td>${seat.ticketPrice}원</td>

<td>${seat.reservationStatus}

<c:if test="${not empty gameInfo.cancelDate}">
            <br>
            <span style="font-size:12px;color:gray;">
                예매일 : ${gameInfo.cancelDate}
            </span>
        </c:if>
        
<c:if test="${not empty gameInfo.cancelDate}">
            <br>
            <span style="font-size:12px;color:gray;">
                취소일 : ${gameInfo.cancelDate}
            </span>
        </c:if>

</td>

</tr>
</c:forEach>



    </table>

</div>

<!-- 취소 환불 -->

<div class="detail-section">

    <h3>취소/환불 안내</h3>

    <div class="notice-box">

        <ul>
            <li>경기 시작 7일 전까지 : 100% 환불 (수수료 제외)</li>
            <li>경기 시작 1일 전까지 : 10% 수수료 제외 후 환불</li>
            <li>경기 당일 취소 및 환불 불가</li>
        </ul>

    </div>

</div>

<div class="bottom-btn">

    <button type="button" onclick="window.close()">
        닫기
    </button>

    <button type="button" class="btn btn-dark"
onclick="if(confirm('예매를 취소하시겠습니까?')) {
    location.href='cancelReservationProcess.jsp?reservationId=<%=reservationId%>';}">
    예매취소
</button>

</div>

</body>
</html>
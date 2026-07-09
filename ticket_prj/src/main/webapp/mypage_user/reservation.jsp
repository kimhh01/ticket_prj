<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.YearMonth"%>
<%@page import="java.util.List"%>

<%@page import="java.util.List"%>
<%@page import="userMypage.MyPageReservationDTO"%>
<%@page import="userMypage.MyPageService"%>
<%@page import="kr.user.member.MemberDTO"%> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");

if(loginMember == null){
    response.sendRedirect("../login/login.jsp");
    return;
}

MyPageService service = new MyPageService();


//날짜조
Date startDate = null;
Date endDate = null;

String period = request.getParameter("period");
String yearParam = request.getParameter("year");
String monthParam = request.getParameter("month");
String tab = request.getParameter("tab");

if(tab == null){
    tab = "reservation";
}

LocalDate today = LocalDate.now();
LocalDate startLocal = null;
LocalDate endLocal = null;

if(period != null && !"".equals(period)){

    endLocal = today;

    if("15".equals(period)){
        startLocal = today.minusDays(15);
    } else {
        startLocal = today.minusMonths(Integer.parseInt(period));
    }

} else if(yearParam != null && monthParam != null &&
          !"".equals(yearParam) && !"".equals(monthParam)){

    int year = Integer.parseInt(yearParam);
    int month = Integer.parseInt(monthParam);

    YearMonth ym = YearMonth.of(year, month);

    startLocal = ym.atDay(1);
    endLocal = ym.atEndOfMonth();
}

if(startLocal != null && endLocal != null){
    startDate = Date.valueOf(startLocal);
    endDate = Date.valueOf(endLocal);
}



List<MyPageReservationDTO> reservationList =
        service.getReservationList(
                loginMember.getMemberCode(),
                startDate,
                endDate,
                "reservation");

List<MyPageReservationDTO> cancelList =
        service.getReservationList(
                loginMember.getMemberCode(),
                startDate,
                endDate,
                "cancel");
%>

<%
pageContext.setAttribute("reservationList", reservationList);
pageContext.setAttribute("cancelList", cancelList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예매확인/취소</title>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" href="myPage.css">

</head>
<body>

<jsp:include page="../fragment/header.jsp"/>
<style>
@import url("myPage.css");
</style>


    <div class="mypage-wrap">


    <h1 class="page-title">마이페이지</h1>

   <div class="mypage-menu">
    <a href="reservation.jsp" class="active">예매관리</a>
    <a href="memberInfo.jsp">회원정보수정</a>
    <a href="withDrawMember.jsp">회원탈퇴</a>
</div>

	<h2 class="section-title">예매확인/취소</h2>
	<div class="tab-area">
	<button type="button" id="btnReservation" class="active-btn">
        예매확인
    </button>

   <button type="button"
        id="btnCancelTab">
    예매취소
</button>

    </div>

    <div class="search-area">

        <div class="period-search">
            <span>기간별 조회</span>

       <button type="button"
class="<%= "15".equals(period) ? "period-active" : "" %>"
onclick="location.href='reservation.jsp?tab=<%=tab%>&period=15'">
15일
</button>
      <button type="button"
class="<%= "1".equals(period) ? "period-active" : "" %>"
onclick="location.href='reservation.jsp?tab=<%=tab%>&period=1'">
1개월
</button> 
<button type="button"
class="<%= "2".equals(period) ? "period-active" : "" %>"
onclick="location.href='reservation.jsp?tab=<%=tab%>&period=2'">
2개월
</button>
<button type="button"
class="<%= "3".equals(period) ? "period-active" : "" %>"
onclick="location.href='reservation.jsp?tab=<%=tab%>&period=3'">
3개월
</button>
       
       

        </div>

        <div class="month-search">
            <span>월별 조회</span>

          <select id="year">
<%
int currentYear = LocalDate.now().getYear();

for(int i=currentYear; i>=2023; i--){
%>
    <option value="<%=i%>"><%=i%></option>
<%
}
%>
</select>

<select id="month">
<%
for(int i=1;i<=12;i++){
%>
    <option value="<%=i%>"><%=i%>월</option>
<%
}
%>
</select>
<button type="button" id="searchMonthBtn">
조회
</button>
        </div>

    </div>
    
<!-- 예매확인 영역 -->

<div id="reservationArea">
    <table class="reservation-table">
        <thead>
            <tr>
                <th>예매번호</th>
                <th>티켓명</th>
                <th>관람일시</th>
                <th>매수</th>
                <th>취소가능일</th>
                <th>상태</th>
            </tr>
        </thead>
	
	<tbody>

<c:forEach var="dto" items="${reservationList}">

<tr class="reservation-row"
	data-id="${dto.reservationCode}">

    <td>${dto.reservationCode}</td>
    <td>${dto.gameName}</td>
    <td>${dto.gameDate} ${dto.gameStartTime}</td>
    <td>${dto.reservationQuantity}</td>
    <td>${dto.cancelAvailableDate}</td>
    <td>
        <button class="status-btn" type="button" disabled>
            ${dto.reservationStatus}
        </button>
    </td>
</tr>
</c:forEach>

</tbody>

    </table>
    </div>
    


    <!-- 예매취소 영역 -->
    
    <div id="cancelArea" style="display:none;">

    <table class="reservation-table">

        <thead>
        <tr>
            <th>예매번호</th>
            <th>티켓명</th>
            <th>관람일시</th>
            <th>매수</th>
              <th>티켓취소일</th>
                <th>상태</th>
        </tr>
        </thead>

        <tbody>


<c:forEach var="dto" items="${cancelList}">

<tr class="cancel-row"
	data-id="${dto.reservationCode}">

    <td>${dto.reservationCode}</td>
    <td>${dto.gameName}</td>
    <td>${dto.gameDate} ${dto.gameStartTime}</td>
    <td>${dto.reservationQuantity}</td>
    
    <td>${dto.cancelDate}</td>

<td>
    <button class="status-btn" type="button" disabled>
        ${dto.reservationStatus}
    </button>
</td>

</tr>
</c:forEach>

        </tbody>
        
    </table>


</div>


<!-- 페이징 버튼 -->
    <div class="pagination-area">

    <button>&lt;</button>

    <button class="active-page">1</button>

    <button>&gt;</button>

</div>

</div>



</main>

</body>

<script>

//예매확인 탭 클릭
$(function(){

    // 처음 열릴 때 탭 유지
    if("<%=tab%>" == "cancel"){

        $("#btnCancelTab").addClass("active-btn");
        $("#btnReservation").removeClass("active-btn");

        $("#reservationArea").hide();
        $("#cancelArea").show();

    }

    // 예매확인
    $("#btnReservation").click(function(){
        location.href="reservation.jsp?tab=reservation";
    });

    // 예매취소
    $("#btnCancelTab").click(function(){
        location.href="reservation.jsp?tab=cancel";
    });

    // 상세창
$(".reservation-row, .cancel-row").click(function(){

    var reservationId=$(this).data("id");

    window.open(
        "reservationDetail.jsp?reservationId="+reservationId,
        "detail",
        "width=900,height=700"
    );

});

    // 월별조회
    $("#searchMonthBtn").click(function(){

        var year=$("#year").val();
        var month=$("#month").val();

        location.href="reservation.jsp?tab=<%=tab%>&year="+year+"&month="+month;

    });

});
  

</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>

<jsp:include page="../fragment/footer.jsp"/>
<style>
@import url("myPage.css");
</style>

</body>
</html>
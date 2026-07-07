<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.sql.Date"%>
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

Date startDate = null;
Date endDate = null;

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

            <button>15일</button>
            <button class="period-active">1개월</button>
            <button>2개월</button>
            <button>3개월</button>
        </div>

        <div class="month-search">
            <span>월별 조회</span>

            <select>
                <option>연도</option>
            </select>

            <select>
                <option>월</option>
            </select>

            <button>조회</button>
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
              <th>취소가능일</th>
                <th>예매취소</th>
        </tr>
        </thead>

        <tbody>

        <tr class="cancel-row" 
        	data-id="${dto.reservationCode}" >
        	
            <td>222222</td>
            <td>두산 VS 한화</td>
            <td>2026.06.05 14:00</td>
            <td>3</td>
            <td>06.04</td>
            <td>
            
            <button class="status-btn btnCancel"
        data-bs-toggle="modal"
        data-bs-target="#cancelModal">
    예매취소
</button>
</td>
        </tr>
        


        </tbody>

    </table>
<div class="modal fade" id="cancelModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered custom-dialog">
        <div class="modal-content custom-modal">

            <div class="modal-body text-left">
                <h4>취소하시겠습니까?</h4>

                <div class="modal-btn-area">
                
                <button type="button"
                            class="btn btn-dark text-"
                            id="confirmCancel">
                        확인
                    </button>
                
                    <button type="button"
                            class="btn btn-secondary"
                            data-bs-dismiss="modal">
                        닫기
                    </button>

                    
                </div>
            </div>

        </div>
    </div>
</div>

</div>

<div class="modal fade" id="completeModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered custom-dialog">
        <div class="modal-content custom-modal">

            <div class="modal-body text-left">
                <h4>예매취소되었습니다.</h4>

                <div class="modal-btn-area">
                    <button type="button"
                            class="btn btn-dark"
                            data-bs-dismiss="modal">
                        확인
                    </button>
                </div>
            </div>

        </div>
    </div>
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

$(function(){

    //예매확인 탭 클릭
    $("#btnReservation").click(function(){

        $("#btnReservation").addClass("active-btn");
        $("#btnCancelTab").removeClass("active-btn");

        $("#reservationArea").show();
        $("#cancelArea").hide();

    });

    // 예매취소 탭 클릭
    $("#btnCancelTab").click(function(){

        $("#btnCancelTab").addClass("active-btn");
        $("#btnReservation").removeClass("active-btn");

        $("#reservationArea").hide();
        $("#cancelArea").show();

    });

	//행 클릭시 상세내역 창 열기 
	$(".reservation-row").click(function(){
		 
		var reservationId=$(this).data("id");
		 
    window.open(
    		 "reservationDetail.jsp?reservationId="+reservationId,
    	        "detail",
    	        "width=900,height=700"
    	    );
});
	
	$(".status-btn").click(function(e){
	    e.stopPropagation();
	});

});

//예매취소 팝업
$(function(){
	
	let selectedRow = null;

	$(".cancel-row").click(function(){

	    var reservationId=$(this).data("id");

	    window.open(
	        "reservationCancel.jsp?reservationId="+reservationId,
	        "cancel",
	        "width=900,height=700"
	    );

	});
	
    $("#completeModal .btn-dark").click(function(){

        if(selectedRow){
            selectedRow.remove();
        }

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
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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

    <button type="button" id="btnCancel">
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

            <tr>
                <td>111111</td>
                <td>LG VS 삼성</td>
                <td>2026.06.05 14:00</td>
                <td>3</td>
                <td>06.04</td>
                <td>
                    <button class="status-btn">예매완료</button>
                </td>
            </tr>

            <tr>
                <td>222222</td>
                <td>두산 VS 한화</td>
                <td>2026.06.05 14:00</td>
                <td>3</td>
                <td>06.04</td>
                <td>
                    <button class="status-btn cancel">취소완료</button>
                </td>
            </tr>

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
            <th></th>
        </tr>
        </thead>

        <tbody>

        <tr class="cancel-row">
            <td>222222</td>
            <td>두산 VS 한화</td>
            <td>2026.06.05 14:00</td>
            <td>3</td>
            <td>06.04</td>
            <td>
                <button class="status-btn">예매취소</button>
            </td>
        </tr>

        <tr class="cancel-detail">
            <td colspan="5">

                <strong>취소일</strong> : 2026.06.04<br>
                <strong>취소금액</strong> : 45,000원<br>
                <strong>환불상태</strong> : 환불완료

            </td>
        </tr>

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


<script>

$(function(){

    // 예매확인 클릭
    $("#btnReservation").click(function(){

        $("#btnReservation").addClass("active-btn");
        $("#btnCancel").removeClass("active-btn");

        $("#reservationArea").show();
        $("#cancelArea").hide();

    });

    // 예매취소 클릭
    $("#btnCancel").click(function(){

        $("#btnCancel").addClass("active-btn");
        $("#btnReservation").removeClass("active-btn");

        $("#reservationArea").hide();
        $("#cancelArea").show();

    });

});
</script>
</body>
</html>
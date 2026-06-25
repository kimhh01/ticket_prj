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
			
            <tr class="reservation-row">
                <td>111111</td>
                <td>LG VS 삼성</td>
                <td>2026.06.05 14:00</td>
                <td>3</td>
                <td>06.04</td>
                <td>
                    <button class="status-btn">예매완료</button>
                </td>
            </tr>

            <tr class="reservation-row">
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
    
        
<!-- 예매 상세 팝업   -->


    <div class="modal fade" id="reservationDetailModal" tabindex="-1">

    <div class="modal-dialog modal-xl modal-dialog-centered">

        <div class="modal-content detail-modal">

            <div class="modal-header">

                <h3 class="modal-title">예매상세</h3>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="modal">
                </button>

            </div>

            <div class="modal-body">

                <!-- 예매 정보 -->

                <h4 class="detail-title">예매 정보</h4>

                <div class="detail-info-wrap">

                    <table class="detail-info-table">
                        <tr>
                            <th>경기명</th>
                            <td>LG VS 삼성</td>
                        </tr>
                        <tr>
                            <th>경기일시</th>
                            <td>2026.06.05 14:00</td>
                        </tr>
                        <tr>
                            <th>경기장</th>
                            <td>잠실야구장</td>
                        </tr>
                        <tr>
                            <th>예매상태</th>
                            <td>
                                <span class="detail-status">
                                    예매완료
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <th>예매번호</th>
                            <td>B245</td>
                        </tr>
                    </table>

                    <table class="detail-info-table">
                        <tr>
                            <th>예매자</th>
                            <td>김희수</td>
                        </tr>
                        <tr>
                            <th>휴대전화</th>
                            <td>010-1234-5678</td>
                        </tr>
                        <tr>
                            <th>예매일시</th>
                            <td>2026.05.28 20:30</td>
                        </tr>
                        <tr>
                            <th>결제금액</th>
                            <td>60,000원</td>
                        </tr>
                        <tr>
                            <th>결제수단</th>
                            <td>신용카드</td>
                        </tr>
                    </table>

                </div>

                <!-- 좌석 정보 -->

                <h4 class="detail-title">
                    좌석 / 티켓 정보
                </h4>

                <table class="table table-bordered">

                    <thead>
                        <tr>
                            <th>티켓번호</th>
                            <th>구역/좌석</th>
                            <th>권종</th>
                            <th>가격</th>
                            <th>상태</th>
                        </tr>
                    </thead>

                    <tbody>

                        <tr>
                            <td>T-123-01</td>
                            <td>1루 블루석 L열 12번</td>
                            <td>성인</td>
                            <td>20,000원</td>
                            <td>예매완료</td>
                        </tr>

                        <tr>
                            <td>T-123-02</td>
                            <td>1루 블루석 L열 13번</td>
                            <td>성인</td>
                            <td>20,000원</td>
                            <td>예매완료</td>
                        </tr>

                    </tbody>

                </table>

                <!-- 환불 안내 -->

                <div class="refund-box">

                    <h4>취소/환불 안내</h4>

                    <ul>
                        <li>경기 시작 7일 전까지 : 100% 환불</li>
                        <li>경기 시작 1일 전까지 : 수수료 제외 후 환불</li>
                        <li>경기 당일 취소 불가</li>
                    </ul>

                </div>

            </div>

        </div>

    </div>

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

        <tr class="cancel-row" >
            <td>222222</td>
            <td>두산 VS 한화</td>
            <td>2026.06.05 14:00</td>
            <td>3</td>
            <td>06.04</td>
            <td>
                <button class="status-btn" data-bs-toggle="modal"
        data-bs-target="#cancelModal">예매취소</button>
            </td>
            
            
        </tr>
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

        </tbody>

    </table>

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

	//행 클릭시 상세내역 창 열기 
	$(".reservation-row").click(function(){

    window.open(
        "reservationDetail.jsp",
        "reservationDetail",
        "width=800,height=500,left=200,top=100"
    );

});
	
	$(".status-btn").click(function(e){
	    e.stopPropagation();
	});

});
$(function(){
	
	let selectedRow = null;

	$(".cancel-row .status-btn").click(function(){
	    selectedRow = $(this).closest("tr");
	});
	
    $("#confirmCancel").click(function(){

        // 첫 번째 팝업 닫기
        $("#cancelModal").modal("hide");

        // 두 번째 팝업 열기
        setTimeout(function(){
            $("#completeModal").modal("show");
        }, 300);

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
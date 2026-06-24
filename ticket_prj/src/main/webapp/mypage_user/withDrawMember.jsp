<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>

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
    <a href="reservation.jsp">예매관리</a>
    <a href="memberInfo.jsp">회원정보수정</a>
    <a href="withDrawMember.jsp" class="active">회원탈퇴</a>
</div>



<h2 class="section-title">회원탈퇴</h2>

<div class="withdraw-box" >
    <p>
        · 서비스 탈퇴 시 즉시 탈퇴 처리되나, 개인정보 도용 등으로 인한 원치 않은 철회,
        부정 이용 방지 등에 대비하기 위하여 회원님의 아이디를 포함한 개인정보가
        3일간 보존됩니다.
    </p>

    <p>
        · 서비스 탈퇴 후 3일간 개인정보(휴대전화번호, 이메일주소, 개인식별정보)가
        보관되며 동일 회원정보로 재가입이 불가능합니다.
    </p>

    <p>
        · 서비스 탈퇴 시 등록된 예매권과 쿠폰은 삭제되며 재이용이 불가능합니다.
    </p>

    <p>
        · 진행중인 전자상거래 이용내역이 있는 경우 서비스 탈퇴를 하실 수 없습니다.
    </p>

    <p>
        · 작성된 게시물은 탈퇴 시 자동 삭제되지 않으며 삭제가 필요한 경우 직접 삭제 후
        서비스 탈퇴해야 합니다.
    </p>

    <p>
        · NHN LINK 메일은 예약발송이 되므로 서비스 탈퇴 후에도 일정 기간 관련 메일이
        발송될 수 있습니다.
    </p>

</div>

<%-- 동의 체크박스 --%>
<div class="agree-area">
    <input type="checkbox" id="agree">
    <label for="agree">
        위 내용을 모두 확인하였으며 동의합니다.
    </label>
</div>


<div class="withdraw-btn-area">
   <button type="button"
        id="withdrawBtn"
        class="withdraw-btn">
    회원탈퇴
</button>
</div>

<div class="modal fade" id="alertModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-body text-center p-5">
                회원탈퇴 안내사항에 동의해야 탈퇴가 가능합니다.
            </div>

            <div class="modal-footer justify-content-center border-0">
                <button type="button"
                        class="btn btn-dark"
                        data-bs-dismiss="modal">
                    확인
                </button>
            </div>

        </div>
    </div>
</div>

<div class="modal fade" id="withdrawModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-body p-5">
                정말 탈퇴하시겠습니까?
            </div>

            <div class="modal-footer border-0">
                <button type="button"
                        class="btn btn-dark"
                        id="confirmWithdraw">
                    확인
                </button>

                <button type="button"
                        class="btn btn-light"
                        data-bs-dismiss="modal">
                    취소
                </button>
            </div>

        </div>
    </div>
</div>

<div class="modal fade" id="completeModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-body p-5">
                탈퇴 되었습니다.
            </div>

            <div class="modal-footer border-0">
                <button type="button"
                        class="btn btn-dark"
                        data-bs-dismiss="modal">
                    확인
                </button>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>

<script>
$(function(){

    $("#withdrawBtn").click(function(){

        if(!$("#agree").is(":checked")){

            let alertModal =
                new bootstrap.Modal(document.getElementById("alertModal"));

            alertModal.show();

            return;
        }

        let withdrawModal =
            new bootstrap.Modal(document.getElementById("withdrawModal"));

        withdrawModal.show();

    });

});

$("#confirmWithdraw").click(function(){

    // 탈퇴 확인 팝업 닫기
    bootstrap.Modal.getInstance(
        document.getElementById("withdrawModal")
    ).hide();

    // 탈퇴 완료 팝업 띄우기
    let completeModal =
        new bootstrap.Modal(
            document.getElementById("completeModal")
        );

    completeModal.show();

});
</script>


</div>

<jsp:include page="../fragment/footer.jsp"/>
<style>
@import url("myPage.css");
</style>
</body>
</html>
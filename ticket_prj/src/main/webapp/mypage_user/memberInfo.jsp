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
        <a href="reservation.jsp">예매관리</a>
        <a href="memberInfo.jsp" class="active">회원정보수정</a>
        <a href="withDrawMember.jsp">회원탈퇴</a>
    </div>

    <h2 class="section-title">회원정보수정</h2>

    <div class="member-grade">
        회원님은 <strong>일반회원</strong> 입니다.
    </div>

    <table class="member-table">

        <tr>
            <th>아이디</th>
            <td>kim****</td>
        </tr>

        <tr>
            <th>비밀번호</th>
            <td>
                <button type="button"
        class="change-pw-btn"
        data-bs-toggle="modal"
        data-bs-target="#changePwModal">
    	비밀번호 변경
		</button>
            </td>
        </tr>

        <tr>
            <th>연락처</th>
            <td>010-****-1234</td>
        </tr>

        <tr>
            <th>주소</th>
            <td>서울시 강남구</td>
        </tr>

    </table>
    
<div class="btn-area">
    <button type="button"
            class="updateBtn"
            data-bs-toggle="modal"
            data-bs-target="#checkPwModal">
        회원정보 수정
    </button>
</div>

</div>


<%-- 비밀번호 변경/임시 비번 1234 --%>
<div class="modal fade" id="changePwModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content pw-modal">

            <div class="modal-body">
				
				 <form id="passwordFrm"
          action="changePasswordProcess.jsp"
          method="post">

        <h3 class="pw-title">
        
                <h3 class="pw-title">
                    비밀번호를 입력해 주세요
                </h3>

                <p class="pw-desc">
                    신규 비밀번호는 8~15자리의<br>
                    영문, 숫자, 특수문자 조합으로 만들어 주세요.
                </p>

                <input type="password"
                       id="currentPw"
                        name="oldPass"
                       class="pw-input"
                       placeholder="사용 중인 비밀번호">

                <input type="password"
                       id="newPw"
                           name="newPass"
                       class="pw-input"
                       placeholder="신규 비밀번호">

                <input type="password"
                       id="newPwChk"
                          name="newPassCheck"
                       class="pw-input"
                       placeholder="신규 비밀번호 재입력">

                <button type="button"
                        class="pw-confirm-btn"
                        id="pwConfirmBtn">
                    확인
                </button>
			</form>
			
            </div>

        </div>
    </div>
</div>

<div class="modal fade" id="pwSuccessModal" tabindex="-1">

    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-body p-4">
                비밀번호가 변경되었습니다.
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

<div class="modal fade" id="pwFailModal" tabindex="-1">

    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-body p-4">
                현재 비밀번호가 정확하지 않습니다.
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

<div class="modal fade" id="pwMismatchModal" tabindex="-1">

    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-body p-4">
                새 비밀번호 확인이 정확하지 않습니다.
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

<div class="modal fade" id="checkPwModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">비밀번호 확인</h5>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="modal">
                </button>
            </div>

            <div class="modal-body">

                <p>회원정보 수정을 위해 비밀번호를 입력해주세요.</p>

                <input type="password"
                       id="checkCurrentPw"
                       class="form-control"
                       placeholder="현재 비밀번호">

            </div>

            <div class="modal-footer">

                <button type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal">
                    취소
                </button>

                <button type="button"
                        class="btn btn-dark"
                        id="pwCheckBtn">
                    확인
                </button>

            </div>

        </div>
    </div>
</div>

<script>
$(function(){

    $("#pwConfirmBtn").click(function(){

        let currentPw=$("#currentPw").val();
        let newPw=$("#newPw").val();
        let newPwChk=$("#newPwChk").val();

        if(currentPw!="1234"){
            new bootstrap.Modal(
                document.getElementById("pwFailModal")
            ).show();
            return;
        }

        if(newPw!=newPwChk){
            new bootstrap.Modal(
                document.getElementById("pwMismatchModal")
            ).show();
            return;
        }

        bootstrap.Modal.getInstance(
            document.getElementById("changePwdal")
        ).hide();

        new bootstrap.Modal(
            document.getElementById("pwSuccessModal")
        ).show();

    });
    
    //닫으면 비밀번호 입력값 초기화 
    $("#changePwModal").on("hidden.bs.modal", function(){

        $("#currentPw").val("");
        $("#newPw").val("");
        $("#newPwChk").val("");

    });

    
    
});

$(function(){

    $("#pwCheckBtn").click(function(){

        var pw=$("#checkCurrentPw").val();

        // 임시 비밀번호
        if(pw=="1234"){

            location.href="memberEdit.jsp";

        }else{

            alert("비밀번호가 일치하지 않습니다.");

            $("#currentPw").val("");
            $("#currentPw").focus();

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
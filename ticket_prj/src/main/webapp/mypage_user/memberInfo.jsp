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
                <button type="button" class="change-pw-btn">
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
        <button type="button" class="modify-btn">
            회원정보 수정
        </button>
    </div>

</div>

</script>
</body>
</html>
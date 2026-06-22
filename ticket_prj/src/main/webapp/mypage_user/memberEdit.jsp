<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="myPage.css">

<script>
$(function(){

    $("#updateBtn").click(function(){
        alert("회원정보 수정이 완료되었습니다.");
        location.href="memberInfo.jsp";
    });

    $("#cancelBtn").click(function(){
        location.href="memberInfo.jsp";
    });

});
</script>
</head>

<body>
<div class="mypage-wrap">
<div class="edit-wrap">

    <h1 class="edit-title">회원 정보 수정</h1>

    <div class="edit-top">
        <strong>기본정보</strong>
        <span><b>*</b> 필수입력사항</span>
    </div>

    <table class="edit-table">
        <tr>
            <th><span>*</span> 아이디</th>
            <td>
                <input type="text" value="k2s0427y" readonly>
                <p>(영문소문자/숫자, 4~16자)</p>
            </td>
        </tr>

        <tr>
            <th><span>*</span> 이름</th>
            <td>
                <input type="text" value="양지윤" readonly>
            </td>
        </tr>

        <tr>
            <th><span>*</span> 주소</th>
            <td>
                <input type="text" class="zip" value="06105">
                <button type="button" class="addr-btn">주소검색</button>
                <input type="text" value="서울 강남구 논현로 662 THE H 타워">
                <input type="text" value="822호">
            </td>
        </tr>

        <tr>
            <th><span>*</span> 휴대전화</th>
            <td class="phone-area">
                <select>
                    <option>010</option>
                    <option>011</option>
                </select>
                -
                <input type="text" value="1234">
                -
                <input type="text" value="5678">
            </td>
        </tr>

        <tr>
            <th><span>*</span> SMS 수신여부</th>
            <td>
                <label><input type="radio" name="sms" checked> 수신함</label>
                <label><input type="radio" name="sms"> 수신안함</label>
                <p>쇼핑몰에서 제공하는 유익한 이벤트 소식을 SMS로 받으실 수 있습니다.</p>
            </td>
        </tr>

        <tr>
            <th><span>*</span> 이메일</th>
            <td>
                <input type="text" value="test@test.com">
            </td>
        </tr>

        <tr>
            <th><span>*</span> 이메일 수신여부</th>
            <td>
                <label><input type="radio" name="emailYn" checked> 수신함</label>
                <label><input type="radio" name="emailYn"> 수신안함</label>
                <p>쇼핑몰에서 제공하는 유익한 이벤트 소식을 이메일로 받으실 수 있습니다.</p>
            </td>
        </tr>
    </table>

    <div class="edit-btn-area">
        <button type="button" id="cancelBtn" class="cancel-btn">취소</button>
        <button type="button" id="updateBtn" class="save-btn">회원정보수정</button>
    </div>

</div>
</div>
</body>
</html>
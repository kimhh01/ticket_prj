<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="kr.user.member.MemberDTO" %>
<%@ page import="userMypage.MyPageService" %>

<%
MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");

MyPageService service = new MyPageService();

MemberDTO memberDTO = service.getMyDetail(loginMember.getMemberCode());

%>


<%
String[] phone = memberDTO.getPhone().split("-");
%>



<script src="//t1.kakaocdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function findZipcode() {
        new kakao.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동로가]$/.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode;
                document.getElementById("address").value = roadAddr;
               // document.getElementById("address2").value = data.jibunAddress;
                document.getElementById("address2").focus();
                
            }
        }).open();
    }
</script>

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

    $("#cancelBtn").click(function(){
        location.href="memberInfo.jsp";
    });

    $("form").submit(function(){

        var email = $("input[name='email']").val().trim();

        // 이메일 형식 검사
        var emailPattern = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

        if(!emailPattern.test(email)){
            alert("올바른 이메일 형식으로 입력해주세요.");
            $("input[name='email']").focus();
            return false;
        }

    });

});
</script>
</head>

<body>
<jsp:include page="../fragment/header.jsp"/>
<style>
@import url("myPage.css");
</style>

<div class="mypage-wrap">
<div class="edit-wrap">

    <h1 class="edit-title">회원 정보 수정</h1>

    <div class="edit-top">
        <strong>기본정보</strong>
        <span><b>*</b> 필수입력사항</span>
    </div>

<form action="memberEditProcess.jsp" method="post">

    <table class="edit-table">
        <tr>
            <th><span>*</span> 아이디</th>
            <td>
                <input type="text" name="memberCode"
                value="<%=memberDTO.getMemberCode()%>" readonly>
                <p>(영문소문자/숫자, 4~16자)</p>
            </td>
        </tr>

        <tr>
            <th><span>*</span> 이름</th>
            <td>
                <input type="text" value="<%=memberDTO.getName()%>" readonly>
            </td>
        </tr>

        <tr>
            <th><span>*</span> 주소</th>
            <td>
            
            <input type="text" id="zipcode" class="zip" name="zipcode"
       			value="<%=memberDTO.getZipcode()%>">

		<input type="text" id="address" name="address"
      	 value="<%=memberDTO.getAddress()%>">

		<input type="text" id="address2" name="address2"
       		value="<%=memberDTO.getAddress2()%>">
       		
               <button type="button" class="addr-btn" onclick="findZipcode()">
  				  주소검색
				</button>
				
            </td>
        </tr>

        <tr>
            <th><span>*</span> 휴대전화</th>
            <td class="phone-area">
                <select name="phone1">
    <option value="010" <%=phone[0].equals("010") ? "selected" : ""%>>010</option>
    <option value="011" <%=phone[0].equals("011") ? "selected" : ""%>>011</option>
				</select>
                
				<input type="text" name="phone2" value="<%=phone[1]%>">
				<input type="text" name="phone3" value="<%=phone[2]%>">
				
            </td>
        </tr>

        <tr>
            <th><span>*</span> SMS 수신여부</th>
            <td>
                <label>
<input type="radio"name="snsReceiveYN"
		value="Y" <%=memberDTO.getSmsReceiveYN()=='Y' ? "checked" : ""%>>
		수신함
</label>

<label>
<input type="radio" name="snsReceiveYN"
		value="N" <%=memberDTO.getSmsReceiveYN()=='N' ? "checked" : ""%>>
		수신안함
</label>
        <p>쇼핑몰에서 제공하는 유익한 이벤트 소식을 SMS로 받으실 수 있습니다.</p>
         </td>
        </tr>

        <tr>
        
        
            <th><span>*</span> 이메일</th>
            <td>
                <input type="text" name="email" value="<%=memberDTO.getEmail()%>">
            </td>
        </tr>

        <tr>
            <th><span>*</span> 이메일 수신여부</th>
            <td>
               <label>
<input type="radio"
       name="emailReceiveYN"
       value="Y"
       <%=memberDTO.getEmailReceiveYN()=='Y' ? "checked" : ""%>>
수신함
</label>

<label>
<input type="radio"
       name="emailReceiveYN"
       value="N"
       <%=memberDTO.getEmailReceiveYN()=='N' ? "checked" : ""%>>
수신안함
</label>

                <p>쇼핑몰에서 제공하는 유익한 이벤트 소식을 이메일로 받으실 수 있습니다.</p>
            </td>
        </tr>
    </table>


    <div class="edit-btn-area">
        <button type="button" id="cancelBtn" class="cancel-btn">취소</button>
		<button type="submit" class="save-btn">회원정보수정</button>

    </div>
    
</form>

</div>
</div>

<jsp:include page="../fragment/footer.jsp"/>
<style>
@import url("myPage.css");
</style>

</body>
</html>
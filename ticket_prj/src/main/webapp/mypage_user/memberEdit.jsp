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
out.println(loginMember.getMemberCode());
out.println("<br>");

out.println("SMS : [" + memberDTO.getSmsReceiveYN() + "]");
out.println("<br>");

out.println("EMAIL : [" + memberDTO.getEmailReceiveYN() + "]");
%>

<%
String[] phone = memberDTO.getPhone().split("-");
%>

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
                <input type="text" class="zip" name="zipcode" value="<%=memberDTO.getZipcode()%>">
                <button type="button" class="addr-btn">주소검색</button>
                <input type="text" name="address" value="<%=memberDTO.getAddress()%>">
                <input type="text" name="address2" value="<%=memberDTO.getAddress2()%>">
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
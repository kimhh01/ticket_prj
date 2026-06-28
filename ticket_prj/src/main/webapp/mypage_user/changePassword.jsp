<%@ page import="kr.user.member.MemberDTO" %>
<%@page import="userMypage.MyPageService"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

MemberDTO loginMember =
        (MemberDTO)session.getAttribute("loginMember");

if(loginMember == null){
    response.sendRedirect("../login/login.jsp");
    return;
}



String memberId = loginMember.getMemberCode();
String oldPass = request.getParameter("oldPass");
String newPass = request.getParameter("newPass");
String newPassCheck = request.getParameter("newPassCheck");

if(!newPass.equals(newPassCheck)){
%>

<script>
alert("새 비밀번호가 일치하지 않습니다.");
history.back();
</script>

<%
    return;
}

MyPageService mps = new MyPageService();

boolean flag = mps.updatePassword(
        memberId,
        oldPass,
        newPass,
        newPassCheck);
        
%>

<% if(flag){ %>

<script>
alert("비밀번호가 변경되었습니다.");
location.href="memberInfo.jsp";
</script>

<% }else{ %>

<script>
alert("현재 비밀번호가 올바르지 않습니다.");
history.back();
</script>

<% } %>
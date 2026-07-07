<%@ page import="kr.user.member.MemberDTO" %>
<%@page import="userMypage.MyPageService"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
request.setCharacterEncoding("UTF-8");

MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");

if(loginMember == null){
    response.sendRedirect(request.getContextPath() + "/login/login.jsp");
    return;
}

String memberCode = loginMember.getMemberCode();
String pass = request.getParameter("pass");

MyPageService service = new MyPageService();

if(service.checkPassword(memberCode, pass)){
    response.sendRedirect("memberEdit.jsp");
    return;
}
%>

<script>
alert("비밀번호가 일치하지 않습니다.");
history.back();
</script>
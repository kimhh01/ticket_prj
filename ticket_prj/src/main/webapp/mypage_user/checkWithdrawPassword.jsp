<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.user.member.MemberDTO" %>
<%@ page import="userMypage.MyPageService" %>

<%
request.setCharacterEncoding("UTF-8");

MemberDTO loginMember =
    (MemberDTO)session.getAttribute("loginMember");

if(loginMember == null){
    out.print("fail");
    return;
}

String memberCode = loginMember.getMemberCode();
String pass = request.getParameter("pass");

MyPageService service = new MyPageService();

if(service.checkPassword(memberCode, pass)){
    out.print("success");
}else{
    out.print("fail");
}
%>
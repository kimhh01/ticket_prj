<%@page import="userMypage.MyPageService"%>
<%@page import="kr.user.member.MemberDTO"%>
<%@page language="java"
contentType="text/plain; charset=UTF-8"
pageEncoding="UTF-8"%>

<%
MemberDTO loginMember =
(MemberDTO)session.getAttribute("loginMember");

MyPageService service =
new MyPageService();

boolean flag =
service.withdrawMember(loginMember.getMemberCode());

if(flag){
    session.invalidate();
}

out.print(flag ? "success" : "fail");
%>
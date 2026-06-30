<%@page import="userMypage.MyPageService"%>
<%@page import="kr.user.member.MemberDTO"%>
<%@page language="java"
        contentType="text/plain; charset=UTF-8"
        pageEncoding="UTF-8"%>

<%
MemberDTO loginMember =
    (MemberDTO)session.getAttribute("loginMember");

int reservationId =
    Integer.parseInt(request.getParameter("reservationId"));

MyPageService service = new MyPageService();

boolean flag =
    service.cancelReservation(
        reservationId,
        loginMember.getMemberCode());

out.print(flag ? "success" : "fail");
%>
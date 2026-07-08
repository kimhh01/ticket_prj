<%@page import="userMypage.MyPageService"%>
<%@page import="kr.user.member.MemberDTO"%>
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
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
%>

<% if(flag){ %>

<script>
alert("예매가 취소되었습니다.");
opener.location.reload();   // 부모창 새로고침
window.close();             // 상세창 닫기
</script>

<% } else { %>

<script>
alert("예매 취소에 실패했습니다.");
history.back();
</script>

<% } %>
<%@page import="kr.user.member.MemberDTO"%>
<%@page import="userEvent.CouponService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
MemberDTO loginMember =
    (MemberDTO)session.getAttribute("loginMember");

if(loginMember == null){
	
    response.sendRedirect("../kr/user/member/login.jsp");
    return;
}

String couponCode = request.getParameter("couponCode");

CouponService service = new CouponService();

boolean flag =
    service.downloadCoupon(
        loginMember.getMemberCode(),
        couponCode);
%>

<% if(flag){ %>

<script>
alert("쿠폰이 발급되었습니다.");
history.back();
</script>

<% } else { %>

<script>
alert("이미 발급받은 쿠폰입니다.");
history.back();
</script>

<% } %>
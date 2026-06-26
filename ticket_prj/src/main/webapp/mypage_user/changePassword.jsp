<%@page import="userMypage.MemberDTO"%>
<%@page import="userMypage.MyPageService"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

// 로그인 회원
MemberDTO loginMember =
        (MemberDTO)session.getAttribute("loginMember");

if(loginMember == null){
    response.sendRedirect("../login/login.jsp");
    return;
}

String memberId = loginMember.getMemberId();

String oldPass = request.getParameter("oldPass");
String newPass = request.getParameter("newPass");
String newPassCheck = request.getParameter("newPassCheck");

MyPageService mps = new MyPageService();

boolean flag = mps.updatePassword(
        memberId,
        oldPass,
        newPass,
        newPassCheck);
%>

<script>
<% if(flag){ %>
    alert("비밀번호가 변경되었습니다.");
    window.close();
<% } else { %>
    alert("현재 비밀번호를 확인해주세요.");
    history.back();
<% } %>
</script>
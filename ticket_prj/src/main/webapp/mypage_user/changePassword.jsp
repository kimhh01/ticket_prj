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


// 빈값 체크
if(oldPass == null || oldPass.trim().isEmpty() ||
   newPass == null || newPass.trim().isEmpty() ||
   newPassCheck == null || newPassCheck.trim().isEmpty()){
%>

<script>
alert("모든 비밀번호를 입력해주세요.");
history.back();
</script>

<%
    return;
}

// 비밀번호 형식 체크
String passPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,15}$";

if(!newPass.matches(passPattern)){
%>

<script>
alert("비밀번호는 8~15자리의 영문, 숫자, 특수문자를 모두 포함해야 합니다.");
history.back();
</script>

<%
    return;
}

//기존 비밀번호와 새 비밀번호 동일 여부 체크
if(oldPass.equals(newPass)){
%>

<script>
alert("새 비밀번호는 현재 비밀번호와 다르게 설정해주세요.");
history.back();
</script>

<%
 return;
}

// 새 비밀번호 확인
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
<% if(flag){ 

    // 세션 종료
    session.invalidate();

%>

<script>
alert("비밀번호가 변경되었습니다. 다시 로그인해주세요.");
location.href="<%=request.getContextPath()%>/kr/user/member/login.jsp";

</script>

<% }else{ %>

<script>
alert("현재 비밀번호가 올바르지 않습니다.");
history.back();
</script>

<% } %>
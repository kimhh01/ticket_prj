<%@page import="userMypage.MyPageService"%>
<%@page import="kr.user.member.MemberDTO"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

// 로그인 정보
MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");

if(loginMember == null){
    response.sendRedirect(request.getContextPath() + "/login/login.jsp");
    return;
}

//이메일 오류 검사
String email = request.getParameter("email");

String emailPattern =
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

if(email == null || !email.matches(emailPattern)){
%>

<script>
alert("올바른 이메일 형식으로 입력해주세요.");
history.back();
</script>

<%
    return;
}

// 수정할 정보
MemberDTO memberDTO = new MemberDTO();

memberDTO.setMemberCode(loginMember.getMemberCode());   // 로그인 아이디

memberDTO.setEmail(request.getParameter("email"));

String phone =
request.getParameter("phone1") + "-"
+ request.getParameter("phone2") + "-"
+ request.getParameter("phone3");

memberDTO.setPhone(phone);

memberDTO.setZipcode(Integer.parseInt(request.getParameter("zipcode")));
memberDTO.setAddress(request.getParameter("address"));
memberDTO.setAddress2(request.getParameter("address2"));

memberDTO.setSmsReceiveYN(
    request.getParameter("snsReceiveYN").charAt(0)
);

memberDTO.setEmailReceiveYN(
    request.getParameter("emailReceiveYN").charAt(0)
);

// 수정
MyPageService service = new MyPageService();

int result = service.updateMyInfo(memberDTO);

if(result > 0){
%>

<script>
alert("회원정보가 수정되었습니다.");
location.href="memberInfo.jsp";
</script>

<%
}else{
%>

<script>
alert("회원정보 수정에 실패했습니다.");
history.back();
</script>

<%
}
%>
<%@ page import="kr.admin.adminLogin.AdminDTO" %>
<%@ page import="kr.admin.adminLogin.AdminLoginService" %>
<%@ page import="kr.admin.adminLogin.AdminInfoDTO" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="adminDTO"
             class="kr.admin.adminLogin.AdminDTO"
             scope="page" />

<jsp:setProperty name="adminDTO"
                 property="*" />

<%
    String msg = "";

    if ("POST".equalsIgnoreCase(request.getMethod())) {

        request.setCharacterEncoding("UTF-8");

        String id = adminDTO.getId();
        String password = adminDTO.getPassword();

        if (id == null || id.trim().isEmpty()) {

            msg = "아이디를 입력해주세요.";

        } else if (password == null || password.trim().isEmpty()) {

            msg = "비밀번호를 입력해주세요.";

        } else {

            AdminLoginService service =
                    new AdminLoginService();

            AdminInfoDTO loginAdmin =
                    service.loginAdmin(adminDTO);

            if (loginAdmin != null) {

                /*
                 * 세션 유지 시간 설정
                 * 단위: 초
                 * 30분 = 60 * 30
                 */
                session.setMaxInactiveInterval(60*10);
                
                session.setAttribute("admin", loginAdmin);

                session.setAttribute(
                        "adminCode",
                        loginAdmin.getAdminCode());

                session.setAttribute(
                        "adminId",
                        String.valueOf(loginAdmin.getAdminCode()));

                session.setAttribute(
                        "loginAdminId",
                        loginAdmin.getId());

                session.setAttribute(
                        "adminName",
                        loginAdmin.getAdminName());

                response.sendRedirect(
                        request.getContextPath() + "/event");

                return;

            } else {

                msg = "아이디 또는 비밀번호가 일치하지 않습니다.";
            }
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 로그인</title>
<!-- <link rel="shortcut icon" href="http://192.168.10.95/jsp_prj/common/images/favicon.ico"/> -->

<!-- Bootstrap CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js" integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y" crossorigin="anonymous"></script>

<style type="text/css">

*{box-sizing:border-box;}

body{margin:0; background:#fff; font-family:"Noto Sans KR",sans-serif;color:#333; overflow: hidden;}

#wrap {
    width: 100%;
    height: 100vh;

    display: flex;
    flex-direction: column;
}

#header {
    height: 62px;
    flex-shrink: 0;

    border-bottom: 1px solid #ddd;

    display: flex;
    align-items: center;

    padding-left: 20px;

    font-weight: bold;
    font-size: 14px;
}

#container {
    flex: 1;

    display: flex;
    justify-content: center;
    align-items: center;

    /*
     * 로그인 박스 살짝 위로 이동
     */
    padding-bottom: 80px;
}

#footer {
    display: none;
}

.logo{width:140px;margin-right:10px;}
.login-area{width:100%;display:flex;justify-content:center;}
.login-box{width:350px;padding:30px 26px;border:1px solid #eee;border-radius:10px;
	box-shadow:0 4px 15px rgba(0,0,0,0.08);}
.input-box{height:42px;border:1px solid #ddd;border-radius:6px;display:flex;align-items:center;
	padding:0 10px;margin-bottom:18px;}
.input-box span{margin-right:8px;color:#777;}
.input-box input{border:0;outline:none;width:100%;font-size:13px;}
.notice{margin-top:18px;background:#f1f1ff;color:#777;border-radius:8px;
	padding:12px;font-size:11px;line-height:18px;}
.error{background:#ffe7e7;color:#d00;border-radius:5px;padding:10px;margin-bottom:15px;font-size:13px;}
	
h2{text-align:center;margin-bottom:8px;}
p{text-align:center;color:#777;font-size:13px;margin-bottom:25px;}
label{display:block;font-size:13px;font-weight:bold;margin-bottom:7px;}
button{width:100%;height:40px;border:0;border-radius:6px;background:#e9363f;
	color:white;font-weight:bold;cursor:pointer;}
button:hover{background:#d92f37;}


</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script type="text/javascript">
$(function(){
	$("#id").focus();
	$("#loginForm").submit(function(){
		let id = $("#id").val().trim();
		let pw = $("#password").val().trim();
		if(id==""){
			alert("아이디를 입력해주세요.");
			$("#id").focus();
			return false;
		}//end if
		
		if(pw==""){
			alert("비밀번호를 입력해주세요.");
			$("#password").focus();
			return false;
		}//end if
		
		return true;

	});
	
	$("#password").keyup(function(e){
		if(e.keyCode==13){
			$("#loginForm").submit();
		}//end if
	});
	
	$("input").keyup(function(){
		$(".error").fadeOut();
	});
	
});
</script>
</head>
<body>
<div id="wrap">
<div id="header">
	<img src="../images/logo.png" class="logo" alt="ticket">
	<span>관리자</span>
</div>

<div id="container">
	<div class="login-area">
	<div class="login-box">
	<h2>관리자 로그인</h2>
	<p>시스템 관리를 위해 계정 정보를 입력해주세요.</p>
	<%
	if(!msg.equals("")){
	%>
		<div class="error">
		<%= msg %>
		</div>
	<% } %>
	
	<form id="loginForm" method="post">
	<label>아이디</label>
	<div class="input-box">
		<input type="text" id="id" name="id" placeholder="아이디를 입력하세요" autocomplete="off">
	</div>
	<label>비밀번호</label>
	<div class="input-box">
		<input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요">
	</div>
	<button type="submit">로그인</button>
	</form>
	<div class="notice">
		인증되지 않은 접근은 법적 처벌을 받을 수 있습니다.<br>
		공용 PC 사용 시 보안에 유의하시기 바랍니다.
	</div>
	</div>
	</div>
</div>
</div>
<div id="footer">
</div>
</body>
</html>
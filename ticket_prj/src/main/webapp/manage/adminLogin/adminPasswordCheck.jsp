<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="kr.admin.adminLogin.AdminLoginService" %>

<%
    request.setCharacterEncoding("UTF-8");

    request.setAttribute("activeMenu", "mypage");

    String errorMsg = "";
    String successMsg = "";

    Integer adminCode = (Integer)session.getAttribute("adminCode");

    if (adminCode == null) {
        response.sendRedirect(request.getContextPath() + "/manage/adminLogin/adminLogin.jsp");
        return;
    }

    if ("POST".equalsIgnoreCase(request.getMethod())) {

        String adminPassword = request.getParameter("adminPassword");

        if (adminPassword == null || adminPassword.trim().isEmpty()) {

            errorMsg = "비밀번호를 입력해 주세요.";

        } else {

            AdminLoginService service = new AdminLoginService();

            boolean passwordFlag =
                    service.checkPasswordForMyPage(adminCode, adminPassword);

            if (passwordFlag) {

                session.setAttribute("mypagePasswordChecked", true);

                response.sendRedirect(
                    request.getContextPath() + "/manage/mypage/adminMyPage.jsp"
                );
                return;

            } else {

                errorMsg = "비밀번호가 일치하지 않습니다.";

            }
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 비밀번호 확인</title>

<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Noto Sans KR', sans-serif;
    background: #F5F5F5;
}

.layout {
    display: flex;
    min-height: 100vh;
}

.main {
    flex: 1;
    padding: 36px 40px;
}

.password-check-wrap {
    width: 100%;
    max-width: 520px;
    margin: 80px auto;
    background: #fff;
    border: 1px solid #E5E7EB;
    border-radius: 16px;
    padding: 36px;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.04);
}

.password-check-title {
    font-size: 24px;
    font-weight: 700;
    color: #111827;
    margin-bottom: 12px;
}

.password-check-desc {
    font-size: 14px;
    color: #6B7280;
    line-height: 1.6;
    margin-bottom: 28px;
}

.form-group {
    margin-bottom: 18px;
}

.form-label {
    display: block;
    font-size: 14px;
    font-weight: 600;
    color: #374151;
    margin-bottom: 8px;
}

.password-input {
    width: 100%;
    height: 44px;
    padding: 0 14px;
    border: 1px solid #D1D5DB;
    border-radius: 8px;
    font-size: 14px;
}

.password-input:focus {
    outline: none;
    border-color: #EF4444;
}

.message {
    margin-bottom: 18px;
    padding: 12px 14px;
    border-radius: 8px;
    font-size: 14px;
}

.error-message {
    background: #FEF2F2;
    color: #DC2626;
}

.success-message {
    background: #ECFDF5;
    color: #047857;
}

.button-area {
    display: flex;
    gap: 10px;
    justify-content: flex-end;
}

.btn {
    height: 42px;
    padding: 0 18px;
    border-radius: 8px;
    border: 0;
    cursor: pointer;
    font-size: 14px;
    font-weight: 600;
}

.btn-cancel {
    background: #F3F4F6;
    color: #374151;
}

.btn-submit {
    background: #EF4444;
    color: #fff;
}

.btn-cancel:hover {
    background: #E5E7EB;
}

.btn-submit:hover {
    background: #DC2626;
}
</style>

</head>

<body>

<div class="layout">

    <main class="main">

        <div class="password-check-wrap">

            <div class="password-check-title">
                비밀번호 확인
            </div>

            <div class="password-check-desc">
                관리자 정보 접근 전 본인 확인을 위해 비밀번호를 입력해 주세요.
            </div>

            <%
            if (errorMsg != null && !"".equals(errorMsg)) {
            %>
                <div class="message error-message">
                    <%= errorMsg %>
                </div>
            <%
            }
            %>

            <%
            if (successMsg != null && !"".equals(successMsg)) {
            %>
                <div class="message success-message">
                    <%= successMsg %>
                </div>
            <%
            }
            %>

            <form method="post" action="<%= request.getContextPath() %>/manage/adminLogin/adminPasswordCheck.jsp">

                <div class="form-group">

                    <label class="form-label" for="adminPassword">
                        비밀번호
                    </label>

                    <input type="password"
                           id="adminPassword"
                           name="adminPassword"
                           class="password-input"
                           placeholder="비밀번호를 입력하세요"
                           autocomplete="current-password"
                           required>

                </div>

                <div class="button-area">

                    <button type="button"
                            class="btn btn-cancel"
                            onclick="location.href='<%= request.getContextPath() %>/admin/dashboard';">
                        취소
                    </button>

                    <button type="submit"
                            class="btn btn-submit">
                        확인
                    </button>

                </div>

            </form>

        </div>

    </main>

</div>

</body>
</html>
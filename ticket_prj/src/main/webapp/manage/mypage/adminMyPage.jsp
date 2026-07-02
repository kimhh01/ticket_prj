<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="kr.admin.mypage.AdminMyPageDTO" %>
<%@page import="kr.admin.mypage.AdminMyPageService"%>

<%
    request.setCharacterEncoding("UTF-8");

    String contextPath = request.getContextPath();

    Integer adminCode = (Integer) session.getAttribute("adminCode");

    if (adminCode == null) {
        response.sendRedirect(contextPath + "/manage/adminLogin/adminLogin.jsp");
        return;
    }

    Boolean mypagePasswordChecked =
            (Boolean) session.getAttribute("mypagePasswordChecked");

    if (mypagePasswordChecked == null || !mypagePasswordChecked) {
        response.sendRedirect(contextPath + "/manage/adminLogin/adminPasswordCheck.jsp");
        return;
    }

    String errorMsg = "";
    String success = request.getParameter("success");

    AdminMyPageService service = new AdminMyPageService();

    if ("POST".equalsIgnoreCase(request.getMethod())) {

        String adminName = request.getParameter("adminName");
        String adminEmail = request.getParameter("adminEmail");
        String adminTel = request.getParameter("adminTel");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        adminName = adminName == null ? "" : adminName.trim();
        adminEmail = adminEmail == null ? "" : adminEmail.trim();
        adminTel = adminTel == null ? "" : adminTel.trim();
        newPassword = newPassword == null ? "" : newPassword.trim();
        confirmPassword = confirmPassword == null ? "" : confirmPassword.trim();

        if (adminName.isEmpty()) {

            errorMsg = "이름을 입력해 주세요.";

        } else if (adminEmail.isEmpty()) {

            errorMsg = "이메일을 입력해 주세요.";

        } else if (adminTel.isEmpty()) {

            errorMsg = "연락처를 입력해 주세요.";

        } else if (!newPassword.isEmpty()
                && !newPassword.equals(confirmPassword)) {

            errorMsg = "새 비밀번호가 일치하지 않습니다.";

        } else {

            AdminMyPageDTO updateAdmin = new AdminMyPageDTO();

            updateAdmin.setAdminCode(adminCode);
            updateAdmin.setAdminName(adminName);
            updateAdmin.setAdminEmail(adminEmail);
            updateAdmin.setAdminTel(adminTel);

            if (!newPassword.isEmpty()) {
                updateAdmin.setAdminPassword(newPassword);
            }

            boolean updateFlag = service.modifyAdminInfo(updateAdmin);

            if (updateFlag) {

                session.setAttribute("adminName", adminName);
                session.setAttribute("adminEmail", adminEmail);

                session.removeAttribute("mypagePasswordChecked");

                out.println("<script>");
                out.println("alert('관리자 정보가 수정되었습니다.');");
                out.println("location.href='" + contextPath + "/manage/main/adminMain.jsp';");
                out.println("</script>");
                return;

            } else {
                errorMsg = "관리자 정보 수정에 실패했습니다.";
            }
        }
    }

    AdminMyPageDTO admin = service.searchAdminInfo(adminCode);

    if (admin == null) {
        errorMsg = "관리자 정보를 찾을 수 없습니다.";
        admin = new AdminMyPageDTO();
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 정보 수정</title>

<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Noto Sans KR', Arial, sans-serif;
    background: #F5F6F8;
    color: #111827;
}

.page-wrap {
    width: 100%;
    min-height: 100vh;
    padding: 64px 24px;
}

.page-container {
    width: 100%;
    max-width: 980px;
    margin: 0 auto;
}

.page-title-area {
    margin-bottom: 24px;
}

.page-title {
    font-size: 28px;
    font-weight: 800;
    color: #111827;
    margin-bottom: 8px;
}

.page-desc {
    font-size: 14px;
    color: #6B7280;
}

.msg {
    margin-bottom: 16px;
    padding: 13px 16px;
    border-radius: 10px;
    font-size: 14px;
    font-weight: 600;
}

.msg.error {
    background: #FEF2F2;
    color: #DC2626;
    border: 1px solid #FECACA;
}

.msg.success {
    background: #EFF6FF;
    color: #2563EB;
    border: 1px solid #BFDBFE;
}

.mypage-card {
    background: #FFFFFF;
    border: 1px solid #E5E7EB;
    border-radius: 18px;
    padding: 34px;
    box-shadow: 0 10px 28px rgba(17, 24, 39, 0.06);
}

.mypage-content {
    display: grid;
    grid-template-columns: 260px 1fr;
    gap: 42px;
    align-items: flex-start;
}

.profile-card {
    min-height: 230px;
    border: 1px solid #E5E7EB;
    border-radius: 16px;
    background: #FAFAFA;
    padding: 28px 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.profile-avatar {
    width: 70px;
    height: 70px;
    border-radius: 50%;
    background: #EF4444;
    color: #FFFFFF;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 28px;
    font-weight: 800;
    margin-bottom: 16px;
}

.profile-name {
    font-size: 21px;
    font-weight: 800;
    color: #111827;
    margin-bottom: 10px;
}

.profile-role {
    border: 1px solid #EF4444;
    color: #EF4444;
    background: #FFF5F5;
    border-radius: 999px;
    padding: 5px 13px;
    font-size: 12px;
    font-weight: 700;
    margin-bottom: 14px;
}

.profile-email {
    font-size: 13px;
    color: #6B7280;
    word-break: break-all;
    text-align: center;
}

.form-area {
    width: 100%;
}

.form-row {
    margin-bottom: 18px;
}

.form-row label {
    display: block;
    font-size: 14px;
    font-weight: 700;
    color: #374151;
    margin-bottom: 8px;
}

.form-row input {
    width: 100%;
    height: 44px;
    border: 1px solid #D1D5DB;
    border-radius: 9px;
    padding: 0 14px;
    font-size: 14px;
    color: #111827;
    background: #FFFFFF;
}

.form-row input:focus {
    outline: none;
    border-color: #EF4444;
    box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.12);
}

.form-row input[readonly] {
    background: #F3F4F6;
    color: #6B7280;
    cursor: not-allowed;
}

.id-row {
    display: flex;
    align-items: center;
    gap: 10px;
}

.id-row input {
    flex: 1;
}

.lock-text {
    height: 44px;
    padding: 0 12px;
    border-radius: 9px;
    background: #F3F4F6;
    color: #6B7280;
    font-size: 13px;
    font-weight: 600;
    display: flex;
    align-items: center;
    white-space: nowrap;
}

.password-guide {
    margin-top: -8px;
    margin-bottom: 18px;
    font-size: 13px;
    color: #9CA3AF;
}

.button-area {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
    margin-top: 28px;
    padding-top: 24px;
    border-top: 1px solid #E5E7EB;
}

.btn {
    min-width: 120px;
    height: 44px;
    border-radius: 9px;
    font-size: 15px;
    font-weight: 700;
    cursor: pointer;
}

.btn-cancel {
    background: #FFFFFF;
    border: 1px solid #D1D5DB;
    color: #374151;
}

.btn-cancel:hover {
    background: #F9FAFB;
}

.btn-submit {
    background: #EF4444;
    border: 1px solid #EF4444;
    color: #FFFFFF;
}

.btn-submit:hover {
    background: #DC2626;
}

@media (max-width: 860px) {
    .mypage-content {
        grid-template-columns: 1fr;
    }

    .profile-card {
        min-height: auto;
    }

    .button-area {
        justify-content: stretch;
    }

    .btn {
        flex: 1;
    }
}
</style>

<script>
    function checkForm() {

        const adminName = document.getElementById("adminName");
        const adminEmail = document.getElementById("adminEmail");
        const adminTel = document.getElementById("adminTel");
        const newPassword = document.getElementById("newPassword");
        const confirmPassword = document.getElementById("confirmPassword");

        if (adminName.value.trim() === "") {
            alert("이름을 입력해 주세요.");
            adminName.focus();
            return false;
        }

        if (adminEmail.value.trim() === "") {
            alert("이메일을 입력해 주세요.");
            adminEmail.focus();
            return false;
        }

        if (adminTel.value.trim() === "") {
            alert("연락처를 입력해 주세요.");
            adminTel.focus();
            return false;
        }

        if (newPassword.value.trim() !== ""
                || confirmPassword.value.trim() !== "") {

            if (newPassword.value.trim() === "") {
                alert("새 비밀번호를 입력해 주세요.");
                newPassword.focus();
                return false;
            }

            if (confirmPassword.value.trim() === "") {
                alert("비밀번호 확인을 입력해 주세요.");
                confirmPassword.focus();
                return false;
            }

            if (newPassword.value !== confirmPassword.value) {
                alert("새 비밀번호가 일치하지 않습니다.");
                confirmPassword.focus();
                return false;
            }
        }

        return confirm("관리자 정보를 수정하시겠습니까?");
    }
</script>

</head>
<body>

<div class="page-wrap">

    <div class="page-container">

        <div class="page-title-area">
            <div class="page-title">관리자 정보 수정</div>
            <div class="page-desc">관리자 계정 정보를 확인하고 필요한 정보를 수정할 수 있습니다.</div>
        </div>

        <% if ("1".equals(success)) { %>
            <div class="msg success">관리자 정보가 수정되었습니다.</div>
        <% } %>

        <% if (errorMsg != null && !"".equals(errorMsg)) { %>
            <div class="msg error"><%= errorMsg %></div>
        <% } %>

        <form method="post"
              action="<%= contextPath %>/manage/mypage/adminMyPage.jsp"
              onsubmit="return checkForm();">

            <div class="mypage-card">

                <div class="mypage-content">

                    <div class="profile-card">
                        <div class="profile-avatar">
                            <%= admin.getAdminName() != null && admin.getAdminName().length() > 0
                                    ? admin.getAdminName().substring(0, 1)
                                    : "관" %>
                        </div>

                        <div class="profile-name">
                            <%= admin.getAdminName() %>
                        </div>

                        <div class="profile-role">
                            관리자
                        </div>

                        <div class="profile-email">
                            <%= admin.getAdminEmail() %>
                        </div>
                    </div>

                    <div class="form-area">

                        <div class="form-row">
                            <label for="adminId">관리자 ID</label>
                            <div class="id-row">
                                <input type="text"
                                       id="adminId"
                                       value="<%= admin.getAdminId() %>"
                                       readonly>
                                <span class="lock-text">수정 불가</span>
                            </div>
                        </div>

                        <div class="form-row">
                            <label for="adminName">이름</label>
                            <input type="text"
                                   id="adminName"
                                   name="adminName"
                                   value="<%= admin.getAdminName() %>">
                        </div>

                        <div class="form-row">
                            <label for="adminEmail">이메일</label>
                            <input type="email"
                                   id="adminEmail"
                                   name="adminEmail"
                                   value="<%= admin.getAdminEmail() %>">
                        </div>

                        <div class="form-row">
                            <label for="adminTel">연락처</label>
                            <input type="text"
                                   id="adminTel"
                                   name="adminTel"
                                   value="<%= admin.getAdminTel() %>">
                        </div>

                        <div class="form-row">
                            <label for="newPassword">새 비밀번호</label>
                            <input type="password"
                                   id="newPassword"
                                   name="newPassword"
                                   autocomplete="new-password"
                                   placeholder="변경하지 않으려면 비워두세요.">
                        </div>

                        <div class="form-row">
                            <label for="confirmPassword">비밀번호 확인</label>
                            <input type="password"
                                   id="confirmPassword"
                                   name="confirmPassword"
                                   autocomplete="new-password"
                                   placeholder="새 비밀번호를 한 번 더 입력하세요.">
                        </div>

                        <div class="password-guide">
                            비밀번호를 변경하지 않으려면 새 비밀번호와 비밀번호 확인을 비워두면 됩니다.
                        </div>

                    </div>

                </div>

                <div class="button-area">
                    <button type="button"
                            class="btn btn-cancel"
                            onclick="location.href='<%= contextPath %>/manage/main/adminMain.jsp'">
                        취소
                    </button>

                    <button type="submit"
                            class="btn btn-submit">
                        수정
                    </button>
                </div>
 
            </div>

        </form>

    </div>

</div>

</body>
</html>
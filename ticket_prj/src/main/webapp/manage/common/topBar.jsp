<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="kr.admin.adminLogin.AdminInfoDTO" %>

<%!
    private String topbarH(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;");
    }
%>

<%
    AdminInfoDTO loginAdmin =
            (AdminInfoDTO) session.getAttribute("admin");

    String adminName = "관리자";

    if (loginAdmin != null &&
        loginAdmin.getAdminName() != null &&
        !loginAdmin.getAdminName().trim().isEmpty()) {

        adminName = loginAdmin.getAdminName();
    }

    int sessionSeconds =
            session.getMaxInactiveInterval();

    if (sessionSeconds <= 0) {
        sessionSeconds = 600;
    }
%>

<header class="topbar">
    <div class="topbar-left">
        <span class="topbar-logo">ticket<strong>LINK</strong></span>
        <span class="topbar-subtitle">야구 관리자</span>
    </div>

    <div class="topbar-right">

        <a href="${pageContext.request.contextPath}/manage/adminLogin/adminPasswordCheck.jsp"
           class="topbar-admin-name">
            <%= topbarH(adminName) %>님
        </a>

        <span class="session-timer">
            남은 시간
            <strong id="sessionTimer"></strong>
        </span>

        <button type="button"
                class="session-extend-btn"
                id="sessionExtendBtn">
            연장
        </button>

        <div class="topbar-divider"></div>

        <a href="${pageContext.request.contextPath}/manage/adminLogin/adminLogout.jsp">
            로그아웃
        </a>
    </div>
</header>

<script>
(function(){

    let remainSeconds = <%= sessionSeconds %>;

    const timerEl =
            document.getElementById("sessionTimer");

    const extendBtn =
            document.getElementById("sessionExtendBtn");

    function formatTime(seconds) {

        const min =
                Math.floor(seconds / 60);

        const sec =
                seconds % 60;

        return String(min).padStart(2, "0")
                + ":"
                + String(sec).padStart(2, "0");
    }

    function renderTimer() {

        if (timerEl) {
            timerEl.textContent =
                    formatTime(remainSeconds);
        }
    }

    renderTimer();

    const timerInterval =
            setInterval(function(){

                remainSeconds--;

                if (remainSeconds <= 0) {

                    clearInterval(timerInterval);

                    alert("세션이 만료되었습니다. 다시 로그인해주세요.");

                    location.href =
                            "${pageContext.request.contextPath}/manage/adminLogin/adminLogout.jsp";

                    return;
                }

                renderTimer();

            }, 1000);

    if (extendBtn) {

        extendBtn.addEventListener("click", function(){

            fetch("${pageContext.request.contextPath}/manage/adminLogin/adminSessionExtend.jsp", {
                method: "POST",
                cache: "no-store"
            })
            .then(function(response){

                if (response.status === 401) {
                    location.href =
                            "${pageContext.request.contextPath}/manage/adminLogin/adminLogin.jsp";
                    return null;
                }

                return response.json();
            })
            .then(function(data){

                if (!data) {
                    return;
                }

                if (data.result === "success") {

                    remainSeconds =
                            data.seconds;

                    renderTimer();

                } else {

                    location.href =
                            "${pageContext.request.contextPath}/manage/adminLogin/adminLogin.jsp";
                }
            })
            .catch(function(){

                alert("세션 연장 중 오류가 발생했습니다.");
            });
        });
    }

})();
</script>
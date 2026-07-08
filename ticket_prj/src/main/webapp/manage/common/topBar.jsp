<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="kr.admin.adminLogin.AdminInfoDTO" %>

<style>

/* ── Topbar ── */
.topbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 24px;
    height: 56px;
    background: #fff;
    border-bottom: 1px solid #E5E7EB;
    position: sticky;
    top: 0;
    z-index: 100;
}

.topbar-left {
    display: flex;
    align-items: center;
    gap: 8px;
}

.topbar-logo {
    font-size: 18px;
    font-weight: 400;
    color: #111;
}

.topbar-logo strong {
    font-weight: 700;
}

.topbar-subtitle {
    font-size: 13px;
    color: #6B7280;
}

.topbar-right {
    display: flex;
    align-items: center;
    gap: 16px;
    font-size: 13px;
    color: #6B7280;
}

.topbar-divider {
    width: 1px;
    height: 14px;
    background: #E5E7EB;
}

.topbar-right a {
    color: #6B7280;
    text-decoration: none;
}

.topbar-right a:hover {
    color: #111;
}

.topbar-logo {
	color: #e50020;
	font-weight: 900px;
}

/* 세션 표시용 디자인 */
.topbar-admin-name {
    color: #333;
    text-decoration: none;
    font-weight: 600;
}

.topbar-admin-name:hover {
    color: #e9363f;
    text-decoration: underline;
}

.session-timer {
    margin-left: 14px;
    font-size: 13px;
    color: #666;
}

.session-timer strong {
    margin-left: 5px;
    color: #e9363f;
}

.session-extend-btn {
    margin-left: 6px;
    padding: 4px 9px;

    border: 1px solid #ddd;
    border-radius: 5px;

    background: #fff;
    color: #333;

    font-size: 12px;
    cursor: pointer;
}

.session-extend-btn:hover {
    background: #f5f5f5;
}
</style>

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
        <span class="topbar-logo"><strong>BallPick⚾</strong></span>
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
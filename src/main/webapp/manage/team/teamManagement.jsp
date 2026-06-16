<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="teamManagement.ScheduleListDTO" %>
<%@ page import="teamManagement.TeamInfoDTO" %>
<%@ page import="common.TeamOptionDTO" %>
<%@ page import="common.StadiumOptionDTO" %>
<%
// 활성 메뉴 설정 → sidebar.jsp에서 참조
    request.setAttribute("activeMenu", "teamManagement");

    // Service에서 전달받은 데이터 (Servlet에서 setAttribute로 넘겨줌)
    List<ScheduleListDTO> scheduleList = (List<ScheduleListDTO>) request.getAttribute("scheduleList");
    List<TeamInfoDTO>     teamList     = (List<TeamInfoDTO>)     request.getAttribute("teamList");
    List<TeamOptionDTO> teamOptionList =
    	    (List<TeamOptionDTO>) request.getAttribute("teamOptionList");
    	List<StadiumOptionDTO> stadiumOptionList =
    	    (List<StadiumOptionDTO>) request.getAttribute("stadiumOptionList");

    // null 방어
    if (scheduleList == null) scheduleList = new java.util.ArrayList<>();
    if (teamList     == null) teamList     = new java.util.ArrayList<>();
    if (teamOptionList == null) teamOptionList = new java.util.ArrayList<>();
    if (stadiumOptionList == null) stadiumOptionList = new java.util.ArrayList<>();
    

    // 탭 상태 유지 (기본: schedule)
    String activeTab = request.getParameter("tab");
    if (activeTab == null || activeTab.isEmpty()) activeTab = "schedule";
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>야구 팀 관리 - ticketLINK</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/tabler-icons.min.css">
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Noto Sans KR', sans-serif; background: #F5F5F5; }

        /* ── Topbar ── */
        .topbar {
            display: flex; align-items: center; justify-content: space-between;
            padding: 0 24px; height: 56px;
            background: #fff;
            border-bottom: 1px solid #E5E7EB;
            position: sticky; top: 0; z-index: 100;
        }
        .topbar-left { display: flex; align-items: center; gap: 8px; }
        .topbar-logo { font-size: 18px; font-weight: 400; color: #111; }
        .topbar-logo strong { font-weight: 700; }
        .topbar-subtitle { font-size: 13px; color: #6B7280; }
        .topbar-right { display: flex; align-items: center; gap: 16px; font-size: 13px; color: #6B7280; }
        .topbar-divider { width: 1px; height: 14px; background: #E5E7EB; }
        .topbar-right a { color: #6B7280; text-decoration: none; }
        .topbar-right a:hover { color: #111; }

        /* ── Layout ── */
        .layout { display: flex; min-height: calc(100vh - 56px); }

        /* ── Sidebar ── */
        .sidebar {
            width: 200px; flex-shrink: 0;
            background: #fff;
            border-right: 1px solid #E5E7EB;
            padding: 12px 0;
        }
        .nav-item {
            display: flex; align-items: center; gap: 10px;
            padding: 10px 20px;
            font-size: 14px; color: #6B7280;
            cursor: pointer;
        }
        .nav-item i { font-size: 18px; }
        .nav-item:hover { background: #F9FAFB; color: #111; }
        .nav-item.active { background: #FDEDF0; color: #C0394B; font-weight: 500; }

        /* ── Main ── */
        .main { flex: 1; padding: 28px 32px; }
        .page-title { font-size: 20px; font-weight: 500; margin-bottom: 20px; color: #111; }

        /* ── Card ── */
        .card {
            background: #fff;
            border: 1px solid #E5E7EB;
            border-radius: 12px;
            overflow: hidden;
        }

        /* ── Tabs ── */
        .tabs { display: flex; border-bottom: 1px solid #E5E7EB; padding: 0 20px; }
        .tab {
            padding: 14px 16px 12px;
            font-size: 14px; color: #6B7280;
            cursor: pointer; text-decoration: none;
            border-bottom: 2px solid transparent;
            margin-bottom: -1px;
            display: inline-block;
        }
        .tab.active { color: #2563EB; border-bottom-color: #2563EB; font-weight: 500; }
        .tab:hover:not(.active) { color: #111; }

        /* ── Section Header ── */
        .section-header {
            display: flex; align-items: center; justify-content: space-between;
            padding: 20px 20px 16px;
        }
        .section-title { font-size: 15px; font-weight: 500; color: #111; }
        .add-btn {
            padding: 9px 20px;
            background: #fff;
            border: 1.5px solid #D1D5DB;
            border-radius: 8px;
            font-size: 14px; font-weight: 500; color: #111;
            cursor: pointer; text-decoration: none;
        }
        .add-btn:hover { background: #F9FAFB; }

        /* ── Table ── */
        table { width: 100%; border-collapse: collapse; }
        thead tr { border-top: 1px solid #E5E7EB; }
        th {
            text-align: left; padding: 10px 16px;
            font-size: 13px; font-weight: 400; color: #6B7280;
            border-bottom: 1px solid #E5E7EB;
        }
        td {
            padding: 14px 16px;
            font-size: 14px; color: #111;
            border-bottom: 1px solid #F3F4F6;
            vertical-align: middle;
        }
        tr:last-child td { border-bottom: none; }
        tbody tr:hover td { background: #F9FAFB; }

        /* ── Team Badge ── */
        .matchup { display: flex; align-items: center; gap: 6px; }
        .team-badge {
            display: inline-flex; align-items: center; justify-content: center;
            padding: 3px 8px; border-radius: 4px;
            font-size: 12px; font-weight: 600; color: #fff;
            min-width: 36px;
        }
        .vs { font-size: 11px; color: #9CA3AF; }

        /* ── Ticket Status ── */
        .ticket-status {
            display: inline-flex; align-items: center; justify-content: center;
            padding: 4px 12px; border-radius: 20px;
            font-size: 12px; font-weight: 500; min-width: 56px;
        }
        .status-on-sale  { background: #D1FAE5; color: #065F46; }
        .status-ended    { background: #E5E7EB; color: #6B7280; }
        .status-scheduled{ background: #DBEAFE; color: #1E40AF; }

        /* ── Edit Button ── */
        .edit-btn {
            background: none; border: 1px solid #E5E7EB;
            border-radius: 6px; padding: 5px 7px;
            cursor: pointer; color: #6B7280;
            display: inline-flex; align-items: center;
        }
        .edit-btn:hover { background: #F9FAFB; color: #111; }
        .edit-btn i { font-size: 15px; }

        /* ── Empty State ── */
        .empty-body {
            padding: 64px 20px;
            display: flex; flex-direction: column; align-items: center; gap: 10px;
        }
        .empty-icon { font-size: 36px; color: #D1D5DB; }
        .empty-text { font-size: 14px; color: #6B7280; }
        .empty-sub  { font-size: 13px; color: #9CA3AF; }
        
        /* ===== 일정 추가 모달 ===== */

		.modal-overlay {
		    display: none;
		    position: fixed;
		    inset: 0;
		
		    background: rgba(0, 0, 0, 0.45);
		
		    z-index: 9999;
		
		    justify-content: center;
		    align-items: center;
		
		    padding: 20px;
		    box-sizing: border-box;
		}
		
		.modal-overlay.show {
		    display: flex;
		}
		
		.modal {
		    width: 640px;
		    max-width: 100%;
		
		    background: #ffffff;
		    border-radius: 16px;
		
		    box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
		
		    overflow: hidden;
		
		    animation: modalFadeIn 0.2s ease-out;
		}
		
		@keyframes modalFadeIn {
		    from {
		        opacity: 0;
		        transform: translateY(12px);
		    }
		
		    to {
		        opacity: 1;
		        transform: translateY(0);
		    }
		}
		
		.modal-header {
		    display: flex;
		    justify-content: space-between;
		    align-items: center;
		
		    padding: 24px;
		
		    border-bottom: 1px solid #e5e7eb;
		}
		
		.modal-title {
		    margin: 0;
		
		    font-size: 20px;
		    font-weight: 700;
		
		    color: #111827;
		}
		
		.modal-close {
		    border: none;
		    background: transparent;
		
		    font-size: 28px;
		    line-height: 1;
		
		    cursor: pointer;
		
		    color: #6b7280;
		}
		
		.modal-close:hover {
		    color: #111827;
		}
		
		.modal-body {
		    padding: 24px;
		}
		
		.form-section + .form-section {
		    margin-top: 28px;
		}
		
		.form-section-title {
		    margin-bottom: 16px;
		
		    font-size: 15px;
		    font-weight: 700;
		
		    color: #374151;
		}
		
		.form-row {
		    display: flex;
		    gap: 16px;
		
		    margin-bottom: 16px;
		}
		
		.form-group {
		    flex: 1;
		}
		
		.form-label {
		    display: block;
		
		    margin-bottom: 8px;
		
		    font-size: 14px;
		    font-weight: 500;
		
		    color: #374151;
		}
		
		.form-label.required::after {
		    content: " *";
		    color: #ef4444;
		}
		
		.form-control {
		    width: 100%;
		    height: 44px;
		
		    padding: 0 14px;
		
		    border: 1px solid #d1d5db;
		    border-radius: 8px;
		
		    font-size: 14px;
		
		    box-sizing: border-box;
		
		    transition: border-color 0.2s ease;
		}
		
		.form-control:focus {
		    outline: none;
		    border-color: #2563eb;
		
		    box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.15);
		}
		
		.modal-footer {
		    display: flex;
		    justify-content: flex-end;
		    gap: 12px;
		
		    padding: 20px 24px;
		
		    border-top: 1px solid #e5e7eb;
		}
		
		.btn-cancel,
		.btn-submit {
		    min-width: 100px;
		    height: 44px;
		
		    border-radius: 8px;
		
		    font-size: 14px;
		    font-weight: 600;
		
		    cursor: pointer;
		
		    transition: all 0.2s ease;
		}
		
		.btn-cancel {
		    background: #ffffff;
		
		    border: 1px solid #d1d5db;
		
		    color: #4b5563;
		}
		
		.btn-cancel:hover {
		    background: #f9fafb;
		}
		
		.btn-submit {
		    background: #2563eb;
		
		    border: none;
		
		    color: #ffffff;
		}
		
		.btn-submit:hover {
		    background: #1d4ed8;
		}
		
		@media (max-width: 768px) {
		
		    .modal {
		        width: 100%;
		    }
		
		    .form-row {
		        flex-direction: column;
		        gap: 12px;
		    }
		
		    .modal-footer {
		        flex-direction: column;
		    }
		
		    .btn-cancel,
		    .btn-submit {
		        width: 100%;
		    }
		}
    </style>
</head>
<body>

<%-- ── 공용 상단바 include ── --%>
<%@ include file="../common/topBar.jsp" %>

<div class="layout">

    <%-- ── 공용 사이드바 include ── --%>
    <%@ include file="../common/sideBar.jsp" %>

    <main class="main">
        <h1 class="page-title">야구 팀 관리</h1>

        <div class="card">

            <%-- ── 탭 ── --%>
            <div class="tabs">
                <a class="tab <%=activeTab.equals("schedule") ? "active" : ""%>"
                   href="?tab=schedule">경기 일정 관리</a>
                <a class="tab <%=activeTab.equals("teamlist") ? "active" : ""%>"
                   href="?tab=teamlist">팀 목록</a>
            </div>

            <%-- ════════════════════════════════
                 탭 1 : 경기 일정 관리
            ════════════════════════════════ --%>
            <%
            if (activeTab.equals("schedule")) {
            %>

            <div class="section-header">
                <span class="section-title">경기 일정</span>
				<button type="button" class="add-btn" id="openScheduleModal">
				    + 일정 추가
				</button>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>경기일</th>
                        <th>대진</th>
                        <th>구장</th>
                        <th>티켓</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    if (scheduleList.isEmpty()) {
                    %>
                    <tr>
                        <td colspan="5">
                            <div class="empty-body">
                                <i class="ti ti-calendar-off empty-icon" aria-hidden="true"></i>
                                <p class="empty-text">등록된 경기 일정이 없습니다.</p>
                                <p class="empty-sub">DB 연결 후 데이터가 표시됩니다.</p>
                            </div>
                        </td>
                    </tr>
                    <%
                    } else {
                    %>
                        <%
                        for (ScheduleListDTO s : scheduleList) {
                        %>
                        <tr>
                            <td><%=s.getGameDate()%></td>
                            <td>
                                <div class="matchup">
                                    <span class="team-badge team-<%=s.getHomeTeam().toLowerCase()%>">
                                        <%=s.getHomeTeam()%>
                                    </span>
                                    <span class="vs">vs</span>
                                    <span class="team-badge team-<%=s.getAwayTeam().toLowerCase()%>">
                                        <%=s.getAwayTeam()%>
                                    </span>
                                </div>
                            </td>
                            <td><%=s.getStadiumName()%></td>
                            <td>
                                <%
                                String status = s.getSalesState();
                                                                                                    String statusClass = "";
                                                                                                    String statusLabel = "";
                                                                                                    if ("판매중".equals(status)) {
                                                                                                        statusClass = "status-on-sale";
                                                                                                        statusLabel = "판매중";
                                                                                                    } else if ("종료".equals(status)) {
                                                                                                        statusClass = "status-ended";
                                                                                                        statusLabel = "종료";
                                                                                                    } else {
                                                                                                        statusClass = "status-scheduled";
                                                                                                        statusLabel = "예정";
                                                                                                    }
                                %>
                                <span class="ticket-status <%=statusClass%>"><%=statusLabel%></span>
                            </td>
                            <td>
                                <button class="edit-btn"
                                        aria-label="수정"
                                        onclick="location.href='${pageContext.request.contextPath}/teamManagement/scheduleEdit?gameScheduleCode=<%= s.getGameScheduleCode() %>'">
                                    <i class="ti ti-edit"></i>
                                </button>
                            </td>
                        </tr>
                        <%
                        }
                        %>
                    <%
                    }
                    %>
                </tbody>
            </table>

            <%-- ════════════════════════════════
                 탭 2 : 팀 목록
            ════════════════════════════════ --%>
            <%
            } else if (activeTab.equals("teamlist")) {
            %>

            <div class="section-header">
                <span class="section-title">팀 목록</span>
                <a class="add-btn" href="${pageContext.request.contextPath}/teamManagement/teamAdd">
                    + 팀 등록
                </a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th style="width:80px;">마크</th>
                        <th>팀 이름</th>
                        <th>홈 경기장</th>
                        <th style="width:60px;"></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    if (teamList.isEmpty()) {
                    %>
                    <tr>
                        <td colspan="4">
                            <div class="empty-body">
                                <i class="ti ti-users-group empty-icon" aria-hidden="true"></i>
                                <p class="empty-text">등록된 팀이 없습니다.</p>
                                <p class="empty-sub">DB 연결 후 데이터가 표시됩니다.</p>
                            </div>
                        </td>
                    </tr>
                    <%
                    } else {
                    %>
                        <%
                        for (TeamInfoDTO t : teamList) {
                        %>
                        <tr>
                            <td>
                                <%
                                if (t.getTeamLogoImg() != null && !t.getTeamLogoImg().isEmpty()) {
                                %>
                                <img src="${pageContext.request.contextPath}/<%= t.getTeamLogoImg() %>"
                                     alt="<%= t.getTeamName() %> 로고"
                                     style="width:44px; height:44px; object-fit:cover; border-radius:10px;">
                                <%
                                } else {
                                %>
                                <div style="width:44px; height:44px; border-radius:10px;
                                            background:#E5E7EB; display:flex; align-items:center;
                                            justify-content:center; font-size:11px; color:#9CA3AF;">
                                    없음
                                </div>
                                <%
                                }
                                %>
                            </td>
                            <td style="font-size:15px; font-weight:500;"><%=t.getTeamName()%></td>
                            <td style="color:#374151;"><%=t.getStadiumName()%></td>
                            <td>
                                <button class="edit-btn"
                                        aria-label="수정"
                                        onclick="location.href='${pageContext.request.contextPath}/teamManagement/teamEdit?teamCode=<%= t.getTeamCode() %>'">
                                    <i class="ti ti-edit"></i>
                                </button>
                            </td>
                        </tr>
                        <%
                        }
                        %>
                    <%
                    }
                    %>
                </tbody>
            </table>

            <%
            }
            %>

        </div><%-- /card --%>
        <%@ include file="fragments/scheduleAddModal.jspf" %>
    </main>

</div><%-- /layout --%>
<script>
    const scheduleModal = document.getElementById('scheduleModal');

    function openScheduleModal() {
        scheduleModal.classList.add('show');
    }

    function closeScheduleModal() {
        scheduleModal.classList.remove('show');
    }

    const openBtn = document.getElementById('openScheduleModal');
    const closeBtn = document.getElementById('closeScheduleModal');
    const cancelBtn = document.getElementById('cancelScheduleModal');

    if (openBtn) {
        openBtn.addEventListener('click', openScheduleModal);
    }

    if (closeBtn) {
        closeBtn.addEventListener('click', closeScheduleModal);
    }

    if (cancelBtn) {
        cancelBtn.addEventListener('click', closeScheduleModal);
    }

    if (scheduleModal) {
        scheduleModal.addEventListener('click', function(e) {
            if (e.target === scheduleModal) {
                closeScheduleModal();
            }
        });
    }

    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            closeScheduleModal();
        }
    });
</script>
</body>
</html>
s
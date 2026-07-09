<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.net.URLEncoder" %>

<%@ page import="kr.admin.ticket.TicketSearchDTO" %>
<%@ page import="kr.admin.common.BoardRangeDTO" %>

<%!
    private String h(Object value) {
        if (value == null) {
            return "";
        }

        return String.valueOf(value)
                     .replace("&", "&amp;")
                     .replace("<", "&lt;")
                     .replace(">", "&gt;")
                     .replace("\"", "&quot;")
                     .replace("'", "&#39;");
    }

    private String enc(String value) {
        try {
            if (value == null) {
                return "";
            }

            return URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    private String selected(String value, String target) {
        if (value == null) {
            value = "";
        }

        if (target == null) {
            target = "";
        }

        return value.equals(target) ? "selected=\"selected\"" : "";
    }
    
    private String statusClass(String state) {
        if (state == null || state.trim().isEmpty() || "-".equals(state)) {
            return "status-end";
        }

        if (state.contains("예정")) {
            return "status-ready";
        }

        if (state.contains("취소") || state.contains("매진") || state.contains("종료")) {
            return "status-end";
        }

        return "status-progress";
    }
%>

<%
    request.setAttribute("activeMenu", "ticket");

    String contextPath = request.getContextPath();

    BoardRangeDTO range =
            (BoardRangeDTO)request.getAttribute("range");

    List<TicketSearchDTO> searchList =
            (List<TicketSearchDTO>)request.getAttribute("searchList");

    List<String> teamNameList =
            (List<String>)request.getAttribute("teamNameList");

    List<String> stadiumNameList =
            (List<String>)request.getAttribute("stadiumNameList");

    if (teamNameList == null) {
        teamNameList = Arrays.asList(
                "LG", "KIA", "삼성", "두산", "롯데",
                "SSG", "KT", "한화", "NC", "키움"
        );
    }

    if (stadiumNameList == null) {
        stadiumNameList = Arrays.asList();
    }

    String startDate = (String)request.getAttribute("startDate");
    String endDate = (String)request.getAttribute("endDate");
    String team = (String)request.getAttribute("team");
    String stadium = (String)request.getAttribute("stadium");
    String phone = (String)request.getAttribute("phone");

    if (startDate == null) {
        startDate = "";
    }

    if (endDate == null) {
        endDate = "";
    }

    if (team == null) {
        team = "";
    }

    if (stadium == null) {
        stadium = "";
    }

    if (phone == null) {
        phone = "";
    }

    if (range == null) {
        range = new BoardRangeDTO();
        range.setPage(1);
        range.setTotalPage(1);
        range.setTotalCount(0);
    }

    String pageQuery =
            "startDate=" + enc(startDate)
          + "&endDate=" + enc(endDate)
          + "&team=" + enc(team)
          + "&stadium=" + enc(stadium)
          + "&phone=" + enc(phone);

    String returnUrl =
            contextPath
          + "/admin/ticket/search?"
          + pageQuery
          + "&page="
          + range.getPage();

    NumberFormat nf = NumberFormat.getInstance();
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>예매 티켓 검색</title>

<style>
*{
    margin:0;
    padding:0;
    box-sizing:border-box;
}

body{
    font-family:'Noto Sans KR', sans-serif;
    background:#F5F5F5;
}

.layout{
    display:flex;
    min-height:calc(100vh - 56px);
}

.main{
    flex:1;
    padding:28px 32px;
}

.page-header{
    display:flex;
    justify-content:space-between;
    align-items:center;
    margin-bottom:20px;
}

.page-title{
    font-size:35px;
    font-weight:700;
    color:#111827;
}

.back-btn{
    display:flex;
    align-items:center;
    gap:6px;

    border:1px solid #D1D5DB;
    background:#fff;
    color:#374151;

    padding:11px 18px;
    border-radius:8px;

    font-size:14px;
    font-weight:600;

    cursor:pointer;
}

.back-btn:hover{
    background:#F9FAFB;
}

.card{
    background:#fff;
    border:1px solid #E5E7EB;
    border-radius:12px;
    overflow:hidden;
}

.tabs{
    display:flex;
    gap:28px;
    padding:20px 24px 0;
    border-bottom:1px solid #E5E7EB;
}

.tab{
    padding:0 4px 16px;
    text-decoration:none;
    color:#6B7280;
    font-size:14px;
    font-weight:700;
}

.tab.active{
    color:#EF4444;
    border-bottom:2px solid #EF4444;
}

.search-area{
    padding:24px;
    border-bottom:1px solid #E5E7EB;
}

.search-title{
    font-size:18px;
    font-weight:700;
    color:#111827;
    margin-bottom:20px;
}

.search-grid{
    display:grid;
    grid-template-columns:260px 180px 180px 220px;
    gap:18px;
    align-items:end;
}

.field label{
    display:block;
    font-size:13px;
    font-weight:600;
    color:#374151;
    margin-bottom:8px;
}

.field input,
.field select{
    width:100%;
    height:38px;

    border:1px solid #D1D5DB;
    border-radius:8px;

    padding:0 10px;
    background:#fff;
}

.date-wrap{
    display:flex;
    gap:6px;
}

.btn-area{
    display:flex;
    justify-content:flex-end;
    gap:8px;
    margin-top:18px;
}

.btn{
    height:38px;
    padding:0 16px;

    border-radius:8px;

    font-size:14px;
    font-weight:600;

    cursor:pointer;
}

.btn-search{
    border:none;
    background:#EF4444;
    color:#fff;
}

.btn-search:hover{
    background:#DC2626;
}

.btn-reset{
    border:1px solid #D1D5DB;
    background:#fff;
    color:#374151;
}

.btn-reset:hover{
    background:#F9FAFB;
}

.result-header{
    display:flex;
    justify-content:space-between;
    align-items:center;

    padding:18px 24px;
    border-bottom:1px solid #E5E7EB;
}

.result-title{
    font-size:18px;
    font-weight:700;
    color:#111827;
}

.result-count{
    color:#6B7280;
    font-size:13px;
}

.ticket-table{
    width:100%;
    border-collapse:collapse;
}

.ticket-table thead{
    background:#F9FAFB;
}

.ticket-table th{
    padding:18px 12px;

    font-size:14px;
    font-weight:600;
    color:#6B7280;

    border-bottom:1px solid #E5E7EB;
}

.ticket-table td{
    padding:16px 12px;

    text-align:center;

    font-size:14px;
    color:#374151;

    border-bottom:1px solid #F3F4F6;
}

.ticket-table tbody tr:hover{
    background:#FAFAFA;
}

.action-area{
    display:flex;
    justify-content:center;
    gap:8px;
}

.cancel-btn{
    width:56px;
    height:32px;

    border-radius:6px;

    background:#fff;
    border:1px solid #EF4444;
    color:#EF4444;

    font-size:13px;
    cursor:pointer;
}

.cancel-btn:hover{
    background:#FEF2F2;
}

.empty-body{
    padding:80px 20px;

    display:flex;
    flex-direction:column;
    align-items:center;

    gap:12px;

    text-align:center;
}

.empty-icon{
    font-size:42px;
    color:#D1D5DB;
}

.empty-text{
    color:#6B7280;
    font-size:15px;
}

.empty-sub{
    color:#9CA3AF;
    font-size:13px;
}

.pagination{
    display:flex;
    justify-content:center;
    gap:8px;

    padding:24px;
}

.page-btn{
    width:36px;
    height:36px;

    border:1px solid #D1D5DB;
    border-radius:8px;

    background:#fff;

    cursor:pointer;
}

.page-btn:hover{
    background:#F9FAFB;
}

.page-btn.active{
    border-color:#EF4444;
    color:#EF4444;
    font-weight:700;
}

.status{
    font-size:13px;
    font-weight:700;
}

.status-progress{
    color:#EF4444;
}

.status-ready{
    color:#2563EB;
}

.status-end{
    color:#9CA3AF;
}

.cancel-done{
    display:inline-flex;
    align-items:center;
    justify-content:center;

    min-width:56px;
    height:32px;

    border-radius:6px;

    background:#F3F4F6;
    color:#9CA3AF;

    font-size:13px;
    font-weight:600;
}
</style>
</head>

<body>

<%@ include file="../common/topBar.jsp" %>

<div class="layout">

    <%@ include file="../common/sideBar.jsp" %>

    <main class="main">

        <div class="page-header">

            <h1 class="page-title">예매 티켓 검색</h1>

        </div>

        <div class="card">

            <div class="tabs">
                <a class="tab"
                   href="<%=contextPath%>/admin/ticket">
                    티켓 관리
                </a>

                <a class="tab active"
                   href="<%=contextPath%>/admin/ticket/search">
                    예매 티켓 검색
                </a>
            </div>

            <form method="get" action="<%=contextPath%>/admin/ticket/search">

                <div class="search-area">

                    <div class="search-title">
                        검색 조건
                    </div>

                    <div class="search-grid">

                        <div class="field">
                            <label>경기 일정</label>

                            <div class="date-wrap">
                                <input type="date"
                                       name="startDate"
                                       value="<%=h(startDate)%>">

                                <input type="date"
                                       name="endDate"
                                       value="<%=h(endDate)%>">
                            </div>
                        </div>

                        <div class="field">
                            <label>구단</label>

                            <select name="team">
                                <option value="">전체</option>

                                <%
                                for (String teamName : teamNameList) {
                                %>

                                    <option value="<%=h(teamName)%>" <%=selected(team, teamName)%>>
                                        <%=h(teamName)%>
                                    </option>

                                <%
                                }
                                %>
                            </select>
                        </div>

                        <div class="field">
                            <label>경기장</label>

                            <select name="stadium">
                                <option value="">전체</option>

                                <%
                                for (String stadiumName : stadiumNameList) {
                                %>

                                    <option value="<%=h(stadiumName)%>" <%=selected(stadium, stadiumName)%>>
                                        <%=h(stadiumName)%>
                                    </option>

                                <%
                                }
                                %>
                            </select>
                        </div>

                        <div class="field">
                            <label>휴대폰 번호</label>

                            <input type="text"
                                   name="phone"
                                   value="<%=h(phone)%>"
                                   placeholder="휴대폰 번호 입력">
                        </div>

                    </div>

                    <div class="btn-area">

                        <button type="button"
                                class="btn btn-reset"
                                onclick="location.href='<%=contextPath%>/admin/ticket/search'">
                            초기화
                        </button>

                        <button type="submit"
                                class="btn btn-search">
                            검색
                        </button>

                    </div>

                </div>

            </form>

            <div class="result-header">

                <div class="result-title">
                    검색 결과
                </div>

                <div class="result-count">
                    총 <%=nf.format(range.getTotalCount())%>건
                </div>

            </div>

            <table class="ticket-table">

                <thead>
                    <tr>
                        <th style="width:100px;">예매번호</th>
                        <th style="width:120px;">예매자</th>
                        <th style="width:160px;">연락처</th>
                        <th>티켓명</th>
                        <th style="width:150px;">좌석</th>
                        <th style="width:90px;">수량</th>
                        <th style="width:130px;">결제금액</th>
                        <th style="width:170px;">예매일</th>
                        <th style="width:110px;">상태</th>
                        <th style="width:100px;">관리</th>
                    </tr>
                </thead>

                <tbody>

                <%
                if (searchList == null || searchList.isEmpty()) {
                %>

                    <tr>
                        <td colspan="10">
                            <div class="empty-body">
                                <i class="ti ti-search-off empty-icon"></i>

                                <p class="empty-text">
                                    검색 결과가 없습니다.
                                </p>

                                <p class="empty-sub">
                                    검색 조건을 변경해서 다시 조회해보세요.
                                </p>
                            </div>
                        </td>
                    </tr>

                <%
                } else {

                    for (TicketSearchDTO ticket : searchList) {
                %>

                    <tr>
                        <td>
                            <%=ticket.getReservationCode()%>
                        </td>

                        <td>
                            <%=h(ticket.getMemberName())%>
                        </td>

                        <td>
                            <%=h(ticket.getMemberTel())%>
                        </td>

                        <td>
                            <%=h(ticket.getTicketName())%>
                        </td>

                        <td>
                            <%=h(ticket.getSeatName())%>
                        </td>

                        <td>
                            <%=nf.format(ticket.getReservationCnt())%>
                        </td>

                        <td>
                            <%=nf.format(ticket.getPaymentPrice())%>원
                        </td>

						<td>
						    <%=h(ticket.getReservationDate())%>
						</td>
						
						<%
						    String reservationState = ticket.getReservationState();
						    boolean canceled = "취소".equals(reservationState);
						%>
						
						<td>
						    <span class="status <%=statusClass(reservationState)%>">
						        <%= canceled ? "취소됨" : h(reservationState) %>
						    </span>
						</td>
						
						<td>
						    <%
						    if (canceled) {
						    %>
						
						        <span class="cancel-done">
						            취소됨
						        </span>
						
						    <%
						    } else {
						    %>
						
						        <div class="action-area">
						            <form method="post"
						                  action="<%=contextPath%>/admin/ticket/cancel"
						                  onsubmit="return confirm('해당 예매를 취소하시겠습니까?');">
						
						                <input type="hidden"
						                       name="reservationCode"
						                       value="<%=ticket.getReservationCode()%>">
						
						                <input type="hidden"
						                       name="returnUrl"
						                       value="<%=h(returnUrl)%>">
						
						                <button type="submit"
						                        class="cancel-btn">
						                    취소
						                </button>
						
						            </form>
						        </div>
						
						    <%
						    }
						    %>
						</td>
                    </tr>

                <%
                    }
                }
                %>

                </tbody>

            </table>

            <div class="pagination">

                <%
                for (int i = 1; i <= range.getTotalPage(); i++) {
                %>

                    <button type="button"
                            class="page-btn <%=i == range.getPage() ? "active" : ""%>"
                            onclick="location.href='<%=contextPath%>/admin/ticket/search?<%=pageQuery%>&page=<%=i%>'">
                        <%=i%>
                    </button>

                <%
                }
                %>

            </div>

        </div>

    </main>

</div>

</body>
</html>
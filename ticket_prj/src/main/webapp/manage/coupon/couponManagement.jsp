<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<%@ page import="kr.admin.coupon.CouponManagementDTO" %>
<%@ page import="kr.admin.coupon.CustodyCouponDTO" %>

<%!
    private String h(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private String js(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("'", "\\'")
                .replace("\r", "")
                .replace("\n", "\\n");
    }
%>

<%
    request.setAttribute("activeMenu", "coupon");

    List<CouponManagementDTO> couponList =
            (List<CouponManagementDTO>) request.getAttribute("couponList");

    Map<String, List<CustodyCouponDTO>> custodyCouponMap =
            (Map<String, List<CustodyCouponDTO>>) request.getAttribute("custodyCouponMap");

    if (couponList == null) {
        couponList =
                new ArrayList<CouponManagementDTO>();
    }

    if (custodyCouponMap == null) {
        custodyCouponMap =
                new HashMap<String, List<CustodyCouponDTO>>();
    }

    String success =
            request.getParameter("success");

    String error =
            request.getParameter("error");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>쿠폰 관리</title>

<style>
* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

body {
    font-family: "Noto Sans KR", sans-serif;
    background: #F5F5F5;
}

/* ── Layout ── */
.layout {
    display: flex;
    min-height: calc(100vh - 56px);
}

/* ── Main ── */
.main {
    flex: 1;
    padding: 28px 32px;
}

.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    margin-bottom: 20px;
}

.page-title {
    font-size: 35px;
    font-weight: 700;
    color: #111827;
}

.register-btn {
    display: flex;
    align-items: center;
    gap: 6px;

    height: 40px;
    padding: 0 16px;

    border: none;
    border-radius: 8px;

    background: #EF4444;
    color: #fff;

    font-size: 14px;
    font-weight: 600;

    cursor: pointer;
}

.register-btn:hover {
    background: #DC2626;
}

.card {
    background: #fff;
    border: 1px solid #E5E7EB;
    border-radius: 12px;

    overflow: hidden;
}

.coupon-table {
    width: 100%;
    border-collapse: collapse;
}

.coupon-table thead {
    background: #F9FAFB;
}

.coupon-table th {
    padding: 15px 12px;

    border-bottom: 1px solid #E5E7EB;

    font-size: 13px;
    font-weight: 600;
    color: #6B7280;

    text-align: center;
}

.coupon-table td {
    padding: 14px 12px;

    border-bottom: 1px solid #F3F4F6;

    font-size: 14px;
    color: #374151;

    text-align: center;
}

.coupon-table tbody tr:hover {
    background: #FAFAFA;
}

.coupon-name {
    text-align: left !important;
    font-weight: 600;
    color: #111827 !important;
}

.coupon-desc {
    text-align: left !important;
    color: #6B7280 !important;
}

.rate-badge {
    display: inline-flex;
    align-items: center;
    justify-content: center;

    min-width: 56px;
    height: 28px;

    border-radius: 999px;

    background: #FEF2F2;
    color: #DC2626;

    font-size: 13px;
    font-weight: 700;
}

.count-badge {
    display: inline-flex;
    align-items: center;
    justify-content: center;

    min-width: 38px;
    height: 26px;

    border-radius: 999px;

    background: #F3F4F6;
    color: #374151;

    font-size: 13px;
    font-weight: 600;
}

.action-area {
    display: flex;
    justify-content: center;
    gap: 6px;
}

.small-btn {
    height: 32px;
    padding: 0 10px;

    border-radius: 6px;

    background: #fff;

    font-size: 13px;
    cursor: pointer;
}

.edit-btn {
    border: 1px solid #9CA3AF;
    color: #374151;
}

.edit-btn:hover {
    background: #F3F4F6;
}

.issue-btn {
    border: 1px solid #2563EB;
    color: #2563EB;
}

.issue-btn:hover {
    background: #EFF6FF;
}

.delete-btn {
    border: 1px solid #EF4444;
    color: #EF4444;
}

.delete-btn:hover {
    background: #FEF2F2;
}

.toggle-btn {
    width: 32px;
    height: 32px;

    border: 1px solid #D1D5DB;
    border-radius: 6px;

    background: #fff;

    cursor: pointer;
}

.custody-row {
    display: none;
}

.coupon-main-row.open + .custody-row {
    display: table-row;
}

.custody-box {
    padding: 18px 22px;

    background: #F9FAFB;
    text-align: left;
}

.custody-title {
    margin-bottom: 12px;

    font-size: 15px;
    font-weight: 700;
    color: #111827;
}

.custody-table {
    width: 100%;
    border-collapse: collapse;

    background: #fff;
    border: 1px solid #E5E7EB;
    border-radius: 10px;
    overflow: hidden;
}

.custody-table th {
    padding: 12px;

    background: #F9FAFB;

    border-bottom: 1px solid #E5E7EB;

    font-size: 13px;
    color: #6B7280;
}

.custody-table td {
    padding: 12px;

    border-bottom: 1px solid #F3F4F6;

    font-size: 13px;
    text-align: center;
}

.state-select {
    height: 32px;
    padding: 0 8px;

    border: 1px solid #D1D5DB;
    border-radius: 6px;

    font-size: 13px;
}

.state-form {
    display: inline-flex;
    align-items: center;
    gap: 6px;
}

.state-save-btn {
    height: 32px;
    padding: 0 10px;

    border: none;
    border-radius: 6px;

    background: #2563EB;
    color: #fff;

    font-size: 12px;
    cursor: pointer;
}

.empty-body {
    padding: 70px 20px;

    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
}

.empty-icon {
    font-size: 40px;
    color: #D1D5DB;
}

.empty-text {
    font-size: 14px;
    color: #6B7280;
}

/* ── Modal ── */
.modal-overlay {
    display: none;

    position: fixed;
    inset: 0;

    background: rgba(0, 0, 0, 0.45);

    justify-content: center;
    align-items: center;

    z-index: 9999;

    padding: 20px;
}

.modal-overlay.show {
    display: flex;
}

.modal {
    width: 560px;
    max-width: 100%;

    background: #fff;
    border-radius: 16px;

    overflow: hidden;

    box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    padding: 20px 24px;

    border-bottom: 1px solid #E5E7EB;
}

.modal-title {
    font-size: 20px;
    font-weight: 700;
    color: #111827;
}

.modal-close {
    border: none;
    background: transparent;

    font-size: 28px;
    color: #6B7280;

    cursor: pointer;
}

.modal-body {
    padding: 24px;
}

.modal-footer {
    display: flex;
    justify-content: flex-end;
    gap: 12px;

    padding: 20px 24px;

    border-top: 1px solid #E5E7EB;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 8px;

    margin-bottom: 16px;
}

.form-row {
    display: flex;
    gap: 14px;
}

.form-label {
    font-size: 14px;
    font-weight: 600;
    color: #374151;
}

.form-label.required::after {
    content: " *";
    color: #EF4444;
}

.form-control {
    width: 100%;
    height: 42px;

    padding: 0 12px;

    border: 1px solid #D1D5DB;
    border-radius: 8px;

    font-size: 14px;
}

textarea.form-control {
    height: 90px;
    padding: 12px;
    resize: vertical;
}

.form-control:focus {
    outline: none;
    border-color: #2563EB;
    box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.15);
}

.btn-cancel,
.btn-submit {
    height: 42px;
    padding: 0 20px;

    border-radius: 8px;

    font-size: 14px;
    font-weight: 600;

    cursor: pointer;
}

.btn-cancel {
    border: 1px solid #D1D5DB;
    background: #fff;
    color: #374151;
}

.btn-submit {
    border: none;
    background: #2563EB;
    color: #fff;
}

.btn-submit:hover {
    background: #1D4ED8;
}

/* ===== 티켓타입 radio 디자인 ===== */
.radio-group {
    display: flex;
    gap: 14px;
    align-items: center;
    height: 42px;
}

.radio-item {
    display: inline-flex;
    align-items: center;
    gap: 6px;

    padding: 0 12px;
    height: 36px;

    border: 1px solid #D1D5DB;
    border-radius: 8px;

    font-size: 14px;
    color: #374151;
    cursor: pointer;
}

.radio-item input {
    margin: 0;
}
</style>
</head>

<body>

<%@ include file="../common/topBar.jsp" %>

<div class="layout">

    <%@ include file="../common/sideBar.jsp" %>

    <main class="main">

        <div class="page-header">
            <h1 class="page-title">
                쿠폰 관리
            </h1>

            <button type="button"
                    class="register-btn"
                    onclick="openCouponModal()">
                <i class="ti ti-plus"></i>
                쿠폰 등록
            </button>
        </div>

        <div class="card">

            <%
            if (couponList.isEmpty()) {
            %>

                <div class="empty-body">
                    <i class="ti ti-ticket empty-icon"></i>

                    <p class="empty-text">
                        등록된 쿠폰이 없습니다.
                    </p>
                </div>

            <%
            } else {
            %>

                <table class="coupon-table">

                    <thead>
                        <tr>
                            <th style="width:70px;">상세</th>
                            <th style="width:110px;">쿠폰 코드</th>
                            <th style="width:90px;">유형</th>
                            <th>쿠폰명</th>
                            <th>설명</th>
                            <th style="width:100px;">할인율</th>
                            <th style="width:210px;">기간</th>
                            <th style="width:90px;">발급</th>
                            <th style="width:90px;">사용가능</th>
                            <th style="width:90px;">사용완료</th>
                            <th style="width:170px;">관리</th>
                        </tr>
                    </thead>

                    <tbody>

                    <%
                    for (CouponManagementDTO coupon : couponList) {

                        List<CustodyCouponDTO> custodyList =
                                custodyCouponMap.get(coupon.getCouponCode());

                        if (custodyList == null) {
                            custodyList =
                                    new ArrayList<CustodyCouponDTO>();
                        }
                    %>

                        <tr class="coupon-main-row">

                            <td>
                                <button type="button"
                                        class="toggle-btn"
                                        onclick="toggleCustodyRow(this)">
                                    <i class="ti ti-chevron-down"></i>
                                </button>
                            </td>

                            <td>
                                <%= h(coupon.getCouponCode()) %>
                            </td>

							<td>
								<span class="count-badge"> <%=h(coupon.getCouponType())%></span>
							</td>

							<td class="coupon-name">
                                <%= h(coupon.getCouponName()) %>
                            </td>

                            <td class="coupon-desc">
                                <%= h(coupon.getCouponDesc()) %>
                            </td>

                            <td>
                                <span class="rate-badge">
                                    <%= coupon.getCouponDiscountRate() %>%
                                </span>
                            </td>

                            <td>
                                <%= h(coupon.getStartDate()) %>
                                ~
                                <%= h(coupon.getEndDate()) %>
                            </td>

                            <td>
                                <span class="count-badge">
                                    <%= coupon.getIssuedCount() %>
                                </span>
                            </td>

                            <td>
                                <span class="count-badge">
                                    <%= coupon.getUsableCount() %>
                                </span>
                            </td>

                            <td>
                                <span class="count-badge">
                                    <%= coupon.getUsedCount() %>
                                </span>
                            </td>

                            <td>
                                <div class="action-area">

									<button type="button"
									        class="small-btn edit-btn"
									        onclick="openCouponEditModal(
									            '<%= js(coupon.getCouponCode()) %>',
									            '<%= js(coupon.getCouponType()) %>',
									            '<%= js(coupon.getCouponName()) %>',
									            '<%= js(coupon.getCouponDesc()) %>',
									            '<%= coupon.getCouponDiscountRate() %>',
									            '<%= js(coupon.getStartDate()) %>',
									            '<%= js(coupon.getEndDate()) %>'
									        )">
									    수정
									</button>

                                    <button type="button"
                                            class="small-btn delete-btn"
                                            onclick="deleteCoupon('<%= js(coupon.getCouponCode()) %>')">
                                        삭제
                                    </button>

                                </div>
                            </td>

                        </tr>

                        <tr class="custody-row">
                            <td colspan="11">

                                <div class="custody-box">

                                    <div class="custody-title">
                                        사용자 보유 쿠폰 내역 -
                                        <%= h(coupon.getCouponCode()) %>
                                    </div>

                                    <%
                                    if (custodyList.isEmpty()) {
                                    %>

                                        <div class="empty-body"
                                             style="padding:35px 10px;">
                                            <i class="ti ti-user-x empty-icon"></i>

                                            <p class="empty-text">
                                                이 쿠폰을 보유한 사용자가 없습니다.
                                            </p>
                                        </div>

                                    <%
                                    } else {
                                    %>

                                        <table class="custody-table">
                                            <thead>
                                                <tr>
                                                    <th>회원 ID</th>
                                                    <th>쿠폰명</th>
                                                    <th>할인율</th>
                                                    <th>발급일</th>
                                                    <th>상태 변경</th>
                                                    <th>삭제</th>
                                                </tr>
                                            </thead>

                                            <tbody>

                                            <%
                                            for (CustodyCouponDTO custody : custodyList) {
                                            %>

                                                <tr>
                                                    <td>
                                                        <%= h(custody.getMemberId()) %>
                                                    </td>

                                                    <td>
                                                        <%= h(custody.getCouponName()) %>
                                                    </td>

                                                    <td>
                                                        <%= custody.getCouponDiscountRate() %>%
                                                    </td>

                                                    <td>
                                                        <%= h(custody.getGetDate()) %>
                                                    </td>

                                                    <td>
                                                        <form class="state-form"
                                                              action="${pageContext.request.contextPath}/admin/coupon/custody/state"
                                                              method="post">

                                                            <input type="hidden"
                                                                   name="couponCode"
                                                                   value="<%= h(custody.getCouponCode()) %>">

                                                            <input type="hidden"
                                                                   name="memberId"
                                                                   value="<%= h(custody.getMemberId()) %>">

                                                            <select name="couponState"
                                                                    class="state-select">
                                                                <option value="사용가능"
                                                                    <%= "사용가능".equals(custody.getCouponState()) ? "selected" : "" %>>
                                                                    사용가능
                                                                </option>

                                                                <option value="사용완료"
                                                                    <%= "사용완료".equals(custody.getCouponState()) ? "selected" : "" %>>
                                                                    사용완료
                                                                </option>

                                                                <option value="만료"
                                                                    <%= "만료".equals(custody.getCouponState()) ? "selected" : "" %>>
                                                                    만료
                                                                </option>
                                                            </select>

                                                            <button type="submit"
                                                                    class="state-save-btn">
                                                                저장
                                                            </button>

                                                        </form>
                                                    </td>

                                                    <td>
                                                        <button type="button"
                                                                class="small-btn delete-btn"
                                                                onclick="deleteCustodyCoupon(
                                                                    '<%= js(custody.getCouponCode()) %>',
                                                                    '<%= js(custody.getMemberId()) %>'
                                                                )">
                                                            삭제
                                                        </button>
                                                    </td>
                                                </tr>

                                            <%
                                            }
                                            %>

                                            </tbody>
                                        </table>

                                    <%
                                    }
                                    %>

                                </div>

                            </td>
                        </tr>

                    <%
                    }
                    %>

                    </tbody>

                </table>

            <%
            }
            %>

        </div>

    </main>

</div>

<!-- 쿠폰 등록/수정 모달 -->
<div class="modal-overlay" id="couponModal">

    <div class="modal">

        <div class="modal-header">
            <h2 class="modal-title" id="couponModalTitle">
                쿠폰 등록
            </h2>

            <button type="button"
                    class="modal-close"
                    onclick="closeCouponModal()">
                ×
            </button>
        </div>

        <form action="${pageContext.request.contextPath}/admin/coupon/save"
              method="post"
              id="couponForm">

            <input type="hidden"
                   name="mode"
                   id="couponMode"
                   value="insert">

            <div class="modal-body">

                <div class="form-row">

                    <div class="form-group">
                        <label class="form-label">
                            쿠폰 코드
                            <span style="font-size:12px; color:#6B7280; font-weight:400;">
                                자동 생성 가능
                            </span>
                        </label>

                        <input type="text"
                               name="couponCode"
                               id="couponCode"
                               class="form-control"
                               placeholder="예) CP003">
                    </div>

                    <div class="form-group">
                        <label class="form-label required">
                            할인율
                        </label>

                        <input type="number"
                               name="couponDiscountRate"
                               id="couponDiscountRate"
                               class="form-control"
                               min="1"
                               max="100"
                               placeholder="10"
                               required>
                    </div>

                </div>
                
			    <div class="form-group">
				    <label class="form-label required">
				        쿠폰 유형
				    </label>
				
				    <div class="radio-group">
				        <label class="radio-item">
				            <input type="radio"
				                   name="couponType"
				                   value="일반"
				                   checked>
				            일반
				        </label>
				
				        <label class="radio-item">
				            <input type="radio"
				                   name="couponType"
				                   value="자동">
				            자동
				        </label>
				    </div>
				</div>

                <div class="form-group">
                    <label class="form-label required">
                        쿠폰명
                    </label>

                    <input type="text"
                           name="couponName"
                           id="couponName"
                           class="form-control"
                           placeholder="예) 신규회원 쿠폰"
                           required>
                </div>

                <div class="form-group">
                    <label class="form-label">
                        쿠폰 설명
                    </label>

                    <textarea name="couponDesc"
                              id="couponDesc"
                              class="form-control"
                              placeholder="쿠폰 설명을 입력하세요."></textarea>
                </div>

                <div class="form-row">

                    <div class="form-group">
                        <label class="form-label required">
                            시작일
                        </label>

                        <input type="date"
                               name="startDate"
                               id="startDate"
                               class="form-control"
                               required>
                    </div>

                    <div class="form-group">
                        <label class="form-label required">
                            종료일
                        </label>

                        <input type="date"
                               name="endDate"
                               id="endDate"
                               class="form-control"
                               required>
                    </div>

                </div>

            </div>

            <div class="modal-footer">

                <button type="button"
                        class="btn-cancel"
                        onclick="closeCouponModal()">
                    취소
                </button>

                <button type="submit"
                        class="btn-submit"
                        id="couponSubmitBtn">
                    등록
                </button>

            </div>

        </form>

    </div>

</div>

<!-- 쿠폰 지급 모달 -->
<div class="modal-overlay" id="issueModal">

    <div class="modal">

        <div class="modal-header">
            <h2 class="modal-title">
                쿠폰 지급
            </h2>

            <button type="button"
                    class="modal-close"
                    onclick="closeIssueModal()">
                ×
            </button>
        </div>

        <form action="${pageContext.request.contextPath}/admin/coupon/issue"
              method="post">

            <div class="modal-body">

                <div class="form-group">
                    <label class="form-label required">
                        쿠폰 코드
                    </label>

                    <input type="text"
                           name="couponCode"
                           id="issueCouponCode"
                           class="form-control"
                           readonly
                           required>
                </div>

                <div class="form-group">
                    <label class="form-label required">
                        회원 ID
                    </label>

                    <input type="text"
                           name="memberId"
                           id="issueMemberId"
                           class="form-control"
                           placeholder="쿠폰을 지급할 회원 ID"
                           required>
                </div>

            </div>

            <div class="modal-footer">

                <button type="button"
                        class="btn-cancel"
                        onclick="closeIssueModal()">
                    취소
                </button>

                <button type="submit"
                        class="btn-submit">
                    지급
                </button>

            </div>

        </form>

    </div>

</div>

<script>
function openCouponModal() {

    document.getElementById('couponMode').value = 'insert';
    document.getElementById('couponModalTitle').textContent = '쿠폰 등록';
    document.getElementById('couponSubmitBtn').textContent = '등록';

    document.getElementById('couponCode').readOnly = false;
    document.getElementById('couponCode').value = '';
    document.getElementById('couponDiscountRate').value = '';
    document.getElementById('couponName').value = '';
    document.getElementById('couponDesc').value = '';
    document.getElementById('startDate').value = '';
    document.getElementById('endDate').value = '';
    setCouponType('일반');

    document.getElementById('couponModal').classList.add('show');
}

function openCouponEditModal(couponCode,
							 couponType,
                             couponName,
                             couponDesc,
                             couponDiscountRate,
                             startDate,
                             endDate) {

    document.getElementById('couponMode').value = 'edit';
    document.getElementById('couponModalTitle').textContent = '쿠폰 수정';
    document.getElementById('couponSubmitBtn').textContent = '수정';

    document.getElementById('couponCode').readOnly = true;
    document.getElementById('couponCode').value = couponCode;
    document.getElementById('couponDiscountRate').value = couponDiscountRate;
    document.getElementById('couponName').value = couponName;
    document.getElementById('couponDesc').value = couponDesc;
    document.getElementById('startDate').value = startDate;
    document.getElementById('endDate').value = endDate;
    
    setCouponType(couponType);

    document.getElementById('couponModal').classList.add('show');
}

function setCouponType(couponType) {

    const type =
            couponType === '자동'
                    ? '자동'
                    : '일반';

    const radio =
            document.querySelector("input[name='couponType'][value='" + type + "']");

    if (radio) {
        radio.checked = true;
    }
}

function closeCouponModal() {
    document.getElementById('couponModal').classList.remove('show');
}

function deleteCoupon(couponCode) {

    if (!confirm('쿠폰을 삭제하시겠습니까?\n사용자에게 지급된 쿠폰이 있으면 삭제되지 않습니다.')) {
        return;
    }

    location.href =
            '${pageContext.request.contextPath}/admin/coupon/delete?couponCode='
            + encodeURIComponent(couponCode);
}

function deleteCustodyCoupon(couponCode, memberId) {

    if (!confirm('사용자 보유 쿠폰을 삭제하시겠습니까?')) {
        return;
    }

    location.href =
            '${pageContext.request.contextPath}/admin/coupon/custody/delete?couponCode='
            + encodeURIComponent(couponCode)
            + '&memberId='
            + encodeURIComponent(memberId);
}

function toggleCustodyRow(button) {

    const row =
            button.closest('.coupon-main-row');

    row.classList.toggle('open');

    const icon =
            button.querySelector('i');

    icon.className =
            row.classList.contains('open')
                    ? 'ti ti-chevron-up'
                    : 'ti ti-chevron-down';
}

document.getElementById('couponForm').addEventListener('submit', function(e) {

    const startDate =
            document.getElementById('startDate').value;

    const endDate =
            document.getElementById('endDate').value;

    if (startDate &&
        endDate &&
        startDate > endDate) {

        alert('종료일은 시작일보다 빠를 수 없습니다.');

        e.preventDefault();
        return;
    }
});

document.querySelectorAll('.modal-overlay').forEach(function(modal) {

    modal.addEventListener('click', function(e) {

        if (e.target === modal) {
            modal.classList.remove('show');
        }
    });
});

<%
if ("save".equals(success)) {
%>
    alert('쿠폰 정보가 저장되었습니다.');
<%
} else if ("delete".equals(success)) {
%>
    alert('쿠폰이 삭제되었습니다.');
<%
} else if ("state".equals(success)) {
%>
    alert('쿠폰 상태가 변경되었습니다.');
<%
} else if ("custodyDelete".equals(success)) {
%>
    alert('사용자 보유 쿠폰이 삭제되었습니다.');
<%
} else if ("save".equals(error)) {
%>
    alert('쿠폰 저장에 실패했습니다.');
<%
} else if ("delete".equals(error)) {
%>
    alert('쿠폰 삭제에 실패했습니다. 이미 사용자에게 지급된 쿠폰은 삭제할 수 없습니다.');
<%
} else if ("state".equals(error)) {
%>
    alert('쿠폰 상태 변경에 실패했습니다.');
<%
} else if ("custodyDelete".equals(error)) {
%>
    alert('사용자 보유 쿠폰 삭제에 실패했습니다.');
<%
}
%>
</script>

</body>
</html>
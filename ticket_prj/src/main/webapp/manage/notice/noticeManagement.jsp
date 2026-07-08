<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="kr.admin.notice.NoticeManagementDTO" %>
<%@ page import="kr.admin.common.TeamOptionDTO" %>

<%!
    private String noticeH(String value) {

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

    private String noticeJs(String value) {

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
    request.setAttribute("activeMenu", "notification");

    Object forwardRequestUri =
            request.getAttribute("javax.servlet.forward.request_uri");

    if (forwardRequestUri == null) {
        response.sendRedirect(
                request.getContextPath()
                + "/admin/notice");
        return;
    }

    List<NoticeManagementDTO> noticeList =
            (List<NoticeManagementDTO>) request.getAttribute("noticeList");

    if (noticeList == null) {
        noticeList =
                new ArrayList<NoticeManagementDTO>();
    }

    List<TeamOptionDTO> teamOptionList =
            (List<TeamOptionDTO>) request.getAttribute("teamOptionList");

    if (teamOptionList == null) {
        teamOptionList =
                new ArrayList<TeamOptionDTO>();
    }

    Integer noticeTabObj =
            (Integer) request.getAttribute("noticeTab");

    Integer teamIdObj =
            (Integer) request.getAttribute("teamId");

    String keyword =
            (String) request.getAttribute("keyword");

    int selectedNoticeTab =
            noticeTabObj == null ? 0 : noticeTabObj.intValue();

    int selectedTeamId =
            teamIdObj == null ? 0 : teamIdObj.intValue();

    if (keyword == null) {
        keyword = "";
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
<title>공지사항 관리</title>

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

/* Layout */
.layout {
    display: flex;
    min-height: calc(100vh - 56px);
}

/* Main */
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

.filter-card {
    margin-bottom: 18px;
    padding: 18px;

    background: #fff;
    border: 1px solid #E5E7EB;
    border-radius: 12px;
}

.filter-form {
    display: flex;
    align-items: center;
    gap: 10px;
}

.filter-control {
    height: 38px;
    padding: 0 10px;

    border: 1px solid #D1D5DB;
    border-radius: 8px;

    font-size: 13px;
}

.filter-keyword {
    width: 260px;
}

.search-btn,
.reset-btn {
    height: 38px;
    padding: 0 14px;

    border-radius: 8px;

    font-size: 13px;
    font-weight: 600;

    cursor: pointer;
}

.search-btn {
    border: none;
    background: #2563EB;
    color: #fff;
}

.reset-btn {
    border: 1px solid #D1D5DB;
    background: #fff;
    color: #374151;
}

.card {
    background: #fff;
    border: 1px solid #E5E7EB;
    border-radius: 12px;

    overflow: hidden;
}

.notice-table {
    width: 100%;
    border-collapse: collapse;
}

.notice-table thead {
    background: #F9FAFB;
}

.notice-table th {
    padding: 14px 10px;

    border-bottom: 1px solid #E5E7EB;

    font-size: 13px;
    font-weight: 600;
    color: #6B7280;

    text-align: center;
}

.notice-table td {
    padding: 13px 10px;

    border-bottom: 1px solid #F3F4F6;

    font-size: 14px;
    color: #374151;

    text-align: center;
    vertical-align: middle;
}

.notice-table tbody tr:hover {
    background: #FAFAFA;
}

.notice-title-cell {
    text-align: left !important;
    font-weight: 600;
    color: #111827 !important;
}

.tab-badge {
    display: inline-flex;
    align-items: center;
    justify-content: center;

    min-width: 100px;
    height: 28px;

    border-radius: 999px;

    font-size: 12px;
    font-weight: 700;
}

.tab-badge.team {
    background: #EEF2FF;
    color: #3730A3;
}

.tab-badge.league {
    background: #ECFDF5;
    color: #047857;
}

.notice-img-name {
    font-size: 12px;
    color: #2563EB;
}

.notice-thumb {
    width: 54px;
    height: 36px;

    object-fit: cover;

    border: 1px solid #E5E7EB;
    border-radius: 6px;

    display: block;
    margin: 0 auto 4px;
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

.delete-btn {
    border: 1px solid #EF4444;
    color: #EF4444;
}

.delete-btn:hover {
    background: #FEF2F2;
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

/* Modal */
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
    width: 640px;
    max-width: 100%;
    max-height: 92vh;

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
    max-height: calc(92vh - 150px);
    overflow-y: auto;
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

.form-row .form-group {
    flex: 1;
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

.form-control:focus {
    outline: none;
    border-color: #2563EB;
    box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.15);
}

.current-img-box {
    display: none;

    padding: 10px 12px;

    border: 1px solid #E5E7EB;
    border-radius: 8px;

    background: #F9FAFB;

    font-size: 13px;
    color: #6B7280;
}

.current-img-box.show {
    display: block;
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

/* 이미지 디자인 */
.image-view-btn {
    height: 30px;
    padding: 0 10px;

    border: 1px solid #2563EB;
    border-radius: 6px;

    background: #fff;
    color: #2563EB;

    font-size: 13px;
    font-weight: 600;

    cursor: pointer;
}

.image-view-btn:hover {
    background: #EFF6FF;
}

.notice-img-empty {
    font-size: 13px;
    color: #9CA3AF;
}

.current-img-box {
    display: none;

    padding: 12px;

    border: 1px solid #E5E7EB;
    border-radius: 8px;

    background: #F9FAFB;

    font-size: 13px;
    color: #6B7280;
}

.current-img-box.show {
    display: block;
}

.current-img-title {
    margin-bottom: 8px;

    font-size: 13px;
    font-weight: 600;
    color: #374151;
}

.current-img-preview {
    width: 100%;
    height: 220px;

    object-fit: contain;

    border: 1px solid #E5E7EB;
    border-radius: 8px;

    background: #fff;

    cursor: pointer;

    display: block;
}

.current-img-preview:hover {
    opacity: 0.85;
}

.image-viewer-overlay {
    display: none;

    position: fixed;
    inset: 0;

    background: rgba(0, 0, 0, 0.72);

    justify-content: center;
    align-items: center;

    z-index: 10000;

    padding: 24px;
}

.image-viewer-overlay.show {
    display: flex;
}

.image-viewer {
    position: relative;

    max-width: 92vw;
    max-height: 88vh;

    background: #fff;
    border-radius: 12px;

    padding: 12px;

    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.35);
}

.image-viewer img {
    max-width: 88vw;
    max-height: 80vh;

    display: block;

    border-radius: 8px;

    object-fit: contain;
}

.image-viewer-close {
    position: absolute;
    top: -14px;
    right: -14px;

    width: 34px;
    height: 34px;

    border: none;
    border-radius: 50%;

    background: #111827;
    color: #fff;

    font-size: 22px;
    line-height: 34px;

    cursor: pointer;
}

.image-name-link {
    display: inline-block;

    max-width: 130px;

    color: #2563EB;
    font-size: 13px;
    font-weight: 600;

    text-decoration: underline;
    text-underline-offset: 3px;

    cursor: pointer;

    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.image-name-link:hover {
    color: #1D4ED8;
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
                공지사항 관리
            </h1>

            <button type="button"
                    class="register-btn"
                    onclick="openNoticeModal()">
                <i class="ti ti-plus"></i>
                공지 등록
            </button>
        </div>

        <div class="filter-card">
            <form class="filter-form"
                  action="${pageContext.request.contextPath}/admin/notice"
                  method="get">

                <select name="noticeTab"
                        class="filter-control">
                    <option value="0"
                        <%= selectedNoticeTab == 0 ? "selected" : "" %>>
                        전체 탭
                    </option>

                    <option value="1"
                        <%= selectedNoticeTab == 1 ? "selected" : "" %>>
                        팀별 공지사항
                    </option>

                    <option value="2"
                        <%= selectedNoticeTab == 2 ? "selected" : "" %>>
                        정규리그 안내
                    </option>
                </select>

                <select name="teamId"
                        class="filter-control">
                    <option value="0"
                        <%= selectedTeamId == 0 ? "selected" : "" %>>
                        전체 팀
                    </option>

                    <%
                    for (TeamOptionDTO team : teamOptionList) {
                    %>
                        <option value="<%= team.getTeamCode() %>"
                            <%= selectedTeamId == team.getTeamCode() ? "selected" : "" %>>
                            <%= noticeH(team.getTeamName()) %>
                        </option>
                    <%
                    }
                    %>
                </select>

                <input type="text"
                       name="keyword"
                       class="filter-control filter-keyword"
                       placeholder="제목 검색"
                       value="<%= noticeH(keyword) %>">

                <button type="submit"
                        class="search-btn">
                    검색
                </button>

                <button type="button"
                        class="reset-btn"
                        onclick="location.href='${pageContext.request.contextPath}/admin/notice';">
                    초기화
                </button>

            </form>
        </div>

        <div class="card">

            <%
            if (noticeList.isEmpty()) {
            %>

                <div class="empty-body">
                    <i class="ti ti-speakerphone empty-icon"></i>

                    <p class="empty-text">
                        등록된 공지사항이 없습니다.
                    </p>
                </div>

            <%
            } else {
            %>

                <table class="notice-table">

                    <thead>
                        <tr>
                            <th style="width:80px;">번호</th>
                            <th style="width:120px;">공지 구분</th>
                            <th style="width:140px;">팀명</th>
                            <th>제목</th>
                            <th style="width:200px;">이미지</th>
                            <th style="width:170px;">작성일</th>
                            <th style="width:150px;">관리</th>
                        </tr>
                    </thead>

                    <tbody>

                    <%
                    for (NoticeManagementDTO notice : noticeList) {

                        String tabClass =
                                notice.getNoticeTab() == 1
                                        ? "team"
                                        : "league";

                        String tabName =
                                notice.getNoticeTab() == 1
                                        ? "팀별 공지사항"
                                        : "정규리그 안내";
                    %>

                        <tr>
                            <td>
                                <%= notice.getNoticeId() %>
                            </td>

                            <td>
                                <span class="tab-badge <%= tabClass %>">
                                    <%= tabName %>
                                </span>
                            </td>

                            <td>
                                <%= noticeH(notice.getTeamName()) %>
                            </td>

                            <td class="notice-title-cell">
                                <%= noticeH(notice.getNoticeTitle()) %>
                            </td>

							<td>
							    <%
							    if (notice.getNoticeImg() != null &&
							        !notice.getNoticeImg().trim().isEmpty()) {
							    %>
							        <span class="image-name-link"
							              onclick="openNoticeImageByFileName('<%= noticeJs(notice.getNoticeImg()) %>')">
							            <%= noticeH(notice.getNoticeImg()) %>
							        </span>
							    <%
							    } else {
							    %>
							        <span class="notice-img-empty">
							            -
							        </span>
							    <%
							    }
							    %>
							</td>

                            <td>
                                <%= noticeH(notice.getNoticeWriteDate()) %>
                            </td>

                            <td>
                                <div class="action-area">

                                    <button type="button"
                                            class="small-btn edit-btn"
                                            onclick="openNoticeEditModal(
                                                '<%= notice.getNoticeId() %>',
                                                '<%= notice.getTeamId() %>',
                                                '<%= notice.getNoticeTab() %>',
                                                '<%= noticeJs(notice.getNoticeTitle()) %>',
                                                '<%= noticeJs(notice.getNoticeImg()) %>'
                                            )">
                                        수정
                                    </button>

                                    <button type="button"
                                            class="small-btn delete-btn"
                                            onclick="deleteNotice('<%= notice.getNoticeId() %>')">
                                        삭제
                                    </button>

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

<!-- 공지 등록/수정 모달 -->
<div class="modal-overlay" id="noticeModal">

    <div class="modal">

        <div class="modal-header">
            <h2 class="modal-title" id="noticeModalTitle">
                공지 등록
            </h2>

            <button type="button"
                    class="modal-close"
                    onclick="closeNoticeModal()">
                ×
            </button>
        </div>

        <form action="${pageContext.request.contextPath}/admin/notice/save"
              method="post"
              enctype="multipart/form-data"
              id="noticeForm">

            <input type="hidden"
                   name="mode"
                   id="noticeMode"
                   value="insert">

            <input type="hidden"
                   name="noticeId"
                   id="noticeId"
                   value="0">

            <div class="modal-body">

                <div class="form-row">

                    <div class="form-group">
                        <label class="form-label required">
                            공지 구분
                        </label>

                        <select name="noticeTab"
                                id="noticeTab"
                                class="form-control"
                                required>
                            <option value="1">
                                팀별 공지사항
                            </option>

                            <option value="2">
                                정규리그 안내
                            </option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label class="form-label required">
                            팀명
                        </label>

                        <select name="teamId"
                                id="teamId"
                                class="form-control"
                                required>
                            <option value="">
                                팀 선택
                            </option>

                            <%
                            for (TeamOptionDTO team : teamOptionList) {
                            %>
                                <option value="<%= team.getTeamCode() %>">
                                    <%= noticeH(team.getTeamName()) %>
                                </option>
                            <%
                            }
                            %>
                        </select>
                    </div>

                </div>

                <div class="form-group">
                    <label class="form-label required">
                        제목
                    </label>

                    <input type="text"
                           name="noticeTitle"
                           id="noticeTitle"
                           class="form-control"
                           placeholder="공지 제목을 입력하세요."
                           required>
                </div>

                <div class="form-group">
                    <label class="form-label">
                        이미지
                    </label>

					<div class="current-img-box"
					     id="currentImgBox">
					    <div class="current-img-title">
					        현재 이미지
					    </div>
					
					    <img id="currentImgPreview"
					         class="current-img-preview"
					         src=""
					         alt="현재 이미지"
					         title="이미지 크게 보기"
					         onclick="openImagePreview(this.src)">
					</div>

                    <input type="file"
                           name="noticeImgFile"
                           id="noticeImgFile"
                           class="form-control"
                           accept="image/*">
                </div>

            </div>

            <div class="modal-footer">

                <button type="button"
                        class="btn-cancel"
                        onclick="closeNoticeModal()">
                    취소
                </button>

                <button type="submit"
                        class="btn-submit"
                        id="noticeSubmitBtn">
                    등록
                </button>

            </div>

        </form>

    </div>

</div>

<!-- 이미지 확대 보기 모달 -->
<div class="image-viewer-overlay"
     id="imageViewer"
     onclick="closeImagePreviewByBackground(event)">

    <div class="image-viewer">
        <button type="button"
                class="image-viewer-close"
                onclick="closeImagePreview()">
            ×
        </button>

        <img id="imageViewerImg"
             src=""
             alt="공지 이미지 확대보기">
    </div>

</div>

<script>

function noticeImageSrc(imagePath) {

    if (!imagePath) {
        return '';
    }

    imagePath = imagePath.trim();

    if (imagePath.startsWith('http://') ||
        imagePath.startsWith('https://')) {

        return imagePath;
    }

    if (imagePath.startsWith('${pageContext.request.contextPath}/')) {
        return imagePath;
    }

    if (imagePath.startsWith('/')) {
        return '${pageContext.request.contextPath}' + imagePath;
    }

    return '${pageContext.request.contextPath}/upload/notice/' + encodeURIComponent(imagePath);
}

function openNoticeModal() {

    document.getElementById('noticeMode').value = 'insert';
    document.getElementById('noticeModalTitle').textContent = '공지 등록';
    document.getElementById('noticeSubmitBtn').textContent = '등록';

    document.getElementById('noticeId').value = '0';
    document.getElementById('teamId').value = '';
    document.getElementById('noticeTab').value = '1';
    document.getElementById('noticeTitle').value = '';
    document.getElementById('noticeImgFile').value = '';

    document.getElementById('currentImgBox').classList.remove('show');
    document.getElementById('currentImgPreview').src = '';

    document.getElementById('noticeModal').classList.add('show');
}


	function openNoticeEditModal(noticeId, teamId, noticeTab, noticeTitle,
			noticeImg) {

		document.getElementById('noticeMode').value = 'edit';
		document.getElementById('noticeModalTitle').textContent = '공지 수정';
		document.getElementById('noticeSubmitBtn').textContent = '수정';

		document.getElementById('noticeId').value = noticeId;
		document.getElementById('teamId').value = teamId;
		document.getElementById('noticeTab').value = noticeTab;
		document.getElementById('noticeTitle').value = noticeTitle;
		document.getElementById('noticeImgFile').value = '';

		const currentImgBox = document.getElementById('currentImgBox');

		const currentImgPreview = document.getElementById('currentImgPreview');

		if (noticeImg && noticeImg.trim() !== '') {

			const imageSrc = noticeImageSrc(noticeImg);

			currentImgPreview.src = imageSrc;

			currentImgBox.classList.add('show');

		} else {

			currentImgPreview.src = '';

			currentImgBox.classList.remove('show');
		}

		document.getElementById('noticeModal').classList.add('show');
	}

	function closeNoticeModal() {
		document.getElementById('noticeModal').classList.remove('show');
	}
	
	function openNoticeImageByFileName(fileName) {

	    const imageSrc =
	            noticeImageSrc(fileName);

	    if (!imageSrc) {
	        return;
	    }

	    openImagePreview(imageSrc);
	}

	function openImagePreview(imageSrc) {

		if (!imageSrc) {
			return;
		}

		document.getElementById('imageViewerImg').src = imageSrc;

		document.getElementById('imageViewer').classList.add('show');
	}

	function closeImagePreview() {

		document.getElementById('imageViewer').classList.remove('show');
		document.getElementById('imageViewerImg').src = '';
	}

	function closeImagePreviewByBackground(event) {

		if (event.target.id === 'imageViewer') {
			closeImagePreview();
		}
	}

	function deleteNotice(noticeId) {

		if (!confirm('공지사항을 삭제하시겠습니까?')) {
			return;
		}

		location.href = '${pageContext.request.contextPath}/admin/notice/delete?noticeId='
				+ encodeURIComponent(noticeId);
	}

	document.getElementById('noticeForm').addEventListener(
			'submit',
			function(e) {

				const teamId = document.getElementById('teamId').value;

				const title = document.getElementById('noticeTitle').value
						.trim();

				if (!teamId) {
					alert('팀을 선택해 주세요.');
					e.preventDefault();
					return;
				}

				if (!title) {
					alert('제목을 입력해 주세요.');
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
    alert('공지사항이 저장되었습니다.');
<%
} else if ("delete".equals(success)) {
%>
    alert('공지사항이 삭제되었습니다.');
<%
} else if ("save".equals(error)) {
%>
    alert('공지사항 저장에 실패했습니다.');
<%
} else if ("delete".equals(error)) {
%>
    alert('공지사항 삭제에 실패했습니다.');
<%
} else if ("unknownAction".equals(error)) {
%>
    alert('알 수 없는 요청입니다.');
<%
}
%>
</script>

</body>
</html>
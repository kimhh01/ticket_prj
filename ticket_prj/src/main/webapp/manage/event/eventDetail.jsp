<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="kr.admin.event.EventDetailDTO" %>

<%!
    private String h(String value) {
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
    request.setAttribute("activeMenu", "event");

    EventDetailDTO event =
            (EventDetailDTO) request.getAttribute("event");

    if (event == null) {
        event = new EventDetailDTO();
    }

    String mode =
            (String) request.getAttribute("mode");

    if (mode == null || mode.trim().isEmpty()) {
        mode = "insert";
    }

    boolean editMode =
            "edit".equals(mode);

    String thumbnailImg =
            event.getThumbnailImg();

    String representativeImg =
            event.getRepresentativeImg();

    String thumbnailPreviewSrc = "";

    if (thumbnailImg != null &&
        !thumbnailImg.trim().isEmpty()) {

        thumbnailPreviewSrc =
                request.getContextPath()
                + "/upload/event/"
                + thumbnailImg;
    }

    String representativePreviewSrc = "";

    if (representativeImg != null &&
        !representativeImg.trim().isEmpty()) {

        representativePreviewSrc =
                request.getContextPath()
                + "/upload/event/"
                + representativeImg;
    }

    boolean discountChecked =
            event.isDiscount();

    /*
     * 등록 화면 기본값
     * 화면 시안처럼 할인 이벤트 사용을 기본 체크 상태로 둠
     */
    if (!editMode) {
        discountChecked = true;
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>이벤트 상세</title>

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/tabler-icons.min.css">

<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

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

/* ── Sidebar ── */
.sidebar {
    width: 200px;
    flex-shrink: 0;
    background: #fff;
    border-right: 1px solid #E5E7EB;
    padding: 12px 0;
}

.nav-item {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 10px 20px;
    font-size: 14px;
    color: #6B7280;
    cursor: pointer;
}

.nav-item i {
    font-size: 18px;
}

.nav-item:hover {
    background: #F9FAFB;
    color: #111;
}

.nav-item.active {
    background: #FDEDF0;
    color: #C0394B;
    font-weight: 500;
}

body {
    font-family: 'Noto Sans KR', sans-serif;
    background: #F5F5F5;
    color: #111827;
}

.layout {
    display: flex;
    min-height: calc(100vh - 56px);
}

.main {
    flex: 1;
    padding: 34px 36px 56px;
    background: #fff;
}

.event-detail-wrap {
    max-width: 1120px;
    margin: 0 auto;
}

.page-head {
    display: flex;
    align-items: center;
    gap: 16px;
    margin-bottom: 28px;
}

.back-btn {
    width: 92px;
    height: 40px;

    border: none;
    border-radius: 6px;

    background: #9CA3AF;
    color: #fff;

    font-size: 14px;
    font-weight: 700;

    cursor: pointer;
}

.back-btn:hover {
    background: #6B7280;
}

.page-title {
    font-size: 24px;
    font-weight: 800;
    color: #111827;
}

.form-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    column-gap: 48px;
    row-gap: 26px;
}

.form-group {
    margin-bottom: 26px;
}

.form-label {
    display: block;
    margin-bottom: 10px;

    font-size: 14px;
    font-weight: 700;

    color: #111827;
}

.form-label.required::after {
    content: " *";
    color: #EF4444;
}

.form-control {
    width: 100%;
    height: 44px;

    padding: 0 14px;

    border: 1px solid #D1D5DB;
    border-radius: 6px;

    font-size: 14px;
    color: #111827;

    background: #fff;
}

.form-control:focus {
    outline: none;
    border-color: #EF4444;
    box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.12);
}

.textarea-control {
    height: 160px;
    padding: 14px;
    resize: none;
    line-height: 1.6;
}

.date-row {
    display: flex;
    align-items: center;
    gap: 14px;
}

.date-row .form-control {
    flex: 1;
}

.date-separator {
    color: #6B7280;
    font-weight: 700;
}

.image-upload-list {
    display: flex;
    flex-direction: column;
    gap: 18px;
}

.image-upload-title {
    margin-bottom: 8px;

    font-size: 13px;
    font-weight: 700;

    color: #374151;
}

.image-help {
    margin-left: 8px;

    font-size: 12px;
    font-weight: 400;
    color: #6B7280;
}

.image-upload-item {
    display: flex;
    gap: 16px;
}

.image-preview-box {
    border: 1px solid #D1D5DB;
    border-radius: 6px;

    background: #FAFAFA;

    display: flex;
    align-items: center;
    justify-content: center;

    overflow: hidden;
}

.image-preview-box.empty {
    background:
        linear-gradient(45deg, transparent 49%, #E5E7EB 50%, transparent 51%),
        linear-gradient(-45deg, transparent 49%, #E5E7EB 50%, transparent 51%),
        #fff;
}

.image-preview-box.thumbnail-preview {
    width: 395px;
    height: 128px;
}

.image-preview-box.representative-preview {
    width: 395px;
    height: 180px;
}

.image-preview-box img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.empty-image-icon {
    color: #C7CBD1;
    font-size: 24px;
}

.image-actions {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.image-btn {
    width: 126px;
    height: 58px;

    border: 1px solid #D1D5DB;
    border-radius: 6px;

    background: #fff;
    color: #111827;

    font-size: 13px;
    font-weight: 700;

    cursor: pointer;
}

.image-btn:hover {
    background: #F9FAFB;
}

.image-btn.delete {
    color: #EF4444;
}

.write-date-box {
    height: 44px;

    display: flex;
    align-items: center;

    padding: 0 14px;

    border: 1px solid #E5E7EB;
    border-radius: 6px;

    background: #F9FAFB;
    color: #6B7280;
    font-size: 14px;
}

.discount-box {
    grid-column: 1 / 3;

    margin-top: 18px;

    border: 1px solid #FCA5A5;
    border-radius: 8px;

    background: #FFF7F7;

    padding: 24px;
}

.discount-head {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;

    margin-bottom: 18px;
}

.discount-title {
    display: flex;
    align-items: center;
    gap: 8px;

    font-size: 20px;
    font-weight: 800;
    color: #DC2626;
}

.discount-desc {
    margin-top: 8px;

    font-size: 13px;
    color: #6B7280;
}

.discount-check {
    display: flex;
    align-items: center;
    gap: 8px;

    font-size: 14px;
    font-weight: 700;
    color: #374151;
}

.discount-check input {
    width: 18px;
    height: 18px;
    accent-color: #DC2626;
}

.discount-panel {
    display: grid;
    grid-template-columns: 1fr 1fr 1.1fr;
    gap: 24px;

    padding: 20px;

    border: 1px solid #FEE2E2;
    border-radius: 8px;

    background: #fff;
}

.coupon-code-box {
    height: 44px;

    display: flex;
    align-items: center;

    padding: 0 14px;

    border: 1px solid #E5E7EB;
    border-radius: 6px;

    background: #F9FAFB;
    color: #DC2626;

    font-size: 14px;
    font-weight: 800;
}

.discount-preview {
    padding: 18px;

    border-radius: 8px;

    background: #FFF1F2;
    color: #DC2626;
}

.discount-preview-title {
    font-size: 14px;
    font-weight: 800;
    margin-bottom: 10px;
}

.discount-preview-desc {
    font-size: 12px;
    color: #6B7280;
    margin-bottom: 10px;
}

.discount-price {
    font-size: 16px;
    font-weight: 800;
}

.discount-price .before {
    color: #374151;
}

.discount-price .after {
    color: #EF4444;
}

.discount-notice {
    margin-top: 16px;

    display: flex;
    align-items: center;
    gap: 8px;

    font-size: 13px;
    font-weight: 700;
    color: #EF4444;
}

.footer-area {
    display: flex;
    justify-content: flex-end;
    gap: 16px;

    margin-top: 40px;
}

.cancel-btn,
.submit-btn {
    width: 130px;
    height: 48px;

    border: none;
    border-radius: 6px;

    color: #fff;

    font-size: 15px;
    font-weight: 800;

    cursor: pointer;
}

.cancel-btn {
    background: #9CA3AF;
}

.cancel-btn:hover {
    background: #6B7280;
}

.submit-btn {
    background: #DC3545;
}

.submit-btn:hover {
    background: #B91C1C;
}

@media (max-width: 1100px) {
    .form-grid {
        grid-template-columns: 1fr;
    }

    .discount-box {
        grid-column: 1;
    }

    .discount-panel {
        grid-template-columns: 1fr;
    }

    .image-upload-item {
        flex-direction: column;
    }

    .image-preview-box.thumbnail-preview,
    .image-preview-box.representative-preview {
        width: 100%;
    }

    .image-actions {
        flex-direction: row;
    }
}
</style>
</head>

<body>

<%@ include file="../common/topBar.jsp" %>

<div class="layout">

    <%@ include file="../common/sideBar.jsp" %>

    <main class="main">

        <div class="event-detail-wrap">

            <div class="page-head">

                <button type="button"
                        class="back-btn"
                        onclick="location.href='${pageContext.request.contextPath}/admin/event'">
                    뒤로 가기
                </button>

                <h1 class="page-title">
                    이벤트 상세
                </h1>

            </div>

            <form action="${pageContext.request.contextPath}/admin/event/save"
                  method="post"
                  enctype="multipart/form-data"
                  id="eventForm">

                <input type="hidden"
                       name="mode"
                       value="<%= mode %>">

                <input type="hidden"
                       name="eventCode"
                       value="<%= event.getEventCode() %>">

                <input type="hidden"
                       name="oldThumbnailImg"
                       id="oldThumbnailImg"
                       value="<%= h(event.getThumbnailImg()) %>">

                <input type="hidden"
                       name="oldRepresentativeImg"
                       id="oldRepresentativeImg"
                       value="<%= h(event.getRepresentativeImg()) %>">

                <input type="hidden"
                       name="thumbnailDeleted"
                       id="thumbnailDeleted"
                       value="N">

                <input type="hidden"
                       name="representativeDeleted"
                       id="representativeDeleted"
                       value="N">

                <div class="form-grid">

                    <div class="left-area">

                        <div class="form-group">
                            <label class="form-label required">
                                이벤트 제목
                            </label>

                            <input type="text"
                                   name="eventTitle"
                                   class="form-control"
                                   maxlength="100"
                                   value="<%= h(event.getEventTitle()) %>"
                                   placeholder="이벤트 제목을 입력하세요. (필수)"
                                   required>
                        </div>

                        <div class="form-group">
                            <label class="form-label required">
                                기간
                            </label>

                            <div class="date-row">

                                <input type="date"
                                       name="startDate"
                                       class="form-control"
                                       value="<%= h(event.getStartDate()) %>"
                                       required>

                                <span class="date-separator">~</span>

                                <input type="date"
                                       name="endDate"
                                       class="form-control"
                                       value="<%= h(event.getEndDate()) %>"
                                       required>

                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label required">
                                이벤트 이미지
                            </label>

                            <div class="image-upload-list">

                                <!-- 썸네일 이미지 -->
                                <div>
                                    <div class="image-upload-title">
                                        썸네일 이미지
                                        <span class="image-help">
                                            목록 / 메인 노출용
                                        </span>
                                    </div>

                                    <div class="image-upload-item">

                                        <div class="image-preview-box thumbnail-preview <%= thumbnailPreviewSrc.isEmpty() ? "empty" : "" %>"
                                             id="thumbnailPreviewBox">

                                            <%
                                            if (!thumbnailPreviewSrc.isEmpty()) {
                                            %>

                                                <img src="<%= thumbnailPreviewSrc %>"
                                                     id="thumbnailPreview"
                                                     alt="썸네일 이미지">

                                            <%
                                            } else {
                                            %>

                                                <i class="ti ti-camera empty-image-icon"
                                                   id="thumbnailEmptyIcon"></i>

                                                <img src=""
                                                     id="thumbnailPreview"
                                                     alt="썸네일 이미지"
                                                     style="display:none;">

                                            <%
                                            }
                                            %>

                                        </div>

                                        <div class="image-actions">

                                            <input type="file"
										       name="thumbnailImgFile"
										       id="thumbnailImgFile"
										       accept="image/png,image/jpeg,image/webp,image/svg+xml"
										       style="display:none;">

                                            <button type="button"
                                                    class="image-btn"
                                                    onclick="document.getElementById('thumbnailImgFile').click();">
                                                <i class="ti ti-refresh"></i>
                                                <br>
                                                이미지 변경
                                            </button>

                                            <button type="button"
                                                    class="image-btn delete"
                                                    onclick="clearThumbnailImage();">
                                                <i class="ti ti-circle-x"></i>
                                                <br>
                                                이미지 삭제
                                            </button>

                                        </div>

                                    </div>
                                </div>

                                <!-- 대표 이미지 -->
                                <div>
                                    <div class="image-upload-title">
                                        대표 이미지
                                        <span class="image-help">
                                            이벤트 상세 노출용
                                        </span>
                                    </div>

                                    <div class="image-upload-item">

                                        <div class="image-preview-box representative-preview <%= representativePreviewSrc.isEmpty() ? "empty" : "" %>"
                                             id="representativePreviewBox">

                                            <%
                                            if (!representativePreviewSrc.isEmpty()) {
                                            %>

                                                <img src="<%= representativePreviewSrc %>"
                                                     id="representativePreview"
                                                     alt="대표 이미지">

                                            <%
                                            } else {
                                            %>

                                                <i class="ti ti-camera empty-image-icon"
                                                   id="representativeEmptyIcon"></i>

                                                <img src=""
                                                     id="representativePreview"
                                                     alt="대표 이미지"
                                                     style="display:none;">

                                            <%
                                            }
                                            %>

                                        </div>

                                        <div class="image-actions">

											<input type="file"
											       name="representativeImgFile"
											       id="representativeImgFile"
											       accept="image/png,image/jpeg,image/webp,image/svg+xml"
											       style="display:none;">

                                            <button type="button"
                                                    class="image-btn"
                                                    onclick="document.getElementById('representativeImgFile').click();">
                                                <i class="ti ti-refresh"></i>
                                                <br>
                                                이미지 변경
                                            </button>

                                            <button type="button"
                                                    class="image-btn delete"
                                                    onclick="clearRepresentativeImage();">
                                                <i class="ti ti-circle-x"></i>
                                                <br>
                                                이미지 삭제
                                            </button>

                                        </div>

                                    </div>
                                </div>

                            </div>
                        </div>

                        <%
                        if (editMode) {
                        %>
                        <div class="form-group">
                            <label class="form-label">
                                작성일
                            </label>

                            <div class="write-date-box">
                                <%= h(event.getWriteDate()) %>
                            </div>
                        </div>
                        <%
                        }
                        %>

                    </div>

                    <div class="right-area">

                        <div class="form-group">
                            <label class="form-label required">
                                이벤트 요약 (메인 노출용)
                            </label>

                            <input type="text"
                                   name="eventSummary"
                                   class="form-control"
                                   maxlength="300"
                                   value="<%= h(event.getEventSummary()) %>"
                                   placeholder="이벤트 요약 내용을 입력하세요. (필수)"
                                   required>
                        </div>

                        <div class="form-group">
                            <label class="form-label required">
                                이벤트 내용
                            </label>

                            <textarea name="eventContent"
                                      class="form-control textarea-control"
                                      placeholder="이벤트 내용을 입력하세요. (필수)"
                                      required><%= h(event.getEventContent()) %></textarea>
                        </div>

                    </div>

                    <div class="discount-box">

                        <div class="discount-head">

                            <div>
                                <div class="discount-title">
                                    <i class="ti ti-external-link"></i>
                                    할인 설정
                                </div>

                                <p class="discount-desc">
                                    할인 이벤트로 설정하면 쿠폰 번호가 발급되고, 사용자가 결제 화면에서 쿠폰 번호를 입력해 할인받을 수 있습니다.
                                </p>
                            </div>

                            <label class="discount-check">
                                <input type="checkbox"
                                       name="isDiscount"
                                       id="isDiscount"
                                       value="Y"
                                       <%= discountChecked ? "checked" : "" %>>
                                할인 이벤트 사용
                            </label>

                        </div>

                        <div class="discount-panel"
						     id="discountPanel">
						
						    <div class="form-group">
						        <label class="form-label">
						            쿠폰 번호
						        </label>
						
						        <div class="coupon-code-box">
						            <%
						            if (editMode &&
						                event.getDiscountRuleCode() > 0) {
						            %>
						                <%= event.getDiscountRuleCode() %>
						            <%
						            } else {
						            %>
						                등록 후 자동 발급
						            <%
						            }
						            %>
						        </div>
						    </div>
						
						    <div class="form-group">
						        <label class="form-label required">
						            할인율 (정률 할인 %)
						        </label>
						
						        <input type="number"
						               name="discountRate"
						               id="discountRate"
						               class="form-control"
						               min="0"
						               max="100"
						               value="<%= event.getDiscountRate() %>">
						    </div>
						
						    <div class="discount-preview">
						        <div class="discount-preview-title">
						            할인 미리보기
						        </div>
						
						        <div class="discount-preview-desc">
						            예시) 15,000원 좌석 기준
						        </div>
						
						        <div class="discount-price">
						            <span class="before">15,000원</span>
						            →
						            <span class="after"
						                  id="discountPreviewPrice">
						                15,000원
						            </span>
						        </div>
						    </div>
						
						</div>

                        <div class="discount-notice">
                            <i class="ti ti-info-circle-filled"></i>
                            쿠폰 번호는 할인 이벤트 등록 시 자동 발급되며, 사용자가 결제 화면에서 입력하면 할인율이 적용됩니다.
                        </div>

                    </div>

                </div>

                <div class="footer-area">

                    <button type="button"
                            class="cancel-btn"
                            onclick="location.href='${pageContext.request.contextPath}/admin/event'">
                        취소
                    </button>

                    <button type="submit"
                            class="submit-btn">
                        <%= editMode ? "수정" : "등록" %>
                    </button>

                </div>

            </form>

        </div>

    </main>

</div>

<script>
const thumbnailImgInput =
        document.getElementById('thumbnailImgFile');

const thumbnailPreview =
        document.getElementById('thumbnailPreview');

const thumbnailPreviewBox =
        document.getElementById('thumbnailPreviewBox');

const thumbnailEmptyIcon =
        document.getElementById('thumbnailEmptyIcon');

const oldThumbnailImgInput =
        document.getElementById('oldThumbnailImg');

const thumbnailDeletedInput =
        document.getElementById('thumbnailDeleted');


const representativeImgInput =
        document.getElementById('representativeImgFile');

const representativePreview =
        document.getElementById('representativePreview');

const representativePreviewBox =
        document.getElementById('representativePreviewBox');

const representativeEmptyIcon =
        document.getElementById('representativeEmptyIcon');

const oldRepresentativeImgInput =
        document.getElementById('oldRepresentativeImg');

const representativeDeletedInput =
        document.getElementById('representativeDeleted');


const isDiscountInput =
        document.getElementById('isDiscount');

const discountPanel =
        document.getElementById('discountPanel');

const discountRateInput =
        document.getElementById('discountRate');

const discountPreviewPrice =
        document.getElementById('discountPreviewPrice');

const eventForm =
        document.getElementById('eventForm');


function previewImage(fileInput,
                      previewImg,
                      previewBox,
                      emptyIcon,
                      deletedInput) {

    const file =
            fileInput.files && fileInput.files[0];

    if (!file) {
        return;
    }

    if (deletedInput) {
        deletedInput.value = 'N';
    }

    const reader =
            new FileReader();

    reader.onload = function(e) {

        if (previewImg) {
            previewImg.src = e.target.result;
            previewImg.style.display = 'block';
        }

        if (emptyIcon) {
            emptyIcon.style.display = 'none';
        }

        if (previewBox) {
            previewBox.classList.remove('empty');
        }
    };

    reader.readAsDataURL(file);
}

if (thumbnailImgInput) {
    thumbnailImgInput.addEventListener('change', function() {
        previewImage(
                thumbnailImgInput,
                thumbnailPreview,
                thumbnailPreviewBox,
                thumbnailEmptyIcon,
                thumbnailDeletedInput);
    });
}

if (representativeImgInput) {
    representativeImgInput.addEventListener('change', function() {
        previewImage(
                representativeImgInput,
                representativePreview,
                representativePreviewBox,
                representativeEmptyIcon,
                representativeDeletedInput);
    });
}

function clearThumbnailImage() {

    if (thumbnailImgInput) {
        thumbnailImgInput.value = '';
    }

    if (thumbnailPreview) {
        thumbnailPreview.removeAttribute('src');
        thumbnailPreview.style.display = 'none';
    }

    if (thumbnailEmptyIcon) {
        thumbnailEmptyIcon.style.display = 'block';
    }

    if (thumbnailPreviewBox) {
        thumbnailPreviewBox.classList.add('empty');
    }

    if (oldThumbnailImgInput) {
        oldThumbnailImgInput.value = '';
    }

    if (thumbnailDeletedInput) {
        thumbnailDeletedInput.value = 'Y';
    }
}

function clearRepresentativeImage() {

    if (representativeImgInput) {
        representativeImgInput.value = '';
    }

    if (representativePreview) {
        representativePreview.removeAttribute('src');
        representativePreview.style.display = 'none';
    }

    if (representativeEmptyIcon) {
        representativeEmptyIcon.style.display = 'block';
    }

    if (representativePreviewBox) {
        representativePreviewBox.classList.add('empty');
    }

    if (oldRepresentativeImgInput) {
        oldRepresentativeImgInput.value = '';
    }

    if (representativeDeletedInput) {
        representativeDeletedInput.value = 'Y';
    }
}

function updateDiscountState() {

    const enabled =
            isDiscountInput && isDiscountInput.checked;

    if (discountPanel) {
        discountPanel.style.opacity = enabled ? '1' : '0.45';
    }

    if (discountRateInput) {
        discountRateInput.disabled = !enabled;
        discountRateInput.required = enabled;
    }
}

function updateDiscountPreview() {

    const basePrice = 15000;

    let rate = 0;

    if (discountRateInput &&
        discountRateInput.value) {

        rate = parseInt(discountRateInput.value, 10);

        if (isNaN(rate)) {
            rate = 0;
        }
    }

    if (rate < 0) {
        rate = 0;
    }

    if (rate > 100) {
        rate = 100;
    }

    const discounted =
            basePrice - Math.floor(basePrice * rate / 100);

    if (discountPreviewPrice) {
        discountPreviewPrice.textContent =
                discounted.toLocaleString() + '원'
                + ' (' + rate + '% 할인)';
    }
}

if (isDiscountInput) {
    isDiscountInput.addEventListener('change', function() {
        updateDiscountState();
    });
}

if (discountRateInput) {
    discountRateInput.addEventListener('input', function() {
        updateDiscountPreview();
    });
}

if (eventForm) {
    eventForm.addEventListener('submit', function(e) {

        const hasOldThumbnail =
                oldThumbnailImgInput &&
                oldThumbnailImgInput.value.trim() !== '';

        const hasNewThumbnail =
                thumbnailImgInput &&
                thumbnailImgInput.files &&
                thumbnailImgInput.files.length > 0;

        const hasOldRepresentative =
                oldRepresentativeImgInput &&
                oldRepresentativeImgInput.value.trim() !== '';

        const hasNewRepresentative =
                representativeImgInput &&
                representativeImgInput.files &&
                representativeImgInput.files.length > 0;

        if (!hasOldThumbnail && !hasNewThumbnail) {
            alert('썸네일 이미지를 등록해주세요.');
            e.preventDefault();
            return;
        }

        if (!hasOldRepresentative && !hasNewRepresentative) {
            alert('대표 이미지를 등록해주세요.');
            e.preventDefault();
            return;
        }

        if (isDiscountInput &&
            isDiscountInput.checked) {

            if (!discountRateInput.value ||
                parseInt(discountRateInput.value, 10) <= 0) {

                alert('할인율을 1 이상 입력해주세요.');
                e.preventDefault();
                return;
            }
        }
    });
}

updateDiscountState();
updateDiscountPreview();
</script>

</body>
</html>
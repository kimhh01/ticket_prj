<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="java.util.Map"%>
<%!
@SuppressWarnings("unchecked")
private Map<Integer, String> getCategories(HttpServletRequest request) {
	return (Map<Integer, String>) request.getAttribute("categories");
}
%>
<%
Map<Integer, String> categories = getCategories(request);
Integer selectedCategoryCode = (Integer) request.getAttribute("selectedCategoryCode");
String formTitle = request.getAttribute("formTitle") == null ? "" : (String) request.getAttribute("formTitle");
String formContent = request.getAttribute("formContent") == null ? "" : (String) request.getAttribute("formContent");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>문의 수정 | BallPick</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kr/user/inquiry/inquiry.css?v=20260709-1">
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />

	<main class="inquiry-page">
		<section class="inquiry-shell inquiry-shell-narrow">
			<h1 class="inquiry-title">문의 수정</h1>
			<p class="inquiry-description">답변대기 상태의 문의만 수정할 수 있습니다.</p>
			<%
			if (request.getAttribute("errorMessage") != null) {
			%>
			<div class="inquiry-error"><%=request.getAttribute("errorMessage")%></div>
			<%
			}
			%>
			<form class="inquiry-form" id="inquiryForm" method="post"
				action="<%=request.getContextPath()%>/user-inquiry/edit" novalidate>
				<input type="hidden" name="stage" value="submit"> <input
					type="hidden" name="inquiryCode"
					value="<%=request.getAttribute("inquiryCode")%>">
				<div class="inquiry-field">
					<label for="inquiryCategoryCode">문의 유형 <span
						class="inquiry-required">*</span></label> <select class="inquiry-select"
						id="inquiryCategoryCode" name="inquiryCategoryCode">
						<option value="">문의 유형을 선택해 주세요.</option>
						<%
						if (categories != null) {
							for (Map.Entry<Integer, String> category : categories.entrySet()) {
						%>
						<option value="<%=category.getKey()%>"
							<%=category.getKey().equals(selectedCategoryCode) ? "selected" : ""%>><%=category.getValue()%></option>
						<%
						}
						}
						%>
					</select>
				</div>
				<div class="inquiry-field">
					<label for="inquiryTitle">제목 <span class="inquiry-required">*</span></label>
					<input class="inquiry-input" type="text" id="inquiryTitle"
						name="inquiryTitle" maxlength="100" value="<%=formTitle%>">
					<div class="inquiry-field-meta">
						<span>최대 100자</span><span id="titleCount">0/100</span>
					</div>
				</div>
				<div class="inquiry-field">
					<label for="inquiryContent">문의 내용 <span
						class="inquiry-required">*</span></label>
					<textarea class="inquiry-textarea" id="inquiryContent"
						name="inquiryContent" maxlength="255"><%=formContent%></textarea>
					<div class="inquiry-field-meta">
						<span>최대 255자</span><span id="contentCount">0/255</span>
					</div>
				</div>
				<div class="inquiry-error" id="clientError" hidden="hidden"></div>
				<div class="inquiry-actions">
					<a class="inquiry-button inquiry-button-light"
						href="<%=request.getContextPath()%>/user-inquiry/list">취소</a>
					<button class="inquiry-button" type="submit">수정 완료</button>
				</div>
			</form>
		</section>
	</main>

	<jsp:include page="/fragment/footer.jsp" />
	<script>
		(function() {
			const form = document.getElementById("inquiryForm");
			const category = document.getElementById("inquiryCategoryCode");
			const title = document.getElementById("inquiryTitle");
			const content = document.getElementById("inquiryContent");
			const error = document.getElementById("clientError");
			const titleCount = document.getElementById("titleCount");
			const contentCount = document.getElementById("contentCount");

			function updateCount() {
				titleCount.textContent = title.value.length + "/100";
				contentCount.textContent = content.value.length + "/255";
			}
			title.addEventListener("input", updateCount);
			content.addEventListener("input", updateCount);
			updateCount();

			form.addEventListener("submit", function(event) {
				let message = "";
				if (category.value === "")
					message = "문의 유형을 선택해 주세요.";
				else if (title.value.trim().length < 1
						|| title.value.trim().length > 100)
					message = "제목은 1자 이상 100자 이하로 입력해 주세요.";
				else if (content.value.trim().length < 1 || content.value.length > 255) {
				    message = "문의 내용은 1자 이상 255자 이하로 입력해 주세요.";
				}
				if (message !== "") {
					event.preventDefault();
					error.textContent = message;
					error.hidden = false;
				}
			});
		})();
	</script>
</body>
</html>

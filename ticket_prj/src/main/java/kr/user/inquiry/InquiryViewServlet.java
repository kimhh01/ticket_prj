package kr.user.inquiry;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user-inquiry/*")
public class InquiryViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Map<Integer, String> CATEGORIES = new LinkedHashMap<Integer, String>();

	static {
		CATEGORIES.put(1, "예매문의");
		CATEGORIES.put(2, "결제문의");
		CATEGORIES.put(3, "회원문의");
		CATEGORIES.put(4, "기타문의");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		prepareEncoding(request, response);
		String path = request.getPathInfo();

		if (path == null || "/".equals(path)) {
			forward(request, response, "main.jsp");
			return;
		}

		switch (path) {
		case "/write":
			prepareCategories(request);
			forward(request, response, "write.jsp");
			break;
		case "/list":
			forward(request, response, "list.jsp");
			break;
		case "/detail":
		case "/edit":
			response.sendRedirect(request.getContextPath() + "/user-inquiry/list");
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		prepareEncoding(request, response);
		String path = request.getPathInfo();

		if ("/write".equals(path)) {
			handleWrite(request, response);
			return;
		}
		if ("/edit".equals(path)) {
			handleEdit(request, response);
			return;
		}
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	private void handleWrite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FormData form = readForm(request);
		String errorMessage = validate(form);

		if (errorMessage != null) {
			prepareForm(request, form, errorMessage);
			forward(request, response, "write.jsp");
			return;
		}

		preparePreview(request, form);
		forward(request, response, "detail.jsp");
	}

	private void handleEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FormData form = readForm(request);
		String errorMessage = validate(form);
		String stage = trim(request.getParameter("stage"));

		if (!"submit".equals(stage)) {
			if (errorMessage != null) {
				response.sendRedirect(request.getContextPath() + "/user-inquiry/list");
				return;
			}
			prepareForm(request, form, null);
			forward(request, response, "edit.jsp");
			return;
		}

		if (errorMessage != null) {
			prepareForm(request, form, errorMessage);
			forward(request, response, "edit.jsp");
			return;
		}

		preparePreview(request, form);
		request.setAttribute("successMessage", "문의 내용이 수정되었습니다. DB 연결 후 실제 저장이 적용됩니다.");
		forward(request, response, "detail.jsp");
	}

	private void preparePreview(HttpServletRequest request, FormData form) {
		InquiryDTO inquiry = new InquiryDTO();
		inquiry.setInquiryCode(0);
		inquiry.setMemberCode("preview");
		inquiry.setInquiryCategoryCode(form.categoryCode);
		inquiry.setInquiryTitle(escapeHtml(form.title));
		inquiry.setInquiryContent(escapeHtml(form.content));
		inquiry.setInquiryDate(new Date());
		inquiry.setInquiryStatus("답변대기");

		request.setAttribute("inquiry", inquiry);
		request.setAttribute("categoryName", CATEGORIES.get(form.categoryCode));
	}

	private void prepareForm(HttpServletRequest request, FormData form, String errorMessage) {
		prepareCategories(request);
		request.setAttribute("selectedCategoryCode", form.categoryCode);
		request.setAttribute("formTitle", escapeHtml(form.title));
		request.setAttribute("formContent", escapeHtml(form.content));
		request.setAttribute("errorMessage", errorMessage);
	}

	private void prepareCategories(HttpServletRequest request) {
		request.setAttribute("categories", CATEGORIES);
	}

	private FormData readForm(HttpServletRequest request) {
		int categoryCode = parseInt(request.getParameter("inquiryCategoryCode"));
		String title = trim(request.getParameter("inquiryTitle"));
		String content = trim(request.getParameter("inquiryContent"));
		return new FormData(categoryCode, title, content);
	}

	private String validate(FormData form) {
		if (!CATEGORIES.containsKey(form.categoryCode)) {
			return "문의 유형을 선택해 주세요.";
		}
		if (form.title.isEmpty() || form.title.length() > 100) {
			return "제목은 1자 이상 100자 이하로 입력해 주세요.";
		}
		if (form.content.isEmpty() || form.content.length() > 2000) {
			return "문의 내용은 1자 이상 2,000자 이하로 입력해 주세요.";
		}
		return null;
	}

	private void forward(HttpServletRequest request, HttpServletResponse response, String jsp)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/kr.user.inquiry/" + jsp);
		dispatcher.forward(request, response);
	}

	private void prepareEncoding(HttpServletRequest request, HttpServletResponse response)
			throws java.io.UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	}

	private int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException exception) {
			return 0;
		}
	}

	private String trim(String value) {
		return value == null ? "" : value.trim();
	}

	private String escapeHtml(String value) {
		return value.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&#39;");
	}

	private static class FormData {
		private final int categoryCode;
		private final String title;
		private final String content;

		private FormData(int categoryCode, String title, String content) {
			this.categoryCode = categoryCode;
			this.title = title;
			this.content = content;
		}
	}
}

package kr.user.member;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/member/*")
public class MemberViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Pattern MEMBER_CODE_PATTERN = Pattern.compile("^[A-Za-z0-9]{4,20}$");
	private static final Pattern PASSWORD_PATTERN = Pattern
			.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,20}$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		prepareEncoding(request, response);

		String path = request.getPathInfo();
		if (path == null || "/".equals(path)) {
			response.sendRedirect(request.getContextPath() + "/member/login");
			return;
		}

		switch (path) {
		case "/login":
			forward(request, response, "login.jsp");
			break;
		case "/find-code":
			forward(request, response, "findCode.jsp");
			break;
		case "/find-password":
			forward(request, response, "findPassword.jsp");
			break;
		case "/join-agree":
			forward(request, response, "joinAgree.jsp");
			break;
		case "/find-code-result":
			response.sendRedirect(request.getContextPath() + "/member/find-code");
			break;
		case "/find-password-result":
			response.sendRedirect(request.getContextPath() + "/member/find-password");
			break;
		case "/join-form":
		case "/join-complete":
			response.sendRedirect(request.getContextPath() + "/member/join-agree");
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
		if (path == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		switch (path) {
		case "/login":
			handleLoginPreview(request, response);
			break;
		case "/find-code-result":
			handleFindCodePreview(request, response);
			break;
		case "/find-password-result":
			handleFindPasswordPreview(request, response);
			break;
		case "/join-form":
			handleJoinAgreement(request, response);
			break;
		case "/join-complete":
			handleJoinPreview(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void handleLoginPreview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String memberCode = trim(request.getParameter("memberCode"));
		String password = trim(request.getParameter("password"));

		if (!MEMBER_CODE_PATTERN.matcher(memberCode).matches() || password.isEmpty()) {
			request.setAttribute("errorMessage", "회원코드와 비밀번호를 확인해 주세요.");
			forward(request, response, "login.jsp");
			return;
		}

		response.sendRedirect(request.getContextPath() + "/main.do");
	}

	private void handleFindCodePreview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = trim(request.getParameter("name"));
		String email = trim(request.getParameter("email"));

		if (name.isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
			request.setAttribute("errorMessage", "이름과 이메일을 확인해 주세요.");
			forward(request, response, "findCode.jsp");
			return;
		}

		request.setAttribute("verifiedName", escapeHtml(name));
		request.setAttribute("verifiedEmail", escapeHtml(email));
		forward(request, response, "findCodeResult.jsp");
	}

	private void handleFindPasswordPreview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String memberCode = trim(request.getParameter("memberCode"));
		String name = trim(request.getParameter("name"));
		String email = trim(request.getParameter("email"));

		if (!MEMBER_CODE_PATTERN.matcher(memberCode).matches() || name.isEmpty()
				|| !EMAIL_PATTERN.matcher(email).matches()) {
			request.setAttribute("errorMessage", "입력한 회원 정보를 확인해 주세요.");
			forward(request, response, "findPassword.jsp");
			return;
		}

		request.setAttribute("verifiedMemberCode", escapeHtml(memberCode));
		request.setAttribute("verifiedName", escapeHtml(name));
		request.setAttribute("verifiedEmail", escapeHtml(email));
		forward(request, response, "findPasswordResult.jsp");
	}

	private void handleJoinAgreement(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!"Y".equals(request.getParameter("termsAgree"))
				|| !"Y".equals(request.getParameter("privacyAgree"))) {
			request.setAttribute("errorMessage", "필수 약관에 동의해 주세요.");
			forward(request, response, "joinAgree.jsp");
			return;
		}

		request.setAttribute("smsReceiveYN", "Y".equals(request.getParameter("smsReceiveYN")) ? "Y" : "N");
		request.setAttribute("emailReceiveYN", "Y".equals(request.getParameter("emailReceiveYN")) ? "Y" : "N");
		forward(request, response, "joinForm.jsp");
	}

	private void handleJoinPreview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String memberCode = trim(request.getParameter("memberCode"));
		String password = trim(request.getParameter("password"));
		String passwordConfirm = trim(request.getParameter("passwordConfirm"));
		String name = trim(request.getParameter("name"));
		String email = trim(request.getParameter("email"));
		String phone2 = trim(request.getParameter("phone2"));
		String phone3 = trim(request.getParameter("phone3"));
		String zipcode = trim(request.getParameter("zipcode"));
		String address = trim(request.getParameter("address"));
		String address2 = trim(request.getParameter("address2"));

		boolean valid = MEMBER_CODE_PATTERN.matcher(memberCode).matches()
				&& PASSWORD_PATTERN.matcher(password).matches()
				&& password.equals(passwordConfirm)
				&& !name.isEmpty()
				&& EMAIL_PATTERN.matcher(email).matches()
				&& phone2.matches("^\\d{3,4}$")
				&& phone3.matches("^\\d{4}$")
				&& zipcode.matches("^\\d{5}$")
				&& !address.isEmpty()
				&& !address2.isEmpty();

		if (!valid) {
			request.setAttribute("errorMessage", "필수 입력값과 입력 형식을 확인해 주세요.");
			request.setAttribute("smsReceiveYN", defaultConsent(request.getParameter("smsReceiveYN")));
			request.setAttribute("emailReceiveYN", defaultConsent(request.getParameter("emailReceiveYN")));
			forward(request, response, "joinForm.jsp");
			return;
		}

		request.setAttribute("joinedName", escapeHtml(name));
		request.setAttribute("joinedMemberCode", escapeHtml(memberCode));
		forward(request, response, "joinComplete.jsp");
	}

	private void forward(HttpServletRequest request, HttpServletResponse response, String jsp)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/kr.user.member/" + jsp);
		dispatcher.forward(request, response);
	}

	private void prepareEncoding(HttpServletRequest request, HttpServletResponse response)
			throws java.io.UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	}

	private String trim(String value) {
		return value == null ? "" : value.trim();
	}

	private String defaultConsent(String value) {
		return "Y".equals(value) ? "Y" : "N";
	}

	private String escapeHtml(String value) {
		return value.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&#39;");
	}
}

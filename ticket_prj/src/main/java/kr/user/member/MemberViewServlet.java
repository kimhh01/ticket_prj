package kr.user.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/*")
public class MemberViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Pattern MEMBER_CODE_PATTERN = Pattern.compile("^[A-Za-z0-9]{4,20}$");
	private static final Pattern PASSWORD_PATTERN = Pattern
			.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,20}$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

	private MemberService memberService;

	public MemberViewServlet() {
		this.memberService = new MemberService();
	}

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
		case "/logout":
			handleLogout(request, response);
			break;
		case "/check-id":
			handleCheckId(request, response);
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
			handleLogin(request, response);
			break;
		case "/find-code-result":
			handleFindCode(request, response);
			break;
		case "/find-password-result":
			handleFindPassword(request, response);
			break;
		case "/join-form":
			handleJoinAgreement(request, response);
			break;
		case "/join-complete":
			handleJoin(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	/**
	 * 입력한 아이디와 비밀번호로 로그인한다.
	 */
	private void handleLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String memberCode = trim(request.getParameter("memberCode"));
		String password = trim(request.getParameter("password"));

		if (!MEMBER_CODE_PATTERN.matcher(memberCode).matches() || password.isEmpty()) {
			request.setAttribute("errorMessage", "아이디와 비밀번호를 확인해 주세요.");
			forward(request, response, "login.jsp");
			return;
		}

		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberCode(memberCode);
		memberDTO.setPassword(password);

		MemberDTO loginMember = memberService.login(memberDTO);

		if (loginMember == null) {
			request.setAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
			forward(request, response, "login.jsp");
			return;
		}

		HttpSession session = request.getSession();
		request.changeSessionId();
		session.setAttribute("loginMember", loginMember);

		String redirectPath = getSafeRedirectPath(request.getParameter("redirect"));
		response.sendRedirect(request.getContextPath()
				+ (redirectPath.isEmpty() ? "/main" : redirectPath));
	}

	/**
	 * 이름과 이메일로 가입한 아이디를 찾는다.
	 */
	private void handleFindCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = trim(request.getParameter("name"));
		String email = trim(request.getParameter("email"));

		if (name.isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
			request.setAttribute("errorMessage", "이름과 이메일을 확인해 주세요.");
			forward(request, response, "findCode.jsp");
			return;
		}

		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setName(name);
		memberDTO.setEmail(email);

		MemberDTO foundMember = memberService.findId(memberDTO);

		if (foundMember == null) {
			request.setAttribute("errorMessage", "입력한 정보와 일치하는 아이디가 없습니다.");
			forward(request, response, "findCode.jsp");
			return;
		}

		request.setAttribute("foundMemberCode", escapeHtml(foundMember.getMemberCode()));
		request.setAttribute("verifiedName", escapeHtml(foundMember.getName()));
		request.setAttribute("verifiedEmail", escapeHtml(foundMember.getEmail()));
		forward(request, response, "findCodeResult.jsp");
	}

	/**
	 * 회원 정보가 일치하면 임시 비밀번호를 발급한다.
	 */
	private void handleFindPassword(HttpServletRequest request, HttpServletResponse response)
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

		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberCode(memberCode);
		memberDTO.setName(name);
		memberDTO.setEmail(email);

		String tempPassword = memberService.findPW(memberDTO);

		if (tempPassword == null) {
			request.setAttribute("errorMessage", "입력한 정보와 일치하는 회원이 없습니다.");
			forward(request, response, "findPassword.jsp");
			return;
		}

		request.setAttribute("verifiedMemberCode", escapeHtml(memberCode));
		request.setAttribute("verifiedName", escapeHtml(name));
		request.setAttribute("verifiedEmail", escapeHtml(email));
		request.setAttribute("tempPassword", escapeHtml(tempPassword));
		forward(request, response, "findPasswordResult.jsp");
	}

	/**
	 * 필수 약관 동의 여부를 확인하고 가입 정보 입력 화면으로 이동한다.
	 */
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

	/**
	 * 회원가입 입력값을 검증하고 회원 정보를 DB에 저장한다.
	 */
	private void handleJoin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String memberCode = trim(request.getParameter("memberCode"));
		String password = trim(request.getParameter("password"));
		String passwordConfirm = trim(request.getParameter("passwordConfirm"));
		String name = trim(request.getParameter("name"));
		String email = trim(request.getParameter("email"));
		String phone1 = trim(request.getParameter("phone1"));
		String phone2 = trim(request.getParameter("phone2"));
		String phone3 = trim(request.getParameter("phone3"));
		String zipcode = trim(request.getParameter("zipcode"));
		String address = trim(request.getParameter("address"));
		String address2 = trim(request.getParameter("address2"));
		String phone = phone1 + "-" + phone2 + "-" + phone3;

		List<String> errorMessages = new ArrayList<String>();
		boolean validMemberCode = MEMBER_CODE_PATTERN.matcher(memberCode).matches();
		boolean validEmail = EMAIL_PATTERN.matcher(email).matches();
		boolean validPhone = phone1.matches("^01[016789]$") && phone2.matches("^\\d{3,4}$")
				&& phone3.matches("^\\d{4}$");
		boolean duplicateMemberCode = validMemberCode && memberService.checkDuplicateId(memberCode);
		boolean duplicateEmail = validEmail && memberService.checkDuplicateEmail(email);
		boolean duplicatePhone = validPhone && memberService.checkDuplicatePhone(phone);

		if (!validMemberCode) {
			errorMessages.add("아이디는 영문 또는 숫자 4~20자로 입력해 주세요.");
		}
		if (!PASSWORD_PATTERN.matcher(password).matches()) {
			errorMessages.add("비밀번호는 영문, 숫자, 특수문자를 포함해 8~20자로 입력해 주세요.");
		}
		if (!password.equals(passwordConfirm)) {
			errorMessages.add("비밀번호 확인이 일치하지 않습니다.");
		}
		if (name.isEmpty()) {
			errorMessages.add("이름을 입력해 주세요.");
		}
		if (!validEmail) {
			errorMessages.add("올바른 이메일을 입력해 주세요.");
		}
		if (!validPhone) {
			errorMessages.add("휴대폰 번호를 확인해 주세요.");
		}
		if (!zipcode.matches("^\\d{5}$") || address.isEmpty() || address2.isEmpty()) {
			errorMessages.add("주소와 상세주소를 입력해 주세요.");
		}
		if (duplicateMemberCode) {
			errorMessages.add("이미 사용 중인 아이디입니다.");
		}
		if (duplicateEmail) {
			errorMessages.add("이미 사용 중인 이메일입니다.");
		}
		if (duplicatePhone) {
			errorMessages.add("이미 사용 중인 휴대폰 번호입니다.");
		}

		if (!errorMessages.isEmpty()) {
			request.setAttribute("joinErrorMessages", errorMessages);
			keepJoinFormValue(request);
			request.setAttribute("codeChecked", validMemberCode && !duplicateMemberCode ? "Y" : "N");
			forward(request, response, "joinForm.jsp");
			return;
		}

		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberCode(memberCode);
		memberDTO.setPassword(password);
		memberDTO.setName(name);
		memberDTO.setEmail(email);
		memberDTO.setPhone(phone1 + "-" + phone2 + "-" + phone3);
		memberDTO.setZipcode(Integer.parseInt(zipcode));
		memberDTO.setAddress(address);
		memberDTO.setAddress2(address2);
		memberDTO.setState("활성");
		memberDTO.setSmsReceiveYN(defaultConsent(request.getParameter("smsReceiveYN")).charAt(0));
		memberDTO.setEmailReceiveYN(defaultConsent(request.getParameter("emailReceiveYN")).charAt(0));

		boolean registerFlag = memberService.register(memberDTO);

		if (!registerFlag) {
			request.setAttribute("errorMessage", "회원가입 중 오류가 발생했습니다.");
			keepJoinFormValue(request);
			request.setAttribute("codeChecked", "Y");
			forward(request, response, "joinForm.jsp");
			return;
		}

		request.setAttribute("joinedName", escapeHtml(name));
		request.setAttribute("joinedMemberCode", escapeHtml(memberCode));
		forward(request, response, "joinComplete.jsp");
	}

	/**
	 * 아이디 중복 여부를 AJAX 응답으로 반환한다.
	 */
	private void handleCheckId(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String memberCode = trim(request.getParameter("memberCode"));

		response.setContentType("text/plain; charset=UTF-8");

		if (!MEMBER_CODE_PATTERN.matcher(memberCode).matches()) {
			response.getWriter().write("INVALID");
			return;
		}

		boolean duplicateFlag = memberService.checkDuplicateId(memberCode);
		response.getWriter().write(duplicateFlag ? "DUPLICATE" : "AVAILABLE");
	}

	/**
	 * 로그인 세션을 제거하고 메인 화면으로 이동한다.
	 */
	private void handleLogout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/main");
	}

	private void forward(HttpServletRequest request, HttpServletResponse response, String jsp)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/kr/user/member/" + jsp);
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
	
	/**
	 * 회원가입 실패 시 비밀번호를 제외한 입력값을 다시 화면에 보여주기 위해 request에 저장한다.
	 */
	private void keepJoinFormValue(HttpServletRequest request) {
		request.setAttribute("memberCode", escapeHtml(trim(request.getParameter("memberCode"))));
		request.setAttribute("name", escapeHtml(trim(request.getParameter("name"))));
		request.setAttribute("email", escapeHtml(trim(request.getParameter("email"))));
		request.setAttribute("phone1", escapeHtml(trim(request.getParameter("phone1"))));
		request.setAttribute("phone2", escapeHtml(trim(request.getParameter("phone2"))));
		request.setAttribute("phone3", escapeHtml(trim(request.getParameter("phone3"))));
		request.setAttribute("zipcode", escapeHtml(trim(request.getParameter("zipcode"))));
		request.setAttribute("address", escapeHtml(trim(request.getParameter("address"))));
		request.setAttribute("address2", escapeHtml(trim(request.getParameter("address2"))));
		request.setAttribute("smsReceiveYN", defaultConsent(request.getParameter("smsReceiveYN")));
		request.setAttribute("emailReceiveYN", defaultConsent(request.getParameter("emailReceiveYN")));
	}

	private String escapeHtml(String value) {
		return value.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&#39;");
	}

	/**
	 * 로그인 후 이동 경로는 현재 애플리케이션 내부의 절대 경로만 허용한다.
	 */
	private String getSafeRedirectPath(String value) {
		String redirectPath = trim(value);
		if (!redirectPath.startsWith("/") || redirectPath.startsWith("//")
				|| redirectPath.indexOf('\r') >= 0 || redirectPath.indexOf('\n') >= 0) {
			return "";
		}
		return redirectPath;
	}

}

package kr.user.inquiry;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.user.member.MemberDTO;

@WebServlet("/user-inquiry/*")
public class InquiryViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 문의 관련 비즈니스 로직을 처리할 Service
	private InquiryService inquiryService;

	// 문의 유형 목록
	private static final Map<Integer, String> CATEGORIES = new LinkedHashMap<Integer, String>();

	static {
		CATEGORIES.put(1, "예매문의");
		CATEGORIES.put(2, "결제문의");
		CATEGORIES.put(3, "회원문의");
		CATEGORIES.put(4, "기타문의");
	}

	public InquiryViewServlet() {
		inquiryService = new InquiryService();
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
			// 로그인하지 않은 사용자는 문의 작성 페이지에 접근할 수 없다.
			if (getLoginMember(request) == null) {
				response.sendRedirect(request.getContextPath() + "/member/login?redirect=/user-inquiry/write");
				return;
			}

			// 문의 작성 페이지로 이동
			prepareCategories(request);
			forward(request, response, "write.jsp");
			break;

		case "/list":
			// 로그인한 회원의 문의 목록 조회
			handleList(request, response);
			break;
		
		case "/success":
			forward(request, response, "success.jsp");
			break;

		case "/detail":
			handleDetail(request, response);
			break;

		case "/edit":
			response.sendRedirect(request.getContextPath() + "/user-inquiry/list");
			break;
			
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
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

	/**
	 * 문의 등록 처리
	 * 
	 * write.jsp에서 넘어온 문의 정보를 읽어서
	 * 로그인한 회원 정보와 함께 DB에 저장한다.
	 */
	private void handleWrite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 로그인한 회원 정보 확인
		MemberDTO loginMember = getLoginMember(request);

		if (loginMember == null) {
			response.sendRedirect(request.getContextPath() + "/member/login");
			return;
		}

		FormData form = readForm(request);
		String errorMessage = validate(form);

		// 입력값 검증 실패 시 다시 작성 페이지로 이동
		if (errorMessage != null) {
			prepareForm(request, form, errorMessage);
			forward(request, response, "write.jsp");
			return;
		}

		// DTO에 문의 등록 정보 담기
		InquiryDTO inquiryDTO = new InquiryDTO();
		inquiryDTO.setMemberCode(loginMember.getMemberCode());
		inquiryDTO.setInquiryCategoryCode(form.categoryCode);
		inquiryDTO.setInquiryTitle(form.title);
		inquiryDTO.setInquiryContent(form.content);

		// DB 저장
		boolean result = inquiryService.addInquiry(inquiryDTO);

		if (result) {
			// 등록 성공 시 등록 성공메세지 출력
			response.sendRedirect(request.getContextPath() + "/user-inquiry/success");
		} else {
			// 등록 실패 시 다시 작성 페이지로 이동
			prepareForm(request, form, "문의 등록에 실패했습니다. 다시 시도해 주세요.");
			forward(request, response, "write.jsp");
		}
	}

	/**
	 * 문의 목록 처리
	 * 
	 * 로그인한 회원의 문의 목록만 조회한다.
	 */
	private void handleList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MemberDTO loginMember = getLoginMember(request);

		if (loginMember == null) {
			response.sendRedirect(request.getContextPath() + "/member/login");
			return;
		}
		
		String result = request.getParameter("result");

		if ("write-success".equals(result)) {
			request.setAttribute("successMessage", "문의가 정상적으로 등록되었습니다.");
		}

		List<InquiryDTO> inquiryList = inquiryService.getInquiryList(loginMember.getMemberCode());

		request.setAttribute("inquiryList", inquiryList);
		forward(request, response, "list.jsp");
		
	}
	
	/**
	 * 문의 상세 처리
	 * 
	 * 로그인한 회원의 특정 문의를 DB에서 조회한 후 detail.jsp로 이동한다.
	 */
	private void handleDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MemberDTO loginMember = getLoginMember(request);

		if (loginMember == null) {
			response.sendRedirect(request.getContextPath() + "/member/login");
			return;
		}

		int inquiryCode = parseInt(request.getParameter("inquiryCode"));

		if (inquiryCode == 0) {
			response.sendRedirect(request.getContextPath() + "/user-inquiry/list");
			return;
		}

		InquiryDTO inquiry = inquiryService.getInquiryDetail(inquiryCode, loginMember.getMemberCode());

		if (inquiry == null) {
			request.setAttribute("errorMessage", "문의 정보를 찾을 수 없습니다.");
			forward(request, response, "detail.jsp");
			return;
		}

		request.setAttribute("inquiry", inquiry);
		request.setAttribute("categoryName", CATEGORIES.get(inquiry.getInquiryCategoryCode()));

		forward(request, response, "detail.jsp");
	}

	/**
	 * 문의 수정 처리
	 * 
	 * 답변이 등록되지 않은 문의만 수정할 수 있다.
	 */
	private void handleEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MemberDTO loginMember = getLoginMember(request);

		if (loginMember == null) {
			response.sendRedirect(request.getContextPath() + "/member/login");
			return;
		}

		String stage = trim(request.getParameter("stage"));
		int inquiryCode = parseInt(request.getParameter("inquiryCode"));

		if (inquiryCode == 0) {
			response.sendRedirect(request.getContextPath() + "/user-inquiry/list");
			return;
		}

		// 상세 페이지에서 수정 버튼을 눌러 수정 폼으로 들어오는 단계
		if (!"submit".equals(stage)) {
			InquiryDTO inquiry = inquiryService.getInquiryDetail(inquiryCode, loginMember.getMemberCode());

			if (inquiry == null) {
				response.sendRedirect(request.getContextPath() + "/user-inquiry/list");
				return;
			}

			// 답변 완료된 문의는 수정 불가
			if (!"답변대기".equals(inquiry.getInquiryStatus())) {
				response.sendRedirect(request.getContextPath() + "/user-inquiry/detail?inquiryCode=" + inquiryCode);
				return;
			}

			prepareCategories(request);
			request.setAttribute("inquiryCode", inquiry.getInquiryCode());
			request.setAttribute("selectedCategoryCode", inquiry.getInquiryCategoryCode());
			request.setAttribute("formTitle", escapeHtml(inquiry.getInquiryTitle()));
			request.setAttribute("formContent", escapeHtml(inquiry.getInquiryContent()));

			forward(request, response, "edit.jsp");
			return;
		}

		// 수정 완료 버튼을 누른 단계
		FormData form = readForm(request);
		String errorMessage = validate(form);

		if (errorMessage != null) {
			prepareCategories(request);
			request.setAttribute("inquiryCode", inquiryCode);
			request.setAttribute("selectedCategoryCode", form.categoryCode);
			request.setAttribute("formTitle", escapeHtml(form.title));
			request.setAttribute("formContent", escapeHtml(form.content));
			request.setAttribute("errorMessage", errorMessage);

			forward(request, response, "edit.jsp");
			return;
		}

		InquiryDTO inquiryDTO = new InquiryDTO();
		inquiryDTO.setInquiryCode(inquiryCode);
		inquiryDTO.setMemberCode(loginMember.getMemberCode());
		inquiryDTO.setInquiryCategoryCode(form.categoryCode);
		inquiryDTO.setInquiryTitle(form.title);
		inquiryDTO.setInquiryContent(form.content);

		boolean result = inquiryService.updateInquiry(inquiryDTO);

		if (result) {
			response.sendRedirect(request.getContextPath() + "/user-inquiry/detail?inquiryCode=" + inquiryCode);
		} else {
			prepareCategories(request);
			request.setAttribute("inquiryCode", inquiryCode);
			request.setAttribute("selectedCategoryCode", form.categoryCode);
			request.setAttribute("formTitle", escapeHtml(form.title));
			request.setAttribute("formContent", escapeHtml(form.content));
			request.setAttribute("errorMessage", "이미 답변이 등록된 문의는 수정할 수 없습니다.");

			forward(request, response, "edit.jsp");
		}
	}

	/**
	 * 세션에서 로그인한 회원 정보 조회
	 * 
	 * 로그인 성공 시 Member 쪽에서 session.setAttribute("loginMember", loginMember)
	 * 형태로 저장했기 때문에 같은 이름으로 꺼낸다.
	 */
	private MemberDTO getLoginMember(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (MemberDTO) session.getAttribute("loginMember");
	}

	/**
	 * 미리보기용 문의 객체 생성
	 * 
	 * 현재 수정 기능에서만 임시로 사용한다.
	 */
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

	/**
	 * 입력값 오류 발생 시 작성 페이지에 다시 보여줄 값 세팅
	 */
	private void prepareForm(HttpServletRequest request, FormData form, String errorMessage) {
		prepareCategories(request);
		request.setAttribute("selectedCategoryCode", form.categoryCode);
		request.setAttribute("formTitle", escapeHtml(form.title));
		request.setAttribute("formContent", escapeHtml(form.content));
		request.setAttribute("errorMessage", errorMessage);
	}

	/**
	 * 문의 유형 목록을 request에 저장
	 */
	private void prepareCategories(HttpServletRequest request) {
		request.setAttribute("categories", CATEGORIES);
	}

	/**
	 * write.jsp 또는 edit.jsp에서 넘어온 입력값 읽기
	 */
	private FormData readForm(HttpServletRequest request) {
		int categoryCode = parseInt(request.getParameter("inquiryCategoryCode"));
		String title = trim(request.getParameter("inquiryTitle"));
		String content = trim(request.getParameter("inquiryContent"));

		return new FormData(categoryCode, title, content);
	}

	/**
	 * 문의 등록 입력값 검증
	 * 
	 * DB의 inquiry_title은 VARCHAR2(100),
	 * inquiry_content는 VARCHAR2(255)이므로 길이를 DB 컬럼에 맞춘다.
	 */
	private String validate(FormData form) {
		if (!CATEGORIES.containsKey(form.categoryCode)) {
			return "문의 유형을 선택해 주세요.";
		}

		if (form.title.isEmpty() || form.title.length() > 100) {
			return "제목은 1자 이상 100자 이하로 입력해 주세요.";
		}

		if (form.content.isEmpty() || form.content.length() > 255) {
			return "문의 내용은 1자 이상 255자 이하로 입력해 주세요.";
		}

		return null;
	}

	/**
	 * JSP forward 공통 메서드
	 */
	private void forward(HttpServletRequest request, HttpServletResponse response, String jsp)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/kr/user/inquiry/" + jsp);
		dispatcher.forward(request, response);
	}

	/**
	 * 한글 인코딩 설정
	 */
	private void prepareEncoding(HttpServletRequest request, HttpServletResponse response)
			throws java.io.UnsupportedEncodingException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	}

	/**
	 * 문자열을 int로 변환
	 * 변환 실패 시 0 반환
	 */
	private int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException exception) {
			return 0;
		}
	}

	/**
	 * null 방지 trim
	 */
	private String trim(String value) {
		return value == null ? "" : value.trim();
	}

	/**
	 * HTML 특수문자 변환
	 * 
	 * 화면에 다시 출력할 때 태그가 실행되지 않도록 처리한다.
	 */
	private String escapeHtml(String value) {
		return value.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&#39;");
	}

	/**
	 * 문의 작성 폼 데이터를 임시로 담는 내부 클래스
	 */
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
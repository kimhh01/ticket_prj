package kr.admin.inquiry;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.admin.common.BoardRangeDTO;

@WebServlet({ "admin/inquiry", "admin/inquiry/detail", "admin/inquiry/reply" })
public class InquiryManagementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private InquiryManagementService service;

	@Override
	public void init() throws ServletException {
		service = new InquiryManagementService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();

		if ("/inquiry/detail".equals(servletPath)) {
			getInquiryDetail(request, response);
			return;
		}

		getInquiryList(request, response);
	}

	/**
	 * 1:1 문의 목록 화면
	 */
	private void getInquiryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("activeMenu", "inquiry");

		String status = request.getParameter("status");

		if (status == null || status.trim().isEmpty()) {
			status = "all";
		}

		String keyword = request.getParameter("keyword");

		String searchDate = request.getParameter("searchDate");

		if (keyword == null) {
			keyword = "";
		}

		if (searchDate == null) {
			searchDate = "";
		}

		int currentPage = 1;

		String pageParam = request.getParameter("page");

		if (pageParam != null && !pageParam.trim().isEmpty()) {
			try {
				currentPage = Integer.parseInt(pageParam);
			} catch (Exception e) {
				currentPage = 1;
			}
		}

		BoardRangeDTO range = new BoardRangeDTO();

		int pageScale = service.pageScale();

		range.setPage(currentPage);
		range.setPageScale(pageScale);

		range.setStartNum(service.startNum(currentPage, pageScale));

		range.setEndNum(service.endNum(currentPage, pageScale));

		int totalCount = service.totalCount("all", keyword, searchDate);

		int waitingCount = service.totalCount("waiting", keyword, searchDate);

		int completeCount = service.totalCount("complete", keyword, searchDate);

		/*
		 * 현재 탭 기준 전체 건수 pagination 계산에는 이 값을 써야 함
		 */
		int listTotalCount = service.totalCount(status, keyword, searchDate);

		range.setTotalCount(listTotalCount);

		range.setTotalPage(service.totalPage(listTotalCount, pageScale));

		List<InquiryListDTO> inquiryList = service.getInquiryDashboardList(range, status, keyword, searchDate);

		request.setAttribute("inquiryList", inquiryList);
		request.setAttribute("range", range);

		request.setAttribute("totalCount", totalCount);
		request.setAttribute("waitingCount", waitingCount);
		request.setAttribute("completeCount", completeCount);

		request.setAttribute("keyword", keyword);
		request.setAttribute("searchDate", searchDate);
		
		request.getRequestDispatcher(
		        "/manage/inquiry/inquiryManagement.jsp")
		       .forward(request, response);
	}

	/**
	 * 1:1 문의 상세 JSON 조회
	 */
	private void getInquiryDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json; charset=UTF-8");

		int inquiryCode = 0;

		String inquiryCodeParam = request.getParameter("inquiryCode");

		if (inquiryCodeParam != null && !inquiryCodeParam.trim().isEmpty()) {
			try {
				inquiryCode = Integer.parseInt(inquiryCodeParam);
			} catch (Exception e) {
				inquiryCode = 0;
			}
		}

		if (inquiryCode <= 0) {
			response.getWriter().write("{\"success\":false}");
			return;
		}

		InquiryDetailDTO detail = service.getInquiryDetail(inquiryCode);

		if (detail == null) {
			response.getWriter().write("{\"success\":false}");
			return;
		}

		StringBuilder json = new StringBuilder();

		json.append("{");
		json.append("\"success\":true,");
		json.append("\"inquiryCode\":").append(detail.getInquiryCode()).append(",");
		json.append("\"inquiryContent\":\"").append(jsonEscape(detail.getInquiryContent())).append("\",");
		json.append("\"replyContent\":\"").append(jsonEscape(detail.getReplyContent())).append("\",");
		json.append("\"replyDate\":\"").append(jsonEscape(detail.getReplyDate())).append("\",");
		json.append("\"adminId\":\"").append(jsonEscape(detail.getAdminId())).append("\"");
		json.append("}");

		response.getWriter().write(json.toString());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String servletPath = request.getServletPath();

		if ("/inquiry/reply".equals(servletPath)) {
			replyInquiry(request, response);
			return;
		}

		doGet(request, response);
	}

	/**
	 * 답변 등록 / 수정
	 */
	private void replyInquiry(HttpServletRequest request, HttpServletResponse response) throws IOException {

		int inquiryCode = 0;

		String inquiryCodeParam = request.getParameter("inquiryCode");

		if (inquiryCodeParam != null && !inquiryCodeParam.trim().isEmpty()) {
			try {
				inquiryCode = Integer.parseInt(inquiryCodeParam);
			} catch (Exception e) {
				inquiryCode = 0;
			}
		}

		String replyContent = request.getParameter("replyContent");

		if (replyContent == null) {
			replyContent = "";
		}

		replyContent = replyContent.trim();

		if (inquiryCode <= 0 || replyContent.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/inquiry?error=invalid");
			return;
		}

		/*
		 * 관리자 로그인 세션 값 가져오기 프로젝트에서 실제로 저장한 세션 이름에 맞춰야 함.
		 */
		Object adminIdObj = request.getSession().getAttribute("adminId");

		if (adminIdObj == null) {
			adminIdObj = request.getSession().getAttribute("loginAdminId");
		}

		if (adminIdObj == null) {
			adminIdObj = request.getSession().getAttribute("adminCode");
		}


		if (adminIdObj == null) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<script>");
			response.getWriter().println("alert('관리자 로그인 정보가 없습니다. 다시 로그인해주세요.');");
			response.getWriter().println("location.href='" + request.getContextPath() +
			"/inquiry';"); response.getWriter().println("</script>"); return; }

		String adminId = String.valueOf(adminIdObj);

		InquiryDetailDTO inquiry = new InquiryDetailDTO();

		inquiry.setInquiryCode(inquiryCode);
		inquiry.setReplyContent(replyContent);
		inquiry.setAdminId(adminId);

		boolean result = service.replyInquiry(inquiry);

		if (result) {
			response.sendRedirect(request.getContextPath() + "/inquiry");
		} else {
			response.sendRedirect(request.getContextPath() + "/inquiry?error=reply");
		}
	}

	/**
	 * JSON 문자열 이스케이프 처리
	 */
	private String jsonEscape(String value) {

		if (value == null) {
			return "";
		}

		return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n").replace("\t",
				"\\t");
	}
}

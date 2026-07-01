package kr.admin.inquiry;

import java.util.List;

import kr.admin.common.BoardRangeDTO;

public class InquiryManagementService {

	private InquiryManagementDAO dao;

	public InquiryManagementService() {
		dao = new InquiryManagementDAO();
	}

	public int pageScale() {
		return 6;
	}

	public int totalPage(int totalCount, int pageScale) {
		int totalPage = totalCount / pageScale;

		if (totalCount % pageScale != 0) {
			totalPage++;
		}

		return totalPage;
	}

	public int totalCount(String status, String keyword, String searchDate) {

		return dao.selectTotalCount(status, keyword, searchDate);
	}

	public int startNum(int currentPage, int pageScale) {
		return (currentPage - 1) * pageScale + 1;
	}

	public int endNum(int currentPage, int pageScale) {
		return currentPage * pageScale;
	}

	public List<InquiryListDTO> getInquiryDashboardList(BoardRangeDTO range, String status, String keyword,
			String searchDate) {

		return dao.selectMatchDashboard(range, status, keyword, searchDate);
	}

	public InquiryDetailDTO getInquiryDetail(int inquiryCode) {
		return dao.selectInquiryDetail(inquiryCode);
	}

	public boolean replyInquiry(InquiryDetailDTO inquiry) {
		int result = dao.updateInquiryAnswer(inquiry);

		return result > 0;
	}

	public boolean removeInquiry(int inquiryCode) {
		return false;
	}
}

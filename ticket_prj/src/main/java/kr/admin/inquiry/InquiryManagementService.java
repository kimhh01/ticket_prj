package kr.admin.inquiry;

import java.util.List;

import common.BoardRangeDTO;

public class InquiryManagementService {
	
	private InquiryManagementDAO dao;
	 
	public InquiryManagementService() {
	        dao = new InquiryManagementDAO();
	    }
	
	public int totalCount(String status) {
		return dao.selectTotalCount(status);
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
	
	public int startNum(int currentPage, int pageScale) {
		return (currentPage - 1) * pageScale + 1;
	}
	
	public int endNum(int currentPage, int pageScale) {
		 return currentPage * pageScale;
	}
	
	public List<InquiryListDTO> getInquiryDashboardList(
	        BoardRangeDTO range,
	        String status){

	    return dao.selectMatchDashboard(range, status);
	}
	
	public InquiryDetailDTO getInquiryDetail(int inquiryCode) {
		return dao.selectInquiryDetail(inquiryCode);
	}
	
	public boolean replyInquiry(InquiryDetailDTO inquiry) {
		return false;
	}
	
	public boolean removeInquiry(int inquiryCode) {
		return false;
	}
}	

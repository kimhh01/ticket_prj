package Inquiry;

import java.util.List;

import common.BoardRangeDTO;

public class InquiryManagementService {
	
	public int totalCount(BoardRangeDTO range) {
		return 0;
	}
	
	public int pageScale() {
		return 0;
	}
	
	public int totalPage(int totalCount, int pageScale) {
		return pageScale;
	}
	
	public int startNum(int currentPage, int pageScale) {
		return pageScale;
	}
	
	public int endNum(int currentPage, int pageScale) {
		return pageScale;
	}
	
	public List<InquiryListDTO> getEventDashboardList(BoardRangeDTO range){
		return null;
	}
	
	public InquiryDetailDTO getInquiryDetail(int inquiryCode) {
		return null;
	}
	
	public boolean replyInquiry(InquiryDetailDTO inquiry) {
		return false;
	}
	
	public boolean removeInquiry(int inquiryCode) {
		return false;
	}
}	

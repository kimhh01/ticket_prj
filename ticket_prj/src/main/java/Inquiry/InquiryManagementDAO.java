package Inquiry;

import java.util.List;

import common.BoardRangeDTO;

public class InquiryManagementDAO {
	
	public int selectTotalCount(BoardRangeDTO range) {
		return 0;
	}
	
	public List<InquiryListDTO> selectMatchDashboard(BoardRangeDTO range){
		return null;
	}
	
	public InquiryDetailDTO selectInquiryDetail(int inquiryCode) {
		return null;
	}
	
	public int updateInquiryAnswer(InquiryDetailDTO inquiry) {
		return 0;
	}
	
	public int deleteInquiry(int inquiryCode) {
		return inquiryCode;
	}
}

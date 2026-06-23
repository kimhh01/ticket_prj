package kr.admin.inquiry;

public class InquiryDetailDTO {
	private int inquiryCode;              
	private String inquiryContent;
	private String replyContent; 
	private String replyDate;
	private String adminId;
	public int getInquiryCode() {
		return inquiryCode;
	}
	public void setInquiryCode(int inquiryCode) {
		this.inquiryCode = inquiryCode;
	}
	public String getInquiryContent() {
		return inquiryContent;
	}
	public void setInquiryContent(String inquiryContent) {
		this.inquiryContent = inquiryContent;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	@Override
	public String toString() {
		return "InquiryDetailDTO [inquiryCode=" + inquiryCode + ", inquiryContent=" + inquiryContent + ", replyContent="
				+ replyContent + ", replyDate=" + replyDate + ", adminId=" + adminId + "]";
	} 
	
	
}

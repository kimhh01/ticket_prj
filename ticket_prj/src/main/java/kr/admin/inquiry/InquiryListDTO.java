package kr.admin.inquiry;

public class InquiryListDTO {
	
	private int inquiryCode;  
	private String inquiryType;      
	private String inquiryTitle;                   
	private String memberId;                      
	private String InquiryDate;             
	private String state;
	public int getInquiryCode() {
		return inquiryCode;
	}
	public void setInquiryCode(int inquiryCode) {
		this.inquiryCode = inquiryCode;
	}
	public String getInquiryType() {
		return inquiryType;
	}
	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
	}
	public String getInquiryTitle() {
		return inquiryTitle;
	}
	public void setInquiryTitle(String inquiryTitle) {
		this.inquiryTitle = inquiryTitle;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getInquiryDate() {
		return InquiryDate;
	}
	public void setInquiryDate(String inquiryDate) {
		InquiryDate = inquiryDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "InquiryListDTO [inquiryCode=" + inquiryCode + ", inquiryType=" + inquiryType + ", inquiryTitle="
				+ inquiryTitle + ", memberId=" + memberId + ", InquiryDate=" + InquiryDate + ", state=" + state + "]";
	}
	
	
}

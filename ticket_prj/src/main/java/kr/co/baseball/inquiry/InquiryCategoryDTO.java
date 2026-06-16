package kr.co.baseball.inquiry;

public class InquiryCategoryDTO {

	private int inquiryCategoryCode;		// 문의 유형 코드
	private int adminCode;				// 관리자 코드
	private String inquiryType;			// 문의 유형 이름 ex) 예매문의, 결제문의, 회원문의

	public InquiryCategoryDTO() {
	}

	public InquiryCategoryDTO(int inquiryCategoryCode, int adminCode, String inquiryType) {
		this.inquiryCategoryCode = inquiryCategoryCode;
		this.adminCode = adminCode;
		this.inquiryType = inquiryType;
	}

	public int getInquiryCategoryCode() {
		return inquiryCategoryCode;
	}

	public void setInquiryCategoryCode(int inquiryCategoryCode) {
		this.inquiryCategoryCode = inquiryCategoryCode;
	}

	public int getAdminCode() {
		return adminCode;
	}

	public void setAdminCode(int adminCode) {
		this.adminCode = adminCode;
	}

	public String getInquiryType() {
		return inquiryType;
	}

	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
	}

	@Override
	public String toString() {
		return "InquiryCategoryDTO [inquiryCategoryCode=" + inquiryCategoryCode + ", adminCode=" + adminCode
				+ ", inquiryType=" + inquiryType + "]";
	}

}
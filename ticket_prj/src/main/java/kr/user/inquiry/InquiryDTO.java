package kr.user.inquiry;

import java.util.Date;

public class InquiryDTO {
	
	private int inquiryCode;				// 문의 코드
	private String memberCode;				// 회원 코드
	private int inquiryCategoryCode;		// 문의 유형 코드
	private String inquiryTitle;			// 문의 제목
	private String inquiryContent;		// 문의 내용
	private Date inquiryDate;			// 문의 작성일
	private String replyContent;			// 답변 내용
	private Date replyDate;				// 답변 작성일
	private String inquiryStatus;		// 문의 상태 ex) 답변대기, 답변완료
	
	public InquiryDTO() {
	}

	public InquiryDTO(int inquiryCode, String memberCode, int inquiryCategoryCode, String inquiryTitle,
			String inquiryContent, Date inquiryDate, String replyContent, Date replyDate, String inquiryStatus) {
		this.inquiryCode = inquiryCode;
		this.memberCode = memberCode;
		this.inquiryCategoryCode = inquiryCategoryCode;
		this.inquiryTitle = inquiryTitle;
		this.inquiryContent = inquiryContent;
		this.inquiryDate = inquiryDate;
		this.replyContent = replyContent;
		this.replyDate = replyDate;
		this.inquiryStatus = inquiryStatus;
	}

	public int getInquiryCode() {
		return inquiryCode;
	}

	public void setInquiryCode(int inquiryCode) {
		this.inquiryCode = inquiryCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public int getInquiryCategoryCode() {
		return inquiryCategoryCode;
	}

	public void setInquiryCategoryCode(int inquiryCategoryCode) {
		this.inquiryCategoryCode = inquiryCategoryCode;
	}

	public String getInquiryTitle() {
		return inquiryTitle;
	}

	public void setInquiryTitle(String inquiryTitle) {
		this.inquiryTitle = inquiryTitle;
	}

	public String getInquiryContent() {
		return inquiryContent;
	}

	public void setInquiryContent(String inquiryContent) {
		this.inquiryContent = inquiryContent;
	}

	public Date getInquiryDate() {
		return inquiryDate;
	}

	public void setInquiryDate(Date inquiryDate) {
		this.inquiryDate = inquiryDate;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getInquiryStatus() {
		return inquiryStatus;
	}

	public void setInquiryStatus(String inquiryStatus) {
		this.inquiryStatus = inquiryStatus;
	}

	@Override
	public String toString() {
		return "InquiryDTO [inquiryCode=" + inquiryCode + ", memberCode=" + memberCode + ", inquiryCategoryCode="
				+ inquiryCategoryCode + ", inquiryTitle=" + inquiryTitle + ", inquiryContent=" + inquiryContent
				+ ", inquiryDate=" + inquiryDate + ", replyContent=" + replyContent + ", replyDate=" + replyDate
				+ ", inquiryStatus=" + inquiryStatus + "]";
	}

}
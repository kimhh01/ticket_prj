package kr.user.inquiry;

import java.util.List;

public class InquiryService {

	private InquiryDAO inquiryDAO;

	public InquiryService() {
		inquiryDAO = new InquiryDAO();
	}

	/**
	 * 문의 카테고리 목록 조회
	 * 
	 * @return 문의 카테고리 목록
	 */
	public List<InquiryCategoryDTO> getInquiryCategoryList() {
		return inquiryDAO.selectInquiryCategoryList();
	}

	/**
	 * 1:1 문의 등록
	 * 
	 * @param inquiryDTO 등록할 문의 정보
	 */
	public void addInquiry(InquiryDTO inquiryDTO) {
		inquiryDAO.insertInquiry(inquiryDTO);
	}

	/**
	 * 회원별 1:1 문의 목록 조회
	 * 
	 * @param memberCode 회원 코드
	 * @return 문의 목록
	 */
	public List<InquiryDTO> getInquiryList(String memberCode) {
		return inquiryDAO.selectInquiryList(memberCode);
	}

	/**
	 * 1:1 문의 상세 조회
	 * 
	 * @param inquiryCode 문의 코드
	 * @param memberCode 회원 코드
	 * @return 문의 상세 정보
	 */
	public InquiryDTO getInquiryDetail(int inquiryCode, String memberCode) {
		return inquiryDAO.selectInquiryDetail(inquiryCode, memberCode);
	}

	/**
	 * 1:1 문의 수정
	 * 답변이 등록되지 않은 문의만 수정할 수 있다.
	 * 
	 * @param inquiryDTO 수정할 문의 정보
	 * @return 수정 성공 여부
	 */
	public boolean updateInquiry(InquiryDTO inquiryDTO) {
		int replyCount = inquiryDAO.selectReplyStatus(
				inquiryDTO.getInquiryCode(),
				inquiryDTO.getMemberCode()
		);

		// 이미 답변이 등록된 문의는 수정 불가
		if (replyCount > 0) {
			return false;
		}

		inquiryDAO.updateInquiry(inquiryDTO);
		return true;
	}

	/**
	 * 문의 수정 가능 여부 확인
	 * 답변이 등록되지 않았으면 true, 답변이 등록되었으면 false
	 * 
	 * @param inquiryCode 문의 코드
	 * @param memberCode 회원 코드
	 * @return 수정 가능 여부
	 */
	public boolean checkReplyStatus(int inquiryCode, String memberCode) {
		int replyCount = inquiryDAO.selectReplyStatus(inquiryCode, memberCode);
		return replyCount == 0;
	}

}
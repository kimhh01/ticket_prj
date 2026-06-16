package kr.co.baseball.inquiry;

import java.util.List;

public class InquiryDAO {

	// DB 연결 객체
	// private DBConnection dbCon;

	/**
	 * 문의 카테고리 목록 조회
	 * 예매문의, 결제문의, 회원문의, 기타문의 등의 목록을 조회한다.
	 * 
	 * @return 문의 카테고리 목록
	 */
	public List<InquiryCategoryDTO> selectInquiryCategoryList() {
		return null;
	}

	/**
	 * 1:1 문의 등록
	 * 사용자가 작성한 문의 내용을 DB에 추가한다.
	 * 
	 * @param inquiryDTO 등록할 문의 정보
	 */
	public void insertInquiry(InquiryDTO inquiryDTO) {
		
	}

	/**
	 * 회원별 1:1 문의 목록 조회
	 * 로그인한 회원의 문의 목록만 조회한다.
	 * 
	 * @param memberCode 회원 코드
	 * @return 문의 목록
	 */
	public List<InquiryDTO> selectInquiryList(String memberCode) {
		return null;
	}

	/**
	 * 1:1 문의 상세 조회
	 * inquiryCode와 memberCode를 함께 사용해서 본인 문의만 조회되도록 한다.
	 * 
	 * @param inquiryCode 문의 코드
	 * @param memberCode 회원 코드
	 * @return 문의 상세 정보
	 */
	public InquiryDTO selectInquiryDetail(int inquiryCode, String memberCode) {
		return null;
	}

	/**
	 * 1:1 문의 수정
	 * 제목, 내용, 문의 유형 등을 수정한다.
	 * 실제 수정 가능 여부는 Service에서 먼저 검사한다.
	 * 
	 * @param inquiryDTO 수정할 문의 정보
	 */
	public void updateInquiry(InquiryDTO inquiryDTO) {
		
	}

	/**
	 * 답변 등록 여부 조회
	 * 답변이 등록된 문의인지 확인한다.
	 * 답변이 있으면 1, 없으면 0을 반환한다고 가정한다.
	 * 
	 * @param inquiryCode 문의 코드
	 * @param string 회원 코드
	 * @return 답변 등록 여부
	 */
	public int selectReplyStatus(int inquiryCode, String memberCode) {
		return 0;
	}

}
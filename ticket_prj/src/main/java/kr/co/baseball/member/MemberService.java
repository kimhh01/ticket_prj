package kr.co.baseball.member;

public class MemberService {

	private MemberDAO memberDAO;

	public MemberService() {
		memberDAO = new MemberDAO();
	}

	/**
	 * 로그인 처리
	 * 
	 * @param memberDTO 로그인할 아이디, 비밀번호 정보
	 * @return 로그인 성공 시 회원 정보, 실패 시 null
	 */
	public MemberDTO login(MemberDTO memberDTO) {
		return memberDAO.selectLogin(memberDTO);
	}

	/**
	 * 회원가입 처리
	 * 
	 * @param memberDTO 가입할 회원 정보
	 * @return 가입 성공 여부
	 */
	public boolean register(MemberDTO memberDTO) {
		return memberDAO.insertMember(memberDTO) == 1;
	}

	/**
	 * 아이디 중복 확인
	 * true면 중복, false면 사용 가능
	 * 
	 * @param memberId 확인할 회원 아이디
	 * @return 아이디 중복 여부
	 */
	public boolean checkDuplicateId(String memberId) {
		int count = memberDAO.selectDuplicateId(memberId);
		return count > 0;
	}

	/**
	 * 아이디 찾기
	 * 
	 * @param memberDTO 이름, 이메일, 휴대폰 번호 등의 정보
	 * @return 조회된 회원 정보
	 */
	public MemberDTO findId(MemberDTO memberDTO) {
		return memberDAO.selectId(memberDTO);
	}

	/**
	 * 비밀번호 찾기
	 * 회원 정보가 일치하면 임시 비밀번호를 생성하고 DB에 반영한다.
	 * 
	 * @param memberDTO 비밀번호 찾기에 필요한 회원 정보
	 * @return 임시 비밀번호, 실패 시 null
	 */
	public String findPW(MemberDTO memberDTO) {
		MemberDTO findMemberDTO = memberDAO.selectMemberForPW(memberDTO);

		// 일치하는 회원이 없으면 null 반환
		if (findMemberDTO == null) {
			return null;
		}

		// 임시 비밀번호 생성
		String tempPassword = "temp" + (int)(Math.random() * 900000 + 100000);

		// DTO에 임시 비밀번호 세팅
		findMemberDTO.setPassword(tempPassword);

		// DB 비밀번호 변경
		memberDAO.updatePassword(findMemberDTO);

		return tempPassword;
	}

	/**
	 * 회원 정보 수정
	 * 
	 * @param memberDTO 수정할 회원 정보
	 */
	public void updateMember(MemberDTO memberDTO) {
		memberDAO.updateMember(memberDTO);
	}

}
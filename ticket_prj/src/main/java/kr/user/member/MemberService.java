package kr.user.member;

public class MemberService {

	private MemberDAO memberDAO;

	public MemberService() {
		memberDAO = new MemberDAO();
	}

	/**
	 * 로그인 정보를 확인하고 일치하는 회원 정보를 반환한다.
	 */
	public MemberDTO login(MemberDTO memberDTO) {
		return memberDAO.selectLogin(memberDTO);
	}

	/**
	 * 회원가입 정보를 DB에 저장한다.
	 */
	public boolean register(MemberDTO memberDTO) {
		return memberDAO.insertMember(memberDTO) == 1;
	}

	/**
	 * 입력한 아이디가 이미 사용 중인지 확인한다.
	 */
	public boolean checkDuplicateId(String memberId) {
		int count = memberDAO.selectDuplicateId(memberId);
		return count > 0;
	}

	/**
	 * 입력한 이메일이 이미 사용 중인지 확인한다.
	 */
	public boolean checkDuplicateEmail(String email) {
		int count = memberDAO.selectDuplicateEmail(email);
		return count > 0;
	}

	/**
	 * 입력한 휴대폰 번호가 이미 사용 중인지 확인한다.
	 */
	public boolean checkDuplicatePhone(String phone) {
		int count = memberDAO.selectDuplicatePhone(phone);
		return count > 0;
	}

	/**
	 * 이름과 이메일로 아이디를 찾는다.
	 */
	public MemberDTO findId(MemberDTO memberDTO) {
		return memberDAO.selectId(memberDTO);
	}

	/**
	 * 회원 정보가 일치하면 임시 비밀번호를 생성해 DB에 저장한다.
	 */
	public String findPW(MemberDTO memberDTO) {
		MemberDTO findMemberDTO = memberDAO.selectMemberForPW(memberDTO);

		if (findMemberDTO == null) {
			return null;
		}

		String tempPassword = "temp" + (int)(Math.random() * 900000 + 100000);
		findMemberDTO.setPassword(tempPassword);

		if (memberDAO.updatePassword(findMemberDTO) != 1) {
			return null;
		}

		return tempPassword;
	}

	/**
	 * 회원 정보를 수정한다.
	 */
	public void updateMember(MemberDTO memberDTO) {
		memberDAO.updateMember(memberDTO);
	}

}

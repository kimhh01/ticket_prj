package kr.user.member;

public class MemberDAO {

	// DB 연결 객체
	// private DBConnection dbCon;

	/**
	 * 로그인 회원 조회
	 * 아이디와 비밀번호가 일치하는 회원 정보를 조회한다.
	 * 
	 * @param memberDTO 로그인할 아이디, 비밀번호 정보
	 * @return 로그인한 회원 정보
	 */
	public MemberDTO selectLogin(MemberDTO memberDTO) {
		return null;
	}

	/**
	 * 회원가입
	 * 회원 정보를 DB에 추가한다.
	 * 
	 * @param memberDTO 가입할 회원 정보
	 * @return 추가된 행의 수
	 */
	public int insertMember(MemberDTO memberDTO) {
		return 0;
	}

	/**
	 * 아이디 중복 확인
	 * 입력한 아이디가 이미 존재하는지 확인한다.
	 * 
	 * @param memberId 확인할 회원 아이디
	 * @return 존재하면 1 이상, 없으면 0
	 */
	public int selectDuplicateId(String memberId) {
		return 0;
	}

	/**
	 * 아이디 찾기
	 * 이름, 이메일, 휴대폰 번호 등을 이용해서 회원 아이디를 조회한다.
	 * 
	 * @param memberDTO 아이디 찾기에 필요한 회원 정보
	 * @return 조회된 회원 정보
	 */
	public MemberDTO selectId(MemberDTO memberDTO) {
		return null;
	}

	/**
	 * 비밀번호 찾기 대상 회원 확인
	 * 아이디, 이름, 이메일 등의 정보가 일치하는 회원을 조회한다.
	 * 
	 * @param memberDTO 비밀번호 찾기에 필요한 회원 정보
	 * @return 조회된 회원 정보
	 */
	public MemberDTO selectMemberForPW(MemberDTO memberDTO) {
		return null;
	}

	/**
	 * 임시 비밀번호 또는 새 비밀번호 변경
	 * 
	 * @param memberDTO 변경할 비밀번호 정보
	 */
	public void updatePassword(MemberDTO memberDTO) {
		
	}

	/**
	 * 회원 정보 수정
	 * 이메일, 전화번호, 주소, 수신 여부 등을 수정한다.
	 * 
	 * @param memberDTO 수정할 회원 정보
	 */
	public void updateMember(MemberDTO memberDTO) {
		
	}

}
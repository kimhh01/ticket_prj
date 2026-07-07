package member;

import java.util.List;

import kr.admin.common.BoardRangeDTO;

public class MemberManagementDAO {

	public int selectTotalCount(String search, String filter) {
		return 0;
	}// selectTotalCount

	public List<MemberListDTO> selectMemberList(BoardRangeDTO brDTO, String search, String filter) {
		return null;
	}// selectMemberList

	public MemberDetailDTO selectMemberDetail(String memberId) {
		return null;
	}// selectMemberDetail

	public List<MemberPayDTO> selectPayHistory(String memberId) {
		return null;
	}// selectPayHistory

	public int updateMemberState(String memberId) {
		return 0;
	}// updateMemberState

}// class
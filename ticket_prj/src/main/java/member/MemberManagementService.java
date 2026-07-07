package member;

import java.util.List;

import kr.admin.common.BoardRangeDTO;

public class MemberManagementService {

	private MemberManagementDAO memberManagementDAO;

	public MemberManagementService() {
		memberManagementDAO = new MemberManagementDAO();
	}// MemberManagementService

	public int getTotalCount(String search, String gradeFilter) {
		return memberManagementDAO.selectTotalCount(search, gradeFilter);
	}// getTotalCount

	public List<MemberListDTO> getMemberList(BoardRangeDTO brDTO, String search, String gradeFilter) {
		return memberManagementDAO.selectMemberList(brDTO, search, gradeFilter);
	}// getMemberList

	public MemberDetailDTO getMemberDetail(String memberId) {
		return memberManagementDAO.selectMemberDetail(memberId);
	}// getMemberDetail

	public List<MemberPayDTO> getPayHistory(String memberId) {
		return memberManagementDAO.selectPayHistory(memberId);
	}// getPayHistory

	public boolean updateMemberState(String memberId) {
		return memberManagementDAO.updateMemberState(memberId) > 0;
	}// updateMemberState

}// class
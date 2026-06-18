package member;

import java.util.List;

public class MemberManagementService {
	
	private MemberManagementDAO memberManagementDAO;

    public MemberManagementService() {
        memberManagementDAO = new MemberManagementDAO();
    }//MemberManagementService

    public List<MemberListDTO> getMemberList(String search, String gradeFilter) {
        return memberManagementDAO.selectMemberList(search, gradeFilter);
    }//getMemberList

    public MemberDetailDTO getMemberDetail(String memberId) {
        return memberManagementDAO.selectMemberDetail(memberId);
    }//getMemberDetail

    public List<MemberPayDTO> getPayHistory(String memberId) {
        return memberManagementDAO.selectPayHistory(memberId);
    }//getPayHistory

    public boolean updateMemberState(String memberId) {
        return memberManagementDAO.updateMemberState(memberId) > 0;
    }//updateMemberState
    
}//class

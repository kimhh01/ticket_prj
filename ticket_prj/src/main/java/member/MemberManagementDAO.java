package member;

import java.util.List;

public class MemberManagementDAO {
	
	public List<MemberListDTO> selectMemberList(String search, String filter) {
        return null;
    }//selectMemberList

    public MemberDetailDTO selectMemberDetail(String memberId) {
        return null;
    }//selectMemberDetail

    public List<MemberPayDTO> selectPayHistory(String memberId) {
        return null;
    }//selectPayHistory

    public int updateMemberState(String memberId) {
        return 0;
    }//updateMemberState
    
}//class

package kr.admin.member;

import java.util.List;

import kr.admin.common.BoardRangeDTO;

public class MemberManagementService {

    private MemberManagementDAO memberManagementDAO;

    public MemberManagementService() {
        memberManagementDAO = new MemberManagementDAO();
    }//MemberManagementService

    public int getTotalCount(String search, String gradeFilter) {
        return memberManagementDAO.selectTotalCount(search, gradeFilter);
    }//getTotalCount

    public List<MemberListDTO> getMemberList(BoardRangeDTO brDTO, String search, String gradeFilter) {
        return memberManagementDAO.selectMemberList(brDTO, search, gradeFilter);
    }//getMemberList

    public MemberListDTO getMemberBasic(String memberId) {
        return memberManagementDAO.selectMemberBasic(memberId);
    }//getMemberBasic

    public MemberDetailDTO getMemberDetail(String memberId) {
        return memberManagementDAO.selectMemberDetail(memberId);
    }//getMemberDetail

    public List<MemberPayDTO> getPayHistory(String memberId) {
        return memberManagementDAO.selectPayHistory(memberId);
    }//getPayHistory

    public boolean updateMemberState(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            return false;
        }

        return memberManagementDAO.updateMemberState(memberId) > 0;
    }//updateMemberState
    
    public int getVipDiscountRate() {
        return memberManagementDAO.selectVipDiscountRate();
    }//getVipDiscountRate

    public boolean updateVipDiscountRate(int vipDiscountRate) {
        if (vipDiscountRate < 0 || vipDiscountRate > 100) {
            return false;
        }//end if

        return memberManagementDAO.updateVipDiscountRate(vipDiscountRate) > 0;
    }//updateVipDiscountRate
    
    public int refreshDormantMemberState() {
        return memberManagementDAO.updateDormantMemberByLastLogin();
    }//refreshDormantMemberState

}//class
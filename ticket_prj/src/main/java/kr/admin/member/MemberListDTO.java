package kr.admin.member;

public class MemberListDTO {

    private String memberId;
    private String memberName;
    private String memberTel;
    private String memberEmail;
    private String memberGrade;
    private String joinDate;
    private String memberState;

    public MemberListDTO() {
    }//MemberListDTO

    public MemberListDTO(String memberId, String memberName, String memberTel, String memberEmail,
                         String memberGrade, String joinDate, String memberState) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberTel = memberTel;
        this.memberEmail = memberEmail;
        this.memberGrade = memberGrade;
        this.joinDate = joinDate;
        this.memberState = memberState;
    }//MemberListDTO

    public String getMemberId() {
        return memberId;
    }//getMemberId

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }//setMemberId

    // 기존 JSP에서 memberCode로 접근해도 동작하도록 유지
    public String getMemberCode() {
        return memberId;
    }//getMemberCode

    public void setMemberCode(String memberCode) {
        this.memberId = memberCode;
    }//setMemberCode

    public String getMemberName() {
        return memberName;
    }//getMemberName

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }//setMemberName

    public String getMemberTel() {
        return memberTel;
    }//getMemberTel

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }//setMemberTel

    public String getMemberEmail() {
        return memberEmail;
    }//getMemberEmail

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }//setMemberEmail

    public String getMemberGrade() {
        return memberGrade;
    }//getMemberGrade

    public void setMemberGrade(String memberGrade) {
        this.memberGrade = memberGrade;
    }//setMemberGrade

    public String getJoinDate() {
        return joinDate;
    }//getJoinDate

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }//setJoinDate

    public String getMemberState() {
        return memberState;
    }//getMemberState

    public void setMemberState(String memberState) {
        this.memberState = memberState;
    }//setMemberState

}//class
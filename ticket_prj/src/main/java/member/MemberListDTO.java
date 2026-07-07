package member;

public class MemberListDTO {

	private String memberName;
	private String memberTel;
	private String memberEmail;
	private String memberGrade;
	private String joinDate;
	private String memberState;

	public MemberListDTO() {
	}// MemberListDTO

	public MemberListDTO(String memberName, String memberTel, String memberEmail, String memberGrade, String joinDate,
			String memberState) {
		this.memberName = memberName;
		this.memberTel = memberTel;
		this.memberEmail = memberEmail;
		this.memberGrade = memberGrade;
		this.joinDate = joinDate;
		this.memberState = memberState;
	}// MemberListDTO

	public String getMemberName() {
		return memberName;
	}// getMemberName

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}// setMemberName

	public String getMemberTel() {
		return memberTel;
	}// getMemberTel

	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}// setMemberTel

	public String getMemberEmail() {
		return memberEmail;
	}// getMemberEmail

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}// setMemberEmail

	public String getMemberGrade() {
		return memberGrade;
	}// getMemberGrade

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}// setMemberGrade

	public String getJoinDate() {
		return joinDate;
	}// getJoinDate

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}// setJoinDate

	public String getMemberState() {
		return memberState;
	}// getMemberState

	public void setMemberState(String memberState) {
		this.memberState = memberState;
	}// setMemberState

	@Override
	public String toString() {
		return "MemberListDTO [memberName=" + memberName + ", memberTel=" + memberTel + ", memberEmail=" + memberEmail
				+ ", memberGrade=" + memberGrade + ", joinDate=" + joinDate + ", memberState=" + memberState + "]";
	}// toString

}// class
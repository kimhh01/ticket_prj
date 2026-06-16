package kr.co.baseball.member;

import java.util.Date;

public class MemberDTO {

	private String memberCode;       // 회원 아이디
	private String name;           // 이름
	private int gradeCode;         // 등급 코드
	private String email;          // 이메일
	private String password;       // 비밀번호
	private String phone;          // 휴대폰 번호
	private int zipcode;           // 우편번호
	private String address;        // 주소
	private String address2;       // 상세주소
	private String state;          // 회원 상태
	private Date joinDate;         // 가입일
	private char smsReceiveYN;     // SMS 수신 여부
	private char emailReceiveYN;   // 이메일 수신 여부

	public MemberDTO() {
	}

	public MemberDTO(String memberCode, String name, int gradeCode, String email, String password, String phone,
			int zipcode, String address, String address2, String state, Date joinDate, char smsReceiveYN,
			char emailReceiveYN) {
		this.memberCode = memberCode;
		this.name = name;
		this.gradeCode = gradeCode;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.zipcode = zipcode;
		this.address = address;
		this.address2 = address2;
		this.state = state;
		this.joinDate = joinDate;
		this.smsReceiveYN = smsReceiveYN;
		this.emailReceiveYN = emailReceiveYN;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(int gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public char getSmsReceiveYN() {
		return smsReceiveYN;
	}

	public void setSmsReceiveYN(char smsReceiveYN) {
		this.smsReceiveYN = smsReceiveYN;
	}

	public char getEmailReceiveYN() {
		return emailReceiveYN;
	}

	public void setEmailReceiveYN(char emailReceiveYN) {
		this.emailReceiveYN = emailReceiveYN;
	}

	@Override
	public String toString() {
		return "MemberDTO [memberCode=" + memberCode + ", name=" + name + ", gradeCode=" + gradeCode + ", email=" + email
				+ ", password=" + password + ", phone=" + phone + ", zipcode=" + zipcode + ", address=" + address
				+ ", address2=" + address2 + ", state=" + state + ", joinDate=" + joinDate + ", smsReceiveYN="
				+ smsReceiveYN + ", emailReceiveYN=" + emailReceiveYN + "]";
	}

}

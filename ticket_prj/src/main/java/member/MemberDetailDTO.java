package member;

public class MemberDetailDTO {

	private String memberCode;
    private int zipCode;
    private String address;
    private String lastLogin;
    private String marketingAgree;
    private int purchaseCount;
    private int totalPayment;
    
	public MemberDetailDTO() {
	}//MemberDetailDTO
	
	public MemberDetailDTO(String memberCode, int zipCode, String address, String lastLogin, String marketingAgree,
			int purchaseCount, int totalPayment) {
		this.memberCode = memberCode;
		this.zipCode = zipCode;
		this.address = address;
		this.lastLogin = lastLogin;
		this.marketingAgree = marketingAgree;
		this.purchaseCount = purchaseCount;
		this.totalPayment = totalPayment;
	}//MemberDetailDTO
	
	public String getMemberCode() {
		return memberCode;
	}//getMemberCode
	
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}//setMemberCode
	
	public int getZipCode() {
		return zipCode;
	}//getZipCode
	
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}//setZipCode
	
	public String getAddress() {
		return address;
	}//getAddress
	
	public void setAddress(String address) {
		this.address = address;
	}//setAddress
	
	public String getLastLogin() {
		return lastLogin;
	}//getLastLogin
	
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}//setLastLogin
	
	public String getMarketingAgree() {
		return marketingAgree;
	}//getMarketingAgree
	
	public void setMarketingAgree(String marketingAgree) {
		this.marketingAgree = marketingAgree;
	}//setMarketingAgree
	
	public int getPurchaseCount() {
		return purchaseCount;
	}//getPurchaseCount
	
	public void setPurchaseCount(int purchaseCount) {
		this.purchaseCount = purchaseCount;
	}//setPurchaseCount
	
	public int getTotalPayment() {
		return totalPayment;
	}//getTotalPayment
	
	public void setTotalPayment(int totalPayment) {
		this.totalPayment = totalPayment;
	}//setTotalPayment
    
}//class
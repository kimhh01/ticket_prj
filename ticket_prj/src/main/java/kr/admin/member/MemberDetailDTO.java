package kr.admin.member;

public class MemberDetailDTO {

    private String memberId;
    private String zipCode;
    private String address;
    private String lastLogin;
    private String marketingAgree;
    private String snsReceiveYn;
    private String emailReceiveYn;
    private int purchaseCount;
    private int totalPayment;

    public MemberDetailDTO() {
    }//MemberDetailDTO

    public MemberDetailDTO(String memberId, String zipCode, String address, String lastLogin,
                           String marketingAgree, String snsReceiveYn, String emailReceiveYn,
                           int purchaseCount, int totalPayment) {
        this.memberId = memberId;
        this.zipCode = zipCode;
        this.address = address;
        this.lastLogin = lastLogin;
        this.marketingAgree = marketingAgree;
        this.snsReceiveYn = snsReceiveYn;
        this.emailReceiveYn = emailReceiveYn;
        this.purchaseCount = purchaseCount;
        this.totalPayment = totalPayment;
    }//MemberDetailDTO

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

    public String getZipCode() {
        return zipCode;
    }//getZipCode

    public void setZipCode(String zipCode) {
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

    public String getSnsReceiveYn() {
        return snsReceiveYn;
    }//getSnsReceiveYn

    public void setSnsReceiveYn(String snsReceiveYn) {
        this.snsReceiveYn = snsReceiveYn;
    }//setSnsReceiveYn

    public String getEmailReceiveYn() {
        return emailReceiveYn;
    }//getEmailReceiveYn

    public void setEmailReceiveYn(String emailReceiveYn) {
        this.emailReceiveYn = emailReceiveYn;
    }//setEmailReceiveYn

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
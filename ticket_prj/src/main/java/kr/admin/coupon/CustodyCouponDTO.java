package kr.admin.coupon;

public class CustodyCouponDTO {

    private String couponCode;
    private String memberId;
    private String getDate;
    private String couponState;

    /*
     * 조인 조회용
     */
    private String couponName;
    private int couponDiscountRate;

    public CustodyCouponDTO() {
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getGetDate() {
        return getDate;
    }

    public void setGetDate(String getDate) {
        this.getDate = getDate;
    }

    public String getCouponState() {
        return couponState;
    }

    public void setCouponState(String couponState) {
        this.couponState = couponState;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getCouponDiscountRate() {
        return couponDiscountRate;
    }

    public void setCouponDiscountRate(int couponDiscountRate) {
        this.couponDiscountRate = couponDiscountRate;
    }

    @Override
    public String toString() {
        return "CustodyCouponDTO [couponCode=" + couponCode
                + ", memberId=" + memberId
                + ", getDate=" + getDate
                + ", couponState=" + couponState
                + ", couponName=" + couponName
                + ", couponDiscountRate=" + couponDiscountRate + "]";
    }
}
package kr.admin.coupon;

public class CouponManagementDTO {

    private String couponCode;
    private int couponDiscountRate;
    private String couponName;
    private String couponDesc;
    private String startDate;
    private String endDate;
    private String couponType;

    /*
     * CUSTODY_COUPON 기준 통계
     */
    private int issuedCount;
    private int usableCount;
    private int usedCount;

    public CouponManagementDTO() {
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getCouponDiscountRate() {
        return couponDiscountRate;
    }

    public void setCouponDiscountRate(int couponDiscountRate) {
        this.couponDiscountRate = couponDiscountRate;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getIssuedCount() {
        return issuedCount;
    }

    public void setIssuedCount(int issuedCount) {
        this.issuedCount = issuedCount;
    }

    public int getUsableCount() {
        return usableCount;
    }

    public void setUsableCount(int usableCount) {
        this.usableCount = usableCount;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public String getCouponType() {
    	return couponType;
    }
    
    public void setCouponType(String couponType) {
    	this.couponType = couponType;
    }
    
    @Override
    public String toString() {
        return "CouponManagementDTO [couponCode=" + couponCode
                + ", couponDiscountRate=" + couponDiscountRate
                + ", couponName=" + couponName
                + ", couponDesc=" + couponDesc
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + ", couponType=" + couponType
                + ", issuedCount=" + issuedCount
                + ", usableCount=" + usableCount
                + ", usedCount=" + usedCount + "]";
    }

}
package userEvent;

import java.sql.Date;

public class CouponDTO {
	
	private String couponCode;
	private int couponDiscountRate;
	private String couponName;
	private String couponDesc;
	private Date startDate;
	private Date endDate;
	private String couponType;
	
	public CouponDTO(String couponCode, int couponDiscountRate, String couponName, String couponDesc, Date startDate,
			Date endDate, String couponType) {
		super();
		this.couponCode = couponCode;
		this.couponDiscountRate = couponDiscountRate;
		this.couponName = couponName;
		this.couponDesc = couponDesc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.couponType = couponType;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public CouponDTO() {
	}
	

}

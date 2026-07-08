package kr.admin.coupon;

import java.util.List;

public class CouponManagementService {

    private CouponManagementDAO couponManagementDAO;

    public CouponManagementService() {
        couponManagementDAO =
                new CouponManagementDAO();
    }

    /*
     * 쿠폰 목록
     */
    public List<CouponManagementDTO> getCouponList() {
        return couponManagementDAO.selectCouponList();
    }

    /*
     * 쿠폰 상세
     */
    public CouponManagementDTO getCouponDetail(String couponCode) {

        if (isEmpty(couponCode)) {
            return null;
        }

        return couponManagementDAO.selectCouponDetail(
                couponCode.trim());
    }

    /*
     * 쿠폰 등록
     * 성공하면 생성된 couponCode 반환
     * 실패하면 null 반환
     */
    public String registerCoupon(CouponManagementDTO coupon) {

        if (!validateCoupon(coupon)) {
            return null;
        }

        String couponCode =
                coupon.getCouponCode();

        if (!isEmpty(couponCode)) {

            couponCode =
                    couponCode.trim();

            if (couponManagementDAO.existsCouponCode(couponCode)) {
                return null;
            }

            coupon.setCouponCode(couponCode);
        }

        cleanCoupon(coupon);

        return couponManagementDAO.insertCoupon(coupon);
    }

    /*
     * 쿠폰 수정
     */
    public boolean modifyCoupon(CouponManagementDTO coupon) {

        if (!validateCoupon(coupon)) {
            return false;
        }

        if (isEmpty(coupon.getCouponCode())) {
            return false;
        }

        cleanCoupon(coupon);

        return couponManagementDAO.updateCoupon(coupon);
    }

    /*
     * 쿠폰 삭제
     * 사용자에게 지급된 쿠폰이 있으면 DAO에서 false 처리
     */
    public boolean removeCoupon(String couponCode) {

        if (isEmpty(couponCode)) {
            return false;
        }

        return couponManagementDAO.deleteCoupon(
                couponCode.trim());
    }

    /*
     * 사용자 보유 쿠폰 목록
     */
    public List<CustodyCouponDTO> getCustodyCouponList(String couponCode) {

        if (isEmpty(couponCode)) {
            return java.util.Collections.emptyList();
        }

        return couponManagementDAO.selectCustodyCouponList(
                couponCode.trim());
    }

    /*
     * 사용자 보유 쿠폰 상태 변경
     */
    public boolean changeCustodyCouponState(String couponCode,
                                            String memberId,
                                            String couponState) {

        if (isEmpty(couponCode) ||
            isEmpty(memberId) ||
            isEmpty(couponState)) {

            return false;
        }

        couponState =
                couponState.trim();

        if (!"사용가능".equals(couponState) &&
            !"사용".equals(couponState) &&
            !"사용완료".equals(couponState) &&
            !"만료".equals(couponState)) {

            return false;
        }

        return couponManagementDAO.updateCustodyCouponState(
                couponCode.trim(),
                memberId.trim(),
                couponState);
    }

    /*
     * 사용자 보유 쿠폰 삭제
     */
    public boolean removeCustodyCoupon(String couponCode,
                                       String memberId) {

        if (isEmpty(couponCode) ||
            isEmpty(memberId)) {
            return false;
        }

        return couponManagementDAO.deleteCustodyCoupon(
                couponCode.trim(),
                memberId.trim());
    }

    private boolean validateCoupon(CouponManagementDTO coupon) {

        if (coupon == null) {
            return false;
        }

        if (isEmpty(coupon.getCouponName())) {
            return false;
        }

        if (coupon.getCouponDiscountRate() <= 0 ||
            coupon.getCouponDiscountRate() > 100) {
            return false;
        }

        if (isEmpty(coupon.getStartDate())) {
            return false;
        }

        if (isEmpty(coupon.getEndDate())) {
            return false;
        }

        return true;
    }

    private void cleanCoupon(CouponManagementDTO coupon) {

        if (coupon.getCouponCode() != null) {
            coupon.setCouponCode(
                    coupon.getCouponCode().trim());
        }

        if (coupon.getCouponName() != null) {
            coupon.setCouponName(
                    coupon.getCouponName().trim());
        }

        if (coupon.getCouponDesc() == null) {
            coupon.setCouponDesc("");
        } else {
            coupon.setCouponDesc(
                    coupon.getCouponDesc().trim());
        }

        if (coupon.getStartDate() != null) {
            coupon.setStartDate(
                    coupon.getStartDate().trim());
        }

        if (coupon.getEndDate() != null) {
            coupon.setEndDate(
                    coupon.getEndDate().trim());
        }
    }

    private boolean isEmpty(String value) {
        return value == null ||
               value.trim().isEmpty();
    }
}
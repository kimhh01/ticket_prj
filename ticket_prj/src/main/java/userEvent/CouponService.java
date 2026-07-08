package userEvent;

import java.util.List;

public class CouponService {

    private CouponDAO cDAO;

    public CouponService() {
        cDAO = new CouponDAO();
    }

    // 쿠폰 목록 조회
    public List<CouponDTO> searchCoupon() {

        return cDAO.selectCoupon();

    }

    // 쿠폰 다운로드
    public boolean downloadCoupon(String memberId, String couponCode) {

        if(cDAO.hasCoupon(memberId, couponCode)) {
            return false;
        }

        return cDAO.insertCoupon(memberId, couponCode) > 0;
    }

}
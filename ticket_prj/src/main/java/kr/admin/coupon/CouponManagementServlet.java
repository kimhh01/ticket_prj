package kr.admin.coupon;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({
        "/admin/coupon",
        "/admin/coupon/save",
        "/admin/coupon/delete",
        "/admin/coupon/custody/state",
        "/admin/coupon/custody/delete"
})
public class CouponManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CouponManagementService service;

    @Override
    public void init() throws ServletException {
        service =
                new CouponManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String servletPath =
                request.getServletPath();

        if ("/admin/coupon/delete".equals(servletPath)) {
            deleteCoupon(request, response);
            return;
        }

        if ("/admin/coupon/custody/delete".equals(servletPath)) {
            deleteCustodyCoupon(request, response);
            return;
        }

        getCouponManagementPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String servletPath =
                request.getServletPath();

        if ("/admin/coupon/save".equals(servletPath)) {
            saveCoupon(request, response);
            return;
        }

        if ("/admin/coupon/custody/state".equals(servletPath)) {
            changeCustodyCouponState(request, response);
            return;
        }

        response.sendRedirect(
                request.getContextPath()
                + "/admin/coupon?error=unknownAction");
    }

    private void getCouponManagementPage(HttpServletRequest request,
                                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("activeMenu", "coupon");

        List<CouponManagementDTO> couponList =
                service.getCouponList();

        Map<String, List<CustodyCouponDTO>> custodyCouponMap =
                new HashMap<>();

        for (CouponManagementDTO coupon : couponList) {

            List<CustodyCouponDTO> custodyList =
                    service.getCustodyCouponList(
                            coupon.getCouponCode());

            custodyCouponMap.put(
                    coupon.getCouponCode(),
                    custodyList);
        }

        request.setAttribute("couponList", couponList);
        request.setAttribute("custodyCouponMap", custodyCouponMap);

        request.getRequestDispatcher(
                "/manage/coupon/couponManagement.jsp")
               .forward(request, response);
    }

    private void saveCoupon(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException {

        String mode =
                request.getParameter("mode");

        String couponCode =
                request.getParameter("couponCode");

        String couponName =
                request.getParameter("couponName");

        String couponDesc =
                request.getParameter("couponDesc");
        
        String couponType =
                request.getParameter("couponType");

        if (!"자동".equals(couponType) &&
            !"일반".equals(couponType)) {
            couponType = "일반";
        }

        String startDate =
                request.getParameter("startDate");

        String endDate =
                request.getParameter("endDate");

        int couponDiscountRate =
                parseInt(
                        request.getParameter("couponDiscountRate"));

        CouponManagementDTO coupon =
                new CouponManagementDTO();

        coupon.setCouponCode(couponCode);
        coupon.setCouponName(couponName);
        coupon.setCouponDesc(couponDesc);
        coupon.setCouponType(couponType);
        coupon.setCouponDiscountRate(couponDiscountRate);
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);

        boolean resultFlag =
                false;

        if ("insert".equals(mode)) {

            String insertedCouponCode =
                    service.registerCoupon(coupon);

            resultFlag =
                    insertedCouponCode != null;

        } else if ("edit".equals(mode)) {

            resultFlag =
                    service.modifyCoupon(coupon);
        }

        if (resultFlag) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/coupon?success=save");

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/coupon?error=save");
        }
    }

    private void deleteCoupon(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException {

        String couponCode =
                request.getParameter("couponCode");

        boolean resultFlag =
                service.removeCoupon(couponCode);

        if (resultFlag) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/coupon?success=delete");

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/coupon?error=delete");
        }
    }

    private void changeCustodyCouponState(HttpServletRequest request,
                                          HttpServletResponse response)
            throws IOException {

        String couponCode =
                request.getParameter("couponCode");

        String memberId =
                request.getParameter("memberId");

        String couponState =
                request.getParameter("couponState");

        boolean resultFlag =
                service.changeCustodyCouponState(
                        couponCode,
                        memberId,
                        couponState);

        if (resultFlag) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/coupon?success=state");

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/coupon?error=state");
        }
    }

    private void deleteCustodyCoupon(HttpServletRequest request,
                                     HttpServletResponse response)
            throws IOException {

        String couponCode =
                request.getParameter("couponCode");

        String memberId =
                request.getParameter("memberId");

        boolean resultFlag =
                service.removeCustodyCoupon(
                        couponCode,
                        memberId);

        if (resultFlag) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/coupon?success=custodyDelete");

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/coupon?error=custodyDelete");
        }
    }

    private int parseInt(String value) {

        if (value == null ||
            value.trim().isEmpty()) {
            return 0;
        }

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
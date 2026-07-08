package kr.admin.member;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.admin.common.BoardRangeDTO;

@WebServlet({
        "/admin/member",
        "/admin/member/detail",
        "/admin/member/state",
        "/admin/member/discount",
        "/admin/member/discount/update"
})
public class MemberManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MemberManagementService memberManagementService;

    @Override
    public void init() throws ServletException {
        memberManagementService = new MemberManagementService();
    }//init

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String servletPath = request.getServletPath();

        if ("/admin/member".equals(servletPath)) {
            memberList(request, response);
        } else if ("/admin/member/detail".equals(servletPath)) {
            memberDetail(request, response);
        } else if ("/admin/member/discount".equals(servletPath)) {
            memberDiscount(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/member");
        }//end if
    }//doGet

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String servletPath = request.getServletPath();

        if ("/admin/member/state".equals(servletPath)) {
            updateMemberState(request, response);
        } else if ("/admin/member/discount/update".equals(servletPath)) {
            updateDiscount(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/member");
        }//end if
    }//doPost

    private void memberList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String search = request.getParameter("search");
        String gradeFilter = request.getParameter("gradeFilter");
        
        // 마지막 로그인 기준 6개월 이상 지난 회원은 휴면 처리
        int dormantUpdateCount =
                memberManagementService.refreshDormantMemberState();

        int page = parseInt(request.getParameter("page"), 1);
        int pageScale = 10;

        int totalCount = memberManagementService.getTotalCount(search, gradeFilter);
        int totalPage = (int)Math.ceil((double)totalCount / pageScale);

        if (totalPage == 0) {
            totalPage = 1;
        }//end if

        if (page < 1) {
            page = 1;
        }//end if

        if (page > totalPage) {
            page = totalPage;
        }//end if

        int startNum = ((page - 1) * pageScale) + 1;
        int endNum = page * pageScale;

        BoardRangeDTO brDTO = new BoardRangeDTO();
        brDTO.setPage(page);
        brDTO.setPageScale(pageScale);
        brDTO.setStartNum(startNum);
        brDTO.setEndNum(endNum);
        brDTO.setTotalCount(totalCount);
        brDTO.setTotalPage(totalPage);

        List<MemberListDTO> memberList =
                memberManagementService.getMemberList(brDTO, search, gradeFilter);

        request.setAttribute("activeMenu", "member");
        request.setAttribute("memberList", memberList);
        request.setAttribute("brDTO", brDTO);
        request.setAttribute("search", search);
        request.setAttribute("gradeFilter", gradeFilter);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/manage/member/memberList.jsp");
        dispatcher.forward(request, response);
    }//memberList

    private void memberDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String memberId = request.getParameter("memberId");

        if (memberId == null || memberId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/member");
            return;
        }//end if

        MemberListDTO mlDTO = memberManagementService.getMemberBasic(memberId);
        MemberDetailDTO mdDTO = memberManagementService.getMemberDetail(memberId);
        List<MemberPayDTO> payHistory = memberManagementService.getPayHistory(memberId);

        if (mlDTO == null || mdDTO == null) {
            response.sendRedirect(request.getContextPath() + "/admin/member");
            return;
        }//end if

        request.setAttribute("activeMenu", "member");
        request.setAttribute("mlDTO", mlDTO);
        request.setAttribute("mdDTO", mdDTO);
        request.setAttribute("payHistory", payHistory);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/manage/member/memberDetail.jsp");
        dispatcher.forward(request, response);
    }//memberDetail

    private void updateMemberState(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String memberId = request.getParameter("memberId");

        boolean updateFlag = memberManagementService.updateMemberState(memberId);

        if (memberId == null || memberId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/member");
            return;
        }//end if

        if (updateFlag) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/member/detail?memberId=" + memberId + "&result=stateSuccess");
        } else {
            response.sendRedirect(request.getContextPath()
                    + "/admin/member/detail?memberId=" + memberId + "&result=stateFail");
        }//end if
    }//updateMemberState

    private void memberDiscount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int vipDiscountRate =
                memberManagementService.getVipDiscountRate();

        request.setAttribute("activeMenu", "member");
        request.setAttribute("vipDiscountRate", vipDiscountRate);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/manage/member/memberDiscount.jsp");
        dispatcher.forward(request, response);
    }//memberDiscount

    private void updateDiscount(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int vipDiscountRate =
                parseInt(request.getParameter("vipDiscountRate"), -1);

        boolean updateFlag =
                memberManagementService.updateVipDiscountRate(vipDiscountRate);

        if (updateFlag) {
            response.sendRedirect(request.getContextPath()
                    + "/admin/member/discount?result=discountSuccess");
        } else {
            response.sendRedirect(request.getContextPath()
                    + "/admin/member/discount?result=discountFail");
        }//end if
    }//updateDiscount

    private int parseInt(String value, int defaultValue) {
        int result = defaultValue;

        try {
            if (value != null && !value.trim().isEmpty()) {
                result = Integer.parseInt(value);
            }//end if
        } catch (NumberFormatException e) {
            result = defaultValue;
        }//end catch

        return result;
    }//parseInt

}//class
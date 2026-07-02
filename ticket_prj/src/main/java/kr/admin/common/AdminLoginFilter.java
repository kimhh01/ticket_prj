package kr.admin.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
        "/event",
        "/event/*",

        "/team",
        "/team/*",

        "/ticket",
        "/ticket/*",

        "/main",
        "/main/*",
        
        "/stadium",
        "/stadium/*",
        
        "/member",
        "/member/*",
        
        "/inquiry",
        "/inquiry/*"
})
public class AdminLoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("===== AdminLoginFilter init 실행됨 =====");
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        System.out.println("===== AdminLoginFilter 실행됨 =====");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.substring(contextPath.length());

        System.out.println("요청 uri = " + uri);
        System.out.println("contextPath = " + contextPath);
        System.out.println("path = " + path);

        HttpSession session = req.getSession(false);

        Object admin = null;

        if (session != null) {
            admin = session.getAttribute("admin");
        }

        if (admin == null) {
            resp.sendRedirect(contextPath + "/manage/adminLogin/adminLogin.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    /*
     * 현재 로그인 세션 제거
     */
    session.invalidate();

    /*
     * 로그인 페이지로 이동
     */
    response.sendRedirect(
            request.getContextPath()
            + "/manage/adminLogin/adminLogin.jsp");
%>

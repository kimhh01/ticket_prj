<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    Object admin =
            session.getAttribute("admin");

    if (admin == null) {

        response.setStatus(401);

        out.print("{\"result\":\"logout\"}");

        return;
    }

    /*
     * 세션 시간 다시 30분으로 연장
     */
    session.setMaxInactiveInterval(600);

    out.print("{\"result\":\"success\", \"seconds\":600}");
%>
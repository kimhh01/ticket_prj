<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /*
     * 프로젝트 루트(/ticket_prj/)로 접속했을 때 실행되는 welcome file.
     * 메인 화면 데이터는 MainServlet이 준비하므로 JSP를 직접 열지 않고 /main으로 이동시킨다.
     */
    response.sendRedirect(request.getContextPath() + "/main");
%>	

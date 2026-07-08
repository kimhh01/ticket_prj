<% int eventId = Integer.parseInt(request.getParameter("eventId"));
if(eventId == 1){ response.sendRedirect
	("eventDetail_1.jsp?eventId=" + eventId); return; }
if(eventId == 2){ response.sendRedirect
	("eventDetail_2.jsp?eventId=" + eventId); return; }
%>


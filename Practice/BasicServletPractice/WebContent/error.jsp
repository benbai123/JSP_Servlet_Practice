<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Error page</title>
	</head>
	<body>
		<% if (session.getAttribute("ERR_MSG") == null)
				out.print(exception.getMessage());
			else {
				out.print(session.getAttribute("ERR_MSG"));
				session.setAttribute("ERR_MSG", null);
			}
		%>
	</body>
</html>
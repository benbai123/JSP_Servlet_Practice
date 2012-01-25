<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<html>
	<head>
		<title>Error page</title>
	</head>
	<body>
		<% out.print(exception.getMessage());
		%>
	</body>
</html>
<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>Login page</title>
	</head>
	<body>
		<form action="login.go" method="post">
			<span>Type a name then press login to enter chat room</span>
			<input id="userId" type="text" value="Your ID" name="uid" />
			<input type="submit" value="login"/>
		</form>
	</body>
</html>
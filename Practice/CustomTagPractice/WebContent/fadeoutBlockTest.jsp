<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<!-- use the custom taglib with prefix ct -->
<%@taglib prefix="ct" uri="http://test.tag.custom/jsp/impl/taglib"%>
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>EL Math Practice</title>
		<style>
			.msg_block {
				position: absolute;
				left: 300px;
				top: 200px;
				width: 300px;
				height: 150px;
			}
		</style>
	</head>
	<body>
		<ct:fadeoutBlock styleClass="msg_block"
			duration="3000" step="0.05">
			<div style="margin: 20px; border: 1px solid #8463AE;">
				test message, click to fade-out
			</div>
		</ct:fadeoutBlock>
	</body>
</html>
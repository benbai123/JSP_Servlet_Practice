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
			.err_msg_class {
				color: #AE4386;
				font-size: 22px;
			}
		</style>
		<script type="text/javascript">
			function onErrClick (msg) {
				// hide the message after clicked
				msg.style.display = 'none';
			}
		</script>
	</head>
	<body>
		<!-- Use default style and action -->
		<ct:errMsg msg="Error" msgDescription="This is test error message" />
		<!-- Use custom style and action -->
		<ct:errMsg msg="Error" styleClass="err_msg_class" onClick="onErrClick(this);" />
	</body>
</html>
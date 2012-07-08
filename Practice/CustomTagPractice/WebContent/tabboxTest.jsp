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
	</head>
	<body>
		<!-- the tabbox -->
		<ct:tabbox width="500" height="300">
			<!-- child tabpanels -->
			<ct:tabpanel header="Tab 1">
				this is the first panel of the tabbox
				<div style="height: 150px; width: 150px; background-color: red;"></div>
			</ct:tabpanel>
			<ct:tabpanel header="Tab 2">
				<div style="height: 200px; width: 200px; background-color: green;">
					second panel
				</div>
			</ct:tabpanel>
			<ct:tabpanel header="Tab 3">
				the third panel
			</ct:tabpanel>
		</ct:tabbox>
	</body>
</html>
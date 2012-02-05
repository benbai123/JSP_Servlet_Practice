<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- This is the uri for JSTL 1.2 -->
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- EL is required -->
<%@ page isELIgnored ="false" %>
<!-- This page practice the setBundle message tag -->
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>practice three: I18N (Internationalization)</title>
		<script type="text/javascript" src="js/practice_three.js"></script>
	</head>
	<body>
		<!-- load bundle if it is null (without locale setting) -->
		<c:if test="${testBUndle == null}">
			<fmt:setBundle basename="test.jstl.i18n.properties.i18n_test"
				var="testBundle" scope="session"/>
		</c:if>
		<!-- The language select menu, call javascript to change locale when changed -->
		<select id="langVal" onChange="i18n.changeLocale(this.value, '${currentLocale}');" selectedIndex="1">
			<!-- The first option display the current language -->
			<option value="${currentLocale}">${currentLanguage}</option>
			<option value="en_US">English</option>
			<option value="ja_JP">日本語</option>
			<option value="fr_FR">Français</option>
			<option value="de_DE">Deutsch</option>
		</select>

		<!-- out put the message with respect to the key 'i18n.coffee' -->
		<fmt:message key="i18n.coffee"
				bundle="${testBundle}"/>
	</body>
</html>
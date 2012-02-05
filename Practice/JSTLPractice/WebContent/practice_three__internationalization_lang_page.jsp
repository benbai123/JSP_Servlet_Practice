<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- This is the uri for JSTL 1.2 -->
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- EL is required -->
<%@ page isELIgnored ="false" %>
<!-- This page practice setLocale and setBundle tag -->
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>practice three: I18N (Internationalization)</title>
	</head>
	<body>
		<!-- Set the locale by the value of parameter 'lang' -->
		<fmt:setLocale value="${param['lang']}" scope="session" />
		<!-- Should load bundle again with the new locale -->
		<fmt:setBundle basename="test.jstl.i18n.properties.i18n_test"
			var="testBundle" scope="session"/>
		
		<!-- set the value of currentLocale -->
		<c:set var="currentLocale" value="${param['lang']}" scope="session" />

		<!-- Set the value of currentLanguage -->
		<c:choose>
			<c:when test="${param['lang'] eq 'ja_JP'}">
				<c:set var="currentLanguage" value="日本語" scope="session" />
			</c:when>
			<c:when test="${param['lang'] eq 'fr_FR'}">
				<c:set var="currentLanguage" value="Français" scope="session" />
			</c:when>
			<c:when test="${param['lang'] eq 'de_DE'}">
				<c:set var="currentLanguage" value="Deutsch" scope="session" />
			</c:when>
			<c:otherwise>
				<c:set var="currentLanguage" value="English" scope="session" />
			</c:otherwise>
		</c:choose>
	</body>
</html>
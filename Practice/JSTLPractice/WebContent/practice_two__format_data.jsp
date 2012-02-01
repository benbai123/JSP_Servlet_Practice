<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- This is the uri for JSTL 1.2 -->
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- EL is required -->
<%@ page isELIgnored ="false" %>
<!-- This page practice the Number and Date Formatting Tags such like
	setLocale, formatNumber, formatDate, parseDate,
	parseNumber, setTimeZone, timeZone -->
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>practice two: format data</title>
	</head>
	<body>
		<div style="margin: 50px; float: left;">
			<span style="color: #119900;">Locale: en_US, timeZone: GMT+3</span><br />
			<fmt:setLocale value="${'en_US'}" scope="session" />
			<span style="color: #111199;">fmt:formatNumber</span><br />
			<!-- number -->
			<fmt:formatNumber type="number" value="${1234.5678}" /><br />
			<!-- using pattern format number, # denotes 0-n digital, 0 denotes one digital -->
			<fmt:formatNumber type="number" value="${1234.5678}" pattern="#0.00 pound" /><br />
			<!-- currency, locale setting will change currency format -->
			<fmt:formatNumber type="currency" value="${1234.5678}" /><br />
			
			<!-- percentage -->
			<fmt:formatNumber type="percent" value="${1234.5678}" /><br />
			<!-- percentage with maximum integer/fraction digits -->
			<fmt:formatNumber type="percent" value="${1234.5678}"
				maxIntegerDigits="10" maxFractionDigits="10" /><br />
			<!-- percentage with max/min integer/fraction digits -->
			<fmt:formatNumber type="percent" value="${1234.5678}"
				maxIntegerDigits="10" maxFractionDigits="10"
				minIntegerDigits="8" minFractionDigits="4" /><br /><br />

			<span style="color: #111199;">fmt:parseNumber</span><br />
			<!-- parse string to number -->
			<fmt:parseNumber var="parsedNumber" type="number" value="${'1,234.56'}" />
			<c:out value="${parsedNumber + 3}" /><br /><br />

			<span style="color: #111199;">fmt:timeZone, fmt:formatDate</span><br />
			<!-- apply specific timezone to body content -->
			<fmt:timeZone value="${'GMT+3'}">
				<!-- date, init type=date, using java.util.Date -->
				<fmt:formatDate value="${theDate}" /><br />
				<!-- date, type=both for show all,
								date for ymd,
								time for hms -->
				<fmt:formatDate type="both" value="${theDate}" /><br />
				<fmt:formatDate type="date" value="${theDate}" /><br />
				<fmt:formatDate type="time" value="${theDate}" /><br />
				<!-- date with pattern -->
				<fmt:formatDate pattern="yyyy-MM-dd" value="${theDate}" /><br />
				<!-- date with pattern -->
				<fmt:formatDate pattern="HH:mm:ss" value="${theDate}" /><br /><br />
				<span style="color: #111199;">fmt:parseDate</span><br />
				<!-- parse string to date -->
				<fmt:parseDate var="parsedDate" value="${'12-12-2011'}" 
					pattern="dd-MM-yyyy" />
				<c:out value="${parsedDate}" /><br /><br />
			</fmt:timeZone>
			<span style="color: #119900;">timeZone: GMT+4</span><br />
			<span style="color: #111199;">fmt:setTimeZone</span><br />
			<!-- change time zone of page -->
			<fmt:setTimeZone value="GMT+12" />
			<fmt:formatDate type="both" value="${theDate}" /><br />
		</div>
		<div style="margin: 50px; float: left;">
			<span style="color: #119900;">Locale: fr_FR, timeZone: GMT+5</span><br />
			<fmt:setLocale value="${'fr_FR'}" scope="session" />
			<span style="color: #111199;">fmt:formatNumber</span><br />
			<!-- number -->
			<fmt:formatNumber type="number" value="${1234.5678}" /><br />
			<!-- using pattern format number, # denotes 0-n digital, 0 denotes one digital -->
			<fmt:formatNumber type="number" value="${1234.5678}" pattern="#0.00 pound" /><br />
			<!-- currency, locale setting will change currency format -->
			<fmt:formatNumber type="currency" value="${1234.5678}" /><br />
			
			<!-- percentage -->
			<fmt:formatNumber type="percent" value="${1234.5678}" /><br />
			<!-- percentage with maximum integer/fraction digits -->
			<fmt:formatNumber type="percent" value="${1234.5678}"
				maxIntegerDigits="10" maxFractionDigits="10" /><br />
			<!-- percentage with max/min integer/fraction digits -->
			<fmt:formatNumber type="percent" value="${1234.5678}"
				maxIntegerDigits="10" maxFractionDigits="10"
				minIntegerDigits="8" minFractionDigits="4" /><br /><br />

			<span style="color: #111199;">fmt:parseNumber</span><br />
			<fmt:parseNumber var="parsedNumber" type="number" value="${'1,234.56'}" />
			<c:out value="${parsedNumber + 3}" /><br /><br />

			<span style="color: #111199;">fmt:timeZone, fmt:formatDate</span><br />
			<!-- apply specific timezone to body content -->
			<fmt:timeZone value="${'GMT+5'}">
				<!-- date, init type=date, using java.util.Date -->
				<fmt:formatDate value="${theDate}" /><br />
				<!-- date, type=both for show all,
								date for ymd,
								time for hms -->
				<fmt:formatDate type="both" value="${theDate}" /><br />
				<fmt:formatDate type="date" value="${theDate}" /><br />
				<fmt:formatDate type="time" value="${theDate}" /><br />
				<!-- date with pattern -->
				<fmt:formatDate pattern="yyyy-MM-dd" value="${theDate}" /><br />
				<!-- date with pattern -->
				<fmt:formatDate pattern="HH:mm:ss" value="${theDate}" /><br /><br />
				<span style="color: #111199;">fmt:parseDate</span><br />
				<fmt:parseDate var="parsedDate" value="${'12-12-2011'}" 
					pattern="dd-MM-yyyy" />
				<c:out value="${parsedDate}" /><br /><br />
			</fmt:timeZone>
			<span style="color: #119900;">timeZone: GMT+4</span><br />
			<span style="color: #111199;">fmt:setTimeZone</span><br />
			<!-- change time zone of page -->
			<fmt:setTimeZone value="GMT+4" />
			<fmt:formatDate type="both" value="${theDate}" /><br />
		</div>
	</body>
</html>
<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- This is the uri for JSTL 1.2 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- EL is required -->
<%@ page isELIgnored ="false" %>
<!-- This page practice the core tag such like
	&lt;c:out>, &lt;c:set>, &lt;c:remove> &lt;c:if>,
	&lt;c:choose>, &lt;c:forEach> -->
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>practice one</title>
	</head>
	<body>
		<div style="margin: 50px;">
			<div style="float: left; margin: 10px;">
				<span style="color: #111199;">Practice out.</span><br />
				<!-- the UID is 123 from Request scope
					(the lowest scope contains the attribute) -->
				<c:out value="${UID}" /><br />
				<!-- the UID is ABC from Session scope -->
				<c:out value="${sessionScope.UID}" /><br />
				<!-- the Test is ZZZ from Session scope
					(the lowest scope contains the attribute) -->
				<c:out value="${Test}" /><br />
				<br />
				<br />
	
				<span style="color: #111199;">Practice set.</span><br />
				<!-- set a variable in page scope -->
				<c:set var="check" value="123" />
				<!-- set a variable in session scope -->
				<c:set var="check" value="session scope check" scope="session" />
				<c:out value="${check}" /><br />
				<c:out value="${sessionScope.check}" /><br />
				<br />
				<br />

				<span style="color: #111199;">Practice remove.</span><br />
				<!-- remove the 'check' variable from session scope -->
				<c:remove var="check" scope="session" />
				<c:out value="${check}" /><br />
				<c:out value="${sessionScope.check}" /><br />
				<br />
				<br />
	
				<span style="color: #111199;">Practice if.</span><br />
				<!-- the UID in Request scope is 123 -->
				Test one <br />
				<c:if test="${UID eq check}" var="testResult" scope="request">
					UID is 123 !<br />
				</c:if>
				The test result is <c:out value="${testResult}" /><br />
				<!-- change the check variable -->
				<c:set var="check" value="ABC" />
				<!-- the UID in Request scope is not ABC -->
				Test two <br />
				<c:if test="${UID eq check}" var="testResult" />
				<!-- remove the testResult from request scope -->
				<c:remove var="testResult" scope="request" />
				The test result is <c:out value="${testResult}" /><br />
				<!-- the UID in Session scope is not 123 -->
				Test three <br />
				<c:if test="${sessionScope.UID eq '123'}">
					UID in session scope is 123 !<br />
				</c:if>
				<!-- the UID in Session scope is ABC -->
				Test four <br />
				<c:if test="${sessionScope.UID eq check}" var="testResult" scope="request">
					UID in session scope is ABC !<br />
				</c:if>
				The test result is <c:out value="${testResult}" /><br />
			</div>
			<div style="float: left;">
				<span style="color: #111199;">Practice choose.</span><br />
				<!-- use choose to display product conut with different style -->
				<c:choose>
					<c:when test="${sessionScope.UID eq '123'}">
						<span style="color: red;">
							UID in session scope is 123 !<br />
						</span>
					</c:when>
					<c:when test="${sessionScope.UID eq '456'}">
						<span style="color: green;">
							UID in session scope is 456 !<br />
						</span>
					</c:when>
					 <c:otherwise>
					 	<span style="color: brown;">
					 		UID in session scope is <c:out value="${sessionScope.UID}" /><br />
						</span>
					 </c:otherwise>
				</c:choose>
				<br />
				<br />
				
				<span style="color: #111199;">Practice forEach.</span><br />
				<!-- use forEach to convert a list to table -->
				Total product: <c:out value="${data.productCount}" /> <br />
				<table border="1">
					<tr>
						<th scope="col" align="center">
							ID
						</th>
						<th scope="col" align="center">
							Name
						</th>
						<th scope="col" align="center">
							Value
						</th>
					</tr>
					<c:forEach items="${data.productList}" var="product" begin="0" end="9" step="2">
						<tr>
							<td><c:out value="${product.id}" /></td>
							<td><c:out value="${product.name}" /></td>
							<td><c:out value="${product.value}" /></td>
						<tr>
					</c:forEach>
				</table>
				<br />
				<br />
				<!-- use forEach to loop and sum 1 to 10 -->
				<c:set var="sum" value="${0}" />
				<c:forEach begin="1" end="10" step="1" varStatus="status">
					<c:set var="sum" value="${sum+status.count}" />
				</c:forEach>
				<c:out value="${sum}" />
			</div>
		</div>
	</body>
</html>
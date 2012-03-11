<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>Normal - without JAWR</title>
		<jwr:style src="/bundles/cssbundle.cssbundle" />
		<jwr:script src="/bundles/jsbundle_one.jsbundle"/>
		<jwr:script src="/bundles/jsbundle_two.jsbundle"/>
		<script type="text/javascript">
			onload = function () {
				funcA();
				funcB();
				funcC();
				funcD();
				funcE();
			}
		</script>
	</head>
	<body>
		<div class="class_a">
			js file <span id="funcA"></span> loaded
		</div>
		<div class="class_b">
			js file <span id="funcB"></span> loaded
		</div>
		<div class="class_c">
			js file <span id="funcC"></span> loaded
		</div>
		<div class="class_d">
			js file <span id="funcD"></span> loaded
		</div>
		<div class="class_e">
			js file <span id="funcE"></span> loaded
		</div>
	</body>
</html>
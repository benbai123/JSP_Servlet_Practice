<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>Normal - without JAWR</title>
		<link href="css/aaa.css" rel="stylesheet" type="text/css">
		<link href="css/bbb.css" rel="stylesheet" type="text/css">
		<link href="css/ccc.css" rel="stylesheet" type="text/css">
		<link href="css/ddd.css" rel="stylesheet" type="text/css">
		<link href="css/eee.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/bundleOne/aaa.js"></script>
		<script type="text/javascript" src="js/bundleOne/bbb.js"></script>
		<script type="text/javascript" src="js/bundleOne/ccc.js"></script>
		<script type="text/javascript" src="js/bundleTwo/ddd.js"></script>
		<script type="text/javascript" src="js/bundleTwo/eee.js"></script>
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
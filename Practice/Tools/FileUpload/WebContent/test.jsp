<!--
	file upload test

	Libraries: 
		commons-fileupload-1.2.2.jar
		commons-io-2.4.jar

	References:
		Commons FileUpload
		http://commons.apache.org/proper/commons-fileupload/

		Using FileUpload
		http://commons.apache.org/proper/commons-fileupload/using.html
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>File Upload Practice</title>
	</head>
	<body>
		<form method="POST" enctype="multipart/form-data" action="upload.go">
			File to upload: <input type="file" name="upfile"><br/>
			Notes about the file: <input type="text" name="note"><br/>
			<br/>
			<input type="submit" value="Press"> to upload the file!
		</form>
	</body>
</html>
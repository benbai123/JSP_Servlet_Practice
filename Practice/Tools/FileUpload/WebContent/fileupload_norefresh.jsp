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

		The answer at
		http://stackoverflow.com/questions/5700172/jsp-ajax-file-uploading
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>File Upload Practice</title>
		<style>
			.frame {
				position: absolute;
				top: -9999px;
				left: -9999px;
			}
		</style>
		<script type="text/javascript">
			function upload () {
				var uploadform = document.getElementById('uploadform');
				uploadform.target = 'target-frame';
				uploadform.submit();
			}
		</script>
	</head>
	<body>
		<form id="uploadform" method="POST" enctype="multipart/form-data" action="upload.go">
			File to upload: <input type="file" name="upfile"><br/>
			Notes about the file: <input type="text" name="note"><br/>
			<br/>
			<input type="button" onclick="upload()" value="Press"> to upload the file!
		</form>
		<iframe id="target-frame" name="target-frame" class="frame"></iframe>
	</body>
</html>
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
		<!-- use jquery for ajax -->
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<style>
			.frame {
				position: absolute;
				top: -9999px;
				left: -9999px;
			}
			.progress-bar {
				height: 20px;
				width: 100px;
				display: none;
				border: 2px solid green;
			}
			.progress {
				background-color: blue;
				height: 100%;
				width: 0px;
			}
		</style>
		<script type="text/javascript">
			var progress;
			function upload () {
				// avoid concurrent processing
				if (progress) return;
				var uploadform = document.getElementById('uploadform'),
					time = new Date().getTime();
				uploadform.action = 'uploadprogress.go?time=' + time;
				uploadform.target = 'target-frame';
				uploadform.submit();
				startProgressbar(time);
			}
			function startProgressbar (startTime) {
				// display progress bar
				$('.progress-bar').css('display', 'block');
				// start timer
				progress = setInterval(function () {
					// ask progress
					$.ajax({
						type: "GET",
						url: "uploadprogress.go",
						data: {time: startTime},
						success: function (data, textStatus,jqXHR ) {
							// get progress from response data
							var d = eval('(' + data + ')'),
								uploadprogress = parseInt(d.progress[startTime]);
							// change progress width
							$('.progress').css('width', uploadprogress+'px');
							if (uploadprogress == 100) { // upload finished
								// stop timer
								clearInterval(progress);
								setTimeout(function () {
									// hide progress bar
									$('.progress-bar').css('display', '');
									$('.progress').css('width', '');
									// clear timer variable
									progress = null;
								}, 1000);
							}
						}
					})
				}, 1000);
			}
		</script>
	</head>
	<body>
		<form id="uploadform" method="POST" enctype="multipart/form-data">
			File to upload: <input type="file" name="upfile"><br/>
			Notes about the file: <input type="text" name="note"><br/>
			<br/>
			<input type="button" onclick="upload()" value="Press"> to upload the file!
		</form>
		<iframe id="target-frame" name="target-frame" class="frame"></iframe>
		<!-- progress bar -->
		<div class="progress-bar">
			<div class="progress"></div>
		</div>
	</body>
</html>
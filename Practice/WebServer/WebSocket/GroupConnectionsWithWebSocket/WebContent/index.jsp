<%@ page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- EL is required -->
<%@ page isELIgnored ="false" %>
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>practice one</title>
		<script type="text/javascript">
			var TestWebSocket = {
				socket: null,
				group: null,
				connect: (function() {
					// store the specified group
					TestWebSocket.group = '${GROUP}';
					// create WebSocket with the specified group
					var loc = window.location,
						host = ('ws://' + loc.host + loc.pathname).replace('index.jsp', '') + TestWebSocket.group + '.wsreq';
					if ('WebSocket' in window) {
						TestWebSocket.socket = new WebSocket(host);
					} else if ('MozWebSocket' in window) {
						TestWebSocket.socket = new MozWebSocket(host);
					} else {
						alert('Error: WebSocket is not supported by this browser.');
						return;
					}
					// process message from server
					TestWebSocket.socket.onmessage = function (message) {
						document.getElementById('content').innerHTML = message.data;
					};
				}),
				// send specified group to server
				trigger: (function() {
					TestWebSocket.socket.send(TestWebSocket.group);
				})
			};

			TestWebSocket.connect();
		</script>
	</head>
	<body>
		Group: ${GROUP}
		<div id="content"></div>
		<button id="btn" onclick="TestWebSocket.trigger();">trigger</button>
	</body>
</html>
// init
window.chat = {};

// post to send message to chat.do
chat.sendMsg = function (msg) {
	var request;

	msg = msg.replace(/&/g, '&amp;') // encode to prevent XSS
				.replace(/</g, '&lt;')
				.replace(/>/g, '&gt;')
				.replace(/"/g, '&quot;')
				.replace(/\n/g, '<br />'); // replace textarea newline to line break tag
	alert(msg);
	if (request = this.getXmlHttpRequest()) {
		request.open('POST', 'chat.do?action=send&msg='+msg+'&time='+new Date().getTime());
		request.send(null);
		chat.updateContent('<div>You said: '+msg+'</div>');
	}
};

// post 'get' action to chat.do to require new message if any
chat.startListen = function () {
	if (!chat.listen)
		chat.listen = setInterval (function () {
			var request;
			if (request = chat.getXmlHttpRequest()) {
				request.open('POST', 'chat.do?action=get&time='+new Date().getTime());
				request.send(null);
				request.onreadystatechange = function() {
					if(request.readyState === 4) {
						if(request.status === 200) {
							var json = request.responseText;
							// has new message
							if (json && json.length) {
								// parse to array
								var obj = eval('(' + json + ')');
								var msg = '';
								for (var i = 0; i < obj.length; i++) {
									msg += '<div>'+obj[i]+'</div>';
								}
								chat.updateContent(msg);
							}
						} else if(request.status === 400 || request.status === 500)
							document.location.href = 'index.jsp';
					}
				};
			}
		}, 3000);
};

chat.updateContent = function (msg) {
	var content = document.getElementById('content'),
		atBottom = (content.scrollTop + content.offsetHeight) >= content.scrollHeight;
	content.innerHTML += msg;
	// only scroll to bottom if it is at bottom before msg added
	if (atBottom)
		content.scrollTop = content.scrollHeight;
};
chat.dokeyup = function (event) {
	if (!event) // IE will not pass event
		event = window.event;
	if (event.keyCode == 13 && !event.shiftKey) { // ENTER pressed
		var target = (event.currentTarget) ? event.currentTarget : event.srcElement,
			value = target.value;
		// make sure not only space char
		if (value && value.replace(/^\s\s*/, '').replace(/\s\s*$/, '').length > 0) {
			this.sendMsg(target.value);
			target.value = '';
		}
	}
};
// get the XmlHttpRequest object
chat.getXmlHttpRequest = function () {
	if (window.XMLHttpRequest
		&& (window.location.protocol !== 'file:' 
		|| !window.ActiveXObject))
		return new XMLHttpRequest();
	try {
		return new ActiveXObject('Microsoft.XMLHTTP');
	} catch(e) {
		throw new Error('XMLHttpRequest not supported');
	}
};
onload = function () {
	chat.startListen();
};
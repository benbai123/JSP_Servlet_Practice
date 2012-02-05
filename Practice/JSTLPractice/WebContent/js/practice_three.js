if (!window.i18n)
	i18n = {};
i18n.changeLocale = function (locale, current) {
	var request;
	// do nothing if not changed
	if (locale == current)
		return;

	// get a request
	if (request = this.getXmlHttpRequest()) {
		// do post and bring locale value as param['lang']
		request.open('POST', 'practice_three__internationalization_lang_page.jsp?lang='+locale);
		request.send(null);
	}
	// refresh document after request success
	request.onreadystatechange = function() {
		if(request.readyState === 4 && request.status === 200)
			document.location.href = document.location.href;
	};
};

//get the XmlHttpRequest object
i18n.getXmlHttpRequest = function () {
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
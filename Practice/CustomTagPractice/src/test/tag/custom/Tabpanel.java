package test.tag.custom;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * Simple tabpanel with poor look and feel
 */
public class Tabpanel extends BodyTagSupport {
	private String _header = new String("tab header");

	public void setHeader (String header) {
		_header = header;
	}

	public int doStartTag() throws JspException {
		// denotes evaluate body but do not output it, store it in buffer
		return EVAL_BODY_BUFFERED;
	}
	
	public int doEndTag() throws JspException {
		// find the parent tabbox
		Tabbox parent = (Tabbox)findAncestorWithClass(this, Tabbox.class);
		// get the buffered body content
		String body = getBodyContent().getString();
		// parent should not be null
		if(parent == null)
			throw new JspException("Tabpanel.doStartTag(): " + "No Tabbox ancestor");

		// fix empty body
		if (body == null || body.isEmpty())
			body = "&nbsp;"; // at least a space char
		// fix empty header
		if (_header == null || _header.isEmpty())
			_header = "&nbsp;"; // at least a space char
		// add header content to parent tabbox
		parent.addHeaderContent(_header);
		// add body content to parent tabbox
		parent.addBodyContent(body);
		parent.increaseCnt();
		release();

		return EVAL_PAGE;
		
	}
	
	public void release() {
		// have to reset all field values since container may reuse this instance
		_header = null;
	}
	
}
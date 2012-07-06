package test.tag.custom;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;


/**

 */
public class Tabpanel extends TagSupport {
	private String _header = new String("tab header");				
	private String _body = new String("tab content");				

	/**

	 */
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
		
	}
	
	/**

	 */
	public int doEndTag() throws JspException {
		Tabbox parent = (Tabbox)findAncestorWithClass(this, Tabbox.class);

		if(parent == null)
			throw new JspException("Tabpanel.doStartTag(): " + "No Tabbox ancestor");
		parent.addHeaderContent(_header);
		parent.addBodyContent(_body);
		parent.increaseCnt();
		release();

		return EVAL_PAGE;
		
	}
	
	/**

	 */
	public void release() {

		
	}
	
}
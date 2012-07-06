package test.tag.custom;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 

 *
 */
public class Tabbox extends TagSupport {
	private int _panelCnt = 0;
	private int _width = 100;
	private int _height = 100;
	private String _id;
	private int _defaultDisplayNumber = 1;
	private String _styleClass;
	private StringBuilder _headerContent = new StringBuilder("");
	private StringBuilder _bodyContent = new StringBuilder("");

	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.print("<div");
			if (_id != null && !_id.isEmpty()) {
				out.print(" id=\"" + _id + "\"");
			}
			out.print(" style=\"overflow: auto; width: "+_width+"px; height: "+_height+"px; border: 1px solid #CCCCCC;\"");
			if (_styleClass != null && !_styleClass.isEmpty()) {
				out.print(" class=\""+_styleClass+"\"");
			}
			out.print(">");
		} catch (Exception e) {
			throw new JspException("Error: IOException while writing to client");
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.print("<div>"+getHeaderContent()+"</div>");
			out.print("<div>"+getBodyContent()+"</div></div>");
		} catch (Exception ex) {
			throw new JspException(ex.getMessage());
		}
		release();
		
		return EVAL_PAGE;
		
	}
	
	public void release() {
		
	}
	public void addHeaderContent (String content) {
		_headerContent.append(content);
	}
	public void addBodyContent (String content) {
		_bodyContent.append(content);
	}
	public void increaseCnt () {
		_panelCnt++;
	}
	private String getHeaderContent () {
		return _headerContent.toString();
	}
	private String getBodyContent () {
		return _bodyContent.toString();
	}
}
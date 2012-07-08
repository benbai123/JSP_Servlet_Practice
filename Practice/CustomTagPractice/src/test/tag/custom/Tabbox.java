package test.tag.custom;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Simple tabbox with poor look and feel 
 */
public class Tabbox extends BodyTagSupport {
	private int _panelCnt = 0;
	private int _width = 100;
	private int _height = 100;
	private StringBuilder _headerContent = new StringBuilder("");
	private StringBuilder _bodyContent = new StringBuilder("");

	public void setWidth (int width) {
		_width = width;
	}
	public void setHeight (int height) {
		_height = height;
	}
	public int doStartTag() throws JspException {
		try {
			// output the out most area
			JspWriter out = pageContext.getOut();
			out.print("<div");
			out.print(" style=\"overflow: auto; width: "+_width+"px; height: "+_height+"px; border: 1px solid #CCCCCC;\"");
			out.print(">");
		} catch (Exception e) {
			throw new JspException("Error: IOException while writing to client");
		}

		// evaluate body content and output it directly
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			// output the header/body of tabpanels
			out.print("<div>"+_headerContent.toString()+"</div>");
			out.print("<div style=\"margin: 10px; border: 1px solid #3648AE;\">"+_bodyContent.toString()+"</div></div>");
		} catch (Exception ex) {
			throw new JspException(ex.getMessage());
		}
		release();
		// continue evaluate page
		return EVAL_PAGE;
		
	}

	public void release() {
		// have to reset all field values since container may reuse this instance
		_panelCnt = 0;
		_width = 100;
		_height = 100;
		_headerContent.setLength(0);
		_bodyContent.setLength(0);
	}

	public void addHeaderContent (String content) {
		// called by Tabpanel, add header content
		String style = _panelCnt == 0? "background-color: gray;" : "background-color: transparent;";
		style += " margin-right: 5px; border: 1px solid #CCCCCC; border-bottom: 0px; cursor: pointer;";
		_headerContent.append("<span onclick=\""+showMatchedPanel()+"\" style=\""+style+"\">")
			.append(content)
			.append("</span>");
	}
	public void addBodyContent (String content) {
		// called by Tabpanel, add body contents
		String style = _panelCnt == 0? "" : "display: none;";
		_bodyContent.append("<div style=\""+style+"\">")
			.append(content)
			.append("</div>");
	}

	public void increaseCnt () {
		// called by Tabpanel, tell Tabbox the number of tabpanel is increased
		_panelCnt++;
	}

	// its better provided by a .js file
	private String showMatchedPanel () {
		// the javascript that executes while tabpanel's header clicked
		StringBuilder cmd = new StringBuilder();

		cmd.append("var headerContainer = this.parentNode,")
			.append("	headerArray = headerContainer.childNodes,")
			.append("	bodyContainer = headerContainer.nextSibling,")
			.append("	bodyArray = bodyContainer.childNodes,")
			.append("	ele, i, idx;")
			.append("for (i = 0; i < headerArray.length; i++) {")
			.append("	if ((ele = headerArray[i]) == this) {")
			.append("		ele.style.backgroundColor = 'gray';")
			.append("		idx = i;")
			.append("	} else")
			.append("		ele.style.backgroundColor = 'transparent';")
			.append("}")
			.append("for (i = 0; i < bodyArray.length; i++) {")
			.append("	if (i == idx)")
			.append("		bodyArray[i].style.display = 'block';")
			.append("	else")
			.append("		bodyArray[i].style.display = 'none';")
			.append("}");
		return cmd.toString();
	}
}
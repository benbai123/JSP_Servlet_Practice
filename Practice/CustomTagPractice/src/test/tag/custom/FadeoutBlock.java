package test.tag.custom;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Fadeout Block JSP Custom Tag, body tag, can contain jsp content body.
 *
 */
public class FadeoutBlock extends TagSupport {

	private static final long serialVersionUID = 3563006227719937104L;
	private String _style = "background-color: CCBBEE;"; //--css class
	private String _styleClass = null;
	private Integer _duration = 1000;
	private float _step = 0.1f;

	/**
	 * tag attribute setter
	 * @param style The style of this element
	 */
	public void setStyle(String style){
		if (style != null && !style.isEmpty())
			_style = style;
	}
	/**
	 * tag attribute setter
	 * @param styleClass The css class of this element
	 */
	public void setStyleClass(String styleClass){
		_styleClass = styleClass;
	}
	/**
	 * tag attribute setter
	 * @param duration The duration of fade-out action
	 */
	public void setDuration (Integer duration) {
		if (duration > 0)
			_duration = duration;
	}
	/**
	 * tag attribute setter
	 * @param step The step value of each fade-out step
	 */
	public void setStep (float step) {
		if (step > 0)
			_step = step;
	}
	/**
	 * do start tag
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			// output div's start tag, style, class and fadeOut function
			out.print("<div style=\""+_style+"\"");
			if (_styleClass != null)
				out.print(" class=\""+_styleClass+"\"");
			out.print("onclick=\""+fadeOut()+"\">");
		} catch (Exception e) {
			throw new JspException("Error: IOException while writing to client");
		}
		//-- continue process the body content
		return EVAL_BODY_INCLUDE;
	}
	/**
	 * do end tag
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			// output div's end tag
			pageContext.getOut().print("</div>");
		}
		catch (IOException ioe) {
			throw new JspException("Error: IOException while writing to client");
		}
		//-- continue process the page
		return EVAL_PAGE;
	}
	/**
	 * The fadeOut function, its better provided from a js file.
	 * @return String, the fadeOut function
	 */
	private String fadeOut () {
		StringBuilder sb = new StringBuilder();
		sb.append("var ele = this, time = "+_duration / (1.0/_step)
				+", eStyle = ele.style, inc = "+_step+", timer, value;")
			.append("if (!eStyle.opacity) eStyle.opacity = 1;")
			.append("if (!ele.fo) ele.fo = setInterval(function () {")
			.append("if (eStyle.opacity > 0) {")
			.append("value = eStyle.opacity; eStyle.opacity -= inc;")
			.append("if (value == eStyle.opacity) value = eStyle.opacity = 0;")
			.append("else value = eStyle.opacity;")
			.append("eStyle.filter = 'alpha(opacity = ' + (value*100) + ')';")
			.append("} else clearInterval(ele.fo);")
			.append("}, time);");
		
		return sb.toString();
	}
}
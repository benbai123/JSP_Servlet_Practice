package test.servlet.dynamicaddservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet will be added by DynamicAddListener#contextInitialized
 *
 */
public class DynamicAddedServlet extends HttpServlet {

	private static final long serialVersionUID = -8873939883201271898L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext sc = getServletContext();
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Servlet Added </title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h2 style=\"margin: 50px;\">The Servlet Version is 3.0 or later so<br />"
					+ "&nbsp;the Servlet is added.<br /><br />"
					+ "&nbsp;&nbsp;Servlet Version: "+sc.getMajorVersion()+"."+sc.getMinorVersion()+"</h2>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}
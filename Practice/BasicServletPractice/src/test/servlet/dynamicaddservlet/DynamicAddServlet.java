package test.servlet.dynamicaddservlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * This servlet will add the DynamicAddedServlet if servlet version is 3.0 or later
 *
 */
@WebServlet(name="DynamicAddServlet", urlPatterns={"/add.jsp"},
loadOnStartup=1)
public class DynamicAddServlet extends HttpServlet {
	private static final long serialVersionUID = -8873939883201271898L;

	@Override
	public void init() throws ServletException {
		super.init();
		// Dynamic add servlet for servlet 3.x or later
		ServletContext sc = getServletContext();
		if (sc.getMajorVersion() >= 3) {
			final ServletRegistration.Dynamic dn =
					getServletContext().addServlet("DynamicAddedServlet", DynamicAddedServlet.class);
			dn.setAsyncSupported(true);
			dn.addMapping("/added.jsp");
		}
	}
}
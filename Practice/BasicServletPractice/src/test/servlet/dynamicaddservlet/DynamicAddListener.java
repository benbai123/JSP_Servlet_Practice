package test.servlet.dynamicaddservlet;

import javax.servlet.*;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

/**
 * This listener will add the DynamicAddedServlet if servlet version is 3.0 or later
 *
 */
@WebListener()
public class DynamicAddListener implements ServletContextListener {
	private static final long serialVersionUID = -8873939883201271898L;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// Dynamic add servlet for servlet 3.x or later
		ServletContext sc = event.getServletContext();
		if (sc.getMajorVersion() >= 3) {
			final ServletRegistration.Dynamic dn =
					sc.addServlet("DynamicAddedServlet", DynamicAddedServlet.class);
			dn.setAsyncSupported(true);
			dn.addMapping("/added.jsp");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
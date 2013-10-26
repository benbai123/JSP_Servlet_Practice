package test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TestServletContextListener implements ServletContextListener {
	// do something on server shutdown
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("On server shutdown");
	}
	// do something on server startup
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("On server startup");
	}
}
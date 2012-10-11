package test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * print message when server start,
 * to make sure web.xml works fine
 *
 */
public class TestListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("contextInitialized");
	}

}

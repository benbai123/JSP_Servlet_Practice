package test;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.asual.lesscss.LessException;

import test.util.LESSUtils;

public class TestServletContextListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing on server shutdown
	}
	// compile less on server startup
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			String webContentPath = sce.getServletContext().getRealPath("/");
			LESSUtils.compile(new File(webContentPath + File.separator + "resources" + File.separator + "less" + File.separator + "test.less"),
					new File(webContentPath + File.separator + "resources" + File.separator + "css" + File.separator + "test.css"));
		} catch (LessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
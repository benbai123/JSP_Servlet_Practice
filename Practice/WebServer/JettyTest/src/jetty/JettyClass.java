package jetty;


import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * start an embedded jetty server
 *
 */
public class JettyClass {
	public static void main(String[] args) throws Exception {
		// use 8080 port
		Server server = new Server(8080);

		WebAppContext context = new WebAppContext();

		// use relative path, start from project root
		context.setResourceBase("src/webapp/content");
		// page address: http://localhost:8080/EmbeddingJettyTest
		context.setContextPath("/EmbeddingJettyTest");
		context.setParentLoaderPriority(true);

		server.setHandler(context);

		server.start();
		server.join();
    }
}

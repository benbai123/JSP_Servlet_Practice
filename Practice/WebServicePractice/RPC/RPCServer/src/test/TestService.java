package test;

import javax.servlet.*;

import redstone.xmlrpc.XmlRpcServlet;

/**
 * The servlet that extends redstone.xmlrpc.XmlRpcServlet
 */
public class TestService extends XmlRpcServlet {
	/**
	 * auto-generated serial version UID
	 */
	private static final long serialVersionUID = 7764199825847356985L;

	public void init( ServletConfig servletConfig )
		throws ServletException {
		// init
		super.init( servletConfig );
		// regist the Service
		// HelloClockService is the service name for client to invoke
		// new HelloClock() is the instance that provide this service
		getXmlRpcServer().addInvocationHandler( "HelloClockService", new HelloClock());
	}
}
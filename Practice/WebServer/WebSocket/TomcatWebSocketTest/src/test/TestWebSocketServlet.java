package test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

/**
 * Tested with Tomcat 7.0.42
 * @author benbai123
 *
 */
public class TestWebSocketServlet extends WebSocketServlet {

	private static final long serialVersionUID = -7663708549630020769L;

	// for message that will be sent to client
	private final AtomicInteger _cnt = new AtomicInteger(0);
	// hold each connection in this Set
	private final Set<TestMessageInbound> connections =
		new CopyOnWriteArraySet<TestMessageInbound>();
	/**
	 * For create connection only, each connection will
	 * handle it self as needed
	 */
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
		/* enable this fragment to send message by server directly
		 * 
		if (_cnt.getAndIncrement() == 0) {
			new Thread(new Runnable(){
				public void run () {
					try{
						while (true) {
							send();
							Thread.sleep(1000);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		*/
		return new TestMessageInbound();
	}
	private final class TestMessageInbound extends MessageInbound {
		// add self instance into connections Set while opened
		@Override
		protected void onOpen(WsOutbound outbound) {
			connections.add(this);
		}
		// remove self instance from connections Set whild closed
		@Override
		protected void onClose(int status) {
			connections.remove(this);
		}
		// ignore binary message since we just want to process text messages
		@Override
		protected void onBinaryMessage(ByteBuffer message) throws IOException {
			throw new UnsupportedOperationException(
				"Unsupported: Binary message.");
		}
		// send a message to each connection in connections Set
		// while receive a text message
		@Override
		protected void onTextMessage(CharBuffer message) throws IOException {
			send();
		}
	}
	public void send () {
		String msg = "Current count: " + _cnt.getAndIncrement();
		for (TestMessageInbound connection : connections) {
			try {
				connection.getWsOutbound().writeTextMessage(CharBuffer.wrap(msg));
			} catch (IOException ignore) {
				/* ignore */
			}
		}
	}
}
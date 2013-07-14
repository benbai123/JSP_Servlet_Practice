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
	private final AtomicInteger _cntForGroupOne = new AtomicInteger(0);
	private final AtomicInteger _cntForGroupTwo = new AtomicInteger(0);
	private final AtomicInteger _cntForGroupThree = new AtomicInteger(0);
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
		String uri = request.getRequestURI();
		String group = uri.substring(uri.lastIndexOf("/")+1, uri.length()).replace(".wsreq", "");

		// Create connection with specified group
		return new TestMessageInbound(group);
	}
	private final class TestMessageInbound extends MessageInbound {
		private String _group;
		public TestMessageInbound (String group) {
			_group = group;
		}
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
			// ignore
		}
		// send a message to each connection in connections Set
		// while receive a text message (specific group here)
		@Override
		protected void onTextMessage(CharBuffer message) throws IOException {
			send(message.toString());
		}
		public String getGroup () {
			return _group;
		}
	}
	// send message to specific group
	public void send (String group) {
		String msg = "Current count for " + group + ": " + getCounterByGroup(group);;
		for (TestMessageInbound connection : connections) {
			try {
				if (group.equals(connection.getGroup())) {
					connection.getWsOutbound().writeTextMessage(CharBuffer.wrap(msg));
				}
			} catch (IOException ignore) {
				/* ignore */
			}
		}
	}
	// get message for specific group
	private int getCounterByGroup (String group) {
		return "GroupOne".equals(group)? _cntForGroupOne.incrementAndGet() :
			"GroupTwo".equals(group)? _cntForGroupTwo.incrementAndGet() : _cntForGroupThree.incrementAndGet();
	}
}
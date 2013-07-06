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

public class TestWebSocketServlet extends WebSocketServlet {

	private static final long serialVersionUID = -7663708549630020769L;

	private final AtomicInteger _cnt = new AtomicInteger(0);
	private final Set<TestMessageInbound> connections =
		new CopyOnWriteArraySet<TestMessageInbound>();
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
		return new TestMessageInbound();
	}
	private final class TestMessageInbound extends MessageInbound {
		@Override
		protected void onOpen(WsOutbound outbound) {
			connections.add(this);
		}
		@Override
		protected void onClose(int status) {
			connections.remove(this);
		}
		@Override
		protected void onBinaryMessage(ByteBuffer message) throws IOException {
			throw new UnsupportedOperationException(
				"Unsupported: Binary message.");
		}
		@Override
		protected void onTextMessage(CharBuffer message) throws IOException {
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
}
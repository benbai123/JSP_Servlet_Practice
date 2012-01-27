package test.jsp.simplechat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

@WebServlet(name="ChatServlet", urlPatterns={"/chat.do"},
		loadOnStartup=1)
public class Chat extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 113880057049845876L;
	// message map, mapping user UID with a message list
	private static Map<String, List<String>> _chat = new HashMap<String, List<String>>();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		// send message
		if ("send".equals(action)) {
			// get param with UTF-8 enconding
			String msg = new String(req.getParameter("msg").getBytes("ISO-8859-1"), "UTF-8");
			String uid = (String)req.getSession().getAttribute("UID");
			for (String s : _chat.keySet()) {
				if (!s.equals(uid)) {
					synchronized (_chat.get(s)) {
						// put message to any other user's msg list
						_chat.get(s).add(uid+" said: "+msg);
					}
				}
			}
		} else if ("get".equals(action)) { // get message
			String uid = (String)req.getSession().getAttribute("UID");
			if (uid == null)
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			List<String> l = _chat.get(uid);
			synchronized (l) {
				if (l.size() > 0) {
					// for UTF-8 chars
					resp.setCharacterEncoding("UTF-8");
					PrintWriter out = resp.getWriter();
					JSONArray jsna = new JSONArray();
					// add all msg to json array and clear list
					while (l.size() > 0)
						jsna.add(l.remove(0));

					out.println(jsna);
					out.close();
				}
			}
		}
	}
	public static Map<String, List<String>> getChatMap () {
		return _chat;
	}
}
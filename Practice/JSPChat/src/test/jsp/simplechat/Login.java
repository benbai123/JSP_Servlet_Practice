package test.jsp.simplechat;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="LoginServlet", urlPatterns={"/login.go"},
		loadOnStartup=1)
public class Login extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		String uid = new String(req.getParameter("uid").getBytes("ISO-8859-1"), "UTF-8");
		String newUid = uid;
		int i = 2;
		Map chat = Chat.getChatMap();
		synchronized (chat) {
			// prevent uid conflict
			if ("you".equalsIgnoreCase(newUid))
				newUid = uid + i++;
			while (chat.containsKey(newUid))
				newUid = uid + i++;
			uid = newUid;
			chat.put(uid, new ArrayList());
		}
		req.getSession().setAttribute("UID", uid);
		resp.sendRedirect("chat.jsp");
	}
}
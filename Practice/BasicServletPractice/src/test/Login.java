package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// practice: servlet config in web.xml, send redirect, error page, get form value
public class Login extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		String uid = req.getParameter("uid");
		String upw = req.getParameter("upw");

		if ("test".equals(uid) && "abc".equals(upw)) {
			req.getSession().setAttribute("Login", "Login");
			resp.sendRedirect("content.html");
		} else
			throw new ServletException("Login fail");
	}
}
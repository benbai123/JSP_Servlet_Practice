package test;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		HttpSession sess = req.getSession();
		String uid = req.getParameter("uid");
		String upw = req.getParameter("upw");

		if ("test".equals(uid) && "abc".equals(upw))
			resp.sendRedirect("content.html");
		else
			throw new ServletException("Login fail");
	}
}
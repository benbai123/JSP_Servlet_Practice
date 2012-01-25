package test;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name="HintServlet", urlPatterns={"/auth.hint"},
		loadOnStartup=1)
// practice: get/set session attribute, output,
//servlet config by annotation 
public class Hint extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		HttpSession sess = req.getSession();
		String askTimes = (String)sess.getAttribute("ASK_TIMES");
		boolean tooMuch = false;
		if (askTimes == null)
			sess.setAttribute("ASK_TIMES", "1");
		else if (Integer.parseInt(askTimes) <= 10)
			sess.setAttribute("ASK_TIMES", Integer.parseInt(askTimes) + 1 + "");
		else
			tooMuch = true;

		// show alert message if ask too much
		if (tooMuch)
			out.println("You ask too much times !");
		else // else show hint
			out.println("id: test, pw: abc, ask times: " + askTimes + ", last ask at " + req.getParameter("time"));
		out.close();
	}
}
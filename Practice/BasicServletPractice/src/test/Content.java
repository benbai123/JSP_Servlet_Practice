package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

@WebServlet(name="ContentServlet", urlPatterns={"/content.gen"},
		loadOnStartup=1)
// practice: JSON
public class Content extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		JSONArray jsna = new JSONArray();
		// two data array
		JSONArray jsnaOne = new JSONArray();
		JSONArray jsnaTwo = new JSONArray();
		jsnaOne.add("1-1");
		jsnaOne.add("1-2");
		jsnaTwo.add("2-1");
		jsnaTwo.add("2-2");
		// put client side javascript to execute
		jsna.add("content.createTable(2, 2);");
		jsna.add(jsnaOne);
		jsna.add(jsnaTwo);
		out.println(jsna);
		out.close();
	}
}

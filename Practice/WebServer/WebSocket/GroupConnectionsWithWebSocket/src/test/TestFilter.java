package test;

import java.io.IOException;
import java.util.Random;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebFilter(filterName="PracticeOne", urlPatterns={"/index.jsp"})
// practice: filter config by annotation, filter, send error
public class TestFilter implements Filter {
	private static final String[] _groups = {"GroupOne", "GroupTwo", "GroupThree"};
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			 FilterChain chain) throws IOException, ServletException {
		Random r = new Random();
		// put the value "GroupOne"/"GroupTwo"/"GroupThree" with
		// name "GROUP" into request scope
		((HttpServletRequest)request).setAttribute("GROUP", _groups[r.nextInt(3)]);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}
}
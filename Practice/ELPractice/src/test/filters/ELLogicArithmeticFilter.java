package test.filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/* Config in web.xml */
public class ELLogicArithmeticFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		req.setAttribute("num_one", 1);
		req.setAttribute("num_two", 2);
		chain.doFilter(request, response);
	}
	public void destroy() {
		
	}
}

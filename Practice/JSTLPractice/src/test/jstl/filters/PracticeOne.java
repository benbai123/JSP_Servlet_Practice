package test.jstl.filters;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import test.jstl.*;

@WebFilter(filterName="PracticeOne", urlPatterns={"/practice_one.jsp"})
// practice: filter config by annotation, filter, send error
public class PracticeOne implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			 FilterChain chain) throws IOException, ServletException {

		// put the value "123" with name "UID" into request scope
		((HttpServletRequest)request).setAttribute("UID", "123");
		// put the value "ABC" with name "UID" into session scope
		((HttpServletRequest)request).getSession().setAttribute("UID", "ABC");
		// put the value "ZZZ" with name "Test" into session scope
		((HttpServletRequest)request).getSession().setAttribute("Test", "ZZZ");
		// create product list
		List<Product> pdl = new ArrayList<Product>();
		for (int i = 1; i <= 10; i++)
			pdl.add(new Product("ID_"+i, "NAME_"+i, i));
		// create data object
		Data dt = new Data(pdl);
		// put data object into request scope
		((HttpServletRequest)request).setAttribute("data", dt);
		chain.doFilter(request, response);
			
	}

	@Override
	public void destroy() {}
}
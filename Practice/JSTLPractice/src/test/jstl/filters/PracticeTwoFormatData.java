package test.jstl.filters;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import test.jstl.*;

@WebFilter(filterName="PracticeTwoFormatData", urlPatterns={"/practice_two__format_data.jsp"})
public class PracticeTwoFormatData implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			 FilterChain chain) throws IOException, ServletException {

		// put the date and timezone into request scope
		((HttpServletRequest)request).setAttribute("theDate", new java.util.Date());
		chain.doFilter(request, response);
			
	}

	@Override
	public void destroy() {}
}
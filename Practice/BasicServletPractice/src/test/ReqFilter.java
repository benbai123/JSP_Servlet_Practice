package test;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebFilter(filterName="performance", urlPatterns={"/content.gen"})
// practice: filter config by annotation, filter, send error
public class ReqFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			 FilterChain chain) throws IOException, ServletException {
		if (((HttpServletRequest)request).getSession().getAttribute("Login") == null)
			((HttpServletResponse)response).sendError(HttpServletResponse.SC_BAD_REQUEST);
		else
			chain.doFilter(request, response);
			
	}

	@Override
	public void destroy() {}
}
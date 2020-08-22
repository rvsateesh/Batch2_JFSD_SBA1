package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/admin")
public class LoginFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String loginID = request.getParameter("loginid");
		String password = request.getParameter("password");
		if(loginID.equals("admin") && password.equals("admin")) {
		chain.doFilter(request, response);
		}else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print("Credentials invalid! Go Back and login with correct credentials.");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

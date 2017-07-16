package com.employee.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import com.employee.service.AuthorizationService;

public class RestBacicAuthenticationFilter implements Filter {

	private static final String AUTHENTICATION_HEADER = "Authorization";
	private AuthorizationService authorizationService = new AuthorizationService();

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {

		if (arg0 instanceof HttpServletRequest) {

			String authorizationData = ((HttpServletRequest) arg0).getHeader(AUTHENTICATION_HEADER);

			if (authorizationService.checkIfAuthorized(authorizationData)) {
				arg2.doFilter(arg0, arg1);
			} else {
				if (arg1 instanceof HttpServletResponse) {

					HttpServletResponse response = (HttpServletResponse) arg1;

					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				}
			}

		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

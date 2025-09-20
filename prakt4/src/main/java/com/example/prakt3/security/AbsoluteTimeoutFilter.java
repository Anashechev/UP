package com.example.prakt3.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AbsoluteTimeoutFilter extends OncePerRequestFilter {
	private final long absoluteTimeoutMillis;
	private static final String LOGIN_TIME_ATTR = "loginTimeMillis";

	public AbsoluteTimeoutFilter(long absoluteTimeoutMillis) {
		this.absoluteTimeoutMillis = absoluteTimeoutMillis;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Long loginTime = (Long) session.getAttribute(LOGIN_TIME_ATTR);
			if (loginTime == null) {
				// Set on first interaction after authentication
				session.setAttribute(LOGIN_TIME_ATTR, System.currentTimeMillis());
			} else if (System.currentTimeMillis() - loginTime > absoluteTimeoutMillis) {
				session.invalidate();
				response.sendRedirect(request.getContextPath() + "/login?expired");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}

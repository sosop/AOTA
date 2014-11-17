package com.tcl.aota.admin.util;

import com.tcl.aota.persistent.model.Admin;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kelong
 * @date 11/7/14
 */
public class SessionFilter implements Filter {
	private static String loginPage = "/admin/login";
	private static Set<String> ignoreUrls = new HashSet<String>();
	private static String CONTEXTPATH = null;

	static {
		ignoreUrls.add("/admin/login");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String configIgnoreUrl = filterConfig.getInitParameter("config");
		if (!StringUtils.isEmpty(configIgnoreUrl)) {
			String[] urls = configIgnoreUrl.split(",");
			for (String url : urls) {
				ignoreUrls.add(url);
			}
		}
		CONTEXTPATH = filterConfig.getServletContext().getContextPath();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		String servletPath = servletRequest.getServletPath();
		if (ignoreUrls.contains(servletPath)) {
			filterChain.doFilter(request, response);
			return;
		}
		Admin admin = RequestUtil.getSessionUser(servletRequest);
		if (admin == null) {
			servletResponse.sendRedirect(CONTEXTPATH + loginPage);
		} else {
			filterChain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {

	}
}

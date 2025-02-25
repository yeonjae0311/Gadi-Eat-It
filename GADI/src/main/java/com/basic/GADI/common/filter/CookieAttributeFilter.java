package com.basic.GADI.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Collection;


public class CookieAttributeFilter implements Filter {
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		chain.doFilter(request, response);
		addSameSite(httpServletResponse);
	}

	private void addSameSite(HttpServletResponse response) {
		Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
		boolean firstHeader = true;
		boolean isLocal = isLocalEnvironment();  // 로컬 환경인지 체크

		for (String header : headers) {
			String newHeader = isLocal
					? String.format("%s; SameSite=None", header)   // 로컬에서는 Secure 제거
					: String.format("%s; Secure; SameSite=None", header);  // 운영에서는 Secure 추가

			if (firstHeader) {
				response.setHeader(HttpHeaders.SET_COOKIE, newHeader);
				firstHeader = false;
			} else {
				response.addHeader(HttpHeaders.SET_COOKIE, newHeader);
			}
		}
	}

	private boolean isLocalEnvironment() {
		String host = System.getProperty("user.name");  // 또는 다른 환경변수 체크
		return "localhost".equalsIgnoreCase(host) || "127.0.0.1".equalsIgnoreCase(host);
	}
}
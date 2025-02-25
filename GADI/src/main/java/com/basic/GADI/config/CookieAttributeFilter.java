package com.basic.GADI.config;

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
		addSameSite(httpServletResponse , "None"); 	
	}

    private void addSameSite(HttpServletResponse response, String sameSite) {
	    Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
	    boolean firstHeader = true;
	    for (String header : headers) { // there can be multiple Set-Cookie attributes
	        if (firstHeader) {
	            response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
	            firstHeader = false;
	            continue;
	        }
	        response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
	    }  
    }
	
}
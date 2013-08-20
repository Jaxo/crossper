package com.crossper.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
	 
public class HttpCacheFilter implements Filter {
    private final static String    HEADER_GET_KEY = "Cache-Control";
    private final static String    HEADER_PRAGMA = "Pragma";
    private final static String    HEADER_EXPIRES = "Expires";
    private String cacheLifeTimeInstruction = null;
	    
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (null != cacheLifeTimeInstruction) {
            ((HttpServletResponse) res).setHeader(HEADER_GET_KEY, cacheLifeTimeInstruction);
            ((HttpServletResponse) res).setHeader(HEADER_PRAGMA, null);
            //
            final int CACHE_DURATION_IN_SECOND = 60 * 60 * 24 * 5; // 5 days
            final long CACHE_DURATION_IN_MS = CACHE_DURATION_IN_SECOND  * 1000;
            long now = System.currentTimeMillis();
            //((HttpServletResponse) res).setDateHeader("Last-Modified", now);
    ((HttpServletResponse) res).setDateHeader(HEADER_EXPIRES, now + CACHE_DURATION_IN_MS);
        } // end-if

        chain.doFilter(req, res);
    }
	 
    public void init(FilterConfig config) throws ServletException {
        cacheLifeTimeInstruction = config.getInitParameter(HEADER_GET_KEY);
    }
	     
    public void destroy() {
        cacheLifeTimeInstruction = null;
    }
	 
}
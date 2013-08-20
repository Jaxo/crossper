package com.crossper.filters;

import com.crossper.representations.security.UserRememberMeToken;
import com.crossper.services.UserRememberMeServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: deepakpol
 * Date: 29/05/13
 * Time: 11:17 PM
 */
public class AutoLoginFilter implements Filter {
    private ConnectionFactoryRegistry connectionFactoryLocator;
    private UserRememberMeServices rememberMeServices;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String provider = (String) request.getAttribute("provider");
        UserRememberMeToken userRememberMeToken = (UserRememberMeToken) request.getAttribute("user_token");
        if (provider != null) {
            OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(provider);
            try {

                Connection<?> connection = connectionFactory.createConnection(new AccessGrant(userRememberMeToken.getTokenValue()));
                if (connection != null) {
                    Authentication rememberMeAuth = rememberMeServices.createAutoLogin(request, userRememberMeToken.getUsername());

                    SecurityContextHolder.getContext().setAuthentication(rememberMeAuth);
                    rememberMeServices.onLoginSuccess(request, response, rememberMeAuth);
                }
            } catch (NotAuthorizedException e) {

            } catch (Exception e) {

            }
        } else{
            //create authentication
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void setConnectionFactoryLocator(ConnectionFactoryRegistry connectionFactoryLocator) {
        this.connectionFactoryLocator = connectionFactoryLocator;
    }

    public void setRememberMeServices(UserRememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }
}

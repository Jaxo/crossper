package com.crossper.filters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: dpol
 * Date: 5/29/13
 * Time: 1:42 PM
 */
public class SocialAppAuthFilter extends GenericFilterBean implements ApplicationEventPublisherAware {
    private AuthenticationManager authenticationManager;

    private RememberMeServices rememberMeServices;

    private ApplicationEventPublisher eventPublisher;
    private AuthenticationSuccessHandler successHandler;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(SecurityContextHolder.getContext().getAuthentication() == null){
            Authentication authentication = rememberMeServices.autoLogin(request, response);

            if (authentication != null) {
//                authentication = authenticationManager.authenticate(authentication);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                if (eventPublisher != null) {
                    eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                            SecurityContextHolder.getContext().getAuthentication(), this.getClass()));
                }

                if (successHandler != null) {
                    successHandler.onAuthenticationSuccess(request, response, authentication);

                    return;
                }
            }

        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.resources;

import com.crossper.exceptions.UnregisteredUserException;
import com.crossper.exceptions.UserServiceException;
import com.crossper.models.Business;
import com.crossper.models.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.crossper.representations.UserLoginRepresentation;
import com.crossper.representations.UserServiceResponse;
import com.crossper.representations.security.UserRememberMeToken;
import com.crossper.services.BusinessService;
import com.crossper.services.UserRememberMeServices;
import com.crossper.services.UserService;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.RequestBody;

@Path("/users")
public class UserResource {
    private UserService userService;
    private SocialLoginHelper socialLoginHelper;
    @Autowired
    private BusinessService bizService;
    
    @POST
    @Path("/signup/{provider:(\\w*)?}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userSignUp(@PathParam("provider") String provider, @Context HttpServletRequest request, @Context HttpServletResponse response, @RequestBody UserLoginRepresentation userLogin) throws UserServiceException
    {
        if("facebook".equals(provider))
            userLogin.setProvider("facebook");
        UserServiceResponse serviceResponse = userService.registerUser(userLogin);
        if("facebook".equals(provider)){
        	UserRememberMeToken userRememberMeToken = userLogin.getUserToken().convertToRememberMeToken();
            socialLoginHelper.createToken(request, response, provider, userRememberMeToken);
        }

        return  Response.status(Response.Status.OK).entity(serviceResponse.getResponseObj()).build();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSocialLoginHelper(SocialLoginHelper socialLoginHelper) {
        this.socialLoginHelper = socialLoginHelper;
    }
    
    @GET
    @Path("/login")  
    @Produces(MediaType.TEXT_PLAIN)
    public Response userLogin(@Context HttpServletRequest request) throws UserServiceException, UnregisteredUserException 
    {        
        String returnValue = "success";
        if (request.getUserPrincipal() != null) {
            String email = request.getUserPrincipal().getName();
            userService.updateLastLogin(email);
            if ( isBizUser()) {
                 Business biz = bizService.findByEmail(email);
                 HttpSession session = request.getSession(true);
                 session.setAttribute("bizId", biz.getId());        
                 returnValue = biz.getId();
            } else {
                User consumer = userService.findByEmail(email);
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", consumer.getId());
                returnValue = consumer.getId();
            }
                
            return  Response.status(Response.Status.OK).entity(returnValue).build();
        }
        else{
            return  Response.status(Response.Status.UNAUTHORIZED).entity("failed").build();
        }
    }
    
    private boolean isBizUser() {
        boolean isBizUser = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SimpleGrantedAuthority bizUserAuth = new SimpleGrantedAuthority(User.USER_ROLES.ROLE_BUSINESS.toString());
        if ( auth.getAuthorities().contains(bizUserAuth)) {
            isBizUser = true;
        }
        return isBizUser;
    }
    
    @GET
    @Path("/loginFailure")    
    public Response userLoginFailure(@Context HttpServletRequest request) throws UserServiceException 
    {                      
        String userName = request.getParameter("j_username");
        if (userName != null )
            userService.updateLoginFailed(userName);
        return  Response.status(Response.Status.UNAUTHORIZED).entity("failed").build();
    }
    
    @POST
    @Path("/logout")
    //@Consumes(MediaType.APPLICATION_JSON)
    public Response userLogout(@Context HttpServletRequest request) throws UserServiceException 
    {
        request.getSession().invalidate();
        /*URI springLogoutUrl = null;
        try {
            springLogoutUrl = new URI("/j_spring_security_logout");
        }catch (Exception ex) {
            
        }*/
        return Response.status(Response.Status.OK).entity("success").build();
    }
    public static class SocialLoginHelper{

        private ConnectionFactoryRegistry connectionFactoryLocator;
        private UserRememberMeServices rememberMeServices;

        public void createToken(HttpServletRequest request, HttpServletResponse response, String provider, UserRememberMeToken userRememberMeToken){

            if (provider != null) {
                OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(provider);
                try {

                    Connection<?> connection = connectionFactory.createConnection(new AccessGrant(userRememberMeToken.getTokenValue()));
                    if (connection != null) {
                        //TODO:Create constants
                        request.setAttribute("user_token", userRememberMeToken);
                        request.setAttribute("provider", provider);
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

        public void setConnectionFactoryLocator(ConnectionFactoryRegistry connectionFactoryLocator) {
            this.connectionFactoryLocator = connectionFactoryLocator;
        }

        public void setRememberMeServices(UserRememberMeServices rememberMeServices) {
            this.rememberMeServices = rememberMeServices;
        }
    }
}

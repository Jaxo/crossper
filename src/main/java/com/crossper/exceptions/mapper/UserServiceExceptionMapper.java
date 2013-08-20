/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.exceptions.mapper;

import com.crossper.exceptions.UserServiceException;
import java.util.Locale;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


@Provider
@Component
public class UserServiceExceptionMapper implements ExceptionMapper<UserServiceException> {

    @Autowired
    private MessageSource messageResource;

    @Override
    public Response toResponse(UserServiceException e) {
        String msg = messageResource.getMessage(e.getMessage(), null, Locale.ENGLISH);
        return Response.serverError().entity(msg).build();
    }
    
}

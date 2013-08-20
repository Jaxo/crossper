package com.crossper.exceptions.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * User: deepak
 * Date: 6/5/13
 * Time: 3:30 PM
 */
@Provider
@Component
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Autowired
    private MessageSource messageResource;

    @Override
    public Response toResponse(Throwable throwable) {
        throwable.printStackTrace();
        return Response.serverError().entity("Internal Server Error").build();
    }
}

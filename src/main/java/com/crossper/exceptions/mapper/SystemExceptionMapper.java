package com.crossper.exceptions.mapper;

import com.crossper.exceptions.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Locale;

/**
 * User: deepak
 * Date: 6/5/13
 * Time: 2:01 PM
 */
@Provider
@Component
public class SystemExceptionMapper implements ExceptionMapper<SystemException> {

    @Autowired
    MessageSource messageResource;

    @Override
    public Response toResponse(SystemException e) {
        //TODO:Instead of using e.getMessage() use externalized string
        return Response.serverError().entity(messageResource.getMessage("error.systemException", null, Locale.getDefault())).build();
    }
}

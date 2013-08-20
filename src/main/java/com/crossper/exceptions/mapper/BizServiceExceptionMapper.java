/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.exceptions.mapper;


import com.crossper.exceptions.BizRegistrationException;
import com.crossper.exceptions.BizServiceException;
import java.util.Locale;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Provider
@Component
public class BizServiceExceptionMapper implements ExceptionMapper<BizServiceException>{
    @Autowired
    private MessageSource messageResource;

    @Override
    public Response toResponse(BizServiceException bizRegistrationException) {
        return Response.serverError().entity(messageResource.getMessage("error."+bizRegistrationException.getErrorCode().toString(), null, Locale.getDefault())).build();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.exceptions.mapper;

import com.crossper.exceptions.OfferServiceException;
import java.util.Locale;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class OfferServiceExceptionMapper implements ExceptionMapper<OfferServiceException> {

    @Autowired
    private MessageSource messageResource;

    @Override
    public Response toResponse(OfferServiceException e) {
        String msg = messageResource.getMessage(e.getMessage(), null, Locale.ENGLISH);
        return Response.serverError().entity(msg).build();
    }
    
}


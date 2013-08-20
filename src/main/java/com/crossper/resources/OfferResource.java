/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossper.resources;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.crossper.models.Offer;
import com.crossper.representations.ScannedOfferRepresentation;
import com.crossper.services.OfferService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * User: dpol
 * Date: 5/19/13
 * Time: 9:12 PM
 */
@Path("/consumers")

public class OfferResource {
    private OfferService offerService;
    
    @GET
    @Path("/{userId}/offers")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getOffers( @PathParam("userId") String userId , @QueryParam ("filter") String filter,@QueryParam ("search") String search, @Context HttpServletRequest request, @Context HttpServletResponse response) 
    {
        List<ScannedOfferRepresentation> offerList = new ArrayList<ScannedOfferRepresentation>();
    	if ( null != filter && ! filter.trim().isEmpty() ) {
            offerList = offerService.getOffersForCategory(userId, filter);
        } else if ( null != search && ! search.trim().isEmpty()) {
            offerList = offerService.getOffersForSearchCriteria(userId, search);
        }else {
            offerList = offerService.getOffers(userId);
        }
    	return Response.status(Response.Status.OK).entity(offerList).build();
     
    }
    
    @POST
    @Path("/{userId}/offers/{offerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response redeemOffer( @PathParam("userId") String userId , @PathParam("offerId") String offerId , @RequestBody ScannedOfferRepresentation  scanRep) {
    	offerService.redeemOffer(scanRep);
    	return Response.status(Response.Status.OK).entity("success").build();
     
    }
    
    @DELETE
    @Path("/{userId}/offers/{offerId}")
    @Consumes (value = MediaType.APPLICATION_JSON)
    public Response deleteOffer ( @PathParam("userId") String userId, @PathParam("offerId") String offerId, @RequestBody ScannedOfferRepresentation  scanRep) {
        boolean isSuccess = offerService.removeOffer(scanRep);
        return Response.status(Response.Status.OK).entity("success").build();
    }
    public OfferService getOfferService() {
        return offerService;
    }

    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

}

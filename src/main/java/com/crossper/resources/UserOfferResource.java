/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.resources;

import com.crossper.exceptions.OfferServiceException;
import com.crossper.exceptions.UnregisteredUserException;
import com.crossper.representations.ScanRepresentation;
import com.crossper.representations.ScannedOfferRepresentation;
import com.crossper.services.OfferService;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/downloadedOffers")
public class UserOfferResource {
    
    private String redirectUrl;
    public static final String defaultUrl = "http://localhost:8080/crossper";
    
    private OfferService offerService;
    /**
     *
     * @param request
     * @param response
     * @return
     */
    @Path ("/{publisherId}/{userId}")
    @GET
    //@Produces("text/html")
    @Produces (MediaType.APPLICATION_JSON)
    public Response downloadOffers(@PathParam("publisherId") String publisherId, @PathParam("userId") String userId, @Context HttpServletRequest request, @Context HttpServletResponse response)
            throws OfferServiceException
    {
		
//        String publisherId = request.getParameter("publisher");
//        String userId = request.getParameter("user");
         URI redirectTo = null;
        
        
                if ( null == publisherId || null == userId || publisherId.isEmpty() || userId.isEmpty()) {
                    try {
                        redirectTo = new URI(getRedirectUrl());
                     }catch(Exception ex) {
                        
                     }
                        return Response.seeOther(redirectTo).build();
                    
                }
                else {
                    String loggedInUser  = (String) request.getSession().getAttribute("userId");
                    if ( (null != loggedInUser) && loggedInUser.equals(userId)) {
                        ScanRepresentation scan = new ScanRepresentation( userId, publisherId);
                        List<ScannedOfferRepresentation> offers = offerService.downloadOffers(scan);
                        return Response.status(Response.Status.OK).entity(offers).build();
                    }else {
                        throw new UnregisteredUserException("offer.userMismatch");
                    }
                    
                }
            
    }

    @Path("/redeem")
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response testRedeem ( @Context HttpServletRequest request, @Context HttpServletResponse response) {
        String publisherId = request.getParameter("publisher");
        String userId = request.getParameter("user");
        String offerId = request.getParameter("offer");
        int index = Integer.parseInt(request.getParameter("index"));
        ScannedOfferRepresentation scanRep = new ScannedOfferRepresentation( userId, publisherId,offerId,index);
                    boolean isSuccess = offerService.redeemOffer(scanRep);
                    return Response.status(Response.Status.OK).entity(scanRep).build();
    }
    
    @Path("/delete")
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response testDelete ( @Context HttpServletRequest request, @Context HttpServletResponse response) {
        String publisherId = request.getParameter("publisher");
        String userId = request.getParameter("user");
        String offerId = request.getParameter("offer");
        int index = Integer.parseInt(request.getParameter("index"));
        ScannedOfferRepresentation scanRep = new ScannedOfferRepresentation( userId, publisherId,offerId,index);
                    boolean isSuccess = offerService.removeOffer(scanRep);
                    return Response.status(Response.Status.OK).entity(scanRep).build();
    }
    public String getRedirectUrl() {
        if ( redirectUrl == null )
            redirectUrl= defaultUrl;
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public OfferService getOfferService() {
        return offerService;
    }

    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }
    
    
}

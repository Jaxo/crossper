/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.resources;

import com.crossper.exceptions.BizRegistrationException;
import com.crossper.exceptions.BizServiceException;
import com.crossper.exceptions.UserServiceException;
import com.crossper.models.Business;
import com.crossper.representations.BusinessCategoryRepresentation;
import com.crossper.representations.BusinessOfferRepresentation;
import com.crossper.representations.BusinessRepresentation;
import com.crossper.representations.BusinessSignUpRepresentation;
import com.crossper.representations.ContactDetailsRepresentation;
import com.crossper.representations.DashboardStatRepresentation;
import com.crossper.representations.OfferRepresentation;
import com.crossper.representations.PromoterRepresentation;
import com.crossper.services.BusinessService;
import com.crossper.services.StatisticsService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import javax.ws.rs.DELETE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import sun.misc.IOUtils;

@Path ("/businesses")
public class BusinessResource {
    
    private BusinessService bizService;
    private String contactEmail;
    private String contactPhone;
    @Autowired
    private StatisticsService statsService;

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response businessSignUp(@RequestBody BusinessSignUpRepresentation bizSignUp) throws UserServiceException, BizRegistrationException
    {
        bizService.registerBusiness(bizSignUp);
        return  Response.status(Response.Status.CREATED).entity(bizSignUp).build();
    }

    @POST
    @Path("/{businessId}/businessPhoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadBusinessPhoto(@FormDataParam("businessPhoto") InputStream uploadedInputStream,
                                        @FormDataParam("businessPhoto") FormDataContentDisposition fileDetail,
                                        @PathParam("businessId") String businessId){

        bizService.addBusinessPhoto(businessId, uploadedInputStream, fileDetail);

        return  Response.status(Response.Status.CREATED).build();
    }
    
    @GET
    @Path("/{businessId}/offers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffers( @PathParam("businessId") String businessId, @Context HttpServletRequest request, @Context HttpServletResponse response) 
    {
         List<BusinessOfferRepresentation> offerList = bizService.getBusinessOffers(businessId);
        return  Response.status(Response.Status.CREATED).entity(offerList).build();
        
    }
    
    @GET
    @Path("/{businessId}/offers/{offerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffer( @PathParam("businessId") String businessId, @PathParam("offerId") String offerId, @Context HttpServletRequest request, @Context HttpServletResponse response) 
    {
        OfferRepresentation offer = bizService.getBusinessOffer(businessId, offerId);
        return  Response.status(Response.Status.OK).entity(offer).build();
        
    }
    
    @POST
    @Path("/{businessId}/offers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOffer( @PathParam("businessId") String businessId, @Context HttpServletRequest request, @RequestBody OfferRepresentation bizOffer, @Context HttpServletResponse response) 
    {
        OfferRepresentation offer = bizService.addBusinessOffer(bizOffer);
        return  Response.status(Response.Status.OK).entity(offer).build();
        
    }
    
    @PUT
    @Path("/{businessId}/offers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editOffer( @PathParam("businessId") String businessId, @Context HttpServletRequest request, @RequestBody OfferRepresentation bizOffer, @Context HttpServletResponse response) 
    {
        OfferRepresentation offer = bizService.editBusinessOffer(bizOffer);
    	System.out.println("editing Offer ");
        return  Response.status(Response.Status.OK).entity(offer).build();
        
    }
    
    @GET
    @Path("/{businessId}/qrCode")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downLoadQrCodePdf(@PathParam("businessId") String businessId, @Context HttpServletResponse response){
        byte[] docStream = null;
        try {
           File  file = bizService.getQRCodePdf(businessId, 0);
           docStream = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(docStream);
        } catch (Exception e) {
            //TODO:Handle properly
            e.printStackTrace();
        }
        if ( docStream != null )
        return  Response.ok(docStream, MediaType.APPLICATION_OCTET_STREAM)
            .header("content-disposition","attachment; filename = flyer.pdf")
            .build();
        else
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("failed").build();
    }
    @GET
    @Path("/{business_id}/emailQrCode")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getQRCode( @PathParam("business_id") String businessId, @Context HttpServletRequest request, @Context HttpServletResponse response) throws UserServiceException
    {
        bizService.emailQRCode(businessId);
        return  Response.status(Response.Status.OK).entity("success").build();
    }

    
    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryList(  @Context HttpServletRequest request, @Context HttpServletResponse response) throws UserServiceException
    {
        
        List<BusinessCategoryRepresentation> categories = bizService.getCategories();
        return  Response.status(Response.Status.OK).entity(categories).build();
    }
    
    @GET
    @Path("/subcategories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubCategoryList(  @Context HttpServletRequest request, @Context HttpServletResponse response) throws UserServiceException
    {
        String catName = request.getParameter("category");
        List<String> categories = bizService.getSubcategories(catName);
        return  Response.status(Response.Status.OK).entity(categories).build();
    }
    
    @GET
    @Path("/activate")
    @Produces(MediaType.TEXT_HTML)
    public Response activateBusinessAccount(  @Context HttpServletRequest request, @Context HttpServletResponse response) throws UserServiceException
    {
        URI redirectTo = null;
        String email = request.getParameter("email");
        String activationCode = request.getParameter("activationCode");
        
        try {
            bizService.activateBusiness(email, activationCode);
            redirectTo = new URI("/web/signIn?email="+email);
            return  Response.seeOther(redirectTo).build();
        }catch (Exception ex) {
            //TODO
        }
        return  Response.seeOther(redirectTo).build();
    }
    /**
    @POST
    @Path("/{business_id}?action=")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response businessUpdate( @PathParam("business_id") String businessId, @Context HttpServletRequest request, @Context HttpServletResponse response, @RequestBody BusinessRepresentation bizLogin) throws UserServiceException, BizRegistrationException
    {
        bizService.updateBusiness(bizLogin);
        return  Response.status(Response.Status.OK).entity("success").build();
    }
    **/
    @POST
    @Path("/contactCrossper")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMailToCrossperAdmin(@RequestBody ContactDetailsRepresentation contactMsg, @Context HttpServletResponse response ) {
        bizService.sendSupportEmail(contactMsg);
        return Response.status(Response.Status.OK).entity(contactMsg).build();
    }
    
    @GET
    @Path("/contactCrossper")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrossperContactDetails(@Context HttpServletRequest request,@Context HttpServletResponse response ) {
        ContactDetailsRepresentation contactDetails = new ContactDetailsRepresentation();
        contactDetails.setContactEmail(getContactEmail());
        contactDetails.setContactPhone(getContactPhone());
        return Response.status(Response.Status.OK).entity(contactDetails).build();
    }
    @POST
    @Path("/checkEmail/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response isEmailUsed( @PathParam("email") String email, @Context HttpServletRequest request, @Context HttpServletResponse response, @RequestBody OfferRepresentation offer) throws UserServiceException
    {
        this.bizService.validateBusinessEmail(email);
        return  Response.status(Response.Status.OK).entity("success").build();
    }
    public void setBizService(BusinessService bizService) {
        this.bizService = bizService;
    }
    
    @GET
    @Path("business/{business_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBusinessRepresentation(@PathParam("business_id") String businessId) throws UserServiceException, BizRegistrationException
    {
    	Business businessRepresentation=bizService.getBusinessById(businessId);
    	return  Response.status(Response.Status.CREATED).entity(businessRepresentation).build();
    }
    
    @GET
    @Path("/{businessId}/businessPhoto")
    @Produces("image/*")
    public Response getBusinessImage(@PathParam("businessId") String businessId, @Context HttpServletResponse response){
        byte[] docStream = null;
        try {
        	docStream = bizService.getBusinessPhoto(businessId);
//           docStream = new byte[(int) file.length()];
//            FileInputStream fileInputStream = new FileInputStream(file);
//            fileInputStream.read(docStream);
//            fileInputStream.close();
        } catch (Exception e) {
            //TODO:Handle properly
            e.printStackTrace();
        }
        if ( docStream != null )
        return  Response.ok(docStream,"image/*")
            .build();
        else
            return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @POST
    @Path("/{business_id}/{offer_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOffer( @PathParam("business_id") String businessId, @PathParam("offer_id") String offerId,@Context HttpServletRequest request, @Context HttpServletResponse response, @RequestBody OfferRepresentation offer) throws UserServiceException
    {
        //TODO
        return  Response.status(Response.Status.CREATED).entity("success").build();
    }
    
    @DELETE
    @Path("/{business_id}/offers/{offer_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteOffer( @PathParam("business_id") String businessId, @PathParam("offer_id") String offerId,@Context HttpServletRequest request, @Context HttpServletResponse response, @RequestBody OfferRepresentation offer) throws UserServiceException
    {
       
        return  Response.status(Response.Status.CREATED).entity("success").build();
    }
    
    @POST
    @Path("/{business_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBusiness( @PathParam("business_id") String businessId, @Context HttpServletRequest request, @Context HttpServletResponse response, @RequestBody BusinessRepresentation business) throws UserServiceException
    {
        bizService.updateBusiness(business);
        return  Response.status(Response.Status.OK).entity("success").build();
    }
    
    @GET
    @Path("/{business_id}/publishers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPromoters( @PathParam("business_id") String businessId, @Context HttpServletRequest request, @Context HttpServletResponse response) throws BizServiceException
    {
        List<PromoterRepresentation> promoters = bizService.getPromoters(businessId);
        return  Response.status(Response.Status.OK).entity(promoters).build();
    }
    
    @GET
    @Path("/{business_id}/stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatistics( @PathParam("business_id") String businessId, @Context HttpServletRequest request, @Context HttpServletResponse response) throws BizServiceException
    {
        
       DashboardStatRepresentation stats = statsService.getDashboardStats(businessId);
        
        return  Response.status(Response.Status.OK).entity(stats).build();
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    //Test method:Remove afrer testing
    @POST
    @Path("/{businessId}/addoffer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOfferTest( @PathParam("businessId") String businessId, @Context HttpServletRequest request,  @Context HttpServletResponse response) 
    {
        OfferRepresentation bizOffer = new OfferRepresentation();
        bizOffer.setBusinessId(businessId);
        bizOffer.setTitle("Another offer for business");
        bizOffer.setDescription("Description another offer");
        OfferRepresentation offer = bizService.addBusinessOffer(bizOffer);
        return  Response.status(Response.Status.OK).entity(offer).build();
        
    }
    
    
}

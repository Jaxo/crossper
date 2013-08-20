package com.crossper.resources;

import com.crossper.models.Offer;
import com.crossper.models.ScannedOffer;
import com.crossper.models.User;
import com.crossper.repository.dao.UserDao;
import com.crossper.repository.dao.impl.BusinessCategoryDaoImpl;
import com.crossper.repository.dao.impl.OfferDaoImpl;
import com.crossper.repository.dao.impl.UserDaoImpl;
import com.crossper.services.impl.EmailServiceImpl;
import com.sun.jersey.api.view.Viewable;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.sun.jersey.api.view.Viewable;
import java.sql.Timestamp;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/mysignup")
public class SignupResource {
        private UserDaoImpl userDao;
        private BusinessCategoryDaoImpl bizTypeDao;
        private OfferDaoImpl offerDao;
        EmailServiceImpl emailService;
        
        static Logger log = Logger.getLogger(SignupResource.class.getName());
        static 
	@GET
	@Produces("text/html")
	public Viewable index(@QueryParam("error") boolean error){
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", error);
		return new Viewable("/pages/signup.jsp",map);
	}

    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(User user)
	{
             bizTypeDao.getCategories();
            JSONObject response = new JSONObject();
            try {
               log.debug(" Adding user to Mongo db");
                //userDao.registerUser(user);
                response.put("success", true);
                
                //emailService.sendCrossperMail("shubhada.pathak@claricetechnologies.com");
                testOfferSize();
                }catch (Exception ex) {
                    log.error(ex.getMessage());
                }
		return Response.ok(response).build();

	}
        
        public void setUserDao(UserDaoImpl dao) {
            this.userDao = dao;
        }
        public UserDaoImpl getUserDao() {
            return this.userDao;
        }

    public BusinessCategoryDaoImpl getBizTypeDao() {
        return bizTypeDao;
    }

    public void setBizTypeDao(BusinessCategoryDaoImpl bizTypeDao) {
        this.bizTypeDao = bizTypeDao;
    }

    public EmailServiceImpl getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }
        
     public void testOfferSize() {
        
        Date date = new Date();
       // Timestamp t = new Timestamp(date.getTime());
        date.getTime();
        Offer offer = new Offer();
         offer.setBusinessId("51a6ee56594c83c540ded0f9");
         offer.setDescription("Buty one get one coffee free forsafsdfsdafdsafdsfdsfdsfdsafdf. sadsfdsafdasfdsadsfdasfafd");
         offer.setStartDate(date);
         offer.setEndDate(date);
         offer.setTitle("this is offer from coffe shop.");
         offer.setQuantity(10);
         offer.setLimitedQuantity(true);
         //offer id = "51aba7ac594c06c8954ade1b"
           int i=0;
         //Offer offer = offerDao.findOffer("51aba7ac594c06c8954ade1b");
          //Offer offer = new Offer();
         try {
         /*offer.setId("51aba7ac594c06c8954ade1b");
         offer.setBusinessId("51a6ee56594c83c540ded0f9");
         offer.setDescription("Buty one get one coffee free forsafsdfsdafdsafdsfdsfdsfdsafdf. sadsfdsafdasfdsadsfdasfafd");
         offer.setStartDate(t);
         offer.setEndDate(t);
         offer.setTitle("this is offer from coffe shop.");
         offer.setQuantity(10);
         offer.setLimitedQuantity(true);
         while (true) {
         ScannedOffer scan = new ScannedOffer();
         scan.setPromoterId("51a6ee56594c83c540ded0f9");
         scan.setUserId("51a6ee56594c83c540ded0f9");
         scan.setScanDate(t);
         offer.addScannedOffer(scan);
         offerDao.updateOffer(offer); */
          offerDao.addOffer(offer);
          Offer foundOffer = offerDao.findOffer(offer.getId());
         i++;
         //}
         }catch (Exception es) {
             es.printStackTrace();
             log.error(es.getMessage());
             log.error("I value: " + i);
         }
             
    }

    public OfferDaoImpl getOfferDao() {
        return offerDao;
    }

    public void setOfferDao(OfferDaoImpl offerDao) {
        this.offerDao = offerDao;
    }
        
}

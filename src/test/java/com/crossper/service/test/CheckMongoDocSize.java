/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.service.test;


import com.crossper.models.Offer;
import com.crossper.models.Place;
import com.crossper.models.ScannedOffer;
import com.crossper.models.ScannedUserOffer;
import com.crossper.repository.GoogleService;
import com.crossper.repository.dao.impl.BusinessCategoryDaoImpl;
import com.crossper.repository.dao.impl.GoogleBizDaoImpl;
import com.crossper.repository.dao.impl.OfferDaoImpl;
import com.crossper.repository.dao.impl.UserOfferDaoImpl;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Shubhda
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CheckMongoDocSize  extends AbstractJUnit4SpringContextTests{ 
    @Resource
    OfferDaoImpl offerDao;
    
    @Resource
    UserOfferDaoImpl userOfferDao;
    
    @Resource
    GoogleBizDaoImpl gBizDao;
    
    @Resource
    BusinessCategoryDaoImpl bizTypeDao;
    
    @Resource
    GoogleService googleService;
    
    static Logger log = Logger.getLogger(CheckMongoDocSize.class.getName());
    public CheckMongoDocSize() {
        
    }
    @Before
    public void setUp() {
        System.out.println("setUp");
    }

    @After
    public void tearDown() {
        System.out.println("tearDown");
    }
    @Test
    public void testBusinessDao() {
        
    }
    @Test
    public void testOfferSize() {
        Date date = new Date();
       
       // Timestamp t = new Timestamp(date.getTime());
        date.getTime();
        Offer offer = new Offer();
         offer.setBusinessId("51a6ee56594c83c540dedppp");
         offer.setDescription("Buy one coffee get a free cookie.");
         offer.setStartDate(date);
         offer.setEndDate(date);
         offer.setTitle("Free cookie with coffee");
         offer.setQuantity(10);
         offer.setLimitedQuantity(true);
         //offer id = "51aba7ac594c06c8954ade1b"
           int i=0;
         //Offer offer = offerDao.findOffer("51aba7ac594c06c8954ade1b");
          //Offer offer = new Offer();
         try {
             offerDao.addOffer(offer);
          Offer foundOffer = offerDao.findOffer(offer.getId());
       
         /*while (i <4) {
             
         ScannedOffer scan = new ScannedOffer();
         //scan.setPromoterId("51a6ee56594c83c540ded0f9");
         scan.setPromoterId("51a6ee56594c83c540ded0f1");
         scan.setUserId("51a6ee56594c83c540ded0f9");
         scan.setScanDate(date);
         scan.setClaimDate(date);
         foundOffer.addScannedOffer(scan);
         offerDao.updateOffer(foundOffer); 
       
         i++;
         }*/
         while (i <1) {
             
         ScannedOffer scan = new ScannedOffer();
         scan.setPromoterId("51a6ee56594c83c540ded0f9");
         scan.setUserId("51a6ee56594c83c540ded011");
         scan.setScanDate(date);
         scan.setClaimDate(date);
         scan.setClaimed(true);
         foundOffer.addScannedOffer(scan);
         offerDao.updateOffer(foundOffer); 
       
         scan = new ScannedOffer();
         scan.setPromoterId("51a6ee56594c83c540ded0f8");
         scan.setUserId("51a6ee56594c83c540ded012");
         scan.setScanDate(date);
         scan.setClaimDate(date);
         scan.setClaimed(true);
         foundOffer.addScannedOffer(scan);
         offerDao.updateOffer(foundOffer); 
         i++;
         }
         }catch (Exception es) {
             es.printStackTrace();
             log.error(es.getMessage());
             log.error("count of scanned offers : " + i);
         }
             
    }
    
    @Test
    public void testUserOffers() {
        List<ScannedUserOffer> list = userOfferDao.getClaimedOffers("51a6ee56594c83c540ded0f9");
        log.debug("no of offers claimed for user f9 : " +list.size());
        
        List<ScannedUserOffer> list2 = userOfferDao.getClaimedOffers("51a6ee56594c83c540ded011");
        log.debug("no of offers claimed for user 11 : " +list2.size());
    }

    //@Test
    public void testPromotedOffer(){
        Date date = new Date();
        Offer offer = new Offer();
         offer.setBusinessId("51a6ee56594c83c540dedppp");
         offer.setDescription("Buy one cofee get a cookie free.");
         offer.setStartDate(date);
         offer.setEndDate(date);
         offer.setTitle("Free cookie");
         offer.setQuantity(10);
         offer.setLimitedQuantity(true);
         offerDao.addOffer(offer);
          Offer foundOffer = offerDao.findOffer(offer.getId());
          log.debug("New offer id : " + offer.getId());
          ScannedUserOffer updatedOffer = offerDao.publishOffer(foundOffer, "1122", "1111", date);
          
          log.debug("updated count: "+ updatedOffer.getQuantity());
    }
    @Test 
    public void testGoogleBizDump ()  throws Exception {
      
        Place retVal = googleService.getGooglePlaceBasics("Chaat");
        gBizDao.addBusiness(retVal);
    }
    public OfferDaoImpl getOfferDao() {
        return offerDao;
    }

    public void setOfferDao(OfferDaoImpl offerDao) {
        this.offerDao = offerDao;
    }

    public UserOfferDaoImpl getUserOfferDao() {
        return userOfferDao;
    }

    public void setUserOfferDao(UserOfferDaoImpl userOfferDao) {
        this.userOfferDao = userOfferDao;
    }

    public GoogleBizDaoImpl getgBizDao() {
        return gBizDao;
    }

    public void setgBizDao(GoogleBizDaoImpl gBizDao) {
        this.gBizDao = gBizDao;
    }
    @Test
    public void testBusinessCategoryList() {
        bizTypeDao.getCategories();
        
    }
    @Test
    public void testCategoryList() {
        bizTypeDao.getCategoriesForDisplay();
        
    }
    
    
}

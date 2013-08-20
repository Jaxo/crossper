
package com.crossper.services.impl;

import com.crossper.models.Business;
import com.crossper.models.Offer;
import com.crossper.repository.dao.BusinessCategoryDao;
import com.crossper.repository.dao.BusinessDao;
import com.crossper.repository.dao.UserOfferDao;
import com.crossper.representations.ScanRepresentation;
import com.crossper.services.OfferRankingService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class OfferRankingServiceImpl implements OfferRankingService {
    
    @Autowired
    BusinessCategoryDao bizCategoryDao;
    
    @Autowired
    UserOfferDao userOfferDao;
    
    @Autowired
    BusinessDao bizDao;
    
    static final Logger log = Logger.getLogger(OfferRankingServiceImpl.class);
    static final int ALLOWED_GAP_DAYS = 7;
    private int allowAfterDays;
    /**
     * Search valid offers and filter offers based on affinity/ranking with publisher/promoter
     * @param scan
     * @return 
     */
    @Override
    public List<Offer> getRankedOffersForDownload(ScanRepresentation scan) {
        List<Offer> rankedOfferList = new ArrayList<Offer>();
        try {
            List<Offer> offers = userOfferDao.getOffersForDownload(scan.getUserId(), scan.getPublisherId());
            Iterator<Offer> offerItr = offers.iterator();
            
            Business publisherDetails = bizDao.findById(scan.getPublisherId());
            String category = publisherDetails.getCategory();
            String subcategory = publisherDetails.getSubCategory();

            List<String> affinityCatList = bizCategoryDao.getAffinityCategories(category, subcategory);
            if ( affinityCatList != null &&  ! affinityCatList.isEmpty()) {
                while( offerItr.hasNext()) {
                    Offer offer = offerItr.next();
                    Business businessDetails = bizDao.findById(offer.getBusinessId());
                    if ( affinityCatList.contains(businessDetails.getCategory()) || affinityCatList.contains(businessDetails.getSubCategory()))
                        rankedOfferList.add(offer);
                }
           } 
            //TODO: Confirm if ok to select any offer if no offers in affinity categories found
           if ( rankedOfferList.isEmpty()) {
               Iterator<Offer> itr = offers.iterator();
                while(itr.hasNext())
                    rankedOfferList.add(itr.next());
            }
        
        }catch (Exception ex) {
            log.error("Exception getting ranked offers: " + ex.getMessage());
        }
        return rankedOfferList;
    }

    public BusinessCategoryDao getBizCategoryDao() {
        return bizCategoryDao;
    }

    public void setBizCategoryDao(BusinessCategoryDao bizCategoryDao) {
        this.bizCategoryDao = bizCategoryDao;
    }

    public UserOfferDao getUserOfferDao() {
        return userOfferDao;
    }

    public void setUserOfferDao(UserOfferDao userOfferDao) {
        this.userOfferDao = userOfferDao;
    }

    public BusinessDao getBizDao() {
        return bizDao;
    }

    public void setBizDao(BusinessDao bizDao) {
        this.bizDao = bizDao;
    }

    public int getAllowAfterDays() {
        if( allowAfterDays == 0)
            allowAfterDays = ALLOWED_GAP_DAYS;
        return allowAfterDays;
    }

    public void setAllowAfterDays(int allowAfterDays) {
        this.allowAfterDays = allowAfterDays;
    }
    
}

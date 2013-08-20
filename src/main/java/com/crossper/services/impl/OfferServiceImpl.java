
package com.crossper.services.impl;

import com.crossper.exceptions.UnknownPublisherException;
import com.crossper.exceptions.UnregisteredUserException;
import com.crossper.models.Business;
import com.crossper.models.Offer;
import com.crossper.models.ScannedUserOffer;
import com.crossper.repository.dao.BusinessDao;
import com.crossper.repository.dao.UserDao;
import com.crossper.repository.dao.UserOfferDao;
import com.crossper.representations.OfferBusinessRepresentation;
import com.crossper.representations.OfferSearchFilterRepresentation;
import com.crossper.representations.ScanRepresentation;
import com.crossper.representations.ScannedOfferRepresentation;
import com.crossper.services.OfferRankingService;
import com.crossper.services.OfferService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class OfferServiceImpl implements OfferService {
    
    @Autowired
    UserOfferDao userOfferDao;
    @Autowired
    UserDao userDao;
    @Autowired
    BusinessDao bizDao;
    
    private int maxOffersDownloaded;
    static final int MAX_OFFERS_DOWNLOAD = 3;
    @Autowired
    OfferRankingService rankingService;
    
    public List<ScannedOfferRepresentation> downloadOffers(ScanRepresentation scan) throws UnregisteredUserException, UnknownPublisherException {
        List<ScannedOfferRepresentation> offers = null;
        if ( !userDao.isValidUserId(scan.getUserId())) 
            throw new UnregisteredUserException ("offer.invalidUser");
        if ( !bizDao.isValidBizId(scan.getPublisherId()))
            throw new UnknownPublisherException("offer.invalidPublisher");
        
        List<Offer> rankedOffers = rankingService.getRankedOffersForDownload(scan);
        
        List<ScannedUserOffer> scannedOffers = userOfferDao.downloadOffers(scan, rankedOffers, getMaxOffersDownloaded());
        offers = toOfferRepresentation(scannedOffers);
        
        return offers;
    }
    public boolean redeemOffer(ScannedOfferRepresentation offer) {
        boolean returnValue = userOfferDao.redeemOffer(offer.getUserId(),offer.getOfferId(), offer.getScanIndex());
        return returnValue;
    }
    public boolean removeOffer(ScannedOfferRepresentation offer) {
        boolean returnValue = userOfferDao.removeOffer(offer.getUserId(), offer.getOfferId(), offer.getScanIndex());
        return returnValue;
    }
    public List<ScannedOfferRepresentation> getOffers (String userId) {
         List<ScannedOfferRepresentation> offers = null ;
         List<ScannedUserOffer> scannedOffers = userOfferDao.getScannedOffers(userId);
         offers = toOfferRepresentation(scannedOffers);
         return offers;
    }
    
    public List<ScannedOfferRepresentation> getOffersForCategory (String userId, String category) {
        List<ScannedOfferRepresentation> offers = null ;
         List<String> bizIds = bizDao.getBusinessIdsForCategory(category);
         List<ScannedUserOffer> scannedOffers = userOfferDao.getOffersByCategory(userId, bizIds, 0, 10);
         offers = toOfferRepresentation(scannedOffers);
         return offers;
    }
    public List<ScannedOfferRepresentation> getOffersForSearchCriteria (String userId, String searchString) {
        //TODO
        List<ScannedOfferRepresentation> offers = null ;
        List<String> locationIds = bizDao.getBusinessLocationIds(searchString);
        List<String> bizIds = bizDao.getBizIdsForSearchString(searchString);
        OfferSearchFilterRepresentation filter = new OfferSearchFilterRepresentation(locationIds, bizIds);
        List<ScannedUserOffer> scannedOffers = userOfferDao.getOffers(userId, searchString, filter, 0, 10);
        offers = toOfferRepresentation(scannedOffers);
        return offers;
    }
    private List<ScannedOfferRepresentation> toOfferRepresentation(List<ScannedUserOffer> scannedOffers) {
        ArrayList<ScannedOfferRepresentation> offers = new ArrayList<ScannedOfferRepresentation>();
        if (scannedOffers != null) {
            Iterator<ScannedUserOffer> offerItr = scannedOffers.iterator();
            while ( offerItr.hasNext() ) {
                ScannedUserOffer userOffer = offerItr.next();
                ScannedOfferRepresentation offerRep = new ScannedOfferRepresentation(userOffer);
                offerRep.setBusinessId(userOffer.getBusinessId());
                offerRep.setDescription(userOffer.getDescription());
                offerRep.setId(userOffer.getId());
                offerRep.setTitle(userOffer.getTitle());
                offerRep.setEndDate(userOffer.getEndDate());
                offerRep.setStartDate(userOffer.getStartDate());
                offerRep.setValidity(userOffer.getValidity());
                offerRep.setLocationId(userOffer.getLocationId());
                Business biz = getOfferBizDetails(userOffer.getBusinessId());
                if ( biz != null ) {
                    OfferBusinessRepresentation bizDetails = new OfferBusinessRepresentation(biz);
                    offerRep.setBusinessInfo(bizDetails);
                }
                offers.add(offerRep);
            }
        }
        return offers;
        
    }
    
    private Business getOfferBizDetails(String bizId) {
        return bizDao.findById(bizId);
    }

    public int getMaxOffersDownloaded() {
        if (maxOffersDownloaded == 0)
            maxOffersDownloaded = MAX_OFFERS_DOWNLOAD;
        return maxOffersDownloaded;
    }

    public void setMaxOffersDownloaded(int maxOffersDownloaded) {
        this.maxOffersDownloaded = maxOffersDownloaded;
    }
    
}

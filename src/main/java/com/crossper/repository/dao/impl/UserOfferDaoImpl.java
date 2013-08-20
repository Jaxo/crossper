/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao.impl;

import com.crossper.models.Offer;
import com.crossper.models.ScannedUserOffer;
import com.crossper.repository.dao.UserOfferDao;
import com.crossper.representations.OfferSearchFilterRepresentation;
import com.crossper.representations.ScanRepresentation;
import com.crossper.utils.DateHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.jongo.Jongo;

public class UserOfferDaoImpl  extends OfferDaoImpl  implements UserOfferDao {
    static Logger log = Logger.getLogger( UserOfferDaoImpl.class.getName());
    
    public UserOfferDaoImpl(Jongo jongo) {
        super(jongo);
    }
    public List<ScannedUserOffer> getClaimedOffers(String userId) {
        log.debug("Claimed offers for user :" + userId);
        List<ScannedUserOffer> claimedOffers = null;
        try {
        claimedOffers = getCollection().aggregate("{$unwind : '$scannedOffers' }")
          .and("{$match: {'scannedOffers.userId': #, 'scannedOffers.claimed' : #}}", userId, true)
          .as(ScannedUserOffer.class);
        }catch (Exception ex) {
            log.error("Error getting claimed offers for  user :" + userId  +" : " + ex.getMessage());
        }
        return claimedOffers;
    }
    
    public List<ScannedUserOffer> getScannedOffers(String userId) {
        List<ScannedUserOffer> scannedOffers= null;
        log.debug("getting scanned offers for user Id : " + userId);
        Date today = new Date();
        try {
            scannedOffers = getCollection().aggregate("{$unwind : '$scannedOffers' }")
          .and("{$match: {'scannedOffers.userId': #, 'scannedOffers.deleted' : # , 'scannedOffers.claimed' : # , $or: [ {endDate: { $exists: false }},{endDate : { $exists: true, $gte : # }}] }}", userId, false, false,today)
          .and("{$sort : {'scannedOffers.scanDate' : -1 } }")
          .as(ScannedUserOffer.class);
            log.debug("Total scanned and not claimed offers for  user : "+ scannedOffers.size());
        }catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return scannedOffers;
    }
    
    public List<ScannedUserOffer> downloadOffers(ScanRepresentation scanDetail, List<Offer> rankedOffers, int maxOffersCount ) {
        List<ScannedUserOffer> scannedOffers= new ArrayList<ScannedUserOffer>();
        String userId = scanDetail.getUserId();
        String publisherId = scanDetail.getPublisherId();
        int publishCount =0;
        log.debug("downloading offers for user :" + userId + " Publisher : " + publisherId);
        Date scanDateTime = new Date();
        try {
            //Iterator<Offer> offerItr = rankedOgetOffersForDownload(userId, publisherId);
            if ( rankedOffers != null) {
                Iterator<Offer> rankedItr = rankedOffers.iterator();
                while ( rankedItr.hasNext() && publishCount < maxOffersCount ) {
                    ScannedUserOffer  publishedOffer = publishOffer(rankedItr.next(), userId, publisherId,scanDateTime);
                    if ( null != publishedOffer.getScannedOffers() && publishedOffer.getScannedOffers().getScanDate() != null)                    
                        publishCount ++;
                }
                log.debug("Downloaded new offers : " + publishCount);
                // return newly scanned offers just now 
                scannedOffers = getScannedOffersForDate( scanDetail.getUserId(), scanDateTime);
            } else {
                log.warn("No new offers found for download userId: "+ userId + " Publisher Id: " + publisherId);
            }
            
        }catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return scannedOffers;
    }
    
    @Override
    public List<Offer> getOffersForDownload(String userId, String publisherId) {

        log.debug("Filter valid offers ");
        List<Offer> filteredOfferList = new ArrayList<Offer>();
        Date currDate = new Date();
        Date weekBeforeDate = DateHelper.getDateBefore(currDate, 7);
        Iterable<Offer> filteredOffers = getCollection().find(VALID_OFFERS_MATCH, publisherId,currDate, currDate, userId, userId, weekBeforeDate).as(Offer.class);
        Iterator<Offer> offerItr = null;

        if (filteredOffers != null) {
            int count =0;
            offerItr = filteredOffers.iterator();
            while(offerItr.hasNext()) {
                Offer offer = offerItr.next();
                if (offer != null ) {
                    filteredOfferList.add(offer);
                    log.debug(" Offer Id  : " + offer.getId());
                    count++;
                }
            }
            log.debug("Filtered valid offers for publishing: "+ count);
        }
       
        return filteredOfferList;
    }
    
    private List<ScannedUserOffer> getScannedOffersForDate(String userId, Date scanDateTime) {
         List<ScannedUserOffer> scannedOffers= null;
        log.debug("getting newly scanned offers for user Id : " + userId);
        try {
           scannedOffers = getCollection().aggregate("{$unwind : '$scannedOffers' }")
          .and("{$match: {'scannedOffers.userId': #, 'scannedOffers.scanDate' : #  } }", userId, scanDateTime )
          .and("{$sort : {'scannedOffers.scanDate' : -1 } }")
          .as(ScannedUserOffer.class);
            log.debug("Total scanned and not claimed offers for  user : "+ scannedOffers.size());
        }catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return scannedOffers;
    }
    @Override
    public boolean redeemOffer (String userId, String offerId, int scanIndex) {
        boolean isSuccess = false;
        Date claimDate = new Date();
        ScannedUserOffer updatedOffer = null;
        try {
            log.debug(" Redeem offer request userId: " + userId + "offer id : "+ offerId);
            String fieldPrefix = "scannedOffers.$" ;//+String.valueOf(scanIndex);
            String updateQueryString = " { $set : { "+fieldPrefix  +".claimed : # , "+ fieldPrefix +".claimDate : # }, $inc: {claimCount : 1 }   } ";
            Offer offer= getCollection().findAndModify("{ _id: #, 'scannedOffers.userId' : #, 'scannedOffers.scanIndex' : # } ",new ObjectId(offerId), userId, scanIndex).
                    with ( updateQueryString, true, claimDate).returnNew().as(Offer.class);
                    isSuccess = true;
            updatedOffer = offer.getScannedUserOfferObj();
        }catch (Exception ex) {
            isSuccess = false;
            log.error(ex.getMessage());
        }
        return isSuccess;
    }
    @Override
    public boolean removeOffer(String userId, String offerId, int scanIndex ) {
        boolean isSuccess = false;
        Date deleteDate = new Date();
        ScannedUserOffer updatedOffer = null;
        try {
            log.debug(" Remove offer request userId: " + userId + "offer id : "+ offerId);
            String fieldPrefix = "scannedOffers.$" ;
            String updateQueryString = " { $set : { "+fieldPrefix  +".deleted : # , "+ fieldPrefix +".deleteDate : # } } ";
            Offer offer= getCollection().findAndModify("{ _id: #, 'scannedOffers.userId' : # , 'scannedOffers.scanIndex' : # }} ",new ObjectId(offerId), userId, scanIndex).
                with (updateQueryString ,true, deleteDate ).returnNew().as(Offer.class);
             updatedOffer = offer.getScannedUserOfferObj();
            isSuccess = true; 
         }catch (Exception ex) {
             isSuccess = false;
             log.error("Error removing offer: " + ex.getMessage());
         }
        return isSuccess;
    }
    /**
     * Search in User's offers based on search String. SearchCriteria can be location, category, biz name, offer description
     * Filter contains list of locationIds and businessIds that match the search String 
     * 
     * @param userId
     * @param searchCriteria
     * @param filter
     * @param startIndex
     * @param pageSize
     * @return 
     */
    @Override
    public List<ScannedUserOffer> getOffers(String userId, String searchCriteria, OfferSearchFilterRepresentation filter, int startIndex, int pageSize) {
         List<ScannedUserOffer> scannedOffers= null;
        log.debug("getting scanned offers for user Id : " + userId);
        log.debug("getting scanned offers for Search Criteria : " + searchCriteria);
        log.debug("getting scanned offers page size : " + pageSize);
        Date currDate = new Date();
        try {
           
            scannedOffers = getCollection().aggregate("{$unwind : '$scannedOffers' }")
                        .and("{$match: {'scannedOffers.userId': #, 'scannedOffers.deleted' : # , 'scannedOffers.claimed' : #, $or: [ {endDate: { $exists: false }},{endDate : { $exists: true, $gte : # }}] }}", userId, false, false,currDate)               
                        .and("{$match: { $or : [ {businessId : { $in : #} }, {locationId : {$in: #}},{ title : {$regex : #,  $options: 'i' }} , {description : {$regex: # , $options: 'i' } }] } }", 
                        filter.getBizIdList(),filter.getLocationIdList(), searchCriteria, searchCriteria)
                        .and("{$sort : {'scannedOffers.scanDate' : -1 } }")
                        .as(ScannedUserOffer.class);
     
            log.debug("Total scanned and not claimed offers for  user : "+ scannedOffers.size());
        }catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return scannedOffers;
    }
     
    @Override
     public List<ScannedUserOffer> getOffersByCategory(String userId, List<String>categoryBusinessIds, int startIndex, int pageSize) {
           List<ScannedUserOffer> scannedOffers= null;
            log.debug("getting scanned offers for user Id : " + userId);
            
            log.debug("getting scanned offers page limit : " + pageSize);
        Date today = new Date();
        try {
            scannedOffers = getCollection().aggregate("{$unwind : '$scannedOffers' }")
          .and("{$match: {'scannedOffers.userId': #, 'scannedOffers.deleted' : # , 'scannedOffers.claimed' : #, $or: [ {endDate: { $exists: false }},{endDate : { $exists: true, $gte : # }}], businessId : { $in : # }} }", userId, false, false, today, categoryBusinessIds)
          .and("{$sort : {'scannedOffers.scanDate' : -1 } }")
          .as(ScannedUserOffer.class);
            log.debug("Total scanned and not claimed offers for  user : "+ scannedOffers.size());
        }catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return scannedOffers;
      }
}

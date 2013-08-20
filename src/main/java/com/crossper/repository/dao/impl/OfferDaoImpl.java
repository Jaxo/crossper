package com.crossper.repository.dao.impl;

import com.crossper.models.Offer;
import com.crossper.models.ScannedUserOffer;
import com.crossper.repository.dao.OfferDao;
import java.util.Date;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;


public class OfferDaoImpl extends DaoBase implements OfferDao {
    static Logger log = Logger.getLogger(OfferDaoImpl.class.getName());
    
    public static final String OFFERS_AGGREGATE = "{$unwind : '$scannedOffers' }";
    public static final String CLAIMED_OFFERS_MATCH = "{$match : {'scannedOffers.userId' : #, 'scannedOffers.claimed' : # }}";
    public static final String VALID_OFFERS_MATCH= "{ $and : [ { businessId : { $ne: #  }} , { deleted : {$exists: false}}, "
            + " { $or: [ {startDate: {$exists: false} }, {startDate : { $exists: true, $lte: # }}] } , "
            + " { $or: [ {endDate: { $exists: false }},{endDate : { $exists: true, $gte : # }}] }, "
            + " { $or :[{'scannedOffers.userId' : {$ne : # }}, {$and : [{'scannedOffers.userId' : #}, {'scannedOffers.claimed' : true}, {'scannedOffers.claimDate' : {$lt : #} }] } ] }, "
            + " { $or : [{limitedQuantity : false}, { $and : [{limitedQuantity: true}, {quantity : { $gt : 0} }] } ]} ] }";
            //"{ $match : { promoterId : { $ne: #  }, startDate : { $gte: # }, endDate : {$lt : # }, 'scannedOffers.userId' : {$ne : # } }";
    
    static final String OFFER_COLLECTION = "offers";
    //get latest 4 scan details
    static final int DEFAULT_SCAN_DETAIL_LIMIT= -2;
    protected MongoCollection offers;
    
    public OfferDaoImpl(Jongo jongo) {
        super(jongo);
        offers = jongo.getCollection(OFFER_COLLECTION);
    }
    protected MongoCollection getCollection(){
         if( offers == null)
             this.offers = jongo.getCollection(OFFER_COLLECTION);
         return offers;
    }
    
    @Override
    public String addOffer(Offer offer) {
        String offerId;
        offer.setCreateDate(new Date());
        offer.setScanCount(0);
        offer.setClaimCount(0);
        getCollection().insert(offer);
        log.debug(" object id:"+ offer.getId());
        offerId = offer.getId();
        return offerId;
    }
    
    @Override
    public String editOffer(Offer offer) {
    	String offerId;
		getCollection().update(new ObjectId (offer.getId())).with("{$set: {title: #,description:#,startDate:#,"
				+ "endDate:#,validity:#,limitedQuantity:#,quantity:#}}", offer.getTitle(),
		offer.getDescription(), offer.getStartDate(),offer.getEndDate(), offer.getValidity(),
		offer.isLimitedQuantity(), offer.getQuantity());
				
		log.debug("edited object id:"+ offer.getId());
    	offerId = offer.getId();
    	return offerId;
    }
    
    @Override
    public String updateOffer(Offer offer) {
        String offerId;
        offerId = offer.getId();
        offer.setUpdateDate(new Date());
        getCollection().save(offer);
        //update("{_id :#objId }").with(offer);
        return offerId;
    }
    
    @Override
    public Offer findOffer(String offerId) {
       return findOffer(offerId, DEFAULT_SCAN_DETAIL_LIMIT);
    }
     public Offer findOffer(String offerId, int scanDetailLimit) {
         Offer offer= getCollection().findOne(new ObjectId (offerId)).projection("{ title :1, description: 1, startDate: 1, endDate:1, scanCount: 1, deleted: 1, quantity: 1, validity: 1,locationId:1, scannedOffers : { $slice: # }}",scanDetailLimit).as(Offer.class);
       return offer;
     }
    
    @Override
    public ScannedUserOffer publishOffer(Offer offer, String userId, String publisherId, Date scanDate) {
       
        ScannedUserOffer updatedOffer = null;
        Offer offerObj = null;
        try {
            log.debug("Publishing offer Id: " + offer.getId() + "for business :"+ offer.getBusinessId() +" to user :" + userId + " by Publisher " + publisherId);
            if ( offer.isLimitedQuantity()) {
                log.debug(" publishing limited quantity offer ");
                offerObj = getCollection().findAndModify("{ _id: # , quantity : { $gt: 0 } } ",new ObjectId(offer.getId())).
                    with (" { $push : { scannedOffers: { claimed : #, userId : #, promoterId: #, scanDate:#, scanIndex: # , deleted : #}  }, $inc: {quantity : -1 , scanCount : 1 }  }",false, userId, publisherId, scanDate, offer.getScanCount(),false ).returnNew().as (Offer.class);
            } else {
                offerObj = getCollection().findAndModify("{ _id: # } ",new ObjectId(offer.getId())).
                    with (" { $push : { scannedOffers: { claimed : #, userId : #, promoterId: #, scanDate:#, scanIndex : #, deleted : # }  }, $inc:{ scanCount : 1}  }",false, userId, publisherId, scanDate, offer.getScanCount(),false ).returnNew().as(Offer.class);
            }
            if ( offerObj != null ) {
                updatedOffer = offerObj.getScannedUserOfferObj();
                
             }
        } catch ( Exception ex) {
            log.error("Exception publising offer: " + ex.getMessage());
        }
         return updatedOffer;
    }
}

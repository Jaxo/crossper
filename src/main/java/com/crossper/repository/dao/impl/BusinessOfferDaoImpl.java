/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao.impl;

import com.crossper.models.Business;
import com.crossper.models.ClaimedOfferCount;
import com.crossper.models.Offer;
import com.crossper.models.PromotedOfferCount;
import com.crossper.models.ScannedOfferCount;
import com.crossper.models.ScannedUserOffer;
import com.crossper.repository.dao.BusinessOfferDao;
import com.crossper.representations.DashboardStatRepresentation;
import com.crossper.representations.OfferRepresentation;
import com.crossper.representations.OfferStatRepresentation;
import static com.crossper.representations.OfferStatRepresentation.STAT_PERIOD.NINETY_DAYS;
import static com.crossper.representations.OfferStatRepresentation.STAT_PERIOD.THIRTY_DAYS;
import static com.crossper.representations.OfferStatRepresentation.STAT_PERIOD.YEARLY;
import com.crossper.representations.OfferTrendRepresentation;
import com.crossper.representations.StringList;
import com.crossper.utils.DateHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.jongo.Aggregate;
import org.jongo.Jongo;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

/**
 * APIs to get offers created by a business, provide stats for dashboard and edit operation
 * 
 */
public class BusinessOfferDaoImpl  extends OfferDaoImpl  implements BusinessOfferDao {
  
     static Logger log = Logger.getLogger(BusinessOfferDaoImpl.class);
     public static final String BUSINESS_MATCH ="{ $match : { businessId : # }}";         
     public static final String BUSINESS_AND_END_DATE= "{businessId : # , $or: [ {endDate: { $exists: false }},{endDate : { $exists: true, $gte : # }}] }";
     public static final String OFFER_UNWIND = "{$unwind: '$scannedOffers' }";
     public static final String GROUP_BY="{$group : {_id: \"$scannedOffers.promoterId\", scanByPublisherCount: {$sum :1}}}, {$sort : {\"scanByPublisherCount\": -1}}, {$limit: 5}";
     public static final String PUBLISHER_PROJECTION =" { $project : {_id : 0, items: \"$scannedOffers.promoterId\" } }";
     public static final String OFFER_END_DATE_VALID = " {$match: { $or: [ {endDate: { $exists: false }},{endDate : { $exists: true, $gte : # }}] }}";
     public static final String TOTAL_SCANNED_COUNT="{$match: {businessId :    #, \"scannedOffers.scanDate\": {$exists : true, $gte : #}}}";
     public static final String TOTAL_SCANNED_COUNT_IN_DATE_RANGE ="{$match: {businessId :    #, \"scannedOffers.scanDate\": {$exists : true, $gte : #, $lt : #}}}";
     public static final String GROUP_BY_SCANNED= "{$group : {_id: \"$businessId\", scanCount: {$sum :1}}}";
     public static final String TOTAL_CLAIMED_COUNT="{$match: {businessId :    #, \"scannedOffers.claimDate\": {$exists : true, $gte : #}}}";
      public static final String TOTAL_CLAIMED_COUNT_IN_DATE_RANGE="{$match: {businessId :    #, \"scannedOffers.claimDate\": {$exists : true, $gte : #, $lt: #}}}";
     public static final String GROUP_BY_CLAIMED= "{$group : {_id: \"$businessId\", claimCount: {$sum :1}}}";
     public static final String TOTAL_PROMOTED_COUNT = "{$match: {\"scannedOffers.scanDate\": {$exists : true, $gte : #}, \"scannedOffers.promoterId\": #}}";
     public static final String TOTAL_PROMOTED_COUNT_IN_DATE_RANGE = "{$match: {\"scannedOffers.scanDate\": {$exists : true, $gte : #, $lt : #}, \"scannedOffers.promoterId\": #}}";
     public static final String GROUP_BY_OFFER="{$group : {_id: {offerId: \"$_id\", promoterId: \"$scannedOffers.promoterId\" }, promotionCount: {$sum :1}}}";
     public static final String GROUP_BY_PROMOTER="{$group: {_id: \"$_id.promoterId\", totalPromoted: {$sum: \"$promotionCount\"}}}";
     public static final String PROJECTION_FOR_TOP_OFFERS= " {_id : 1, title :1, description: 1, scanCount:1, claimCount: 1 ,startDate:1,endDate:1, scannedOffers : { $slice: -2 }}";
      //public static final String PROJECTION_FOR_TOP_OFFERS= " { $project : {_id : 1, title :1, description: 1, scanCount:1, claimCount: 1 } }, {$sort : {claimCount : -1 }}";
     public BusinessOfferDaoImpl(Jongo jongo) {
        super(jongo);
    }
    @Override
    public List<ScannedUserOffer> getClaimedOffers(String bizId)  {
        List<ScannedUserOffer> claimedOffers = null;
        return claimedOffers;
    }
    @Override
     public List<ScannedUserOffer> getScannedOffers(String bizId) {
         List<ScannedUserOffer> scannedOffers = null;
         return scannedOffers;
     }
    @Override
     public boolean updateOffer(String offerId) {
         return true;
     }
    @Override
     public boolean deleteOffer(String offerId) {
         return true;
     }
    
    @Override
     public List<Offer> getCurrentOffers( String bizId) {
         List<Offer> currentOffers = new ArrayList<>();
         Iterable<Offer> offerItr;
         Date currDate = new Date();
         offerItr = getCollection().find(BUSINESS_AND_END_DATE,  bizId,currDate).sort("{claimCount : -1 }").limit(5).projection(PROJECTION_FOR_TOP_OFFERS).as(Offer.class);
         
         if ( offerItr != null) {
             Iterator<Offer> itr = offerItr.iterator();
             while (itr.hasNext()) {
                 currentOffers.add(itr.next());
             }
         }
         return currentOffers;
     }
    
    @Override
     public List<ScannedUserOffer> getPublishedOffers(String bizId) {
         List<ScannedUserOffer> publishedOffers = null;
         return publishedOffers;
     }
    
     @Override
     public List<String> getPublishers(String bizId) {
         List<String> publishers = new ArrayList<String>();
         Iterable<StringList> publisherIdList = getCollection().aggregate(BUSINESS_MATCH,  bizId).and (OFFER_UNWIND).and (GROUP_BY)
                 .as(StringList.class);
         Iterator<StringList> listItr = publisherIdList.iterator();
         while ( listItr.hasNext()) {
             List<String> idList = listItr.next().getItems();
             Iterator<String> idItr = idList.iterator();
             while ( idItr.hasNext()) {
                 String id = idItr.next();
                 publishers.add(id);
             }
         }
         return publishers;
     }
     
     @Override
     public int getScannedOfferCount(String businessId, OfferStatRepresentation.STAT_PERIOD period) {
         
         Date fromDate = getStartDateForPeriod(period);
         int scanCount = 0;
         List<ScannedOfferCount> result = getCollection().aggregate(TOTAL_SCANNED_COUNT, businessId, fromDate).and(OFFER_UNWIND)
                 .and(GROUP_BY_SCANNED).as(ScannedOfferCount.class);
         if ( ! result.isEmpty())
            scanCount= result.get(0).getScanCount();
         log.debug("Total Scanned offers of business: "+ businessId + " : for period : " + period + " is : " + scanCount);
         return scanCount;
    }
      public int getScannedOfferCountForRange(String businessId, Date fromDate, Date toDate) {
         
         int scanCount = 0;
         List<ScannedOfferCount> result = getCollection().aggregate(TOTAL_SCANNED_COUNT_IN_DATE_RANGE, businessId, fromDate, toDate).and(OFFER_UNWIND)
                 .and(GROUP_BY_SCANNED).as(ScannedOfferCount.class);
         if ( ! result.isEmpty())
            scanCount= result.get(0).getScanCount();
         log.debug("Total Scanned offers of business: "+ businessId + " : fromDate : " + fromDate.toString() + " to Date : " +
                 toDate.toString() + " is : " + scanCount);
         return scanCount;
    }
    @Override
    public int getClaimedOfferCount(String businessId, OfferStatRepresentation.STAT_PERIOD period) {
      
       int claimCount = 0;
       Date fromDate = getStartDateForPeriod(period);
       List<ClaimedOfferCount> result = getCollection().aggregate(TOTAL_CLAIMED_COUNT, businessId, fromDate).and(OFFER_UNWIND)
                 .and(GROUP_BY_CLAIMED).as(ClaimedOfferCount.class);
       if ( ! result.isEmpty()) {        
            claimCount = result.get(0).getClaimCount();          
       }
       log.debug("Total Claimed offers for business: "+ businessId + " : for periond : " + period + " is : " + claimCount);
       return claimCount;
    }
    public int getClaimedOfferCountForRange(String businessId, Date fromDate, Date toDate) {
      
       int claimCount = 0;
       List<ClaimedOfferCount> result = getCollection().aggregate(TOTAL_CLAIMED_COUNT_IN_DATE_RANGE, businessId, fromDate, toDate).and(OFFER_UNWIND)
                 .and(GROUP_BY_CLAIMED).as(ClaimedOfferCount.class);
       if ( ! result.isEmpty()) {        
            claimCount = result.get(0).getClaimCount();          
       }
       log.debug("Total Claimed offers for business: "+ businessId + " : from date " + fromDate.toString() + 
                "to Date " + toDate.toString() + " is : " + claimCount);
       return claimCount;
    }
    @Override
    public int getPromotedOfferCount(String businessId, OfferStatRepresentation.STAT_PERIOD period){
        
        Date fromDate = getStartDateForPeriod(period);
        int promotedCount = 0;
         List<PromotedOfferCount> result = getCollection().aggregate(OFFER_UNWIND).and(TOTAL_PROMOTED_COUNT, fromDate, businessId)
                 .and(GROUP_BY_OFFER).and(GROUP_BY_PROMOTER).as(PromotedOfferCount.class);
        if ( ! result.isEmpty())
            promotedCount = result.get(0).getTotalPromoted();
        log.debug("Total promoted offers by business: "+ businessId + " : for period : " + period + " is : " + promotedCount);
        return promotedCount;
    }
    
    public int getPromotedOfferCountForRange(String businessId, Date fromDate, Date toDate){
        
        int promotedCount = 0;
         List<PromotedOfferCount> result = getCollection().aggregate(OFFER_UNWIND).and(TOTAL_PROMOTED_COUNT_IN_DATE_RANGE, fromDate, toDate, businessId)
                 .and(GROUP_BY_OFFER).and(GROUP_BY_PROMOTER).as(PromotedOfferCount.class);
        if ( ! result.isEmpty())
            promotedCount = result.get(0).getTotalPromoted();
        log.debug("Total promoted offers by business: "+ businessId + " : from Date"  +fromDate.toString() +
                "to date : "+ toDate.toString() + "  is : " + promotedCount);
        return promotedCount;
    }
    @Override
    public OfferStatRepresentation getOfferStats(String businessId, OfferStatRepresentation.STAT_PERIOD period) {
        log.debug("Find stats for period : " + period.toString());
        
        OfferStatRepresentation offerStats = new OfferStatRepresentation();
        Date fromDateForTrend = getStartDateForTrend(period);
        Date toDateForTrend = getStartDateForPeriod(period);
        
        int currentTotal = getPromotedOfferCount(businessId,period);
        int previousTotal = getPromotedOfferCountForRange(businessId,fromDateForTrend, toDateForTrend );
        offerStats.setPromotedCount(currentTotal);
        offerStats.setPromotedTrend(getTrendStats(currentTotal, previousTotal));
        
        int currentRedeemTotal = getClaimedOfferCount(businessId, period);
        int previousRedeemTotal = getClaimedOfferCountForRange( businessId, fromDateForTrend, toDateForTrend);
        offerStats.setRedeemCount(currentRedeemTotal);
        offerStats.setRedeemTrend(getTrendStats(currentRedeemTotal, previousRedeemTotal));
        
        int currentScanTotal = getScannedOfferCount(businessId, period);
        int previousScanTotal = getScannedOfferCountForRange(businessId, fromDateForTrend, toDateForTrend);
        offerStats.setScanCount(getScannedOfferCount(businessId, period));
        offerStats.setScanTrend(getTrendStats(currentScanTotal, previousScanTotal));
        return offerStats;
    }
    
    @Override
    public DashboardStatRepresentation getDashBoardStat(String businessId) {
        log.debug( "get Dashboard for businessId  : " + businessId);
        
        DashboardStatRepresentation dashboardStat = new DashboardStatRepresentation();
        OfferStatRepresentation thirtyDayStat = getOfferStats(businessId, OfferStatRepresentation.STAT_PERIOD.THIRTY_DAYS);
        OfferStatRepresentation ninetyDayStat = getOfferStats(businessId, OfferStatRepresentation.STAT_PERIOD.NINETY_DAYS);
        OfferStatRepresentation yearlyStat = getOfferStats(businessId, OfferStatRepresentation.STAT_PERIOD.YEARLY);
        dashboardStat.setThirtyDayStat(thirtyDayStat);
        dashboardStat.setNintyDayStat(ninetyDayStat);
        dashboardStat.setYearlyStat(yearlyStat);
        dashboardStat.setId(businessId);
        
        return dashboardStat;
    }
    
    private OfferTrendRepresentation getTrendStats(int currentTotal, int previousTotal) {
        OfferTrendRepresentation trend = new OfferTrendRepresentation();
        float percentChange;
        if (previousTotal != 0 ) {
            if  (currentTotal > previousTotal) {
                trend.setIsUp(true);
                percentChange = ((currentTotal - previousTotal)/previousTotal ) * 100 ;
            }
            else {
                trend.setIsUp(false);
                percentChange = ((previousTotal - currentTotal) / previousTotal ) *100 ;
            }
        } else {
            percentChange = 100;
            trend.setIsUp(true);
        }
        trend.setPercentValue(percentChange);
        return trend;
    }
    
    private Date getStartDateForPeriod(OfferStatRepresentation.STAT_PERIOD period) {
        Date periodStartDate = null;
        Date currentDate = new Date();
        switch (period)  {
            case THIRTY_DAYS:
                periodStartDate =  DateHelper.getDateMonthBefore(currentDate);
                break;
            case NINETY_DAYS :
                periodStartDate = DateHelper.getDateBefore(currentDate, 90);
                break;
            case YEARLY:
                periodStartDate = DateHelper.getDateOneYearBefore(currentDate);
                break;
            default :
                periodStartDate = DateHelper.getDateMonthBefore(currentDate);
                break;
        }
        return periodStartDate;
    }
    /**
     * Returns start date for previous period
     * for month return date 2 months before
     * for 90 day period date 180 days before is returned
     * @param period
     * @return 
     */
     private Date getStartDateForTrend(OfferStatRepresentation.STAT_PERIOD period) {
        Date periodStartDate = null;
        Date currentDate = new Date();
        switch (period)  {
            case THIRTY_DAYS:
                periodStartDate =  DateHelper.getDateMonthsBefore(currentDate, 2);
                break;
            case NINETY_DAYS :
                periodStartDate = DateHelper.getDateBefore(currentDate, 180);
                break;
            case YEARLY:
                periodStartDate = DateHelper.getDateYearsBefore(currentDate, 2);
                break;
            default :
                periodStartDate = DateHelper.getDateMonthBefore(currentDate);
                break;
        }
        return periodStartDate;
    }
}

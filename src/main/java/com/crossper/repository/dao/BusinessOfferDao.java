package com.crossper.repository.dao;

import com.crossper.models.Business;
import com.crossper.models.Offer;
import com.crossper.models.ScannedUserOffer;
import com.crossper.representations.DashboardStatRepresentation;
import com.crossper.representations.OfferStatRepresentation;
import java.util.List;

public interface BusinessOfferDao {
     public List<ScannedUserOffer> getClaimedOffers(String bizId) ;
     public List<ScannedUserOffer> getScannedOffers(String bizId);
     public boolean updateOffer(String offerId);
     public boolean deleteOffer(String offerId);
     public List<Offer> getCurrentOffers( String bizId);
     public List<ScannedUserOffer> getPublishedOffers(String bizId);
     public List<String> getPublishers(String bizId); 
    public int getScannedOfferCount(String businessId, OfferStatRepresentation.STAT_PERIOD period);
    public int getClaimedOfferCount(String businessId, OfferStatRepresentation.STAT_PERIOD periond);
    public int getPromotedOfferCount(String businessId, OfferStatRepresentation.STAT_PERIOD period);
    public OfferStatRepresentation getOfferStats(String businessId, OfferStatRepresentation.STAT_PERIOD period);
    public DashboardStatRepresentation getDashBoardStat(String businessId);
}

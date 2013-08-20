
package com.crossper.repository.dao;

import com.crossper.models.Offer;
import com.crossper.models.ScannedUserOffer;
import com.crossper.representations.OfferSearchFilterRepresentation;
import com.crossper.representations.ScanRepresentation;
import java.util.Iterator;
import java.util.List;


public interface UserOfferDao {
     public List<ScannedUserOffer> getClaimedOffers(String userId) ;
     public List<ScannedUserOffer> getScannedOffers(String userId);
     public List<ScannedUserOffer> downloadOffers( ScanRepresentation scanData, List<Offer> rankedOffers, int maxOffersCount);
     public boolean redeemOffer(String userId, String offerId, int scanIndex);
     public boolean removeOffer(String userId, String offerId, int scanIndex);
     public List<Offer> getOffersForDownload(String userId, String publisherId);
     public List<ScannedUserOffer> getOffers(String userId, String searchCriteria, OfferSearchFilterRepresentation filter, int startIndex, int pageSize);
     public List<ScannedUserOffer> getOffersByCategory(String userId, List<String> categoryBusinessIds, int startIndex, int pageSize);
}

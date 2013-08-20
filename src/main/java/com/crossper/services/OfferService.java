package com.crossper.services;

import com.crossper.exceptions.UnknownPublisherException;
import com.crossper.exceptions.UnregisteredUserException;
import com.crossper.representations.OfferRepresentation;
import com.crossper.representations.ScanRepresentation;
import com.crossper.representations.ScannedOfferRepresentation;
import java.util.List;

public interface OfferService {
    public List<ScannedOfferRepresentation> downloadOffers(ScanRepresentation scan) 
            throws UnregisteredUserException, UnknownPublisherException;
    public boolean redeemOffer(ScannedOfferRepresentation offer);
    public boolean removeOffer(ScannedOfferRepresentation offer);
    public List<ScannedOfferRepresentation> getOffers (String userId);
    public List<ScannedOfferRepresentation> getOffersForCategory (String userId, String category);
    public List<ScannedOfferRepresentation> getOffersForSearchCriteria (String userId, String searchString);
    
}

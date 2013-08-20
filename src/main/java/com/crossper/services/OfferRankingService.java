
package com.crossper.services;

import com.crossper.models.Offer;
import com.crossper.representations.ScanRepresentation;
import java.util.List;

public interface OfferRankingService {
    public List<Offer> getRankedOffersForDownload(ScanRepresentation scan);
}

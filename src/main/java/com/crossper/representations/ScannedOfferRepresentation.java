
package com.crossper.representations;

import com.crossper.models.ScannedOffer;
import com.crossper.models.ScannedUserOffer;
import java.util.Date;

public class ScannedOfferRepresentation  extends OfferRepresentation {
    private String userId;
    private String publisherId;
    private int scanIndex;
    private Date scanDate;
    private Date redeemDate;
    
    public ScannedOfferRepresentation(){}
    
    public ScannedOfferRepresentation( String userId, String publisherId, String offerId, int scanIndex) {
        this.userId = userId; 
        this.publisherId =   publisherId;
        this.id = offerId;
        this.scanIndex = scanIndex;
    }
    public ScannedOfferRepresentation( ScannedUserOffer userOffer) {
        this.userId = userOffer.getScannedOffers().getUserId();
        this.publisherId = userOffer.getScannedOffers().getPromoterId();
        this.id = userOffer.getId();
        this.scanIndex = userOffer.getScannedOffers().getScanIndex();
        this.scanDate = userOffer.getScannedOffers().getScanDate();
        this.redeemDate = userOffer.getScannedOffers().getClaimDate();
    }
    
     public ScannedOfferRepresentation( ScannedOffer scanDetails) {
        this.userId = scanDetails.getUserId();
        this.publisherId = scanDetails.getPromoterId();
        this.id = scanDetails.getId();
        this.scanIndex = scanDetails.getScanIndex();
        this.scanDate = scanDetails.getScanDate();
        this.redeemDate = scanDetails.getClaimDate();
    }
    public String getOfferId() {
        return id;
    }

    public void setOfferId(String offerId) {
        this.id = offerId;
    }

    public int getScanIndex() {
        return scanIndex;
    }

    public void setScanIndex(int scanIndex) {
        this.scanIndex = scanIndex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

   
         
    
}

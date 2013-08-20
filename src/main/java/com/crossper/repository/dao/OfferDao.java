/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao;

import com.crossper.models.Offer;
import com.crossper.models.ScannedUserOffer;
import java.util.Date;
import org.bson.types.ObjectId;

public interface OfferDao {
    
    public String addOffer(Offer offer);
    public String updateOffer(Offer offer) ;
    public Offer findOffer(String offerId);
    public Offer findOffer(String offerId, int scanDetailLimit);
    public ScannedUserOffer publishOffer(Offer offer, String userId, String publihserId, Date scanDate);
    public String editOffer(Offer offer);
        
}

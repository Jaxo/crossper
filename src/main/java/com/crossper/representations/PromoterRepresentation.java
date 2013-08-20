/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.representations;

/**
 * 
 *  Details of promoter/publisher for a business
 */
public class PromoterRepresentation extends BusinessRepresentation{
    private int promotedOffersCount;

    public int getPromotedOffersCount() {
        return promotedOffersCount;
    }

    public void setPromotedOffersCount(int promotedOffersCount) {
        this.promotedOffersCount = promotedOffersCount;
    }
    
    
}

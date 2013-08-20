/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.models;

/**
 *
 * @author Shubhda
 */
public class ClaimedOfferCount extends OfferCount{
    private int claimCount;

    public ClaimedOfferCount() {
    }

    public int getClaimCount() {
        return claimCount;
    }

    public void setClaimCount(int claimCount) {
        this.claimCount = claimCount;
    }
    
}

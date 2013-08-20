/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.representations;

/**
 *
 * Offer statistics for the dashboard
 */
public class OfferStatRepresentation {
    
    int scanCount;
    int redeemCount;
    int promotedCount;
    
    OfferTrendRepresentation scanTrend;
    OfferTrendRepresentation redeemTrend;
    OfferTrendRepresentation promotedTrend;
    
    public enum STAT_PERIOD { THIRTY_DAYS, NINETY_DAYS, YEARLY };
    
    public int getScanCount() {
        return scanCount;
    }

    public void setScanCount(int scanCount) {
        this.scanCount = scanCount;
    }

    public int getRedeemCount() {
        return redeemCount;
    }

    public void setRedeemCount(int redeemCount) {
        this.redeemCount = redeemCount;
    }

    public int getPromotedCount() {
        return promotedCount;
    }

    public void setPromotedCount(int promotedCount) {
        this.promotedCount = promotedCount;
    }

    public OfferTrendRepresentation getScanTrend() {
        return scanTrend;
    }

    public void setScanTrend(OfferTrendRepresentation scanTrend) {
        this.scanTrend = scanTrend;
    }

    public OfferTrendRepresentation getRedeemTrend() {
        return redeemTrend;
    }

    public void setRedeemTrend(OfferTrendRepresentation redeemTrend) {
        this.redeemTrend = redeemTrend;
    }

    public OfferTrendRepresentation getPromotedTrend() {
        return promotedTrend;
    }

    public void setPromotedTrend(OfferTrendRepresentation promotedTrend) {
        this.promotedTrend = promotedTrend;
    }
    
    
}

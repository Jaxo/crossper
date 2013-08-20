/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.representations;

import java.util.List;

public class BusinessOfferRepresentation extends OfferRepresentation {
    private int scanCount;
    private int redeemCount;
    private List<ScannedOfferRepresentation> scanList;

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

    public List<ScannedOfferRepresentation> getScanList() {
        return scanList;
    }

    public void setScanList(List<ScannedOfferRepresentation> scanList) {
        this.scanList = scanList;
    }
    
    
}

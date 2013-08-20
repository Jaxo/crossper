package com.crossper.models;


import java.util.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


public class ScannedOffer extends Offer {

	private String promoterId;
	private String userId;
	private Date scanDate;
        
        private Date claimDate;
        @JsonProperty ("claimed")
        private boolean claimed;
        private boolean deleted;
        private int scanIndex;
    public String getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(String promoterId) {
        this.promoterId = promoterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }
    
    public boolean isLimitedQuantity() {
        return limitedQuantity;
    }

    @Override
    @JsonIgnore
    public void setLimitedQuantity(boolean limitedQuantity) {
        this.limitedQuantity = limitedQuantity;
    }

    public int getQuantity() {
        return quantity;
    }
    
     @Override
    @JsonIgnore
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getScanIndex() {
        return scanIndex;
    }

    public void setScanIndex(int scanIndex) {
        this.scanIndex = scanIndex;
    }
     
     
    
}

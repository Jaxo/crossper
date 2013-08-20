/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.models;

import java.util.Date;

/**
 *
 * @author Shubhda
 */
public class ScanDetails {
        private String promoterId;
	private String userId;
	private Date scanDate;
        private Date claimDate;
        private boolean claimed;
        private Date deleteDate;
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

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
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

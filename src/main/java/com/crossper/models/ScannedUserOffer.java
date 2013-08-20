/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.models;

import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

public class ScannedUserOffer {
        @Id @ObjectId
	private String id;
	private String businessId;
	private String title;
	private String description;
        private String validity;
        
	private Date startDate;
	private Date endDate;
	boolean limitedQuantity;
	int quantity;
        private String locationId;
        @JsonProperty ("scannedOffers")
        private ScanDetails scannedOffers;
        
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isLimitedQuantity() {
        return limitedQuantity;
    }

    public void setLimitedQuantity(boolean limitedQuantity) {
        this.limitedQuantity = limitedQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ScanDetails getScannedOffers() {
        return scannedOffers;
    }

    public void setScannedOffers(ScanDetails scannedOffers) {
        this.scannedOffers = scannedOffers;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
    
    
}

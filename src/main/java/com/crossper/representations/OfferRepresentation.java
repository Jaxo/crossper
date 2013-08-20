package com.crossper.representations;

import com.crossper.models.GeoLocation;
import java.util.Date;

public class OfferRepresentation {
        protected String id;
	protected String businessId;
	protected String title;
	protected String description;
        
	protected Date startDate;
	protected Date endDate;
	protected boolean limitedQuantity;
	protected int quantity;
        protected String validity;
        protected OfferBusinessRepresentation businessInfo;
        protected String locationId;
        
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

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public OfferBusinessRepresentation getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(OfferBusinessRepresentation businessInfo) {
        this.businessInfo = businessInfo;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
    
    
}

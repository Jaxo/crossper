package com.crossper.models;

import java.util.*;

import org.jongo.marshall.jackson.oid.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Offer extends AuditBase {
    @ObjectId
	private String _id;
    @JsonIgnore
    private Business business;
//	private String businessId;
	private String title;
	private String description;
        
	private Date startDate;
	private Date endDate;
	boolean limitedQuantity;
	int quantity;
        int scanCount;
        int claimCount;
        private String validity;
        private String locationId;
        @JsonProperty ("scannedOffers")
        private List<ScannedOffer> scannedOffers;
        public enum OFFER_VALIDITY {EVERY_DAY, WEEK_DAYS_ONLY, WEEK_ENDS_ONLY};
        
    public Offer(){
    	this.business=new Business();
    }
    
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    @JsonProperty("businessId")
    public String getBusinessId() {
        return business.getId();
    }

    public void setBusinessId(String businessId) {
        this.business.setId(businessId);
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

    public List<ScannedOffer> getScannedOffers() {
        return scannedOffers;
    }

    public void setScannedOffers(List<ScannedOffer> scannedOffers) {
        this.scannedOffers = scannedOffers;
    }
 
    public void addScannedOffer(ScannedOffer scannedoffer) {
        if (scannedOffers == null )
            scannedOffers = new ArrayList<ScannedOffer>();
        this.scannedOffers.add(scannedoffer);
        
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public int getScanCount() {
        return scanCount;
    }

    public void setScanCount(int scanCount) {
        this.scanCount = scanCount;
    }

    public int getClaimCount() {
        return claimCount;
    }

    public void setClaimCount(int claimCount) {
        this.claimCount = claimCount;
    }
    
    @JsonIgnore
    public Business getBusiness() {
		return business;
	}

    @JsonIgnore
	public void setBusiness(Business business) {
		this.business = business;
	}

	@JsonIgnore
    public ScannedUserOffer getScannedUserOfferObj() {
        ScannedUserOffer scannedUserOffer = new ScannedUserOffer();
        scannedUserOffer.setBusinessId(this.getBusinessId());
        scannedUserOffer.setDescription(this.description);
        if (this.getScannedOffers() != null ) {
            Iterator<ScannedOffer> itr = getScannedOffers().iterator();
            ScannedOffer scan = itr.next();
            ScanDetails scanDetails = new ScanDetails();
            scanDetails.setClaimDate(scan.getClaimDate());
            scanDetails.setClaimed(scan.isClaimed());
            scanDetails.setDeleteDate(scan.getDeleteDate());
            scanDetails.setDeleted(scan.isDeleted());
            scanDetails.setUserId(scan.getUserId());
            scanDetails.setScanIndex(scan.getScanIndex());
            scanDetails.setScanDate(scan.getScanDate());
            scanDetails.setPromoterId(scan.getPromoterId());
            scannedUserOffer.setScannedOffers(scanDetails);
        }
      
        return scannedUserOffer;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
    
}

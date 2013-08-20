
package com.crossper.representations;

import com.crossper.models.Business;
import com.crossper.models.GeoLocation;

/**
 * 
 * Details of business for an offer
 */
public class OfferBusinessRepresentation {
        protected String category;
        protected String subcategory;
        protected String address;
        protected GeoLocation latLan;   
        protected String rating;
        protected String name;
        public OfferBusinessRepresentation() { }
        
        public OfferBusinessRepresentation(Business biz) {
            this.setCategory(biz.getCategory());
            this.setAddress(biz.getBusinessAddress());
            this.setSubcategory(biz.getSubCategory());
            this.setLatLan(biz.getPrimaryGeoLocation());
            this.setName(biz.getName());
        }
        public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoLocation getLatLan() {
        return latLan;
    }

    public void setLatLan(GeoLocation latLan) {
        this.latLan = latLan;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
        
    
}

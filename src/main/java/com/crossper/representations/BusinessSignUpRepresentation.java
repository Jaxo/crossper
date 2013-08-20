package com.crossper.representations;

/**
 * 
 * @author ddhanawade
 * Date : 26-Jun-2013
 */
public class BusinessSignUpRepresentation {

    private String id;
	private BusinessRepresentation businessInfo;
	private OfferRepresentation offer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BusinessRepresentation getBusinessInfo() {
		return businessInfo;
	}
	public void setBusinessInfo(BusinessRepresentation businessInfo) {
		this.businessInfo = businessInfo;
	}
	public OfferRepresentation getOffer() {
		return offer;
	}
	public void setOffer(OfferRepresentation offer) {
		this.offer = offer;
	}
	
	
}

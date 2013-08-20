
package com.crossper.representations;

import java.util.List;

/**
 * Placeholder for locationId and bizId filter for offer
 * 
 */
public class OfferSearchFilterRepresentation {
    private List<String> locationIdList;
    private List<String> bizIdList;

    public OfferSearchFilterRepresentation(List<String> locationIdList, List<String> bizIdList) {
        this.locationIdList = locationIdList;
        this.bizIdList = bizIdList;
    }

    public List<String> getLocationIdList() {
        return locationIdList;
    }

    public void setLocationIdList(List<String> locationIdList) {
        this.locationIdList = locationIdList;
    }

    public List<String> getBizIdList() {
        return bizIdList;
    }

    public void setBizIdList(List<String> bizIdList) {
        this.bizIdList = bizIdList;
    }
    
}

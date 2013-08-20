
package com.crossper.models;

import org.jongo.marshall.jackson.oid.ObjectId;

public class LocationItem {
    @ObjectId
    private String locationId;
    public LocationItem() {}

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
     
}

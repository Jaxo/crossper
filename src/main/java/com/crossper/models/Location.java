package com.crossper.models;

import java.util.List;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

public class Location {
        @Id
        @ObjectId
	private String id;
	private String street;
        private String city;
	private String state;
	private String zip;
	private GeoLocation geoLocation;
	private List<Phone> phoneNumbers;
    
    public Location() {
        //TODO move to other method? newLocation
        id = new org.bson.types.ObjectId().toString();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public List<Phone> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<Phone> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getCity() {
        if (null == city)
            return " ";
        else
            return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
        
}

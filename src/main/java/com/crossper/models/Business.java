package com.crossper.models;

import java.util.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

public class Business extends AuditBase {
    @Id
    @ObjectId
    private String id;
    private String name;
    private String email;
    private String contactPhone;
    private List types;
    private List<Location> locations;
    private int priceLevel;
    private int rating;
    private String icon;
    private String userId;
    private boolean emailAlert;
    private int offerQuota;
    private String website;
    private String category;
    private String subCategory;
    private int locationCount;
    private String activationCode;
    private String activationStatus;
    
    public enum STATUS {unverified, verified};
    public Business() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailAlert() {
        return emailAlert;
    }

    public void setEmailAlert(boolean emailAlert) {
        this.emailAlert = emailAlert;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Location> getLocations() {
        if (locations == null) {
            locations = new ArrayList<Location>();
        }
        return locations;
    }

    public void setLocations(List<Location> locations) {
        if (locations != null) {
            this.locations = locations;
            //this.locationCount = locations.size();
        }
    }

    @JsonIgnore
    public void addLocation(Location location) {
        locationCount++;
        //location.setId(locationCount);
        getLocations().add(location);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List getTypes() {
        return types;
    }

    public void setTypes(List types) {
        this.types = types;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOfferQuota() {
        return offerQuota;
    }

    public void setOfferQuota(int offerQuota) {
        this.offerQuota = offerQuota;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public int getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(int locationCount) {
        this.locationCount = locationCount;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(String activationStatus) {
        this.activationStatus = activationStatus;
    }
    
    
    @JsonIgnore
    public String getBusinessAddress() {
        String address = null;
        Location loc = getLocations().get(0);
        if (loc != null) {
            address = loc.getStreet() + " " + loc.getCity() +" "+ loc.getState() + "  " + loc.getZip();
        }
        return address;
    }

    @JsonIgnore
    public void setPrimaryGeoLocation(GoogleLocation googleLoc) {
        Location loc = getLocations().get(0);
        if (loc != null) {
            loc.setGeoLocation(new GeoLocation(googleLoc.location.lat, googleLoc.location.lng));
            getLocations().set(0, loc);
        }
    }

    @JsonIgnore
    public GeoLocation getPrimaryGeoLocation() {
        Location loc = getLocations().get(0);
        if (loc != null) {    
            return loc.getGeoLocation();
        }
        else 
            return null;
    }
    
     @JsonIgnore
    public Location getPrimaryLocation() {
        Location loc = getLocations().get(0);
        if (loc != null) {    
            return loc;
        }
        else 
            return null;
    }
    @JsonIgnore
    public String getQRcodePdfName(int flyerType) {
        String pdfName = "QRcodeFlyer_" + id + "_" + flyerType + ".pdf";
        return pdfName;
    }
    
}

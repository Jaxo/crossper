
package com.crossper.representations;

public class ScanRepresentation {
    protected String userId;
    protected String publisherId;
    protected int location;

    public ScanRepresentation( String userId, String promoterId) {
        this.userId = userId;
        this.publisherId = promoterId;
             
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
    
    
}


package com.crossper.representations;

/**
 * 
 * Contact details to show on Contact Page
 */
public class ContactDetailsRepresentation {
    private String contactEmail;
    private String contactPhone;
    
    private String senderEmail;
    private String senderName;
    private String senderMessage;

    public ContactDetailsRepresentation() {
        senderEmail= "";
        senderName="";
        senderMessage="";
    }
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
    }
    
    
    
}

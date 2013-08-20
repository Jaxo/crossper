package com.crossper.models;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: dpol
 * Date: 7/11/13
 * Time: 1:50 PM
 */
public class BusinessProfileImage {
    @Id
    @ObjectId
    private String id;
    private String businessId;
    private String fileLocation;
    private byte [] fileContent;

    public BusinessProfileImage(){}

    public BusinessProfileImage(String businessId, String fileLocation, byte [] fileContent){
        this.businessId = businessId;
        this.fileLocation = fileLocation;
        this.fileContent = fileContent;
    }

    public BusinessProfileImage(String businessId, String fileLocation, InputStream fileInputStream) throws IOException {
        this.businessId = businessId;
        this.fileLocation = fileLocation;
        this.fileContent = IOUtils.readFully(fileInputStream, -1, false);
    }

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

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }
}

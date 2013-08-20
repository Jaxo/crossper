/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.representations;


public class ServiceResponse {
    public static final int ERROR = 1;
    public static final int SUCCESS = 0;
    
    private int status;
    private String message;
    
    public ServiceResponse() {
        this.status= SUCCESS;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.representations;

import com.crossper.models.User;

public class UserServiceResponse extends ServiceResponse {

    private User responseObj;
    
    public UserServiceResponse() {
        this.setStatus(SUCCESS);
    }

    public User getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(User responseObj) {
        this.responseObj = responseObj;
    }

    
    
}

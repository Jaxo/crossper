/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.exceptions;


public class UserServiceException extends Exception {
    static final long serialVersionUID = 1L;
    
    public UserServiceException ( String code ) {
        super(code);
    }
}

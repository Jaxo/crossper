/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.exceptions;

public class EmailValidationException extends UserServiceException {
    public EmailValidationException (String code) {
        super(code);
    }
}

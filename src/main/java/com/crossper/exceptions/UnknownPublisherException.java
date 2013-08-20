/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.exceptions;

public class UnknownPublisherException extends OfferServiceException {
     static final long serialVersionUID = 1L;
     
     public UnknownPublisherException(String code) {
         super(code);
     }
}

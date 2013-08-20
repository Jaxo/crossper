
package com.crossper.exceptions;

public class UnregisteredUserException extends OfferServiceException {
     static final long serialVersionUID = 1L;
     
     public UnregisteredUserException (String code) {
         super(code);
     }
}

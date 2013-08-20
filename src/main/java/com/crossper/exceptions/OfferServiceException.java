
package com.crossper.exceptions;
/**
 * Base class for Exception thrown from offer Service
 * 
 */
public class OfferServiceException extends Exception {
    public OfferServiceException ( String code) {
        super(code);
    }
}

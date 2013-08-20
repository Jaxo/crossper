/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.exceptions;

/**
 *
 * Exceptions from  businessService
 */
public class BizServiceException extends Exception {
     static final long serialVersionUID = 1L;
    
     public enum ExceptionCode {
    	DUPLICATE_EMAIL;
    }
     private ExceptionCode errorCode;
    public BizServiceException ( String code ) {
        super(code);
    }
    public BizServiceException ( ) {
        super();
    }
    
    public BizServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public BizServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public BizServiceException(Throwable cause) {
		super(cause);
        }
        
        public BizServiceException(ExceptionCode errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	public ExceptionCode getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(ExceptionCode errorCode) {
		this.errorCode = errorCode;
	}
}

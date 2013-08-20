/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.exceptions;
public class BizRegistrationException extends BizServiceException {
     static final long serialVersionUID = 1L;
       
	public BizRegistrationException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BizRegistrationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BizRegistrationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BizRegistrationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BizRegistrationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
         public BizRegistrationException(ExceptionCode errorCode) {
		super(errorCode);
	}
    
}

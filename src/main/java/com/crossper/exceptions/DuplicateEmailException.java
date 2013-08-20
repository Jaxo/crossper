
package com.crossper.exceptions;

public class DuplicateEmailException extends UserServiceException {
    public DuplicateEmailException (String code) {
        super(code);
    }
}

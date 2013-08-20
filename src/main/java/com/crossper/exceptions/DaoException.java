/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.exceptions;

import org.springframework.dao.DataAccessException;
/**
 *
 * @author Shubhda
 */
public class DaoException extends DataAccessException{ 
    public DaoException (String message ) {
        super(message);
    }
}

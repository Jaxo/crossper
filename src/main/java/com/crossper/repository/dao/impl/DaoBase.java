/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao.impl;

import org.jongo.Jongo;

public class DaoBase {
    protected Jongo jongo;
    
    public DaoBase(Jongo jongo ) {
        this.jongo = jongo;
    }
   
}

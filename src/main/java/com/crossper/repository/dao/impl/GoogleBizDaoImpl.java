/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao.impl;

import com.crossper.models.Offer;
import com.crossper.models.Place;
import com.mongodb.WriteResult;
import org.apache.log4j.Logger;
import org.jongo.Jongo;

public class GoogleBizDaoImpl extends DaoBase {
    static Logger log = Logger.getLogger(GoogleBizDaoImpl.class.getName());
    
    public GoogleBizDaoImpl(Jongo jongo) {
        super(jongo);
    }
    public String addBusiness (Place placeDetail) {
        String bizId = "test";
        jongo.getCollection("googlebiz").insert(placeDetail);
        return bizId;
    }
}

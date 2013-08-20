/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao.impl;

import com.crossper.repository.dao.BusinessStatsDao;
import com.crossper.representations.DashboardStatRepresentation;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class BusinessStatsDaoImpl extends DaoBase implements BusinessStatsDao {
    static Logger log = Logger.getLogger(BusinessStatsDaoImpl.class.getName());
    static final String BUSINESS_STATS_COLLECTION = "businessStatistics";

    protected MongoCollection stats;
    public BusinessStatsDaoImpl(Jongo jongo) {
        super(jongo);
    }
    
    public MongoCollection getCollection() {
        if (this.stats == null)
            this.stats = jongo.getCollection(BUSINESS_STATS_COLLECTION);
        return stats;
    }
    @Override
    public void saveStats(DashboardStatRepresentation stats) {
        try {
            getCollection().save(stats);
        }catch ( Exception ex) {
            log.error("Error saving business statistics : " + ex.getMessage());
        }
    }

    @Override
    public DashboardStatRepresentation getStats(String businessId) {
       DashboardStatRepresentation dashboard = null;
       dashboard  = getCollection().findOne(new ObjectId (businessId)).as(DashboardStatRepresentation.class);
       return dashboard;
    }
    
}

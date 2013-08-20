/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.services.impl;

import com.crossper.repository.dao.BusinessDao;
import com.crossper.repository.dao.BusinessOfferDao;
import com.crossper.repository.dao.BusinessStatsDao;
import com.crossper.representations.DashboardStatRepresentation;
import com.crossper.services.StatisticsService;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Save offer business statistics
 */
public class StatisticsServiceImpl implements StatisticsService {
    private static final Logger logger = Logger.getLogger(StatisticsServiceImpl.class);
    @Autowired
    private BusinessDao bizDao;
    
    @Autowired
    private BusinessOfferDao bizOfferDao;
    
    @Autowired
    private BusinessStatsDao bizStatsDao;
    
    @Override
    public void saveBusinessOfferStats() {
        //TODO
        //get all businessIds
        List<String> bizIdList = bizDao.getAllBizIds();
        //calculate and save statistics to mongodb
        Iterator<String> idItr = bizIdList.iterator();
        while ( idItr.hasNext()) {
            String bizId = idItr.next();
            try {
                DashboardStatRepresentation stat = bizOfferDao.getDashBoardStat(bizId);
                stat.setCreateDate(new Date());
                bizStatsDao.saveStats(stat);
            }catch (Exception ex) {
                logger.error("Exception saving stats for biz : " + bizId);
            }
        }
    }
    
    @Override
    public DashboardStatRepresentation getDashboardStats( String businessId) {
        DashboardStatRepresentation stats = bizStatsDao.getStats(businessId);
        if ( stats == null ) {
            stats = generateStatForBusiness(businessId);     
        }
        return stats;
    }
    
    private DashboardStatRepresentation generateStatForBusiness(String businessId) {
        DashboardStatRepresentation stat = null;
        try {
                stat = bizOfferDao.getDashBoardStat(businessId);
                stat.setCreateDate(new Date());
                bizStatsDao.saveStats(stat);
            }catch (Exception ex) {
                logger.error("Exception saving stats for biz : " + businessId);
            }
        return stat;
    }
}

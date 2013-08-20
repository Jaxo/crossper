/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.services;

import com.crossper.representations.DashboardStatRepresentation;

/**
 *
 * Service interface to generate and save offer statistics
 */
public interface StatisticsService {
     public void saveBusinessOfferStats();
     public DashboardStatRepresentation getDashboardStats( String businessId);
}

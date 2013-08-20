/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao;

import com.crossper.representations.DashboardStatRepresentation;

/**
 *
 * Interface to access businessStatistics
 */
public interface BusinessStatsDao {
    public void saveStats(DashboardStatRepresentation stats);
    public DashboardStatRepresentation getStats(String businessId);
}

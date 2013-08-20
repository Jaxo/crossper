/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.representations;

import java.util.Date;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

/**
 * Container for offer statistics for the business dashboard
 */
public class DashboardStatRepresentation {
    @Id
    @ObjectId
    private String id;
    private float budget;
    private float remainingBudget;
    private OfferStatRepresentation thirtyDayStat;
    private OfferStatRepresentation nintyDayStat;
    private OfferStatRepresentation yearlyStat;
    private Date createDate;
    
    public String getId() {
        return id;
    }

    public void setId(String businessId) {
        this.id = businessId;
    }
    
    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(float remainigBudget) {
        this.remainingBudget = remainigBudget;
    }

    public OfferStatRepresentation getThirtyDayStat() {
        return thirtyDayStat;
    }

    public void setThirtyDayStat(OfferStatRepresentation thirtyDayStat) {
        this.thirtyDayStat = thirtyDayStat;
    }

    public OfferStatRepresentation getNintyDayStat() {
        return nintyDayStat;
    }

    public void setNintyDayStat(OfferStatRepresentation nintyDayStat) {
        this.nintyDayStat = nintyDayStat;
    }

    public OfferStatRepresentation getYearlyStat() {
        return yearlyStat;
    }

    public void setYearlyStat(OfferStatRepresentation yearlyStat) {
        this.yearlyStat = yearlyStat;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
           
}

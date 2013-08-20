/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.representations;

/**
 *
 * Object representing offer stat trend - % up or down compared to
 * previous average
 */
public class OfferTrendRepresentation {
    private boolean isUp;
    private float percentValue;

    public boolean isIsUp() {
        return isUp;
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }

    public float getPercentValue() {
        return percentValue;
    }

    public void setPercentValue(float percentValue) {
        this.percentValue = percentValue;
    }
    
}

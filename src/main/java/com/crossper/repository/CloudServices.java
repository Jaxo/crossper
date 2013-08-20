/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository;

import com.crossper.models.Business;
import com.crossper.models.GeoLocation;
import com.crossper.models.Place;
import com.crossper.services.AsyncRequestCallback;

public interface CloudServices {
    public GeoLocation getGeoLocation(String streetAddress);
    public void getBusinessDetails(String businessName, String businessLocation, AsyncRequestCallback<Place> callback);
}

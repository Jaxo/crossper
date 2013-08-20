/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.service.test;

import com.crossper.models.Business;
import com.crossper.models.Place;
import com.crossper.repository.GoogleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GoogleServiceTest {
    public GoogleServiceTest() {
        
    }
    @Before
    public void setUp() {
        System.out.println("setUp");
    }

    @After
    public void tearDown() {
        System.out.println("tearDown");
    }

     @Test
     public void getBusinessDetails() throws Exception {
         GoogleService service = new GoogleService("AIzaSyBFTDWnWve1q8r6BtR_B3SvOJP5zah_x3E");
         //Business returnValue = service.getBusinessDetails(name, "berkley");
         //TODO : "Chaat Cafe" : search fails
         //url built should be https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.871610,-122.272996&radius=500&name="Chaat+Cafe"&sensor=false&key=AIzaSyBFTDWnWve1q8r6BtR_B3SvOJP5zah_x3E
         //and NOT : https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.871610,-122.272996&radius=500&name=Chaat+Cafe&sensor=false&key=AIzaSyBFTDWnWve1q8r6BtR_B3SvOJP5zah_x3E
          Place ref = service.getGooglePlaceBasics("Chaat");
          //service.performDetails(ref);
     }
     
     @Test
     public void testGeolocation() throws Exception {
         GoogleService service = new GoogleService("AIzaSyBFTDWnWve1q8r6BtR_B3SvOJP5zah_x3E");
         service.getGeoCode("3601 Powelton Avenune, Philadelphia PA");
     }
}

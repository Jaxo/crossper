/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository;

import com.crossper.models.GeoLocation;
import com.crossper.models.GoogleLocation;
import com.crossper.models.LatLan;
import com.crossper.models.MapDetails;
import com.crossper.models.MapResult;
import com.crossper.models.Place;
import com.crossper.models.PlaceDetail;
import com.crossper.models.PlacesList;
import com.crossper.services.AsyncRequestCallback;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GoogleService implements CloudServices {
    private static final String OK = "OK";
    double latitude = 37.871610;
    double longitude = -122.272996;

    private String placesSearchAPI;
    private String placesDetailsAPI;
    private String geoCodeAPI;
    private String apiKey;

    private static final Logger logger = Logger.getLogger(GoogleService.class);

    public GoogleService(String apiKey) {
        this.apiKey = apiKey;
    }

    private static final HttpTransport transport = new ApacheHttpTransport();
    private static HttpRequestFactory httpRequestFactory;

    static {

        httpRequestFactory = transport.createRequestFactory(new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("applicationName", "Google-Places-Client");
                //headers.setApplicationName("Google-Places-DemoApp");
                request.setHeaders(headers);
                JsonFactory jsonFactory = new JacksonFactory();
                JsonObjectParser parser = new JsonObjectParser(jsonFactory);
                //parser.jsonFactory = new JacksonFactory();
                request.setParser(parser);
            }
        });
    }

    @Override
    public GeoLocation getGeoLocation(String streetAddress) {
        GeoLocation loc = null;
        //TODO call required api
        return loc;
    }

    @Override
    @Async
    public void getBusinessDetails(String businessName, String businessLocation, AsyncRequestCallback<Place> callback) {
        Place place = new Place();
        GeoLocation geoCode = getGeoCode(businessLocation);
        if (geoCode != null) {
            latitude = geoCode.getLat();
            longitude = geoCode.getLng();

            GoogleLocation gLoc = new GoogleLocation();
            gLoc.location = new LatLan();
            gLoc.location.lat = latitude;
            gLoc.location.lng = longitude;
            place.geometry = gLoc;

            Place gPlace = getGooglePlaceBasics(businessName);
            if (gPlace != null) {
                place.price_level = gPlace.price_level;
                place.rating = gPlace.rating;
            }

        }
        callback.success(place);
    }

    public static HttpRequestFactory getHttpRequestFactory(){
        return httpRequestFactory;
    }

    public Place getGooglePlaceBasics(String name) {
        String result;
        Place placeDetails = null;
        try {
            logger.debug("Perform Search ....");
            HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(placesSearchAPI));

            request.setCurlLoggingEnabled(true);
            request.getUrl().put("key", getApiKey());
            request.getUrl().put("location", latitude + "," + longitude);
            request.getUrl().set("query", name);
            request.getUrl().put("radius", 500);
            request.getUrl().put("sensor", "false");
            logger.debug("Url String : " + request.getUrl().toString());

            PlacesList places = request.execute().parseAs(PlacesList.class);
            logger.debug("STATUS = " + places.status);
            if (OK.equals(places.status)) {
                placeDetails = places.results.get(0);
                logger.debug(placeDetails);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return placeDetails;
    }

    public void performDetails(String reference) throws Exception {
        try {
            logger.debug("Perform Place Detail....");
            HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(placesDetailsAPI));
            request.getUrl().put("key", getApiKey());
            request.getUrl().put("reference", reference);
            request.getUrl().put("sensor", "false");

            PlaceDetail place = request.execute().parseAs(PlaceDetail.class);
            logger.debug(place);

        } catch (HttpResponseException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public GeoLocation getGeoCode(String address) {
        GeoLocation geoLoc = null;
        try {
            logger.debug("Perform Place Detail....");
            HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(geoCodeAPI));

            request.setCurlLoggingEnabled(true);
            request.getUrl().put("address", address);
            request.getUrl().put("sensor", "false");

            MapResult mapRes = request.execute().parseAs(MapResult.class);
            if (OK.equals(mapRes.status)) {
                MapDetails detail = mapRes.results.get(0);
                logger.debug(" Lat details :" + detail.geometry.location.lat);
                geoLoc = new GeoLocation(detail.geometry.location.lat, detail.geometry.location.lng);
                geoLoc.setLng(detail.geometry.location.lng);
                geoLoc.setLat(detail.geometry.location.lat);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return geoLoc;
    }

    public String getPlacesSearchAPI() {
        return placesSearchAPI;
    }

    public void setPlacesSearchAPI(String placesAPI) {
        this.placesSearchAPI = placesAPI;
    }

    public String getPlacesDetailsAPI() {
        return placesDetailsAPI;
    }

    public void setPlacesDetailsAPI(String placesDetailsAPI) {
        this.placesDetailsAPI = placesDetailsAPI;
    }

    public String getGeoCodeAPI() {
        return geoCodeAPI;
    }

    public void setGeoCodeAPI(String geoCodeAPI) {
        this.geoCodeAPI = geoCodeAPI;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApikey(String apikey) {
        this.apiKey = apikey;
    }


}

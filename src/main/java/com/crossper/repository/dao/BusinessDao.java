/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao;

import com.crossper.models.Business;
import java.util.List;


public interface BusinessDao {
    public Business addBusiness(Business biz);
    public boolean deleteBusiness( Business biz);
    public Business updateBusiness ( Business biz);
    public Business updateBizGeocodeDetails( Business biz);
    public Business findByEmail( String bizEmail);
    public Business findById(String bizId);
    public boolean isValidBizId ( String bizId);
    void addBusinessPhoto(String businessId, byte[] uploadedInputStream, String location);
    public String getBusinessPhotoLocation(String businessId);
    public byte[] getbusinessPhoto(String businessId);
    public boolean activateBusiness ( String bizEmail, String activationCode);
    public List<String> getBusinessIdsForCategory( String category);
    public List<String> getBusinessLocationIds( String locationName);
    public List<String> getBizIdsForSearchString( String searchString);
    public List<String> getAllBizIds();
}

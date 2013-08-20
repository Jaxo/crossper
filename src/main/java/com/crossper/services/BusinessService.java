/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.services;

import com.crossper.exceptions.BizRegistrationException;
import com.crossper.exceptions.DuplicateEmailException;
import com.crossper.models.Business;
import com.crossper.representations.BusinessCategoryRepresentation;
import com.crossper.representations.BusinessOfferRepresentation;
import com.crossper.representations.BusinessRepresentation;
import com.crossper.representations.BusinessSignUpRepresentation;
import com.crossper.representations.ContactDetailsRepresentation;
import com.crossper.representations.DashboardStatRepresentation;
import com.crossper.representations.OfferRepresentation;
import com.crossper.representations.PromoterRepresentation;
import com.sun.jersey.core.header.FormDataContentDisposition;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface BusinessService {
    public BusinessRepresentation registerBusiness ( BusinessSignUpRepresentation biz) 
            throws DuplicateEmailException, BizRegistrationException ;
    public boolean updateBusiness (BusinessRepresentation biz);
    public OfferRepresentation addBusinessOffer (OfferRepresentation offer);
    public boolean deleteBusinessOffer(String offerId);
    public List<BusinessOfferRepresentation> getBusinessOffers(String bizId);
    public List<BusinessCategoryRepresentation> getCategories();
    public boolean emailQRCode(String businessEmail);
    public Business findByEmail(String email);
    public boolean validateBusinessEmail( String email) throws DuplicateEmailException;
    public File getQRCodePdf(String businessId, int type);
    public Business getBusinessById(String businessId);
    public List<String> getSubcategories (String category);
    void addBusinessPhoto(String businessId, InputStream uploadedInputStream, FormDataContentDisposition fileDetails);
    public byte[] getBusinessPhoto(String businessId);
    public boolean activateBusiness (String businessEmail, String activationCode);
    public boolean sendSupportEmail(ContactDetailsRepresentation contact);
    public OfferRepresentation getBusinessOffer (String bizId, String offerId, int scanDetailLimit);
    public BusinessOfferRepresentation getBusinessOffer (String bizId, String offerId);
    public DashboardStatRepresentation getDashoardStats(String bizId);
    public List<PromoterRepresentation> getPromoters(String bizId);
    public OfferRepresentation editBusinessOffer (OfferRepresentation offer);
}

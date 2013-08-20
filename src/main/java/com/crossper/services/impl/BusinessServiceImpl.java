/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.services.impl;

import com.crossper.exceptions.BizRegistrationException;
import com.crossper.exceptions.BizServiceException;
import com.crossper.exceptions.DuplicateEmailException;
import com.crossper.exceptions.SystemException;
import com.crossper.models.Business;
import com.crossper.models.Location;
import com.crossper.models.Offer;
import com.crossper.models.Place;
import com.crossper.models.ScannedOffer;
import com.crossper.models.User;
import com.crossper.repository.CloudServices;
import com.crossper.repository.dao.BusinessCategoryDao;
import com.crossper.repository.dao.BusinessDao;
import com.crossper.repository.dao.BusinessOfferDao;
import com.crossper.repository.dao.OfferDao;
import com.crossper.repository.dao.UserDao;
import com.crossper.representations.BusinessCategoryRepresentation;
import com.crossper.representations.BusinessOfferRepresentation;
import com.crossper.representations.BusinessRepresentation;
import com.crossper.representations.BusinessSignUpRepresentation;
import com.crossper.representations.ContactDetailsRepresentation;
import com.crossper.representations.DashboardStatRepresentation;
import com.crossper.representations.OfferRepresentation;
import com.crossper.representations.PromoterRepresentation;
import com.crossper.representations.ScannedOfferRepresentation;
import com.crossper.services.AsyncRequestCallback;
import com.crossper.services.BusinessService;
import com.crossper.services.EmailService;
import com.crossper.utils.QRCodeHelper;
import com.crossper.utils.StringHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.jersey.core.header.FormDataContentDisposition;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sun.misc.IOUtils;

public class BusinessServiceImpl implements BusinessService {

    private static final Logger logger = Logger.getLogger(BusinessServiceImpl.class);
    private static final String BUSINESS_PROFILE_IMG = "business-profile-img-";
    @Autowired
    private BusinessDao bizDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private BusinessCategoryDao categoryDao;
    
    @Autowired
    private CloudServices googleService;
    
    @Autowired
    private QRCodeHelper qrCodeHelper;
    
    @Autowired
    private BusinessOfferDao bizOfferDao;
    
    private String qrCodeMessage;
    
    private String activationUrl;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private OfferDao offerDao;

    private String businessImagesFSPath;

    public BusinessRepresentation registerBusiness ( BusinessSignUpRepresentation bizSignup) 
                    throws DuplicateEmailException, BizRegistrationException
    {
        User bizUser = new User();
        BusinessRepresentation biz = bizSignup.getBusinessInfo();
        if ( isEmailUsed(biz.getEmail())){
        	throw new BizRegistrationException(BizServiceException.ExceptionCode.DUPLICATE_EMAIL);
        }
        
            bizUser.setEmail(biz.getEmail());
            bizUser.setPassword(biz.getPassword());
            List<String>roles = new ArrayList<String>();
            roles.add(User.USER_ROLES.ROLE_BUSINESS.toString());
            bizUser.setRoles(roles);
            bizUser = userDao.addUser(bizUser);
        
        Business newBusiness = toBusinessModel(biz);
        newBusiness.setUserId(bizUser.getId());
        newBusiness.setActivationStatus(Business.STATUS.unverified.toString());
        newBusiness.setActivationCode(StringHelper.generateActivationCode());
        try {
            bizDao.addBusiness(newBusiness);
        }catch (Exception ex) {
            logger.error("Save business failed: "+ ex.getMessage());
           // throw new BizRegistrationException("signup.bizInfoFailed");
        }
        try {
            biz.setId(newBusiness.getId());
            bizSignup.getOffer().setBusinessId(newBusiness.getId());
            bizSignup.getOffer().setLocationId(newBusiness.getPrimaryLocation().getId());
            bizSignup.setId(biz.getId());
            addBusinessOffer(bizSignup.getOffer());
            
            generateQRCodeImage(newBusiness.getId());
            logger.debug("Async-in::Fetch and update places details for business");
            updatePlacesDetails(newBusiness);
            logger.debug("Async-out::Fetch and update places details for business");
            logger.debug("Async-in::send QR code email");
            emailQRCodeWithActivation(newBusiness);
            //emailQRcode(newBusiness);
            logger.debug("Async-out::send QR code email");
        }catch (Exception ex) {
            logger.error("Failed while adding offer or qr code generation: "+ ex.getMessage());
        }
        return biz;
    }
    
    @Override
    public boolean updateBusiness (BusinessRepresentation biz) {
        try {
            bizDao.updateBusiness(toBusinessModel(biz));
        }catch(Exception ex) {
            logger.error("Biz update failed: " + ex.getMessage());
        }
        return true;
    }
    
    @Override
    public OfferRepresentation addBusinessOffer (OfferRepresentation offer) {
        
        Offer offerModel = toOfferModel(offer);
        try {
            offerDao.addOffer(offerModel);
            offer.setId(offerModel.getId());
        }catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return offer;
    }
    
    public static Business toBusinessModel( BusinessRepresentation biz) {
        Business bizModel = new Business();
        bizModel.setEmail(biz.getEmail());
        bizModel.setName(biz.getName());
        bizModel.setContactPhone(biz.getPhone());
        bizModel.setWebsite(biz.getWebsite());
        bizModel.setOfferQuota(biz.getOfferQuota());
        bizModel.setCategory(biz.getCategory());
        bizModel.setSubCategory(biz.getSubCategory());
        Location loc = new Location();
        loc.setStreet(biz.getAddress());
        loc.setCity(biz.getCity());
        loc.setState(biz.getState());
        loc.setZip(biz.getZipCode());
        bizModel.addLocation(loc);
        
        return bizModel;
    }
    
    public static Offer toOfferModel( OfferRepresentation offer) {
        Offer offerModel = new Offer();
        offerModel.setId(offer.getId());
        offerModel.setBusinessId(offer.getBusinessId());
        offerModel.setDescription(offer.getDescription());
        offerModel.setTitle(offer.getTitle());
        offerModel.setLimitedQuantity(offer.isLimitedQuantity());
        offerModel.setValidity(offer.getValidity());
        offerModel.setStartDate(offer.getStartDate());
        offerModel.setEndDate(offer.getEndDate());
        offerModel.setQuantity(offer.getQuantity());
        offerModel.setLocationId(offer.getLocationId());
        return offerModel;
    }
    
    @Override
    public List<BusinessCategoryRepresentation> getCategories() {
        return categoryDao.getCategoriesForDisplay();
    }
    
    public List<String> getSubcategories (String category) {
        return categoryDao.getSubcategories(category);
    }

    private void updatePlacesDetails(final Business biz) {
        try {
            googleService.getBusinessDetails(biz.getName(), biz.getBusinessAddress(), new AsyncRequestCallback<Place>() {
                @Override
                public void success(Place details) {
                    if (details != null && details.geometry != null) {
                        biz.setPriceLevel(details.price_level);
                        biz.setRating(details.rating);
                        biz.setPrimaryGeoLocation(details.geometry);
                        bizDao.updateBusiness(biz);
                    }
                }

                @Override
                public void error(Place data) {
                }

                @Override
                public void complete(Place data) {
                }
            });

            
        }catch (Exception ex) {
            logger.error("Exception updating business details: "+ ex.getMessage());
        }
    }
    
    private void generateQRCodeImage(String businessId) {
        String qrCodeUrl = getQrCodeMessage() + businessId;
        qrCodeHelper.createQRCodePdf(businessId, qrCodeUrl, QRCodeHelper.FLYER_TYPES.FLYER_0.ordinal());
    }
    
    public String getQrCodeMessage() {
        return qrCodeMessage;
    }

    public void setQrCodeMessage(String qrCodeMessage) {
        this.qrCodeMessage = qrCodeMessage;
    }
    
    @Override
    public boolean emailQRCode(String businessId) {
        Business biz = findById(businessId); 
        if (biz != null) {
            if ( ! qrCodeHelper.isPdfPresent(biz.getQRcodePdfName(QRCodeHelper.FLYER_TYPES.FLYER_0.ordinal())))
                generateQRCodeImage(biz.getId());
            emailService.sendQRCodeMail(biz.getEmail(), biz.getName(), qrCodeHelper.getQrCodeDir() + biz.getQRcodePdfName(QRCodeHelper.FLYER_TYPES.FLYER_0.ordinal()));
        }
        return true;
    }
    
    private Business findById( String businessId) {
        Business biz = bizDao.findById(businessId);
        return biz;
    }
    @Override
    public Business findByEmail(String email) {
        Business biz = bizDao.findByEmail(email);
        return biz;
    }
    @Override
    public boolean validateBusinessEmail( String email) throws DuplicateEmailException {
        if (isEmailUsed(email) )
                throw new DuplicateEmailException("error.DUPLICATE_EMAIL");
        else
            return true;
    }
    private void emailQRcode(Business biz)  {
        emailService.sendQRCodeMail(biz.getEmail(), biz.getName(), qrCodeHelper.getQrCodeDir() + biz.getQRcodePdfName(QRCodeHelper.FLYER_TYPES.FLYER_0.ordinal()));
    }
    
    private void emailQRCodeWithActivation(Business biz) {
        emailService.sendActivationMail(biz, buildUrlForEmail(biz), qrCodeHelper.getQrCodeDir() + biz.getQRcodePdfName(QRCodeHelper.FLYER_TYPES.FLYER_0.ordinal()));
    }
    private boolean isEmailUsed(String email) {
        boolean isUsed= false;
        if( findByEmail(email) != null)
            isUsed= true;
        else {
            try {
                if (userDao.isRegisteredUser(email))
                    isUsed = true;
                else
                    isUsed = false;
            }catch (Exception ex) {
                isUsed = true;
            }
        }
        return isUsed;
    }
    
    public File getQRCodePdf(String businessId, int type) {
        return qrCodeHelper.getPdfFile(businessId, type);
    }

	public byte[] getBusinessPhoto(String businessId) {
		byte[] docStream = null;

		String location = bizDao.getBusinessPhotoLocation(businessId);
		if (location == null) {
			docStream = bizDao.getbusinessPhoto(businessId);
		} else {
			try {
				File file=new File(location);
				FileInputStream fileInputStream = new FileInputStream(file);
				docStream = new byte[(int) file.length()];
				fileInputStream.read(docStream);
				fileInputStream.close();
			} catch (FileNotFoundException ex) {
				docStream = bizDao.getbusinessPhoto(businessId);
			} catch (IOException ex) {
				logger.error("IOException while getting businessPhoto: "
						+ ex.getMessage());
			}
		}

		return docStream;
	}

	@Override
	public Business getBusinessById(String businessId) {
		return bizDao.findById(businessId);
	}

    public void setBusinessImagesFSPath(String businessImagesFSPath) {
        this.businessImagesFSPath = businessImagesFSPath;
    }

    @Override
    public void addBusinessPhoto(String businessId, InputStream uploadedInputStream, FormDataContentDisposition fileDetails) {
        String location = businessImagesFSPath + BUSINESS_PROFILE_IMG + businessId + fileExtension(fileDetails.getFileName());
        byte [] fileContent;
        try(OutputStream outputStream = new FileOutputStream(location)) {
            fileContent =IOUtils.readFully(uploadedInputStream, -1, false);
            outputStream.write(fileContent);
        } catch (IOException e) {
            logger.error("Exception while writing file to file system, filename: " + fileDetails.getFileName() + " at location "+ location, e);
            throw new SystemException(e);
        }

        bizDao.addBusinessPhoto(businessId, fileContent, location);
    }

    private String fileExtension(String fileName) {
        return "." + fileName.split("\\.(?=[^\\.]+$)")[1];
    }

    public String getActivationUrl() {
        return activationUrl;
    }

    public void setActivationUrl(String activationUrl) {
        this.activationUrl = activationUrl;
    }
    
    private String buildUrlForEmail(Business biz) {
        String url = getActivationUrl()+ biz.getEmail()+ "&activationCode=";
        return url;
    }
    @Override
    public boolean activateBusiness (String businessEmail, String activationCode) {
        boolean returnValue = bizDao.activateBusiness(businessEmail, activationCode);
        return returnValue;
    }
    
    @Override
    public boolean sendSupportEmail(ContactDetailsRepresentation contact) {
        return emailService.sendSupportEmail(contact);
    }
    
    @Override
    public List<BusinessOfferRepresentation> getBusinessOffers(String bizId) {
        List<BusinessOfferRepresentation> offerList = new ArrayList<BusinessOfferRepresentation>();
        List<Offer> bizOffers = bizOfferDao.getCurrentOffers(bizId);
        Iterator<Offer> offerIter = bizOffers.iterator();
        while (offerIter.hasNext()) {
            offerList.add(toOfferRepresentation(offerIter.next()));
        }
        return offerList;
    }
    
    @Override
    public OfferRepresentation editBusinessOffer (OfferRepresentation offer) {
        
        Offer offerModel = toOfferModel(offer);
        try {
            offerDao.editOffer(offerModel);
        }catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return offer;
    }
    
    public OfferRepresentation getBusinessOffer (String bizId, String offerId, int scanDetailLimit) {
        Offer offer = offerDao.findOffer(offerId);
        OfferRepresentation offerRep = toOfferRepresentation(offer);
        return offerRep;
    }
    public BusinessOfferRepresentation getBusinessOffer (String bizId, String offerId) {
        Offer offer = offerDao.findOffer(offerId);
        BusinessOfferRepresentation offerRep = toOfferRepresentation(offer);
        return offerRep;
    }
    
    public DashboardStatRepresentation getDashoardStats(String bizId) {
        DashboardStatRepresentation stats = null;
        stats = bizOfferDao.getDashBoardStat(bizId);
        return stats;
    }
    
    public boolean deleteBusinessOffer(String offerId) {
        bizOfferDao.deleteOffer(offerId);
        return true;
    }
    
    public List<PromoterRepresentation> getPromoters(String bizId) {
        List<PromoterRepresentation> promoters = new ArrayList<PromoterRepresentation>();
        List<String> publisherIdList = bizOfferDao.getPublishers(bizId);
        if ( publisherIdList != null && ! publisherIdList.isEmpty()) {
            Iterator<String> idItr = publisherIdList.iterator();
            while(idItr.hasNext()) {
                Business publisherDetails = bizDao.findById(idItr.next());
                promoters.add(toPromoterRepresentation(publisherDetails));
            }
        }
        return promoters;
    }
    
    public static PromoterRepresentation toPromoterRepresentation( Business biz) {
        PromoterRepresentation bizRep = new PromoterRepresentation ();
        bizRep.setName(biz.getName());
        
        return bizRep;
    }
    
     public static BusinessRepresentation toBusinessRepresentation( Business biz) {
        BusinessRepresentation bizRep = new BusinessRepresentation ();
        bizRep.setName(biz.getName());
        bizRep.setAddress(biz.getBusinessAddress());
        //bizRep.setAveragePrice();
        return bizRep;
    }
    public static BusinessOfferRepresentation toOfferRepresentation( Offer offerModel) {
        BusinessOfferRepresentation offer = new BusinessOfferRepresentation();
        offer.setId(offerModel.getId());
        offer.setBusinessId(offerModel.getBusinessId());
        offer.setDescription(offerModel.getDescription());
        offer.setTitle(offerModel.getTitle());
        offer.setLimitedQuantity(offerModel.isLimitedQuantity());
        offer.setValidity(offerModel.getValidity());
        offer.setStartDate(offerModel.getStartDate());
        offer.setEndDate(offerModel.getEndDate());
        offer.setQuantity(offerModel.getQuantity());
        offer.setLocationId(offerModel.getLocationId());
        offer.setScanCount(offerModel.getScanCount());
        offer.setRedeemCount(offerModel.getClaimCount());
        List<ScannedOffer> scanDetails = offerModel.getScannedOffers();
        List<ScannedOfferRepresentation> scanList = new ArrayList<>();
        if ( scanDetails != null ) {
            Iterator<ScannedOffer> detailItr = scanDetails.iterator();
            while(detailItr.hasNext()) {
                scanList.add(new ScannedOfferRepresentation(detailItr.next()));     
            }
        }
        offer.setScanList(scanList);
        return offer;
    }

}

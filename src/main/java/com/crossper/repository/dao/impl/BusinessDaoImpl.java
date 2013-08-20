/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao.impl;

import com.crossper.models.Business;
import com.crossper.models.BusinessProfileImage;
import com.crossper.models.Location;
import com.crossper.models.LocationItem;
import com.crossper.repository.dao.BusinessDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;


public class BusinessDaoImpl extends DaoBase implements BusinessDao
{
    static Logger log = Logger.getLogger(BusinessDaoImpl.class.getName());
    static final String BUSINESS_COLLECTION = "businesses";

    protected MongoCollection businesses;
    private BusinessDaoImpl.BusinessProfileImageDaoImpl businessProfileImageDao =  new BusinessProfileImageDaoImpl();;

    public BusinessDaoImpl (Jongo jongo) {
        super(jongo);
        this.businesses = jongo.getCollection(BUSINESS_COLLECTION);
    }
    
    public MongoCollection getCollection() {
        if (this.businesses == null)
            this.businesses = jongo.getCollection(BUSINESS_COLLECTION);
        return businesses;
    }
    
    @Override
     public Business addBusiness(Business biz) {
         biz.setCreateDate(new Date());
         getCollection().insert(biz);
         return biz;
     }
    @Override
    public boolean deleteBusiness( Business biz) {
        try {
            biz.setDeleteDate(new Date());
        }catch ( Exception e) {
            
        }
        return true;
    }
    @Override
    public Business updateBusiness ( Business biz) {
        try {
            biz.setUpdateDate(new Date());
            getCollection().save(biz);
        }catch (Exception ex) {
            log.error("Failed to update Business");
        }
        return biz;
               
    }
    @Override
    public Business findByEmail( String bizEmail) {
        Business biz = getCollection().findOne("{email:#}",bizEmail).as(Business.class);
        return biz;
    }
    
    @Override
    public Business findById( String bizId) {
        Business biz = getCollection().findOne("{_id:#}",new ObjectId(bizId)).as(Business.class);
        return biz;
    }
     @Override
     public Business updateBizGeocodeDetails( Business biz) {
         try {
            //biz.setUpdateDate(new Date());
            getCollection().findAndModify("{_id : #}", new ObjectId(biz.getId()) ).with(
                    "{ $set: {updateDate: # , priceLevel : #, rating: # ,locations: # } }", 
                    new Date(),4, 4, biz.getLocations());
            
        }catch (Exception ex) {
            log.error("Failed to update Business");
        }
        return biz;
     }
     
     @Override
    public boolean isValidBizId( String bizId) {
        boolean isValidId = false;
        Business biz = getCollection().findOne("{_id:#}",new ObjectId(bizId)).as(Business.class);
        if ( biz != null)
            isValidId = true;
        return isValidId;
    }

    @Override
    public void addBusinessPhoto(String businessId, byte[] uploadedInputStream, String location) {

        businessProfileImageDao.addBusinessPhoto(businessId, uploadedInputStream, location);
    }
    
    @Override
	public String getBusinessPhotoLocation(String businessId) {
    	
		return businessProfileImageDao.getBusinessPhotoLocation(businessId);
	}
    
    @Override
	public byte[] getbusinessPhoto(String businessId) {
		return businessProfileImageDao.getbusinessPhoto(businessId);
	}
    public boolean activateBusiness ( String bizEmail, String activationCode) {
        boolean isSuccess = false;
        try {
            Business biz = findByEmail(bizEmail);
            if ( biz.getActivationCode().equalsIgnoreCase(activationCode)) {
                biz.setActivationStatus(Business.STATUS.verified.toString());
                updateBusiness(biz);
                isSuccess = true;
            }
            
        }catch (Exception ex) {
            log.error("Error activating business email " + bizEmail + " : "+ ex.getMessage());
        }
        return isSuccess;
    }
    
    @Override
    public List<String> getBusinessIdsForCategory( String category) {
        List<String> bizIdList = new ArrayList<String>();
        List<Business> bizList= getCollection().aggregate(" { $match: { $or : [ {'category' : # }, {'subCategory' : #}]} }", category, category)
                .as(Business.class);
        //TODO : use distinct and get id list from mongo
        Iterator<Business> bizItr = bizList.iterator();
        while ( bizItr.hasNext()) {
            Business biz = bizItr.next();
            bizIdList.add(biz.getId());
        }
        //.and ("{$project : {}");
        return bizIdList;
                
    }
     @Override
    public List<String> getBusinessLocationIds( String locationName) {
        List<String> locationIdList = new ArrayList<String>();
        List<LocationItem> locList= getCollection().aggregate(" { $unwind: '$locations'}").
                and (" {$match: { $or : [ {'locations.street' : { $regex: #, $options: 'i' }},{'locations.state' : { $regex: #, $options: 'i' }},{'locations.zip' : { $regex: #, $options: 'i' }} ]} } }", locationName, locationName, locationName)
                .and("{$project: {'_id' :0, 'locationId' : \"$locations._id\"} }")
                .as(LocationItem.class);
       
        Iterator<LocationItem> locItr = locList.iterator();
        while ( locItr.hasNext()) {
            LocationItem location = locItr.next();
            locationIdList.add(location.getLocationId());
        }
        //.and ("{$project : {}");
        return locationIdList;
                
    }
     
   @Override
    public List<String> getBizIdsForSearchString( String searchString) {
        List<String> bizIdList = new ArrayList<>();
        Iterable<Business> bizList= getCollection().find(" { $or : [ {'category' : # }, {'subCategory' : #}, {name: { $regex: #, $options: 'i' }},{description: { $regex: #, $options: 'i' }} ]}", searchString, searchString,searchString,searchString)
                .as(Business.class);
        //TODO : use distinct and get id list from mongo
        Iterator<Business> bizItr = bizList.iterator();
        while ( bizItr.hasNext()) {
            Business biz = bizItr.next();
            bizIdList.add(biz.getId());
        }
        //.and ("{$project : {}");
        return bizIdList;
                
    }
   @Override
   public List<String> getAllBizIds() {
       List <String> bizIdList = new ArrayList<>();
       Iterable<ObjectId> idList = getCollection().distinct("_id").as(ObjectId.class);
       Iterator<ObjectId> idItr = idList.iterator();
       while(idItr.hasNext()) {
           String idString = idItr.next().toStringMongod();
           log.debug(" String value of id: " + idString);
           bizIdList.add(idString);
       }
       return bizIdList;
   }
    
    public class BusinessProfileImageDaoImpl{
        protected MongoCollection businessImages;
        static final String BUSINESS_PROFILE_IMG_COLLECTION = "businesses_profile_images";

        public MongoCollection getCollection() {
            if (this.businessImages == null)
                this.businessImages = jongo.getCollection(BUSINESS_PROFILE_IMG_COLLECTION);
            return businessImages;
        }

        public void addBusinessPhoto(String businessId, byte[] uploadedInputStream, String location) {

            BusinessProfileImage businessProfileImage = new BusinessProfileImage(businessId, location, uploadedInputStream);
            getCollection().insert(businessProfileImage);
        }
        
        public String getBusinessPhotoLocation(String businessId) {
        	BusinessProfileImage businessPhotoLocation=getCollection().findOne("{businessId:#},{fileLocation=1}",businessId).as(BusinessProfileImage.class);
        	return businessPhotoLocation.getFileLocation();
        }

        public byte[] getbusinessPhoto(String businessId) {
        	BusinessProfileImage businessPhotoLocation=getCollection().findOne("{businessId:#},{fileContent=1}",businessId).as(BusinessProfileImage.class);
        	return businessPhotoLocation.getFileContent();
        }
    }
	
}

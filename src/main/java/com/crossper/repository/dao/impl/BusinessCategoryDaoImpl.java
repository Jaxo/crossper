/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao.impl;

import com.crossper.models.BusinessCategory;
import com.crossper.repository.dao.BusinessCategoryDao;
import com.crossper.representations.BusinessCategoryRepresentation;
import com.crossper.representations.StringList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class BusinessCategoryDaoImpl extends DaoBase  implements BusinessCategoryDao{
    static Logger log = Logger.getLogger(BusinessCategoryDaoImpl.class.getName());
    static final String CATEGORY_COLLECTION = "business_categories";
    
    protected MongoCollection categories;
    private List<BusinessCategoryRepresentation> categoryList;
    public BusinessCategoryDaoImpl (Jongo jongo){
        super(jongo);
    }
   public MongoCollection getCollection() {
        if (this.categories == null)
            this.categories = jongo.getCollection(CATEGORY_COLLECTION);
        return categories;
    }
   @Override
    public List<String> getSubcategories(String category) {
        List<String>subcatList = new ArrayList<String>();
         
        try {
        Iterable<StringList> subcats = getCollection().aggregate("{ $match: {name : #} } ", category)
                .and( "{ $project : { _id : 0, items: \"$subcategories.name\"  } }")
                .as(StringList.class);
        if ( subcats != null && subcats.iterator() != null)  {
            Iterator<StringList> subCatItr = subcats.iterator();
            while (subCatItr.hasNext()) {
                subcatList.addAll(subCatItr.next().getItems());
            }
        }
        }catch (Exception ex) {
            log.error("Exception getting subcategories : "+ ex.getMessage());
        }
        return subcatList;
    }
   @Override
     public List<String> getAffinityCategories(String category, String subcategory) {
        List<String>affinityList = new ArrayList<String>();
        log.debug("Getting affinity categories for category : " + category + "  and subcategory: " + subcategory);
        try {
        /*Iterable<StringList>subcatAffinity = getCollection().aggregate("{ $match : {name : #, subcategories.name : # } }", category, subcategory)
                .and(" {$project : {_id : 0, items : \"$subcategories.affinity_with\"}} ").as(StringList.class); */
        /*Iterable<StringList>subcatAffinity = getCollection().find("{name : #, subcategories.name : # } }", category, subcategory)
                .projection(" {_id : 0, items : \"subcategories.$.affinity_with\"}").as(StringList.class); */
        Iterable<StringList> subcatAffinity = getCollection().aggregate(" { $unwind : '$subcategories' }").
                and("{ $match : {name : #, subcategories.name : # } }", category, subcategory).
                and(" { $project: {_id: 0, items: \"$subcategories.affinity_with\" } }").as(StringList.class);
        if ( subcatAffinity != null && subcatAffinity.iterator() != null ) {
            Iterator<StringList> itemItr = subcatAffinity.iterator();
            while ( itemItr.hasNext()) {
                affinityList.addAll(itemItr.next().getItems());
            }
        }
        Iterable<StringList> affCats = getCollection().aggregate("{$match : { name: # }}", category).and("{ $project: { _id : 0, items :  \"$affinity_with\"} }" ).as(StringList.class);
                
        if ( affCats != null && affCats.iterator() != null)  {
            Iterator<StringList> subCatItr = affCats.iterator();
            while (subCatItr.hasNext()) {
                StringList affCat = subCatItr.next();
                if ( affCat != null )
                    affinityList.addAll(affCat.getItems());
            }
        }
        log.debug("Found affinity categories : " + affinityList.size());
        } catch (Exception ex) {
            log.error("Exception getting affinity category for category " + category + 
                    "  subcategory : "+ subcategory + "  : " + ex.getMessage());
        }
        return affinityList;
     }
     @Override
     public List<BusinessCategoryRepresentation> getCategoriesForDisplay() {
         
         Iterable <BusinessCategoryRepresentation> presents = getCollection().aggregate(" { $project : { name : 1, _id :0, subcategories: \"$subcategories.name\" } }").as(BusinessCategoryRepresentation.class);
        
         if ( categoryList == null || categoryList.isEmpty()) {
            categoryList = new ArrayList<BusinessCategoryRepresentation> ();
            if ( presents != null && presents.iterator() != null ) {
                Iterator<BusinessCategoryRepresentation> itr = presents.iterator();
              while (itr.hasNext()) {
                  BusinessCategoryRepresentation rep = itr.next();
                  categoryList.add(rep);
               }
           }
         }
         return categoryList;
     }
    @Override
     public List<String> getCategories(){
         List<String> names = getCollection().distinct("name").as(String.class);
         return names;
     }
    
    public static BusinessCategoryRepresentation convertToRepresentation(BusinessCategory cat) {
        BusinessCategoryRepresentation obj = new BusinessCategoryRepresentation();
        obj.setName(cat.getName());
        List<String> subCats = new ArrayList<String>();
        if( cat.getSubcategories() != null) {
           Iterator<BusinessCategory> itr = cat.getSubcategories().iterator();
           while (itr.hasNext()) {
               subCats.add(itr.next().getName());
           }
           obj.setSubcategories(subCats);
       }
        return obj;
    }
    
   
}

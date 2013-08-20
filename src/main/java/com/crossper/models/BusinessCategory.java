package com.crossper.models;

import java.util.List;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

public class BusinessCategory extends AuditBase {
       @Id @ObjectId
        String id;
        private String name;
	private List<String> affinityWith;
        private List<BusinessCategory> subcategories;

    public BusinessCategory() {
        
    }
    public List<String> getAffinityWith() {
        return affinityWith;
    }

    public void setAffinityWith(List<String> affinityWith) {
        this.affinityWith = affinityWith;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }

    public List<BusinessCategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<BusinessCategory> subcategories) {
        this.subcategories = subcategories;
    }
    
	

}

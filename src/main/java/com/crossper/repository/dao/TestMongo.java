package com.crossper.repository.dao;

import com.crossper.models.People;
import java.util.Iterator;

import com.fasterxml.jackson.databind.MapperFeature;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;
import org.codehaus.jettison.json.JSONObject;
import org.jongo.FindOne;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;
import org.jongo.marshall.Unmarshaller;
import org.jongo.marshall.jackson.JacksonEngine;
import org.jongo.marshall.jackson.JacksonMapper;
import org.jongo.marshall.jackson.configuration.AbstractMappingBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * User: dpol
 * Date: 5/19/13
 * Time: 8:59 PM
 */

public class TestMongo {
   // @Autowired
    private Jongo jongo;
    private Mongo mongo;

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    static MongoCollection people;
    private static final org.codehaus.jackson.map.ObjectMapper objMapper = new org.codehaus.jackson.map.ObjectMapper();
    public void setJongo(Jongo jongo) {
        this.jongo = jongo;
    }

    public void insert(){
        if (jongo != null) {
            System.out.println("Jongo not null");
            if ( jongo.getCollection("people") != null)
                jongo.getCollection("people").insert("{name:'Someone'}");
            else
                System.out.println("no collection found");
        }
    }

    public People find()  {
        //String output= "not found";

        People found = null;
        people = jongo.getCollection("people");
        try {
            found = people.findOne("{name:'Someone'}").map(new ResultHandler<People>()  {

                @Override
                public People map(DBObject dbObject) {
                    People people1 = new People();
                    people1.setId(dbObject.get("_id").toString());
                    people1.setName((String) dbObject.get("name"));
                    return people1;
                }
            });
        }catch(Exception ex) {
            System.out.println("parsing as string :");
            ex.printStackTrace();
        }
        if ( jongo.getCollection("people") != null)
            found = jongo.getCollection("people").findOne("{name:'Someone'}").as(People.class);

        return found;
    }

    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossper.models;


import org.jongo.marshall.jackson.oid.ObjectId;
public class People {
    String name;
    @ObjectId
    String _id;
    public People() {
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }
    
}

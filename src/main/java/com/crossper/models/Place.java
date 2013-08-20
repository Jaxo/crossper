package com.crossper.models;

import com.google.api.client.util.Key;
import java.util.List;

public class Place {

	@Key
	public String id;
	
	@Key
	public String name;
	
	@Key
	public String reference;
        
        @Key
        public GoogleLocation geometry;
        
        @Key
        public int price_level;
        @Key
        public int rating;
        @Key
        public List<String> types; 
	@Override
	public String toString() {
		return name + " - " + id + " - " + reference;
	}
	
}

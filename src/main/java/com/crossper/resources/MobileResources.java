package com.crossper.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.view.Viewable;

/**
 * User: dpol
 * Date: 6/25/13
 * Time: 6:32 PM
 */
@Path("/mobile")
public class MobileResources {

	private String fbClientId;
    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public Viewable mobileHome(@Context HttpServletRequest request){
    	request.setAttribute("fbClientId",fbClientId);
        return new Viewable("/mobile/index.jsp",null);
    }
    
	public String getFbClientId() {
		return fbClientId;
	}
	public void setFbClientId(String fbClientId) {
		this.fbClientId = fbClientId;
	}
    
    
}

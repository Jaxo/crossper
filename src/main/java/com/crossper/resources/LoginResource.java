package com.crossper.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


import com.sun.jersey.api.view.Viewable;

/**
 * 
 * @author ddhanawade
 * Date : 29-May-2013
 */

@Path("/login")
public class LoginResource {

	@GET
	@Produces("text/html")
	public Viewable index(@QueryParam("error") boolean error)
	{
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", error);
		return new Viewable("/pages/login.jsp",map);
	}
}

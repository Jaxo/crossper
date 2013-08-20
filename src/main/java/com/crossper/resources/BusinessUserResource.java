/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.resources;

import com.crossper.exceptions.UserServiceException;
import com.crossper.representations.UserLoginRepresentation;
import com.sun.jersey.api.view.Viewable;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

@Path("/web")
public class BusinessUserResource {
   
    @GET
    @Path("/{action:(signUp|signIn|offers|contacts|aboutUs)?}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable globalGet(){
        return new Viewable("/web/index.jsp");
    }

    @GET
    @Path("/signUp/stepOne")
    @Produces(MediaType.TEXT_HTML)
    public Viewable signUpSteps(){
        return new Viewable("/web/index.jsp");
    }
    
    @POST
    @Path("/login")
    @Produces("text/html")
    public Viewable businessSignIn( @QueryParam("error") boolean error,@Context HttpServletRequest request, @Context HttpServletResponse response, @RequestBody UserLoginRepresentation bizLogin) throws UserServiceException
    {
		Map<String, Object> map = new HashMap<String, Object>();
                map.put("error", error);
		return new Viewable("/web/index.jsp",map);
	
    }
   
    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public Viewable webHome(){
        return new Viewable("/web/index.jsp");
    }
}

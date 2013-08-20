/**
 * Crossper Utility functions
 */

define(function () {
	 
	/**
	 * method get server base URL for given pathname
	 * @param pathname
	 */
	 
	  return {
	    getURL: function (pathname){			
		var root=location.pathname.substring(1,location.pathname.indexOf("/",2));
		return location.origin+"/"+root+"/"+pathname;
            },
            getServerURL: function (url){			
		var root=location.pathname.substring(1,location.pathname.indexOf("/",2));
                //TODO get server url using correct JS functions
                //console.log("Root :" + root);
                //console.log("protocol :" + window.location.protocol);
                //console.log(("port :" +window.location.port));
                //console.log("host : " +window.location.host);
                var server = location.protocol + "//"+location.host;
                //var server = "http://ec2-54-215-172-148.us-west-1.compute.amazonaws.com:8080";
		return server+"/"+root+"/"+ url;
            }
                    
	  }
          
          
});
define(["crossper-util"],function (CrossperUtil) {
	return{
		loginToFacebook:function ()
		{
			
			FB.login(function(response) {
				   if (response.authResponse) {
		               var fbResponse = response.authResponse;
				     FB.api('/me', function(response) {
				         console.log('Good to see you, ' + response.name + '.');
				        
				         delete response.work;
		                 delete response.hometown;
		                 delete response.education;
		                 delete response.favorite_athletes;
		                 delete response.location;
		                 delete response.sports;


		                 var reqData = {"username": response.username,
		                     "email": response.email,
		                     "password":"",
		                     "userToken": {
		                         "type":"facebook",
		                         "tokenValue": fbResponse.accessToken,
		                         "expiresAfter": fbResponse.expiresIn,
		                         "userId": fbResponse.userId,
		                         "series": fbResponse.signedRequest,
		                         "username": response.email
		                     },
		                     facebookProfile: response
		                 };
		                
		                 $.ajax(CrossperUtil.getURL('users/signup/facebook'),
		                     {
		                         contentType: "application/json",
		                         
		                         data: JSON.stringify(reqData),
		                         
		                         success: function(data, textStatus, jqXHR){
		                             
		                        	 window.userInfo={};
		                        	 window.userInfo.userId=data.id;  
		                        	 
		                        	 console.log("signed up to server");
		       					  	 
		                             if(textStatus=="success"){
		                                	 Backbone.history.navigate('#offers', true);
		                             }
		                                
		                         },
		                         
		                         type: "POST"
		                     }

		                 )
				     });
				   } else {
				     console.log('User cancelled login or did not fully authorize.');
				   }
			 }, {scope: 'email'});
			
			//window.location.href = "https://www.facebook.com/dialog/oauth?client_id=139903926198344&scope=email,user_birthday&redirect_uri="+redirectURL;
			
		}

	}
});

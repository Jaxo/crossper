
define(["backbone"], function(Backbone) {
	
	var crossper = crossper || {};
	
	crossper.UserDetails = Backbone.Model.extend({
	  	url: '/crossper/users/signup/crossper',
		
		defaults: function() {
			return {
	           	username	:'',
				email:'',
				password:'',
				agreeTerms:false,
				provider: ''
			};
		},
		
		validate:function(attrs){
			var errors = [];
			var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
			
			if (!attrs.username) {
		        errors.push({name: 'username', message: 'Please fill username field.'});
		    }
			
		    if (!attrs.email) {
		        errors.push({name: 'email', message: 'Please fill email field.'});
		    }else
		    if(!email_regex.test(attrs.email)){
		    	errors.push({name: 'email', message: 'email is not valid'});
		    }
		    	 
		    if (!attrs.password) {
		        errors.push({name: 'password', message: 'Please fill field field.'});
		    }
		 
		    if (!attrs.agreeTerms) {
		        errors.push({name: 'agreeTerms', message: 'Please accept terms & conditions.'});
		    }
		    
		    return errors.length > 0 ? errors : false;
		},
		
		 parse: function(response){
	            var parsedObject={};

	            parsedObject.username = response.name;
	            parsedObject.email=response.email;
	            parsedObject.password = response.password;
	            parsedObject.agreeTerms = response.agreeTerms;
	            parsedObject.provider = response.provider;
	            parsedObject.id = response.id;
	           
	            return parsedObject;
	        }
	})
	
  return crossper.UserDetails;
});

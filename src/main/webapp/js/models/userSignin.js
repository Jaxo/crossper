
define(["backbone"], function(Backbone) {
	
	var crossper = crossper || {};
	
	crossper.UserSignin = Backbone.Model.extend({
	  	url: '/crossper/j_spring_security_check',
		
		defaults: function() {
			return {
				j_username:'',
				j_password:'',
 				provider: ''
			};
		},
		
		validate:function(attrs){
			var errors = [];
			var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
			
		    if (!attrs.j_usernmae) {
		        errors.push({name: 'j_username', message: 'Please fill email field.'});
		    }else
		    if(!email_regex.test(attrs.j_username)){
		    	errors.push({name: 'j_username', message: 'email is not valid'});
		    }
		    	 
		    if (!attrs.j_password) {
		        errors.push({name: 'j_password', message: 'Please enter password.'});
		    }
		 
		    return errors.length > 0 ? errors : false;
		}
	})
	
  return crossper.UserSignin;
});

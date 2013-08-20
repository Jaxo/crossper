
define(["backbone"], function(Backbone) {
	
	var crossper = crossper || {};
	
	crossper.ContactDetails = Backbone.Model.extend({
	  	url: '/crossper/businesses/contactCrossper',
		
	  	msg:'',
	  	
		defaults: function() {
			return {
                                contactEmail: '',
                                contactPhone: '',
                                senderName	:'',
				senderEmail:'',
				senderMessage: ''
			};
		},
                parse: function(response){
                        var parsedObject={};
                        
                        parsedObject.contactEmail=response.contactEmail;
                        parsedObject.contactPhone = response.contactPhone;
                        parsedObject.senderName= response.senderName;
                        parsedObject.senderEmail = response.senderEmail;
                        parsedObject.senderMessage = response.senderMessage;
                        return parsedObject;
                },
		
		validate:function(attrs){
			var errors = [];
			var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
			
			if (!attrs.senderName) {
		        errors.push({name: 'senderName', message: 'Please fill your name.'});
		    }
			
		    if (!attrs.senderEmail) {
		        errors.push({name: 'senderEmail', message: 'Please fill email address.'});
		    }else
		    if(!email_regex.test(attrs.senderEmail)){
		    	errors.push({name: 'senderEmail', message: 'email is not valid'});
		    }
		    
		    return errors.length > 0 ? errors : false;
		}
	});
	
  return crossper.ContactDetails;
});

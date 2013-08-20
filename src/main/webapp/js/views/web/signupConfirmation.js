   define(
  ["jQuery",
   "underscore",
   "backbone",
   "text!templates/web/business-sign-up-step3.html",
  ], function($,_,Backbone,signupConfirmation) {
  
  var crossper = crossper || {};
  
  crossper.SignupConfirmationView = Backbone.View.extend({
	  
	signupConfirmationTemplate:	Handlebars.compile(signupConfirmation),
	
	tagName:"section",
	
	className:"clearfix setting-wrapper",

    events: {
        "click #email-qr-code"   : "emailQRCode",
      },

      initialize: function(){
		//this.$el.attr('data-role', 'content');
		//this.$el.attr('id', 'containerSetcion');
		this.render();
      },
      
      render: function(){ 
        this.$el.html(this.signupConfirmationTemplate(this.model)); 
        this.$el.trigger('create');
        return this; 
      }, 

      emailQRCode: function(){ 
            $("email-qr-code").prop('disabled',true);
    	  this.options.vent.trigger("emailQRCode");
      }
      
      
      

  });
  return  crossper.SignupConfirmationView;
});

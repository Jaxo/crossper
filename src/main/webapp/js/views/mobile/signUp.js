   define(
  ["jQuery",
   "underscore",
   "backbone",
   "models/userDetails",
   "text!templates/mobile/sign-up.html"
  ], function($,_,Backbone,UserDetails,signUp) {
  
  var crossper = crossper || {};
  
  crossper.SignUpView = Backbone.View.extend({

    signUpTemplate:	Handlebars.compile(signUp),
    
    model:new UserDetails(),
    
    events: {
        "click #sign-up-button"   : "submitForm",
        "change input[name=username]" : "setUsername",
        "change input[name=email]" : "setEmail",
        "change input[name=password]" : "setPassword",
        "change input[name=agreeTerms]" : "setAgreeTerms",
        "click #globalBackBtn" :"goBack"
      },

      initialize: function(){
    	  $("#sign-up-button").unbind("click");
    	  var me=this;
		  this.model.on("invalid",  function(model, error){ 
            me.showErrors(error);
		  });
		  
		  this.render();
      },
      
      render: function(){ 
        //append the compiled template into view div container 
        this.$el.append(this.signUpTemplate(this.model)); 
   
        return this; 
      }, 
    
      goBack:function(){
			window.history.back();
      },
		
      submitForm:function(){
	  
		  var me = this;
		  var options = {
		    	error: function(model, error){
		    	  me.showErrors(error);
		    	},
		    	success: function(data, textStatus, jqXHR) {
					window.userInfo = {};
					window.userInfo.userId = me.model.get('id');

					me.hideErrors();
					Backbone.history.navigate('#offers', true);
		    	}
		      };
		  
		  this.model.save({},options);
	 
      },
  
  
  
	  setUsername:function(){
		  this.model.set({'username':this.$('input[name=username]').val().trim()});
	  },
	    
	  setEmail:function(){
		  this.model.set({'email':this.$('input[name=email]').val().trim()});
	  },
	    
	  setPassword:function(){
		  this.model.set({'password':this.$('input[name=password]').val()});
		  this.model.set({'provider':'crossper'});
	  },
	  
	  setAgreeTerms:function(){
		  this.model.set({'agreeTerms':this.$("#checkbox-1a").prop("checked")});
		  
		  if(this.$("#checkbox-1a").prop("checked")){
			  $("#sign-up-button").unbind("click");
		  }else{
			  $('#sign-up-button').click(function() { return false; }); 
		  }
			  
	  },
  
	  showErrors: function(errors) {
		  	this.$('#errorDiv span').removeClass('error');
		    this.$('#errorDiv span').text('');
		    
		    _.each(errors, function (error) {
		        var errorContainer = this.$('#' + error.name+'Error');
		        errorContainer.addClass('error');
		        errorContainer.text(error.message);
		    }, this);
		},
	 
	hideErrors: function () {
	    this.$('#errorDiv span').removeClass('error');
	    this.$('#errorDiv span').text('');
	}

  });
  return  crossper.SignUpView;
});

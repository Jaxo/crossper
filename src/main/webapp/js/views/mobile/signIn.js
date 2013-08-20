   define(
  ["jQuery",
   "underscore",
   "backbone",
   "models/signIn",
   "text!templates/mobile/sign-in.html"
  ], function($,_,Backbone,SignInModel,signIn) {
  
  var crossper = crossper || {};
  
  crossper.SignInView = Backbone.View.extend({

    signInTemplate:	Handlebars.compile(signIn),
    
    model:new SignInModel(),
    
    events: {
        "click #sign-in-button"   : "submitForm",
        "change input[name=j_username]" : "setUsername",
        "change input[name=j_password]" : "setPassword",
        "click #globalBackBtn" :"goBack"        
      },

      initialize: function(){
    	  var me=this;
		  this.model.on("invalid",  function(model, error){ 
            me.showErrors(error);
		  });
		  
		  this.render();
      },
      
      render: function(){ 
        //append the compiled template into view div container 
        this.$el.append(this.signInTemplate(this.model)); 
   
        return this; 
      }, 
    
      goBack:function(){
			window.history.back();
      },
		
      submitForm:function(){
	  
		  var me = this;
/*		  var options = {
		    	error: function(model, error){
		    	  console.log("Error while Logging");
		    	  me.showErrors(error);
		    	},
		    	success: function(data, textStatus, jqXHR) {
		    	  //TODO:Add Check wheather login is sucessfull or not
		    	  console.log("Logged in SuccessFully");
		    	  me.hideErrors();
//		    	  Backbone.history.navigate('#offers', true);
		    	}
		      };
		  
		  this.model.save({},options);
	 */
		  if(this.model.isValid()){
			  
			  $.ajax({
	              
				  url: '/crossper/mobile/j_spring_security_check',
	              
	              type: 'POST',
	          
	              data: {
	                  j_username: this.model.get('j_username'),
	                  j_password: this.model.get('j_password')
	              },
	              
	              success: function(data, textStatus, jqXHR) {
	            	  //set userData to window level
	            	  window.userInfo={};
					  window.userInfo.userId=data;
	                  
					  me.hideErrors();
	                  Backbone.history.navigate('#offers', true);
	              },
	              
	              error: function(jqXHR, textStatus, errorThrown) {
	            	var errorContainer = $('#ErrorMsg');
	      		    errorContainer.addClass('error');
	      		    errorContainer.text("The username or password is incorrect");
	              }
	          });
			  
		  }
		  
      },
  
	    
      setUsername:function(){
		  this.model.set({'j_username':this.$('input[name=j_username]').val().trim()});
	  },
	    
	  setPassword:function(){
		  this.model.set({'j_password':this.$('input[name=j_password]').val()});
//		  this.model.set({'provider':'crossper'});
	  },
	  
	
	  showErrors: function(errors) {
		  	this.$('#errorDiv span').removeClass('error');
		    this.$('#errorDiv span').text('');
		    
		    var errorContainer = this.$('#ErrorMsg');
		    errorContainer.addClass('error');
		    errorContainer.text("Please fill out the necessary fields above");
		    
		},
	 
	hideErrors: function () {
	    this.$('#errorDiv span').removeClass('error');
	    this.$('#errorDiv span').text('');
	}

  });
  return  crossper.SignInView;
});

   define(
  ["jQuery",
   "underscore",
   "backbone",
   "models/signIn",
   "text!templates/web/sign-in.html"
  ], function($,_,Backbone,UserSignin,signIn) {
  
  var crossper = crossper || {};
  
  crossper.SignInView = Backbone.View.extend({
    el:"#middleContainer",
    signInTemplate:	Handlebars.compile(signIn),
    
    model:new UserSignin(),
    
    events: {
        "click #sign-in-button"   : "submitForm",
        "change input[name=j_username]" : "setEmail",
        "change input[name=j_password]" : "setPassword"
      },

      initialize: function(){
    	  $("#sign-in-button").unbind("click");
    	  var me=this;
		  this.model.on("invalid",  function(model, error){ 
            me.showErrors(error);
		  });
      },
      
      render: function(){ 
        //append the compiled template into view div container 
        this.$el.html(this.signInTemplate(this.model));
        $("#unameStatus").hide();
        $("#pwStatus").hide();
		return this;
        
      }, 
      renderWithEmail: function(email){ 
        //append the compiled template into view div container 
        this.model.set({
					'j_username' : email
				});
        this.$el.html(this.signInTemplate(this.model));
        this.$('input[name=j_username]').val = email;
        $("#username").val(email);
        $("#unameStatus").hide();
        $("#pwStatus").hide();
	   return this;
        
      }, 
    
      submitForm:function(){
	  
		  var me = this;
                  var valid = this.model.isValid();
		  if(valid){	  
			  $.ajax({
				url: '/crossper/web/j_spring_security_check',
                                type: 'POST',
	          
                                data: {
                                    j_username: this.model.get('j_username'),
                                    j_password: this.model.get('j_password')
                                },

                                success: function(data, textStatus, jqXHR) {
                                	window.businessInfo={};
               					    window.businessInfo.businessId=data;
               					  
                                    me.hideErrors();
                                    Backbone.history.navigate('web/home', true);
                                },

                                error: function(jqXHR, textStatus, errorThrown) {
                                    var errorContainer = $('#ErrorMsg');
                                    errorContainer.addClass('error');
                                    errorContainer.text("Incorrect Username or password.");
                                }
                        });
                  }
                  else {
                      var errorContainer = $('#ErrorMsg');
                      errorContainer.addClass('error');
                      errorContainer.text("Please enter Username and Password.");
                      
                  }
      },
  
  	    
	  setEmail:function(){
		  this.model.set({'j_username':this.$('input[name=j_username]').val()});
                  this.validateName();
	  },
	    
	  setPassword:function(){
		  this.model.set({'j_password':this.$('input[name=j_password]').val()});
                  this.validatePassword();
		  //this.model.set({'provider':'crossper'});
	  },
	  
	  showErrors: function(errors) {
		  	this.$('#errorDiv span').removeClass('error');
                        this.$('#errorDiv span').text('');
                        this.showNameError();
                        this.showPwdError();
                        var errorContainer = this.$('#errorDiv span');
                        errorContainer.addClass('error');
                        
		    _.each(errors, function (error) {
          
		        errorContainer.addClass('error');
		        errorContainer.text(error.message);
		    }, this);
		},
	 
	hideErrors: function () {
	    this.$('#errorDiv span').removeClass('error');
	    this.$('#errorDiv span').text('');
	},
        validateName: function() {
            var name = this.$('input[name=j_username]').val();
            if ( name.trim() !== "" && this.isNameValid(name)) {
               this.showNameSuccess();
            }
            else {
                this.showNameError();
            }
        },
        showNameSuccess: function() {
            this.$('#username').removeClass('textbox error-border');
            this.$('#username').addClass('textbox success-border');
             $("#unameStatus").removeClass('error-icon');
                $("#unameStatus").addClass('success-icon');
            $("#unameStatus").show();
        },
        showNameError: function() {
            $("#unameStatus").removeClass('success-icon');
                $("#unameStatus").addClass('error-icon');
                $("#unameStatus").show();
        },
        showPwdError: function() {
            $("#pwStatus").removeClass('success-icon'); 
            $("#pwStatus").addClass('error-icon'); 
            $("#pwStatus").show();
        },
        isNameValid: function(name) {
            var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;			 
            if (!name )
                return false;
            else if (! email_regex.test(name)) 
                return false;
            else
                return true;
        },
        validatePassword: function () {
            var pw = this.$('input[name=j_password]').val();
            if ( pw.trim() !== " ") {
                this.$('#password').removeClass('textbox error-border');
                 this.$('#password').addClass('textbox success-border');
                 $("#pwStatus").removeClass('error-icon'); 
                 $("#pwStatus").addClass('success-icon'); 
                $("#pwStatus").show();
            }
            else {
                 $("#pwStatus").removeClass('success-icon'); 
                 $("#pwStatus").addClass('error-icon'); 
                 $("#pwStatus").show();
                 
            }
        }        
        

  });
  return  crossper.SignInView;
});

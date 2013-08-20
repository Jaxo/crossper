
define(
  ["backbone",
   "underscore",
   "mobileViews/signUp",
   "text!templates/mobile/index.html",
  ], function(Backbone,_,SignUpView,indexTemplate) {
  
	  var crossper = crossper || {};
	  
	  crossper.MobileMainView = Backbone.View.extend({
        
		
		template:_.template(indexTemplate),
		
		
		
		events:{
			"click #signUp" : "signUp",
			"click #signIn" : "signIn",
			"click #businessSignUp" : "businessSignUp",
			"click #fb-login-button" : "fbLogin"
		},
		
		signUp:function(){
			this.router.navigate("signUp", {trigger : true	});
		},
		
		signIn:function(){
			this.router.navigate("signIn", {trigger : true	});
		},
		
		businessSignUp:function(){
			this.router.navigate("stepOne", {trigger : true	});
		},
		
		fbLogin:function(){
			this.router.navigate("fbLogin", {trigger : true	});
		},
		
		initialize: function(){
			this.router=this.options.router;
			this.render();
	      },
        
        render: function(){ 
            //append the compiled template into view div container 
            this.$el.append(this.template()); 
       
            return this; 
          } 
    })
	
   return crossper.MobileMainView;
});

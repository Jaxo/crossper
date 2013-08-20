define(
  ['backbone',
   "webViews/signUp",
   "webViews/signIn",
   "webViews/contacts",
   "webViews/aboutUs",
   "webRoot/router",
   'webViews/home',
   "webViews/landing",
   "text!templates/web/index.html",
   "text!templates/web/contacts.html"
  ], function(Backbone,SignUpView,SignInView,ContactsView, AboutUsView, CrossperRouter,CrossperHomeView, LandingView, indexTemplate, contactsTemplate) {
  
	  var crossper = crossper || {};
	  
	  crossper.MainView = Backbone.View.extend({
        
		el:"body",	  
		 
		event : _.extend({}, Backbone.Events),
		
		events: {
            "click #signUpLink1"   : "signUp",
            "click #signUpLink2"   : "signUp",
            "click #signInLink"   : "signIn",
            "click #contactLink"   : "contacts",
            "click #logoutLink"     : "logout",
            "click #homePageLink"       : "landing",
            "click #aboutLink" : "aboutUs",
            "click #signInLinkFooter" : "signIn",
            "click #contactLinkFooter" : "contacts",
            "click #logoutLinkFooter" : "logout",
             "click #aboutLinkFooter" : "aboutUs"
        },
          
        initialize: function(){ 
        	this.registerHandleBarHelpers();
        	
			this.$el.html(indexTemplate);
			$("#logoutLink").hide(); 
                        $("#logoutLinkFooter").hide();
			this.event.bind("showContacts", this.contacts, this); 
                        this.event.bind("showAboutUs", this.aboutUs, this); 
			this.event.bind("signUp", this.signUp, this);             
			this.event.bind("signIn", this.signIn, this); 
                        this.event.bind("signInWithEmail", this.signInWithEmail, this);    
			this.event.bind("home", this.home, this);     
                        this.event.bind("showMain", this.landing, this);
                        this.router=new CrossperRouter({event:this.event});
			Backbone.history.start({pushState: true, root: "/crossper/"});
                        //this.render();
        },
	
        registerHandleBarHelpers:function(){
			Handlebars.registerHelper('dateFormat', function(inputDate) {
				  return ("0" + (inputDate.getMonth() + 1)).slice(-2) +"/"+("0" + inputDate.getDate()).slice(-2)+"/"+inputDate.getFullYear();
				});
		},
        
        //Load signup
        signUp: function() {
        	
        	this.router.navigate("web/stepOne", {trigger : true	});
        	/*
        	var signUpView = new SignUpView({
				router : this.router,
				event : this.event
			});
			signUpView.render();*/
        },
        
        signIn: function() {
        	this.router.navigate("web/signIn", {trigger : false});
        	var signInView = new SignInView();
			signInView.render();
        },
        signInWithEmail: function(email) {
         
        	this.router.navigate("web/signIn?email="+email, {trigger : false});
        	var signInView = new SignInView();
			signInView.renderWithEmail(email);
        },
        contacts:function() {
        	this.router.navigate("web/contacts", {trigger : false});
        	var contactsView = new ContactsView();
			contactsView.render();
        },
         aboutUs:function() {
        	this.router.navigate("web/aboutUs", {trigger : false});
        	var aboutUsView = new AboutUsView();
			aboutUsView.render();
        },
        home:function(){
        	this.crossperHomeView=new CrossperHomeView({event:this.event,router:this.router});
        	this.$el.find('.outer-wrapper').addClass('border-bottom');
        	
        	this.$el.find("#signInLink").hide(); 
                this.$el.find("#signInLinkFooter").hide(); 
        	this.$el.find("#logoutLink").show();
                this.$el.find("#logoutLinkFooter").show();
        	this.delegateEvents();
        	
        	this.crossperHomeView.render();
        	this.crossperHomeView.rightSummary();
        	this.crossperHomeView.dashboard({event:this.event,currentOfferView:this.crossperHomeView.currentOfferView});
        },
        landing: function() {
                this.router.navigate("web/", {trigger : false});
		var landingView = new LandingView();
                landingView.render();
        },
         logout:function(){
		  var me = this;	  
			  $.ajax({
	              url: '/crossper/web/j_spring_security_logout',
                     
	              type: 'POST',
	              
	              success: function(data, textStatus, jqXHR) {
                          $("#logoutLink").hide();
                          $("#logoutLinkFooter").hide();
                          $("#signInLink").show();
                           $("#signInLinkFooter").show();
	                  Backbone.history.navigate('', true);
                          
	              },
	              
	              error: function(jqXHR, textStatus, errorThrown) {
	            	Backbone.history.navigate('', true);
	              }
	          });
        }
    });
	
   return crossper.MainView;
});

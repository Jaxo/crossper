define(['jQuery',
         'underscore',
    'backbone',
        'webViews/contacts',
        'webViews/signUp',
        'webViews/signIn',
		'webViews/offer', 
		'webViews/home'], 
		function($, _,Backbone, ContactsView, SignUpView,SignInView, WebOfferView,CrossperHomeView) {

	var crossper = crossper || {};

	crossper.CrossperRouter = Backbone.Router.extend({
		routes : {
			'' : 'showMain',
			'web/' : 'showMain',
			'web/contacts' : 'showContacts',
			'web/aboutUs' : 'showAboutUs',
			'web/signUp' : 'signUp',
                        "web/signIn?email=:email" : 'signIn',
			'web/signIn' : 'signIn',
			"web/stepOne" : "stepOne",
			"web/stepTwo" : "stepTwo",
			"web/stepThree" : "stepThree",
			"web/home" : "home",
			"web/home/dashboard" : "dashboard",
			"web/home/offers" : "offers",
			"web/home/promoters" : "promoters",
			"web/home/settings" : "settings",
			"web/home/locations" : "locations"
		},
		
	//	event : _.extend({}, Backbone.Events),
		
		initialize : function(options) {
			this.event = options.event;
		},
                
                showMain : function() {
                   this.event.trigger("showMain");
                },
		showContacts : function() {
			this.event.trigger("showContacts");
		},
                showAboutUs: function () {
                        this.event.trigger("showAboutUs");
                },
		signUp : function() {
			// this.event.trigger("signUp");

			this.signUpView = new SignUpView({
				router : this,
				event : this.event
			});
			this.signUpView.render();
		},
		stepOne : function() {
//			var activePageId = $.web.activePage.attr('id');

			if (this.signUpView === undefined) {
				this.signUp();
			} else {
				this.event.trigger("goToStepOne");
			}

		},

		dashboard:function(){
			this.event.trigger("dashboard");
		},
		
		offers:function(){
			this.event.trigger("offers");
		},
		
		promoters:function(){
			this.event.trigger("promoters");
		},
		
		settings:function(){
			this.event.trigger("settings");
		},
		
		locations:function(){
			this.event.trigger("locations");
		},
		
		stepTwo : function() {
			this.event.trigger("goToStepTwo");
		},

		stepThree : function() {
			this.event.trigger("goToStepThree");
		},
		signIn : function( email) {
                        console.log("email value is "+email);
                        if(typeof email !== 'undefined'){
                            console.log("sign in with email");
                         this.event.trigger("signInWithEmail");
                        }else {
                            console.log("sign in without email");
			this.event.trigger("signIn");
                        }
                    },

		home:function(){
			this.event.trigger("home");
		}

	});

	return crossper.CrossperRouter;
});

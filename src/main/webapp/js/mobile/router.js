/**
 * Backbone Router for mobile
 */
define(['jQuery',
        'underscore', 
        'backbone', 
        'mobileViews/main',
		'mobileViews/signUp', 
		'mobileViews/signIn', 
		'mobileViews/offerWrapper',
		'mobileViews/businessSignUp', 
		'mobileRoot/fblogin',
		'mobileViews/menu'], 
		function($, _, Backbone,MainView, SignUpView, SignInView, OfferWrapperView, BusinessSignUpView,FBLogin,MenuView) {

	var crossper = crossper || {};

	crossper.CrossperMobileRouter = Backbone.Router.extend({
		routes : {
			"" 			: "home",
			"signUp" 	: "signUp",
			"offers" 	: "offers",
			"fbLogin" 	: "fblogin",
			"signIn" 	: "signIn",
			"businessSignUp" : "businessSignUp",
			"stepOne"	: "stepOne",
			"stepTwo"	: "stepTwo",
			"stepThree"	: "stepThree"
		},
		
		vent : _.extend({}, Backbone.Events),
		
		home : function() {
			var mainView = new MainView({router:this});
			this.changePage(mainView);

		},

		signUp : function() {
			var signUpView = new SignUpView();
			this.changePage(signUpView);
		},

		offers : function() {
			if(this.offerWrapperView==undefined){
				this.offerWrapperView = new OfferWrapperView({router:this,event:this.vent});
				this.addMenu();
				this.changePage(this.offerWrapperView);
			}else{
				this.offerWrapperView.loadOffers();
			}
		},

		fblogin : function() {
			require([ 'fblogin' ], function(FBLogin) {
				FBLogin.loginToFacebook();
			});
		},

		businessSignUp : function() {
			var businessSignUpView = new BusinessSignUpView({router:this,vent:this.vent});
			this.changePage(businessSignUpView);
		},

		signIn : function() {
			var signInView = new SignInView();
			this.changePage(signInView);
		},

		stepOne:function(){
			var activePageId=$.mobile.activePage.attr('id');

			if(activePageId!="businessSignUpView"){
				this.businessSignUp();
			}else{
				this.vent.trigger("goToStepOne");
			}

		},
		
		categorization:function(){
			this.vent.trigger("categorization");
		},
		
		stepTwo:function(){
			this.vent.trigger("goToStepTwo");
		},
		
		stepThree:function(){
			this.vent.trigger("goToStepThree");
		},
		
		addMenu:function(){
			this.menuView=new MenuView();
		},
		
		changePage : function(view) {
			view.$el.attr('data-role', 'page');

			$('body').append(view.$el);

			if (!this.init) {
				$.mobile.changePage($(view.el), {
					changeHash : false
				});
			} else {
				this.init = false;
			}
		}

	});

	return crossper.CrossperMobileRouter;
});

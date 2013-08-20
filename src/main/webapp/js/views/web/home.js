define(["jQuery",
        "underscore",
        "backbone",
        "webViews/dashboard",
        "webViews/offers",
        "webViews/promoters",
        "webViews/settings",
        "webViews/locations",
        "webViews/rightSummary",
        "webViews/offerListWrapper",
		"text!templates/web/crossper-home.html" ],
		function($, _, Backbone,DashboardView,OffersView,PromotersView,SettingsView,LocationsView,RightSummaryView,OfferListWrapperView,crossperHome) {

	var crossper = crossper || {};

	crossper.CrossperHomeView = Backbone.View.extend({
		el : "#middleContainer",
		crossperHomeTemplate : Handlebars.compile(crossperHome),

		events : {
			"click #dashbordTab" : "dashboard",
			"click #offersTab" : "offers",
			"click #promotersTab" : "promoters",
			"click #settingsTab" : "settings",
			"click #locationsTab" : "locations"
		},

		initialize : function() {
			this.event = this.options.event;	
			this.router = this.options.router;	
			
			this.event.bind("dashboard", this.dashboard, this);     
			this.event.bind("offers", this.offers, this);     
			this.event.bind("promoters", this.promoters, this);     
			this.event.bind("settings", this.settings, this);     
			this.event.bind("locations", this.locations, this);    
			
			this.event.bind("addOffer", this.addOffer, this);  
			this.event.bind("addNewOfferToCurrentOffers", this.addNewOfferToCurrentOffers, this);    
			
			this.event.bind("editProfile", this.settings, this);  
			this.event.bind("offerClicked", this.offerClicked, this);  
			
			this.currentOfferView=new OfferListWrapperView({event:this.event});
		},

		
		render : function() {
			this.$el.html(this.crossperHomeTemplate());
			return this;
		},

		dashboard:function(){
			this.router.navigate("/web/home/dashboard", {
                trigger: false
            });
			if(this.dashboardView==undefined){
				this.dashboardView=new DashboardView({event:this.event,currentOfferView:this.currentOfferView});
				this.$el.find("#myTabContent").html(this.dashboardView.el);
			}else{
				this.$el.find("#myTabContent").html(this.dashboardView.el);
				this.dashboardView.setOffersList(this.currentOfferView);
				this.dashboardView.delegateEvents();
			}
			for(var i=0;i<this.currentOfferView.offerList.length;i++){
				this.currentOfferView.offerList[i].delegateEvents();
			};
			
		},

		offers:function(){
			this.router.navigate("/web/home/offers", {
                trigger: false
            });
			if(this.offersView==undefined){
				this.offersView=new OffersView({event:this.event,currentOfferView:this.currentOfferView});
				this.$el.find("#myTabContent").html(this.offersView.el);
			}else{
				this.$el.find("#myTabContent").html(this.offersView.el);
				this.offersView.setOffersList(this.currentOfferView);
				this.offersView.delegateEvents();
			}
			for(var i=0;i<this.currentOfferView.offerList.length;i++){
				this.currentOfferView.offerList[i].delegateEvents();
			};
		},
		
		promoters:function(){
			this.router.navigate("/web/home/promoters", {
                trigger: false
            });
			if(this.promotersView==undefined){
				this.promotersView=new PromotersView();
				this.$el.find("#myTabContent").html(this.promotersView.el);
			}else{
				this.$el.find("#myTabContent").html(this.promotersView.el);
				this.promotersView.delegateEvents();
			}
		},
		
		settings:function(){
			this.router.navigate("/web/home/settings", {
                trigger: false
            });
			if(this.settingsView==undefined){
				this.settingsView=new SettingsView();
				this.$el.find("#myTabContent").html(this.settingsView.el);
			}else{
				this.$el.find("#myTabContent").html(this.settingsView.el);
				this.settingsView.delegateEvents();
			}
		},
		
		locations:function(){
			this.router.navigate("/web/home/locations", {
                trigger: false
            });
			if(this.locationsView==undefined){
				this.locationsView=new LocationsView();
				this.$el.find("#myTabContent").html(this.locationsView.el);
			}else{
				this.$el.find("#myTabContent").html(this.locationsView.el);
				this.locationsView.delegateEvents();
			}
		},
		
		rightSummary:function(){
			this.rightSummaryView=new RightSummaryView();
			this.$el.find("#home-right").html(this.rightSummaryView.el);
		},
		
		addOffer:function(){

			if(this.offersView==undefined){
				this.offersView=new OffersView({event:this.event,currentOfferView:this.currentOfferView});
				this.$el.find("#myTabContent").html(this.offersView.el);
			}else{
				this.$el.find("#myTabContent").html(this.offersView.el);
				this.offersView.setOffersList(this.currentOfferView);
				this.offersView.delegateEvents();
			}
			
			this.offersView.addOffer();
		},
		
		addNewOfferToCurrentOffers:function(offer){
			this.currentOfferView.model.create(offer);
		},
		
		offerClicked:function(offer){
			
			if(this.offersView==undefined){
				this.offersView=new OffersView({event:this.event,currentOfferView:this.currentOfferView});
				this.$el.find("#myTabContent").html(this.offersView.el);
			}else{
				this.$el.find("#myTabContent").html(this.offersView.el);
				this.offersView.setOffersList(this.currentOfferView);
				this.offersView.delegateEvents();
			}
			
			this.offersView.offerClicked(offer);
		}
	});
	return crossper.CrossperHomeView;
});

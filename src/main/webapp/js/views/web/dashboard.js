define(["jQuery",
        "underscore",
        "backbone",
        "webViews/businessTile",
        "webViews/offerListWrapper",
		"text!templates/web/dashboard.html" ],
		function($, _, Backbone,BusinessTileView,OfferListWrapperView,dashboard) {

	var crossper = crossper || {};

	crossper.DashboardeView = Backbone.View.extend({
		
		dashboardTemplate : Handlebars.compile(dashboard),

		tagName:"div",
		
		className:"tab-pane fade active in",
		
		id:"dashboard",
		events : {
		 "click #addOffer":"addOffer"
		},

		initialize : function() {
			this.event = this.options.event;	
			
			this.render();
			this.businessTileView=new BusinessTileView({event:this.event});
			//this.OfferListWrapperView=new OfferListWrapperView({event:this.event});

			this.currentOfferView=this.options.currentOfferView;
			this.$el.find("#businessTile").html(this.businessTileView.el);
			this.$el.find("#offersContainer").html(this.currentOfferView.el);
		},

		render : function() {
			this.$el.html(this.dashboardTemplate());
			return this;
		},
		
		setOffersList:function(offersView){
			this.$el.find("#offersContainer").html(offersView.el);
		},
		
		addOffer:function(){
			this.event.trigger("addOffer",this.model); 
		}

	});
	return crossper.DashboardeView;
});

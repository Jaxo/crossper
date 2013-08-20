define(["jQuery",
        "underscore",
        "backbone",
        "webViews/offerListWrapper",
        "webViews/singleOffer",
        "webViews/editOffer",
        "webViews/offers-main",
        "webViews/addOffer",
		"text!templates/web/offers.html" ],
		function($, _, Backbone,OfferListWrapperView,SingleOfferView,EditOfferView,OfferMainView,AddOfferView,offers) {

	var crossper = crossper || {};

	//Main offers Tab View
	crossper.OffersView = Backbone.View.extend({
		
		offersTemplate : Handlebars.compile(offers),

		tagName:"div",
		
		className:"tab-pane fade active in",
		
		id:"offers",
		
		events : {
		},

		initialize : function() {
			this.event = this.options.event;	
			
			this.render();
			
			this.event.bind("editOffer", this.editOffer, this);  
			

			this.currentOfferView=this.options.currentOfferView;
			this.offerMainView=new OfferMainView({currentOfferView:this.currentOfferView,event:this.event});
			this.$el.html(this.offerMainView.el);
		},

		render : function() {
			this.$el.html(this.offersTemplate());
			return this;
		},
		
		setOffersList:function(offersView){
			this.$el.html(this.offerMainView.el);
			this.$el.find("#offersContainer").html(offersView.el);
		},
		
		offerClicked:function(offer){
			this.singleOfferView=new SingleOfferView({model:offer,event:this.event});
			this.$el.html(this.singleOfferView.el);
		},
		
		editOffer:function(offer){
			this.editOfferView=new EditOfferView({model:offer,event:this.event});
			this.$el.html(this.editOfferView.el);
		},
		
		addOffer:function(){
			this.addOfferView=new AddOfferView({event:this.event});
			this.$el.html(this.addOfferView.el);
		}
	});
	return crossper.OffersView;
});

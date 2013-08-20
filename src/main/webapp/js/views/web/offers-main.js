define(["jQuery",
        "underscore",
        "backbone",
        "webViews/addOffer", 
		"text!templates/web/offers-main.html" ],
		function($, _, Backbone,AddOfferView,offersMain) {

	var crossper = crossper || {};

	crossper.OfferMainView = Backbone.View.extend({
		
		offersMainTemplate : Handlebars.compile(offersMain),

		tagName:"div",
		
		id:"offerlist",
		
		events : {
			"click #addOffer":"addOffer"
		},

		initialize : function() {
			this.event = this.options.event;	
			
			this.render();
			
			this.$el.find("#offersContainer").html(this.options.currentOfferView.el);
		},

		render : function() {
			this.$el.html(this.offersMainTemplate());
			return this;
		},

		addOffer:function(){
			this.event.trigger("addOffer",this.model); 
		}
	});
	return crossper.OfferMainView;
});

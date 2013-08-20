define(["jQuery",
        "underscore",
        "backbone",
		"text!templates/web/singleOffer.html" ],
		function($, _, Backbone,singleOffer) {

	var crossper = crossper || {};

	crossper.SingleOfferView = Backbone.View.extend({
		
		singleOfferTemplate : Handlebars.compile(singleOffer),

		tagName:"div",
		
//		className:"info-block",
		
		id:"singleoffer",
		
		events : {
			"click #editOffer":"editOffer"
		},

		initialize : function() {
			this.event = this.options.event;	
			
			this.render();
		},

		render : function() {
			this.$el.html(this.singleOfferTemplate(this.model.toJSON()));
			return this;
		},

		editOffer:function(){
	    	  this.event.trigger("editOffer",this.model); 
		}
	});
	return crossper.SingleOfferView;
});

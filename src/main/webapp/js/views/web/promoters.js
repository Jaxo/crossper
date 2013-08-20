define(["jQuery",
        "underscore",
        "backbone",
		"text!templates/web/promoters.html" ],
		function($, _, Backbone,promoters) {

	var crossper = crossper || {};

	crossper.PromotersView = Backbone.View.extend({
		
		promotersTemplate : Handlebars.compile(promoters),

		tagName:"div",
		
		className:"tab-pane fade active in",
		
		id:"promoters",
		
		events : {
		},

		initialize : function() {
			this.render();
		},

		render : function() {
			this.$el.html(this.promotersTemplate());
			return this;
		},

	});
	return crossper.PromotersView;
});

define(["jQuery",
        "underscore",
        "backbone",
		"text!templates/web/locations.html" ],
		function($, _, Backbone,locations) {

	var crossper = crossper || {};

	crossper.LocationsView = Backbone.View.extend({
		
		locationsTemplate : Handlebars.compile(locations),

		tagName:"div",
		
		className:"tab-pane fade active in",
		
		id:"settings",
		
		events : {
		},

		initialize : function() {
			this.render();
		},

		render : function() {
			this.$el.html(this.locationsTemplate());
			return this;
		},

	});
	return crossper.LocationsView;
});

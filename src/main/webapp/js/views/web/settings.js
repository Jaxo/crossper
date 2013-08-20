define(["jQuery",
        "underscore",
        "backbone",
		"text!templates/web/settings.html" ],
		function($, _, Backbone,settings) {

	var crossper = crossper || {};

	crossper.SettingsView = Backbone.View.extend({
		
		settingsTemplate : Handlebars.compile(settings),

		tagName:"div",
		
		className:"tab-pane fade active in",
		
		id:"settings",
		
		events : {
		},

		initialize : function() {
			this.render();
		},

		render : function() {
			this.$el.html(this.settingsTemplate());
			return this;
		}

	});
	return crossper.SettingsView;
});

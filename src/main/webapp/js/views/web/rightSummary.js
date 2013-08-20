define(["jQuery",
        "underscore",
        "backbone",
		"text!templates/web/right-summary.html" ],
		function($, _, Backbone,rightSummary) {

	var crossper = crossper || {};

	crossper.RightSummaryView = Backbone.View.extend({
		
		rightSummaryTemplate : Handlebars.compile(rightSummary),

		tagName:"div",
		
		className:"rightblock",

		events : {
		},

		initialize : function() {
		this.render();
		},

		render : function() {
			this.$el.html(this.rightSummaryTemplate());
			return this;
		},

	});
	return crossper.RightSummaryView;
});

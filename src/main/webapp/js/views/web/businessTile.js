define(["jQuery",
        "underscore",
        "backbone",
        "models/businessInfo",
		"text!templates/web/businessTile.html" ],
		function($,_, Backbone,BusinessInfo,businessTile) {

	var crossper = crossper || {};

	crossper.BusinessTileView = Backbone.View.extend({
		
		businessTileTemplate : Handlebars.compile(businessTile),

		tagName:"div",
		
		model:new BusinessInfo,
		
		className:"info-block",
		
		events : {
			"click #editProfile":"editProfile"
		},

		initialize : function() {
			this.event = this.options.event;	
			
			this.listenTo(this.model, 'sync', this.render);
			this.model.set('id', window.businessInfo.businessId).fetch();
			//	this.render();
		},

		editProfile:function(){
			this.event.trigger("editProfile");	
		},
		
		render : function() {
			this.$el.html(this.businessTileTemplate(this.model.toJSON()));
			return this;
		}

	});
	return crossper.BusinessTileView;
});

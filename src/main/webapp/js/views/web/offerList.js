  define(
  ["backbone",
   "handlebar",
//   "views/ShowOffer",
   "text!templates/web/offerTitle.html"
  ], function(Backbone,Handlebars,offerTitle) {
  var OfferListView  = Backbone.View.extend({
	
	//parentEl:$("#offerList"),
    
	tagName:  "li",

	offerTitleTemplate: Handlebars.compile(offerTitle),

    events: {
     // "click .dashboardOffer"   : "showOffer",
    },

    initialize: function() {
//      this.listenTo(this.model, 'change', this.render);
//      this.listenTo(this.model, 'destroy', this.remove);
    },

    render: function() {
		this.$el.html(this.offerTitleTemplate(this.model.toJSON()));
		
//		$("#containerDiv").children('div').addClass('displayNone');
//		this.parentEl.removeClass('displayNone');
      return this;
    },

    // Switch this view.
    showOffer: function() {
	/*$('<div id="showOffer" class="showOffer"></div>').insertAfter(this.parentEl);
	 var view = new ShowOfferView({model: this.model});
	 view.render();*/
    },

  });
  return OfferListView;
});

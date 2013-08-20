define(
  ["backbone",
   "handlebar",
   "text!templates/web/landing.html"
  ], function(Backbone,Handlebars,landingTemplate) {
  
  var crossper = crossper || {};
  
  crossper.LandingView  = Backbone.View.extend({

	el:"#middleContainer",

	 initialize: function(){
		 this.render();
	 },
     
    events: {
//      "click .saveOfferBtn"   : "saveOffer",
    },

    render: function() {
		this.$el.html(landingTemplate);
		return this;
    }

  });
  return  crossper.LandingView;
});
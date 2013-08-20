define(
  ["backbone",
   "handlebar",
   "text!templates/web/about-us.html"
  ], function(Backbone,Handlebars, aboutTemplate) {
  
  var crossper = crossper || {};
  
  crossper.AboutUsView  = Backbone.View.extend({

	el:"#middleContainer",
	 initialize: function(){
                 
		 this.render();
	 },
        render: function() { 
		this.$el.html(aboutTemplate);
		return this;
        }

  });
  return  crossper.AboutUsView;
});
   define(
  ["jQuery",
   "handlebar",
   "backbone",
   "models/offer",
   "text!templates/web/offerTitle.html"
  ], function($,Handlebars,Backbone,Offer,offerTemplate) {
  
  var crossper = crossper || {};
  
  crossper.OfferView = Backbone.View.extend({

	tagName:"li",
	  
    offerTemplate:	Handlebars.compile(offerTemplate),
    
    model:new Offer(),
    
    events: {
    	"click":"offerClicked"
    },

    initialize: function(){
    	this.event = this.options.event;
//		 this.render();
	 },
    
    render: function(){ 
        this.$el.html(this.offerTemplate(this.model.toJSON())); 
          
        return this; 
      },
      
      offerClicked:function(){
    	  this.event.trigger("offerClicked",this.model); 
      }
    
  });
  return  crossper.OfferView;
});

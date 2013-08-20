define(
  ["jQuery",
   "handlebar",
   "backbone",
   "models/offer",
   "text!templates/mobile/scannedOffer.html"
  ], function($,Handlebars,Backbone,Offer,scannedOffer) {
  
  var crossper = crossper || {};
  
  crossper.ScannedOfferView = Backbone.View.extend({

	  scannedOfferTemplate:	Handlebars.compile(scannedOffer),
    
    model:new Offer(),

    tagName:"div",
    
    className:'offer-block clearfix',
    
    initialize: function(){
    	this.event=this.options.event;
		this.render();
    },
    events: {
    	"click":"renderOffer"
      },
      
      render: function(){ 
        this.$el.html(this.scannedOfferTemplate(this.model.toJSON())); 
   
        return this; 
      },
      
      renderOffer:function(){
    	  this.event.trigger("showSelectedOffer",this.model);
      }
  });
  return  crossper.ScannedOfferView;
});

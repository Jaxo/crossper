
define(["backbone", "models/offer"], function(Backbone, Offer) {
  var Offers = Backbone.Collection.extend({
		url : function () { return '/crossper/consumers/'+this.userId+'/offers'; } ,
		model: Offer,
        initialize: function(){
        }
    })
  return Offers;
});


define([ "backbone", "models/businessInfo", "models/offer" ], function(
		Backbone, BusinessInfo, Offer) {

	var crossper = crossper || {};

	crossper.BusinessSignUp = Backbone.Model.extend({

		url : '/crossper/businesses/signup',

		defaults : function() {
			return {
                id: null,
				businessInfo : new BusinessInfo,
				offer : new Offer
			};
		}

	/*		
	 initialize: function(){
	 this.businessInfo = new BusinessInfo;
	 this.offer		  = new Offer;
	 },
	 */
	})
	return crossper.BusinessSignUp;
});

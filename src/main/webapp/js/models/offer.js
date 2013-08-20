define([ "backbone" ], function(Backbone) {

	var crossper = crossper || {};

	crossper.Offer = Backbone.Model.extend({
		defaults : function() {
			return {
				id:null,
				title : '',
				description : '',
				startDate : null,
				endDate : null,
				limitedQuantity : false,
				quantity : null,
				validity: 'everyday'
			/*
			 * promoted : 0, claimed : 0, promotedPercentage : 0,
			 * claimedPercentage : 0
			 */};
		},

		validate : function(attrs) {
			var errors = [];
			var number_regex=/^\s*\d+\s*$/;
			var isNonblank_re    = /\S/;
			
			if (!attrs.title.trim()) {
				errors.push({
					name : 'title',
					message : 'Please enter offer title.'
				});
			}

			if(attrs.limitedQuantity){
				if (!attrs.quantity) {
					errors.push({
						name : 'quantity',
						message : 'Please fill quantity field.'
					});
				}else if (!number_regex.test(attrs.quantity)) {
					errors.push({
						name : 'quantity',
						message : 'Quantity is not valid'
					});
				}
			}

			return errors.length > 0 ? errors : false;
		},
		
		parse: function(response){
            var parsedObject={};
            
            parsedObject.id = response.id;
            parsedObject.title = response.title;
            parsedObject.description = response.description;
            parsedObject.startDate = new Date(response.startDate);
            parsedObject.endDate = new Date(response.endDate);
            parsedObject.limitedQuantity = response.limitedQuantity;
            parsedObject.quantity = response.quantity;
            parsedObject.validity = response.validity;
           
            return parsedObject;
            
        }

	});
	return crossper.Offer;
});

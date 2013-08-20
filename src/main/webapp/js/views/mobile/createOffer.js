define([ "jQuery",
         "underscore", 
         "backbone", 
         "models/offer",
		 "text!templates/mobile/business-sign-up-step2.html"],
		function($, _, Backbone, Offer, createOffer) {

		var crossper = crossper || {};
	
		crossper.CreateOfferView = Backbone.View.extend({
	
			createOfferViewTemplate : Handlebars.compile(createOffer),
	
			model : new Offer(),
			
			tagName:"section",
			
			className:"clearfix setting-wrapper",
	
			events : {
				"click #add-offer-btn" : "addOffer",
				"keyup input" : "keyUpValidation",
				"change input" : "keyUpValidation",
				"keyup [name='description']" : "setDescription",
				//"change input[name=limitedQuantity]" : "togglelimitedQty",
				//"change input[name=startDate]" : "setStartDate",
				//"change input[name=endDate]" : "setEndDate",
				//"change input[name=validity]" : "setValidity"
			},
	
			initialize : function() {
				
				this.$el.attr('data-role', 'content');
				this.$el.attr('id', 'containerSetcion');
				
				this.render();
			},
	
			render : function() {
				this.$el.html(this.createOfferViewTemplate(this.model.toJSON()));
				return this;
			},
	
			addOffer : function() {
				var validationResult = this.model.validate(this.model.toJSON());
	
				if (validationResult.length > 0) {
					this.showErrors(validationResult);
				} else {
					this.options.vent.trigger("createOfferSubmit");
				}
	
			},
	
			/**
			 * validations while typing
			 */
			keyUpValidation : function(element) {
				var ele = element.currentTarget;
				var val = ele.name;
				switch (ele.name) {
				case "title":
					this.validateForBlank(ele);
					this.model.set({
						'title' : ele.value
					});
					break;
	
				case "startDate":
					this.model.set({
						'startDate' : new Date(Date.parse(ele.value))
					});
					break;
	
				case "endDate":
					this.model.set({
						'endDate' : new Date(Date.parse(ele.value))
					});
					break;
	
				case "quantity":
					this.validateNumber(ele);
					this.model.set({
						'quantity' : ele.value
					});
					break;
					
				case "limitedQuantity":
					var isLimited=this.$("input[name=limitedQuantity]").prop("checked");
					this.toggleQuantity(isLimited);
					this.model.set({
						'limitedQuantity' : isLimited
					});
					break;
					
				case "validity":
					this.model.set({
						'validity' : this.$("input[name=validity]:checked").val()
					});
					break;
				}
	
			},
	
			toggleQuantity:function(isLimited){
				if(isLimited){
					this.$el.find("[name=quantity]").textinput('enable');
				}else{
					this.$el.find("[name=quantity]").textinput('disable');
					this.$el.find("[name=quantity]").val("");
				}
			},
			
			setDescription : function(ele) {
				this.model.set({
					'description' : ele.srcElement.value
				});
			},
	
			validateForBlank : function(ele) {
				var val = ele.value;
	
				if (val == "") {
					this.showIncorrect(ele);
					return false;
				} else {
					this.showCorrect(ele);
					return true;
				}
			},
			
			validateNumber:function(ele){
				// Check if string is a whole number(digits only).
				var isWhole_re       = /^\s*\d+\s*$/;
				var isNumeric= String (ele.value).search (isWhole_re) != -1;

				var val = ele.value;

				if (isNumeric) {
					this.showCorrect(ele);
					return false;
				} else {
					this.showIncorrect(ele);
					return true;
				}
			},
			
			showErrors : function(errors) {
	
				this.$('input').removeClass('error-border');
				this.$('input').addClass('sucess-border');
	
				this.$('[id$="ErrorContainer"]').text("");
				this.$('[id$="ErrorContainer"]').hide();
				this.$('[id$="ErrorAsteric"]').hide();
	
				_.each(errors, function(error) {
					var errorContainer = this
							.$('#' + error.name + 'ErrorContainer');
					var errorAsteric = this.$('#' + error.name + 'ErrorAsteric');
	
					this.$("[name=" + error.name + "]")
							.removeClass('sucess-border');
					this.$("[name=" + error.name + "]").addClass('error-border');
	
					errorContainer.text(error.message);
					errorContainer.show();
					errorAsteric.show();
				}, this);
			},
	
			showIncorrect : function(ele) {
				$(ele).next("span").removeClass("sucess-icon");
				$(ele).next("span").addClass("error-icon");
				$(ele).next("span").show();
				
				$(ele).addClass('error-border');
				$(ele).removeClass('sucess-border');
			},
	
			showCorrect : function(ele) {
				$(ele).next("span").removeClass("error-icon");
				$(ele).next("span").addClass("sucess-icon");
				$(ele).next("span").show();
				
				$(ele).siblings('[id$="ErrorContainer"]').text("");
				$(ele).siblings('[id$="ErrorContainer"]').hide();
				$(ele).siblings('label').find('[id$="ErrorAsteric"]').hide();
				$(ele).removeClass('error-border');
				$(ele).addClass('sucess-border');
			}
	
		});
		return crossper.CreateOfferView;
		});

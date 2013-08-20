define([ "jQuery",
         "underscore", 
         "backbone", 
         "models/offer",
		 "text!templates/web/business-sign-up-step2.html"],
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
				"keyup [name='description']" : "changeDescription",
                "click #prev-btn" : "goPrevious"
			},
	
			initialize : function() {
				
				this.render();
			},
	
			render : function() {
				this.$el.html(this.createOfferViewTemplate(this.model.toJSON()));
				var self=this;
                                
				require(["datepicker"],function(){
					self.$el.find("[id=dpEndDate]").datepicker({autoclose: true});
					self.$el.find("[id=dpStartDate]").datepicker({autoclose: true});
				});
                $("#title").focus();
                                   
				return this;
			},
            goPrevious : function() {
                this.options.vent.trigger("goToStepOne");
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
					
				case "limitedQuantity":
					var isLimited=this.$("input[name=limitedQuantity]").prop("checked");
					this.toggleQuantity(isLimited);
					this.model.set({
						'limitedQuantity' : isLimited
					});
					break;
					
				case "quantity":
					this.validateForBlank(ele);
					this.model.set({
						'quantity' : ele.value
					});
					break;
               case "validity":
					this.model.set({
						'validity' : this.$("input[name=validity]:checked").val()
					});
					break;
				}
                                
	
			},
	
			changeDescription : function(ele) {
				this.model.set({
					'description' : ele.currentTarget.value
				});
			},
	
			
			toggleQuantity:function(isLimited){
				if(isLimited){
					this.$el.find("[name=quantity]").removeAttr('disabled');
				}else{
					this.$el.find("[name=quantity]").attr('disabled','disabled');
					this.$el.find("[name=quantity]").val("");
				}
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
			},
	
			showCorrect : function(ele) {
				$(ele).next("span").removeClass("error-icon");
				$(ele).next("span").addClass("sucess-icon");
				$(ele).next("span").show();
			}
	
		});
		return crossper.CreateOfferView;
		});

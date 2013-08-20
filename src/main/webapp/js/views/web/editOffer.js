define(["jQuery",
        "underscore",
        "backbone",
		"text!templates/web/editOffer.html" ],
		function($, _, Backbone,editOffer) {

	var crossper = crossper || {};

	crossper.EditOfferView = Backbone.View.extend({
		
		editOfferTemplate : Handlebars.compile(editOffer),

		tagName:"div",
		
		id:"editoffer",
		
		events : {
			"click #saveOffer":"saveOffer",
			
			"keyup input" : "keyUpValidation",
			"change input" : "keyUpValidation",
			"keyup [name='description']" : "changeDescription"
		},

		initialize : function() {
			
			this.event = this.options.event;	
			
			this.render();
		},

		render : function() {
			this.$el.html(this.editOfferTemplate(this.model.toJSON()));
			
			require(["datepicker"],function(){
				self.$el.find("[id=dpEndDate]").datepicker({autoclose: true});
				self.$el.find("[id=dpStartDate]").datepicker({autoclose: true});
			});
			return this;
		},

		keyUpValidation : function(event) {
			var ele = event.currentTarget;
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
		
		showIncorrect : function(ele) {
//			$(ele).next("span").removeClass("sucess-icon");
//			$(ele).next("span").addClass("error-icon");
//			$(ele).next("span").show();
		},

		showCorrect : function(ele) {
//			$(ele).next("span").removeClass("error-icon");
//			$(ele).next("span").addClass("sucess-icon");
//			$(ele).next("span").show();
		},
		
		saveOffer:function(){
			this.model.url="/crossper/businesses/"+window.businessInfo.businessId+"/offers";
			var self=this;
			var options = {
			    	error: function(model, error){
			    		self.showErrors(error);
			    	},
			    	success: function(data, textStatus, jqXHR) {
			    		self.event.trigger("offers");
			    	}
			      };
			this.model.save({},options);
		},
		
		showErrors : function(error) {
		},

		

	});
	return crossper.EditOfferView;
});

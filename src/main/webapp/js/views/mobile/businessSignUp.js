define(
    ["jQuery",
        "underscore",
        "backbone",
        "models/businessInfo",
        "models/offer",
        "models/businessSignUp",
        "mobileViews/businessInfo",
        "mobileViews/createOffer",
        "mobileViews/signupConfirmation",
        "text!templates/mobile/business-sign-up-wrapper.html",
        "crossper-util",
        "jquery-transport",
        "jquery-file-upload"

    ], function ($, _, Backbone, BusinessInfo, Offer, BusinessSignUp, BusinessInfoView, CreateOfferView, SignupConfirmationView, businessSignUpWrapper, CrossperUtil) {


        var crossper = crossper || {};

        crossper.BusinessSignUpView = Backbone.View.extend({

            wrapperTemplate: Handlebars.compile(businessSignUpWrapper),

            model: new BusinessSignUp(),

            id: "businessSignUpView",

            events: {
                "click #globalBackBtn": "goBack"
            },

            initialize: function () {
                this.render();

                this.options.vent.bind("businessInfoSubmit",
                    this.businessInfoSubmit, this);
                this.options.vent.bind("createOfferSubmit", this.createOfferSubmit,
                    this);
                this.options.vent.bind("emailQRCode", this.emailQRCode, this);

                this.options.vent.bind("goToStepOne", this.goToStepOne, this);
                this.options.vent.bind("goToStepTwo", this.goToStepTwo, this);
                this.options.vent.bind("goToStepThree", this.goToStepThree, this);

            },

            render: function () {
                this.$el.append(this.wrapperTemplate(this.model));

                this.goToStepOne();

                return this;
            },

            goBack: function () {
                window.history.back();
            },

            /**
             * business Info
             */
            goToStepOne: function () {
                this.options.router.navigate("stepOne", {
                    trigger: false
                });
                if (this.businessInfoView == undefined) {
                    this.businessInfoView = new BusinessInfoView({
                        vent: this.options.vent
                    });
                    this.$el.find("#containerSetcion").replaceWith(this.businessInfoView.el);
                } else {
                    this.$el.find("#containerSetcion").replaceWith(this.businessInfoView.el);
                    this.businessInfoView.delegateEvents();
                }
            },

            /**
             * create New Offer
             */
            goToStepTwo: function () {
                this.options.router.navigate("stepTwo", {
                    trigger: false
                });
                if (this.createOfferView == undefined) {
                    this.createOfferView = new CreateOfferView({
                        vent: this.options.vent
                    });
                    this.$el.find("#containerSetcion").replaceWith(this.createOfferView.el);
                    
                    var self=this;
                    require(["calbox"],function(){                    	
                    	self.createOfferView.$el.trigger('create');
                    });
                } else {
                    this.$el.find("#containerSetcion").replaceWith(this.createOfferView.el);
                    this.createOfferView.delegateEvents();
                }
            },

            /**
             * Email QR code
             */
            goToStepThree: function () {
                this.options.router.navigate("stepThree", {
                    trigger: false
                });
                if (this.signupConfirmationView == undefined) {
                    this.signupConfirmationView = new SignupConfirmationView({
                        vent: this.options.vent
                    });
                    this.$el.find("#containerSetcion").replaceWith(this.signupConfirmationView.el);
                    this.signupConfirmationView.$el.trigger('create');
                } else {
                    this.$el.find("#containerSetcion").replaceWith(this.signupConfirmationView.el);
                    this.signupConfirmationView.delegateEvents();
                }
            },

            businessInfoSubmit: function () {
                this.goToStepTwo();
            },

            createOfferSubmit: function () {
            	
            	   delete this.businessInfoView.model.attributes.confirmPassword;

                   this.model.set({
                       businessInfo: this.businessInfoView.model,
                       offer: this.createOfferView.model
                   });

                   //TODO:write error and success callbacks to controll the flow
                   var theModel = this.model;
                   var businessInfoView = this.businessInfoView;
                   var self=this;
                   this.model.save({}, {
                       success: function (model, response, options) {
                           console.log(response);
                           if(businessInfoView.getBusinessPhotoImgEle()!=undefined){
                        	   businessInfoView.getBusinessPhotoImgEle().attr('data-url', CrossperUtil.getURL("businesses/" + theModel.get('id') + "/businessPhoto"));
                        	   businessInfoView.getBusinessPhotoImgEle().fileupload({
                        		   dataType: 'json',
                        		   formData: {id: theModel.get('id')},
                        		   done: function (e, data) {
                        			   console.log("successful file upload");
                        		   }
                        	   });
                        	   
                        	   businessInfoView.getBusinessPhotoImgEle().change();
                           }
                           self.goToStepThree();
                       },
                       error:function(model, xhr, options){
                    	   console.log(model);
                    	   console.log(options);
                    	   self.createOfferView.$el.find("#serverErrorDiv").html('<strong>Error!</strong> <span id="serverError">'+xhr.responseText+'</span>');
                    	   self.createOfferView.$el.find("#serverErrorDiv").show();
                       }
                   });
            },

            /**
             * save all models to server.
             */
            emailQRCode: function () {

            	var self=this;
            	 
            	$.ajax({
                    
                    url : '/crossper/businesses/'+this.model.get('id')+'/emailQrCode',

					contentType : "application/json",

					success : function(data) {
						 this.options.router.navigate("", {
			                    trigger: true
			                });
					},
					error:function(model){
                 	   console.log(model);
                 	   this.options.router.navigate("", {
		                    trigger: true
		                });
                    },
					type : "POST"
				})
				
            }

        });
        return crossper.BusinessSignUpView;
    });

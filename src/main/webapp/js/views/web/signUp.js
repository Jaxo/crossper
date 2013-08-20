    
   define(
  ["jQuery",
   "underscore",
   "backbone",
   "models/businessInfo",
   "models/offer",
   "models/businessSignUp",
   "webViews/businessInfo",
   "webViews/createOffer",
   "webViews/signupConfirmation",
   "text!templates/web/business-sign-up-step1.html",
    "crossper-util",
   "jquery-transport",
    "jquery-file-upload",
    "bootstrap-min",
    "custom-form-elements",
    "jquery-sb"
  ], function($,_,Backbone,BusinessInfo,Offer,BusinessSignUp,BusinessInfoView,CreateOfferView,SignupConfirmationView,businessSignUpWrapper,CrossperUtil) {
  
  var crossper = crossper || {};
  
  crossper.SignUpView  = Backbone.View.extend({

	el:"#middleContainer",
        model : new BusinessSignUp(),
        infoModel: new BusinessInfo(),
        signUpTemplate: Handlebars.compile(businessSignUpWrapper),
	id : "businessSignupView",
    events: {
//      "click .saveOfferBtn"   : "saveOffer",
    },
    initialize: function () {
                this.render();

                this.options.event.bind("businessInfoSubmit",
                    this.businessInfoSubmit, this);
                this.options.event.bind("createOfferSubmit", this.createOfferSubmit,
                    this);
                this.options.event.bind("emailQRCode", this.emailQRCode, this);

                this.options.event.bind("goToStepOne", this.goToStepOne, this);
                this.options.event.bind("goToStepTwo", this.goToStepTwo, this);
                this.options.event.bind("goToStepThree", this.goToStepThree, this);

            },
    render: function() {
		this.$el.html(this.signUpTemplate(this.infoModel));
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
                this.options.router.navigate("web/stepOne", {
                    trigger: false
                });
                if (this.businessInfoView == undefined) {
                    this.businessInfoView = new BusinessInfoView({
                        vent: this.options.event
                    });
                    this.$el.html(this.businessInfoView.el);
                } else {
                    this.$el.html(this.businessInfoView.el);
                    this.businessInfoView.delegateEvents();
                }
            },

            /**
             * create New Offer
             */
            goToStepTwo: function () {
                this.options.router.navigate("web/stepTwo", {
                    trigger: false
                });
                if (this.createOfferView == undefined) {
                    this.createOfferView = new CreateOfferView({
                        vent: this.options.event
                    });
                    this.$el.html(this.createOfferView.el);
                    this.createOfferView.$el.trigger('create');
                } else {
                    this.$el.html(this.createOfferView.el);
                    this.createOfferView.delegateEvents();
                }
            },

            /**
             * Email QR code
             */
            goToStepThree: function () {
                this.options.router.navigate("web/stepThree", {
                    trigger: false
                });
                if (this.signupConfirmationView == undefined) {
                    this.signupConfirmationView = new SignupConfirmationView({
                        vent: this.options.event
                    });
                    this.$el.html(this.signupConfirmationView.el);
                    this.signupConfirmationView.$el.trigger('create');
                } else {
                    this.$el.html(this.signupConfirmationView.el);
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
                    if (this.model.get('id') !== null ) {
                        if (this.model.get('id').length >0 ) {
                            self.createOfferView.$el.find("#serverErrorDiv").html('<strong>Error!</strong> <span id="serverError">'+'business created '+'</span>');
                    	   self.createOfferView.$el.find("#serverErrorDiv").show();
                           return;
                        }
                    }
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
            
            printPDF : function( pdfUrl ) 
            {
                var downloadUrl = CrossperUtil.getServerURL(pdfUrl);
                var w = window.open( downloadUrl, 'Print QRCode');
                w.print();
            },
            /**
             * email qr code.
             */
            emailQRCode: function () {
                        var theModel = this.model;
                        var pdfUrl = "businesses/" + theModel.get('id') + "/qrCode";
                        this.printPDF(pdfUrl);
                    }
               
          
        });

  return  crossper.SignUpView;
});

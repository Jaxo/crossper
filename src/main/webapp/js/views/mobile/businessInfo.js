define(
		[ "jQuery",
		  "underscore", 
		  "backbone",
		  "crossper-util",
		  "models/businessInfo",
		  "text!templates/mobile/business-sign-up-step1.html"],
		function($, _, Backbone,CrossperUtil, BusinessInfo, businessInfo) {

			var crossper = crossper || {};

			crossper.BusinessInfoView = Backbone.View
					.extend({

						businessInfoTemplate : Handlebars.compile(businessInfo),

						model : new BusinessInfo(),
						
						tagName:"section",
						
						className:"clearfix setting-wrapper",

						events : {
							"click #sign-up-button" : "submitForm",
							"keyup input" : "keyUpValidation",
							"change input" : "keyUpValidation",
							"change #select-category" : "setCategories",
							"change #select-subcategory" : "setSubcategories",
                            "change #img-uploader" : 'uploadPhoto'

						},

						initialize : function() {
							// this.listenTo(this.model, 'change', this.render);
							this.$el.attr('data-role', 'content');
							this.$el.attr('id', 'containerSetcion');
							this.render();
							this.loadCategories();
						},

						render : function() {
							// append the compiled template into view div
							// container
							this.$el.html(this.businessInfoTemplate(this.model
									.toJSON()));
							this.$el.trigger('create');
							this.updateCategories();
							if(this.model.category!=''){
								
							}
							return this;
						},

						browseForImage:function(){
							$("#businessLogo").click();
						},
						/**
						 * validations while typing
						 */
						keyUpValidation : function(element) {
							var ele = element.currentTarget;
							var val = ele.value;
							switch (ele.name) {
							case "name":
								this.validateForBlank(ele);
								this.model.set({
									'name' : ele.value.trim()
								});
								break;

							case "address":
								this.validateForBlank(ele);
								this.model.set({
									'address' : ele.value.trim()
								});
								break;

							case "website":
                                                                if (ele.value.trim()) {
                                                                    var url_regex = /^((http|https|ftp|ftps):\/\/)?([a-z0-9\-]+\.)?[a-z0-9\-]+\.[a-z0-9]{2,4}(\.[a-z0-9]{2,4})?(\/.*)?$/i;
                                                                    this.validateRegex(ele,url_regex);
                                                                }
								this.model.set({
									'website' : ele.value.trim()
								});
								break;
								
							case "averagePrice":
								this.validateNumber(ele);
								this.model.set({
									'averagePrice' : ele.value.trim()
								});
								break;
								
							case "email":

								var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
								this.validateRegex(ele,email_regex);
								this.model.set({
									'email' : ele.value.trim()
								});

								
								break;
                                                        case "city":
								this.validateForBlank(ele);
								this.model.set({
									'city' : ele.value.trim()
								});
								break;

							case "state":
								this.validateForBlank(ele);
								this.model.set({
									'state' : ele.value.trim()
								});
								break;

							case "zipCode":
								var zipcode_regex= /(^\d{5}$)|(^\d{5}-\d{4}$)/;
								this.validateRegex(ele,zipcode_regex);
								this.model.set({
									'zipCode' : ele.value.trim()
								});
								break;

							case "phone":
								this.validatePhoneUs(ele);
								this.model.set({
									'phone' : ele.value.trim()
								});
								break;
								
							case "password":
								this.validatePassword(ele);
								this.model.set({
									'password' : ele.value
								});
								break;
								
							case "confirmPassword":
								this.validateConfirmPassword(ele);
								this.model.set({
									'confirmPassword' : ele.value
								});
								
									
								break;
							}

						},

						/**
						 * saves form values in localstorage
						 */
						submitForm : function() {

							var validationResult = this.model.validate(this.model.toJSON());

							console.log(validationResult);

							if (validationResult.length > 0) {
								this.showErrors(validationResult);
							} else {
								this.options.vent.trigger("businessInfoSubmit");
							}
						},

						validateForBlank : function(ele) {
							
							// Check if string is non-blank
							var isNonblank_re    = /\S/;
							
							var isNotBlank= String (ele.value).search (isNonblank_re) != -1;

							var val = ele.value;

							if (isNotBlank) {
								this.showCorrect(ele);
								return false;
							} else {
								this.showIncorrect(ele);
								return true;
							}
						},

						validatePhoneUs:function(ele) {
							var phone_regex = /^(\(\d{3}\)|\d{3})(-?| ?)\d{3}(-?| ?)\d{4}$/;
							if(ele.value.trim().match(phone_regex)!=null){
								this.showCorrect(ele);
								return false;
							}else{
								this.showIncorrect(ele);
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
						
						validateRegex:function(ele,regex){
							if (!regex.test(ele.value)) {
								this.showIncorrect(ele);
								return false;
							} else {
								this.showCorrect(ele);
								return true;
							}
						},
						
						validatePassword:function(ele){
							var val = ele.value.trim();
							
							if(val.length<6){
								this.showIncorrect(ele);
							}else{
								this.showCorrect(ele);
							}
						},
						
						validateConfirmPassword:function(ele){
							var val = ele.value.trim();
							
							if(val.length<6){
								this.showIncorrect(ele);
							}else{
								var pass=this.$el.find("[name=password]").val();
								if(pass==val){
									this.showCorrect(ele);
								}else{
									this.showIncorrect(ele);
								}
							}
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
						},

						showErrors : function(errors) {

							this.$('input').removeClass('error-border');
							this.$('input').addClass('sucess-border');

							this.$('[id$="ErrorContainer"]').text("");
							this.$('[id$="ErrorContainer"]').hide();
							this.$('[id$="ErrorAsteric"]').hide();

							_.each(errors, function(error) {
								var errorContainer = this.$('#' + error.name
										+ 'ErrorContainer');
								var errorAsteric = this.$('#' + error.name
										+ 'ErrorAsteric');

								if (this.$("[name=" + error.name + "]").prop("tagName")=="INPUT") {
									this.$("[name=" + error.name + "]").removeClass('sucess-border');
									this.$("[name=" + error.name + "]").addClass('error-border');
								}

								errorContainer.text(error.message);
								errorContainer.show();
								errorAsteric.show();
							}, this);
						},

						setSubcategories : function() {
							this.model.set({
								subCategory : this.$el.find("#select-subcategory").val()
							});
							
							if(this.$el.find("#select-subcategory").val()!='Select SubCategory'){
								this.$el.find("#select-subcategory").parents('li').find('[id$="ErrorContainer"]').text("");
								this.$el.find("#select-subcategory").parents('li').find('[id$="ErrorContainer"]').hide();
								this.$el.find("#select-subcategory").parents('li').find('[id$="ErrorAsteric"]').hide();
							}
						},

						setCategories : function() {
							
							
							this.model.set({
								category : this.$el.find("#select-category").val()
							});
							this.model.set({
								subCategory : "Select SubCategory"
							});
							
							if(this.$el.find("#select-category").val()!='Select Category'){
								this.$el.find("#select-category").parents('li').find('[id$="ErrorContainer"]').text("");
								this.$el.find("#select-category").parents('li').find('[id$="ErrorContainer"]').hide();
								this.$el.find("#select-category").parents('li').find('[id$="ErrorAsteric"]').hide();
							}
							
							this.updateSubcategories();
						},

						updateSubcategories : function() {
							this.$el.find('#select-subcategory').html("");

							this.$el.find('#select-subcategory').append($('<option>', {
								value : "Select SubCategory",
								text : "Select SubCategory"
							}));

							me = this;

							$.each(this.categoryData, function(i, category) {

								if (me.model.get('category') == category.name) {

									var subcategory = category.subcategories;
									 
									$.each(subcategory, function(i,
											subcategoryItem) {
										me.$el.find('#select-subcategory').append(
												$('<option>', {
													value : subcategoryItem,
													text : subcategoryItem
												}));
									});

								}

							});

							this.$el.find('#select-subcategory').trigger('create');
							this.$el.find('#select-subcategory')
									.selectmenu("refresh", true);

						},

						updateCategories : function() {
							if (this.categoryData != undefined) {

								this.$el.find('#select-category').append($('<option>', {
									value : "Select Category",
									text : "Select Category"
								}));
	
								var self=this;
								$.each(this.categoryData,
										function(i, category) {
											self.$el.find('#select-category').append(
													$('<option>', {
														value : category.name,
														text : category.name
													}));
										});

								this.$el.find('#select-category').trigger('create');
								this.$el.find('#select-category').selectmenu("refresh",
										true);
								this.model.set({
									category : "Select Category"
								});
							}
						},

						/**
						 * loads business categories and respective subcategories
						 */
						loadCategories : function() {
							var me = this;

							$.ajax({
                                                             
                                        url : '/crossper/businesses/categories',
		
										contentType : "application/json",

										success : function(data) {
											console.log(data);
											me.categoryData = data;
											me.updateCategories();
										},

										type : "GET"
									}

							)
						},

                        uploadPhoto: function(event){
                            this.photo = event.target.files[0];

                            if(!this.photo.type.match('image.*')){
                                this.photo = null;
                                return;
                            }

                            if (window.FileReader) {
                                var fileReader = new FileReader();

                                fileReader.onload = (function (theFile) {
                                    return function (e) {
                                        var newImage = ['<img class="thumbnails" height="50px" width="50px" src="', e.target.result,
                                            '" title="', escape(theFile.name), '"/>'].join('');
                                        $('span.photo-block').html(newImage);
                                    }
                                })(this.photo);

                                fileReader.readAsDataURL(this.photo);
                            } else {

                            }


                        },

                        getBusinessPhoto: function(){
                            return this.photo;
                        },

                        getBusinessPhotoImgEle: function(){
                            return $('#img-uploader', this.$el);;
						}

					});
			return crossper.BusinessInfoView;
		});

define([ "jQuery",
         "underscore", 
         "backbone", 
		 "text!templates/mobile/categories.html",
		 "text!templates/mobile/subCategories.html"],
		function($, _, Backbone,categories,subCategories) {

		var crossper = crossper || {};
	
		crossper.CategoriesView = Backbone.View.extend({
	
			categoriesViewTemplate : Handlebars.compile(categories),
			subCategoriesViewTemplate : Handlebars.compile(subCategories),
	
			tagName:"ul",
			
			id:"categoriesList",
			
			events : {
				"click [id^='category-items_']" : "filterOffers", 
				"click .ui-icon-arrow-r"		: "selectSubCategory",
				"click [id^='subCategory-items_']" : "filterOffers" 
			},
	
			loadingCategories:false,
			
			initialize : function() {
				this.event=this.options.event;
				
				this.$el.attr('data-role','listview');
				this.$el.attr('data-divider-theme','b');
				this.$el.attr('data-inset',true);
				this.$el.attr('data-divider-theme','b');
				this.render();
			},
	
			render : function() {
				if(this.catagories==undefined && this.loadingCategories==false){
					this.loadCategories();
				}else{
					this.setCategories();
				}
				return this;
			},
			
			setCategories:function(){
				this.$el.html("");
				this.$el.append(this.categoriesViewTemplate({"categoryData":this.catagories}));
				this.$el.listview();
				this.delegateEvents();
			},
			
			/**
			 * loads business categories and respective subcategories
			 */
			loadCategories : function() {
				this.loadingCategories=true;
				var self = this;
				$.mobile.showPageLoadingMsg();
				$.ajax({
                                                 
                            url : '/crossper/businesses/categories',

							contentType : "application/json",

							success : function(data) {
								$.mobile.hidePageLoadingMsg();
								
								self.categoryData = data;
								self.catagories=new Array();
								
								$.each(self.categoryData,function(i, category) {
									self.catagories.push(category.name);
								});

								self.render();
							},
							error:function(data){
								$.mobile.hidePageLoadingMsg();
							},

							type : "GET"
						}

				)
			},
			
			
			selectSubCategory:function(event){
				var categoryName =$(event.currentTarget).siblings(".ui-btn-text").find("[id^='category-items']").attr('id').split("_")[1];

					var subCategories ;
				
					$.each(this.categoryData,function(i, category) {
						if(category.name==categoryName){
							subCategories=category.subcategories;
						}
					});
				
					
					this.$el.html("");
					this.$el.append(this.subCategoriesViewTemplate({"subCategoryData":subCategories}));
					this.$el.listview('refresh');
					
					this.delegateEvents();

			},
			
			filterOffers:function(event){
				var subCategory=event.currentTarget.id.split("_")[1];
				this.event.trigger("categorizeOffers",subCategory);
			}
			
	
		});
		return crossper.CategoriesView;
		});

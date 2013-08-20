   define(
  ["jQuery",
   "handlebar",
   "backbone",
   "models/offer",
   "collections/offers",
   "mobileViews/offer",
   "mobileViews/scannedOffer",
   "mobileViews/categories",
   "mobileViews/message",
   "text!templates/mobile/offersWrapper.html"
  ], function($,Handlebars,Backbone,Offer,Offers,OfferView,ScannedOfferView,CategoriesView,MessageView,offerWrapperTemplateHtml) {
  
  var crossper = crossper || {};
  
  crossper.OfferWrapperView = Backbone.View.extend({

    offerWrapperTemplate:	Handlebars.compile(offerWrapperTemplateHtml),
    
    model:new Offers(),
    
    scanModel:new Offers(),

    menuStatus:false,
    
    initialize: function(){
    	this.router=this.options.router;
    	this.event=this.options.event;
    	
		this.render();
		
		var self = this;
		window.downloadOfferForPromoter = function(url){
			self.downloadOffers.call(self, url);
		};

		this.listenTo(this.model, 'sync', this.addAll);
		this.listenTo(this.scanModel, 'sync', this.addScannedOffer);
//		this.listenTo(this.model, 'add', this.addScannedInOfferContainer);
		this.event.bind("categories", this.categories, this);
		this.event.bind("categorizeOffers", this.categorizeOffers, this);
		this.event.bind("showSelectedOffer", this.showSelectedOffer, this);
		 
		this.model.userId= window.userInfo.userId;
		
		this.loadOffers();
    },
    events: {
        "click #logoutLink"   : "logout",
        "click #scanOffer"   : "scanOffer",
		"click .offer-border":"offerClick",
		"click #categoriesButton":"categories",
		"click a.showMenu":"toggleMenu",
		"click #allOffersButton":"loadOffers",
		
		"click #searchIcon" : "searchToggle",
		"change #searchInput" : "searchText"
      },
      
	  offerClick:function(ele){
		 ele.addClass("offer-border-active");
		 ele.find(".decription-block").show();
		 ele.find(".decription-block").removeClass("display-none");
		 ele.find(".deleteoffer-icon").show();	
		 $("[data-position='fixed']").fixedtoolbar('show');
	  },
	  
      logout:function(){
	  
		  var me = this;
			  $.ajax({
                             
				  url: '/crossper/mobile/j_spring_security_logout', 
	              
	              type: 'POST',
	              
	              success: function(data, textStatus, jqXHR) {
	                  Backbone.history.navigate('', true);
                          
	              },
	              
	              error: function(jqXHR, textStatus, errorThrown) {
	            	//me.showErrors();
	              }
	          });
			  
	
      },
      
      scanOffer:function(){
    	  window.location="http://www.crossperapp.com";    
//    	  this.downloadOffers("url");
      },
      
      downloadOffers:function(url){
    	  console.log("inside downloadOffers ");
    	  console.log("url -- "+url);
    	  
    	  //this.$el.find("#offerContainer").html("");
		  this.scanModel.url=url+"/"+this.model.userId;
    	  
		  //this.scanModel.url="/crossper/consumers/"+this.model.userId+"/offers";
    	  
		  this.scanModel.fetch();    
      },
      
      render: function(){ 
        this.$el.append(this.offerWrapperTemplate()); 
   
        return this; 
      },
      
      loadOffers:function(){
    	this.$el.find("#offerContainer").html("");
  		this.model.url="/crossper/consumers/"+this.model.userId+"/offers";
		this.model.fetch();    	  
      },
      
      addOffer:function(offer){
    	  var offerView = new OfferView({model: offer});
		  
		  this.$el.find("#offerContainer").append(offerView.render().el);
		  
		  offerView.$el.trigger('create');
		  
		  offerView.applyToggle();
		
      },
      
      addOfferToPopup:function(offer){
    	  var scannedOfferView = new ScannedOfferView({model: offer,event:this.event});
		  
		  this.$el.find("#scanPopup").append(scannedOfferView.render().el);
		  
		  scannedOfferView.$el.trigger('create');
      },
      
      addScannedOffer:function(offer){
    	  this.$el.find("#scanPopup").html("");
		  this.scanModel.each(this.addOfferToPopup, this);
		  this.$el.find("#scanPopup").popup( "open");
      },
      
      categories:function(){
    	this.options.router.navigate("categorization", {
              trigger: false
          });
          if (this.categoriesView == undefined) {
              this.categoriesView = new CategoriesView({
                  event: this.event
              });
              this.$el.find("#offerContainer").html(this.categoriesView.el);
          } else {
			  this.$el.find("#offerContainer").html(this.categoriesView.el);
        	  this.categoriesView.setCategories();
              this.categoriesView.delegateEvents();
          }
      },
      
      categorizeOffers:function(subCategory){
    	this.$el.find("#offerContainer").html("");
    	this.model.url="/crossper/consumers/"+this.model.userId+"/offers?filter="+subCategory;
  		this.model.fetch();
      },
      
      showSelectedOffer:function(selectedOffer){
    	  this.$el.find("#scanPopup").popup("close");
		  this.$el.find("#offerContainer").html("");
		  //this.model.add(this.scanModel.toJSON());
		  
		  this.model.add(this.scanModel.toJSON());
		  
    	  this.model.each(this.addOffer, this);  
    	  $.mobile.silentScroll(this.$el.find("#offerContainer_"+selectedOffer.id).offset().top);
    	  this.$el.find("#offerContainer_"+selectedOffer.id).click();
      },
      
      addAll:function(offer){
    	  if(offer.length==0){
    		  console.log("inside offerWrapper...starting if");
    		  var msgView=new MessageView({msg:"No Offers Available ..."});
    		  this.$el.find("#offerContainer").html(msgView.$el);
    		  console.log("inside offerWrapper...el..  "+msgView.$el);
//    		  this.$el.find("#notificationPopup").html("No Offers Available ...");
//    		  this.$el.find("#notificationPopup").popup( "open");
    	  }else{
    		  this.model.each(this.addOffer, this);
    	  }
 	  },
	  
 	  toggleMenu:function(){
 		  var self=this;
		if (this.menuStatus != true) {
			$(".ui-page-active").animate({
				marginLeft : "275px",
			}, 300, function() {
				self.menuStatus = true;
			});
			$("a.showMenu span").addClass("ci-menu-icon-active");
			$("a.showMenu span").removeClass("ci-menu-icon");
			return false;
		} else {
			$(".ui-page-active").animate({
				marginLeft : "0px",
			}, 300, function() {
				self.menuStatus = false;
			});
			$("a.showMenu span").removeClass("ci-menu-icon-active");
			$("a.showMenu span").addClass("ci-menu-icon");
			return false;
		}
 	  },
 	  
  	 searchToggle:function(){
   		if(this.$el.find("#searchInputDiv").is(":visible")){
   			this.$el.find("#searchInputDiv").hide();
   			this.$el.find("#navbarDiv").show();
   		}else{
   			this.$el.find("#searchInputDiv").show();
   			this.$el.find("#navbarDiv").hide();
   			
   		}
   	 },
   	 
   	searchText:function(){
  		this.$el.find("#offerContainer").html("");
  		var searchText=this.$el.find("#searchInput").val();
  		this.model.url="/crossper/consumers/"+this.model.userId+"/offers?search="+searchText;
  		this.model.fetch();
  	}
    
  });
  return  crossper.OfferWrapperView;
});

   define(
  ["jQuery",
   "handlebar",
   "backbone",
   "models/offer",
   "text!templates/mobile/offer.html"
  ], function($,Handlebars,Backbone,Offer,offerTemplateHtml) {
  
  var crossper = crossper || {};
  
  crossper.OfferView = Backbone.View.extend({

    offerTemplate:	Handlebars.compile(offerTemplateHtml),
    
    model:new Offer(),

    tagName:"div",
    
    className:'offer-block clearfix',
    
    initialize: function(){
		this.render();
		this.$el.attr('id',"offerContainer_"+this.model.id);
    },
    events: {
    	"click #claimOffer":"claimOffer",
    	"click #shareOffer":"shareOffer",
    	"click #directions":"directions",
    	"click #deleteOffer":"deleteOffer"
      },
      
      render: function(){ 
        this.$el.html(this.offerTemplate(this.model.toJSON())); 
   
        return this; 
      }, 
      
      claimOffer:function(event){
    	  event.stopPropagation();
    	  
    	  var self=this;
    	  
    	  var scanOffer={
    				userId:window.userInfo.userId,
    				publisherId:this.model.get('businessId'),
    				id:this.model.get('id')
    			 };

    	  		var postData=JSON.stringify(scanOffer);
    	    	  $.ajax({
    	              url : '/crossper/consumers/'+window.userInfo.userId+'/offers/'+this.model.get('id'),

    	             contentType: "application/json; charset=utf-8",
    				  
    					data: postData,
    		              
    					success : function(data) {
    						self.remove();
    					},

    					type : "POST"
    				});

      },
      
      shareOffer:function(event){
    	  event.stopPropagation();
      },
      
      directions:function(event){
    	  event.stopPropagation();
      },
      

      deleteOffer : function(event) {
			event.stopPropagation();

			var self = this;

			var scanOffer = {
				userId : window.userInfo.userId,
				publisherId : this.model.get('businessId'),
				id : this.model.get('id')
			};

			var postData = JSON.stringify(scanOffer);
			$.ajax({
				url : '/crossper/consumers/' + window.userInfo.userId
						+ '/offers/' + this.model.get('id'),

				contentType : "application/json; charset=utf-8",

				data : postData,

				success : function(data) {
					self.remove();
				},

				type : "DELETE"
			});
		},
      
      applyToggle:function(){
    	  this.$el.toggle(
			   function() {
					 $(this).children('.offer-description-block').find('.description').show();
					 var descHeight=$(this).children('.offer-description-block').height();
					 $(this).children('.offer-image-block').find('img').css('height',descHeight+32);
					 $(this).children('.offer-description-block').find('.deleteoffer-icon').show();
			   }, function() {
					 $(this).children('.offer-description-block').find('.description').hide();
					 $(this).children('.offer-image-block').find('img').css('height','100px');
					 $(this).children('.offer-description-block').find('.deleteoffer-icon').hide();
			   });
      }
    
  });
  return  crossper.OfferView;
});

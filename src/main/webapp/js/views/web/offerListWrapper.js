define([ 'backbone', 
         "handlebar", 
         "webViews/offer", 
         "collections/offers",
         ], function(Backbone, Handlebars,OfferView, Offers) {

			var OfferWrapperView = Backbone.View.extend({

				tagName:"ul",

				className:"current-offer-list",
				
				id:"offerContainer",
				
				model : new Offers,
				
				offerList:new Array(),
				
				initialize : function() {
					this.event = this.options.event;	
					
					this.listenTo(this.model, 'sync', this.addAll);
					
					this.render();
					
			  		this.model.url="/crossper/businesses/"+window.businessInfo.businessId+"/offers";
					this.model.fetch(); 
					
				},
				
				render:function(){
				//	this.$el.html(this.offerWrapperTemplate());
				},

				addNewOffer : function(offer) {
					var view = new OfferView({
						model : offer,
						event:this.event
					});
					this.$el.append(view.render().el);
					this.offerList.push(view);
				},

				addOffer : function(offer) {
//					OffersList.create(offer);
				},

				addAll:function(){
					this.$el.html("");
					var self=this;
					this.model.each(function(offer){
						self.addNewOffer(offer);
					});
				}
				
			});

			return OfferWrapperView;
		});

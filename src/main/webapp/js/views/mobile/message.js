define(
  ["jQuery",
   "handlebar",
   "backbone",
   "text!templates/mobile/message.html"
  ], function($,Handlebars,Backbone,messageTemplateHtml) {
  
  var crossper = crossper || {};
  
  crossper.MessageView = Backbone.View.extend({

	messageTemplate : Handlebars.compile(messageTemplateHtml),
    
    tagName:"div",
    
    className:'custom-message',
    
    initialize: function(){
    	console.log("inside MessageView...initialize");
		this.render();
    },
    events: {
      },
      
      render: function(){ 
    	console.log("inside MessageView...render...");
    	
        this.$el.html(this.messageTemplate({msg:this.options.msg})); 
   
        return this; 
      } 
    
  });
  return  crossper.MessageView;
});

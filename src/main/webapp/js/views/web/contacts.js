define(
  ["backbone",
   "handlebar",
   "models/contactDetails",
   "text!templates/web/contacts.html"
  ], function(Backbone,Handlebars, ContactDetails, contactsTemplate) {
  
  var crossper = crossper || {};
  
  crossper.ContactsView  = Backbone.View.extend({

	el:"#middleContainer",
        contactsViewTemplate:        Handlebars.compile(contactsTemplate),
        model : new ContactDetails,
	 initialize: function(){
                 this.model.fetch();
                 this.listenTo(this.model, 'sync', this.render);
		 //this.render();
	 },
     
        events: {
              "click #send-msg-btn"   : "sendMessage",
              "keyup input" : "keyUpValidation",
              "change input" : "keyUpValidation",
              "keyup [name='senderMessage']" : "changeMessage"
        },
        keyUpValidation : function(element) {
                        var ele = element.currentTarget;
                        var val = ele.value;
                        switch (ele.name) {
                        case "senderName":
                                this.validateForBlank(ele);
                                this.model.set({
                                        'senderName' : ele.value.trim()
                                });
                                break;
                        case "senderEmail":
                                this.validateForBlank(ele);
                                var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
				this.validateRegex(ele,email_regex);
                                this.model.set({
                                        'senderEmail' : ele.value.trim()
                                });
                                break;
                        
                        }
                        
        },
        changeMessage : function(ele) {
				this.model.set({
					'senderMessage' : ele.currentTarget.value
				});
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
        validateForBlank : function(ele) {							
                // Check if string is non-blank
                var isNonblank_re    = /\S/;

                var isNotBlank= String (ele.value).search (isNonblank_re) !== -1;
                var val = ele.value;

                if (isNotBlank) {
                        this.showCorrect(ele);
                        return false;
                } else {
                        this.showIncorrect(ele);
                        return true;
                }
        },
        showIncorrect : function(ele) {
                    //$(ele).next("span").removeClass("sucess-icon");
                    //$(ele).next("span").addClass("error-icon");
                    //$(ele).next("span").show();
                    $(ele).addClass('error-border');
                    $(ele).removeClass('sucess-border');
          },

        showCorrect : function(ele) {
                //$(ele).next("span").removeClass("error-icon");
                //$(ele).next("span").addClass("sucess-icon");
                //$(ele).next("span").show();

                $(ele).siblings('[id$="ErrorContainer"]').text("");
                $(ele).siblings('[id$="ErrorContainer"]').hide();
                $(ele).siblings('label').find('[id$="ErrorAsteric"]').hide();
                $(ele).removeClass('error-border');
                $(ele).addClass('sucess-border');
        },
            sendMessage: function() {
                        var validationResult = this.model.validate(this.model.toJSON());
                        var me = this;
                        this.model.save({}, {
                               success: function (model, response, options) {
                                   console.log(response);
                                   me.showMsg();
                               },
                               error:function(model, xhr, options){
                                   console.log(model);
                                    me.showMsg();
                               }
                           });
            },
    hideMsg: function () {
	    //this.$('#successDiv span').removeClass('error');
	    this.$('#successDiv span').text('');
    },
    showMsg: function () {
	    //this.$('#successDiv span').removeClass('error');
             //var successContainer = this.$('#successDiv span');
            
    	/* console.log("show mesaage");
             $('#senderName').val("");
             $('#senderEmail').val(" ");
             $("textarea#senderMessage").val("");
             var msgContainer = $('#msg');
             msgContainer.addClass('error');
             msgContainer.text("Thank you for contacting Crossper.");
              $('#msg').text("Thank you for contacting Crossper");*/
    	
              //$('#successDiv span').text('Thank you for contacting Crossper. Our representative will get back to you.');
             this.model.set({
                                        'senderEmail' : "",
                                        'senderName': "",
                                        'senderMessage': "",
                                        'msg':"Thank you for contacting Crossper."
                                });
	    //$('#successDiv span').text('Thank you for contacting Crossper. Our representative will get back to you.');
            //this.render();
    },
    render: function() { 
		this.$el.html(this.contactsViewTemplate(this.model.toJSON()));
		delete this.model.attributes.msg;
		return this;
    }

  });
  return  crossper.ContactsView;
});
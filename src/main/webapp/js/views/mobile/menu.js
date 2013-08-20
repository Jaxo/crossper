define([ "jQuery",
         "underscore", 
         "backbone", 
		 "text!templates/mobile/menu.html"],
		function($, _, Backbone,menu) {

		var crossper = crossper || {};
	
		crossper.MenuView = Backbone.View.extend({
	
			menuTemplate : Handlebars.compile(menu),
	
			tagName:'div',
			
			id:'menu',
			
			className:'menu',
			
			events : {
			},
	
			initialize : function() {
				
				this.$el.attr('data-role','menu');
				
				this.render();
			},
	
			render : function() {
				this.$el.html(this.menuTemplate());
				this.$el.trigger('create');
				$("[data-role='page']").after(this.el);
			},
			
		});
	return crossper.MenuView;
});

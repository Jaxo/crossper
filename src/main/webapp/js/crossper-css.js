/*----------------Event handlers-----------------------------------------------------------------------------------*/
	
$(window).on('load', function () {
    calculatePage();
});

$(document).live( 'pageshow',function(){
	notificationBlock();
	menuBlock();
	accordion();
	tapDown();
	overlayPopup();
}); 

$(document).live( 'pagebeforeshow',function(){
	/*---REF overlayPopup--add classes to data role overlay block---*/
	$('div[data-role="overlay"]').addClass('ci-overlay-popup');
	
	/*---REF overlayPopup--hide initially---*/
	$('#ciOverlayWrapper').hide();
}); 

/*--Hide Previous DOM---*/
$('div[data-role="page"]').live('pagehide', function (event, ui) {
	$(event.currentTarget).remove();
});

/*
$("a").focus(function(){
    $(this).attr("hideFocus", "hideFocus");
});

*/
/*----------------Functions-----------------------------------------------------------------------------------*/

/*---Accordion-----*/
function accordion() {
	//onload keep open
	$('.ci-accordion .ci-legend').not('.ci-active').addClass('ci-inactive');
	$('.ci-accordion .ci-legend.ci-active').next().show();

	$('.ci-accordion .ci-legend').prepend('<span class="ci-icon ci-sprite"></span>');
	$('.ci-accordion .ci-legend').click(function(){
		var accordionId = $(this).parent().parent().attr('id');
		accordionId = ('#'+accordionId);

		if($(this).is('.ci-inactive')) {			
			$(accordionId + ' .ci-active').toggleClass('ci-active').toggleClass('ci-inactive').next().slideToggle();
			$(this).toggleClass('ci-active').toggleClass('ci-inactive');
			$(this).next().slideToggle();
			
		} else {			
			$(this).toggleClass('ci-active').toggleClass('ci-inactive');
			$(this).next().slideToggle();
		}
	});
}

/*---Calculate middle page spacing----*/
function calculatePage() {
	var headerHeight = $('header').height();
	var footerHeight = $('footer').height();
	var h = $('header').height();
	$('.ui-page').css({'padding-top':headerHeight,'padding-bottom':footerHeight});
}

/*---For android tap down----*/
function tapDown() {	
	$('.ci-tap, .ci-tap-list li').on('vmousedown',function() {		
		$(this).addClass('ci-tap-down');
	}).on('vmouseup',function() {		
		$(this).removeClass('ci-tap-down');
	});
	
}

/*---For notification Block----*/
function notificationBlock() {
	var ciNotice = $('#ciNotice');
	$('.ci-notification').on('click',function(){
		if($(this).is('.ci-active')) {
			$(this).removeClass('ci-active');
			ciNotice.slideUp();			
			$('section,footer').show();
		} else {			
			$(this).addClass('ci-active');
			ciNotice.slideDown('',function(){
				$('section,footer').hide();
			});			
		}
	});	
}

/*---For Menu Block----*/
function menuBlock() {
	var windowHeight = $(window).height();
	var headerHeight = $('header').height();
	var footerHeight = $('footer').height();
	var ciOptions = $('#ciOptions');
	$('.ci-options-list').css('min-height',windowHeight);	
		$('.ci-menu').on('click',function(){
			ciOptions.show('',function(){				
				ciOptions.animate({right:'0%'},function(){					
					$('header,section,footer,#ciNotice').hide();
					$('header .ci-notification').removeClass('ci-active');
				});
			});
	});	
	$('#ciOptions .ci-menu-button').click(function(){	
		ciOptions.animate({right:'-100%'},function(){
			ciOptions.hide();
		});	
		$('header,section,footer').show();	
		
	});	

}


/*---Overlay Popup----*/
function overlayPopup() {
	$('div[data-role="overlay"] .ci-legend') .prepend('<div class="ci-close-overlay"><div class="ci-icon ci-sprite"></div></div>');
	
	/*---dd ciOverlayWrapper to body---*/
	var overlayLenght = $('div[data-role="overlay"]').length;
	if(overlayLenght > 0 && $('#ciOverlayWrapper').length < 1) { $('body').prepend('<div id="ciOverlayWrapper"></div>');}	

	$('[data-rel="overlay"]').on('click',function(){
		$(".ci-overlay-popup:visible .ci-close-overlay").hide();		
	
		var overlayId = '#'+$(this).attr('rel');
		var windowHeight = $(window).height();
		var headerHeight = $('header').height();		
		$('#ciOverlayWrapper').fadeIn();
		$(overlayId).show('',function(){
			$(overlayId).animate({bottom:'0%'},500);			
		});		
		
		//set z-index for multiple popup
		var zInd= $('.ci-overlay-popup').css('z-index');
		$('.ci-overlay-popup').css('z-index',zInd);
		var highest = zInd;
		$(overlayId).css('z-index',highest + 1);		
		//alert(highest+1);
		
		//add max height to scroll as per window height
		var windowHeight = $(window).height();
		var headerHeight = $('header').height();
		$(overlayId+' .ci-scroll').css('max-height',windowHeight - 100);
	});
	$('.ci-close-overlay').on('click',function(){	
		$(".ci-overlay-popup:visible .ci-close-overlay").show();
	
		$(this).closest('.ci-overlay-popup').animate({bottom:'-100%'},500, function(){		
		
		 //if multiple popup open
		 var len = $(".ci-overlay-popup:visible").length;
		 if(len < 2){
			$('#ciOverlayWrapper').hide();
		}
			$(this).closest('.ci-overlay-popup').hide();
		});	
		
	});
}


/*---Splash Animation functions----*/

     
 //Plugin start
 (function($)
   {
     var methods = 
       {
         init : function( options ) 
         {
           return this.each(function()
             {
               var _this=$(this);
                   _this.data('marquee',options);
               var _li=$('>div',_this);
                   
                   _this.wrap('<div class="slide_container"></div>')
                        .height(_this.height())
                       .hover(function(){if($(this).data('marquee').stop){$(this).stop(true,false);}},
                              function(){if($(this).data('marquee').stop){$(this).marquee('slide');}})
                        .parent()
                        .css({position:'relative',overflow:'hidden','height':$('>div',_this).height()})
                        .find('>ul')
                        .css({width:screen.width*2,position:'absolute'});
           
                   for(var i=0;i<Math.ceil((screen.width*3)/_this.width());++i)
                   {
                     _this.append(_li.clone());
                   } 
             
               _this.marquee('slide');});
         },
      
         slide:function()
         {
           var $this=this;
           $this.animate({'left':$('>div',$this).width()*-1},
                         $this.data('marquee').duration,
                         'swing',
                         function()
                         {
                           $this.css('left',0).append($('>div:first',$this));
                           $this.delay($this.data('marquee').delay).marquee('slide');
             
                         }
                        );
                             
         }
       };
   
     $.fn.marquee = function(m) 
     {
       var settings={
                     'delay':2000,
                     'duration':900,
                     'stop':true
                    };
       
       if(typeof m === 'object' || ! m)
       {
         if(m){ 
         $.extend( settings, m );
       }
 
         return methods.init.apply( this, [settings] );
       }
       else
       {
         return methods[m].apply( this);
       }
     };
   }
 )( jQuery );
  //call
$(document).live( 'pageshow',function(){
   $('.ci-slide').marquee({delay:3000});
 }
 );

// JavaScript Document
	var self;
$(document).ready(function(){
	
	// for tow column same height 
   // $("#offerimg1").height($("#offertdes1").height());
	
	
	$(".offer-border").click(function(){
		 $(this).addClass("offer-border-active");
		 $(this).find(".decription-block").show();
		 $(this).find(".decription-block").removeClass("display-none");
		 $(this).find(".deleteoffer-icon").show();	
		 $("[data-position='fixed']").fixedtoolbar('show');
		});
		//offer expand collapse functionality as below
		$('.description').hide();
   	    $('.offer-block').each(function(){
		  $(this).toggle(
		   function() {
				 $(this).children('.offer-description-block').find('.description').show();
				// $(this).children('.offer-image-block').find('img').css('height','446px');
				 $(this).children('.offer-image-block').find('img').height($(".offer-description-block").height());
				// alert($(".offer-description-block").height());
				 $(this).children('.offer-description-block').find('.deleteoffer-icon').show();
				 
				 
		   }, function() {
				 $(this).children('.offer-description-block').find('.description').hide();
				  $(this).children('.offer-image-block').find('img').height($(".offer-description-block").height());
				  
				// $(this).children('.offer-image-block').find('img').css('height','100px');
				 $(this).children('.offer-description-block').find('.deleteoffer-icon').hide();
		   }
		  );
		 });
	
	});

$(function(){
	var menuStatus;
	
	$("a.showMenu").click(function(){
		if(menuStatus != true){				
		$(".ui-page-active").animate({
			marginLeft: "275px",
		  }, 300, function(){menuStatus = true});
		  $("a.showMenu span").addClass("ci-menu-icon-active");
		   $("a.showMenu span").removeClass("ci-menu-icon");
		  return false;
		  } else {
			$(".ui-page-active").animate({
			marginLeft: "0px",
		  }, 300, function(){menuStatus = false});
		  $("a.showMenu span").removeClass("ci-menu-icon-active");
		  $("a.showMenu span").addClass("ci-menu-icon");
			return false;
		  }
	});

	//$('.pages').live("swipeleft", function(){
//		if (menuStatus){	
//		$(".ui-page-active").animate({
//			marginLeft: "0px",
//		  }, 300, function(){menuStatus = false});
//		  $("a.showMenu span").removeClass("ci-menu-icon-active");
//		   $("a.showMenu span").addClass("ci-menu-icon");
//		  }
//	});
//	
//	$('.pages').live("swiperight", function(){
//		if (!menuStatus){	
//		$(".ui-page-active").animate({
//			marginLeft: "275px",
//		  }, 300, function(){menuStatus = true});
//		  $("a.showMenu span").addClass("ci-menu-icon-active");
//		   $("a.showMenu span").removeClass("ci-menu-icon");
//		  }
//	});
	
	$("#menu li a").click(function(){
		var p = $(this).parent();
		if($(p).hasClass('active')){
			$("#menu li").removeClass('active');
		} else {
			$("#menu li").removeClass('active');
			$(p).addClass('active');
		}
	});
	
	//$(".register-steps-back li a").click(function(){
		//var p = $(this).parent();
		//if($(p).hasClass('active')){
		//	$(".register-steps-back li").removeClass('active');
		//} else {
		//	$(".register-steps-back li").removeClass('active');
		//	$(p).addClass('active');
		//}
	//});
	
	$(".submit").click(function(){
		
		$("#register2").removeClass("display-none");
		$("#register1").addClass("display-none");
		$("#step2").addClass('active');
		$("#step1").removeClass('active');
		
		
	});
	$(".add").click(function(){
		
		$("#register3").removeClass("display-none");
		$("#register1").addClass("display-none");
		$("#register2").addClass("display-none");
		$("#step3").addClass('active');
		$("#step2").removeClass('active');
		$("#step1").removeClass('active');
		
		
	});
		
		


});	
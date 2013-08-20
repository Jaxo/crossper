// JavaScript Document

$(document).ready(function() {
		  $(".custom-combo").sb({ ddCtx: function() { return $(this).closest("div"); } });
		  
		  
		  $(".current-offer-list li").click(function(){
			  $("#singleoffer").removeClass("display-none");
			  $("#offerlist").addClass("display-none");
			  
			  });
			  
			  
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
	
	

	$(".btn-gray-edit").click(function(){
		
		if($('#myTab li').hasClass('active')){
			$('#myTab li').removeClass('active');
			$("#myTab li.setting").addClass("active");
			}
		
		
		 // sidebar.find('.active').removeClass('active');
		  
		});
	
	
	$('.btn-edit-offer').click(function(){
		$('#editoffer').show();
		$('#singleoffer').hide();
	});
	$('.btn-save').click(function(){
		$('#editoffer').hide();
		$('#offerlist').show();
	});
	
		});
		
		
		
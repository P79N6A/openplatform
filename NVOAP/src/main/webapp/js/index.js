$(document).ready(function(){
	$(".develop-item").mouseover(function(){
		  $(this).css("background-color","blue");
		  $(this).find("i").css("color","#ffffff");
		  $(this).find("p").css("color","#ffffff");
	});
	$(".develop-item").mouseleave(function(){
		 $(this).css("background-color","#ffffff");
		 $(this).find("i").css("color","blue");
		 $(this).find("p").css("color","#000000");
	});
});


/* To get content div on the full height of the page  */
$(function(){
	$('#content').css({'height':($(document).height())+'px'});
	$(window).resize(function(){
		$('#content').css({'height':($(document).height())+'px'});
	});
});


$(function(){
	$('#contacts').css({'height':($(document).height()/100*92)+'px'});
	$(window).resize(function(){
		$('#contacts').css({'height':($(document).height()/100*92)+'px'});
	});
});

/* To get content div on the full height of the page  */
$(function(){
	$('#content').css({'height':($(document).height())+'px'});
	$(window).resize(function(){
		$('#content').css({'height':($(document).height())+'px'});
	});
});

$(function(){
	$('#sidebar-left').css({'height':($(document).height())+'px'});
	$(window).resize(function(){
		$('#sidebar-left').css({'height':($(document).height())+'px'});
	});
});

$(function(){
	$('#contacts').css({'height':($(document).height()/100*88)+'px'});
	$(window).resize(function(){
		$('#contacts').css({'height':($(document).height()/100*88)+'px'});
	});
});

$(function(){
	$('#pageContent').css({'height':($(document).height()/100*80)+'px'});
	$(window).resize(function(){
		$('#pageContent').css({'height':($(document).height()/100*80)+'px'});
	});
});
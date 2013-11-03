/* To get content div on the full height of the page  */
/*
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
 });*/

//Initial load of page
$(document).ready(sizeContent);

//Every resize of window
$(window).resize(sizeContent);

//Dynamically assign height
function sizeContent() {
	var newHeight = $(document).height();
	/*console.log(newHeight);
	 console.log($("#sidebar-header").height());*/
	$("#sidebar-left").css("height", newHeight + "px");
	$("#content").css("height", newHeight + "px");
	$("#pageContent").css("height", newHeight-200 + "px");
	$("#contacts").css("height", newHeight / 100 * 88 + "px");
	/*$("#pageContent").css("height", newHeight-$(".pageHeader").height() + "px");*/
}

$(function (){
	$('a').tooltip();
	$('button').tooltip();
	$('input').tooltip();
});
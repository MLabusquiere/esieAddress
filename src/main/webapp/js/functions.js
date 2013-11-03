/* To get content div on the full height of the page  */

//Initial load of page
$(document).ready(sizeContent);

//Every resize of window
$(window).resize(sizeContent);

$("#sidebar-header").resize(sizeContent);

//Dynamically assign height
function sizeContent() {
	var pageHeight = $(document).height();
	var sidebarHeaderHeight = $("#sidebar-header").height();
//	var pageHeaderHeight = $("#page-header").innerHTML;
	var pageHeaderHeight = 150;

	$("#sidebar-left").css("height", pageHeight + "px");
	$("#contacts").css("height", pageHeight - sidebarHeaderHeight - 30 + "px");
	$("#content").css("height", pageHeight + "px");
	$("#pageContent").css("height", pageHeight - pageHeaderHeight + "px");
}

$(function (){
	$('a').tooltip();
	$('button').tooltip();
	$('input').tooltip();
});
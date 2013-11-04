/* To get content div on the full height of the page  */

//Initial load of page
$(document).ready(sizeContent);

//Every resize of window
$(window).resize(sizeContent);
$(document).resize(sizeContent);
$("#content").resize(sizeContent);

$("#sidebar-header").resize(sizeContent());

$("#cfg-btn").click(function() {
	sizeContent();
	console.log("cfg");
});

//Dynamically assign height
function sizeContent() {
	var pageHeight = $(window).height() - 20;
	var contentHeight = $("#content").outerHeight(true);
	var pageContentHeight = $("#pageContent").outerHeight(true);
	var sidebarHeaderHeight = $("#sidebar-header").outerHeight(true);
//	var pageHeaderHeight = $(".page-header");
//	var pageHeaderHeight = 150;
	console.log($(".page-header").outerHeight(true));

//	$("#sidebar-left").height(pageHeight);
	console.log(pageHeight, pageContentHeight);
	if(contentHeight > pageHeight) {
		$("#contacts").height(contentHeight - sidebarHeaderHeight);
	}
	else {
		$("#contacts").height(pageHeight - sidebarHeaderHeight - 20);
	}
//	$("#content").height(pageHeight - 22);
//	$("#pageContent").height(pageHeight - pageHeaderHeight);
//	$(document).height(pageHeight);
}

$(function (){
	$('a').tooltip();
	$('button').tooltip();
	$('input').tooltip();
	$('img').tooltip();
});
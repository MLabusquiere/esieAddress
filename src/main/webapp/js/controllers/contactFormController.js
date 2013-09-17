'use strict';

/* User Controllers */

function ContactFormCtrl($scope, $location, Contact) {

	$scope.days = new Array();
	$scope.months = new Array();
	$scope.years = new Array();

	for(var i = 0; i<31; i++){
		$scope.days[i] = (i+1);
	}

	for(i = 0; i<12; i++){
		$scope.months[i] = (i+1);
	}

	for(i = 0; i<120; i++){
		var date = new Date().getFullYear();
		$scope.years[i] = (date-i);
	}
}
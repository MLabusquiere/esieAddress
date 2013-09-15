'use strict';

/* User Controllers */

function ContactListCtrl($scope, $location, Contact) {

	$scope.layout = 'list';

	$scope.contacts = Contact.query(); 

	$scope.click = function(){
		console.info("click");
	};

	$scope.edit = function(){
		console.info("edit");
	};

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

// UserListCtrl.$inject = ['$scope', '$location', 'User'];

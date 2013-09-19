'use strict';

/* User Controllers */

function ContactFormCtrl($scope, $location, Contact) {

	$scope.newContact = {};
	$scope.newContact.addresses = new Array();

	$scope.addressNumber = 0;
	$scope.addresses = new Array();

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

	$scope.newAddress = function () {
		var address = {};
		address.text = "";
		address.id = $scope.addresses.length;
		console.log("Adding address ");
		$scope.addresses[$scope.addresses.length] = address;
	};

	$scope.deleteLastAddress = function () {
		console.log("Deleting last address");
		$scope.addresses.splice($scope.addresses.length-1, 1);
	};

	$scope.deleteAddress = function (index) {
		console.log("Deleting address # "+index);
		$scope.addresses.splice(index, 1);
	};

	$scope.addQuestion = function() {
		console.log($scope.newContact);
	};
}
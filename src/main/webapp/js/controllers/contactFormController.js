'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('ContactFormCtrl',function($scope, $location, $routeParams, Contact) {

	$scope.edit = false;

	$scope.newContact = {};

	$scope.addressNumber = 0;
	$scope.addresses = new Array();

	if($routeParams.id != undefined){
		console.log("Edit contact "+$routeParams.id)
		$scope.edit = true;
		Contact.get({
			id: $routeParams.id
		}, function(data) {
			$scope.newContact = data;
		}, function(error) {
			$location.path('/error/'+error.status);
		});
	}

/*	$scope.days = new Array();
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
	}*/

	$scope.saveContact = function () {
		if($scope.edit = true){

		}
		console.log($scope.newContact);
		Contact.save($scope.newContact,
			function(data) {
				console.log("OK");
			},
			function(error) {
				console.log("Error "+error.status);
			}
		);
	};

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

});
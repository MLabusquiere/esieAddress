'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('ContactFormCtrl',function($rootScope, $scope, $location, $routeParams, Contact) {

	$scope.newContact = {};
	$scope.newContact.actif = true;

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

	$scope.saveContact = function () {
		if($scope.newContact.id == undefined){
			console.log("Creating new contact :")
			console.log($scope.newContact);
			Contact.save($scope.newContact,
				function(data) {
					console.log("New contact created");
					$rootScope.$broadcast('updateContactList');
				},
				function(error) {
					console.log("Error "+error.status);
				}
			);
		}
		else {
			console.log("Updating contact "+$scope.newContact.id);
			Contact.update($scope.newContact,
				function(data) {
					console.log("Contact updated");
					$rootScope.$broadcast('updateContactList');
				},
				function(error) {
					console.log("Error "+error.status);
				}
			);
		}
	};

	$scope.newAddress = function () {
		var address = {};
		address.street = "";
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

});

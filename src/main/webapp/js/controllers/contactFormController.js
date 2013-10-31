'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('ContactFormCtrl', function ($rootScope, $scope, $location, $routeParams, Contact, Address) {

	$scope.newContact = {};
	$scope.newContact.actif = true;

	$scope.newContact.addresses = [];

	$scope.addresses = [];

	if ($routeParams.id != undefined) {
		console.log("Edit contact " + $routeParams.id)
		$scope.edit = true;
		Contact.get({
			id: $routeParams.id
		}, function (data) {
			$scope.newContact = data;
			$scope.addresses = data.addresses;
			console.log($scope.newContact);
			console.log($scope.addresses);
		}, function (error) {
			$location.path('/error/' + error.status);
		});
	}

	$scope.saveContact = function () {
		console.log("Save function");
		console.log($scope.newContact);
		console.log($scope.addresses);
		if ($scope.newContact) {
			if ($scope.newContact.id == undefined) {
				console.log("Creating new contact :");
				$scope.newContact.addresses = $scope.addresses;

				Contact.save($scope.newContact,
					function (data) {
						console.log("New contact created");
						console.log(data);
						$rootScope.$broadcast('updateContactList');
					},
					function (error) {
						console.log("Error " + error.status);
					}
				);
			}
			else {
				console.log("Updating contact " + $scope.newContact.id);
				for (var i = 0; i < $scope.addresses.length; i++) {
					console.log("Updating address");
					console.log($scope.addresses[i]);
					Address.update($scope.addresses[i],
						function (data) {
							console.log("Added address to contact");
							console.log(data);
						},
						function (error) {
							console.log("Updating addresses : Error " + error.status);
						}
					);
				}
				Contact.update($scope.newContact,
					function (data) {
						console.log("Contact information updated");
						console.log(data);
						$rootScope.$broadcast('updateContactList');
					},
					function (error) {
						console.log("Updating contact info : Error " + error.status);
					}
				);
			}
		}
	};

	$scope.newAddress = function () {
		var address = {};
		address.street = "";
		console.log("Adding address ");
		$scope.addresses.push(address);
		sizeContent();
	};

	$scope.deleteLastAddress = function () {
		console.log("Deleting last address");
		$scope.addresses.pop();
		sizeContent()
	};

	$scope.deleteAddress = function (index) {
		console.log("Deleting address # " + index);
		$scope.addresses.splice(index, 1);
		sizeContent()
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

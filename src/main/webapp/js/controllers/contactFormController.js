'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('ContactFormCtrl', function ($rootScope, $scope, $location, $routeParams, Contact, Address) {

	$scope.newContact = {};
	$scope.newContact.actif = true;

	$scope.errors = {};
	$scope.birthdate = {};

	$scope.newContact.addresses = [];

	$scope.addresses = [];

	if ($routeParams.id != undefined) {
		console.log("Edit contact", $routeParams.id)
		$scope.edit = true;
		Contact.get({
			id: $routeParams.id
		}, function (data) {
			console.log("Contact", data)
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
		$scope.newContact.addresses = $scope.addresses;
		console.log("Contact:", $scope.newContact);

		if ($scope.newContact) {
			if ($scope.newContact.id == undefined) {
				console.log("Creating new contact :");
				Contact.save($scope.newContact,
					function (data) {
						console.log("New contact created");
						console.log(data);
						$rootScope.$broadcast('updateContactList');
					},
					function (error) {
						console.log("Error", error.status, error.data);
						$scope.errors = error.data;
					}
				);
			}
			else {
				console.log("Updating contact ", $scope.newContact.id);
				Contact.update(
					$scope.newContact,
					function (data) {
						console.log("Contact information updated", data);
						$rootScope.$broadcast('updateContactList');
						$location.path("contacts/"+$routeParams.id);
					},
					function (error) {
						console.log("Updating contact info : Error ", error.status, error.data);
						$scope.errors = error.data;
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
		console.log("form", $scope.contact_form);
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

});

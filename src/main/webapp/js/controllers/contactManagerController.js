'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('ContactManagerCtrl', function ($rootScope, $location, $scope, Contact) {

	$scope.contacts = [];

	$scope.modal = "";

	var loadContacts = function(){
		Contact.query(function (data) {
			$scope.contacts = data;
			console.log("Contact list loaded successfully");
		}, function (error) {
			console.log("Error " + error.status);
			$location.path('/error/' + error.status);
		});
	}

	loadContacts();

	$scope.deleteContact = function() {
		$('#modal').modal('hide');
		for (var i = 0; i < $scope.contacts.length; i++) {
			if($scope.contacts[i].selected == true){
				Contact.remove({
					id: $scope.contacts[i].id
				}, function () {
					$rootScope.$broadcast('updateContactList');
				}, function (error) {
					console.log(error);
					$location.path('/error/' + error.status);
				});
			}
		}
		loadContacts();
	}

	$scope.showContact = function() {
		$('#modal').modal('hide');
		for (var i = 0; i < $scope.contacts.length; i++) {
			if($scope.contacts[i].selected == true){
				$scope.contacts[i].actif = true;
				$scope.contacts[i].selected = undefined;
				Contact.update($scope.contacts[i]
				, function () {
					$rootScope.$broadcast('updateContactList');
				}, function (error) {
					console.log(error);
					$location.path('/error/' + error.status);
				});
			}
		}
		loadContacts();
	}

	$scope.hideContact = function() {
		$('#modal').modal('hide');
		for (var i = 0; i < $scope.contacts.length; i++) {
			if($scope.contacts[i].selected == true){
				$scope.contacts[i].actif = false;
				$scope.contacts[i].selected = undefined;
				Contact.update($scope.contacts[i]
				, function () {
					$rootScope.$broadcast('updateContactList');
				}, function (error) {
					console.log(error);
					$location.path('/error/' + error.status);
				});
			}
		}
		loadContacts();
	}
});

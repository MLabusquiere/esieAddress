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

	$scope.contactVisibility = function(action) {
		var visibility = {visible: false};
		for (var i = 0; i < $scope.contacts.length; i++) {
			if($scope.contacts[i].selected == true){
				if(action == "show"){
					visibility.visible = true;
				}
				Contact.updateVisibility({
                        id: $scope.contacts[i].id,
                        visibility:visibility.visible
                    }
				, function () {
					$rootScope.$broadcast('updateContactList');
					$scope.contacts[i].actif = visible;
					$scope.contacts[i].selected = undefined;
				}, function (error) {
					console.log(error);
//					$location.path('/error/' + error.status);
				});
			}
		}
	}
});

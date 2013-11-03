'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('ContactListCtrl', function ($rootScope, $scope, $location, Contact) {

	$scope.sorts = [
		{predicate: ['lastname', 'firstname', 'id'], text: 'Last name'},
		{predicate: ['firstname', 'lastname', 'id'], text: 'First name'}
	];
	$scope.sort = $scope.sorts[0];

	$scope.reverse = false;
	$scope.layout = 'list';
	$scope.showAll = false;

	$scope.contacts = [];

	$('.label-toggle-switch').on('switch-change', function (e, data) {
		var scope = angular.element($(".container")).scope();
		scope.$apply(function(){
			$scope.showAll = !$scope.showAll;
		});
	});

	loadContactList();

	function loadContactList() {
		console.log("Loading contact list");
		Contact.query(function (data) {
			$scope.contacts = data;
			console.log("Contact list loaded successfully:", data);
		}, function (error) {
			console.log("Error " + error.status);
			$location.path('/error/' + error.status);
		});
	}


	$scope.$on('updateContactList', function () {
		console.log("Querying contact list update");
		loadContactList();
	});

	$scope.synchronizeFacebook = function() {
		Facebook.synchronizeFacebook(
			{},
			{},
			function(data) {
				$scope.contacts = data;
			},
			function(error) {
				console.log("Failed to synchronize contacts with Facebook: Error", error.status, error.data);
			}
		);
	};

});

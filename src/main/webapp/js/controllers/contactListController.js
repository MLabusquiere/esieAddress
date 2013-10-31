'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('ContactListCtrl', function ($scope, $location, Contact) {

	$scope.sorts = [
		{predicate: ['lastname', 'firstname', 'id'], text: 'Last name'},
		{predicate: ['firstname', 'lastname', 'id'], text: 'First name'}
	];
	$scope.sort = $scope.sorts[0];

	$scope.reverse = false;
	$scope.layout = 'list';
	$scope.showAll = false;

	$scope.contacts = [];

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

	$scope.test = function () {
		console.log("switch");
	};

});

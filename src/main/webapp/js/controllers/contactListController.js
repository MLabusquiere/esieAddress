'use strict';

/* User Controllers */

function ContactListCtrl($scope, $location, Contact) {

	$scope.layout = 'list';
	$scope.showAll = true;

	$scope.contacts = [];

	Contact.query(function(data) {
		$scope.contacts = data;
	}, function(error) {
		$location.path('/error/'+error.status);
	});

	$scope.test = function () {
		console.log("switch");
	};
}

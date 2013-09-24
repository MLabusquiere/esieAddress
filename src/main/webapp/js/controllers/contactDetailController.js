'use strict';

/* User Detail Controller */

function ContactDetailCtrl($scope, $location, $routeParams, Contact) {

	$scope.contact = {};

	Contact.get({
		id: $routeParams.id
	}, function(data) {
		$scope.contact = data;
	}, function(error) {
		$location.path('/error/'+error.status);
	});

}
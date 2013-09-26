'use strict';

/* User Detail Controller */
var module = angular.module('esieAddress.controllers');
module.controller('ContactDetailCtrl', function($scope, $location, $routeParams, Contact) {

	$scope.contact = {};

	Contact.get({
		id: $routeParams.id
	}, function(data) {
		$scope.contact = data;
	}, function(error) {
		$location.path('/error/'+error.status);
	});

});
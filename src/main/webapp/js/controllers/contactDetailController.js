'use strict';

/* User Detail Controller */
var module = angular.module('esieAddress.controllers');
module.controller('ContactDetailCtrl', function ($rootScope, $scope, $location, $routeParams, Contact) {

	$scope.contact = {};

	Contact.get({
		id: $routeParams.id
	}, function (data) {
		$scope.contact = data;
	}, function (error) {
		$location.path('/error/' + error.status);
	});

	$scope.deleteContact = function() {
		Contact.remove({
			id: $routeParams.id
		}, function () {
			$rootScope.$broadcast('updateContactList');
			$('#deleteModal').modal('hide');
			$location.path('/contacts');
		}, function (error) {
			console.log(error);
			$location.path('/error/' + error.status);
		});
	}

});
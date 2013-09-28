'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('ContactListCtrl',function($scope, $location, Contact) {

	$scope.predicate = 'lastname';
	$scope.reverse = false;
	$scope.layout = 'list';
	$scope.showAll = true;

	$scope.contacts = [];

	var updateList = function(){
		Contact.query(function(data) {
			$scope.contacts = data;
		}, function(error) {
			$location.path('/error/'+error.status);
		});
	}

	updateList();

	$scope.$on('importSuccess', function () {
		updateList();
	});

	$scope.test = function () {
		console.log("switch");
	};

});

'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('ContactListCtrl',function($scope, $location, Contact) {

	$scope.sorts = [
		{predicate:'lastname', text:'Last name'},
		{predicate:'firstname', text:'First name'}
	];
	$scope.sort = $scope.sorts[0];

	$scope.reverse = false;
	$scope.layout = 'list';
	$scope.showAll = false;

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

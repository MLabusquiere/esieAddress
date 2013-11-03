'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('SignUpCtrl', function ($scope, Signup) {

	$scope.user = {};
	$scope.errors = {};

	$('#loginModal').modal('hide');

	$scope.signup = function() {
		Signup.save(
			{},
			$scope.user,
			function(data){
				console.log("Successfully signed up", data)
			},
			function(error){
				console.log("Error", error.status, error.data);
				$scope.errors = error.data;
			}
		);
	}
});

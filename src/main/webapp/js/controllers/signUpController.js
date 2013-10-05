'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('SignUpCtrl', function ($scope, Signup) {

	$scope.user = {};

	$scope.signup = function() {
		Signup.save($scope.user
			, function(){
				console.log("Successfully signed up")
			}
			, function(error){
				console.log("Error "+error.status);
			}
		);
	}
});

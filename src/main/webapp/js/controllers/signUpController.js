'use strict';

/* User Controllers */

var module = angular.module('esieAddress.controllers');

module.controller('SignUpCtrl', function ($scope,$location, Signup) {

	$scope.user = {};
	$scope.errors = {};

	$('#loginModal').modal('hide');

	$scope.signup = function() {
		Signup.save(
			{},
			$scope.user,
			function(data){
                $scope.login ={};
                var scope = angular.element($("body")).scope();
                scope.login.email = $scope.user.email;
                scope.login.password = $scope.user.password;

                $scope.$emit('event:loginRequest');

                if ($scope.previousRoute && $scope.previousRoute != "/signup") {
                    $location.path($scope.previousRoute);
                }
                else {
                    $location.path("/");
                }

                console.log("Successfully signed up", data)
			},
			function(error){
				console.log("Error", error.status, error.data);
				$scope.errors = error.data;
			}
		);
	}
});

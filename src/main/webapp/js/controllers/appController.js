'use strict';

/* Controllers */

var module = angular.module('esieAddress.controllers');
module.controller('AppCtrl', function ($rootScope, $route, $http, $scope, $location, Login, Logout) {

	$scope.login = {};
	$scope.user = {};
	$scope.logged = false;

	$('#loginInfo').hide();

	//Permet de verifier après un rafraichissement si on est loggué
	fetchUserData();

	/**
	 * Ping server to figure out if user is already logged in.
	 */
	function fetchUserData() {
		Login.get(
			{},
			function (data) {
				$scope.user = data;
				$scope.logged = true;
				console.log("Fetched user: ", data);
				$scope.$broadcast('event:loginConfirmed');
			},
			function (error) {
				console.log("Login failed : Error", error.status);
				$scope.logged = false;
			}
		);
	}

	/*
	 * Called when the authentication form is field
	 */
	$scope.connect = function () {
		$scope.$broadcast('event:loginRequest');
	};

	/*
	 * Permit to broadcast that a login  is required
	 */
	$scope.loginRequired = function () {
		console.info("Send event login request");
		$scope.$broadcast('event:loginRequired');
	};

	/**
	 * On 'logoutRequest' invoke logout on the server and broadcast 'event:loginRequired'.
	 */
	$scope.logout = function () {
		Logout.get({}, function () {
				$scope.logged = false;
				console.info("logout success");
				$scope.user = {};
				$rootScope.$broadcast('updateContactList');
				$location.path("/");
			}
			, function () {
				console.info("Logout failed:", error.status);
			})
	};

	/**
	 * Holds all the requests which failed due to 401 response.
	 */
	$rootScope.$on('event:loginRequired', function () {
		console.log("Login required");
		if($route.current.$$route.controller != "SignUpCtrl") {
			if ($('#loginModal').is(":visible")) {
				$('#loginInfo').show();
			}
			else {
				$('#loginModal').modal('show');
			}
		}
	});

	/**
	 * On 'event:loginRequest' Ask the server with scope.login
	 */
	$scope.$on('event:loginRequest', function (event) {
		console.info("Login requested", $scope.login);
		Login.save(
			$scope.login,
			function (data) {
				if (data != null) {
					$scope.$broadcast('event:loginConfirmed');
					fetchUserData();
					$scope.login = {};
				}
			},
			function (error) {
				console.log("Login failed (the interceptor may not works):", error.status);
				$('#loginInfo').show();
			}
		);
	});

	/**
	 * On 'event:loginConfirmed', resend all the 401 requests.
	 */
	$scope.$on('event:loginConfirmed', function () {
		console.info("in login confirmed");
		$('#loginModal').modal('hide');
		var i, requests = $scope.requests401;
		console.info("request length " + requests.length);
		for (i = 0; i < requests.length; i++) {
			retry(requests[i]);
		}
		$scope.requests401 = [];
		$route.reload();

		function retry(req) {
			$http(req.config).then(function (response) {
				req.deferred.resolve(response);
			});
		}

		//Handle the fact the the side bar is not in this scope
//		$rootScope.$broadcast('updateContactList');
	});

	/**
	 * On 'event:accessForbidden' pop up a modal
	 */
	$scope.$on('event:accessForbidden', function (event, Login) {
		$scope.shouldOpenAccessForbiddenModal = true;
		console.info("accesForbidden in appController")
	});

});
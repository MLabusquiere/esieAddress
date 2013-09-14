'use strict';

/* User Controllers */

function ContactListCtrl($scope, $location, Contact) {

	Contact.query(function(data) {
		$scope.contacts = data;
	}, function(error) {
		$location.path('/error/'+error.status);
	});

}

// UserListCtrl.$inject = ['$scope', '$location', 'User'];

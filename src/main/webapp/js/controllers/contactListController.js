'use strict';

/* User Controllers */

function ContactListCtrl($scope, $location, Contact) {

		$scope.contacts = Contact.query();

}

// UserListCtrl.$inject = ['$scope', '$location', 'User'];

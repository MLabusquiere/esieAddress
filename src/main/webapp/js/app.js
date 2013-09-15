'use strict';

/* App Module */

var servicesModule = angular.module('services', ['ngResource']);
var filtersModule = angular.module('filters', [])

var esieAddressApp = angular.module('esieAddress', ['filters', 'services']);

console.log(document.URL);

var url = "http://esieaddress.labusquiere.eu.cloudbees.net/"
var path = url+"/rest";

esieAddressApp.config(['$routeProvider', function ($routeProvider) {
	$routeProvider.
		/* Related Contacts Pages */
		when('/contacts', {templateUrl: 'partials/contact-list.html', controller: ContactListCtrl}).

		otherwise({redirectTo: '/contacts'});
}]);


'use strict';

/* App Module */

var servicesModule = angular.module('services', ['ngResource']);
var filtersModule = angular.module('filters', [])

var esieAddressApp = angular.module('esieAddress', ['filters', 'services']);

var url = "localhost\\:8080/esieAddress"
var path = "http://"+url+"/rest";

esieAddressApp.config(['$routeProvider', function ($routeProvider) {
	$routeProvider.
		when('/', {redirectTo: '/contacts'}).

		/* Related Contacts Pages */
		when('/contacts', {templateUrl: 'partials/contact-list.html', controller: ContactListCtrl}).

		otherwise({redirectTo: '/error/404'});
}]);


'use strict';

/* App Module */

var servicesModule = angular.module('services', ['ngResource']);
var filtersModule = angular.module('filters', []);
var directivesModule = angular.module('directives', []);

var esieAddressApp = angular.module('esieAddress', ['filters', 'services']);

var path = "rest";

esieAddressApp.config(['$routeProvider', function ($routeProvider) {
	$routeProvider.
		/* Related Contacts Pages */
		when('/contacts', {templateUrl: 'partials/contact-list.html', controller: ContactListCtrl}).

		otherwise({redirectTo: '/contacts'});
}]);


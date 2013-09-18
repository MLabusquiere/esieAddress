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
		when('/contacts', {templateUrl: 'partials/home.html', controller: ContactListCtrl}).
		when('/contacts/:id', {templateUrl: 'partials/contact-detail.html', controller: ContactDetailCtrl}).

		when('/new', {templateUrl: 'partials/contact-form.html', controller: ContactFormCtrl}).

		when('/error/:id', {templateUrl: 'partials/error.html', controller: ErrorCtrl}).

		otherwise({redirectTo: '/contacts'});
}]);


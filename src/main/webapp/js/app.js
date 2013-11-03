'use strict';

/* App Module */

/*var servicesModule = angular.module('esieAddress.services', ['ngResource']);
 var controller = angular.module('esieAddress.controllers',['ngResource'])*/

angular.module('esieAddress.services', ['ngResource']);
angular.module('esieAddress.controllers', ['ngResource']);
angular.module('esieAddress.filters', ['ngResource']);
angular.module('esieAddress.directives', ['ngResource']);
angular.module('esieAddress.interceptors', ['ngResource']);

var esieAddressApp = angular.module('esieAddress', [
	'esieAddress.services',
	'esieAddress.controllers',
	'esieAddress.filters',
	'esieAddress.directives',
	'esieAddress.interceptors',
	'ui.bootstrap.dialog'
]);

var path = "rest";

esieAddressApp.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
	$routeProvider.
		/* Related Contacts Pages */
		when('/contacts', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'}).
		when('/manage', {templateUrl: 'partials/manager.html', controller: 'ContactManagerCtrl'}).
		when('/signup', {templateUrl: 'partials/signup.html', controller: 'SignUpCtrl'}).
		when('/contacts/:id', {templateUrl: 'partials/contact-detail.html', controller: 'ContactDetailCtrl'}).
		when('/contacts/:id/edit', {templateUrl: 'partials/contact-form.html', controller: 'ContactFormCtrl'}).

		when('/new', {templateUrl: 'partials/contact-form.html', controller: 'ContactFormCtrl'}).

		when('/import', {templateUrl: 'partials/import.html', controller: 'ImportCtrl'}).

		when('/error/:id', {templateUrl: 'partials/error.html', controller: 'ErrorCtrl'}).

		otherwise({redirectTo: '/contacts'});


	//Push Interceptor in the http provider
	$httpProvider.responseInterceptors.push('interceptor401_403');
}]);


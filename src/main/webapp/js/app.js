'use strict';

/* App Module */

/*var servicesModule = angular.module('esieAddress.services', ['ngResource']);
var controller = angular.module('esieAddress.controllers',['ngResource'])*/

angular.module('esieAddress.services', ['ngResource']);
angular.module('esieAddress.controllers', ['ngResource']);
angular.module('esieAddress.filters', ['ngResource']);
angular.module('esieAddress.directives', ['ngResource']);
var esieAddressApp = angular.module('esieAddress', ['esieAddress.services','esieAddress.controllers','esieAddress.filters','esieAddress.directives','ui.bootstrap.dialog']);
var path = "rest";

esieAddressApp.config(['$routeProvider', function ($routeProvider) {
	$routeProvider.
		/* Related Contacts Pages */
		when('/contacts', {templateUrl: 'partials/home.html', controller: 'ContactListCtrl'}).
		when('/contacts/:id', {templateUrl: 'partials/contact-detail.html', controller: 'ContactDetailCtrl'}).

		when('/new', {templateUrl: 'partials/contact-form.html', controller: 'ContactFormCtrl'}).

        when('/import', {templateUrl: 'partials/import.html', controller: 'ImportCtrl'}).

        when('/error/:id', {templateUrl: 'partials/error.html', controller: 'ErrorCtrl'}).

		otherwise({redirectTo: '/contacts'});
}]);


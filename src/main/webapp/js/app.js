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

esieAddressApp.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider)  {
	$routeProvider.
		/* Related Contacts Pages */
		when('/contacts', {templateUrl: 'partials/home.html', controller: 'ContactListCtrl'}).
		when('/contacts/:id', {templateUrl: 'partials/contact-detail.html', controller: 'ContactDetailCtrl'}).
		when('/contacts/:id/edit', {templateUrl: 'partials/contact-form.html', controller: 'ContactFormCtrl'}).

		when('/new', {templateUrl: 'partials/contact-form.html', controller: 'ContactFormCtrl'}).

        when('/import', {templateUrl: 'partials/import.html', controller: 'ImportCtrl'}).

        when('/error/:id', {templateUrl: 'partials/error.html', controller: 'ErrorCtrl'}).

		otherwise({redirectTo: '/contacts'});

    //Interceptor for error 401
    var interceptor401_403 = ['$rootScope', '$q', function (scope, $q) {

        scope.requests401 = [];

        function success(response) {
            return response;
        }

        function error(response) {
            console.log("In the intercepter");
            var status = response.status;
            if (status == 403) {
                console.info("403 detected");
                scope.$broadcast('event:accessForbidden');
                return;
            }

            if (status == 401) {
                var deferred = $q.defer();
                var req = {
                    config: response.config,
                    deferred: deferred
                };
                scope.requests401.push(req);
                scope.$broadcast('event:loginRequired');
                return deferred.promise;
            }
            // otherwise
            return $q.reject(response);

        }

        return function (promise) {
            return promise.then(success, error);
        }

    }];

    //Push Interceptor in the http provider
    $httpProvider.responseInterceptors.push(interceptor401_403);
}]);


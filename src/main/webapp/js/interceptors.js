/**
 * Created with IntelliJ IDEA.
 * User: Damien
 * Date: 31/10/13
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */


var module = angular.module('esieAddress.interceptors');

module.factory('interceptor401_403', function ($rootScope, $q) {

	$rootScope.requests401 = [];

	function success(response) {
		return response;
	}

	function error(response) {
		console.log("In the intercepter");
		var status = response.status;
		if (status == 403) {
			console.info("403 detected");
			$rootScope.$broadcast('event:accessForbidden');
			return;
		}

		if (status == 401) {
			var deferred = $q.defer();
			var req = {
				config: response.config,
				deferred: deferred
			};
			console.log("Pushed in request401 " + req)
			$rootScope.requests401.push(req);
			$rootScope.$broadcast('event:loginRequired');
			return deferred.promise;
		}
		// otherwise
		return $q.reject(response);

	}

	return function (promise) {
		return promise.then(success, error);
	}

});
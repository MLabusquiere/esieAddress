'use strict';

/* Services */

	/* User services */
var module = angular.module('esieAddress.services');

module.factory('Contact', function($resource) {
	return $resource(path+'/contacts', {}, {
		query: {method:'GET', isArray:true}
	});
});

module.factory('Contact', function($resource) {
	return $resource(path+'/contacts', {}, {
		save: {method:'POST'}
	});
});

module.factory('Contact', function($resource) {
	return $resource(path+'/contacts/:id', {}, {
		get: {method:'GET', isArray:true}
	});
});

module.factory('Contact', function($resource) {
	return $resource(path+'/contacts/:id', {}, {
		remove: {method:'DELETE'}
	});
});


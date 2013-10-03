'use strict';

/* Services */

	/* User services */
var module = angular.module('esieAddress.services');

module.factory('Contact', function($resource) {
	return $resource(path+'/contacts/:id', {}, {
		query: {method:'GET', isArray:true},
		get: {method:'GET', params: {id: 'id'}},
		save: {method:'POST'},
		update: {method:'PUT'},
		remove: {method:'DELETE', params: {id: 'id'}}
	});
});

module.factory('Address', function($resource) {
	return $resource(path+'/addresses/:id', {}, {
		query: {method:'GET', isArray:true},
		get: {method:'GET', params: {id: 'id'}},
		save: {method:'POST'},
		update: {method:'PUT', params: {id: 'id'}},
		remove: {method:'DELETE', params: {id: 'id'}}
	});
});

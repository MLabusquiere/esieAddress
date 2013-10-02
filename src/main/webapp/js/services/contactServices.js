'use strict';

/* Services */

	/* User services */
var module = angular.module('esieAddress.services');

module.factory('Contact', function($resource) {
	return $resource(path+'/contacts/:id', {}, {
		query: {method:'GET', isArray:true},
		get: {method:'GET'},
		save: {method:'POST'},
		update: {method:'PUT'},
		remove: {method:'DELETE'}
	});
});

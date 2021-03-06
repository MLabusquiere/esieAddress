'use strict';

/* Services */

/* User services */
var module = angular.module('esieAddress.services');

module.factory('Contact', function ($resource) {
	return $resource(path + '/contacts/:id/:param/:visibility', {}, {
		query: {method: 'GET', isArray: true},
		get: {method: 'GET', params: {id: ':id'}},
		save: {method: 'POST'},
		update: {method: 'PUT'},
		updateVisibility: {method: 'PUT', params: {id: '@id', param: 'visibility',visibility:'@visibility'}},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Address', function ($resource) {
	return $resource(path + '/addresses/:contactid', {}, {
		query: {method: 'GET', isArray: true},
		get: {method: 'GET', params: {id: ':id'}},
		save: {method: 'POST'},
		update: {method: 'PUT', params: {contactid: ':contactid', id: ':id', baseLabel: ':label'}},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Login', function ($resource) {
	return $resource('rest/login', {}, {
		get: {method: 'GET'},
		save: {method: 'POST'}
	});
});

module.factory('Signup', function ($resource) {
	return $resource('rest/users', {}, {
		save: {method: 'POST'}
	});
});

module.factory('Logout', function ($resource) {
	return $resource('rest/logout', {}, {
		save: {method: 'POST'}
	});
});

module.factory('Facebook', function ($resource) {
	return $resource('rest/facebook/synchronize', {}, {
		synchronize: {method: 'GET'}
	});
});
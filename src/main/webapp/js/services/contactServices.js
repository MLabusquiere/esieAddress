'use strict';

/* Services */

	/* User services */
servicesModule.factory('Contact', function($resource) {
	return $resource(path+'/contacts', {}, {
		query: {method:'GET', isArray:true}
	});
});

servicesModule.factory('Contact', function($resource) {
	return $resource(path+'/contacts', {}, {
		save: {method:'POST'}
	});
});

servicesModule.factory('Contact', function($resource) {
	return $resource(path+'/contacts/:id', {}, {
		get: {method:'GET', isArray:true}
	});
});

servicesModule.factory('Contact', function($resource) {
	return $resource(path+'/contacts/:id', {}, {
		remove: {method:'DELETE'}
	});
});


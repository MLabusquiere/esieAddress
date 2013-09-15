filtersModule.filter('checkmark', function() {
	return function(input) {
		return input ? 'ok' : 'remove';
	};
});
var module = angular.module('esieAddress.filters');

module.filter('checkmark', function () {
	return function (input) {
		return input ? 'ok' : 'remove';
	};
});
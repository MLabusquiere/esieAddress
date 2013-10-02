var module = angular.module('esieAddress.controllers');

module.controller('ImportCtrl', function ($rootScope, $scope) {
    $scope.uploadComplete = function () {
        console.log('Contacts imported successfully');
		$rootScope.$broadcast('updateContactList');
    };

    $scope.uploadError = function () {
        console.log('Contact import error');
    };
});
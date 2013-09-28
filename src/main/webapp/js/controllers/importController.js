var module = angular.module('esieAddress.controllers');

module.controller('ImportCtrl', function ($rootScope, $scope) {
    $scope.uploadComplete = function () {
        console.log('Success');
		$rootScope.$broadcast('importSuccess');
    };

    $scope.uploadError = function () {
        alert('Echec');
    };
});
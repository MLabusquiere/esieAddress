var module = angular.module('esieAddress.controllers');

module.controller('ImportCtrl', function ($scope) {
    $scope.uploadComplete = function () {
        alert('Success');
    };

    $scope.uploadError = function () {
        alert('Echec');
    };
});
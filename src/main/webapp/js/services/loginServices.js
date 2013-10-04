'use strict';

var module = angular.module('esieAddress.services');

module.factory('Login', function ($resource) {

    return $resource('rest/login/', {}, {
        get: {method: 'GET'},
        save: {method: 'POST'}
    });

});

module.factory('Logout', function ($resource) {
    return $resource('rest/logout/', {}, {
        save: {method: 'POST'}
    });
});
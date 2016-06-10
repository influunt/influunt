'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('LoginCtrl', ['$scope', function LoginCtrl ($scope) {
    $scope.credenciais = {};

    $scope.submitLogin = function(formValido) {
      $scope.submited = true;
      if (!formValido) {
        return false;
      }
    };

  }]);

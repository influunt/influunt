'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('LoginCtrl', ['$scope', 'Restangular', '$state',
    function LoginCtrl ($scope, Restangular, $state) {
      $scope.credenciais = {};

      $scope.submitLogin = function(formValido) {
        $scope.submited = true;
        if (!formValido) {
          return false;
        }

        Restangular.all('login').post($scope.credenciais)
          .then(function() {
            $state.go('app.main');
          })
          .catch(function(err) {
            console.log(err);
          });
      };

    }]);

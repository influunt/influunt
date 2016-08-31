'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('LoginCtrl', ['$scope', 'Restangular', '$state', '$filter', 'influuntAlert',
    function LoginCtrl ($scope, Restangular, $state, $filter, influuntAlert) {
      $scope.credenciais = {};

      $scope.submitLogin = function(formValido) {
        $scope.submited = true;
        if (!formValido) {
          return false;
        }

        Restangular.all('login').post($scope.credenciais)
          .then(function(res) {
            localStorage.setItem('usuario', JSON.stringify(_.pick(res,['id', 'login', 'email'])));
            $state.go('app.main');
          })
          .catch(function(err) {
            if (err.status === 401) {
              err.data.forEach(function(error) {
                influuntAlert.alert($filter('translate')('geral.atencao'), error.message);
              });
            }
          });
      };

    }]);

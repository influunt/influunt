'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('MainCtrl', ['$scope', '$state', '$filter', '$controller', '$http', 'influuntAlert', 'Restangular',
    function MainCtrl($scope, $state, $filter, $controller, $http, influuntAlert, Restangular) {
      // Herda todo o comportamento de breadcrumbs.
      $controller('BreadcrumbsCtrl', {$scope: $scope});

      $scope.sair = function() {
        influuntAlert
          .confirm(
            $filter('translate')('geral.mensagens.sair'),
            $filter('translate')('geral.mensagens.confirma_saida')
          )
          .then(function(confirmado) {
            if (confirmado) {
              Restangular.one('logout', localStorage.token).remove()
                .then(function(res) {
                  localStorage.removeItem('token');
                  $state.go('login');
                })
                .catch(function(err) {
                  if (err.status === 401) {
                    err.data.forEach(function(error) {
                      influuntAlert.alert($filter('translate')('geral.atencao'), error.message);
                    });
                  }
                });
            }
          });
      };

      $scope.getUsuario = function() {
        return JSON.parse(localStorage.usuario);
      };

      $http.get('/json/menus.json').then(function(res) {
        $scope.menus = res.data;
      });
    }]);

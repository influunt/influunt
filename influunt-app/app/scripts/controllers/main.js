'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('MainCtrl', ['$scope', '$state', '$filter', '$controller', '$http', 'influuntAlert',
    function MainCtrl($scope, $state, $filter, $controller, $http, influuntAlert) {
      // Herda todo o comportamento de breadcrumbs.
      $controller('BreadcrumbsCtrl', {$scope: $scope});
      $controller('DatatablesCtrl', {$scope: $scope});

      $scope.sair = function() {
        influuntAlert
          .confirm(
            $filter('translate')('geral.mensagens.sair'),
            $filter('translate')('geral.mensagens.confirma_saida')
          )
          .then(function(confirmado) {
            if (confirmado) {
              localStorage.removeItem('token');
              $state.go('login');
            }
          });
      };

      $scope.getUsuario = function() {
        return JSON.parse(localStorage.usuario);
      };

      $http.get('/json/menus.json').then(function(res) {
        $scope.menus = res.data;
      });
    }
  ]);

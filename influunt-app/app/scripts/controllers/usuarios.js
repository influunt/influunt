'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:UsuariosCtrl
 * @description
 * # UsuariosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('UsuariosCtrl', ['$scope', '$controller', 'Restangular',
    function ($scope, $controller, Restangular) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('usuarios');

      /**
       * Recupera a lista de configuracoes que podem ser relacionadas aos modelos.
       */
      $scope.beforeShow = function() {
        Restangular.all('areas').getList().then(function(res) {
          $scope.areas = res;
        });

        Restangular.all('perfis').getList().then(function(res) {
          $scope.perfis = res;
        });
      };

    }]);

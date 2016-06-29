'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:UsuariosCtrl
 * @description
 * # UsuariosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('UsuariosCtrl', ['$scope', '$controller',
    function ($scope, $controller) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('usuarios');

    }]);

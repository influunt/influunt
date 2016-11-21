'use strict';

/**
 * @ngdoc function
 * @name influuntApp:ImporConfigCtrl
 * @description
 * # ImporConfigCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ImporConfigCtrl', ['$scope', '$controller',
    function ($scope, $controller) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('cidades');

      $scope.pesquisa = {
        campos: [
          {
            nome: 'nome',
            label: 'cidades.nome',
            tipo: 'texto'
          }
        ]
      };
    }]);

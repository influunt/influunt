'use strict';

/**
 * @ngdoc function
 * @name influuntApp:CidadesCtrl
 * @description
 * # CidadesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('CidadesCtrl', ['$scope', '$controller',
    function ($scope, $controller) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('cidades');

      $scope.pesquisa = {
        campos: [
          {
            nome: 'nome',
            label: 'Nome',
            tipo: 'texto'
          }
        ]
      };
    }]);

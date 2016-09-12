'use strict';

/**
 * @ngdoc function
 * @name influuntApp:CorredoresCtrl
 * @description
 * # CidadesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('AgrupamentosCtrl', ['$scope', '$controller', 'Restangular',
    function ($scope, $controller, Restangular) {

      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', { $scope: $scope });
      $scope.inicializaNovoCrud('agrupamentos');

      $scope.pesquisa = {
        campos: [
          {
            nome: 'nome',
            label: 'agrupamentos.nome',
            tipo: 'texto'
          },
          {
            nome: 'numero',
            label: 'agrupamentos.numero',
            tipo: 'texto'
          },
          {
            nome: 'tipo',
            label: 'agrupamentos.tipo',
            tipo: 'texto'
          }
        ]
      };

      /**
       * Recupera a lista de controladores que podem ser relacionadas ao agrupamento.
       */
      $scope.beforeShow = function() {
        Restangular.all('controladores').getList().then(function(res) {
          $scope.controladores = [];
          res.forEach(function(controlador) {
            $scope.controladores.push({ id: controlador.id, name: controlador.nomeEndereco });
          });
        });
      };

    }]);

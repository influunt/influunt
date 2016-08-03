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

'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:AreasCtrl
 * @description
 * # AreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('AreasCtrl', ['$controller', '$scope', 'Restangular',
    function ($controller, $scope, Restangular) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('areas');

      /**
       * Adiciona um novo formulario de coordenadas à area.
       */
      $scope.adicionarCoordenadas = function() {
        $scope.objeto.coordenadas = $scope.objeto.coordenadas || [];
        $scope.objeto.coordenadas.push({
          latitude: null,
          longitude: null
        });
      };

      /**
       * Remove um formulario de coordenadas da lista de coordenadas da área.
       *
       * @param      {integer}  index   The index
       */
      $scope.removerCoordenadas = function(index) {
        $scope.objeto.coordenadas.splice(index, 1);
      };

      /**
       * Recupera a lista de cidades que podem ser relacionadas à área.
       */
      $scope.beforeShow = function() {
        Restangular.all('cidades').getList().then(function(res) {
          $scope.cidades = res;
        });
      };

    }]);

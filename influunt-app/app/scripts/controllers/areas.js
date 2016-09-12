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

      $scope.pesquisa = {
        campos: [
          {
            nome: 'cidades.nome',
            label: 'areas.cidade',
            tipo: 'texto'
          },
          {
            nome: 'descricao',
            label: 'areas.descricao',
            tipo: 'texto'
          }
        ]
      };

      /**
       * Adiciona um novo formulario de coordenadas à area.
       */
      $scope.adicionarCoordenadas = function() {
        $scope.objeto.limites = $scope.objeto.limites || [];
        var posicao = $scope.objeto.limites.length + 1;
        $scope.objeto.limites.push({
          idJson: UUID.generate(),
          posicao: posicao,
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
        $scope.objeto.limites.splice(index, 1);
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

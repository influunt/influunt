'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:AreasCtrl
 * @description
 * # AreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('FabricantesCtrl', ['$controller', '$scope',
    function ($controller, $scope) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('fabricantes');

      $scope.pesquisa = {
        campos: [
          {
            nome: 'nome',
            label: 'fabricantes.nome',
            tipo: 'texto'
          },
          {
            nome: 'modelo_controladores.descricao',
            label: 'fabricantes.modelo',
            tipo: 'texto'
          }
        ]
      };

      /**
       * Adiciona um novo formulario de modelos ao fabricante.
       */
      $scope.adicionarModelo = function() {
        $scope.objeto.modelos = $scope.objeto.modelos || [];
        $scope.objeto.modelos.push({
					fabricante: {id: $scope.objeto.id},
          descricao: ''
        });
      };

      /**
       * Remove um formulario de modelo da lista de modelos do fabricante.
       *
       * @param      {integer}  index   The index
       */
      $scope.removerModelo = function(index) {
        if ($scope.objeto.modelos && $scope.objeto.modelos.length > index && index >= 0) {
          $scope.objeto.modelos.splice(index, 1);
        }
      };

      /**
       * Recupera a lista de configuracoes que podem ser relacionadas aos modelos.
       */
      $scope.beforeShow = function() {
      };

    }]);

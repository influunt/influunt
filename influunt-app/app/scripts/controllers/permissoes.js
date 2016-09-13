'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:PermissoesCtrl
 * @description
 * # PermissoesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('PermissoesCtrl', ['$scope', '$controller',
    function ($scope, $controller) {
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('permissoes');

      $scope.pesquisa = {
        campos: [
          {
            nome: 'descricao',
            label: 'permissoes.descricao',
            tipo: 'texto'
          },
          {
            nome: 'chave',
            label: 'permissoes.chave',
            tipo: 'texto'
          }
        ]
      };

    }]);

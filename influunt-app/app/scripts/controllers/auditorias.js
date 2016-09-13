'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:AuditoriasCtrl
 * @description
 * # AuditoriasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('AuditoriasCtrl', ['$scope', '$controller',
    function ($scope, $controller) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('auditorias');

      $scope.pesquisa = {
        campos: [
          {
            nome: 'change.table',
            label: 'auditorias.classe',
            tipo: 'texto'
          },
          {
            nome: 'change.type',
            label: 'auditorias.tipo',
            tipo: 'texto'
          },
          {
            nome: 'usuario.login',
            label: 'auditorias.usuario',
            tipo: 'texto'
          },
          {
            nome: 'timestamp',
            label: 'auditorias.data',
            tipo: 'data'
          }
        ]
      };

    }]);


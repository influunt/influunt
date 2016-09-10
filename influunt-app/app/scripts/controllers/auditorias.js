'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:AuditoriasCtrl
 * @description
 * # AuditoriasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('AuditoriasCtrl', ['$scope', '$controller', 'Restangular',
    function ($scope, $controller, Restangular) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('auditorias/');

      // @todo       assim que a api implementar o mesmo modelo de paginação para controladores,
      //             este metodo deverá voltar trabalhar com o metodo index padrao.
      $scope.index = function() {
        return Restangular.all('auditorias/').getList()
          .then(function(res) {
            $scope.lista = res;
          });
      };

    }]);


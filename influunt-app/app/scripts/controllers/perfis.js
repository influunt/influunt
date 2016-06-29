'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:PerfisCtrl
 * @description
 * # PerfisCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('PerfisCtrl', ['$scope', '$controller', 'Restangular',
    function ($scope, $controller, Restangular) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('perfis');

      $scope.inicializaPermissoes = function() {
        $scope.show();
      };

      $scope.beforeShow = function() {
        Restangular.all('permissoes').getList().then(function(res) {
          $scope.permissoes = res;
        });
      };

      $scope.afterShow = function() {
        $scope.checked = {};
        $scope.objeto.permissoes.forEach(function(permissao) {
          $scope.checked[permissao.id] = true;
        });
      };

      $scope.atualizaListaPermissoes = function(permissao) {
        $scope.objeto.permissoes = $scope.objeto.permissoes || [];
        var index = _.findIndex($scope.objeto.permissoes, {id: permissao.id});
        if (index >= 0) {
          $scope.objeto.permissoes.splice(index, 1);
        } else {
          $scope.objeto.permissoes.push(permissao);
        }
      };

    }]);

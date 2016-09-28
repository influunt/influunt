'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:PerfisCtrl
 * @description
 * # PerfisCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('PerfisCtrl', ['$scope', '$controller', 'Restangular', '$timeout', 'influuntBlockui',
    function ($scope, $controller, Restangular, $timeout, influuntBlockui) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('perfis');

      $scope.pesquisa = {
        campos: [
          {
            nome: 'nome',
            label: 'perfis.nome',
            tipo: 'texto'
          }
        ]
      };

      $scope.pesquisaPermissoes = {
        campos: [
          {
            nome: 'descricao',
            label: 'perfis.permissoes.descricao',
            tipo: 'texto'
          }
        ]
      };

      var perPageTimeout = null;
      $scope.onPerPageChange = function() {
        $timeout.cancel(perPageTimeout);
        perPageTimeout = $timeout(function() {
          return $scope.$state.current.name === 'app.perfis_permissoes' ? $scope.show() : $scope.index();
        }, 500);
      };

      $scope.onPageChange = function() {
        return $scope.$state.current.name === 'app.perfis_permissoes' ? $scope.show() : $scope.index();
      };

      $scope.inicializaPermissoes = function() {
        $scope.show();
      };

      $scope.beforeShow = function() {
        var query = $scope.buildQuery($scope.pesquisaPermissoes);

        Restangular.all('permissoes').customGET(null, query)
          .then(function(res) {
            $scope.permissoes = res.data;
            $scope.pagination.totalItems = res.total;
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.afterShow = function() {
        $scope.permissoesAtivadas = {};
        return $scope.objeto.permissoes &&
          $scope.objeto.permissoes.forEach(function(permissao) {
            $scope.permissoesAtivadas[permissao.id] = true;
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

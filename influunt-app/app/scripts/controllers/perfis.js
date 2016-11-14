'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:PerfisCtrl
 * @description
 * # PerfisCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('PerfisCtrl', ['$scope', '$controller', 'Restangular', '$timeout', 'influuntBlockui', 'PermissionsService',
    function ($scope, $controller, Restangular, $timeout, influuntBlockui, PermissionsService) {

      var atualizarRolesAtivos, objetoLoaded = false, permissoesLoaded = false;

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
        PermissionsService.getPermissions()
          .then(function(response) {
            $scope.permissions = response.permissoes;
            $scope.roles = response.permissoesApp;
            permissoesLoaded = true;
            if (objetoLoaded) {
              atualizarRolesAtivos();
            }
          }).finally(influuntBlockui.unblock);

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
        $scope.rolesAtivados = {};
        objetoLoaded = true;
        if (permissoesLoaded) {
          atualizarRolesAtivos();
        }
      };

      $scope.atualizarPermissoes = function() {
        var permissoes = [];
        _.forEach($scope.rolesAtivados, function(ativado, roleId) {
          var role = _.find($scope.roles, { id: roleId });
          if (ativado) {
            permissoes.push(role.permissoes);
          }
        });
        $scope.objeto.permissoes = _
          .chain(permissoes)
          .flatten()
          .map('id')
          .uniq()
          .map(function(permissaoId) { return { id: permissaoId }; })
          .value();
      };

      atualizarRolesAtivos = function() {
        _.forEach($scope.roles, function(role) {
          var permissoesRole = _.map(role.permissoes, 'id');
          var permissoesObjeto = _.map($scope.objeto.permissoes, 'id');
          $scope.rolesAtivados[role.id] = _.intersection(permissoesRole, permissoesObjeto).length === permissoesRole.length;
        });
      };

      $scope.afterSave = function() {
        PermissionsService.refreshUsuario();
      };

    }]);

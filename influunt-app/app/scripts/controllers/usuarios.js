'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:UsuariosCtrl
 * @description
 * # UsuariosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('UsuariosCtrl', ['$scope', '$controller', 'Restangular', '$state', '$timeout', 'influuntBlockui', 'PermissionsService',
    function ($scope, $controller, Restangular, $state, $timeout, influuntBlockui, PermissionsService) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('usuarios');

      $scope.pesquisa = {
        orderField: 'nome', 
        campos: [
          {
            nome: 'nome',
            label: 'usuarios.nome',
            tipo: 'texto'
          },
          {
            nome: 'login',
            label: 'usuarios.login',
            tipo: 'texto'
          },
          {
            nome: 'email',
            label: 'usuarios.email',
            tipo: 'texto'
          },
          {
            nome: 'perfil.nome',
            label: 'usuarios.perfil',
            tipo: 'texto'
          }
        ]
      };

      $scope.pesquisaAcessos = {
        campos: [
          {
            nome: 'dataCriacao',
            label: 'sessoes.dataAcesso',
            tipo: 'data'
          }
        ]
      };

      var perPageTimeout = null;
      $scope.onPerPageChange = function() {
        $timeout.cancel(perPageTimeout);
        perPageTimeout = $timeout(function() {
          return $scope.$state.current.name === 'app.usuarios_access_log' ? $scope.accessLog() : $scope.index();
        }, 500);
      };

      $scope.onPageChange = function() {
        return $scope.$state.current.name === 'app.usuarios_access_log' ? $scope.accessLog() : $scope.index();
      };

      /**
       * Recupera a lista de configuracoes que podem ser relacionadas aos modelos.
       */
      $scope.beforeShow = function() {
        PermissionsService.checkRole('editarUsuarios')
          .then(function() {
            Restangular.all('areas').customGET()
              .then(function(res) {
                $scope.areas = res.data;
              })
              .finally(influuntBlockui.unblock);

            Restangular.all('perfis').customGET()
              .then(function(res) {
                $scope.perfis = res.data;
              })
              .finally(influuntBlockui.unblock);
          });
      };

      $scope.accessLog = function() {
        var id = $state.params.id;
        var query = $scope.buildQuery($scope.pesquisaAcessos);
        query.sort = 'data_criacao';
        query.sort_type = 'desc';

        Restangular.one('usuarios', id).all('access_log').customGET(null, query)
          .then(function(res){
            $scope.sessoes = res.data;
            $scope.pagination.totalItems = res.total;
          })
          .finally(influuntBlockui.unblock);
      };

    }]);

'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:UsuariosCtrl
 * @description
 * # UsuariosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('UsuariosCtrl', ['$scope', '$controller', 'Restangular',
    function ($scope, $controller, Restangular) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('usuarios');

      $scope.pesquisa = {
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
            nome: 'perfis.nome',
            label: 'usuarios.perfil',
            tipo: 'texto'
          }
        ]
      };

      /**
       * Recupera a lista de configuracoes que podem ser relacionadas aos modelos.
       */
      $scope.beforeShow = function() {
        Restangular.all('areas').getList().then(function(res) {
          $scope.areas = res;
        });

        Restangular.all('perfis').getList().then(function(res) {
          $scope.perfis = res;
        });
      };

    }]);

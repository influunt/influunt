'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ModelosCtrl
 * @description
 * # ModelosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ModelosCtrl', ['$controller', '$scope', 'Restangular', '$filter',
    function ($controller, $scope, Restangular, $filter) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', { $scope: $scope });
      $scope.inicializaNovoCrud('modelos');

      $scope.pesquisa = {
        campos: [
          {
            nome: 'fabricante.nome',
            label: 'modelos.fabricante',
            tipo: 'texto'
          },
          {
            nome: 'descricao',
            label: 'modelos.descricao',
            tipo: 'texto'
          }
        ]
      };

      /**
       * Recupera a lista de fabricantes que podem ser relacionadas ao modelo.
       */
      $scope.beforeShow = function() {
        Restangular.all('fabricantes').customGET().then(function(res) {
          $scope.fabricantes = res.data;
        });
      };

      $scope.limitesList = function(modelo) {
        var fields = ['limiteEstagio', 'limiteGrupoSemaforico', 'limiteAnel', 'limiteDetectorPedestre', 'limiteDetectorVeicular', 'limiteTabelasEntreVerdes'];
        var list = '<ul>';
        fields.forEach(function(field) {
          list += '<li>'+$filter('translate')('modelos.'+field)+': '+modelo[field]+'</li>';
        });
        list += '</ul>';
        return list;
      };

    }]);

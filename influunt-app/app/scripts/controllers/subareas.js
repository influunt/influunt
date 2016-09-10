'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:SubAreasCtrl
 * @description
 * # SubAreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('SubAreasCtrl', ['$controller', '$scope', 'Restangular',
    function ($controller, $scope, Restangular) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('subareas');

      $scope.pesquisa = {
        campos: [
          {
            nome: 'nome',
            label: 'Nome',
            tipo: 'texto'
          },
          {
            nome: 'numero',
            label: 'Número',
            tipo: 'texto'
          }
        ]
      };

      var getHelpersControlador;
      /**
       * Recupera a lista de cidades que podem ser relacionadas à área.
       */
      $scope.beforeShow = function() {
        Restangular.all('areas').getList().then(function(res) {
          $scope.areas = res;
        });
        getHelpersControlador();
      };

      getHelpersControlador = function() {
        return Restangular.one('helpers', 'controlador').get().then(function(res) {
          $scope.data = res;
          $scope.helpers = {};

          if ($scope.objeto.area && $scope.objeto.areas) {
            var idJson = $scope.objeto.area.idJson;
            var area = _.find($scope.objeto.areas, {idJson: idJson});
            var cidade = _.find($scope.objeto.cidades, {idJson: area.cidade.idJson});

            cidade.areas = cidade.areas.map(function(area) {
              return _.find($scope.objeto.areas, {idJson: area.idJson});
            });

            $scope.helpers.cidade = cidade;
          } else {
            $scope.helpers.cidade = $scope.data.cidades[0];
            $scope.objeto.cidades = $scope.data.cidades;
            $scope.objeto.areas = _.chain($scope.data.cidades).map('areas').flatten().value();
          }
        });
      };

    }]);

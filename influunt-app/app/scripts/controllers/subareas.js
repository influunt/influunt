'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:SubAreasCtrl
 * @description
 * # SubAreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('SubAreasCtrl', ['$controller', '$scope', 'Restangular', 'influuntBlockui',
    function ($controller, $scope, Restangular, influuntBlockui) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('subareas');

      var getHelpersControlador, carregaControladoresDaSubarea, carregaControladoresSemSubarea, carregaAreas;

      $scope.pesquisa = {
        campos: [
          {
            nome: 'area.cidade.nome',
            label: 'areas.cidade',
            tipo: 'texto'
          },
          {
            nome: 'area.descricao',
            label: 'subareas.area',
            tipo: 'texto'
          },


          {
            nome: 'nome',
            label: 'subareas.nome',
            tipo: 'texto'
          },
          {
            nome: 'numero',
            label: 'subareas.numero',
            tipo: 'texto'
          }
        ]
      };

      /**
       * Recupera a lista de cidades que podem ser relacionadas à área.
       */
      $scope.beforeShow = function() {
        if ($scope.$state.current.name === 'app.subareas_show') {
          carregaControladoresDaSubarea();
        } else {
          carregaControladoresSemSubarea();
        }

        carregaAreas();
      };

      $scope.afterShow = function() {
        getHelpersControlador();
      };

      $scope.onSubmit = function() {
        $scope.objeto.controladoresAssociados = $scope.objeto.controladoresAssociados.map(function(i) {
          return {id: i.id};
        });

        return $scope.save();
      };

      carregaControladoresDaSubarea = function() {
        var filtroControladores = {
          'page': 0,
          'per_page': 300,
          'subarea.id_eq': $scope.$state.params.id
        };

        return Restangular.all('controladores').get('', filtroControladores)
          .then(function(res) {
            $scope.controladores = res.data;
          })
          .finally(influuntBlockui.unblock);
      };

      carregaControladoresSemSubarea = function() {
        return Restangular.all('controladores').customGET('sem_subarea')
          .then(function(res) {
            $scope.controladores = res;
          })
          .finally(influuntBlockui.unblock);
      };

      carregaAreas = function() {
        return Restangular.all('areas').customGET()
          .then(function(res) {
            $scope.areas = res.data;
          })
          .finally(influuntBlockui.unblock);
      };

      getHelpersControlador = function() {
        return Restangular.one('helpers', 'controlador').get()
          .then(function(res) {
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
          })
          .finally(influuntBlockui.unblock);
      };

    }]);

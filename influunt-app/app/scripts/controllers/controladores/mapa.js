'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresMapaCtrl
 * @description
 * # ControladoresMapaCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresMapaCtrl', ['$scope', '$controller', '$filter', 'Restangular',
    function ($scope, $controller, $filter, Restangular) {
      $controller('ControladoresCtrl', {$scope: $scope});

      var filtraDados, filtraControladores, getMarkersControladores, getMarkersAneis, getAreas, getDadosFiltros;

      $scope.inicializaMapa = function() {
        return $scope.index().then(function() {
          getDadosFiltros().then(function() {
            filtraDados();
          });
        });
      };

      $scope.$watch('filtro', function(value) {
        return value && filtraDados();
      }, true);

      filtraDados = function() {
        $scope.markers = [];
        $scope.areas = [];
        var controladores = filtraControladores($scope.lista, $scope.filtro);
        angular.forEach(controladores, function(controlador) {
          $scope.markers = _.concat($scope.markers, getMarkersAneis(controlador));
          $scope.markers = _.concat($scope.markers, getMarkersControladores(controlador));
          $scope.areas = _.concat($scope.areas, getAreas(controlador));
        });

        $scope.areas = _.uniqBy($scope.areas, 'popupText');
      };

      getDadosFiltros = function() {
        return Restangular.all('agrupamentos').customGET()
          .then(function(res) {
            $scope.filtro = {
              areas: _.chain($scope.lista).map('areas').flatten().uniqBy('idJson').value(),
              controladores: _.orderBy($scope.lista, ['CLC']),
              agrupamentos: res.data,

              exibirAreas: true,
              exibirControladores: true,
              exibirAneis: true
            };

            return $scope.filtro;
          });
      };

      filtraControladores = function(controladores, filtro) {
        var areasIds = _.map(filtro.areasSelecionadas, 'idJson');
        var controladoresIds = _.map(filtro.controladoresSelecionados, 'id');
        var controladoresPorAgrupamentos = _.chain(filtro.agrupamentosSelecionados)
          .map('controladores')
          .flatten()
          .map('id')
          .uniq().value();

        var lista = controladores;
        if (_.isArray(areasIds) && areasIds.length > 0) {
          lista = _.filter(lista, function(i) {return areasIds.indexOf(i.area.idJson) >= 0;});
        }

        if (_.isArray(controladoresIds) && controladoresIds.length > 0) {
          lista = _.filter(lista, function(i) {return controladoresIds.indexOf(i.id) >= 0;});
        }

        if (_.isArray(controladoresPorAgrupamentos) && controladoresPorAgrupamentos.length > 0) {
          lista = _.filter(lista, function(i) {return controladoresPorAgrupamentos.indexOf(i.id) >= 0;});
        }

        return lista;
      };

      getMarkersControladores = function(controlador) {
        if (!$scope.filtro.exibirControladores) {
          return [];
        }

        var endereco = _.find(controlador.todosEnderecos, controlador.enderecos[0]);
        return {
          latitude: endereco.latitude,
          longitude: endereco.longitude,
          popupText: '<strong>CLC: </strong>' + controlador.CLC,
          options: {
            draggable: false,
            icon: 'images/leaflet/influunt-icons/controlador.svg',
            iconSize: [32, 37],
            iconAnchor:   [16, 36],
            popupAnchor: [0, -30]
          }
        };
      };

      getMarkersAneis = function(controlador) {
        if (!$scope.filtro.exibirAneis) {
          return [];
        }

        return _.chain(controlador.aneis)
          .filter('ativo')
          .map(function(anel) {
            var endereco = _.find(controlador.todosEnderecos, anel.enderecos[0]);
            return {
              latitude: endereco.latitude,
              longitude: endereco.longitude,
              popupText: $filter('markersAneisPopup')(anel, controlador),
              options: {
                draggable: false,
                icon: 'images/leaflet/influunt-icons/anel.svg',
                iconSize: [32, 37],
                iconAnchor:   [16, 36],
                popupAnchor: [0, -30]
              }
            };
          })
          .value();
      };

      getAreas = function(controlador) {
        if (!$scope.filtro.exibirAreas) {
          return [];
        }

        return _.map(controlador.areas, function(area) {
          var ids = _.map(area.limites, 'idJson');
          var limites = _
            .chain(controlador.limites)
            .filter(function(l) {
              return ids.indexOf(l.idJson) >= 0;
            })
            .orderBy(['posicao'])
            .map(function(limite) {
              return {
                latitude: limite.latitude,
                longitude: limite.longitude
              };
            })
            .value();

          return {
            points: limites,
            popupText: '<h1>CTA' + area.descricao + '</h1>'
          };
        });
      };
    }]);

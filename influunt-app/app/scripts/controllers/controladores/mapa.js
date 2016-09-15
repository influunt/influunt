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

      var filtraDados, filtraControladores, getMarkersControladores, getMarkersAneis, getAreas, getDadosFiltros, getAgrupamentos;

      $scope.inicializaMapa = function() {
        return Restangular
          .all('controladores')
          .getList()
          .then(function(res) {
            $scope.lista = res;
            return getDadosFiltros();
          })
          .then(function() {
            return filtraDados();
          });
      };

      $scope.setCurrentObject = function(markerData) {
        if (markerData.tipo === 'ANEL') {
          // Busca anel e controlador a partir do id do elemento clicado.
          $scope.lista.some(function(c) {
            $scope.currentControlador = c;
            $scope.currentAnel = _.find($scope.currentControlador.aneis, {idJson: markerData.idJson});

            return !!$scope.currentControlador && !!$scope.currentAnel;
          });

          $scope.currentAnel.planos = _
            .chain($scope.currentAnel.planos)
            .map(function(plano) {
              return _.find($scope.currentControlador.planos, {idJson: plano.idJson});
            })
            .orderBy(['posicao'])
            .value();

          var enderecoAnel = _.find(
            $scope.currentControlador.todosEnderecos,
            {idJson: $scope.currentAnel.endereco.idJson}
          );
          $scope.currentAnel.localizacao = $filter('nomeEndereco')(enderecoAnel);
          $scope.openAcoesAnel();
          $scope.$apply();
        }
      };

      $scope.$watch('filtro', function(value) {
        return value && filtraDados();
      }, true);

      filtraDados = function() {
        $scope.markers = [];
        $scope.areas = [];
        $scope.agrupamentos = [];
        var controladores = filtraControladores($scope.lista, $scope.filtro);
        angular.forEach(controladores, function(controlador) {
          $scope.markers = _.concat($scope.markers, getMarkersAneis(controlador));
          $scope.markers = _.concat($scope.markers, getMarkersControladores(controlador));
          $scope.areas = _.concat($scope.areas, getAreas(controlador));
        });

        $scope.agrupamentos = _.concat($scope.agrupamentos, getAgrupamentos());
        $scope.areas = _.uniqBy($scope.areas, 'popupText');
      };

      getDadosFiltros = function() {
        return Restangular.all('agrupamentos').customGET()
          .then(function(res) {
            $scope.filtro = $scope.filtro || {};
            $scope.filtro.areas = _.chain($scope.lista).map('areas').flatten().uniqBy('idJson').value();
            $scope.filtro.controladores = _.orderBy($scope.lista, ['CLC']);
            $scope.filtro.agrupamentos = res.data;

            return $scope.filtro;
          });
      };

      getAgrupamentos = function() {
        return _.map($scope.filtro.agrupamentos, function(agrupamento) {
          var enderecosAgrupamento = _
            .chain(agrupamento.controladores)
            .map(function(cont) {
              return _.find($scope.lista, {id: cont.id})
            })
            .map('aneis')
            .flatten()
            .map('endereco.idJson')
            .value();

          var enderecos = _
            .chain($scope.lista)
            .map('todosEnderecos')
            .flatten()
            .uniqBy('id')
            .filter(function(e) {
              return enderecosAgrupamento.indexOf(e.idJson) >= 0;
            })
            .value();

          return {points: enderecos};
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

        var endereco = _.find(controlador.todosEnderecos, controlador.endereco);
        return {
          latitude: endereco.latitude,
          longitude: endereco.longitude,
          popupText: '<strong>CLC: </strong>' + controlador.CLC,
          options: {
            id: controlador.id,
            idJson: controlador.idJson,
            tipo: 'CONTROLADOR',
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
            var endereco = _.find(controlador.todosEnderecos, anel.endereco);
            return {
              latitude: endereco.latitude,
              longitude: endereco.longitude,
              popupText: $filter('markersAneisPopup')(anel, controlador),
              options: {
                id: anel.id,
                idJson: anel.idJson,
                tipo: 'ANEL',
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

      $scope.openPlanos = function() {
        $('.btnPlanos').on('click', function(){
          $(this).parents().eq(2).find('.planos').toggleClass('openPlanos');
        });
      };

      $scope.closeAcoesAnel = function() {
        $('.acoes').removeClass('open-acao-panel');
      };

      $scope.openAcoesAnel = function() {
        $('.acoes').addClass('open-acao-panel');
      };
    }]);

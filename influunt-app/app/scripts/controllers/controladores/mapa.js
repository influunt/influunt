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
                                        'geraDadosDiagramaIntervalo', 'influuntAlert', 'influuntBlockui',
    function ($scope, $controller, $filter, Restangular,
              geraDadosDiagramaIntervalo, influuntAlert, influuntBlockui) {
      $controller('ControladoresCtrl', {$scope: $scope});

      var filtraDados, filtraControladores, getMarkersControladores, getMarkersAneis,
          getAreas, getDadosFiltros, getAgrupamentos, getSubareas, getCoordenadasFromControladores,
          getSubareasAsAgrupamentos;

      $scope.inicializaMapa = function() {
        return Restangular
          .all('controladores')
          .all('mapas')
          .getList()
          .then(function(res) {
            $scope.lista = res;
            return getDadosFiltros();
          })
          .then(function() {
            return filtraDados();
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.setCurrentObject = function(markerData) {
        if (markerData.tipo === 'ANEL') {
          // Busca anel e controlador a partir do id do elemento clicado.
          $scope.lista.some(function(c) {
            $scope.currentControlador = c;
            $scope.currentAnel = _
              .chain($scope.currentControlador.aneis)
              .find({idJson: markerData.idJson})
              .clone()
              .value();

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
        } else {
          // quando clicar no marker controlador, deve-se abrir os dados do primeiro
          // anel (geralmente localizado no mesmo ponto do controlador).
          var controlador = _.find($scope.lista, {id: markerData.id});
          var idMarker = _.orderBy(controlador.aneis, 'posicao')[0].idJson;
          var marker = _.chain($scope.markers).map('options').find({idJson: idMarker}).value();
          $scope.setCurrentObject(marker);
        }
      };

      $scope.showDiagramaIntervalos = function(plano) {
        $scope.comCheckBoxGrupo = false;
        $scope.currentPlano = plano;
        $('#modalDiagramaIntervalos').modal('show');

        var gruposSemaforicos = $scope.currentAnel.gruposSemaforicos.map(function(gs) {
          return _.find($scope.currentControlador.gruposSemaforicos, {idJson: gs.idJson});
        });

        $scope.plano = geraDadosDiagramaIntervalo.gerar(
          plano, $scope.currentAnel, gruposSemaforicos, $scope.currentControlador
        );
        var diagramaBuilder = new influunt.components.DiagramaIntervalos($scope.plano, $scope.valoresMinimos);
        var result = diagramaBuilder.calcula();
        _.each(result.gruposSemaforicos, function(g) {
          g.ativo = true;
        });
        $scope.dadosDiagrama = result;
      };


      $scope.imporPlano = function() {
        $scope.currentAnel.currentPlano = $scope.planoImposto;
        $scope.currentAnel.hasPlanoImposto = true;
      };

      $scope.enviarPlano = function(anel) {
        var plano = anel.currentPlano || anel.planos[0];
        var endereco = _.find($scope.currentControlador.todosEnderecos, {idJson: anel.endereco.idJson});
        var localizacao = $filter('nomeEndereco')(endereco);
        influuntAlert.confirm(
          $filter('translate')('controladores.mapaControladores.enviarPlano'),
          $filter('translate')(
            'controladores.mapaControladores.confirmacaoEnvioPlano',
            {PLANO: plano.descricao, ANEL: localizacao}
          )
        )
        .then(function(confirma) {
          return confirma && influuntAlert.prompt(
            $filter('translate')('controladores.mapaControladores.enviarPlano'),
            $filter('translate')('controladores.mapaControladores.descrevaEnvioPlano')
          );
        })
        .then(function(res) {
          return res && influuntAlert.alert(
            $filter('translate')('controladores.mapaControladores.enviarPlano'),
            $filter('translate')(
              'controladores.mapaControladores.sucessoEnvioPlano',
              {PLANO: plano.descricao, ANEL: localizacao}
            )
          );
        });
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
        $scope.agrupamentos = _.concat($scope.agrupamentos, getSubareas());
        $scope.areas = _.uniqBy($scope.areas, 'id');
      };

      getDadosFiltros = function() {
        return Restangular.all('agrupamentos').customGET()
          .then(function(res) {
            $scope.filtro = $scope.filtro || {};
            $scope.filtro.areas = _.chain($scope.lista).map('areas').flatten().uniqBy('idJson').value();
            $scope.filtro.controladores = _.orderBy($scope.lista, ['CLC']);
            $scope.filtro.agrupamentos = res.data;

            var subareas = getSubareasAsAgrupamentos();
            $scope.filtro.agrupamentos = _.concat($scope.filtro.agrupamentos, subareas);

            return $scope.filtro;
          })
          .finally(influuntBlockui.unblock);
      };

      getSubareas = function() {
        if (!$scope.filtro.exibirSubareas || !$scope.filtro.exibirAgrupamentos) {
          return [];
        }

        var subareas = _
          .chain($scope.lista)
          .filter('subarea')
          .groupBy('subarea.idJson')
          .value();

          return _.map(subareas, function(controladores) {
            return {
              points: getCoordenadasFromControladores(controladores),
              type: 'SUBAREA'
            };
          });
      };

      getSubareasAsAgrupamentos = function() {
        return _
          .chain($scope.lista)
          .filter('subarea')
          .groupBy('subarea.id')
          .map(function(controladores, subarea) {
            subarea = _
              .chain($scope.lista)
              .map('areas').flatten()
              .map('subareas').flatten()
              .uniqBy('id')
              .find({id: subarea})
              .value();

            return {
              id: subarea.id,
              nome: subarea.nome,
              controladores: controladores
            };
          })
          .value();
      };

      getAgrupamentos = function() {
        if (!$scope.filtro.exibirAgrupamentos) {
          return [];
        }

        return _.map($scope.filtro.agrupamentos, function(agrupamento) {
          var controladores = _.map(agrupamento.controladores, 'id');
          controladores = _.filter($scope.lista, function(c) {
            return controladores.indexOf(c.id) >= 0;
          });

          return {
            points: getCoordenadasFromControladores(controladores),
            type: agrupamento.tipo
          };
        });
      };

      getCoordenadasFromControladores = function(controladores) {
        controladores = filtraControladores(controladores, $scope.filtro);
        var enderecosAgrupamento = _
          .chain(controladores)
          .map(function(cont) {
            return _.find($scope.lista, {id: cont.id});
          })
          .map('aneis')
          .flatten()
          .map('endereco.idJson')
          .value();

        return _
          .chain($scope.lista)
          .map('todosEnderecos')
          .flatten()
          .uniqBy('id')
          .filter(function(e) {
            return enderecosAgrupamento.indexOf(e.idJson) >= 0;
          })
          .value();
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
          .orderBy('posicao')
          .map(function(anel) {
            var endereco = _.find(controlador.todosEnderecos, anel.endereco);
            return {
              latitude: endereco.latitude,
              longitude: endereco.longitude,
              popupText: $filter('markersAneisPopup')(anel, controlador),
              options: {
                id: anel.id,
                idJson: anel.idJson,
                controladorId: controlador.id,
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
            id: area.id,
            points: limites,
            label: '<h1><strong>' + $filter('translate')('controladores.geral.CTA') + area.descricao + '</strong></h1>'
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

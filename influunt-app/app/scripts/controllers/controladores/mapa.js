'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresMapaCtrl
 * @description
 * # ControladoresMapaCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresMapaCtrl', ['$scope', '$filter', 'Restangular', 'geraDadosDiagramaIntervalo',
                                        'influuntAlert', 'influuntBlockui', 'filtrosMapa', 'planoService',
    function ($scope, $filter, Restangular, geraDadosDiagramaIntervalo,
              influuntAlert, influuntBlockui, filtrosMapa, planoService) {
      var filtraDados, getMarkersControladores, getMarkersAneis,
          getAreas, constroiFiltros, getAgrupamentos, getSubareas, getCoordenadasFromControladores;

      $scope.inicializaMapa = function() {
        return Restangular.all('controladores').all('mapas').getList()
          .then(function(res) {
            if (res.length === 0) {
              return false;
            }

            $scope.listaControladores = res;
            return Restangular.all('areas').customGET(null, {'cidade.id': $scope.listaControladores[0].cidade.id});
          })
          .then(function(res) {
            $scope.listaAneis     = _.chain($scope.listaControladores).map('aneis').flatten().uniqBy('id').value();
            $scope.listaEnderecos = _.chain($scope.listaControladores).map('todosEnderecos').flatten().uniqBy('id').value();
            $scope.listaAreas     = res.data;
            $scope.listaSubareas  = _.chain($scope.listaAreas).map('subareas').flatten().value();
            $scope.listaLimites   = _.chain($scope.listaAreas).map('limites').flatten().value();

            return Restangular.all('agrupamentos').customGET();
          })
          .then(function(res) {
            $scope.listaAgrupamentos = res.data;
            constroiFiltros();
            filtraDados();
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.$watch('filtro', function(value) {
        return value && filtraDados();
      }, true);

      constroiFiltros = function() {
        $scope.filtro = $scope.filtro || {};

        $scope.filtro.exibirAreas = true;
        $scope.filtro.exibirControladores = true;
        $scope.filtro.exibirAneis = true;
        $scope.filtro.exibirSubareas = true;
        $scope.filtro.exibirAgrupamentos = true;

        $scope.filtro.areas = _.orderBy($scope.listaAreas, 'descricao');
        $scope.filtro.subareas = _.orderBy($scope.listaSubareas, 'nome');
        $scope.filtro.controladores = _.orderBy($scope.listaControladores, 'CLC');
        $scope.filtro.agrupamentos = _.orderBy($scope.listaAgrupamentos, 'nome');

        return $scope.filtro;
      };

      filtraDados = function() {
        $scope.markers = [];
        $scope.areas = [];
        $scope.agrupamentos = [];

        var controladores = filtrosMapa.getControladores($scope.filtro, $scope.listaControladores);
        angular.forEach(controladores, function(controlador) {
          $scope.markers = _.concat($scope.markers, getMarkersAneis(controlador));
          $scope.markers = _.concat($scope.markers, getMarkersControladores(controlador));
        });

        $scope.areas = _
          .chain(controladores)
          .map('area')
          .uniqBy('idJson')
          .map(function(area) { return _.find($scope.listaAreas, {idJson: area.idJson}); })
          .value();

        $scope.areas = getAreas(filtrosMapa.getAreas($scope.areas, $scope.filtro));
        $scope.agrupamentos = _.concat($scope.agrupamentos, getAgrupamentos(
          filtrosMapa.getAgrupamentos(controladores, $scope.filtro, $scope.listaAgrupamentos))
        );

        $scope.agrupamentos = _.concat(
          $scope.agrupamentos,
          getSubareas(filtrosMapa.getSubareas($scope.filtro, controladores))
        );
      };

      getAreas = function(areas) {
        if (!$scope.filtro.exibirAreas) {
          return [];
        }

        return _.map(areas, function(area) {
          var ids = _.map(area.limites, 'idJson');
          var limites = _
            .chain($scope.listaLimites)
            .filter(function(l) { return ids.indexOf(l.idJson) >= 0; })
            .orderBy('posicao')
            .value();

          return {
            id: area.id,
            points: limites,
            label: '<h1><strong>' + $filter('translate')('controladores.geral.CTA') + area.descricao + '</strong></h1>'
          };
        });
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

      getSubareas = function(subareas) {
        if (!$scope.filtro.exibirSubareas) {
          return [];
        }

        return _.map(subareas, function(controladores) {
          return {
            points: getCoordenadasFromControladores(controladores),
            type: 'SUBAREA'
          };
        });
      };

      getAgrupamentos = function(agrupamentos) {
        if (!$scope.filtro.exibirAgrupamentos) {
          return [];
        }

        return _.map(agrupamentos, function(agrupamento) {
          var pontos = _.map(agrupamento.aneis, function(anel) {
            return _.find($scope.listaEnderecos, {
              idJson: _.find($scope.listaAneis, {id: anel.id}).endereco.idJson
            });
          });

          return {
            points: pontos,
            type: agrupamento.tipo,
            name: agrupamento.nome
          };
        });
      };

      getCoordenadasFromControladores = function(controladores) {
        controladores = filtrosMapa.getControladores($scope.filtro, controladores);
        var enderecosAgrupamento = _
          .chain(controladores)
          .map(function(cont) {
            return _.find($scope.listaControladores, {id: cont.id});
          })
          .map('aneis')
          .flatten()
          .map('endereco.idJson')
          .value();

        return _
          .chain($scope.listaControladores)
          .map('todosEnderecos')
          .flatten()
          .uniqBy('id')
          .filter(function(e) {
            return enderecosAgrupamento.indexOf(e.idJson) >= 0;
          })
          .value();
      };


      $scope.setCurrentObject = function(markerData) {
        if (markerData.tipo === 'ANEL') {
          // Busca anel e controlador a partir do id do elemento clicado.
          $scope.listaControladores.some(function(c) {
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
          var controlador = _.find($scope.listaControladores, {id: markerData.id});
          var idMarker = _.orderBy(controlador.aneis, 'posicao')[0].idJson;
          var marker = _.chain($scope.markers).map('options').find({idJson: idMarker}).value();
          $scope.setCurrentObject(marker);
        }
      };

      $scope.showDiagramaIntervalos = function(plano) {
        $scope.comCheckBoxGrupo = false;
        $scope.currentPlano = plano;

        if ($scope.currentPlano.modoOperacao === 'ATUADO' || $scope.currentPlano.modoOperacao === 'MANUAL') {
          influuntAlert.alert(
            $filter('translate')('planos.modoOperacaoSemDiagrama.tituloAlert'),
            $filter('translate')('planos.modoOperacaoSemDiagrama.textoAlert')
          );
        }

        var estagiosPlanos = planoService.atualizaEstagiosPlanos($scope.currentControlador, $scope.currentPlano);
        var valoresMinimos = planoService.montaTabelaValoresMinimos($scope.currentControlador);

        var gruposSemaforicos = _
          .chain($scope.currentAnel.gruposSemaforicos)
          .map(function(gs) {
            return _.find($scope.currentControlador.gruposSemaforicos, {idJson: gs.idJson});
          })
          .orderBy('posicao')
          .value();

        $scope.dadosDiagrama = planoService.atualizaDiagramaIntervalos(
          $scope.currentControlador, $scope.currentAnel, gruposSemaforicos,
          estagiosPlanos, $scope.currentPlano, valoresMinimos
        );

        $('#modalDiagramaIntervalos').modal('show');
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
          return res && influuntAlert.success(
            $filter('translate')('controladores.mapaControladores.enviarPlano'),
            $filter('translate')(
              'controladores.mapaControladores.sucessoEnvioPlano',
              {PLANO: plano.descricao, ANEL: localizacao}
            )
          );
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

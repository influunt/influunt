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
                                        'pahoProvider', 'eventosDinamicos', 'toast', 'mapaProvider', 'alarmesDinamicoService',
    function ($scope, $filter, Restangular, geraDadosDiagramaIntervalo,
              influuntAlert, influuntBlockui, filtrosMapa, planoService,
              pahoProvider, eventosDinamicos, toast, mapaProvider, alarmesDinamicoService) {
      var filtraDados, getMarkersControladores, getMarkersAneis, getAreas, constroiFiltros, getAgrupamentos, getSubareas,
          getCoordenadasFromControladores, registerWatchers, getIconeAnel, getIconeControlador, getPopupText,
          atualizaErros, atualizaStatusPlanos;

      var FALHA = 'FALHA';
      var OPERANDO_COM_FALHAS = 'OPERANDO_COM_FALHAS';
      var OFFLINE = 'OFFLINE';

      $scope.map = { id: {} };

      $scope.inicializaMapa = function() {
        return Restangular.all('controladores').all('mapas').getList()
        // return Promise.resolve(window.controladoresTeste)
          .then(function(res) {
            if (res.length === 0) {
              return false;
            }

            $scope.listaControladores = res;
            return Restangular.one('monitoramento', 'status_aneis').get();
          })
          .then(function(res) {
            $scope.statusObj = res;
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
            registerWatchers();
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
        $scope.areas = [];
        $scope.agrupamentos = [];

        atualizaErros();
        atualizaStatusPlanos();

        var controladores = filtrosMapa.getControladores($scope.filtro, $scope.listaControladores);
        var markers = [];
        controladores.forEach(function(controlador) {
          controlador.online = !!$scope.statusObj.onlines[controlador.controladorFisicoId];
          controlador.status = $scope.statusObj.status[controlador.controladorFisicoId];

          if (!controlador.online) {
            _.set(controlador, 'status.statusDevice', OFFLINE);
          }

          if (_.get(controlador, 'status.statusAneis')) {
            var statusAneis = controlador.status.statusAneis;
            controlador.aneis.forEach(function(anel) {
              if (anel.ativo) {
                anel.status = statusAneis[anel.posicao] || anel.status;
              }
            });
          }

          markers = markers.concat(getMarkersAneis(controlador), getMarkersControladores(controlador));
        });

        $scope.markers = markers;
        // controladores.forEach(function(controlador) {
        //   controlador.online = !!$scope.statusObj.onlines[controlador.controladorFisicoId];
        //   controlador.status = $scope.statusObj.status[controlador.controladorFisicoId];

        //   if (_.get(controlador, 'status.statusAneis')) {
        //     var statusAneis = controlador.status.statusAneis;
        //     controlador.aneis.forEach(function(anel) {
        //       if (anel.ativo) {
        //         anel.status = statusAneis[anel.posicao] || anel.status;
        //       }
        //     });
        //     // _
        //     //   .chain(controlador.aneis)
        //     //   .filter('ativo')
        //     //   .each(function(anel) {
        //     //     anel.status = statusAneis[anel.posicao] || anel.status;
        //     //   })
        //     //   .value();
        //   }

        //   if (!controlador.online) {
        //     _.set(controlador, 'status.statusDevice', OFFLINE);
        //   }

        //   markers = _.concat(markers, getMarkersAneis(controlador));
        //   markers = _.concat(markers, getMarkersControladores(controlador));
        //   $scope.markers = markers;
        // });

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

      atualizaStatusPlanos = function() {
        _.each($scope.statusObj.statusPlanos, function(statusPlano) {
          var controlador = _.find($scope.listaControladores, {controladorFisicoId: statusPlano.idControlador});
          if (controlador) {
            var anel = _.find(controlador.aneis, {posicao: parseInt(statusPlano.anelPosicao)});

            if (anel){
              anel.modoOperacao = statusPlano.modoOperacao;
              anel.hasPlanoImposto = statusPlano.hasPlanoImposto;

              var posicaoPlano = parseInt(statusPlano.planoPosicao);
              var ids = _.map(anel.planos, 'idJson');
              anel.planoVigente = _.find(controlador.planos, function(plano) {
                return ids.indexOf(plano.idJson) >= 0 && plano.posicao === posicaoPlano;
              });

              // atualiza dados do current anel na view.
              if (anel && $scope.currentAnel && $scope.currentAnel.idJson === anel.idJson) {
                $scope.currentAnel = anel;
              }
            }
          }
        });
      };

      atualizaErros = function() {
        var compare = function(a,b) {
          if (a.data < b.data){
            return -1;
          }
          if (a.data > b.data) {
            return 1;
          }
          return 0;
        };

        $scope.listaControladores.forEach(function(controlador) {
          var erros = $scope.statusObj.erros.filter(function(e){
            return e.idControlador === controlador.controladorFisicoId;
          }).sort(compare);
          controlador.erros = null;
          controlador.aneis.forEach(function(a) { a.erros = null; });

          controlador.erros = _.reject(erros, 'idAnel');
          _.chain(erros)
            .filter('idAnel')
            .groupBy('idAnel')
            .each(function(errosAnel, anelId) {
              var anel = _.find(controlador.aneis, {id: anelId});
              if (anel) {
                anel.erros = errosAnel;
              }
            })
            .value();
        });
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
        var popupText = getPopupText(controlador);

        return {
          latitude: endereco.latitude,
          longitude: endereco.longitude,
          options: {
            popupText: popupText,
            id: controlador.id,
            idJson: controlador.idJson || UUID.generate(),
            tipo: 'CONTROLADOR',
            draggable: false,
            icon: getIconeControlador(controlador.status),
            iconSize: [32, 37],
            iconAnchor:   [16, 36],
            popupAnchor: [0, -30]
          }
        };
      };

      getMarkersAneis = function(controlador) {
        return _.chain(filtrosMapa.getAneis($scope.filtro, controlador.aneis))
          .filter('ativo')
          .orderBy('posicao')
          .filter(function() {
            return $scope.filtro.exibirAneis;
          })
          .map(function(anel) {
            var iconeAnel = anel.status || anel.tipoControleVigente;
            var endereco = _.find(controlador.todosEnderecos, anel.endereco);
            var popupText = getPopupText(anel);

            return {
              latitude: endereco.latitude,
              longitude: endereco.longitude,
              options: {
                popupText: popupText,
                id: anel.id,
                idJson: anel.idJson,
                controladorId: controlador.id,
                tipo: 'ANEL',
                draggable: false,
                icon: getIconeAnel(iconeAnel),
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

        var result = [];

        // var controladoresLength = controladores.length;
        // for (var i = 0; i < controladoresLength; i++) {
        //   var controlador = controladores[i];
        //   controlador.aneis.forEach(function(anel) {
        //     result.push(controlador.todosEnderecos.find(function(e) { return e.idJson === anel.endereco.idJson; }));
        //   });
        // }

        controladores.forEach(function(controlador) {
          controlador.aneis.forEach(function(anel) {
            result.push(controlador.todosEnderecos.find(function(e) { return e.idJson === anel.endereco.idJson; }));
          });
        });

        return result;

        // var enderecosAgrupamento = _
        //   .chain(controladores)
        //   .map(function(cont) {
        //     return _.find($scope.listaControladores, {id: cont.id});
        //   })
        //   .map('aneis')
        //   .flatten()
        //   .map('endereco.idJson')
        //   .value();

        // return _
        //   .chain($scope.listaControladores)
        //   .map('todosEnderecos')
        //   .flatten()
        //   .uniqBy('id')
        //   .filter(function(e) {
        //     return enderecosAgrupamento.indexOf(e.idJson) >= 0;
        //   })
        //   .value();
      };

      registerWatchers = function() {
        var alarmes = alarmesDinamicoService($scope.statusObj);
        alarmes.setListaControladores($scope.listaControladores);
        alarmes.onEventTriggered(filtraDados);
        alarmes.onClickToast(function(target) {
          var mapa = mapaProvider.getMap($scope.map.id);
          return target && mapa.selectMarkerById(target.id);
        });
        alarmes.registerWatchers();
      };

      getPopupText = function(obj) {
        return _.size(obj.erros) > 0 && _
          .chain(obj.erros)
          .orderBy('data', 'desc')
          .map(function(i) { return '<li>' + i.descricaoEvento + '</li>'; })
          .value()
          .join('');
      };

      getIconeAnel = function(status) {
        switch (status) {
          case 'COM_FALHA':
            return 'images/leaflet/influunt-icons/anel-em-falha.svg';
          case 'MANUAL':
            return 'images/leaflet/influunt-icons/anel-controle-manual.svg';
          case 'AMARELO_INTERMITENTE_POR_FALHA':
          case 'AMARELO_INTERMITENTE_FORCADO':
            return 'images/leaflet/influunt-icons/anel-controle-local.svg';
          case 'APAGADO_POR_FALHA':
            return 'images/leaflet/influunt-icons/anel-offline.svg';
          default:
            return 'images/leaflet/influunt-icons/anel-controle-central.svg';
        }
      };

      getIconeControlador = function(status) {
        switch (status.statusDevice) {
          case FALHA:
            return 'images/leaflet/influunt-icons/controlador-em-falha.svg';
          case OPERANDO_COM_FALHAS:
            return 'images/leaflet/influunt-icons/controlador-em-falha.svg';
          case OFFLINE:
            return 'images/leaflet/influunt-icons/controlador-offline.svg';
          default:
            return 'images/leaflet/influunt-icons/controlador.svg';
        }
      };

      $scope.setCurrentObject = function(markerData) {
        if (markerData.tipo === 'ANEL') {
          // Busca anel e controlador a partir do id do elemento clicado.
          $scope.listaControladores.some(function(c) {
            $scope.currentControlador = c;
            $scope.currentAnel = _
              .chain($scope.currentControlador.aneis)
              .find({idJson: markerData.idJson})
              .set('controladorFisicoId', $scope.currentControlador.controladorFisicoId)
              .clone()
              .value();

            return !!$scope.currentControlador && !!$scope.currentAnel;
          });

          $scope.currentAnel.controlador = {id: $scope.currentControlador.id};
          $scope.currentAnel.planos = _
            .chain($scope.currentAnel.planos)
            .map(function(plano) {
              return _.find($scope.currentControlador.planos, {idJson: plano.idJson});
            })
            .orderBy('posicao')
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

          if (!marker) {
            marker = {
              tipo: 'ANEL',
              idJson: idMarker
            };
          }

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

          return false;
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

      $scope.posicaoPlano = function(plano) {
        if (plano.isManual) {
          return $filter('translate')('planos.modoManual');
        } else if (plano.isTemporario) {
          return $filter('translate')('planos.temporario');
        } else {
          return plano.posicao;
        }
      };
    }]);

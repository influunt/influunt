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
                                        'pahoProvider', 'eventosDinamicos', 'toast', 'mapaProvider', 'audioNotifier',
    function ($scope, $filter, Restangular, geraDadosDiagramaIntervalo,
              influuntAlert, influuntBlockui, filtrosMapa, planoService,
              pahoProvider, eventosDinamicos, toast, mapaProvider, audioNotifier) {
      var filtraDados, getMarkersControladores, getMarkersAneis, addFalha, removeFalha,
          getAreas, constroiFiltros, getAgrupamentos, getSubareas, getCoordenadasFromControladores,
          registerWatchers, alarmesEFalhasWatcher, trocaPlanoWatcher, statusControladoresWatcher, onlineOfflineWatcher,
          getIconeAnel, getIconeControlador, exibirAlerta, getPopupText, handleAlarmesEFalhas, handleRecuperacaoFalhas,
          notifica, setStatus, atualizaErros, atualizaStatusPlanos;

      var FALHA = 'FALHA';
      var REMOCAO_FALHA = 'REMOCAO_FALHA';
      var LOCAL = 'LOCAL';
      var MANUAL = 'MANUAL';
      var OPERANDO_COM_FALHAS = 'OPERANDO_COM_FALHAS';
      var OFFLINE = 'OFFLINE';
      var ONLINE = 'ONLINE';

      $scope.map = {
        id: {}
      };

      $scope.inicializaMapa = function() {
        return Restangular.all('controladores').all('mapas').getList()
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
        $scope.markers = [];
        $scope.areas = [];
        $scope.agrupamentos = [];

        atualizaErros();
        atualizaStatusPlanos();

        var controladores = filtrosMapa.getControladores($scope.filtro, $scope.listaControladores);
        controladores.forEach(function(controlador) {
          controlador.status = $scope.statusObj.status[controlador.id];
          controlador.online = !!$scope.statusObj.onlines[controlador.id];

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

      atualizaStatusPlanos = function() {
        _.each($scope.statusObj.statusPlanos, function(statusPlano) {
          var controlador = _.find($scope.listaControladores, {id: statusPlano.idControlador});
          if (controlador) {
            var anel = _.find(controlador.aneis, {posicao: parseInt(statusPlano.anelPosicao)});

            anel.modoOperacao = statusPlano.modoOperacao;
            anel.hasPlanoImposto = statusPlano.hasPlanoImposto;

            var posicaoPlano = parseInt(statusPlano.planoPosicao);
            var ids = _.map(anel.planos, 'idJson');
            anel.planoVigente = _.find(controlador.planos, function(plano) {
              return ids.indexOf(plano.idJson) >= 0 && plano.posicao === posicaoPlano;
            });
          }
        });
      };

      atualizaErros = function() {
        $scope.listaControladores.forEach(function(controlador) {
          var erros = _.chain($scope.statusObj.erros).filter({idControlador: controlador.id}).sort('data', 'desc').value();
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
          .filter(function(anel) {
            // Se o filtro de "exibirAneis" estiver desativado, somente o primeiro
            // anel deverá ser selecionado.
            if (!$scope.filtro.exibirAneis) {
              return anel.posicao === 1;
            }

            return true;
          })
          .map(function(anel) {
            var iconeAnel = [FALHA, OFFLINE].indexOf(anel.status) >= 0 ? anel.status : anel.tipoControleVigente;
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

      registerWatchers = function() {
        pahoProvider.connect()
          .then(function() {
            pahoProvider.register(eventosDinamicos.ALARMES_FALHAS, alarmesEFalhasWatcher);
            pahoProvider.register(eventosDinamicos.TROCA_PLANO, trocaPlanoWatcher);
            pahoProvider.register(eventosDinamicos.STATUS_CONTROLADORES, statusControladoresWatcher);
            pahoProvider.register(eventosDinamicos.CONTROLADOR_ONLINE, onlineOfflineWatcher);
            pahoProvider.register(eventosDinamicos.CONTROLADOR_OFFLINE, onlineOfflineWatcher);
          });
      };

      onlineOfflineWatcher = function(payload) {
        var mensagem = JSON.parse(payload);
        var controlador = _.find($scope.listaControladores, {id: mensagem.idControlador});

        if (!controlador) {
          console.log('controlador', mensagem.idControlador, 'não existe.');
          return false;
        }

        var isOnline = mensagem.tipoMensagem === 'CONTROLADOR_ONLINE';
        var status = isOnline ? (controlador.status || ONLINE) : OFFLINE;
        $scope.statusObj.onlines[controlador.id] = isOnline;
        $scope.statusObj.status[controlador.id] = status;
        controlador.online = isOnline;
        controlador.status = status;

        controlador.aneis.forEach(function(anel) {
          anel.online = isOnline;
          anel.status = isOnline ? (anel.status || ONLINE) : OFFLINE;
        });

        var msg = isOnline ?
          'controladores.mapaControladores.alertas.controladorOnline' :
          'controladores.mapaControladores.alertas.controladorOffline';

        msg = $filter('translate')(msg, {CONTROLADOR: controlador.CLC});
        exibirAlerta(msg, controlador, !isOnline);
        return filtraDados();
      };

      statusControladoresWatcher = function(payload) {
        var mensagem = JSON.parse(payload);
        mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
        var controlador = _.find($scope.listaControladores, {id: mensagem.idControlador});

        if (!controlador) {
          console.log('controlador', mensagem.idControlador, 'não existe.');
          return false;
        }

        controlador.status = mensagem.conteudo.status;
        $scope.statusObj.status[controlador.id] = mensagem.conteudo.status;

        var msg = $filter('translate')(
          'controladores.mapaControladores.alertas.mudancaStatusControlador',
          {CONTROLADOR: controlador.CLC}
        );
        exibirAlerta(msg, controlador);
        return filtraDados();
      };

      trocaPlanoWatcher = function(payload) {
        var mensagem = JSON.parse(payload);
        mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
        var controlador = _.find($scope.listaControladores, {id: mensagem.idControlador});

        if (!controlador) {
          console.log('controlador', mensagem.idControlador, 'não existe.');
          return false;
        }

        mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
        var posicaoAnel = parseInt(mensagem.conteudo.anel.posicao);
        var anel = _.find(controlador.aneis, {posicao: posicaoAnel});

        var statusObj = _.find($scope.statusObj.statusPlanos, function(obj) {
          return obj.idControlador === mensagem.idControlador &&
            parseInt(obj.anelPosicao) === parseInt(posicaoAnel);
        });

        if (statusObj) {
          statusObj = {
            idControlador: mensagem.idControlador,
            anelPosicao: posicaoAnel
          };

          $scope.statusObj.statusPlanos = $scope.statusObj.statusPlanos || [];
          $scope.statusObj.statusPlanos.push(statusObj);
        }

        var posicaoPlano = parseInt(mensagem.conteudo.plano.posicao);
        var ids = _.map(anel.planos, 'idJson');
        anel.planoVigente = _.find(controlador.planos, function(plano) {
          return ids.indexOf(plano.idJson) >= 0 && plano.posicao === posicaoPlano;
        });

        anel.hasPlanoImposto = mensagem.conteudo.imposicaoDePlano;
        anel.modoOperacao = mensagem.conteudo.plano.modoOperacao;
        anel.tipoControleVigente = mensagem.conteudo.plano.modoOperacao === 'MANUAL' ? 'MANUAL' : 'CENTRAL';

        statusObj.hasPlanoImposto = anel.hasPlanoImposto;
        statusObj.modoOperacao = anel.modoOperacao;
        statusObj.planoPosicao = posicaoPlano;

        var msg = $filter('translate')('controladores.mapaControladores.alertas.trocaPlanoAnel', {ANEL: anel.CLA});
        exibirAlerta(msg, anel);
        return filtraDados();
      };

      alarmesEFalhasWatcher = function(payload) {
        var mensagem = JSON.parse(payload);
        mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
        $scope.statusObj.erros = $scope.statusObj.erros || {};

        var controlador = _.find($scope.listaControladores, {id: mensagem.idControlador});
        if (!controlador) {
          console.log('controlador', mensagem.idControlador, 'não existe.');
          return false;
        }

        var posicaoAnel = _.get(mensagem, 'conteudo.params[0]');
        var anel = _.find(controlador.aneis, {posicao: posicaoAnel});

        switch(_.get(mensagem, 'conteudo.tipoEvento.tipoEventoControlador')) {
          case FALHA:
            return handleAlarmesEFalhas(mensagem, controlador, anel);
          case REMOCAO_FALHA:
            return handleRecuperacaoFalhas(mensagem, controlador, anel);
        }
      };

      exibirAlerta = function(msg, target, isPrioritario) {
        if ($scope.filtro.exibirAlertas || isPrioritario) {
          toast.warn(msg, null,{
            onclick: function() {
              var mapa = mapaProvider.getMap($scope.map.id);
              return target && mapa.selectMarkerById(target.id);
            }
          });

          audioNotifier.notify();
        }
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
          case FALHA:
            return 'images/leaflet/influunt-icons/anel-em-falha.svg';
          case MANUAL:
            return 'images/leaflet/influunt-icons/anel-controle-manual.svg';
          case LOCAL:
            return 'images/leaflet/influunt-icons/anel-controle-local.svg';
          case OFFLINE:
            return 'images/leaflet/influunt-icons/anel-offline.svg';
          default:
            return 'images/leaflet/influunt-icons/anel-controle-central.svg';
        }
      };

      getIconeControlador = function(status) {
        switch (status) {
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

      handleAlarmesEFalhas = function(mensagem, controlador, anel) {
        var msg;
        var obj = anel || controlador;

        msg = anel ? $filter('translate')(
            'controladores.mapaControladores.alertas.controladorEmFalha',
            {CONTROLADOR: controlador.CLC}
          ) : $filter('translate')(
            'controladores.mapaControladores.alertas.anelEmFalha',
            {ANEL: anel.CLA}
          );

        if (mensagem.conteudo.tipoEvento.tipoEventoControlador === FALHA) {
          setStatus(FALHA, obj, controlador, anel);
        }

        addFalha(mensagem, controlador, anel);
        notifica(msg, controlador, anel);
        return filtraDados();
      };

      handleRecuperacaoFalhas = function(mensagem, controlador, anel) {
        var msg;

        msg = anel ? $filter('translate')(
            'controladores.mapaControladores.alertas.controladorRecuperouDeFalha',
            {CONTROLADOR: controlador.CLC, FALHA: _.get(mensagem, 'conteudo.descricaoEvento')}
          ) : $filter('translate')(
            'controladores.mapaControladores.alertas.anelRecuperouDeFalha',
            {ANEL: anel.CLA, FALHA: _.get(mensagem, 'conteudo.descricaoEvento')}
          );

        removeFalha(mensagem);
        notifica(msg, controlador, anel);

        return filtraDados();
      };

      notifica = function(msg, controlador, anel) {
        // Se a visualização de controladores estiver ativa e o anel for o primeiro, a falha deverá ser
        // apresentada (visualmente) para o controlador.
        var target = controlador;
        if (anel && !(anel.posicao === 1 && $scope.filtro.exibirControladores)) {
          target = anel;
        }

        exibirAlerta(msg, target, true);
      };

      setStatus = function(status, obj, controlador, anel) {
        obj.status = status;

        if (!anel) {
          $scope.statusObj.status[obj.id] = status;
          controlador.aneis = controlador.aneis.map(function(anel) {
            anel.status = status;
            return anel;
          });
        }
      };

      removeFalha = function(mensagem) {
        $scope.statusObj.erros = _.reject($scope.statusObj.erros, function(falha) {
          return !!mensagem.conteudo.tipoEvento.tipo.match(new RegExp(falha.tipo + '$')) &&
            !!mensagem.conteudo.tipoEvento.tipoEventoControlador.match(new RegExp(falha.tipoEventoControlador + '$'));
        });
      };

      addFalha = function(mensagem, controlador, anel) {
        var endereco = anel !== null ? anel.endereco : controlador.endereco;
        endereco = _.find(controlador.todosEnderecos, {idJson: endereco.idJson});

        var objErro = {
          cla: _.get(anel, 'CLA'),
          clc: controlador.CLC,
          data: mensagem.carimboDeTempo,
          endereco: 'endereco',
          idAnel: _.get(anel, 'id'),
          idControlador: controlador.id,
          descricaoEvento: _.get(mensagem, 'conteudo.descricaoEvento'),
          tipo: mensagem.conteudo.tipoEvento.tipo,
          tipoEventoControlador: mensagem.conteudo.tipoEvento.tipoEventoControlador
        };

        $scope.statusObj.erros = $scope.statusObj.erros || [];
        $scope.statusObj.erros.push(objErro);
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

          $scope.currentAnel.controlador = {id: $scope.currentControlador.id};
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
    }]);

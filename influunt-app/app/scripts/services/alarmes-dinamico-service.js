'use strict';

/**
 * @ngdoc service
 * @name influuntApp.alarmesDinamicoService
 * @description
 * # alarmesDinamicoService
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .provider('alarmesDinamicoService', function() {
    this.$get = ['$rootScope', 'pahoProvider', 'eventosDinamicos', 'Restangular', '$filter', 'toast', 'audioNotifier', '$q', '$state',
      function alarmesDinamicoService($rootScope, pahoProvider, eventosDinamicos, Restangular, $filter, toast, audioNotifier, $q, $state) {

        var FALHA = 'FALHA';
        var REMOCAO_FALHA = 'REMOCAO_FALHA';

        var OFFLINE = 'OFFLINE';
        var ONLINE = 'ONLINE';

        // métodos privados;
        var getControlador, isAlertaAtivado, exibirAlerta, statusControladoresWatcher, alarmesEFalhasWatcher,
            trocaPlanoWatcher, handleAlarmesEFalhas, handleRecuperacaoFalhas, onlineOfflineWatcher, addFalha,
            removeFalha, setStatus, trocaPlanosMapa, trocaPlanosDashboard;
        var statusObj, $$fnOnEventTriggered, controladores;
        var $$fnonClickToast = function(){};

        var registerWatchers = function() {
          pahoProvider.connect()
            .then(function() {
              pahoProvider.register(eventosDinamicos.STATUS_CONTROLADORES, statusControladoresWatcher);
              pahoProvider.register(eventosDinamicos.ALARMES_FALHAS, alarmesEFalhasWatcher);
              pahoProvider.register(eventosDinamicos.TROCA_PLANO, trocaPlanoWatcher);
              pahoProvider.register(eventosDinamicos.CONTROLADOR_ONLINE, onlineOfflineWatcher);
              pahoProvider.register(eventosDinamicos.CONTROLADOR_OFFLINE, onlineOfflineWatcher);
            });
        };

        var unregisterWatchers = function() {
          pahoProvider.connect()
            .then(function() {
              pahoProvider.unregister(eventosDinamicos.STATUS_CONTROLADORES);
              pahoProvider.unregister(eventosDinamicos.ALARMES_FALHAS);
              pahoProvider.unregister(eventosDinamicos.TROCA_PLANO);
              pahoProvider.unregister(eventosDinamicos.CONTROLADOR_ONLINE);
              pahoProvider.unregister(eventosDinamicos.CONTROLADOR_OFFLINE);
            });
        };


        // watchers.
        statusControladoresWatcher = function(payload, topic) {
          console.log(topic, payload);
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
          statusObj.status = statusObj.status || {};

          return getControlador(mensagem.idControlador).then(function(controlador) {
            controlador.statusControlador = mensagem.conteudo.status;
            controlador.status = mensagem.conteudo.status;
            statusObj.status[mensagem.idControlador] = _.get(mensagem, 'conteudo.status');

            if (isAlertaAtivado(mensagem.tipoMensagem)) {
              var msg = $filter('translate')(
                'controladores.mapaControladores.alertas.mudancaStatusControlador',
                {CONTROLADOR: controlador.CLC}
              );

              exibirAlerta(msg, controlador);
            }

            return _.isFunction($$fnOnEventTriggered) && $$fnOnEventTriggered.apply(this, mensagem);
          });
        };

        alarmesEFalhasWatcher = function(payload, topic) {
          console.log(topic, payload);
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
          statusObj.erros = statusObj.erros || {};

          getControlador(mensagem.idControlador).then(function(controlador) {
            var posicaoAnel = _.get(mensagem, 'conteudo.params[0]');
            var anel = _.find(controlador.aneis, {posicao: posicaoAnel});

            switch(_.get(mensagem, 'conteudo.tipoEvento.tipoEventoControlador')) {
              case FALHA:
                return handleAlarmesEFalhas(mensagem, controlador, anel);
              case REMOCAO_FALHA:
                return handleRecuperacaoFalhas(mensagem, controlador, anel);
            }
          });
        };

        trocaPlanoWatcher = function(payload, topic) {
          console.log(topic, payload);
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;

          return getControlador(mensagem.idControlador)
            .then(function(controlador) {
              var posicaoAnel = parseInt(mensagem.conteudo.anel.posicao);
              var anel = _.find(controlador.aneis, {posicao: posicaoAnel});

              if ($state.current.name === 'app.mapa_controladores') {
                trocaPlanosMapa(mensagem, controlador, anel, posicaoAnel);
              } else {
                trocaPlanosDashboard(mensagem, controlador, anel, posicaoAnel);
              }

              if (isAlertaAtivado('TROCA_DE_PLANO_NO_ANEL')) {
                var msg = $filter('translate')(
                  'controladores.mapaControladores.alertas.trocaPlanoAnelEControlador',
                  {ANEL: anel.CLA, CONTROLADOR: controlador.CLC}
                );

                exibirAlerta(msg, anel);
              }

              return _.isFunction($$fnOnEventTriggered) && $$fnOnEventTriggered.apply(this, statusObj);
            });
        };

        onlineOfflineWatcher = function(payload, topic) {
          console.log(topic, payload);
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
          statusObj.onlines = statusObj.onlines || {};

          return getControlador(mensagem.idControlador)
            .then(function(controlador) {
              var isOnline = mensagem.tipoMensagem === 'CONTROLADOR_ONLINE';
              var status = isOnline ? (controlador.status || ONLINE) : OFFLINE;

              statusObj.onlines[mensagem.idControlador] = isOnline;
              statusObj.status[controlador.id] = status;
              controlador.online = isOnline;
              controlador.status = status;

              controlador.aneis.forEach(function(anel) {
                anel.online = isOnline;
                anel.status = isOnline ? ONLINE : OFFLINE;
              });

              if (isAlertaAtivado(mensagem.tipoMensagem)) {
                var msg = isOnline ?
                  'controladores.mapaControladores.alertas.controladorOnline' :
                  'controladores.mapaControladores.alertas.controladorOffline';
                msg = $filter('translate')(msg, {CONTROLADOR: controlador.CLC});

                exibirAlerta(msg, controlador);
              }

              return _.isFunction($$fnOnEventTriggered) && $$fnOnEventTriggered.apply(this, statusObj);
            });
        };


        // helpers.
        var onEventTriggered = function(fn) { $$fnOnEventTriggered = fn; };
        var onClickToast = function(fn) { $$fnonClickToast = fn; };
        var setListaControladores = function(_refControladores) { controladores = _refControladores; };

        /**
         * Processamento das trocas de plano para a tela de mapas.
         */
        trocaPlanosMapa = function(mensagem, controlador, anel, posicaoAnel) {
          var obj = _.find(statusObj.statusPlanos, function(obj) {
            return obj.idControlador === mensagem.idControlador && parseInt(obj.anelPosicao) === parseInt(posicaoAnel);
          });

          if (!obj) {
            obj = {
              idControlador: mensagem.idControlador,
              anelPosicao: posicaoAnel
            };

            statusObj.statusPlanos = statusObj.statusPlanos || [];
            statusObj.statusPlanos.push(obj);
          }

          var posicaoPlano = parseInt(mensagem.conteudo.plano.posicao);
          var ids = _.map(anel.planos, 'idJson');
          anel.planoVigente = _.find(controlador.planos, function(plano) {
            return ids.indexOf(plano.idJson) >= 0 && plano.posicao === posicaoPlano;
          });

          anel.hasPlanoImposto = mensagem.conteudo.imposicaoDePlano;
          anel.modoOperacao = mensagem.conteudo.plano.modoOperacao;
          anel.tipoControleVigente = mensagem.conteudo.plano.modoOperacao === 'MANUAL' ? 'MANUAL' : 'CENTRAL';

          obj.hasPlanoImposto = anel.hasPlanoImposto;
          obj.modoOperacao = anel.modoOperacao;
          obj.planoPosicao = posicaoPlano;
        };

        /**
         * Processamento das trocas de plano para a tela de dashboard,
         */
        trocaPlanosDashboard = function(mensagem) {
          statusObj.imposicaoPlanos = statusObj.imposicaoPlanos || {};
          statusObj.modosOperacoes  = statusObj.modosOperacoes || {};

          statusObj.modosOperacoes[mensagem.idControlador] = _.get(mensagem, 'conteudo.plano.modoOperacao');
          statusObj.imposicaoPlanos[mensagem.idControlador] = _.get(mensagem, 'conteudo.imposicaoDePlano');
        };

        handleAlarmesEFalhas = function(mensagem, controlador, anel) {
          var msg;
          var obj = anel || controlador;
          addFalha(mensagem, controlador, anel);

          if (mensagem.conteudo.tipoEvento.tipoEventoControlador === FALHA) {
            setStatus(FALHA, obj, controlador, anel);
          }

          if(isAlertaAtivado(mensagem.conteudo.tipoEvento.tipo)) {
            msg = $filter('translate')(
              'controladores.mapaControladores.alertas.controladorEmFalha',
              {CONTROLADOR: controlador.CLC}
            );
            if (anel) {
              msg = $filter('translate')('controladores.mapaControladores.alertas.anelEmFalha', {ANEL: anel.CLA});
            }

            exibirAlerta(msg, controlador);
          }

          return _.isFunction($$fnOnEventTriggered) && $$fnOnEventTriggered.apply(this, statusObj);
        };

        handleRecuperacaoFalhas = function(mensagem, controlador, anel) {
          var sampleFalha = removeFalha(mensagem);

          if (sampleFalha) {
            var msg = $filter('translate')(
              'controladores.mapaControladores.alertas.controladorRecuperouDeFalha',
              {CONTROLADOR: controlador.CLC, FALHA: sampleFalha.descricaoEvento}
            );
            if (anel) {
              msg = $filter('translate')(
                'controladores.mapaControladores.alertas.anelRecuperouDeFalha',
                {ANEL: anel.CLA, FALHA: sampleFalha.descricaoEvento}
              );
            }

            if(isAlertaAtivado(mensagem.conteudo.tipoEvento.tipo)) {
              exibirAlerta(msg, controlador);
            }

            return _.isFunction($$fnOnEventTriggered) && $$fnOnEventTriggered.apply(this, statusObj);
          }
        };

        removeFalha = function(mensagem) {
          var sampleFalha;
          _.filter(statusObj.erros, function(falha) {
            return !!mensagem.conteudo.tipoEvento.tipo.match(new RegExp(falha.tipo + '$')) &&
              !!mensagem.conteudo.tipoEvento.tipoEventoControlador.match(new RegExp(falha.tipoEventoControlador + '$'));
          })
          .map(function(falha) {
            sampleFalha = falha;
            falha.recuperado = true;
          });

          return sampleFalha;
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

          statusObj.erros = statusObj.erros || [];
          statusObj.erros.push(objErro);
        };

        setStatus = function(status, obj, controlador, anel) {
          obj.status = status;

          if (!anel) {
            statusObj.status[obj.id] = status;
            controlador.aneis = controlador.aneis.map(function(anel) {
              anel.status = status;
              return anel;
            });
          }
        };

        getControlador = function(idControlador) {
          var deferred = $q.defer();
          if (_.isArray(controladores) && controladores.length > 0) {
            var controlador = _.find(controladores, {controladorFisicoId: idControlador});
            if (controlador) {
              deferred.resolve(controlador);
            } else {
              deferred.reject('O controlador ' + idControlador + ' não existe.');
            }
          } else {
            Restangular
              .one('controladores', idControlador)
              .one('status_dinamico', null)
              .get({}, {'x-prevent-block-ui': true})
              .then(deferred.resolve)
              .catch(deferred.reject);
          }

          return deferred.promise;
        };

        isAlertaAtivado = function(chave) {
          return $rootScope.alarmesAtivados[chave] || ($rootScope.eventos && $rootScope.eventos.exibirTodosAlertas);
        };

        exibirAlerta = function(msg, target) {
          toast.warn(msg, null,{
            onclick: function() { return _.isFunction($$fnonClickToast) &&   $$fnonClickToast(target); }
          });

          audioNotifier.notify();
        };

        return function(_refStatusObj) {
          statusObj = _refStatusObj;

          return {
            registerWatchers: registerWatchers,
            unregisterWatchers: unregisterWatchers,
            setListaControladores: setListaControladores,
            onEventTriggered: onEventTriggered,
            onClickToast: onClickToast
          };
        };
      }];
  });

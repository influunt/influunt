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
    this.$get = ['$rootScope', 'pahoProvider', 'eventosDinamicos', 'Restangular', '$filter', 'toast', 'audioNotifier', '$q',
      function alarmesDinamicoService($rootScope, pahoProvider, eventosDinamicos, Restangular, $filter, toast, audioNotifier, $q) {

        var ALARME = 'ALARME';
        var FALHA = 'FALHA';
        var REMOCAO_FALHA = 'REMOCAO_FALHA';

        var OFFLINE = 'OFFLINE';
        var ONLINE = 'ONLINE';

        // métodos privados;
        var getControlador, isAlertaAtivado, exibirAlerta, statusControladoresWatcher, alarmesEFalhasWatcher,
            trocaPlanoWatcher, handleAlarmesEFalhas, handleRecuperacaoFalhas, onlineOfflineWatcher, statusTransacaoWatcher,
            dadosControladorWatcher, addFalha, removeFalha, setStatus, trocaPlanos;
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
              pahoProvider.register(eventosDinamicos.STATUS_TRANSACAO, statusTransacaoWatcher);
              pahoProvider.register(eventosDinamicos.DADOS_CONTROLADOR, dadosControladorWatcher);
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
              pahoProvider.unregister(eventosDinamicos.STATUS_TRANSACAO);
              pahoProvider.unregister(eventosDinamicos.DADOS_CONTROLADOR);
            });
        };

        // watchers.
        dadosControladorWatcher = function(payload) {
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
          statusObj.dadosControlador = {};

          statusObj.dadosControlador = mensagem.conteudo;
        };

        statusControladoresWatcher = function(payload) {
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

        alarmesEFalhasWatcher = function(payload) {
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
          statusObj.erros = statusObj.erros || {};

          getControlador(mensagem.idControlador).then(function(controlador) {
            var posicaoAnel = _.get(mensagem, 'conteudo.params[0]');
            var anel = _.find(controlador.aneis, {posicao: posicaoAnel});

            switch(_.get(mensagem, 'conteudo.tipoEvento.tipoEventoControlador')) {
              case ALARME:
              case FALHA:
                return handleAlarmesEFalhas(mensagem, controlador, anel);
              case REMOCAO_FALHA:
                return handleRecuperacaoFalhas(mensagem, controlador, anel);
            }
          });
        };

        trocaPlanoWatcher = function(payload) {
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;

          return getControlador(mensagem.idControlador)
            .then(function(controlador) {
              var posicaoAnel = parseInt(mensagem.conteudo.anel.posicao);
              var anel = _.find(controlador.aneis, {posicao: posicaoAnel});

              trocaPlanos(mensagem, controlador, anel, posicaoAnel);

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

        statusTransacaoWatcher = function(payload) {
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;

          statusObj.transacoes = {};
          _.each(mensagem.conteudo.transacoes, function(transacao) {
            var target = transacao.idAnel || transacao.idControlador;
            var isPending = mensagem.conteudo.statusPacoteTransacao === 'PENDING';
            statusObj.transacoes[target] = {
              id: mensagem.conteudo.id,
              isPending: isPending,
              statusPacote: mensagem.conteudo.statusPacoteTransacao,
              statusTransacao: transacao.etapaTransacao,
              tipoTransacao: mensagem.conteudo.tipoTransacao
            };
          });
        };

        onlineOfflineWatcher = function(payload) {
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
          statusObj.onlines = statusObj.onlines || {};

          return getControlador(mensagem.idControlador)
            .then(function(controlador) {
              var isOnline = mensagem.tipoMensagem === 'CONTROLADOR_ONLINE';
              var status = isOnline ? ONLINE : OFFLINE;

              statusObj.onlines[mensagem.idControlador] = isOnline;
              statusObj.status[mensagem.idControlador] = status;
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
        trocaPlanos = function(mensagem, controlador, anel, posicaoAnel) {
          // troca de plano do mapa.
          var obj = _.find(statusObj.statusPlanos, function(obj) {
            return obj.idControlador === mensagem.idControlador && parseInt(obj.anelPosicao) === parseInt(posicaoAnel);
          });

          if (!obj) {
            obj = {
              idControlador: mensagem.idControlador,
              anelPosicao: posicaoAnel,
              hasPlanoImposto: mensagem.conteudo.imposicaoDePlano,
              modoOperacao: mensagem.conteudo.plano.modoOperacao,
              saida: mensagem.conteudo.dataSaidaImposicao,
              tipoControleVigente: mensagem.conteudo.plano.modoOperacao === 'MANUAL' ? 'MANUAL' : 'CENTRAL'
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
          anel.saida = mensagem.conteudo.dataSaidaImposicao;
          anel.tipoControleVigente = mensagem.conteudo.plano.modoOperacao === 'MANUAL' ? 'MANUAL' : 'CENTRAL';

          obj.hasPlanoImposto = anel.hasPlanoImposto;
          obj.saida = anel.saida;
          obj.modoOperacao = anel.modoOperacao;
          obj.planoPosicao = posicaoPlano;

          // troca de plano do dashboard.
          statusObj.imposicaoPlanos = statusObj.imposicaoPlanos || {};
          statusObj.modosOperacoes  = statusObj.modosOperacoes || {};

          statusObj.modosOperacoes[anel.id] = {
            _id: anel.id,
            idControlador: mensagem.idControlador,
            modoOperacao: _.get(mensagem, 'conteudo.plano.modoOperacao'),
            timestamp: mensagem.carimboDeTempo
          };

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

            if (_.get(mensagem, 'conteudo.tipoEvento.tipoEventoControlador') === ALARME) {
              msg = $filter('translate')(
                'controladores.mapaControladores.alertas.controladorEnviouAlerta',
                {CONTROLADOR: controlador.CLC}
              );
            }

            msg = msg + ' - ' + _.get(mensagem, 'conteudo.descricaoEvento');

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
          var endereco;
          if (!!anel) {
            endereco = anel !== null ? anel.endereco : controlador.endereco;
            endereco = _.find(controlador.todosEnderecos, {idJson: endereco.idJson});
          }

          var objErro = {
            cla: _.get(anel, 'CLA'),
            clc: controlador.CLC,
            data: mensagem.carimboDeTempo,
            endereco: endereco,
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
            statusObj.status[obj.controladorFisicoId] = status;
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

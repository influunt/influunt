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
      function ($rootScope, pahoProvider, eventosDinamicos, Restangular, $filter, toast, audioNotifier, $q) {

        var FALHA = 'FALHA';
        var REMOCAO_FALHA = 'REMOCAO_FALHA';

        // métodos privados;
        var getControlador, isAlertaAtivado, exibirAlerta, statusControladoresWatcher, alarmesEFalhasWatcher,
            trocaPlanoWatcher, handleAlarmesEFalhas, handleRecuperacaoFalhas, onlineOfflineWatcher;
        var statusObj, _fnOnEventTriggered, _fnOnEventTriggered, controladores;

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

        var onEventTriggered = function(fn) { _fnOnEventTriggered = fn; };
        var onClickToast = function(fn) { _fnonClickToast = fn; };

        var setListaControladores = function(_refControladores) { controladores = _refControladores; };

        // watchers.
        statusControladoresWatcher = function(payload) {
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
          statusObj.status = statusObj.status || {};

          return getControlador(mensagem.idControlador).then(function(controlador) {
            if (!controlador) {
              console.log('controlador', mensagem.idControlador, 'não existe.');
              return false;
            }

            controlador.status = mensagem.conteudo.status;
            statusObj.status[mensagem.idControlador] = _.get(mensagem, 'conteudo.status');

            if (isAlertaAtivado(mensagem.tipoMensagem)) {
              var msg = $filter('translate')(
                'controladores.mapaControladores.alertas.mudancaStatusControlador',
                {CONTROLADOR: controlador.CLC}
              );

              exibirAlerta(msg);
            }

            return _.isFunction(_fnOnEventTriggered) && _fnOnEventTriggered.apply(this, mensagem);
          });
        };

        alarmesEFalhasWatcher = function(payload) {
          var mensagem = JSON.parse(payload);
          mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
          statusObj.erros = statusObj.erros || {};

          switch(_.get(mensagem, 'conteudo.tipoEvento.tipoEventoControlador')) {
            case FALHA:
              return handleAlarmesEFalhas(mensagem);
            case REMOCAO_FALHA:
              return handleRecuperacaoFalhas(mensagem);
          }
        };

        trocaPlanoWatcher = function(payload) {
          var mensagem = JSON.parse(payload);

          return getControlador(mensagem.idControlador)
            .then(function(controlador) {
              mensagem.conteudo = _.isString(mensagem.conteudo) ? JSON.parse(mensagem.conteudo) : mensagem.conteudo;
              var posicaoAnel = parseInt(mensagem.conteudo.anel.posicao);
              var anel = _.find(controlador.aneis, {posicao: posicaoAnel});

              statusObj.imposicaoPlanos = statusObj.imposicaoPlanos || {};
              statusObj.modosOperacoes  = statusObj.modosOperacoes || {};

              statusObj.modosOperacoes[mensagem.idControlador] = _.get(mensagem, 'conteudo.plano.modoOperacao');
              statusObj.imposicaoPlanos[mensagem.idControlador] = _.get(mensagem, 'conteudo.imposicaoDePlano');

              var msg = $filter('translate')(
                'controladores.mapaControladores.alertas.trocaPlanoAnelEControlador',
                {ANEL: anel.CLA, CONTROLADOR: controlador.CLC}
              );

              if (isAlertaAtivado('TROCA_DE_PLANO_NO_ANEL')) {
                exibirAlerta(msg);
              }


              return _.isFunction(_fnOnEventTriggered) && _fnOnEventTriggered.apply(this, statusObj);
            });
        };

        onlineOfflineWatcher = function(payload) {
          var mensagem = JSON.parse(payload);
          statusObj.onlines = statusObj.onlines || {};

          return getControlador(mensagem.idControlador)
            .then(function(controlador) {
              var isOnline = mensagem.tipoMensagem === 'CONTROLADOR_ONLINE';
              statusObj.onlines[mensagem.idControlador] = isOnline;

              if (isAlertaAtivado(mensagem.tipoMensagem)) {
                var msg = isOnline ?
                  'controladores.mapaControladores.alertas.controladorOnline' :
                  'controladores.mapaControladores.alertas.controladorOffline';
                msg = $filter('translate')(msg, {CONTROLADOR: controlador.CLC});

                exibirAlerta(msg);
              }

              return _.isFunction(_fnOnEventTriggered) && _fnOnEventTriggered.apply(this, statusObj);
            });
        };


        // helpers.
        handleAlarmesEFalhas = function(mensagem) {
          return getControlador(mensagem.idControlador)
            .then(function(controlador) {
              var posicaoAnel = _.get(mensagem, 'conteudo.params[0]');
              var anel = _.find(controlador.aneis, {posicao: posicaoAnel});
              var endereco = anel !== null ? anel.endereco : controlador.endereco;
              endereco = _.find(controlador.todosEnderecos, {idJson: endereco.idJson});

              var falha = {
                clc: controlador.CLC,
                data: mensagem.carimboDeTempo,
                endereco: $filter('nomeEndereco')(endereco),
                id: mensagem.idControlador,
                descricaoEvento: _.get(mensagem, 'conteudo.descricaoEvento'),
                tipo: _.get(mensagem, 'conteudo.tipoEvento.tipo'),
                tipoEventoControlador: _.get(mensagem, 'conteudo.tipoEvento.tipoEventoControlador')
              };

              statusObj.erros = statusObj.erros || [];
              statusObj.erros.push(falha);


              var msg = $filter('translate')('controladores.mapaControladores.alertas.controladorEmFalha', {CONTROLADOR: controlador.CLC});
              if (anel) {
                msg = $filter('translate')('controladores.mapaControladores.alertas.anelEmFalha', {ANEL: anel.CLA});
              }

              if(isAlertaAtivado(mensagem.conteudo.tipoEvento.tipo)) {
                exibirAlerta(msg);
              }

              return _.isFunction(_fnOnEventTriggered) && _fnOnEventTriggered.apply(this, statusObj);
            });
        };

        handleRecuperacaoFalhas = function(mensagem) {
          return getControlador(mensagem.idControlador)
            .then(function(controlador) {
              var sampleFalha;
              _.filter(statusObj.erros, function(falha) {
                return !!mensagem.conteudo.tipoEvento.tipo.match(new RegExp(falha.tipo + '$')) &&
                  !!mensagem.conteudo.tipoEvento.tipoEventoControlador.match(new RegExp(falha.tipoEventoControlador + '$'));
              })
              .map(function(falha) {
                sampleFalha = falha;
                falha.recuperado = true;
              });

              if (sampleFalha) {
                var posicaoAnel = _.get(mensagem, 'conteudo.params[0]');
                var anel = _.find(controlador.aneis, {posicao: posicaoAnel});

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
                  exibirAlerta(msg);
                }

                return _.isFunction(_fnOnEventTriggered) && _fnOnEventTriggered.apply(this, statusObj);
              }
            });
        };

        getControlador = function(idControlador) {
          var deferred = $q.defer();
          debugger;
          if (_.isArray(controladores) && controladores.length > 0) {
            deferred.resolve(controladores);
          } else {
            Restangular
              .one('controladores', idControlador)
              .get({}, {'x-prevent-block-ui': true})
              .then(deferred.resolve)
              .catch(deferred.reject);
          }

          return deferred.promise;
        };

        isAlertaAtivado = function(chave) {
          return $rootScope.alarmesAtivados[chave] || ($rootScope.eventos && $rootScope.eventos.exibirTodosAlertas);
        };

        exibirAlerta = function(msg) {
          toast.warn(msg);
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

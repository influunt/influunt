'use strict';

/**
 * @ngdoc service
 * @name influuntApp.imposicoesService
 * @description
 * # imposicoesService
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('imposicoesService', ['Restangular', 'influuntBlockui', '$filter', 'toast', 'mqttTransactionStatusService',
    function (Restangular, influuntBlockui, $filter, toast, mqttTransactionStatusService) {
      var LIMITE_MINIMO_DURACAO = 15;
      var LIMITE_MAXIMO_DURACAO = 600;

      var transactionTracker;
      var trackTransaction = false;

      var imposicao = function(tipo, configuracao, idsTransacoes) {
        var horarioEntrada = moment(configuracao.horarioEntradaObj.data)
          .startOf('day')
          .add(parseInt(configuracao.horarioEntradaObj.hora), 'hours')
          .add(parseInt(configuracao.horarioEntradaObj.minuto), 'minutes')
          .add(parseInt(configuracao.horarioEntradaObj.segundo), 'seconds');


        configuracao.horarioEntrada = horarioEntrada.toDate().getTime();
        // horario de entrada
        return Restangular
          .one('imposicoes', tipo)
          .post(null, configuracao)
          .then(function(response) {
            _.each(response.plain(), function(transacaoId, id) {
              idsTransacoes[id] = transacaoId;
              return trackTransaction && transactionTracker(transacaoId, tipo);
            });
          })
          .catch(console.error)
          .finally(influuntBlockui.unblock);
      };

      transactionTracker = function(id, tipo) {
        return mqttTransactionStatusService
          .watchTransaction(id)
          .then(function(transmitido) {
            if (transmitido) {
              toast.success($filter('translate')('imporConfig.' + tipo + '.sucesso'));
            } else {
              toast.warn($filter('translate')('imporConfig.' + tipo + '.erro'));
            }
          });
      };

      var setTrackTransaction = function(shouldTrack) {
        trackTransaction = shouldTrack;
      };

      return {
        LIMITE_MINIMO_DURACAO: LIMITE_MINIMO_DURACAO,
        LIMITE_MAXIMO_DURACAO: LIMITE_MAXIMO_DURACAO,
        imposicao: imposicao,
        setTrackTransaction: setTrackTransaction
      };
    }])

'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntMqttTransactionStatus
 * @description
 * # influuntMqttTransactionStatus
 */
angular.module('influuntApp')
  .factory('mqttTransactionStatusService', ['pahoProvider', 'eventosDinamicos', '$timeout', '$q',
    function (pahoProvider, eventosDinamicos, $timeout, $q) {
      var NAO_INICIADA = 'NAO_INICIADA',
          INICIADA = 'INICIADA',
          ERRO = 'ERRO',
          OK = 'OK';

      var currentStatus = NAO_INICIADA;
      var possuiTransaction = false;

      var isTransacaoNova = function() {
        return possuiTransaction && currentStatus === NAO_INICIADA;
      };

      var isTransacaoIniciada = function() {
        return currentStatus === INICIADA;
      };

      var isTransacaoFinalizada = function() {
        return currentStatus === ERRO || currentStatus === OK;
      };

      var currentStatusAsBoolean = function() {
        return currentStatus === OK;
      };

      var watchTransaction = function(transactionId) {
        var deferred = $q.defer();
        possuiTransaction = true;
        var topic = eventosDinamicos.STATUS_TRANSACAO.replace(':transacaoId', transactionId);

        pahoProvider.connect()
          .then(function() {
            pahoProvider.register(topic, function(message) {
              $timeout(function() {
                var msg = JSON.parse(message);
                var conteudo = JSON.parse(msg.conteudo);
                currentStatus = _.get(conteudo, 'status') || ERRO;

                if (isTransacaoFinalizada()) {
                  pahoProvider.unregister(topic);
                  deferred.resolve(currentStatusAsBoolean());
                }
              });
            }, true);
          })
          .catch(function(e) { console.log('erro ao se conectar com o paho: ', e); });

        return deferred.promise;
      };

      return {
        isTransacaoNova: isTransacaoNova,
        isTransacaoIniciada: isTransacaoIniciada,
        isTransacaoFinalizada: isTransacaoFinalizada,
        currentStatusAsBoolean: currentStatusAsBoolean,
        watchTransaction: watchTransaction
      };

    }])
  .directive('influuntMqttTransactionStatus', ['mqttTransactionStatusService',
    function (mqttTransactionStatusService) {
      return {
        templateUrl: 'views/directives/influunt-mqtt-transaction-status.html',
        restrict: 'E',
        scope: {
          transactionId: '='
        },
        link: function(scope) {

          scope.isTransacaoNova = function() {
            return mqttTransactionStatusService.isTransacaoNova();
          };

          scope.isTransacaoIniciada = function() {
            return mqttTransactionStatusService.isTransacaoIniciada();
          };

          scope.isTransacaoFinalizada = function() {
            return mqttTransactionStatusService.isTransacaoFinalizada();
          };

          scope.currentStatusAsBoolean = function() {
            return mqttTransactionStatusService.currentStatusAsBoolean();
          };

          scope.$watch('transactionId', function(transactionId) {
            return transactionId && mqttTransactionStatusService.watchTransaction(transactionId);
          });
        }
      };
    }]);

'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntMqttTransactionStatus
 * @description
 * # influuntMqttTransactionStatus
 */
angular.module('influuntApp')
  .directive('influuntMqttTransactionStatus', ['pahoProvider', 'eventosDinamicos',
    function (pahoProvider, eventosDinamicos) {
      return {
        templateUrl: 'views/directives/influunt-mqtt-transaction-status.html',
        restrict: 'E',
        scope: {
          transactionId: '='
        },
        link: function(scope) {
          var NAO_INICIADA = 'NAO_INICIADA',
              INICIADA = 'INICIADA',
              ERRO = 'ERRO',
              OK = 'OK';

          scope.currentStatus = NAO_INICIADA;

          scope.possuiTransaction = false;

          scope.isTransacaoNova = function() {
            return scope.possuiTransaction && scope.currentStatus === NAO_INICIADA;
          };

          scope.isTransacaoIniciada = function() {
            return scope.currentStatus === INICIADA;
          };

          scope.isTransacaoFinalizada = function() {
            return scope.currentStatus === ERRO || scope.currentStatus === OK;
          };

          scope.currentStatusAsBoolean = function() {
            return scope.currentStatus === OK;
          };

          scope.$watch('transactionId', function(transactionId) {
            if (transactionId) {
              scope.possuiTransaction = true;
              var topic = eventosDinamicos.STATUS_TRANSACAO.replace(':transacaoId', transactionId);
              console.log('vai se conectar ao paho...')
              pahoProvider.connect()
                .then(function() {
                  console.log('conectado! registrando listener no topic ' + topic)
                  pahoProvider.register(topic, function(message, topic) {
                    scope.$apply(function() {
                      console.log('mqtt chamou!')
                      var msg = JSON.parse(message);
                      console.log('message: ', msg)
                      var conteudo = JSON.parse(msg.conteudo);
                      scope.currentStatus = _.get(conteudo, 'status') || ERRO;
                      console.log('current status: ' + scope.currentStatus)

                      if (scope.isTransacaoFinalizada()) {
                        pahoProvider.disconnect();
                      }
                    });
                  }, true);
                })
                .catch(function(e) { console.log('erro ao se conectar com o paho: ', e) });
            }
          });
        }
      };
    }]);

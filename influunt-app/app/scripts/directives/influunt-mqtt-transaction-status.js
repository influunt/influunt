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
      var NOT_STARTED = 'NOT_STARTED',
          NEW = 'NEW',
          ABORTED = 'ABORTED',
          PENDING = 'PENDING',
          DONE = 'DONE';

      var currentStatus = NOT_STARTED;
      var possuiTransaction = false;

      // var isTransacaoNova = function() {
      //   return possuiTransaction && currentStatus === NOT_STARTED;
      // };

      // var isTransacaoIniciada = function() {
      //   return currentStatus === NEW;
      // };

      // var isTransacaoFinalizada = function() {
      //   return currentStatus === ABORTED || currentStatus === DONE;
      // };

      // var currentStatusAsBoolean = function() {
      //   return currentStatus === DONE;
      // };

//       var watchTransaction = function(transactionId) {
//         var deferred = $q.defer();
//         possuiTransaction = true;
//         var topic = eventosDinamicos.STATUS_TRANSACAO.replace(':transacaoId', transactionId);
// console.log('topic> ', topic);
//         pahoProvider.connect()
//           .then(function() {
//             pahoProvider.register(topic, function(message) {
// console.log(message);
//               $timeout(function() {
//                 var msg = JSON.parse(message);
//                 var conteudo = JSON.parse(msg.conteudo);
//                 currentStatus = _.get(conteudo, 'status') || ABORTED;

//                 if (isTransacaoFinalizada()) {
//                   pahoProvider.unregister(topic);
//                   deferred.resolve(currentStatusAsBoolean());
//                 }
//               });
//             }, true);
//           })
//           .catch(function(e) { console.log('erro ao se conectar com o paho: ', e); });

//         return deferred.promise;
//       };

      var watchDadosControlador = function(envelopeId) {
        var deferred = $q.defer();
        possuiTransaction = false;
        var topic = eventosDinamicos.DADOS_CONTROLADOR.replace(':envelopeId', envelopeId);
        pahoProvider.connect()
          .then(function() {
            pahoProvider.register(topic, function(message) {
              $timeout(function() {
                var msg = JSON.parse(message);
                var conteudo = JSON.parse(msg.conteudo);
                pahoProvider.unregister(topic);
                deferred.resolve(conteudo);
              });
            }, true);
          })
          .catch(function(e) { console.log('erro ao se conectar com o paho: ', e); });

        return deferred.promise;
      };

      return {
        // isTransacaoNova: isTransacaoNova,
        // isTransacaoIniciada: isTransacaoIniciada,
        // isTransacaoFinalizada: isTransacaoFinalizada,
        // currentStatusAsBoolean: currentStatusAsBoolean,
        // watchTransaction: watchTransaction,
        watchDadosControlador: watchDadosControlador
      };

    }])
  .directive('influuntMqttTransactionStatus', ['mqttTransactionStatusService',
    function (mqttTransactionStatusService) {
      return {
        templateUrl: 'views/directives/influunt-mqtt-transaction-status.html',
        restrict: 'E',
        scope: {
          statusTransaction: '='
        },
        // link: function(scope) {

        //   var NOT_STARTED = 'NOT_STARTED',
        //       NEW = 'NEW',
        //       ABORTED = 'ABORTED',
        //       ABORTED = 'ABORTED',
        //       DONE = 'DONE';

        //   var isTransacaoNova = function() {
        //     return scope.statusTransaction === NOT_STARTED;
        //   };

        //   var isTransacaoIniciada = function() {
        //     return scope.statusTransaction === NEW;
        //   };

        //   var isTransacaoFinalizada = function() {
        //     return scope.statusTransaction === ABORTED || scope.statusTransaction === DONE;
        //   };

        //   var currentStatusAsBoolean = function() {
        //     return scope.statusTransaction === DONE;
        //   };

        //   scope.isTransacaoNova = function() {
        //     return mqttTransactionStatusService.isTransacaoNova();
        //   };

        //   scope.isTransacaoIniciada = function() {
        //     return mqttTransactionStatusService.isTransacaoIniciada();
        //   };

        //   scope.isTransacaoFinalizada = function() {
        //     return mqttTransactionStatusService.isTransacaoFinalizada();
        //   };

        //   scope.currentStatusAsBoolean = function() {
        //     return mqttTransactionStatusService.currentStatusAsBoolean();
        //   };

        //   scope.$watch('statusTransaction', function(statusTransaction) {
        //     return statusTransaction && mqttTransactionStatusService.watchTransaction(statusTransaction);
        //   });
        // }
      };
    }]);

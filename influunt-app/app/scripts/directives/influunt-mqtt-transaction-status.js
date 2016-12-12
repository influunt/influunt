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
      var possuiTransaction = false;

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
        watchDadosControlador: watchDadosControlador
      };

    }])
  .directive('influuntMqttTransactionStatus', [
    function () {
      return {
        templateUrl: 'views/directives/influunt-mqtt-transaction-status.html',
        restrict: 'E',
        scope: {
          statusTransaction: '='
        }
      };
    }]);

'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntMqttTransactionStatus
 * @description
 * # influuntMqttTransactionStatus
 */
angular.module('influuntApp')
  .directive('influuntMqttTransactionStatus', [
    function () {
      return {
        templateUrl: 'views/directives/influunt-mqtt-transaction-status.html',
        restrict: 'E',
        scope: {
          statusPacote: '<',
          etapaTransacao: '<'
        },
        link: function(scope) {

          scope.pacoteFinalizou = function() {
            return scope.statusPacote === 'ABORTED' || scope.statusPacote === 'DONE';
          };

          scope.pendingComErro = function() {
            return scope.statusPacote === 'PENDING' && scope.etapaTransacao === 'ABORT';
          };

        }
      };
    }]);

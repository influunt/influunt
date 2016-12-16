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
          statusTransaction: '='
        }
      };
    }]);

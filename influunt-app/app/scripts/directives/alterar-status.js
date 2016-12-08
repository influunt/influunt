'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:alterarStatus
 * @description
 * # alterarStatus
 */
angular.module('influuntApp')
  .directive('alterarStatus', ['Restangular', 'influuntBlockui', '$filter', 'toast', 'mqttTransactionStatusService',
   function (Restangular, influuntBlockui, $filter, toast, mqttTransactionStatusService) {
    return {
      templateUrl: 'views/directives/alterar-status.html',
      restrict: 'E',
      scope: {
        aneisSelecionados: '=',
        idsTransacoes: '=',
        trackTransaction: '='
      },
      link: function alterarStatus(scope) {
        var transactionTracker;
        scope.alterar = function() {
          scope.idsTransacoes = {};
          var idsAneisSelecionados = _.map(scope.aneisSelecionados, 'id');
          var resource, data;
          resource = scope.dataStatus.tipo;
          data = idsAneisSelecionados;

          return Restangular.one('imposicoes').customPOST(data, resource)
            .then(function(response) {
              _.each(response.plain(), function(transacaoId, controladorId) {
                scope.idsTransacoes[controladorId] = transacaoId;
                return scope.trackTransaction && transactionTracker(transacaoId);
              });
            })
            .finally(influuntBlockui.unblock);
      };

      transactionTracker = function(id) {
        return mqttTransactionStatusService
          .watchTransaction(id)
          .then(function(transmitido) {
            if (transmitido) {
              toast.success($filter('translate')('imporConfig.aletarcaoStatus.sucesso'));
            } else {
              toast.warn($filter('translate')('imporConfig.aletarcaoStatus.erro'));
            }
          });
      };
    }
  };
}]);

'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:liberar
 * @description
 * # liberar
 */
angular.module('influuntApp')
  .directive('liberarImposicao', ['Restangular', 'influuntBlockui', 'influuntAlert', '$filter', 'toast', 'mqttTransactionStatusService',
    function (Restangular, influuntBlockui, influuntAlert, $filter, toast, mqttTransactionStatusService) {
      return {
        template: '<a data-ng-click="liberarPlanoImposto(anel)">{{ "imporConfig.liberacao.liberar" | translate }}</a>',
        restrict: 'E',
        scope: {
          anel: '=',
          idsTransacoes: '='
        },
        link: function liberar(scope) {
          scope.idsTransacoes = {};
          var transactionTracker;
          scope.liberarPlanoImposto = function() {
            return influuntAlert.confirm($filter('translate')('imporConfig.liberacao.confirmLiberarImposicao'))
              .then(function(res) {
                return res && Restangular.all('imposicoes')
                  .all('liberar')
                  .post({
                    aneisIds: [scope.anel.id],
                    timeout: 60
                  });
              })
              .then(function(response) {
                console.log(response);
                _.each(response.plain(), function(transacaoId, id) {
                  scope.idsTransacoes[id] = transacaoId;
                  return transactionTracker(transacaoId);
                });
              })
              .finally(influuntBlockui.unblock);
          };

          transactionTracker = function(id) {
            toast.success($filter('translate')('imporConfig.liberacao.sucesso'));
          };
        }
      };
    }]);

'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:sincronizacao
 * @description
 * # sincronizacao
 */
angular.module('influuntApp')
  .directive('sincronizacao', ['Restangular', 'influuntBlockui', '$filter', 'toast', 'mqttTransactionStatusService',
    function (Restangular, influuntBlockui, $filter, toast, mqttTransactionStatusService) {
      return {
        templateUrl: 'views/directives/imposicoes/sincronizacao.html',
        restrict: 'E',
        scope: {
          aneisSelecionados: '=',
          idsTransacoes: '=',
          trackTransaction: '='
        },
        link: function sincronizacao(scope) {
          scope.sincronizar = function() {
            var resource = scope.dataSincronizar.tipo === 'pacotePlanos' ? 'pacote_plano' : 'configuracao_completa';
            var idsAneisSelecionados = _.map(scope.aneisSelecionados, 'id');
            return Restangular.one('imposicoes').customPOST(idsAneisSelecionados, resource)
              .then(function(response) {
                _.each(response.plain(), function(transacaoId, controladorId) {
                  scope.idsTransacoes[controladorId] = transacaoId;
                  return scope.trackTransaction && transactionTracker(transacaoId);
                });
              })
              .finally(influuntBlockui.unblock);
          };

          var transactionTracker = function(id) {
            return mqttTransactionStatusService
              .watchTransaction(id)
              .then(function(transmitido) {
                if (transmitido) {
                  toast.success($filter('translate')('imporConfig.sincronizacao.sincronizadoComSucesso'));
                } else {
                  toast.warn($filter('translate')('imporConfig.sincronizacao.sincronizadoComErro'));
                }
              });
          };
        }
      };
    }]);

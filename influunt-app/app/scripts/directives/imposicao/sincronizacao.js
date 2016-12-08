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
        templateUrl: 'views/directives/imposicoes/sincronizacao-form.html',
        restrict: 'E',
        scope: {
          aneisSelecionados: '=',
          idsTransacoes: '=',
          trackTransaction: '=',
          dismissOnSubmit: '=',
          timeout: '='
        },
        link: function sincronizacao(scope, el) {
          var transactionTracker;
          scope.sincronizar = function() {
            scope.idsTransacoes = {};
            var idsAneisSelecionados = _.map(scope.aneisSelecionados, 'id');
            var resource = scope.dataSincronizar.tipo;
            var data = {
              aneisIds: idsAneisSelecionados,
              timeout: scope.timeout
            };

            if (resource.match(/tabela_horaria/)) {
              resource = 'tabela_horaria';
              data.imediato = scope.dataSincronizar.tipo === 'tabela_horaria_imediato';
            }

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
                  toast.success($filter('translate')('imporConfig.sincronizacao.sucesso'));
                } else {
                  toast.warn($filter('translate')('imporConfig.sincronizacao.erro'));
                }
              });
          };

          $(el).find('#sincronizar-submit').on('click', function() {
            if (scope.dismissOnSubmit) {
              $('#modal-sincronizar').modal('toggle');
            }
          });
        }
      };
    }])

  .directive('sincronizacaoPopup', [function () {
    return {
      templateUrl: 'views/directives/imposicoes/sincronizacao-popup.html',
      restrict: 'E',
      scope: {
        aneisSelecionados: '=',
        idsTransacoes: '=',
        trackTransaction: '='
      }
    };
  }])
;

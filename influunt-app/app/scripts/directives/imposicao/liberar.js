'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:liberar
 * @description
 * # liberar
 */
angular.module('influuntApp')
  .directive('liberarImposicao', ['Restangular', 'influuntBlockui', 'influuntAlert', '$filter', 'toast',
    function (Restangular, influuntBlockui, influuntAlert, $filter, toast) {
      return {
        template: '<a data-ng-click="liberarPlanoImposto()">{{ "imporConfig.liberacao.liberar" | translate }}</a>',
        restrict: 'E',
        scope: {
          anel: '=',
          transacoes: '='
        },
        link: function liberar(scope) {
          scope.idsTransacoes = {};
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
              .finally(influuntBlockui.unblock);
          };

          scope.$watch('transacoes', function(transacoes) {
            if (_.isObject(transacoes) && _.isObject(scope.anel)) {
              var transacao = transacoes[scope.anel.id] || transacoes[scope.anel.controladorFisicoId];

              if (!_.isObject(transacao)) {
                return false;
              }

              if (transacao.tipoTransacao === 'LIBERAR_IMPOSICAO' && transacao.status === 'DONE') {
                toast.success($filter('translate')('imporConfig.liberacao.sucesso'));
              } else if (transacao.tipoTransacao === 'LIBERAR_IMPOSICAO' && transacao.status === 'ABORT') {
                // @todo: Adicionar mensagem de erro do mqtt?
                toast.warn($filter('translate')('imporConfig.liberacao.erro'));
              }
            }
          }, true);
        }
      };
    }]);

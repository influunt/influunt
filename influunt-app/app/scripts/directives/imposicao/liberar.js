'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:liberar
 * @description
 * # liberar
 */
angular.module('influuntApp')
  .directive('liberarImposicao', ['Restangular', 'influuntBlockui', 'influuntAlert', '$filter', 'liberarImposicaoNotifier',
    function (Restangular, influuntBlockui, influuntAlert, $filter, liberarImposicaoNotifier) {
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
            liberarImposicaoNotifier.notify(transacoes, scope.anel);
          }, true);
        }
      };
    }])
    .factory('liberarImposicaoNotifier', ['$filter', 'toast', function ($filter, toast) {
      var debouncerId;
      var notify = function(transacoes, anel) {
          if (_.isObject(transacoes) && _.isObject(anel)) {
            var transacao = transacoes[anel.id] || transacoes[anel.controladorFisicoId];

            if (!_.isObject(transacao)) {
              return false;
            }

            if (transacao.tipoTransacao === 'LIBERAR_IMPOSICAO') {
              clearTimeout(debouncerId);
              debouncerId = setTimeout(function() {
                if (transacao.statusPacote === 'DONE') {
                  toast.success($filter('translate')('imporConfig.liberacao.sucesso'));
                } else if (transacao.statusPacote === 'ABORT') {
                  // @todo: Adicionar mensagem de erro do mqtt?
                  toast.warn($filter('translate')('imporConfig.liberacao.erro'));
                }
              }, 200);
            }
          }
      };

      return {
        notify: notify
      };
    }])
  ;

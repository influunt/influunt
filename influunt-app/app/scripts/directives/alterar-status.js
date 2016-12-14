'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:alterarStatus
 * @description
 * # alterarStatus
 */
angular.module('influuntApp')
  .directive('alterarStatus', ['Restangular', 'influuntBlockui', '$filter', 'toast',
    function (Restangular, influuntBlockui, $filter, toast) {
      return {
        templateUrl: 'views/directives/alterar-status.html',
        restrict: 'E',
        scope: {
          aneisSelecionados: '=',
          idsTransacoes: '=',
          trackTransaction: '=',
          dismissOnSubmit: '=',
          timeout: '=?',
          transacoes: '=?'
        },
        link: function alterarStatus(scope) {
          var TIPOS_TRANSACOES = ['COLOCAR_CONTROLADOR_MANUTENCAO', 'INATIVAR_CONTROLADOR', 'ATIVAR_CONTROLADOR'];
          var DEFAULT_TIMEOUT = 60;
          scope.alterar = function() {
            scope.idsTransacoes = {};
            var idsAneisSelecionados = _.map(scope.aneisSelecionados, 'id');
            var resource;
            resource = scope.dataStatus.tipo;
            var data = {
              aneisIds: idsAneisSelecionados,
              timeout: scope.timeout || DEFAULT_TIMEOUT
            };

            return Restangular.one('imposicoes').customPOST(data, resource)
              .finally(influuntBlockui.unblock);
          };

          /**
           * Exibição de mensagem de sucesso para mapas (toast).
           */
          scope.$watch('transacoes', function(transacoes) {
            if (_.isObject(transacoes) && _.isArray(scope.aneisSelecionados)) {
              _.each(scope.aneisSelecionados, function(anel) {
                if (anel) {
                  var transacao = transacoes[anel.controladorFisicoId];
                  var isPacoteTabelaHoraria = transacao && TIPOS_TRANSACOES.indexOf(transacao.tipoTransacao) >= 0;

                  if (isPacoteTabelaHoraria && transacao.status === 'DONE') {
                    toast.success($filter('translate')('imporConfig.aletarcaoStatus.sucesso'));
                  } else if (isPacoteTabelaHoraria && transacao.status === 'ABORTED') {
                    // @todo: Adicionar mensagem de erro do mqtt?
                    toast.error($filter('translate')('imporConfig.aletarcaoStatus.erro'));
                  }
                }
              });
            }
          }, true);
        }
      };
    }])
  .directive('alterarStatusPopup', [function () {
    return {
      templateUrl: 'views/directives/alterar-status-popup.html',
      restrict: 'E',
      scope: {
        aneisSelecionados: '=',
        idsTransacoes: '=',
        trackTransaction: '=',
        transacoes: '=?'
      }
    };
  }])
  ;

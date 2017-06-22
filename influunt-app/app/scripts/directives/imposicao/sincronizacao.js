'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:sincronizacao
 * @description
 * # sincronizacao
 */
angular.module('influuntApp')
  .directive('sincronizacao', ['Restangular', 'influuntBlockui', '$filter', 'toast',
    function (Restangular, influuntBlockui, $filter, toast) {
      return {
        templateUrl: 'views/directives/imposicoes/sincronizacao-form.html',
        restrict: 'E',
        scope: {
          aneisSelecionados: '=',
          idsTransacoes: '=',
          trackTransaction: '=',
          dismissOnSubmit: '=',
          timeout: '=?',
          transacoes: '=?'
        },
        link: function sincronizacao(scope, el) {
          var DEFAULT_TIMEOUT = 60;
          var TIPOS_TRANSACOES = ['PACOTE_TABELA_HORARIA', 'PACOTE_PLANO', 'CONFIGURACAO_COMPLETA'];
          scope.sincronizar = function() {
            scope.idsTransacoes = {};
            var idsAneisSelecionados = _.map(scope.aneisSelecionados, 'id');
            var resource = scope.dataSincronizar.tipo;
            var data = {
              aneisIds: idsAneisSelecionados,
              timeout: scope.timeout || DEFAULT_TIMEOUT
            };

            if (resource.match(/tabela_horaria/)) {
              resource = 'tabela_horaria';
              data.imediato = scope.dataSincronizar.tipo === 'tabela_horaria_imediato';
            }

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
                  var isPacoteTabelaHoraria = transacao && TIPOS_TRANSACOES.indexOf(transacao.tipoTransacao)  >= 0;

                  if (isPacoteTabelaHoraria && transacao.statusPacote === 'DONE') {
                    toast.success($filter('translate')('imporConfig.sincronizacao.sucesso'));
                  } else if (isPacoteTabelaHoraria && transacao.statusPacote === 'ABORTED') {
                    // @todo: Adicionar mensagem de erro do mqtt?
                    toast.error($filter('translate')('imporConfig.sincronizacao.erro'));
                  }
                }
              });
            }
          }, true);

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
        trackTransaction: '=',
        transacoes: '=?'
      }
    };
  }])
;

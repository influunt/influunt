'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:imposicaoModo
 * @description
 * # imposicaoModo
 */
angular.module('influuntApp')
  .directive('imposicaoModo', ['imposicoesService',
      function (imposicoesService) {
      return {
        templateUrl: 'views/directives/imposicoes/imposicao-modo-form.html',
        restrict: 'E',
        scope: {
          aneisSelecionados: '=',
          idsTransacoes: '=',
          trackTransaction: '=',
          dismissOnSubmit: '=',
          timeout: '=?',
          transacoes: '=?'
        },
        link: function postLink(scope, el) {
          var DEFAULT_TIMEOUT = 60;
          scope.LIMITE_MINIMO_DURACAO = imposicoesService.LIMITE_MINIMO_DURACAO;
          scope.LIMITE_MAXIMO_DURACAO = imposicoesService.LIMITE_MAXIMO_DURACAO;

          scope.configuracao = {};
          scope.imporModo = function() {
            scope.configuracao.timeout = scope.timeout || DEFAULT_TIMEOUT;
            scope.idsTransacoes = {};
            return imposicoesService.imposicao('modo_operacao', scope.configuracao, scope.idsTransacoes);
          };

          scope.$watch('aneisSelecionados', function(aneisSelecionados) {
            if (_.isArray(aneisSelecionados)) {
              scope.configuracao.aneisIds = _.map(aneisSelecionados, 'id');
            }
          }, true);

          scope.$watch('transacoes', function(transacoes) {
            imposicoesService.alertStatusTransacao(transacoes, scope.aneisSelecionados, 'modo_operacao');
          }, true);

          $(el).find('#impor-modo-submit').on('click', function() {
            if (scope.dismissOnSubmit) {
              $('#modal-impor-modo').modal('toggle');
            }
          });
        }
      };
    }])

  .directive('imposicaoModoPopup', [function () {
    return {
      templateUrl: 'views/directives/imposicoes/imposicao-modo-popup.html',
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

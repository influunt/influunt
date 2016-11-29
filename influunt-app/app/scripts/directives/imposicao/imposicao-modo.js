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
        templateUrl: 'views/directives/imposicoes/imposicao-modo.html',
        restrict: 'E',
        scope: {
          aneisSelecionados: '=',
          idsTransacoes: '=',
          trackTransaction: '='
        },
        link: function postLink(scope) {
          scope.LIMITE_MINIMO_DURACAO = imposicoesService.LIMITE_MINIMO_DURACAO;
          scope.LIMITE_MAXIMO_DURACAO = imposicoesService.LIMITE_MAXIMO_DURACAO;

          imposicoesService.setTrackTransaction(!!scope.trackTransaction);

          scope.configuracao = {};
          scope.imporModo = function() {
            scope.idsTransacoes = {};
            return imposicoesService.imposicao('modo_operacao', scope.configuracao, scope.idsTransacoes);
          };

          scope.$watch('aneisSelecionados', function(aneisSelecionados) {
            if (_.isArray(aneisSelecionados)) {
              scope.configuracao.aneis = _.map(aneisSelecionados, 'id');
            }
          }, true);
        }
      };
    }]);

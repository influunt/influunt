'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:imposicaoPlano
 * @description
 * # imposicaoPlano
 */
angular.module('influuntApp')
  .directive('imposicaoPlano', ['HorariosService', 'imposicoesService', '$filter',
      function (HorariosService, imposicoesService, $filter) {
      return {
        templateUrl: 'views/directives/imposicoes/imposicao-plano-form.html',
        restrict: 'E',
        scope: {
          aneisSelecionados: '=',
          idsTransacoes: '=',
          trackTransaction: '=',
          dismissOnSubmit: '='
        },
        link: function postLink(scope, el) {
          scope.LIMITE_MINIMO_DURACAO = imposicoesService.LIMITE_MINIMO_DURACAO;
          scope.LIMITE_MAXIMO_DURACAO = imposicoesService.LIMITE_MAXIMO_DURACAO;

          imposicoesService.setTrackTransaction(!!scope.trackTransaction);

          scope.configuracao = {};
          scope.planos = HorariosService.getPlanos();
          scope.planos = scope.planos.map(function(plano) { return ++plano; });
          scope.planos.push($filter('translate')('imporConfig.plano.planoTemporario'));

          var getControladores;
          scope.imporPlano = function() {
            scope.idsTransacoes = {};
            return imposicoesService.imposicao('plano', scope.configuracao, scope.idsTransacoes);
          };

          scope.todosAneisDoMesmoControlador = function() {
            return getControladores().length === 1;
          };

          scope.getControlador = function() {
            return getControladores()[0];
          };

          scope.$watch('aneisSelecionados', function(aneisSelecionados) {
            if (_.isArray(aneisSelecionados)) {
              scope.configuracao.aneis = _.map(aneisSelecionados, 'id');
            }
          }, true);

          getControladores = function() {
            return _.chain(scope.aneisSelecionados).map('controlador.id').uniq().value();
          };

          $(el).find('#impor-plano-submit').on('click', function() {
            if (scope.dismissOnSubmit) {
              $('#modal-impor-plano').modal('toggle');
            }
          });
        }
      };
    }])

  .directive('imposicaoPlanoPopup', [function () {
    return {
      templateUrl: 'views/directives/imposicoes/imposicao-plano-popup.html',
      restrict: 'E',
      scope: {
        aneisSelecionados: '=',
        idsTransacoes: '=',
        trackTransaction: '='
      }
    };
  }])
;


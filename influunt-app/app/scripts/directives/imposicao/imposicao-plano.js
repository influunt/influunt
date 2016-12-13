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
          dismissOnSubmit: '=',
          timeout: '=?',
          transacoes: '=?'
        },
        link: function postLink(scope, el) {
          var DEFAUT_TIMEOUT = 60;
          scope.LIMITE_MINIMO_DURACAO = imposicoesService.LIMITE_MINIMO_DURACAO;
          scope.LIMITE_MAXIMO_DURACAO = imposicoesService.LIMITE_MAXIMO_DURACAO;

          scope.configuracao = {};
          scope.planos = HorariosService.getPlanos();
          scope.planos = scope.planos.map(function(plano) { return ++plano; });
          scope.planos.push($filter('translate')('imporConfig.plano.planoTemporario'));

          var getControladores;
          scope.imporPlano = function() {
            scope.configuracao.timeout = scope.timeout || DEFAUT_TIMEOUT;
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
              scope.configuracao.aneisIds = _.map(aneisSelecionados, 'id');
            }
          }, true);

          scope.$watch('transacoes', function(transacoes) {
            imposicoesService.alertStatusTransacao(transacoes, scope.aneisSelecionados, 'plano');
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
        trackTransaction: '=',
        transacoes: '=?'
      }
    };
  }])
;


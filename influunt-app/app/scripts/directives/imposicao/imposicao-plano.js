'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:imposicaoPlano
 * @description
 * # imposicaoPlano
 */
angular.module('influuntApp')
  .directive('imposicaoPlano', ['HorariosService', 'Restangular', 'influuntBlockui',
      function (HorariosService, Restangular, influuntBlockui) {
      return {
        templateUrl: 'views/directives/imposicoes/imposicao-plano.html',
        restrict: 'E',
        scope: {
          aneisSelecionados: '=',
          idsTransacoes: '='
        },
        link: function postLink(scope) {
          scope.configuracao = {};
          scope.planos = HorariosService.getPlanos();

          var getControladores;

          scope.todosAneisDoMesmoControlador = function() {
            return getControladores().length === 1;
          };

          scope.getControlador = function() {
            return getControladores()[0];
          };

          scope.imporPlano = function() {
            // horario de entrada
            return Restangular
              .one('imposicoes', 'plano')
              .post(null, scope.configuracao)
              .then(function(response) {
                _.each(response.plain(), function(transacaoId, controladorId) {
                  scope.idsTransacoes[controladorId] = transacaoId;
                });
              })
              .catch(console.error)
              .finally(influuntBlockui.unblock);
          };

          scope.$watch('aneisSelecionados', function(aneisSelecionados) {
            if (_.isArray(aneisSelecionados)) {
              scope.configuracao.aneis = _.map(aneisSelecionados, 'id');
            }
          }, true);

          getControladores = function() {
            return _.chain(scope.aneisSelecionados).map('controlador.id').uniq().value();
          };
        }
      };
    }]);

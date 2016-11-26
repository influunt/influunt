'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:imposicaoModo
 * @description
 * # imposicaoModo
 */
angular.module('influuntApp')
  .directive('imposicaoModo', ['HorariosService', 'Restangular', 'influuntBlockui',
      function (HorariosService, Restangular, influuntBlockui) {
      return {
        templateUrl: 'views/directives/imposicoes/imposicao-modo.html',
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

          scope.imporModo = function() {
            // horario de entrada
            return Restangular
              .one('imposicoes', 'modo_operacao')
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

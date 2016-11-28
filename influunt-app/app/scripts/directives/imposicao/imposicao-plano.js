'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:imposicaoPlano
 * @description
 * # imposicaoPlano
 */
angular.module('influuntApp')
  .directive('imposicaoPlano', ['HorariosService', 'Restangular', 'influuntBlockui', '$filter', 'toast', 'mqttTransactionStatusService',
      function (HorariosService, Restangular, influuntBlockui, $filter, toast, mqttTransactionStatusService) {
      return {
        templateUrl: 'views/directives/imposicoes/imposicao-plano.html',
        restrict: 'E',
        scope: {
          aneisSelecionados: '=',
          idsTransacoes: '=',
          trackTransaction: '='
        },
        link: function postLink(scope) {
          scope.LIMITE_MINIMO_DURACAO = 15;
          scope.LIMITE_MAXIMO_DURACAO = 600;

          scope.configuracao = {};
          scope.planos = HorariosService.getPlanos();

          var getControladores, transactionTracker;

          scope.todosAneisDoMesmoControlador = function() {
            return getControladores().length === 1;
          };

          scope.getControlador = function() {
            return getControladores()[0];
          };

          scope.imporPlano = function() {
            var horarioEntrada = moment(scope.configuracao.horarioEntradaObj.data)
              .startOf('day')
              .add(parseInt(scope.configuracao.horarioEntradaObj.hora), 'hours')
              .add(parseInt(scope.configuracao.horarioEntradaObj.minuto), 'minutes')
              .add(parseInt(scope.configuracao.horarioEntradaObj.segundo), 'seconds');

          scope.configuracao.horarioEntrada = horarioEntrada.toDate().getTime();

            // horario de entrada
            return Restangular
              .one('imposicoes', 'plano')
              .post(null, scope.configuracao)
              .then(function(response) {
                _.each(response.plain(), function(transacaoId, controladorId) {
                  scope.idsTransacoes[controladorId] = transacaoId;
                  return scope.trackTransaction && transactionTracker(transacaoId);
                });

              })
              .catch(console.error)
              .finally(influuntBlockui.unblock);
          };

          transactionTracker = function(id) {
            return mqttTransactionStatusService
              .watchTransaction(id)
              .then(function(transmitido) {
                if (transmitido) {
                  toast.success($filter('translate')('imporConfig.imposicaoPlano.sucesso'));
                } else {
                  toast.warn($filter('translate')('imporConfig.imposicaoPlano.erro'));
                }
              });
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

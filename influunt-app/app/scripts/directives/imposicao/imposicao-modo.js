'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:imposicaoModo
 * @description
 * # imposicaoModo
 */
angular.module('influuntApp')
  .directive('imposicaoModo', ['HorariosService', 'Restangular', 'influuntBlockui', '$filter', 'toast', 'mqttTransactionStatusService',
      function (HorariosService, Restangular, influuntBlockui, $filter, toast, mqttTransactionStatusService) {
      return {
        templateUrl: 'views/directives/imposicoes/imposicao-modo.html',
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

          scope.imporModo = function() {
            var horarioEntrada = moment(scope.configuracao.horarioEntradaObj.data)
              .startOf('day')
              .add(parseInt(scope.configuracao.horarioEntradaObj.hora), 'hours')
              .add(parseInt(scope.configuracao.horarioEntradaObj.minuto), 'minutes')
              .add(parseInt(scope.configuracao.horarioEntradaObj.segundo), 'seconds');


            scope.configuracao.horarioEntrada = horarioEntrada.toDate().getTime();
            // horario de entrada
            return Restangular
              .one('imposicoes', 'modo_operacao')
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
                  toast.success($filter('translate')('imporConfig.imposicaoModo.sucesso'));
                } else {
                  toast.warn($filter('translate')('imporConfig.imposicaoModo.erro'));
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

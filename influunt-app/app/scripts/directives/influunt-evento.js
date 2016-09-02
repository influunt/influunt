'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntEvento
 * @description
 * # influuntEvento
 */
angular.module('influuntApp')
  .directive('influuntEvento', ['influuntAlert', 'TabelaHorariaService',
    function (influuntAlert, TabelaHorariaService) {
      return {
        templateUrl: 'views/directives/influunt-evento.html',
        restrict: 'A',
        scope: {
          evento: '=',
          planos: '=',
          horarios: '=',
          minutos: '=',
          segundos: '=',
          dias: '=',
          tabelaHoraria: '=',
          podeRemover: '=',
          erros: '=',
          onVerificaAtualizacaoDeEventos: '&',
          onVisualizarPlano: '&',
          onRemoverEvento: '&',
          readOnly: '=?'
        },
        link: function influuntEvento(scope) {
          scope.removeEvento = function(){
            return influuntAlert.delete().then(function(confirma) {
              return confirma && scope.onRemoverEvento()(scope.evento);
            });
          };

          scope.abrirPlano = function(){
            scope.onVisualizarPlano()(scope.evento);
          };

          scope.verificaAtualizacao = function(){
            scope.onVerificaAtualizacaoDeEventos()(scope.evento);
          };

          scope.minDate = moment().startOf('year').toDate();
          scope.maxDate = moment().endOf('year').toDate();

          scope.$watch('evento', function(evento){
            if(evento) {
              TabelaHorariaService.initialize(evento.tipo);
              scope.TabelaHorariaService = TabelaHorariaService;
            }
          });

          return true;
        }
      };
    }]);

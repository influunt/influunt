'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntEvento
 * @description
 * # influuntEvento
 */
angular.module('influuntApp')
  .directive('influuntEvento', ['influuntAlert',
    function (influuntAlert) {
      return {
        templateUrl: 'views/directives/influunt-evento.html',
        restrict: 'A',
        scope: {
          evento: '=',
          planos: '=',
          horarios: '=',
          dias: '=',
          podeRemover: '=',
          erros: '=',
          onVerificaAtualizacaoDeEventos: '&',
          onVisualizarPlano: '&',
          onRemoverEvento: '&'
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

          return true;
        }
      };
    }]);

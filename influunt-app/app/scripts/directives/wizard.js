'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:wizard
 * @description
 * # wizard
 *
 * Embarca o plugin jQuery.steps ao angularjs.
 *
 * CORRIGIR: As traduções das labels ainda estão estáticas, porque o filter é
 * carregado antes mesmo do angular-translate estar pronto (ele carrega o arquivo
 * i18n de forma assincrona).
 */
angular.module('influuntApp')
  .directive('wizard', ['$compile',
    function ($compile) {
      return {
        restrict: 'A',
        scope: {
          onStepChanging: '=',
          onFinishing: '=',
          onFinished: '='
        },
        link: function (scope, ele) {
          ele.wrapInner('<div class="steps-wrapper">');
          var steps = ele.children('.steps-wrapper').steps({
            onStepChanging: scope.onStepChanging,
            onFinishing: scope.onFinishing,
            onFinished: scope.onFinished,
            labels: {
              cancel: 'cancelar',
              current: 'atual',
              pagination: 'paginação',
              finish: 'finalizar',
              next: 'próximo',
              previous: 'Anterior',
              loading: 'carregando'
            }
          });
          $compile(steps)(scope);
        }
      };
    }]);
